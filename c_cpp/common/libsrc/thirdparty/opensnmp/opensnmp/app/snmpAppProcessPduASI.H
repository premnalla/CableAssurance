// snmpAppProcessPduASI.H

#ifndef SNMPAPPPROCESSPDUASI_H
#define SNMPAPPPROCESSPDUASI_H

#include "snmpStandardMessage.H"
#include "asnDataTypes.H"

class snmpAppProcessPduASI : public snmpStandardMessage {
  
 public:
   
   snmpAppProcessPduASI(Integer32   *messageProcessingModel = NULL,
			Integer32            *securityModel = NULL,
			OctetString           *securityName = NULL,
			Integer32            *securityLevel = NULL,
			OctetString        *contextEngineID = NULL,
			OctetString            *contextName = NULL,
			PDU_Version             *pduVersion = NULL,
			PDU                            *pdu = NULL,
			Integer32 *maxSizeResponseScopedPDU = NULL,
			snmpObj             *stateReference = NULL); 

   snmpAppProcessPduASI(snmpAppProcessPduASI &thecopy);

   snmpAppProcessPduASI(snmpStandardMessage_internalData *intData,
			snmpSynchDataObj          *newData = NULL,
			snmpFIFOObj               *theFIFO = NULL );

   virtual ~snmpAppProcessPduASI();

};

#endif /*  SNMPAPPPROCESSPDUASI_H  */
