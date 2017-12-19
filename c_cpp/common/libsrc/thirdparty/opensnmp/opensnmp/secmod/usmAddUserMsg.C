#include "config.h"
#include "usmAddUserMsg.H"
#include <stdio.h>

usmAddUserMsg::usmAddUserMsg
(OctetString             *engID,
 OctetString             *userName,
 snmpFIFOObj             *returnFIFO,
 OID                     *authProtocolOID,
 opensnmpKey             *authKey,
 OID                     *privProtocolOID,
 opensnmpKey             *privKey,
 AddUserMethod_Enum       addMethod,
 int                      storageType,

 snmpMessageObj   *copyFromMsg,
 int               newID,
 snmpSynchDataObj *newData)

  : usmUserMsg(engID, userName, returnFIFO,
	       authProtocolOID, authKey,
	       privProtocolOID, privKey,
	       (snmpStatusInfo *)NULL,  // status information
	       copyFromMsg, newID, newData)
{
  this->addMethod    = addMethod;
  this->storageType  = storageType;
  //  cout << "usmAddUserMsg:constructor\n";
}

usmAddUserMsg::usmAddUserMsg(usmAddUserMsg *copyFrom) 
  : usmUserMsg(copyFrom)
{
  this->addMethod    = copyFrom->addMethod;
  this->storageType  = copyFrom->storageType;
  //  cout << "usmAddUserMsg:constructor\n";
}
