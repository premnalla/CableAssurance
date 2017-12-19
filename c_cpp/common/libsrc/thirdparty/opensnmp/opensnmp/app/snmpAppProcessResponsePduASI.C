// snmpAppProcessResponsePduASI.C

#include "config.h"
#include "snmpAppProcessResponsePduASI.H"


snmpAppProcessResponsePduASI::snmpAppProcessResponsePduASI
  (Integer32 *messageProcessingModel,
   Integer32          *securityModel,
   OctetString         *securityName,
   Integer32          *securityLevel,
   OctetString      *contextEngineID,
   OctetString          *contextName,
   PDU_Version           *pduVersion,
   PDU                          *pdu,
   snmpStatusInfo *statusInformation,
   unsigned long        sendPduHandle) :
  snmpStandardMessage((TransportDomain *)NULL,   // TransportDomain
		      (TransportAddress *)NULL,  // TransportAddress
		      messageProcessingModel,    // MessageProcessingModel
		      securityModel,             // SecurityModel
		      securityName,              // SecurityName
		      securityLevel,             // SecurityLevel
		      (OctetString *)NULL,       // SecurityEngineID
		      // -----------------------------------------
		      contextEngineID,           // ContextEngineID
		      contextName,               // ContextName
		      pduVersion,                // PDUVersion
		      (PDU_Type *)NULL,          // PDUType 
		      pdu,                       // MsgPDU
		      (ScopedPDU *)NULL,         // ScopedPDU
		      (bool *)NULL,              // ExpectResponse
		      // -----------------------------------------
		      (Integer32 *)NULL,         // MaxSizeResponsePDU
		      sendPduHandle,             // SendPduHandle
		      (BufferClass *)NULL,       // OutMsg
		      (Integer32 *)NULL,         // OutMsgLength
		      (asnDataType *)NULL,       // SecurityParameters
		      // -----------------------------------------
		      (snmpObj *)NULL,           // StateReference
		      (snmpObj *)NULL,           // SecurityStateReference
		      (Integer32 *)NULL,         // MaxMsgSize
		      statusInformation,         // StatusInformation
		      (HeaderData *)NULL,        // GlobalData
		      (snmpMessageObj *)NULL,    // copy From Msg
		      int(0),                    // Msg ID
		      (snmpSynchDataObj *)NULL,  // Synch Data
		      (snmpFIFOObj *)NULL )      // return FIFO
{
}

snmpAppProcessResponsePduASI::snmpAppProcessResponsePduASI
  (snmpAppProcessResponsePduASI &thecopy) 
  : snmpStandardMessage(thecopy)
{
}


snmpAppProcessResponsePduASI::snmpAppProcessResponsePduASI
   (snmpStandardMessage_internalData *intData,
    snmpSynchDataObj                 *newData,
    snmpFIFOObj                      *theFIFO ) :
    snmpStandardMessage(intData, newData, theFIFO) {
}


snmpAppProcessResponsePduASI::~snmpAppProcessResponsePduASI() {
}
