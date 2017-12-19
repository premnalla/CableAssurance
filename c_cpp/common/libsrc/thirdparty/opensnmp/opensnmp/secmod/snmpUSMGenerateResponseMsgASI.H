#ifndef SNMPUSMGENERATERESPONSEMSGASI_H
#define SNMPUSMGENERATERESPONSEMSGASI_H

#include "snmpStandardMessage.H"

class snmpUSMGenerateResponseMsgASI : public snmpStandardMessage {

public:
  snmpUSMGenerateResponseMsgASI(snmpMessageObj   *copyFromMsg = NULL,
				int               newID       = 0,
				snmpSynchDataObj *newData     = NULL,
				snmpFIFOObj      *theFIFO     = NULL  );
    
  snmpUSMGenerateResponseMsgASI
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

  snmpUSMGenerateResponseMsgASI(snmpStandardMessage_internalData *intData,
				snmpSynchDataObj          *newData = NULL,
				snmpFIFOObj               *theFIFO = NULL );

};

#endif /* SNMPUSMGENERATERESPONSEMSGASI_H */
