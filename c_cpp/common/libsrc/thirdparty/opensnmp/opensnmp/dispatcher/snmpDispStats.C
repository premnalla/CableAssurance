// snmpDispStats.C
//

#include "config.h"
#include "snmpDispStats.H"
#include "debug.H"

snmpDispStats::snmpDispStats(const snmpDispStats &ref)
{
  snmpInPkts = ref.snmpInPkts;
  snmpInBadVersions = ref.snmpInBadVersions;
  snmpUnknownPDUHandlers = ref.snmpUnknownPDUHandlers;
}

snmpDispStats::snmpDispStats() {
  DEBUGCREATE_L(debugObj, "snmpDispStats");
  DEBUG9_L(debugObj, string("Disp:snmpDispStats constructor"));
  init();
}

snmpDispStats::~snmpDispStats() {
  DEBUGCREATE_L(debugObj, "snmpDispStats");
  DEBUG9_L(debugObj, string("Disp:snmpDispStats destructor"));
}

void
snmpDispStats::init() {
  snmpInPkts = 0;
  snmpInBadVersions = 0;
  snmpUnknownPDUHandlers = 0;
}

Integer32 
snmpDispStats::get_snmpInPkts() {
  return this->snmpInPkts;
}

Integer32 
snmpDispStats::incr_snmpInPkts() {
  return ++(this->snmpInPkts);
}
  
Integer32 
snmpDispStats::get_snmpInBadVersions() {
  return this->snmpInBadVersions;
}
  
Integer32 
snmpDispStats::incr_snmpInBadVersions() {
  return ++(this->snmpInBadVersions);
}
  
Integer32 
snmpDispStats::get_snmpUnknownPDUHandlers() {
  return this->snmpUnknownPDUHandlers;
}

Integer32 
snmpDispStats::incr_snmpUnknownPDUHandlers() {
  return ++(this->snmpUnknownPDUHandlers);
}
