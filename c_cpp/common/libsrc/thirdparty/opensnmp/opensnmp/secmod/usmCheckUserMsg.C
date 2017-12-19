#include "config.h"
#include "usmCheckUserMsg.H"
#include <stdio.h>

usmCheckUserMsg::usmCheckUserMsg
(OctetString             *engID,
 OctetString             *userName,
 snmpFIFOObj             *returnFIFO,
 OID                     *authProtocolOID,
 opensnmpKey             *authKey,
 OID                     *privProtocolOID,
 opensnmpKey             *privKey,

 snmpMessageObj   *copyFromMsg,
 int               newID,
 snmpSynchDataObj *newData)

  : usmUserMsg(engID, userName, returnFIFO,
	       authProtocolOID, authKey, 
	       privProtocolOID, privKey,
	       NULL,  // status Information
	       copyFromMsg, newID, newData)
{
  //  cout << "usmCheckUserMsg:constructor\n";
}

usmCheckUserMsg::usmCheckUserMsg(usmCheckUserMsg *copyFrom) 
  : usmUserMsg(copyFrom)
{
  //  cout << "usmCheckUserMsg:constructor\n";
}
