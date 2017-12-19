#ifndef SNMPCRARCHOBJ_H
#define SNMPCRARCHOBJ_H

#include "snmpArchObj.H"
#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "mibreg/MIBRegTree.H"
#include "snmpAppProcessPduASI.H"
#include "snmpEngine.H"
#include "snmpDispReturnResponsePduASI.H"
#include "snmpVACMIsAccessAllowedASI.H"

class snmpCRArchObj : public snmpArchObj {

 private:
   enum crState { NOTHING_SPECIAL, WAITING_ACM };
   set<TransportAddress> *listenTAddresses;
   DEBUGDECLARE(debugObj);

   class crRef {
    public:
      int reqID;
      unsigned int non_repeaters;
      unsigned int max_repetitions;
      unsigned int getbulknum;
      PDU *pdu;
      VarBindList *varbinds, *varbinds_copy;
//      crState state;
      snmpAppProcessPduASI *origMessage;
      VACMError *acmResultCode;
      unsigned int bulkpassnum;

      crRef() {
          reqID = non_repeaters = max_repetitions = getbulknum = 0;
          pdu = NULL;
          varbinds = NULL;
          varbinds_copy = NULL;
          origMessage = NULL;
          acmResultCode = NULL;
      }
   };

   class stateRef : public snmpObj {
      public:
      int reqID;
      VarBind *vb;

      stateRef(const stateRef&ref) {
        reqID = ref.reqID;
        vb = ref.vb;
      }
      stateRef(int reqID, VarBind *vb) {
          this->reqID = reqID;
          this->vb = vb;
      }

      ~stateRef() {
          // don't delete anything
      }
     virtual stateRef *clone() const { return new stateRef(*this); }
   };

   friend class snmpCRArchRegObj;  // to share the fifoPtr
   MIBRegTree *mibtop;
   snmpEngine *ourEngine;
   multimap<int, VarBind *> acmStash;
   map<int, crRef *> refStash;
   map<int, VarBindList *> processThese;

   crRef *extractReference(PDU *pdu);

 protected:

   snmpFIFOObj  *dispFIFO, *fifoPtr, *vacmFIFO;

 public:

   snmpCRArchObj(snmpEngine *ourEngine, snmpFIFOObj *fifoPtr, 
		 set<TransportAddress> *listenTAddresses = NULL);
   virtual ~snmpCRArchObj();

  // ---  New Methods ---

   virtual void main_loop();

   virtual snmpDispReturnResponsePduASI *handleMsg(snmpAppProcessPduASI *message);
   virtual VarBind *assignVarBinds(PDU *pdu, map<OID, MIBRegTree *> *saved,
                                   VarBindList *vbl = NULL);
   virtual snmpProtoErr *processGetNextPDUs(PDU *pdu, VarBindList *vbl = NULL);
   virtual snmpProtoErr *processGetPDUs(PDU *pdu, VarBindList *vbl = NULL);
   virtual snmpProtoErr *processSetPDUs(PDU *pdu);
   virtual void sendIsAccessAllowed(crRef *ref, VarBindList *vbl);
   virtual void sendIsAccessAllowed(crRef *ref, VarBind *vb);
   virtual snmpDispReturnResponsePduASI *sendResponse(snmpProtoErr *error, 
                                                      crRef *ref);
   virtual snmpDispReturnResponsePduASI *handleACMResults(snmpVACMIsAccessAllowedASI *acmmsg);
   virtual void registerUs();
};


#endif /* SNMPCRARCHOBJ_H ifndef */
