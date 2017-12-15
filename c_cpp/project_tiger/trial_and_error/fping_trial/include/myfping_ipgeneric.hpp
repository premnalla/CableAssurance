
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _myfping3_hpp_
#define _myfping3_hpp_

//********************************************************************
// include files
//********************************************************************
// #include <config.h>
#include <stdio.h>
#include <pthread.h>
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
// #ifdef LINUX
// #include "linux.h"
// #else
#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
// #endif
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/select.h>
#include "options.h"

//********************************************************************
// definitions/macros
//********************************************************************
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

//********************************************************************
// forward declerations
//********************************************************************

extern "C" {

typedef struct host_entry
{
     struct host_entry    *prev,*next;        /* doubly linked list */
     int                  i;                  /* index into array */
     char                 *name;              /* name as given by user */
     char                 *host;              /* text description of host */
     char                 *pad;               /* pad to align print names */
     // struct sockaddr_storage saddr;              /* internet address */
     // struct sockaddr      saddr;              /* internet address */
     struct sockaddr_in6  saddr;              /* internet address */
     size_t               addr_len;           /* internet address */
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

}; /* extern "C" */

/** 
 * This class is used to ...
 * 
 * 
 * file/class: myfping3.hpp
 * 
 * Design Document:
 * 
 * System:
 *  
 * Sub-system:
 * 
 * History:
 * 
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 * 
 */

class myfping3 
{
public:

  /// default constructor
  myfping3();

  /// destructor
  virtual ~myfping3();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return 
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed 
   *     \item AX_FAILED  unsuccessfully executed 
   *   \end{itemize}
   * @see
   */

  void setThreadId(pthread_t);

  void run(void);

protected:


private:

  myfping3(const myfping3 &);

  void errno_crash_and_burn(char * str);
  void crash_and_burn(char * str);
  int in_cksum( u_short *p, int n );
  void remove_job( HOST_ENTRY *h );
  void add_addr( char * );
  // int recvfrom_wto( char *buf, int len, struct sockaddr *saddr, int timo );
  int send_echo_request( HOST_ENTRY * h );
  int handle_random_icmp( struct icmp *p, int psize, struct sockaddr_in *addr );
  char * sprint_tm( int t );
  int wait_for_echo_reply( void );
  int receive_echo_reply( HOST_ENTRY * );


  struct timeval current_time;    /* current time (pseudo) */
  struct timeval start_time;
  struct timeval end_time;
  struct timeval last_send_time;    /* time last ping was sent */
  struct timeval last_report_time;  /* time last report was printed */
  struct timezone tz;
  int ident;          /* our pid */
  int s;            /* socket */
  int num_alive,          /* total number alive */
      num_unreachable,
      num_noaddress;        /* total number of addresses not found */
  int elapsed_flag, version_flag, count_flag, loop_flag;
  int num_timeout,        /* number of times select timed out */
      num_pingsent,       /* total pings sent */
      num_pingreceived,     /* total pings received */
      num_othericmprcvd;      /* total non-echo-reply ICMP received */
  u_int ping_data_size;
  u_int ping_pkt_size;
  int num_jobs;         /* number of hosts still to do */
  int num_hosts;            /* total number of hosts */
  HOST_ENTRY *head;  /* linked list of hosts be pinged */
  HOST_ENTRY *tail;  /* linked list of hosts be pinged */
  HOST_ENTRY **table;  /* array of pointers to items in the list */
  HOST_ENTRY *cursor;

  /* times get *100 because all times are calculated in 10 usec units, not ms */
  u_int retry;
  u_int timeout;
  u_int interval;
  u_int perhost_interval;
  float backoff;
  u_int select_time;
  int max_hostname_len;
  u_int count;
  u_int trials;
  u_int report_interval;
  int verbose_flag, quiet_flag, stats_flag, unreachable_flag, alive_flag;

  char sprint_tm_buf[10];

  /// my
  pthread_t m_threadId;
};

#endif // _myfping3_hpp_
