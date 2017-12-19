#ifndef SNMPUSMPROCESSINCOMINGASI_H
#define SNMPUSMPROCESSINCOMINGASI_H

#include "snmpStandardMessage.H"
#include "snmpStatusInfo.H"
#include "HeaderData.H"

#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "ScopedPDU.H"
#include "snmpV3Message.H"


class snmpUSMProcessIncomingASI : public snmpStandardMessage {

public:
   
  snmpUSMProcessIncomingASI(snmpMessageObj   *copyFromMsg = NULL,
			    int               newID       = 0,
			    snmpSynchDataObj *newData     = NULL,
			    snmpFIFOObj      *theFIFO     = NULL  );

  snmpUSMProcessIncomingASI
  ( 

    Integer32         *newMessageProcessingModel,
    Integer32         *newSecurityModel,
    OctetString       *newSecurityName,
    Integer32         *newSecurityLevel,
    OctetString       *newSecurityEngineID,
    // -----------------------------------------





    string            *newScopedPDU_string,

    // -----------------------------------------
    Integer32         *newMaxSizeRespPDU,
    BufferClass       *newOutMsg,
    Integer32         *newOutMsgLength,
    asnDataType       *newSecurityParameters,
    // -----------------------------------------

    snmpObj           *newSecurityStateReference,
    Integer32         *newMaxMessageSize,
    snmpStatusInfo    *newStatusInformation,

    snmpSynchDataObj  *newData     = NULL,
    snmpFIFOObj       *theFIFO     = NULL );

  snmpUSMProcessIncomingASI(snmpStandardMessage_internalData *intData,
			    snmpSynchDataObj          *newData = NULL,
			    snmpFIFOObj               *theFIFO = NULL );


  virtual ~snmpUSMProcessIncomingASI();

};

#endif /* SNMPUSMPROCESSINCOMINGASI_H */
