// snmpAppProcessResponsePduASI.H

#ifndef SNMPAPPPROCESSRESPONSEPDUASI_H
#define SNMPAPPPROCESSRESPONSEPDUASI_H

#include "snmpStandardMessage.H"
#include "asnDataTypes.H"
#include "snmpStatusInfo.H"

class snmpAppProcessResponsePduASI : public snmpStandardMessage {
  
 public:
   
   snmpAppProcessResponsePduASI(Integer32 *messageProcessingModel = NULL,
				Integer32          *securityModel = NULL,
				OctetString         *securityName = NULL,
				Integer32          *securityLevel = NULL,
				OctetString      *contextEngineID = NULL,
				OctetString          *contextName = NULL,
				PDU_Version           *pduVersion = NULL,
				PDU                          *pdu = NULL,
				snmpStatusInfo *statusInformation = NULL,
				unsigned long       sendPduHandle = 0);

   snmpAppProcessResponsePduASI(snmpAppProcessResponsePduASI &thecopy);

   snmpAppProcessResponsePduASI(snmpStandardMessage_internalData *intData,
				snmpSynchDataObj          *newData = NULL,
				snmpFIFOObj               *theFIFO = NULL );


   virtual ~snmpAppProcessResponsePduASI();

};

#endif /*  SNMPAPPPROCESSRESPONSEPDUASI_H  */
