
#include "config.h"
#include "usmUserCloneFrom.H"

OID usmUserCloneFrom::usmUserEntryOID = OID(".1.3.6.1.6.3.15.1.2.2.1");

usmUserCloneFrom::usmUserCloneFrom(snmpDataTable *table, 
                                   snmpRow *row) 
    : OID("0.0"), snmpRowDependent(table, row)
{
  have_cloned = false;
  DEBUGCREATE(userDebug,"USM");}


usmUserCloneFrom::usmUserCloneFrom(const usmUserCloneFrom &thecopy) 
    : OID("0.0"), snmpRowDependent(thecopy)
{
  have_cloned = false;
  DEBUGCREATE(userDebug,"USM");
}

usmUserCloneFrom::~usmUserCloneFrom() 
{
  DEBUGDESTROY(userDebug);
}

// parse the full oid for the user name and eng ID values.
bool
usmUserCloneFrom::parseOID(OID         theOID, 
			   OctetString *userName, 
			   OctetString *engID) {
  DEBUG9(userDebug, "usmUserCloneFrom::parseOID");
  
  int i, size, theOID_len = theOID.size(), 
               prefix_len = this->usmUserEntryOID.size();
  // check that this is a usmUserEntryOID
  if (this->usmUserEntryOID.mincompare(theOID) != 0) return false;
  
  OID::iterator OID_it = theOID.begin();
  // skip the usmUserEntryOID prefix
  for (i = 1; i <= prefix_len; i++) OID_it++;

  // get engine ID
  size = *OID_it++;
  prefix_len += size;
  if (prefix_len > theOID_len) return false;

  engID->EraseData();
  char tempID[size];
  for (i = 1; i <= size; i++) tempID[i] = char(*OID_it++);
  engID->append(tempID, size);

  // get user name
  size = *OID_it++;
  prefix_len += size;
  if (prefix_len > theOID_len) return false;
  
  userName->EraseData();
  char tempName[size];
  for (i = 1; i <= size; i++) tempName[i] = char(*OID_it++);
  userName->append(tempName, size);

  return true;
}

usmUserCloneFrom &usmUserCloneFrom::change_value(const asnDataType &newValue) {
  //  snmpRow         *row = this->get_row(false);
  snmpDataTable *table = this->get_table(false);

  DEBUG9(this->userDebug, "usmUserCloneFrom::change_value");

  if (!have_cloned) {
    OID cloneFrom = (dynamic_cast<const OID &>(newValue));
    // XXX: use cloneFrom to perform the clone (copying stuff into
    // this get_row() from another row found in the get_table()
    // based on the parsing of the cloneFrom oid.
    have_cloned = true;

    // figure out the index values for the row to clone from
    OctetString *name = new OctetString(), *engID = new OctetString();
    if ( !this->parseOID(cloneFrom, name, engID) ) {
      throw asnBadAssignment
	("usmUserCloneFrom:change_value: failed to parse OID");
    }

    // find the row to clone from
    std::list<asnIndex *> searchKey;
    searchKey.push_back(engID);
    searchKey.push_back(name);
    snmpRow *cloneRow = table->find(searchKey);
    if ( cloneRow == NULL ) {
      throw asnBadAssignment
	("usmUserCloneFrom:change_value: could not find row to copy");
    }
    
    

  } else {
    // It's legal to double-clone something even though we don't
    // actually do anything on the second clone, so don't throw an
    // error here.
  }
  return *this;
}

