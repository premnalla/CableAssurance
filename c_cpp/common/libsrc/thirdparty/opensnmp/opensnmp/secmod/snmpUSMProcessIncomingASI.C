//
//  snmpUSMProcessIncomingASI
//  This object represents the Process Incoming ASI
//  in the SNMPV3 architecture description (ietf RFC draft). It is 
//  generally used by the dispatcher to prepare incoming data elements 
//  from a message via the message proccesor.    
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpUSMProcessIncomingASI.H"


snmpUSMProcessIncomingASI::snmpUSMProcessIncomingASI
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpStandardMessage(copyFromMsg, newID, newData, theFIFO)
{
}

snmpUSMProcessIncomingASI::snmpUSMProcessIncomingASI
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

  snmpSynchDataObj  *newData,     //= NULL
  snmpFIFOObj       *theFIFO)  :  //= NULL
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
		       (ScopedPDU *)NULL,         // ScopedPDU
		       (bool *)NULL,              // ExpectResponse
		       // -----------------------------------------
		       newMaxSizeRespPDU,         // MaxSizeResponsePDU
		       (unsigned long)(0),        // SendPduHandle
		       newOutMsg,                 // OutMsg
		       newOutMsgLength,           // OutMsgLength
		       newSecurityParameters,     // SecurityParameters
		       // -----------------------------------------
		       (snmpObj *)NULL,           // StateReference
		       newSecurityStateReference, // SecurityStateReference
		       newMaxMessageSize,         // MaxMsgSize
		       newStatusInformation,      // StatusInformation
		       (HeaderData *)NULL,        // GlobalData
		       (snmpMessageObj *)NULL,    // copy From Msg
		       int(0),                    // Msg ID
		       newData,       // Synch Data
		       theFIFO )      // return FIFO
{
  this->set_scopedPDU_string(newScopedPDU_string);
}

snmpUSMProcessIncomingASI::snmpUSMProcessIncomingASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL
    : snmpStandardMessage( intData, newData, theFIFO) 
{
}


snmpUSMProcessIncomingASI::~snmpUSMProcessIncomingASI()  
{
}
