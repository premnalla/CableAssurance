#ifndef SYSORTABLE_H
#define SYSORTABLE_H
// sysOrTable.H

// A basic "show how it works" implementation class.

#include "MIBImplementation.H"
#include <asnDataTypes.H>

class sysOrEntry {
 private:
   OID         *sysORID;
   OctetString *sysORDescr;
   TimeTicks   *sysORUpTime;
 public:
   sysOrEntry(OID *sysORID = 0, OctetString *sysORDescr = 0, 
              TimeTicks *sysORUpTime = 0);
   virtual ~sysOrEntry();
};

class sysOrTableMib : public MIBImplementation {
 private:
   std::list<sysOrEntry> items;
   // data
 public:

   sysOrTableMib();
   virtual ~sysOrTableMib();

   virtual void add(sysOrEntry item);
   
   virtual snmpProtoErr *get(MIBRegistration *reg, SearchRangeList *thevars);
   virtual snmpProtoErr *getNext(MIBRegistration *reg, 
                                 SearchRangeList *thevars);
   virtual snmpProtoErr *set(MIBRegistration *reg, set_actions action, 
                             SearchRangeList *thevars = 0);
};
#endif /* SYSORTABLE_H */
