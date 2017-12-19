#ifndef MIBIMPLEMENTATION_H
#define MIBIMPLEMENTATION_H
// MIBImplementation.H

// This is an abstract class the defines the API calls that must be
// implemented by a mib implementation that will return real data.

#include <VarBindList.H>
#include "MIBRegistration.H"
#include "SearchRange.H"
#include "snmpProtoErr.H"

class MIBRegistration;
class SearchRange;

class MIBImplementation {
 private:
   // data
 public:
   MIBImplementation();
   virtual ~MIBImplementation();
   virtual snmpProtoErr *snmp_get(MIBRegistration *reg,
                                  SearchRangeList *thevars) = 0;
   virtual snmpProtoErr *snmp_getNext(MIBRegistration *reg,
                                      SearchRangeList *thevars) = 0;
   virtual snmpProtoErr *snmp_set(MIBRegistration *reg, set_actions action, 
                                  SearchRangeList *thevars = 0) = 0;
};
#endif /* MIBIMPLEMENTATION_H */
