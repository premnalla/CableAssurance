#ifndef SNMPUSMGENERATEREQUESTMSGASI_H
#define SNMPUSMGENERATEREQUESTMSGASI_H

#include "snmpStandardMessage.H"

class snmpUSMGenerateRequestMsgASI : public snmpStandardMessage {

public:
  snmpUSMGenerateRequestMsgASI(snmpMessageObj   *copyFromMsg = NULL,
			       int               newID       = 0,
			       snmpSynchDataObj *newData     = NULL,
			       snmpFIFOObj      *theFIFO     = NULL  );
    
  snmpUSMGenerateRequestMsgASI
  (

   Integer32         *newMessageProcessingModel,
   Integer32         *newSecurityModel,
   OctetString       *newSecurityName,
   Integer32         *newSecurityLevel,
   OctetString       *newSecurityEngineID,
   // -----------------------------------------
   
   

   
   
   ScopedPDU         *newScopedPDU,

   // -----------------------------------------
   
   BufferClass       *newOutMsg,
   Integer32         *newOutMsgLength,
   asnDataType       *newSecurityParameters,
   // -----------------------------------------

   snmpObj           *newSecurityStateReference,
   Integer32         *newMaxMsgSize,
   snmpStatusInfo    *newStatusInformation,
   HeaderData        *newGlobalData,
   snmpSynchDataObj  *newData     = NULL,
   snmpFIFOObj       *theFIFO     = NULL );
  
  snmpUSMGenerateRequestMsgASI(snmpStandardMessage_internalData *intData,
			       snmpSynchDataObj          *newData = NULL,
			       snmpFIFOObj               *theFIFO = NULL );

};

#endif /* SNMPUSMGENERATEREQUESTMSGASI_H */
