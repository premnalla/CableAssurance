#ifndef SNMPDISPNETOBJ_H
#define SNMPDISPNETOBJ_H

#include <set>
#include <netinet/in.h>

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpDispStats.H"
#include "snmpDispHandleInMsg.H"
#include "snmpArchObj.H"
#include "snmpMutexObj.H"

#include "snmpEngine.H"
#include "debug.H"


class snmpDispNetObj : public snmpArchObj {

  friend class snmpDispArchRegObj;  // to share the fifoPtr
  friend class snmpDispArchObj;     // share socket descriptors

protected:

  DEBUGDECLARE(netDebugObj);

  snmpFIFOObj  *dispFIFO;

  int  send_sd;
  int  Max_sd;
  // NOTE: exit_net_object is set in the constructor and is ONLY
  //    written to otherwise by the snmpDispArchObj that started 
  //    this object.
  bool exit_net_object;
  snmpMutexObj     netExitMutex;
  snmpConditionObj netExitCondition;

  list<int> socket_list;

  // static procedures
  static void* start_net_recv_loop(snmpDispNetObj*);

  // non-static procedurs
  bool listen_tAddress(TransportAddress tAdd);
  int init_socket(int *in_sd, int port, int net_add = INADDR_ANY);

  int net_recv_loop(void);
  int send_net_msg(TransportDomain*  transportDomain,
		   TransportAddress* transportAddress, 
		   u_char*           data, 
		   u_long            len);
  void calc_fdset_data (fd_set *fdset);

public:

  snmpEngine  *enginePtr;

  snmpDispNetObj(const snmpDispNetObj &ref);
  snmpDispNetObj(snmpEngine *theEngine);
  virtual ~snmpDispNetObj();

  virtual snmpDispNetObj *clone() const { return new snmpDispNetObj(*this); };

};


#endif /* SNMPDISPNETOBJ_H ifndef */
