//
//  snmpMPPrepareResponseMsgASI
//  This object represents the Prepare Response Message call
//  in the SNMPV3 architecture description (ietf RFC draft). It is 
//  generally used by the dispatcher to prepare a response message 
//  via the message proccesor.    
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpMPPrepareResponseMsgASI.H"


snmpMPPrepareResponseMsgASI::snmpMPPrepareResponseMsgASI
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpStandardMessage(copyFromMsg, newID, newData, theFIFO)  
{
  DEBUGCREATE(debugObj, "snmpMPPrepareResponseMsgASI");
  DEBUG9(debugObj, "snmpMPPrepareResponseMsgASI contructor, 4 params");
}


snmpMPPrepareResponseMsgASI::snmpMPPrepareResponseMsgASI
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


  // -----------------------------------------
  Integer32         *newMaxSizeRespPDU,
  BufferClass       *newOutMsg,
  Integer32         *newOutMsgLength,

  // -----------------------------------------
  snmpObj           *newStateReference,


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
		      (bool *)NULL,              // ExpectResponse
		      // -----------------------------------------
		      newMaxSizeRespPDU,         // MaxSizeResponsePDU
		      (unsigned long)(0),        // SendPduHandle
		      newOutMsg,                 // OutMsg
		      newOutMsgLength,           // OutMsgLength
		      (asnDataType *)NULL,       // SecurityParameters
		      // -----------------------------------------
		      newStateReference,         // StateReference
		      (snmpObj *)NULL,           // SecurityStateReference
		      (Integer32 *)NULL,         // MaxMsgSize
		      newStatusInformation,      // StatusInformation
		      (HeaderData *)NULL,        // GlobalData
		      (snmpMessageObj *)NULL,    // copy From Msg
		      int(0),                    // Msg ID
		      newData,     // Synch Data
		      theFIFO )    // return FIFO
{
  DEBUGCREATE(debugObj, "snmpMPPrepareResponseMsgASI");
  DEBUG9(debugObj, "snmpMPPrepareResponseMsgASI contructor, full params.");
}

snmpMPPrepareResponseMsgASI::snmpMPPrepareResponseMsgASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL 
    : snmpStandardMessage( intData, newData, theFIFO) 
{
  DEBUGCREATE(debugObj, "snmpMPPrepareResponseMsgASI");
  DEBUG9(debugObj, "snmpMPPrepareResponseMsgASI contructor, with intData.");
}


snmpMPPrepareResponseMsgASI::~snmpMPPrepareResponseMsgASI()  {
  DEBUG9(debugObj, "~snmpMPPrepareResponseMsgASI destructor");
  DEBUGDESTROY(debugObj);
}
