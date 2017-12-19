//
// snmpDispReturnResponsePduASI implementation
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>

#include "snmpDispReturnResponsePduASI.H"
#include "debug.H"


snmpDispReturnResponsePduASI::snmpDispReturnResponsePduASI
  (Integer32      *messageProcessingModel,   //= NULL
   Integer32      *securityModel,            //= NULL
   OctetString    *securityName,             //= NULL
   Integer32      *securityLevel,            //= NULL
   OctetString    *contextEngineID,          //= NULL
   OctetString    *contextName,              //= NULL
   PDU_Version    *pduVersion,               //= NULL
   PDU            *msgPDU,                   //= NULL
   Integer32      *maxSizeResponseScopedPDU, //= NULL
   snmpObj        *stateReference,           //= NULL
   snmpStatusInfo *statusInformation,        //= NULL
   snmpFIFOObj      *theFIFO,     //= NULL
   snmpSynchDataObj *newData,     //= NULL
   snmpMessageObj   *copyFromMsg, //= NULL
   int               newID) :     //= 0
    snmpStandardMessage
      ((TransportDomain *)NULL,  // TransportDomain
       (TransportAddress *)NULL, // TransportAddress
       messageProcessingModel,   // MessageProcessingModel
       securityModel,            // SecurityModel
       securityName,             // SecurityName
       securityLevel,            // SecurityLevel
       (OctetString *)NULL,      // SecurityEngineID
       // -----------------------------------------
       contextEngineID,          // ContextEngineID
       contextName,              // ContextName
       pduVersion,               // PDUVersion
       (PDU_Type *)NULL,         // PDUType 
       msgPDU,                   // MsgPDU
       (ScopedPDU *)NULL,        // ScopedPDU
       (bool *)NULL,             // ExpectResponse
       // -----------------------------------------
       maxSizeResponseScopedPDU, // MaxSizeResponsePDU
       (unsigned long)(0),       // SendPduHandle
       (BufferClass *)NULL,      // OutMsg
       (Integer32 *)NULL,        // OutMsgLength
       (asnDataType *)NULL,      // SecurityParameters
       // -----------------------------------------
       stateReference,           // StateReference
       (snmpObj *)NULL,          // SecurityStateReference
       (Integer32 *)NULL,        // MaxMsgSize
       statusInformation,        // StatusInformation
       (HeaderData *)NULL,       // GlobalData
       copyFromMsg, newID, newData, theFIFO)
{
  DEBUGCREATE(debugObj, "snmpDispReturnResponsePduASI");
  DEBUG9(debugObj, "Disp:snmpDispReturnResponsePduASI contructor, full params"
	 << endl);
}
snmpDispReturnResponsePduASI::snmpDispReturnResponsePduASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL
    : snmpStandardMessage( intData, newData, theFIFO) 
{
  DEBUGCREATE(debugObj, "snmpDispReturnResponsePduASI");
  DEBUG9(debugObj, "Disp:snmpDispReturnResponsePduASI contructor, with intData"
	 << endl);
}


snmpDispReturnResponsePduASI::~snmpDispReturnResponsePduASI()  {
  DEBUG9(debugObj, "Disp:~snmpDispReturnResponsePduASI() destructor" << endl);
  DEBUGDESTROY(debugObj);
}
