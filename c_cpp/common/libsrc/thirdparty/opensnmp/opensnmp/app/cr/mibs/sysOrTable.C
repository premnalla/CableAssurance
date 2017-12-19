// sysOrTable.C: implementation for the sysOrTable mib.

#include "config.h"
#include "sysOrTable.H"
#include "debug.H"

//
// sysOrEntry
//
sysOrEntry::sysOrEntry(OID *sysORID,             //= 0
		       OctetString *sysORDescr,  //= 0 
		       TimeTicks *sysORUpTime) { //= 0
  this->sysORID = sysORID;
  this->sysORDescr = sysORDescr;
  this->sysORUpTime = sysORUpTime;
}

sysOrEntry::~sysOrEntry() {
  if (sysORID)
    delete sysORID;
  if (sysORDescr)
    delete sysORDescr;
  if (sysORUpTime)
    delete sysORUpTime;
}

//
// sysOrTableMib
//

sysOrTableMib::sysOrTableMib() {
}

sysOrTableMib::~sysOrTableMib() {
}

snmpProtoErr *
sysOrTableMib::get(MIBRegistration *reg, SearchRangeList *thevars) {
  DEBUGINIT(debugObj, "sysOrTableMib");
  DEBUG9(debugObj, "        sysOrTableMib::get\n");
  return 0;
}

snmpProtoErr *
sysOrTableMib::getNext(MIBRegistration *reg, SearchRangeList *thevars) {
  DEBUGINIT(debugObj, "sysOrTableMib");
  DEBUG9(debugObj, "        sysOrTableMib::getNext\n");
  return 0;
}

void
sysOrTableMib::add(sysOrEntry item) {
  items.push_back(item);
}
