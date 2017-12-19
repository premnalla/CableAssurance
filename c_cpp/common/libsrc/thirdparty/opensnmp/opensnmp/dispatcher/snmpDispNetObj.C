//
// snmpDispNetObj
//

#include <config.h>

#include <stdio.h>
#include <iostream>
#include <string>
#include <typeinfo>
#include <iostream>

#include <sys/time.h>
#include <sys/types.h>
#include <errno.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include "snmpDispNetObj.H"
#include "asnDataTypes.H"
#include "snmpFIFOObj.H"
#include "snmpRegObj.H"
#include "snmpMessageObj.H"
#include "snmpDispHandleInMsg.H" 
#include "snmpDispArchObj.H"
#include "BufferClass.H"


snmpDispNetObj::snmpDispNetObj(const snmpDispNetObj &ref)
{
  cerr << "ERROR:snmpDispNetObj copy ctor not implemented!" ;
  throw;
}

snmpDispNetObj::snmpDispNetObj(snmpEngine *theEngine)  {
  snmpRegObj  theReg;
  DEBUGCREATE(netDebugObj, "NET");
  DEBUG9(netDebugObj, "constructor");

  this->enginePtr    = theEngine;
  this->exit_net_object = false;
  this->Max_sd    = 0;
  this->send_sd   = 0;

  // set send socket to random port. UDP
  if (this->init_socket(&(this->send_sd), 0)) {
    this->socket_list.push_back(this->send_sd);
    this->Max_sd = this->send_sd + 1;
  }
  else {
    DEBUG0(netDebugObj, "failed to open send socket, will try again later.");
    this->send_sd = 0;
  }
  
} // snmpDispNetObj


snmpDispNetObj::~snmpDispNetObj()  {
  DEBUG9(netDebugObj, "destructor");
  DEBUGDESTROY(netDebugObj);
}


void
snmpDispNetObj::calc_fdset_data (fd_set *fdset)
{
  DEBUGN(25, netDebugObj, "calc_fdset_data");

  // check parameters
  if (!fdset) return;
  FD_ZERO(fdset);

  list<int>::iterator sd_it;
  
  for(sd_it = socket_list.begin(); sd_it != socket_list.end(); sd_it++) {
    DEBUGN(25, netDebugObj, ": FD_SET'ing sd '" << *sd_it << "'");  
    FD_SET(*sd_it, fdset);
  }

}


int
snmpDispNetObj::send_net_msg(TransportDomain*  transportDomain,
			     TransportAddress* transportAddress, 
			     u_char*           data,
			     u_long            len)
{
  struct sockaddr_in addr;
  string addrData = (string)(*transportAddress);
  DEBUGCREATE_L(netDebugObj, "send_net_msg");
  DEBUG9_L(netDebugObj, " ");

  addrData.copy((char*)&(addr.sin_addr), sizeof(addr.sin_addr));
  addrData.copy((char*)&(addr.sin_port), sizeof(addr.sin_port), 
		sizeof(addr.sin_addr));

  addr.sin_family = AF_INET;

  // figure out what socket to send on (if the transportAddress has it)
  int sd = transportAddress->get_socket();
  if (sd == 0) {
    if (this->send_sd != 0) {
      sd = this->send_sd;
      DEBUG9_L(netDebugObj, " TransportAddress does not contain the " <<
	       "socket to send from, using default send socket '" << 
	       this->send_sd << "'");
    }
    else {
      DEBUG0_L(netDebugObj, "ERROR: no socket to send from? huh," <<
	     " this should not happen");
      return 0;
    }
  }

  DEBUG9_L(netDebugObj, "sending:" << len << " bytes to "  \
       << inet_ntoa(addr.sin_addr) << ":" << ntohs(addr.sin_port) \
       << ":" << transportAddress->size() );
  int res;
  /* send the message */
  if ( (res = sendto(sd, data, len, 0, 
		     (struct sockaddr*)&addr, sizeof(addr))) < 0 ) {
    DEBUG0_L(netDebugObj, "ERROR '" << errno << "' from sento() using sd '" <<
	     sd << "'");
    return 0;
  }
  DEBUG9_L(netDebugObj, "sendto returned:" << res );
  
  return 1;
} // send_net_msg


bool
snmpDispNetObj::listen_tAddress(TransportAddress tAdd) {
  int net_address = 0, portnum_length = sizeof(unsigned short), newsd;
  unsigned short portnum = 0;
  DEBUG9(netDebugObj, "listen_tAddress");

  tAdd.get_string((char *)&net_address, (unsigned int)sizeof(unsigned int), 
		  false);
  tAdd.get_string((char *)&portnum, (unsigned int)sizeof(unsigned int), 
		  (unsigned int *)&portnum_length, false);
  
  struct in_addr add_struct;
  add_struct.s_addr = (unsigned long int)net_address;

  // Note: The TransportAddress class stores the address in network
  // order, init_socket expects the 'int port' to be in host order,
  // sigh.
  unsigned short host_portnum = ntohs(portnum);

  if ( this->init_socket(&newsd, int(host_portnum), net_address) ) {
    this->socket_list.push_back(newsd);
    if (this->Max_sd <= newsd) this->Max_sd = newsd + 1;
    DEBUGN(15, netDebugObj, "inited address '" << net_address << ":"
	   << host_portnum << "'");
    DEBUG9(netDebugObj, "listening on '" << inet_ntoa(add_struct) << ":" <<
	   host_portnum << "'");
    return true;
  }
  else {
    DEBUG0(netDebugObj, "Error trying to listen on '" << 
	   inet_ntoa(add_struct) << " : " << host_portnum << "'");
  }
  return false;
} // listen_tAddress


int
snmpDispNetObj::init_socket(int *in_sd, int port, int net_add)
{
  int sd;
  struct sockaddr_in local_addr;
  unsigned short local_port = port; 
  DEBUG9(netDebugObj, "init_socket: creating socket on port: " << local_port );

  sd = socket(AF_INET, SOCK_DGRAM, 0);
  if (sd < 0) {
    DEBUG9(netDebugObj, "init_socket: Error creating socket on port: " << 
	   local_port );
    return 0;
  }

#ifdef SO_BSDCOMPAT
  /* Patch for Linux.  Without this, UDP packets that fail get_ an ICMP
     * response.  Linux turns the failed ICMP response into an error message
     * and return value, unlike all other OS's.
     */
  {
    int one=1;
    setsockopt(sd, SOL_SOCKET, SO_BSDCOMPAT, &one, sizeof(one));
  }
#endif /* SO_BSDCOMPAT */

  memset(&local_addr, '\0', sizeof(local_addr));
  //  local_addr.sin_addr.s_addr = INADDR_ANY;
  local_addr.sin_addr.s_addr = net_add;
  local_addr.sin_family = AF_INET;
  local_addr.sin_port = htons(local_port);
  if (bind(sd, (struct sockaddr *)&local_addr, sizeof(local_addr)) != 0){
    DEBUG9(netDebugObj, "init_socket: ERROR:binding socket");
    return 0;
  }
  DEBUG9(netDebugObj, "jinit_socket: ust bound socket to port = " << 
	 ntohs(local_addr.sin_port) << ", sd = " << sd );
  *in_sd = sd;
  return 1;
} // init_socket


void *
snmpDispNetObj::start_net_recv_loop(snmpDispNetObj* netObj)
{
  int *status = new int(0); // does this need to be static?
                            // Nope, but it does need to exist after the 
                            // thread exits....
  DEBUGCREATE_L(startNetDebug, "start_net_recv_loop");
  DEBUG9_L(startNetDebug, "starting: ");
  try {
    netObj->net_recv_loop();
  }
  catch (exception &except) {
    DEBUG0_L(startNetDebug, " Exception, '" << typeid(except).name() 
	   << "', caught in net_recv_loop, Failing!");
  }
  catch(...) {
    DEBUG0_L(startNetDebug, "Unknown Exception caught in net_recv_loop, Failing!");
  }
  DEBUG9_L(startNetDebug, "exiting: ");

  pthread_exit((void *) status);
  return NULL;
}


int
snmpDispNetObj::net_recv_loop(void)
{
  int count, loc_buff_size = 2048;
  fd_set fdset;
  int sd;
  struct timeval timeout, *tvp;
  int loop_num=0;
  struct sockaddr_in	from;
  struct sockaddr_in	to;
  int length;
  unsigned int fromlength;
  unsigned int tolength;
  char *buf;
  snmpMessageObj *dispHandleInMsg;
  DEBUG9(netDebugObj,  "net_recv_loop");

  while (1) {
    loop_num++;
    tvp = &timeout;
    tvp->tv_sec = 0;
    tvp->tv_usec = 100;
    
    this->calc_fdset_data(&fdset);
    DEBUGN(25, netDebugObj, "net_recv_loop: doing loop:" << loop_num 
	   <<  ": max sd = " << this->Max_sd);
    
    count = select(this->Max_sd, &fdset, 0, 0, tvp);    
    //    count = select(this->Max_sd, &fdset, 0, 0, 0);
    DEBUGN(25, netDebugObj, "net_recv_loop: select returned " << count);
    if (this->exit_net_object) {
      // set by snmpDispArchObj when it is time to exit.
      netExitMutex.lock("NET: exiting");
      netExitMutex.unlock("NET: exiting");
      netExitCondition.signal("NET: exiting");
      return 1;
    }
    else if (count < 0) {
      DEBUG9(netDebugObj, "net_recv_loop: ERROR:select returned < 0");
      return 0;
    } 
    else if (count == 0) {
      DEBUGN(25, netDebugObj, "net_recv_loop: timed out: loop: " << loop_num);
    } 
    else {
      while (count > 0) {
	list<int>::iterator sd_it;
	sd = 0;
	for (sd_it = this->socket_list.begin(); 
	     sd_it != this->socket_list.end(); sd_it++) {
	  if (FD_ISSET(*sd_it, &fdset)) {
	    sd = *sd_it;
	  }
	}

	if (sd == 0) {
	  DEBUG0(netDebugObj, "net_recv_loop: ERROR:unknown select socket");
	  break;
	}

	buf = (char *)calloc(sizeof(char), loc_buff_size);
	FD_CLR(sd,&fdset);
	fromlength = sizeof(struct sockaddr);
	length = 
	  recvfrom(sd, buf, loc_buff_size, 0, 
		   (struct sockaddr *)&from, &fromlength);
	
        // this should be cached instead
	tolength = sizeof(struct sockaddr);
	int res = getsockname(sd, (struct sockaddr *)&to,&tolength);
	
	  DEBUG9(netDebugObj, "net_recv_loop: rcvd:result =" \
                 << res << ":len = " \
                 << length << " bytes from " << inet_ntoa(from.sin_addr) \
                 << ":" << ntohs(from.sin_port) << ":" << fromlength \
                 << " on " << inet_ntoa(to.sin_addr) << ":" \
                 << ntohs(to.sin_port) << ":" << tolength );

	TransportAddress *fromTransAddr = new TransportAddress
	        ( short(ntohs(from.sin_port)),
		  string(inet_ntoa(from.sin_addr)) );

	fromTransAddr->set_socket(sd);

	TransportAddress *toTransAddr = new TransportAddress
	        ( short(ntohs(to.sin_port)),
		  string(inet_ntoa(to.sin_addr)) );

        DEBUGN(25,netDebugObj,"net_recv_loop: TEST: " << 
	       inet_ntoa(to.sin_addr) << ":" << ntohs(to.sin_port) );
	
	BufferClass *outMessage = new BufferClass(buf, loc_buff_size, 
						  buf, length);

	dispHandleInMsg = 
	  new snmpDispHandleInMsg
	    (new TransportDomain(SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP),
	     fromTransAddr,
	     toTransAddr,
	     outMessage,
	     new Integer32(length));

	if (this->dispFIFO != NULL) {
	  this->dispFIFO->push(dispHandleInMsg);
	} else {
	  DEBUG0(netDebugObj, "net_recv_loop: ERROR: DISP fifoPtr is NULL");
	}
	
	count--;
      } // while count > 0 
    } // else of count values
  } // while (1)

} // net_recv_loop
