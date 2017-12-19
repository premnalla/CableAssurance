#include "config.h"
#include "snmpUSMGenerateRequestMsgASI.H"

snmpUSMGenerateRequestMsgASI::snmpUSMGenerateRequestMsgASI
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpStandardMessage(copyFromMsg, newID, newData, theFIFO)
{
}

snmpUSMGenerateRequestMsgASI::snmpUSMGenerateRequestMsgASI
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
  snmpSynchDataObj  *newData,     //= NULL
  snmpFIFOObj       *theFIFO) :   //= NULL
  snmpStandardMessage((TransportDomain *)NULL,   // TransportDomain
		      (TransportAddress *)NULL,  // TransportAddress
		      newMessageProcessingModel, // MessageProcessingModel
		      newSecurityModel,          // SecurityModel
		      newSecurityName,           // SecurityName
		      newSecurityLevel,          // SecurityLevel
		      newSecurityEngineID,       // SecurityEngineID
		      // -----------------------------------------
		      (OctetString *)NULL,       // ContextEngineID
		      (OctetString *)NULL,       // ContextName
		      (PDU_Version *)NULL,       // PDUVersion
		      (PDU_Type *)NULL,          // PDUType 
		      (PDU *)NULL,               // MsgPDU
		      newScopedPDU,              // ScopedPDU
		      (bool *)NULL,              // ExpectResponse
		      // -----------------------------------------
		      (Integer32 *)NULL,         // MaxSizeResponsePDU
		      (unsigned long)(0),        // SendPduHandle
		      newOutMsg,                 // OutMsg
		      newOutMsgLength,           // OutMsgLength
		      newSecurityParameters,     // SecurityParameters
		      // -----------------------------------------
		      (snmpObj *)NULL,           // StateReference
		      newSecurityStateReference, // SecurityStateReference
		      newMaxMsgSize,             // MaxMsgSize
		      newStatusInformation,      // StatusInformation
		      newGlobalData,             // GlobalData
		      (snmpMessageObj *)NULL,    // copy From Msg
		      int(0),                    // Msg ID
		      newData,      // Synch Data
		      theFIFO )     // return FIFO
{
}


snmpUSMGenerateRequestMsgASI::snmpUSMGenerateRequestMsgASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL 
    : snmpStandardMessage( intData, newData, theFIFO) 
{
}
