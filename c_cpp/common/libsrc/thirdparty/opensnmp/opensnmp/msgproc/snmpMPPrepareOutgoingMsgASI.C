//
//  snmpMPPrepareOutgoingMsgASI
//  This object represents the Prepare Outgoing Message call
//  in the SNMPV3 architecture description (ietf RFC draft). It is 
//  generally used by the dispatcher to prepare an outgoing message 
//  via the message proccesor.    
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpMPPrepareOutgoingMsgASI.H"


snmpMPPrepareOutgoingMsgASI::snmpMPPrepareOutgoingMsgASI
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpStandardMessage(copyFromMsg, newID, newData, theFIFO)
{
  DEBUGCREATE(debugObj, "snmpMPPrepareOutgoingMsgASI");
  DEBUG9(debugObj, "snmpMPPrepareOutgoingMsgASI contructor, 4 params");
}


snmpMPPrepareOutgoingMsgASI::snmpMPPrepareOutgoingMsgASI
( TransportDomain   *newTransportDomain,
  TransportAddress  *newTransportAddress,
  Integer32         *newMessageProcessingModel,
  Integer32         *newSecurityModel,
  OctetString       *newSecurityName,
  Integer32         *newSecurityLevel,

  // -----------------------------------------
  OctetString       *newContextEngineID,
  OctetString       *newContextName,
  PDU_Version       *newPDUVersion,

  PDU               *newMsgPDU,

  bool              *newExpectResponse,

  unsigned long      newSendPduHandle,
  // -----------------------------------------

  BufferClass       *newOutMsg,
  Integer32         *newOutMsgLength,

  // -----------------------------------------

  snmpStatusInfo    *newStatusInformation,

  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO) :   //= NULL
  snmpStandardMessage(newTransportDomain,        // TransportDomain
		      newTransportAddress,       // TransportAddress
		      newMessageProcessingModel, // MessageProcessingModel
		      newSecurityModel,          // SecurityModel
		      newSecurityName,           // SecurityName
		      newSecurityLevel,          // SecurityLevel
		      (OctetString *)NULL,       // SecurityEngineID
		      // -----------------------------------------
		      newContextEngineID,        // ContextEngineID
		      newContextName,            // ContextName
		      newPDUVersion,             // PDUVersion
		      (PDU_Type *)NULL,          // PDUType 
		      newMsgPDU,                 // MsgPDU
		      (ScopedPDU *)NULL,         // ScopedPDU
		      newExpectResponse,         // ExpectResponse
		      // -----------------------------------------
		      (Integer32 *)NULL,         // MaxSizeResponsePDU
		      newSendPduHandle,          // SendPduHandle
		      newOutMsg,                 // OutMsg
		      newOutMsgLength,           // OutMsgLength
		      (asnDataType *)NULL,       // SecurityParameters
		      // -----------------------------------------
		      (snmpObj *)NULL,           // StateReference
		      (snmpObj *)NULL,           // SecurityStateReference
		      (Integer32 *)NULL,         // MaxMsgSize
		      newStatusInformation,      // StatusInformation
		      (HeaderData *)NULL,        // GlobalData
		      (snmpMessageObj *)NULL,    // copy From Msg
		      int(0),                    // Msg ID
		      newData,                   // Synch Data
		      theFIFO )                  // return FIFO
{
  DEBUGCREATE(debugObj, "snmpMPPrepareOutgoingMsgASI");
  DEBUG9(debugObj, "snmpMPPrepareOutgoingMsgASI contructor, full params.");
}

snmpMPPrepareOutgoingMsgASI::snmpMPPrepareOutgoingMsgASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL
    : snmpStandardMessage( intData, newData, theFIFO) 
{
  DEBUGCREATE(debugObj, "snmpMPPrepareOutgoingMsgASI");
  DEBUG9(debugObj, "snmpMPPrepareOutgoingMsgASI contructor, with intData.");
}


snmpMPPrepareOutgoingMsgASI::~snmpMPPrepareOutgoingMsgASI() {
  DEBUG9(debugObj, "~snmpMPPrepareOutgoingMsgASI destructor");
  DEBUGDESTROY(debugObj);
}
