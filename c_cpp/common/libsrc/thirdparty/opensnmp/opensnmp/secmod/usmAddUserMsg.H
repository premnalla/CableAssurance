#ifndef SNMPUSMADDUSERMSG_H
#define SNMPUSMADDUSERMSG_H

#include "usmUserMsg.H"

// The class usmAddUserMsg is used to add a user to the USM MIB DB.
// By default, a user is created that only exists for this session
// and will not over write an existing user / engine ID.

class usmAddUserMsg : public usmUserMsg {

public:
  enum AddUserMethod_Enum {
    OVER_WRITE_USER,
    DO_NOT_OVER_WRITE_USER
  };

private:
  AddUserMethod_Enum  addMethod;

  // uses SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE,
  //      SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE,
  //      SNMP_CONSTANTS::SNMP_STORAGE_PERMANENT,
  //      SNMP_CONSTANTS::SNMP_STORAGE_READONLY
  int  storageType;

public:

  // By default, a user is created that only exists for this session
  // and will not over write an existing user / engine ID.

  // By default, it uses the pass phrase -> key generation method
  // suggest in STD 62.  If the authKey or privKey parameters are
  // non-null and >0 length, these keys will be used instead.

  usmAddUserMsg
  (OctetString             *engID,
   OctetString             *userName,
   snmpFIFOObj             *returnFIFO, 
   OID         *authProtocolOID  = new OID(snmpUSMData::usmNoAuthProtocolOID),
   opensnmpKey *authKey          = new opensnmpKey(""),
   OID         *privProtocolOID  = new OID(snmpUSMData::usmNoPrivProtocolOID),
   opensnmpKey *privKey          = new opensnmpKey(""),
   AddUserMethod_Enum  addMethod   = DO_NOT_OVER_WRITE_USER,
   int                 storageType = SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE,

   snmpMessageObj   *copyFromMsg = NULL,
   int               newID       = 0,
   snmpSynchDataObj *newData     = NULL );

  usmAddUserMsg(usmAddUserMsg *copyFrom);

  virtual  usmAddUserMsg   *clone()  { return (new usmAddUserMsg(this)); };

  DEFINE_ACCESSOR(AddUserMethod_Enum, addMethod, );

  DEFINE_ACCESSOR(int, storageType, );

};

#endif /* SNMPUSMADDUSERMSG_H */
