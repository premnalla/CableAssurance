#ifndef SNMPUSMDATA_H
#define SNMPUSMDATA_H

#include "snmpFIFOObj.H"
#include "snmpCrypto.H"
#include "snmpDataTable.H"
#include "KeyChange.H"
#include "debug.H"
#include "snmpCounterSet.H"
#include "snmpRowStatus.H"
#include "snmpStorageType.H"
#include "opensnmpKey.H"

class snmpUSMData :
  public snmpDataTable {

private:

  //int            usmUserSpinLock;
  snmpFIFOObj   *crFIFO;
  snmpCrypto     crypto;
  DEBUGDECLARE(USMMibDebug);
public:

  // NOTE: if you change colum enums, make sure to update
  // app/cr/snmp-db-init to use the new values.
  enum usmUserTableColumnEnum {
    usmUserEngineIDCol         = 1,
    usmUserNameCol             = 2,
    usmUserSecurityNameCol     = 3,
    usmUserCloneFromCol        = 4,
    usmUserAuthProtocolCol     = 5,
    usmUserKeyChangeCol        = 6,
    usmOwnAuthKeyChangeCol     = 7,
    usmUserPrivProtocolCol     = 8,
    usmUserPrivKeyChangeCol    = 9,
    usmUserOwnPrivKeyChangeCol = 10,
    usmUserPublicCol           = 11,
    usmUserStorageTypeCol      = 12,
    usmUserStatusCol           = 13,
    usmUserAuthKeyCol          = -1,
    usmUserPrivKeyCol          = -2
  };
  // NOTE: if you change colum enums, make sure to update
  // app/cr/snmp-db-init to use the new values.

  static const OID  usmMIBOID;
  static const OID  usmMIBObjectsOID;
  static const OID  usmUserTableOID;
  static const OID  usmUserEntryOID;
    
  static const OID  snmpAuthProtocolsOID;
  static const OID  usmNoAuthProtocolOID;
  static const OID  usmHMACMD5AuthProtocolOID;
  static const OID  usmHMACSHAAuthProtocolOID;

  static const OID  snmpPrivProtocolsOID;
  static const OID  usmNoPrivProtocolOID;
  static const OID  usmDESPrivProtocolOID;
  
  static const OID snmpUSMData::net_snmp_oid;
  static const OID snmpUSMData::usmAESPrivProtocolOID;

  // methods

  snmpUSMData();
  virtual ~snmpUSMData();

  // returns NULL if engID/user combo not found in DB
  snmpRow *searchUSMTable(OctetString* engID, OctetString* userName);
  
  virtual void addRow
  (OctetString *engID,
   OctetString *userName,
   OctetString *userSecurityName,

   OID         *authProtocolOID,
   OID         *privProtocolOID,
		      
   OctetString *authKey = new OctetString(""),
   OctetString *privKey = new OctetString(""),

   snmpStorageType *storageType = 
        new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),
   snmpRowStatus   *rowStatus   = 
        new snmpRowStatus(1)); // 1 active

  
  virtual void addRow
  (OctetString *engID,
   OctetString *userName,

   snmpStorageType *storageType = 
       new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),

   OID         *authType = new OID(usmNoAuthProtocolOID),
   opensnmpKey *authKey  = new opensnmpKey(""),
   OID         *privType = new OID(usmNoPrivProtocolOID),
   opensnmpKey *privKey  = new opensnmpKey("") );

  virtual void delRow(OctetString *engID, OctetString *userName);
  
  virtual string toString();
};


#endif /* SNMPUSMDATA_H ifndef */
