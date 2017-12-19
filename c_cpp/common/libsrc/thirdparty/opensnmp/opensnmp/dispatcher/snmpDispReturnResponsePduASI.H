#ifndef SNMPDISPRETURNRESPONSEPDUASI_H
#define SNMPDISPRETURNRESPONSEPDUASI_H

#include "asnDataTypes.H"
#include "snmpStandardMessage.H"
#include "snmpStatusInfo.H"


class snmpDispReturnResponsePduASI : public snmpStandardMessage {

private:

  DEBUGDECLARE(debugObj);

public:
   
  snmpDispReturnResponsePduASI
    (Integer32      *messageProcessingModel   = NULL,
     Integer32      *securityModel            = NULL,
     OctetString    *securityName             = NULL,
     Integer32      *securityLevel            = NULL,
     OctetString    *contextEngineID          = NULL,
     OctetString    *contextName              = NULL,
     PDU_Version    *pduVersion               = NULL,
     PDU            *msgPDU                   = NULL,
     Integer32      *maxSizeResponseScopedPDU = NULL,
     snmpObj        *stateReference           = NULL,
     snmpStatusInfo *statusInformation        = NULL,
     snmpFIFOObj      *theFIFO     = NULL,
     snmpSynchDataObj *newData     = NULL,
     snmpMessageObj   *copyFromMsg = NULL,
     int               newID       = 0);

  snmpDispReturnResponsePduASI(snmpStandardMessage_internalData *intData,
			       snmpSynchDataObj          *newData = NULL,
			       snmpFIFOObj               *theFIFO = NULL );

  virtual ~snmpDispReturnResponsePduASI();

};


#endif /*  SNMPDISPRETURNRESPONSEPDUASI_H  */
