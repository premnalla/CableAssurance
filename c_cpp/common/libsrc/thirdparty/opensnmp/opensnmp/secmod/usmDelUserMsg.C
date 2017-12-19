#include "config.h"
#include "usmDelUserMsg.H"
#include <stdio.h>

usmDelUserMsg::usmDelUserMsg
(OctetString      *engID,
 OctetString      *userName,
 snmpFIFOObj      *returnFIFO,

 snmpMessageObj   *copyFromMsg,
 int               newID,
 snmpSynchDataObj *newData)

  : usmUserMsg(engID, userName, returnFIFO,
	       new OID(snmpUSMData::usmNoAuthProtocolOID), // no Auth OID
	       new opensnmpKey(""),                        // no auth pass
               new OID(snmpUSMData::usmNoPrivProtocolOID), // no Priv OID
	       new opensnmpKey(""),                        // no priv pass
	       NULL,                                       // status Info
	       copyFromMsg, newID, newData)
{
  //  cout << "usmDelUserMsg:constructor\n";
}

usmDelUserMsg::usmDelUserMsg(usmDelUserMsg *copyFrom) 
  : usmUserMsg(copyFrom)
{
  //  cout << "usmDelUserMsg:constructor\n";
}

usmDelUserMsg::~usmDelUserMsg()
{
}
