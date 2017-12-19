#ifndef SNMPTABLE_H
#define SNMPTABLE_H
// snmptable.H

// A basic "show how it works" table implementation class.

#include "MIBImplementation.H"
#include "snmpDataTable.H"
#include "snmpIndexes.H"
#include "snmpRow.H"
#include <asnDataTypes.H>
#include "snmpRowAllocator.H"

class undoKey; // defined in .C

class snmpTable : public MIBImplementation {
 private:
   // configuration
   unsigned int maxcolnum, mincolnum;

protected:
   snmpDataTable::dataStorageType dSType;
   typedef std::pair<unsigned int, snmpIndexes> undoKey;
   std::map <undoKey, asnDataType *> undoInformation;
   std::set <snmpIndexes> newRows;
   snmpRowManager *manager;
   snmpDataTable *datatable;

 public:
   snmpTable(snmpDataTable *table, unsigned int maxcolumn, 
             unsigned int mincolumn = 1, 
             snmpDataTable::dataStorageType dSType = snmpDataTable::READ_ONLY,
	     snmpRowManager *manager = NULL );
   snmpTable(const snmpIndexes & indexes, unsigned int maxcolumn, 
             unsigned int mincolumn = 1, 
             snmpDataTable::dataStorageType dSType = snmpDataTable::READ_ONLY,
             snmpRowManager *manager = NULL);
   virtual ~snmpTable();

   // Setup

   // init() is called after the table is registered by the cr.
   virtual void init( const OID & registeredAt );
 // virtual void set_setableFields(std::map<unsigned int, bool> &setable_cols);

   // SNMP protocol accessors
   virtual snmpProtoErr *snmp_get(MIBRegistration *reg,
                                  SearchRangeList *thevars);
   virtual snmpProtoErr *snmp_getNext(MIBRegistration *reg, 
                                      SearchRangeList *thevars);
   virtual snmpProtoErr *snmp_set(MIBRegistration *reg, set_actions action, 
                                  SearchRangeList *thevars = 0);

   // Pass throughs
   virtual bool add_row(const snmpIndexes &indexValue, snmpRow *row);

};
#endif /* SNMPTABLE_H */
