
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axPinger_hpp_
#define _axPinger_hpp_

//********************************************************************
// include files
//********************************************************************
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
#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/select.h>
#include "options.h"
#include "axAll.h"
#include "axIcmpCASocket.hpp"

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

/* select/wait-response timeout in milli-seconds */
#define AX_MAX_ICMP_TIMEOUT                                       1000

//********************************************************************
// forward declerations
//********************************************************************

extern "C" {

typedef struct PING_DATA {
  int             pingCount;
  struct timeval  pingTm;
} PING_DATA;

};

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axPinger.hpp
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

class axPinger 
{
public:

  /**
   *
   * IN: socket
   *
   */
  axPinger(axIcmpCASocket *);

  /// default constructor
  axPinger();

  /// destructor
  virtual ~axPinger();

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

  /**
   *
   * IN: socket fd
   *
   */
  // void setSocketFd(AX_INT32);

  /**
   *
   * IN: time (milli sec.)
   *
   */
  void setSelectTimeout(AX_UINT32);

  /**
   *
   * IN: Address Family (AF_INET, AF_INET6), 
   *     Stringified IP address (v4 OR v6); 
   *       NOTE: this must be in either "x.x.x.x" or "ff:ff:..." format
   *
   */
  bool setIpAddress(AX_UINT32, AX_INT8 *);

  /**
   *
   * IN: Valid values (AF_INET, AF_INET6)
   *
   */
  // void setAddressFamily(AX_UINT32);

  /**
   *
   * IN: 
   * OUT: 
   *    -1 : error
   *     0 : success (i.e., host alive)
   *     1 : timeout
   *     2 : host unreachable
   *
   */
  AX_INT32 ping(void);
  static bool IsHostAlive(AX_INT32);
  static bool IsHostUnreachable(AX_INT32);
  static bool IsRequestTimeOut(AX_INT32);

protected:


private:

  axPinger(const axPinger &);

  /**
   *
   * IN:
   * OUT:
   *
   */
  AX_INT32 sendEchoRequest(void);

  /**
   *
   * IN:
   * OUT:
   *
   */
  AX_INT32 waitForReply(void);

  /**
   *
   * IN:
   * OUT:
   *    -1 : receive error
   *     0 : success (i.e., host alive)
   *     1 : reply not from the the expected destination
   *     2 : received icmp reply too short;
   *     3 : host unreachable
   *     4 : received icmp reply other than echo-reply
   *     5 : icmp_id does not match
   *
   */
  AX_INT32 receiveReply(void);

  AX_INT32 cksum(AX_UINT16 *, AX_INT32);
  void     init(void);

  axIcmpCASocket         * m_icmpSocket;
  AX_INT32                 m_addressFamily;
  AX_INT8                * m_ipAddress;
  struct timeval           m_selectTimeout;
  size_t                   m_pingPktSize;
  size_t                   m_pingDataSize;
  AX_UINT32                m_seqNum;
  AX_UINT32                m_procId;
  struct sockaddr_storage  m_sockAddr;
  size_t                   m_sockAddrLen;
  struct sockaddr_storage  m_recvSockAddr;
  size_t                   m_recvSockAddrLen;
};

#endif // _axPinger_hpp_
