#ifndef SNMPDISPSENDPDUASI_H
#define SNMPDISPSENDPDUASI_H

#include "asnDataTypes.H"
#include "snmpStatusInfo.H"
#include "snmpStandardMessage.H"
#include "snmpConstants.H"
using  SNMP_CONSTANTS::TransportDomain;


class snmpDispSendPduASI : public snmpStandardMessage {

private:
  DEBUGDECLARE(debugObj);
  
public:
   
  snmpDispSendPduASI();
  snmpDispSendPduASI(TransportDomain  *transportDomain        = NULL,
		     TransportAddress *transportAddress       = NULL,
		     Integer32        *messageProcessingModel = NULL,
		     Integer32        *securityModel          = NULL,
		     OctetString      *securityName           = NULL,
		     Integer32        *securityLevel          = NULL,
		     OctetString      *contextEngineID        = NULL,
		     OctetString      *contextName            = NULL,
		     PDU_Version      *pduVersion             = NULL,
		     PDU              *msgPDU                 = NULL,
		     bool             *expectResponse         = NULL,
		     snmpFIFOObj      *theFIFO     = NULL,
		     snmpSynchDataObj *newData     = NULL,
		     snmpMessageObj   *copyFromMsg = NULL,
		     int               newID       = 0);

  snmpDispSendPduASI(snmpStandardMessage_internalData *intData,
		     snmpSynchDataObj                 *newData = NULL,
		     snmpFIFOObj                      *theFIFO = NULL );

  virtual ~snmpDispSendPduASI();

};


#endif /*  SNMPDISPSENDPDUASI_H  */
