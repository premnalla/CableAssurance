
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axPinger.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axPinger::axPinger()
{
  init();
}


//********************************************************************
// destructor:
//********************************************************************
axPinger::~axPinger()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axPinger::axPinger(axIcmpCASocket * icmpSocket)
{
  init();
  m_icmpSocket = icmpSocket;
}


//********************************************************************
// method:
//********************************************************************
void
axPinger::init(void)
{
  m_icmpSocket = NULL;
  m_addressFamily = AF_INET;
  m_ipAddress = NULL;
  m_selectTimeout.tv_sec = AX_MAX_ICMP_TIMEOUT/1000;
  m_selectTimeout.tv_usec = (AX_MAX_ICMP_TIMEOUT % 1000) * 1000;
  m_pingDataSize = DEFAULT_PING_DATA_SIZE;
  m_pingPktSize = m_pingDataSize + SIZE_ICMP_HDR;
  m_seqNum = 0;
  m_procId = getpid();
  memset(&m_sockAddr, 0, sizeof(m_sockAddr));
  m_sockAddrLen = sizeof(m_sockAddr);
  memset(&m_recvSockAddr, 0, sizeof(m_recvSockAddr));
  m_recvSockAddrLen = sizeof(m_recvSockAddr);
}


//********************************************************************
// method:
//********************************************************************
void
axPinger::setSelectTimeout(AX_UINT32 milliSeconds)
{
  m_selectTimeout.tv_sec = milliSeconds / 1000;
  m_selectTimeout.tv_usec = (milliSeconds % 1000) * 1000;
}


//********************************************************************
// method:
//********************************************************************
bool
axPinger::setIpAddress(AX_UINT32 family, AX_INT8 * addr)
{
  bool ret = false;

  struct addrinfo hints;
  struct addrinfo * res = NULL;

  m_addressFamily = family;
  m_ipAddress = addr;

  memset(&hints, 0, sizeof(hints));
  hints.ai_family = family;
  if (getaddrinfo(addr, NULL, &hints, &res)) {
    goto EXIT_LABEL;
  }

  m_sockAddr = *((sockaddr_storage *) res->ai_addr);
  m_sockAddrLen = res->ai_addrlen;

  ret = true;

EXIT_LABEL:

  if (res) {
    freeaddrinfo(res);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axPinger::ping(void)
{
  AX_INT32 ret = -1;
  AX_INT32 rc;
  bool continueToWait;

  rc = sendEchoRequest();
  if (rc < 0) {
    // return -1
    goto EXIT_LABEL;
  }

  continueToWait = true;

  while (continueToWait) {

    rc = waitForReply();

    if (rc < 0) {
      // return -1
      goto EXIT_LABEL;
    } else if (!rc) {
      ret = 1;
      goto EXIT_LABEL;
    }

    rc = receiveReply();

    continueToWait = false;

    switch (rc) {
      case 0: /* host reachable */
        ret = 0;
        break;

      case 3: /* host unreachable */
        ret = 2;
        break;

      case 1: /* reply not from expected destination */
        continueToWait = true;
        break;

      default:
        ret = -1;
        break;
    }
  }


#if 0
  if (rc < 0) {
    goto EXIT_LABEL;
  }

  ret = 0;
#endif

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axPinger::IsHostAlive(AX_INT32 rc)
{
  return (!rc);
}


//********************************************************************
// method:
//********************************************************************
bool
axPinger::IsHostUnreachable(AX_INT32 rc)
{
  return ((rc == 2));
}


//********************************************************************
// method:
//********************************************************************
bool
axPinger::IsRequestTimeOut(AX_INT32 rc)
{
  return ((rc == 1));
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axPinger::sendEchoRequest(void)
{
  AX_INT32 ret = -1;
  struct icmp   * icmpPkt;
  PING_DATA     * miscData;
  char            memBuffer[m_pingPktSize];

  memset(memBuffer, 0, m_pingPktSize);

  icmpPkt = (struct icmp *) memBuffer;
  icmpPkt->icmp_type = ICMP_ECHO;
  icmpPkt->icmp_code = 0;
  icmpPkt->icmp_cksum = 0;
  icmpPkt->icmp_seq = m_seqNum++;
  icmpPkt->icmp_id = m_procId;

  miscData = ( PING_DATA * )( memBuffer + SIZE_ICMP_HDR );
  gettimeofday(&miscData->pingTm, NULL);
  miscData->pingCount = icmpPkt->icmp_seq;

  icmpPkt->icmp_cksum = cksum((u_short *) icmpPkt, m_pingPktSize);

  ret = sendto(m_icmpSocket->getHandle(), memBuffer, m_pingPktSize, 0,
                       (struct sockaddr *)&m_sockAddr, m_sockAddrLen);

#if 0
  if (ret < 0 || ret != m_pingPktSize) {
    goto EXIT_LABEL;
  }
#endif

// EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axPinger::waitForReply(void)
{
  AX_INT32 ret;

  fd_set readset, writeset;
  FD_ZERO(&readset);
  FD_ZERO(&writeset);
  FD_SET(m_icmpSocket->getHandle(), &readset);

  ret = select(m_icmpSocket->getHandle() + 1, 
                         &readset, &writeset, NULL, &m_selectTimeout);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axPinger::receiveReply(void)
{
  AX_INT32 ret = -1;
  AX_INT32 recvRc;
  static const int memBuffLen = 1024;
  struct ip * ipPkt;
  struct icmp * icmpPkt;
  int hlen;
  char memBuffer[memBuffLen];

  recvRc = recvfrom(m_icmpSocket->getHandle(), memBuffer, memBuffLen, 0, 
                 (struct sockaddr *) &m_recvSockAddr, 
                 (socklen_t *) &m_recvSockAddrLen);

  if (recvRc <= 0) {
    goto EXIT_LABEL;
  }

  if (memcmp(&m_recvSockAddr, &m_sockAddr, m_recvSockAddrLen)) {
    ret = 1;
    goto EXIT_LABEL;
  }

  ipPkt = (struct ip *) memBuffer;

  hlen = ipPkt->ip_hl << 2;

  if (recvRc < (hlen + ICMP_MINLEN )) {
    ret = 2;
    goto EXIT_LABEL; /* too short */
  }

  icmpPkt = (struct icmp *) (memBuffer + hlen);

  if( icmpPkt->icmp_type == ICMP_UNREACH ) {
    ret = 3;
    goto EXIT_LABEL;
  }

  if( icmpPkt->icmp_type != ICMP_ECHOREPLY ) {
    ret = 4;
    goto EXIT_LABEL;
  }

  if (icmpPkt->icmp_id != m_procId) {
    ret = 5;
    goto EXIT_LABEL;
  }

  ret = 0;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axPinger::cksum(AX_UINT16 * p, AX_INT32 n)
{
  register AX_UINT16 answer;
  register AX_LONG sum = 0;
  AX_UINT16 odd_byte = 0;

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
}



