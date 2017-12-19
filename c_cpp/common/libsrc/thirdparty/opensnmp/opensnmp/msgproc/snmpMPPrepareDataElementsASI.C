//
//  snmpMPPrepareDataElementsASI
//  This object represents the Prepare Data Elements call
//  in the SNMPV3 architecture description (ietf RFC draft). It is 
//  generally used by the dispatcher to prepare incoming data elements 
//  from a message via the message proccesor.    
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpMPPrepareDataElementsASI.H"


snmpMPPrepareDataElementsASI::snmpMPPrepareDataElementsASI
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpStandardMessage(copyFromMsg, newID, newData, theFIFO)
{
}


snmpMPPrepareDataElementsASI::snmpMPPrepareDataElementsASI
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
  PDU_Type          *newPDUType, 
  PDU               *newMsgPDU,


  // -----------------------------------------
  Integer32         *newMaxSizeRespPDU,
  BufferClass       *newOutMsg,
  Integer32         *newOutMsgLength,

  // -----------------------------------------
  snmpObj           *newStateReference,
  unsigned long      newSNMPPDUHandle,

  snmpStatusInfo    *newStatusInformation,

  snmpSynchDataObj  *newData,     //= NULL
  snmpFIFOObj       *theFIFO) :   //= NULL
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
		      newPDUType,                // PDUType 
		      newMsgPDU,                 // MsgPDU
		      (ScopedPDU *)NULL,         // ScopedPDU
		      (bool *)NULL,              // ExpectResponse
		      // -----------------------------------------
		      newMaxSizeRespPDU,         // MaxSizeResponsePDU
		      newSNMPPDUHandle,          // SendPduHandle
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
		      newData,       // Synch Data
		      theFIFO )      // return FIFO
{
}

snmpMPPrepareDataElementsASI::snmpMPPrepareDataElementsASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL
    : snmpStandardMessage( intData, newData, theFIFO) 
{
}

snmpMPPrepareDataElementsASI::~snmpMPPrepareDataElementsASI() {
}
