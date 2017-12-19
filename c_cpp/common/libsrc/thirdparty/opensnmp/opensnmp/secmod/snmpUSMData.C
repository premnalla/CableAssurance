//
// snmpUSMData
//

#include "config.h"
#include "snmpUSMData.H"
#include "snmpCRArchObj.H"
#include "snmpCRRegisterTable.H"
#include "snmpColumnCloneAllocator.H"
#include "snmpRowStatusColumnAllocator.H"
#include "snmpRowStatusCheckColsExist.H"
#include "snmpRow.H"
#include "snmpPersistentRowManager.H"

// OID constants 

const OID snmpUSMData::usmMIBOID             = OID(".1.3.6.1.6.3.15");
const OID snmpUSMData::usmMIBObjectsOID      = OID(usmMIBOID, ".1");
const OID snmpUSMData::usmUserTableOID       = OID(usmMIBObjectsOID, ".2.2");
const OID snmpUSMData::usmUserEntryOID       = OID(usmUserTableOID, ".1");

const OID snmpUSMData::snmpAuthProtocolsOID  = OID(".1.3.6.1.6.3.10.1.1");
const OID snmpUSMData::usmNoAuthProtocolOID  = OID(snmpAuthProtocolsOID, ".1");
const OID snmpUSMData::usmHMACMD5AuthProtocolOID = 
                                             OID(snmpAuthProtocolsOID, ".2");
const OID snmpUSMData::usmHMACSHAAuthProtocolOID = 
                                             OID(snmpAuthProtocolsOID, ".3");

const OID snmpUSMData::snmpPrivProtocolsOID  = OID("1.3.6.1.6.3.10.1.2");
const OID snmpUSMData::usmNoPrivProtocolOID  = OID(snmpPrivProtocolsOID, ".1");
const OID snmpUSMData::usmDESPrivProtocolOID = OID(snmpPrivProtocolsOID, ".2");

// 6/29/04: currently RFC 3826 formally assigns the following
// NOTE : not backwards compatible to previous net-snmp and opensnmp
//        values commented out below.
// const OID snmpUSMData::net_snmp_oid = OID(".1.3.6.1.4.1.8072.876.876");
// const OID snmpUSMData::usmAESPrivProtocolOID = OID(net_snmp_oid, ".128");
const OID snmpUSMData::usmAESPrivProtocolOID = OID(snmpPrivProtocolsOID, ".4");

// methods

snmpUSMData::snmpUSMData() 
  : snmpDataTable( 2 )
{
  //  usmUserSpinLock = 0;
  // load usmStatistics !?
  crFIFO          = 0;

  add_index(BER_TAG_OCTET_STRING);
  add_index(BER_TAG_OCTET_STRING);

  DEBUGCREATE(USMMibDebug,"USM");
  DEBUG9(USMMibDebug, "snmpUSMData constructor");
}

snmpUSMData::~snmpUSMData()  {
  DEBUG9(USMMibDebug, "snmpUSMData destructor");
#ifndef NODEBUGGING
  delete this->USMMibDebug;
#endif
}

// delete a user
void
snmpUSMData::delRow(OctetString *engID,
                    OctetString *userName)  {
  list<berTag> lb;
  lb.push_back(BER_TAG_OCTET_STRING);
  lb.push_back(BER_TAG_OCTET_STRING);
  snmpIndexes si(lb);

  si.set_index_number(usmUserEngineIDCol, engID);
  si.set_index_number(usmUserNameCol,     userName);
  delete_row(si);
}


// addRow, add a row to the usmUserTable, with auth !
void
snmpUSMData::addRow(OctetString     *engID,
		    OctetString     *userName,
		    snmpStorageType *storageType,
		    OID             *authType,
		    opensnmpKey     *authKey,
		    OID             *privType,
		    opensnmpKey     *privKey)  {
  
  OctetString *authKul = new OctetString();
  if (*authType != usmNoAuthProtocolOID) {
    this->crypto.generate_Kul(*authType,
			      *engID,
			      *authKey,
			      authKul);
  }

  OctetString *privKul = new OctetString();
  if (*privType != this->usmNoPrivProtocolOID) {
    this->crypto.generate_Kul(*authType,
			      *engID,
			      *privKey,
			      privKul);
  }

  this->addRow(engID,
	       userName,
	       new OctetString(*userName),
	       authType, 
	       privType,
	       authKul, privKul, storageType);
}


// addRow, add a row to the usmUserTable.
void
snmpUSMData::addRow(OctetString *engID,
		    OctetString *userName,
		    OctetString *userSecurityName,

		    OID         *authProtocolOID,
		    OID         *privProtocolOID,

		    OctetString *authKey,
		    OctetString *privKey,

		    snmpStorageType *storageType,
		    snmpRowStatus   *rowStatus) {

  OctetString *cloneFromCol     = new OctetString("");
  OctetString *ownAuthKeyChange = new OctetString("");
  OctetString *ownPrivKeyChange = new OctetString("");
  OctetString *usmUserPublic    = new OctetString("");

  // see createTable for definition of row columns!
  list<berTag> lb;
  lb.push_back(BER_TAG_OCTET_STRING);
  lb.push_back(BER_TAG_OCTET_STRING);
  snmpIndexes si(lb);

  snmpRow *sr= new snmpRow( NULL, NULL );
  
  // this needs to be changed to get the local engine ID!
  si.set_index_number(usmUserEngineIDCol, engID);
  si.set_index_number(usmUserNameCol,     userName);
  
  sr->set_column(usmUserSecurityNameCol,     *userSecurityName);
  sr->set_column(usmUserCloneFromCol,        *cloneFromCol);
  sr->set_column(usmUserAuthProtocolCol,     *authProtocolOID);
  sr->set_column(usmUserKeyChangeCol,        KeyChange(this, sr));
  sr->set_column(usmOwnAuthKeyChangeCol,     *ownAuthKeyChange);
  sr->set_column(usmUserPrivProtocolCol,     *privProtocolOID);
  sr->set_column(usmUserPrivKeyChangeCol,    KeyChange(this, sr));
  sr->set_column(usmUserOwnPrivKeyChangeCol, *ownPrivKeyChange);
  sr->set_column(usmUserPublicCol,           *usmUserPublic);
  
  sr->set_column(usmUserStorageTypeCol, *storageType);
  sr->set_column(usmUserStatusCol,      *rowStatus);
  // will this remain ??
  sr->set_column(usmUserAuthKeyCol, *authKey);
  sr->set_column(usmUserPrivKeyCol, *privKey);

  // sigh, clean up, clean up, clean up
  // XXX Note: engID & userName are passed into set_index_number 
  // as pointers, I don't know if ownership is passed, I'm assuming 
  // it is and not deleting them. 
  delete userSecurityName;
  delete cloneFromCol;
  delete authProtocolOID;
  delete ownAuthKeyChange;
  delete privProtocolOID;
  delete ownPrivKeyChange;
  delete usmUserPublic;
  delete authKey;
  delete privKey;
  delete storageType;
  delete rowStatus;

  //  this->usmUserTable->add_row(si, sr);
  //  undecided if this will stay, but for time being
  //  adding a row will overwrite any existing row...
  delete_row(si);

  this->add_row(si, sr);
}

snmpRow*
snmpUSMData::searchUSMTable(OctetString* engID, OctetString* userName) {
  // create key list to search for.
  list<asnIndex *> searchKeysList;
  searchKeysList.push_back(engID);
  searchKeysList.push_back(userName);

  return(find(searchKeysList));
}

string
snmpUSMData::toString() {
  string retString;
  map<snmpIndexes, snmpRow *>  tableData = this->data;  
  map<snmpIndexes, snmpRow *>::const_iterator table_it;
  const OctetString *OStrPtr;
  const asnIndex    *aInd;

  for(table_it = tableData.begin(); table_it != tableData.end(); table_it++) {
    if ( (aInd = table_it->first.get_index_number(int(usmUserEngineIDCol))) 
	 != NULL) {
      if ( NULL != (OStrPtr = dynamic_cast<const OctetString *>(aInd))) {
	   retString.append("Engine ID: ");
	   retString = retString + OStrPtr->toHexString();
      }
    }
    if (table_it->first.get_index_number(int(usmUserNameCol)) != NULL) {
      if ( NULL != (OStrPtr = dynamic_cast<const OctetString *>
	   (table_it->first.get_index_number(int(usmUserNameCol)))) ) {
	retString.append("\nUser Name: ");
	retString = retString + string(*OStrPtr);
      }
    }
    if (table_it->second->get_column(int(usmUserAuthProtocolCol)) != NULL) {
      retString.append("\nAuth Proto: ");
      retString = retString + string(*table_it->second->
	             get_column_OID(int(usmUserAuthProtocolCol)));
    }
    if (table_it->second->get_column(int(usmUserPrivProtocolCol)) != NULL) {
      retString.append("\nPriv Proto: ");
      retString = retString + string(*table_it->second->
	       	     get_column_OID(int(usmUserPrivProtocolCol)));
    }
    retString.append("\n");
  }
  retString.append("\n");;
  return retString;
}
