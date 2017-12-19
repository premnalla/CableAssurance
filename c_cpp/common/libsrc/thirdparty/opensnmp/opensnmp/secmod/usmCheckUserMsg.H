#ifndef SNMPUSMCHECKUSERMSG_H
#define SNMPUSMCHECKUSERMSG_H

#include "usmUserMsg.H"

// The class usmCheckUserMsg is used to check on the existence 
// of a user / engine ID in the USM MIB DB.

class usmCheckUserMsg : public usmUserMsg {

public:

  // By default, if only the userName / engine ID exists in the
  // message, only the existence of that user/engID combonation is
  // checked.  If a a protocolOID/passphrase is included, they will be
  // checked for a match. (Note: the authProtocolOID is neccessary for
  // the privPassPhrase.)

  usmCheckUserMsg
  (OctetString             *engID,
   OctetString             *userName,
   snmpFIFOObj             *returnFIFO, 

   OID         *authProtocolOID  = new OID(snmpUSMData::usmNoAuthProtocolOID),
   opensnmpKey         *authKey  = new opensnmpKey(""),
   OID         *privProtocolOID  = new OID(snmpUSMData::usmNoPrivProtocolOID),
   opensnmpKey         *privKey  = new opensnmpKey(""),

   snmpMessageObj   *copyFromMsg = NULL,
   int               newID       = 0,
   snmpSynchDataObj *newData     = NULL );

  usmCheckUserMsg(usmCheckUserMsg *copyFrom);

  virtual  usmCheckUserMsg   *clone()  { return (new usmCheckUserMsg(this)); };

};

#endif /* SNMPUSMCHECKUSERMSG_H */
