
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "myfping3.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************
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

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
myfping3::myfping3() :
  m_threadId(0)
{
  num_alive = num_pingsent = num_pingreceived = num_othericmprcvd = 0;
  num_alive = num_unreachable = num_noaddress = 0;
  ping_data_size = DEFAULT_PING_DATA_SIZE;
  num_jobs = 0;
  head = NULL;
  tail = NULL;
  table = NULL;
  retry = DEFAULT_RETRY;
  timeout = 1000;
  interval = DEFAULT_INTERVAL * 100;
  perhost_interval = DEFAULT_PERHOST_INTERVAL * 100;
  backoff = DEFAULT_BACKOFF_FACTOR;
  select_time = 1000;
  max_hostname_len = 0;
  count = 1;
  report_interval = 0;
  num_hosts = 0;
}


//********************************************************************
// destructor:
//********************************************************************
myfping3::~myfping3()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// myfping3::myfping3()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
myfping3::setThreadId(pthread_t id)
{
  m_threadId = id;
}


//********************************************************************
// method:
//********************************************************************
void
myfping3::run(void)
{
  u_int ipaddress;
  struct in_addr *ipa = ( struct in_addr* )&ipaddress;
  char * host = "192.168.1.103";
  int waitRc;
  int sendRc;
  int recvRc;

  if( geteuid() )
  {
    fprintf( stderr,
      "This program can only be run by root, or it must be setuid root.\n" );

    exit( 3 );

  }/* IF */

  /* create raw socket for ICMP calls (ping) */
  s = socket( AF_INET, SOCK_RAW, IPPROTO_ICMP);

  if( s < 0 ) {
    errno_crash_and_burn( "can't create raw socket" );
  }

  ident = getpid() & 0xFFFF;
  // ident = m_threadId & 0xFFFF;
  // ident = m_threadId;
  // ident = getpid();
  // ident = (int) pthread_self();
  // fprintf(stderr, "ident=%d\n", ident);

  ipaddress = inet_addr(host);

  add_addr(host, host, *ipa);

  cursor = head;

  for( num_jobs = 0; num_jobs < num_hosts; num_jobs++ ) {
    cursor->i = num_jobs;
    cursor=cursor->next;
  }

  ping_pkt_size = ping_data_size + SIZE_ICMP_HDR;

  cursor = head;

  bool forever = true;

  while( forever ) {

    sendRc = send_echo_request( cursor );

    waitRc = wait_for_echo_reply();
    if (waitRc < 0) {
      fprintf(stderr, "select error\n");
      break;
    } else if (waitRc == 0) {
      fprintf(stderr, "timed-out\n");
      break;
    }

    recvRc = receive_echo_reply(cursor);

    cursor = cursor->next;

    if( !cursor ) {
      cursor = head;
    }

    sleep(1);

    // forever = false;

  } // while

  close(s);

}


//********************************************************************
// method:
//********************************************************************
void 
myfping3::errno_crash_and_burn(char * str)
{
  fprintf(stderr, "%s\n", str);
  exit (-1);
}


//********************************************************************
// method:
//********************************************************************
void 
myfping3::crash_and_burn(char * str)
{
  errno_crash_and_burn(str);
}


//********************************************************************
// method:
//********************************************************************
int 
myfping3::in_cksum( u_short *p, int n )
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


//********************************************************************
// method:
//********************************************************************
void 
myfping3::remove_job( HOST_ENTRY *h )
{

  h->running = 1;

#if 0
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

  }/* ELSE */
#endif

} /* remove_job() */


//********************************************************************
// method:
//********************************************************************
void
myfping3::add_addr( char *name, char *host, struct in_addr ipaddr )
{
  HOST_ENTRY * p;

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

  if( !tail ) {

    tail = p;
    head = p;
    p->next = NULL;
    p->prev = NULL;

  } else {

    tail->next = p;
    p->prev = tail;
    p->next = NULL;
    tail = p;

  }

  num_hosts++;
}


//********************************************************************
// method:
//********************************************************************
int
myfping3::send_echo_request( HOST_ENTRY * h )
{
  char * buffer;
  struct icmp * icp;
  PING_DATA * pdp;
  int sendRc;

  // fprintf(stderr, "%s\n", h->name);

  buffer = ( char* )malloc( ( size_t )ping_pkt_size );

  if( !buffer ) {
    crash_and_burn( "can't malloc ping packet" );
  }

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

  sendRc = sendto( s, buffer, ping_pkt_size, 0,
    ( struct sockaddr* )&h->saddr, sizeof( struct sockaddr_in ) );

  if( sendRc < 0 || sendRc != ping_pkt_size ) {

    fprintf(stderr, "send failed\n");

  } else {

    /* mark this trial as outstanding */
    h->num_sent++;
    h->num_sent_i++;
    h->waiting++;
    num_pingsent++;

    // fprintf(stderr, "send succeeded\n");

  }

  free( buffer );

  return (sendRc);
}


//********************************************************************
// method:
//********************************************************************
int
myfping3::handle_random_icmp( struct icmp *p, int psize, struct sockaddr_in *addr )
{
  return (0);
}


//********************************************************************
// method:
//********************************************************************
char *
myfping3::sprint_tm( int t )
{
  // char buf[10];

  /* <= 0.99 ms */
  if( t < 100 )
  {
    sprintf( sprint_tm_buf, "0.%02d", t );
    return( sprint_tm_buf );

  }/* IF */

  /* 1.00 - 9.99 ms */
  if( t < 1000 )
  {
    sprintf( sprint_tm_buf, "%d.%02d", t / 100, t % 100 );
    return( sprint_tm_buf );

  }/* IF */

  /* 10.0 - 99.9 ms */
  if( t < 10000 )
  {
    sprintf( sprint_tm_buf, "%d.%d", t / 100, ( t % 100 ) / 10 );
    return( sprint_tm_buf );

  }/* IF */

  /* >= 100 ms */
  sprintf( sprint_tm_buf, "%d", t / 100 );
  return( sprint_tm_buf );

} /* sprint_tm() */


//********************************************************************
// method:
//********************************************************************
int
myfping3::wait_for_echo_reply( void )
{
  int selectRc;
  struct timeval to;
  fd_set readset, writeset;

  to.tv_sec = select_time / 1000;
  to.tv_usec = (select_time % 1000) * 1000;

  FD_ZERO( &readset );
  FD_ZERO( &writeset );
  FD_SET( s, &readset );

  selectRc = select( s + 1, &readset, &writeset, NULL, &to );
  // selectRc = select( s + 1, &readset, &writeset, NULL, NULL );

  return (selectRc);
}


//********************************************************************
// method:
//********************************************************************
int
myfping3::receive_echo_reply( HOST_ENTRY * h )
{
  static const int buffLen = 4096;
  int slen, receiveRc;
  char buffer[buffLen];
  struct sockaddr_in response_addr;
  struct ip *ip;
  int hlen;
  struct icmp * icp;

  slen = sizeof( struct sockaddr );

  receiveRc = recvfrom( s, buffer, buffLen, 0, (struct sockaddr *) &response_addr, 
                       (socklen_t *) &slen );

  if( receiveRc < 0 ) {
    errno_crash_and_burn( "recvfrom" );
  }

  ip = ( struct ip* )buffer;

  hlen = ip->ip_hl << 2;

  if( receiveRc < hlen + ICMP_MINLEN )
  {
    return( receiveRc ); /* too short */
  }

  icp = ( struct icmp * )( buffer + hlen );

  if( icp->icmp_type != ICMP_ECHOREPLY ) {
    return (receiveRc);
  }

  if( icp->icmp_id != ident ) {
    return (receiveRc); 
  }

  /* received ping is cool, so process it */
  gettimeofday( &current_time, &tz );
  h->waiting = 0;
  h->timeout = timeout;
  h->num_recv++;
  h->num_recv_i++;

  printf("thread %d: host %s is alive\n", (int) pthread_self(), h->host );

  return 1;

} /* receive_echo_reply() */



