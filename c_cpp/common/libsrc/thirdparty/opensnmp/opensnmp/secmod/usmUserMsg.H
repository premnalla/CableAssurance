#ifndef SNMPUSMUSERMSG_H
#define SNMPUSMUSERMSG_H

#include "snmpMessageObj.H"
#include "snmpUSMData.H"
#include "snmpStatusInfo.H"
#include "opensnmpKey.H"

class usmUserMsg : public snmpMessageObj {

private:
  OctetString    *engID;
  OctetString    *userName;
  OID            *authProtocolOID;
  OID            *privProtocolOID;
  opensnmpKey    *authKey;
  opensnmpKey    *privKey;
  snmpStatusInfo *statusInformation;

  // XXX check user exist (w & w/o keys, 
  // XXX over-write/don't overwrite, permanent/impermanent
  
public:
  usmUserMsg
  (OctetString *engID,
   OctetString *userName,
   snmpFIFOObj *returnFIFO, 
   OID         *authProtocolOID  = new OID(snmpUSMData::usmNoAuthProtocolOID),
   opensnmpKey *authKey          = new opensnmpKey(""),
   OID         *privProtocolOID  = new OID(snmpUSMData::usmNoPrivProtocolOID),
   opensnmpKey *privKey          = new opensnmpKey(""),

   snmpStatusInfo   *status      = NULL,

   snmpMessageObj   *copyFromMsg = NULL,
   int               newID       = 0,
   snmpSynchDataObj *newData     = NULL );

  usmUserMsg(usmUserMsg *copyFrom);

  virtual ~usmUserMsg();
  virtual  usmUserMsg   *clone()  { return (new usmUserMsg(this)); };

  DEFINE_PTR_ACCESSOR(OctetString, engID, );
 
  DEFINE_PTR_ACCESSOR(OctetString, userName, );

  DEFINE_PTR_ACCESSOR(OID, authProtocolOID, );

  DEFINE_PTR_ACCESSOR(OID, privProtocolOID, ); 

  DEFINE_PTR_ACCESSOR(opensnmpKey, authKey, );  

  DEFINE_PTR_ACCESSOR(opensnmpKey, privKey, );  

  DEFINE_PTR_ACCESSOR(snmpStatusInfo, statusInformation, );

};

#endif /* SNMPUSMUSERMSG_H */
