#ifndef SCALARSET_H
#define SCALARSET_H
// scalarSet.H

#include "MIBImplementation.H"
#include "snmpScalarData.H"
#include <VarBindList.H>

class scalarSet : public MIBImplementation {
 private:
   DEBUGDECLARE(debugObj);
   std::map<unsigned int, asnDataType *> undoInfo;
   snmpScalarData *data;

 public:

   scalarSet(snmpScalarData *data);
   virtual ~scalarSet();

   // accessors called by the CR.
   virtual snmpProtoErr *snmp_get(MIBRegistration *reg,
                                  SearchRangeList *thevars);
   virtual snmpProtoErr *snmp_getNext(MIBRegistration *reg, 
                                      SearchRangeList *thevars);
   virtual snmpProtoErr *snmp_set(MIBRegistration *reg, set_actions action, 
                                  SearchRangeList *thevars = 0);
};
#endif /* SCALARSET_H */
