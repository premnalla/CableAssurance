
#include <config.h>
#include <stdio.h>
#include <errno.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stddef.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/socket.h>
#include <sys/file.h>
#include <netinet/in_systm.h>
#include <netinet/in.h>
#ifdef LINUX
#include "linux.h"
#else
#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
#endif
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/select.h>
#include "options.h"

#define MIN_PING_DATA sizeof( PING_DATA )
#define MAX_IP_PACKET 65536 /* (theoretical) max IP packet size */
#define SIZE_IP_HDR   20
#define SIZE_ICMP_HDR ICMP_MINLEN   /* from ip_icmp.h */
#define MAX_PING_DATA ( MAX_IP_PACKET - SIZE_IP_HDR - SIZE_ICMP_HDR )

/* sized so as to be like traditional ping */
#define DEFAULT_PING_DATA_SIZE  ( MIN_PING_DATA + 44 )

/* response time array flags */
#define RESP_WAITING  -1
#define RESP_UNUSED   -2

#define ICMP_UNREACH_MAXTYPE  15

extern char *optarg;
extern int optind,opterr;
extern int h_errno;


/* Long names for ICMP packet types */
char *icmp_type_str[19] =
{
  "ICMP Echo Reply",      /* 0 */
  "",
  "",
  "ICMP Unreachable",     /* 3 */
  "ICMP Source Quench",   /* 4 */
  "ICMP Redirect",      /* 5 */
  "",
  "",
  "ICMP Echo",        /* 8 */
  "",
  "",
  "ICMP Time Exceeded",   /* 11 */
  "ICMP Paramter Problem",  /* 12 */
  "ICMP Timestamp Request", /* 13 */
  "ICMP Timestamp Reply",   /* 14 */
  "ICMP Information Request", /* 15 */
  "ICMP Information Reply", /* 16 */
  "ICMP Mask Request",    /* 17 */
  "ICMP Mask Reply"     /* 18 */
};

char *icmp_unreach_str[16] =
{
  "ICMP Network Unreachable",                   /* 0 */
  "ICMP Host Unreachable",                    /* 1 */
  "ICMP Protocol Unreachable",                  /* 2 */
  "ICMP Port Unreachable",                    /* 3 */
  "ICMP Unreachable (Fragmentation Needed)",            /* 4 */
  "ICMP Unreachable (Source Route Failed)",           /* 5 */
  "ICMP Unreachable (Destination Network Unknown)",       /* 6 */
  "ICMP Unreachable (Destination Host Unknown)",          /* 7 */
  "ICMP Unreachable (Source Host Isolated)",            /* 8 */
  "ICMP Unreachable (Communication with Network Prohibited)",   /* 9 */
  "ICMP Unreachable (Communication with Host Prohibited)",    /* 10 */
  "ICMP Unreachable (Network Unreachable For Type Of Service)", /* 11 */
  "ICMP Unreachable (Host Unreachable For Type Of Service)",    /* 12 */
  "ICMP Unreachable (Communication Administratively Prohibited)", /* 13 */
  "ICMP Unreachable (Host Precedence Violation)",         /* 14 */
  "ICMP Unreachable (Precedence cutoff in effect)"        /* 15 */
};

typedef struct host_entry
{
     struct host_entry    *prev,*next;        /* doubly linked list */
     int                  i;                  /* index into array */
     char                 *name;              /* name as given by user */
     char                 *host;              /* text description of host */
     char                 *pad;               /* pad to align print names */
     struct sockaddr_in   saddr;              /* internet address */
     int                  timeout;            /* time to wait for response */
     u_char               running;            /* unset when through sending */
     u_char               waiting;            /* waiting for response */
     struct timeval       last_send_time;     /* time of last packet sent */
     int                  num_sent;           /* number of ping packets sent */
     int                  num_recv;           /* number of pings received */
     int                  max_reply;          /* longest response time */
     int                  min_reply;          /* shortest response time */
     int                  total_time;         /* sum of response times */
     int                  num_sent_i;         /* number of ping packets sent */
     int                  num_recv_i;         /* number of pings received */
     int                  max_reply_i;        /* longest response time */
     int                  min_reply_i;        /* shortest response time */
     int                  total_time_i;       /* sum of response times */
     int                  *resp_times;        /* individual response times */
} HOST_ENTRY;

typedef struct ping_data
{
  int                  ping_count;         /* counts up to -c count or 1 */
  struct timeval       ping_ts;            /* time sent */
} PING_DATA;


struct timeval current_time;    /* current time (pseudo) */
struct timeval start_time;
struct timeval end_time;
struct timeval last_send_time;    /* time last ping was sent */
struct timeval last_report_time;  /* time last report was printed */
struct timezone tz;
int ident;          /* our pid */
int s;            /* socket */
int num_alive = 0,          /* total number alive */
    num_unreachable = 0,
    num_noaddress = 0;        /* total number of addresses not found */
int elapsed_flag, version_flag, count_flag, loop_flag;
int num_timeout = 0,        /* number of times select timed out */
    num_pingsent = 0,       /* total pings sent */
    num_pingreceived = 0,     /* total pings received */
    num_othericmprcvd = 0;      /* total non-echo-reply ICMP received */
u_int ping_data_size = DEFAULT_PING_DATA_SIZE;
u_int ping_pkt_size;
int num_jobs = 0;         /* number of hosts still to do */
int num_hosts;            /* total number of hosts */
HOST_ENTRY *rrlist = NULL;  /* linked list of hosts be pinged */
HOST_ENTRY **table = NULL;  /* array of pointers to items in the list */
HOST_ENTRY *cursor;
/* times get *100 because all times are calculated in 10 usec units, not ms */
u_int retry = DEFAULT_RETRY;
u_int timeout = DEFAULT_TIMEOUT * 100;
u_int interval = DEFAULT_INTERVAL * 100;
u_int perhost_interval = DEFAULT_PERHOST_INTERVAL * 100;
float backoff = DEFAULT_BACKOFF_FACTOR;
u_int select_time = DEFAULT_SELECT_TIME * 100;
int max_hostname_len = 0;
u_int count = 1;
u_int trials;
u_int report_interval = 0;
int verbose_flag, quiet_flag, stats_flag, unreachable_flag, alive_flag;



void errno_crash_and_burn(char * str)
{
  fprintf(stderr, "%s\n", str);
  exit (-1);
}


void crash_and_burn(char * str)
{
  errno_crash_and_burn(str);
}


int in_cksum( u_short *p, int n )
{
  register u_short answer;
  register long sum = 0;
  u_short odd_byte = 0;

  while( n > 1 )
  {
    sum += *p++;
    n -= 2;

  }/* WHILE */


  /* mop up an odd byte, if necessary */
  if( n == 1 )
  {
    *( u_char* )( &odd_byte ) = *( u_char* )p;
    sum += odd_byte;

  }/* IF */

  sum = ( sum >> 16 ) + ( sum & 0xffff ); /* add hi 16 to low 16 */
  sum += ( sum >> 16 );         /* add carry */
  answer = ~sum;              /* ones-complement, truncate*/

  return ( answer );

} /* in_cksum() */


void remove_job( HOST_ENTRY *h )
{
  h->running = 0;
  h->waiting = 0;
  --num_jobs;

  if( num_jobs )
  {
    /* remove us from list of active jobs */
    h->prev->next = h->next;
    h->next->prev = h->prev;
    if( h==cursor )
      cursor = h->next;

  }/* IF */
  else
  {
    cursor = NULL;
    rrlist = NULL;

  }/* ELSE */

} /* remove_job() */


void add_addr( char *name, char *host, struct in_addr ipaddr )
{
  HOST_ENTRY *p;
  int n, *i;

  p = ( HOST_ENTRY* )malloc( sizeof( HOST_ENTRY ) );

  if( !p ) {
    crash_and_burn( "can't allocate HOST_ENTRY" );
  }

  memset( ( char* ) p, 0, sizeof( HOST_ENTRY ) );

  p->name = name;
  p->host = host;
  p->saddr.sin_family = AF_INET;
  p->saddr.sin_addr = ipaddr;
  p->timeout = timeout;
  p->running = 1;
  p->min_reply = 10000000;

  if( strlen( p->host ) > max_hostname_len ) {
    max_hostname_len = strlen( p->host );
  }

#if 0
  /* array for response time results */
  if( !loop_flag ) {
    i = ( int* )malloc( trials * sizeof( int ) );
    if( !i ) {
      crash_and_burn( "can't allocate resp_times array" );
    }

    for( n = 1; n < trials; n++ ) {
      i[n] = RESP_UNUSED;
    }

    p->resp_times = i;

  }/* IF */
#endif

  if( !rrlist ) {
    rrlist = p;
    p->next = p;
    p->prev = p;

  }/* IF */
  else
  {
    p->next = rrlist;
    p->prev = rrlist->prev;
    p->prev->next = p;
    p->next->prev = p;

  }/* ELSE */

  num_hosts++;
}


int 
recvfrom_wto( int s, char *buf, int len, struct sockaddr *saddr, int timo )
{
  int nfound, slen, n;
  struct timeval to;
  fd_set readset, writeset;

  to.tv_sec = timo / 100000;
  to.tv_usec = ( timo - ( to.tv_sec * 100000 ) ) * 10;

  FD_ZERO( &readset );
  FD_ZERO( &writeset );
  FD_SET( s, &readset );

  nfound = select( s + 1, &readset, &writeset, NULL, &to );

  if( nfound < 0 ) {
    errno_crash_and_burn( "select" );
  }

  if( nfound == 0 ) {
    return -1;              /* timeout */
  }

  slen = sizeof( struct sockaddr );

  n = recvfrom( s, buf, len, 0, saddr, &slen );

  if( n < 0 ) {
    errno_crash_and_burn( "recvfrom" );
  }

  return n;

} /* recvfrom_wto() */


void send_ping( int s, HOST_ENTRY *h )
{
  char *buffer;
  struct icmp *icp;
  PING_DATA *pdp;
  int n;

  buffer = ( char* )malloc( ( size_t )ping_pkt_size );
  if( !buffer )
    crash_and_burn( "can't malloc ping packet" );

  memset( buffer, 0, ping_pkt_size * sizeof( char ) );
  icp = ( struct icmp* )buffer;

  gettimeofday( &h->last_send_time, &tz );
  icp->icmp_type = ICMP_ECHO;
  icp->icmp_code = 0;
  icp->icmp_cksum = 0;
  icp->icmp_seq = h->i;
  icp->icmp_id = ident;

  pdp = ( PING_DATA* )( buffer + SIZE_ICMP_HDR );
  pdp->ping_ts = h->last_send_time;
  pdp->ping_count = h->num_sent;

  icp->icmp_cksum = in_cksum( ( u_short* )icp, ping_pkt_size );

  n = sendto( s, buffer, ping_pkt_size, 0,
    ( struct sockaddr* )&h->saddr, sizeof( struct sockaddr_in ) );

  if( n < 0 || n != ping_pkt_size ) {
    num_unreachable++;
    remove_job( h );
  } else {
    /* mark this trial as outstanding */
#if 0
    if( !loop_flag )
      h->resp_times[h->num_sent] = RESP_WAITING;
#endif

    h->num_sent++;
    h->num_sent_i++;
    h->waiting++;
    num_pingsent++;
    last_send_time = h->last_send_time;

  }

  free( buffer );
}


int handle_random_icmp( struct icmp *p, int psize, struct sockaddr_in *addr )
{
  struct icmp *sent_icmp;
  struct ip *sent_ip;
  u_char *c;
  HOST_ENTRY *h;

  c = ( u_char* )p;
  switch( p->icmp_type )
  {
  case ICMP_UNREACH:
    sent_icmp = ( struct icmp* )( c + 28 );

    if( ( sent_icmp->icmp_type == ICMP_ECHO ) &&
      ( sent_icmp->icmp_id == ident ) &&
      ( sent_icmp->icmp_seq < ( n_short )num_hosts ) )
    {
      /* this is a response to a ping we sent */
      h = table[sent_icmp->icmp_seq];

      if( p->icmp_code > ICMP_UNREACH_MAXTYPE )
      {
        fprintf( stderr, "ICMP Unreachable (Invalid Code) from %s for ICMP Echo sent to %s",
          inet_ntoa( addr->sin_addr ), h->host );

      }/* IF */
      else
      {
        fprintf( stderr, "%s from %s for ICMP Echo sent to %s",
          icmp_unreach_str[p->icmp_code], inet_ntoa( addr->sin_addr ), h->host );

      }/* ELSE */

      if( inet_addr( h->host ) == -1 )
        fprintf( stderr, " (%s)", inet_ntoa( h->saddr.sin_addr ) );

      fprintf( stderr, "\n" );

    }/* IF */

    return 1;

  case ICMP_SOURCEQUENCH:
  case ICMP_REDIRECT:
  case ICMP_TIMXCEED:
  case ICMP_PARAMPROB:
    sent_icmp = ( struct icmp* )( c + 28 );
    if( ( sent_icmp->icmp_type = ICMP_ECHO ) &&
      ( sent_icmp->icmp_id = ident ) &&
      ( sent_icmp->icmp_seq < ( n_short )num_hosts ) )
    {
      /* this is a response to a ping we sent */
      h = table[sent_icmp->icmp_seq];
      fprintf( stderr, "%s from %s for ICMP Echo sent to %s",
        icmp_type_str[p->icmp_type], inet_ntoa( addr->sin_addr ), h->host );

      if( inet_addr( h->host ) == -1 )
        fprintf( stderr, " (%s)", inet_ntoa( h->saddr.sin_addr ) );

      fprintf( stderr, "\n" );

    }/* IF */

    return 2;

  /* no way to tell whether any of these are sent due to our ping */
  /* or not (shouldn't be, of course), so just discard            */
  case ICMP_TSTAMP:
  case ICMP_TSTAMPREPLY:
  case ICMP_IREQ:
  case ICMP_IREQREPLY:
  case ICMP_MASKREQ:
  case ICMP_MASKREPLY:
  default:
    return 0;

  }/* SWITCH */

} /* handle_random_icmp() */


char * sprint_tm( int t )
{
  static char buf[10];

  /* <= 0.99 ms */
  if( t < 100 )
  {
    sprintf( buf, "0.%02d", t );
    return( buf );

  }/* IF */

  /* 1.00 - 9.99 ms */
  if( t < 1000 )
  {
    sprintf( buf, "%d.%02d", t / 100, t % 100 );
    return( buf );

  }/* IF */

  /* 10.0 - 99.9 ms */
  if( t < 10000 )
  {
    sprintf( buf, "%d.%d", t / 100, ( t % 100 ) / 10 );
    return( buf );

  }/* IF */

  /* >= 100 ms */
  sprintf( buf, "%d", t / 100 );
  return( buf );

} /* sprint_tm() */


int wait_for_reply( void )
{
  int result;
  static char buffer[4096];
  struct sockaddr_in response_addr;
  struct ip *ip;
  int hlen;
  struct icmp *icp;
  int n, avg;
  HOST_ENTRY *h;
  long this_reply;
  int this_count;
  struct timeval sent_time;

  result = recvfrom_wto( s, buffer, 4096,
    ( struct sockaddr* )&response_addr, select_time );

  if( result < 0 ) {
    return 0; /* timeout */
  }

  ip = ( struct ip* )buffer;

  hlen = ip->ip_hl << 2;

  if( result < hlen + ICMP_MINLEN )
  {
    if( verbose_flag )
      printf( "received packet too short for ICMP (%d bytes from %s)\n", result,
        inet_ntoa( response_addr.sin_addr ) );

    return( 1 ); /* too short */

  }/* IF */

  icp = ( struct icmp* )( buffer + hlen );

  if( icp->icmp_type != ICMP_ECHOREPLY ) {
    /* handle some problem */
    if( handle_random_icmp( icp, result, &response_addr ) )
      num_othericmprcvd++;

    return 1;

  }/* IF */

  if( icp->icmp_id != ident ) {
    return 1; /* packet received, but not the one we are looking for! */
  }

  num_pingreceived++;

  if( icp->icmp_seq  >= ( n_short )num_hosts ) {
    return( 1 ); /* packet received, don't worry about it anymore */
  }

  n = icp->icmp_seq;
  h = table[n];

  /* received ping is cool, so process it */
  gettimeofday( &current_time, &tz );
  h->waiting = 0;
  h->timeout = timeout;
  h->num_recv++;
  h->num_recv_i++;

  memcpy( &sent_time, icp->icmp_data + offsetof( PING_DATA, ping_ts ), sizeof( sent_time ) );
  memcpy( &this_count, icp->icmp_data, sizeof( this_count ) );

#if 0
  this_reply = timeval_diff( &current_time, &sent_time );
  if( this_reply > max_reply ) max_reply = this_reply;
  if( this_reply < min_reply ) min_reply = this_reply;
  if( this_reply > h->max_reply ) h->max_reply = this_reply;
  if( this_reply < h->min_reply ) h->min_reply = this_reply;
  if( this_reply > h->max_reply_i ) h->max_reply_i = this_reply;
  if( this_reply < h->min_reply_i ) h->min_reply_i = this_reply;
  sum_replies += this_reply;
  h->total_time += this_reply;
  h->total_time_i += this_reply;
  total_replies++;

  /* note reply time in array, probably */
  if( !loop_flag )
  {
    if( ( this_count >= 0 ) && ( this_count < trials ) )
    {
      if( h->resp_times[this_count] != RESP_WAITING )
      {
        if( !per_recv_flag )
        {
          fprintf( stderr, "%s : duplicate for [%d], %d bytes, %s ms",
            h->host, this_count, result, sprint_tm( this_reply ) );

          if( response_addr.sin_addr.s_addr != h->saddr.sin_addr.s_addr )
            fprintf( stderr, " [<- %s]", inet_ntoa( response_addr.sin_addr ) );

          fprintf( stderr, "\n" );

        }/* IF */
          }/* IF */
      else
        h->resp_times[this_count] = this_reply;

    }/* IF */
    else
    {
      /* count is out of bounds?? */
      fprintf( stderr, "%s : duplicate for [%d], %d bytes, %s ms\n",
        h->host, this_count, result, sprint_tm( this_reply ) );

    }/* ELSE */
  }/* IF */
#endif

  if( h->num_recv == 1 )
  {
    num_alive++;
    if( verbose_flag || alive_flag )
    {
      printf( "%s", h->host );

      if( verbose_flag )
        printf( " is alive" );

      if( elapsed_flag )
        printf( " (%s ms)", sprint_tm( this_reply ) );

      if( response_addr.sin_addr.s_addr != h->saddr.sin_addr.s_addr )
        printf( " [<- %s]", inet_ntoa( response_addr.sin_addr ) );

      printf( "\n" );

    }/* IF */
  }/* IF */

#if 0
  if( per_recv_flag )
  {
    avg = h->total_time / h->num_recv;
    printf( "%s%s : [%d], %d bytes, %s ms",
      h->host, h->pad, this_count, result, sprint_tm( this_reply ) );
    printf( " (%s avg, ", sprint_tm( avg ) );

    if( h->num_recv <= h->num_sent )
    {
      printf( "%d%% loss)",
        ( ( h->num_sent - h->num_recv ) * 100 ) / h->num_sent );

    }/* IF */
    else
    {
      printf( "%d%% return)",
        ( h->num_recv * 100 ) / h->num_sent );

    }/* ELSE */

    if( response_addr.sin_addr.s_addr != h->saddr.sin_addr.s_addr )
      printf( " [<- %s]", inet_ntoa( response_addr.sin_addr ) );

    printf( "\n" );

  }/* IF */
#endif

  return num_jobs;

} /* wait_for_reply() */



int
main(int argc, char * argv[])
{
  struct protoent *proto;
  uid_t uid;
  u_int ipaddress;
  struct in_addr *ipa = ( struct in_addr* )&ipaddress;
  const char * host = "192.168.1.103";

  verbose_flag = 1;
  alive_flag = 1;
  elapsed_flag = 1;

  if( geteuid() ) {
    fprintf( stderr, "This program can only be run by root, or it must be setuid root.\n" );
    exit( 3 );
  }

  /* confirm that ICMP is available on this machine */
  if( ( proto = getprotobyname( "icmp" ) ) == NULL ) {
    crash_and_burn( "icmp: unknown protocol" );
  }

  /* create raw socket for ICMP calls (ping) */
  s = socket( AF_INET, SOCK_RAW, proto->p_proto );

  if( s < 0 ) {
    errno_crash_and_burn( "can't create raw socket" );
  }

  if( ( uid = getuid() ) )
  {
    seteuid( getuid() );

  }/* IF */

  ident = getpid() & 0xFFFF;

  ipaddress = inet_addr(host);

  add_addr(host, host, *ipa);

  table = ( HOST_ENTRY** )malloc( sizeof( HOST_ENTRY* ) * num_hosts );
  if( !table ) {
    crash_and_burn( "Can't malloc array of hosts" );
  }

  cursor = rrlist;

  for( num_jobs = 0; num_jobs < num_hosts; num_jobs++ ) {
    table[num_jobs] = cursor;
    cursor->i = num_jobs;
    cursor=cursor->next;
  }

  ping_pkt_size = ping_data_size + SIZE_ICMP_HDR;

  cursor = rrlist;

  while( num_jobs ) {

    if( num_pingsent ) {
      while( wait_for_reply() );  /* call wfr until we timeout */
    }

    if( cursor ) {
      cursor = cursor->next;
    }

    send_ping( s, cursor );
  }


  return (0);
}
