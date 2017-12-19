#ifndef SNMPDISPARCHOBJ_H
#define SNMPDISPARCHOBJ_H

#include <set>

#include "snmpArchObj.H"
#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpDispArchRegObj.H"
#include "snmpDispStats.H"
#include "snmpDispHandleInMsg.H"
#include "snmpMPPrepareDataElementsASI.H"
#include "snmpDispNetObj.H"

#include "snmpEngine.H"
#include "debug.H"


class snmpDispArchObj : public snmpArchObj {

  friend class snmpDispArchRegObj;  // to share the fifoPtr

protected:
  DEBUGDECLARE(dispDebugObj);

  snmpDispStats    dispStats;
  snmpFIFOObj     *fifoPtr, *mpFIFO;
  snmpDispNetObj  *netObj;

  unsigned long pduHandleCounter;

  typedef struct snmpPduTypeReg {
    set<PDU_Type> *pduTypeSet;
    snmpFIFOObj *fifoPtr;
  };

  typedef struct snmpSendPduData {
    int refCount;
    int reqid;
  } snmpSendPduData;

  struct ltostr {
    bool operator()(const OctetString o1, const OctetString o2) const  {
      if (o1.size() < o2.size()) return 1;
      if (o1.size() > o2.size()) return 0;
      return ((string)o1).compare((string)o2) < 0;
    }
  };
  multimap<OctetString, snmpPduTypeReg *, ltostr> engIdPduRegistry;

  int register_engid(OctetString           *engId, 
		     set<PDU_Type>         *pduTypeSet, 
		     set<TransportAddress> *addSet,
		     snmpFIFOObj           *retFifo);
  int unregister_engid(OctetString   *engId, 
		       set<PDU_Type> *pduTypeSet);

  int send_MP_msg(snmpMessageObj *msg);
  int parse_version(char *data, int length);
  int check_internal_message(snmpMessageObj* inMsg, 
			     snmpSynchDataObj** synchData,
			     snmpMessageObj** synchMsg, snmpFIFOObj** retFifo);
  int dispatch_incoming_msg(snmpMPPrepareDataElementsASI *msg);
public:

  snmpEngine  *enginePtr;

  snmpDispArchObj(const snmpDispArchObj &ref);
  snmpDispArchObj(snmpEngine *theEngine);
  virtual ~snmpDispArchObj();


  virtual void main_loop();

  virtual void handleMsg(snmpMessageObj *message);

  virtual snmpDispArchObj *clone() const { return new snmpDispArchObj(*this); };

};


#endif /* SNMPDISPARCHOBJ_H ifndef */
