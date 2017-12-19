#ifndef MIBEMPTY_H
#define MIBEMPTY_H
// mibEmpty.H

// A empty mib object to register at the top node primarily.  Contains no data.

#include "MIBImplementation.H"
#include <VarBindList.H>

class mibEmpty : public MIBImplementation {
 public:

   mibEmpty() {}; // no
   virtual ~mibEmpty() {};

   // accessors called by the CR.
   virtual snmpProtoErr *snmp_get(MIBRegistration *reg,
                                  SearchRangeList *thevars) {
       return NULL;
   }
   
   virtual snmpProtoErr *snmp_getNext(MIBRegistration *reg, 
                                      SearchRangeList *thevars) {
       return NULL;
   }
   
   virtual snmpProtoErr *snmp_set(MIBRegistration *reg, set_actions action, 
                                  SearchRangeList *thevars = 0) {
       return NULL;
   }
};
#endif /* MIBEMPTY_H */
