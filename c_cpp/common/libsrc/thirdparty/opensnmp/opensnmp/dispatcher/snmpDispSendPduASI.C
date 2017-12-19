//
// snmpDispSendPduASI
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>

#include "snmpDispSendPduASI.H"
#include "debug.H"

snmpDispSendPduASI::snmpDispSendPduASI()  
{
  DEBUGCREATE(debugObj, "snmpDispSendPduASI");
  DEBUG9(debugObj, "snmpDispSendPduASI contructor, no params");
}

snmpDispSendPduASI::snmpDispSendPduASI
(TransportDomain  *transportDomain,        //= NULL
 TransportAddress *transportAddress,       //= NULL
 Integer32        *messageProcessingModel, //= NULL
 Integer32        *securityModel,          //= NULL
 OctetString      *securityName,           //= NULL
 Integer32        *securityLevel,          //= NULL
 OctetString      *contextEngineID,        //= NULL
 OctetString      *contextName,            //= NULL
 PDU_Version      *pduVersion,             //= NULL
 PDU              *msgPDU,                 //= NULL
 bool             *expectResponse,         //= NULL
 snmpFIFOObj      *theFIFO,     //= NULL
 snmpSynchDataObj *newData,     //= NULL
 snmpMessageObj   *copyFromMsg, //= NULL
 int               newID) :     //= 0
   snmpStandardMessage(transportDomain,          // TransportDomain
		       transportAddress,         // TransportAddress
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
		       msgPDU,                   // MsgPDUu
		       (ScopedPDU *)NULL,        // ScopedPDU
		       expectResponse,           // ExpectResponse
		       // -----------------------------------------
		       (Integer32 *)NULL,        // MaxSizeResponsePDU
		       (unsigned long)(0),       // SendPduHandle
		       (BufferClass *)NULL,      // OutMsg
		       (Integer32 *)NULL,        // OutMsgLength
		       (asnDataType *)NULL,      // SecurityParameters
		       // -----------------------------------------
		       (snmpObj *)NULL,          // StateReference
		       (snmpObj *)NULL,          // SecurityStateReference
		       (Integer32 *)NULL,        // MaxMsgSize
		       (snmpStatusInfo *)NULL,   // StatusInformation
		       (HeaderData *)NULL,       // GlobalData
		       copyFromMsg, newID, newData, theFIFO)
{
  DEBUGCREATE(debugObj, "snmpDispSendPduASI");
  DEBUG9(debugObj, "Disp:snmpDispSendPduASI contructor, full params" << endl);
}

snmpDispSendPduASI::snmpDispSendPduASI
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, //= NULL
   snmpFIFOObj                      *theFIFO) //= NULL
    : snmpStandardMessage( intData, newData, theFIFO) 
{
  DEBUGCREATE(debugObj, "snmpDispSendPduASI");
  DEBUG9(debugObj, "Disp:snmpDispSendPduASI contructor, with intDat" << endl);
}

snmpDispSendPduASI::~snmpDispSendPduASI()  
{
  DEBUG9(debugObj, "Disp:~snmpDispSendPduASI destructor" << endl);
  DEBUGDESTROY(debugObj);
}

