// snmpAppProcessPduASI.C

#include "config.h"
#include "snmpAppProcessPduASI.H"

snmpAppProcessPduASI::snmpAppProcessPduASI
  (Integer32   *messageProcessingModel,
   Integer32            *securityModel,
   OctetString           *securityName,
   Integer32            *securityLevel,
   OctetString        *contextEngineID,
   OctetString            *contextName,
   PDU_Version             *pduVersion,
   PDU                            *pdu,
   Integer32 *maxSizeResponseScopedPDU,
   snmpObj             *stateReference ) :
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
		      maxSizeResponseScopedPDU,  // MaxSizeResponsePDU
		      (unsigned long)(0),        // SendPduHandle
		      (BufferClass *)NULL,       // OutMsg
		      (Integer32 *)NULL,         // OutMsgLength
		      (asnDataType *)NULL,       // SecurityParameters
		      // -----------------------------------------
		      stateReference,            // StateReference
		      (snmpObj *)NULL,           // SecurityStateReference
		      (Integer32 *)NULL,         // MaxMsgSize
		      (snmpStatusInfo *)NULL,    // StatusInformation
		      (HeaderData *)NULL,        // GlobalData
		      (snmpMessageObj *)NULL,    // copy From Msg
		      int(0),                    // Msg ID
		      (snmpSynchDataObj *)NULL,  // Synch Data
		      (snmpFIFOObj *)NULL )      // return FIFO
{
}

snmpAppProcessPduASI::snmpAppProcessPduASI(snmpAppProcessPduASI &thecopy)
  : snmpStandardMessage(thecopy) {
}

snmpAppProcessPduASI::snmpAppProcessPduASI
   (snmpStandardMessage_internalData *intData,
    snmpSynchDataObj                 *newData,
    snmpFIFOObj                      *theFIFO ) :
    snmpStandardMessage(intData, newData, theFIFO) {
}


snmpAppProcessPduASI::~snmpAppProcessPduASI() {
}
