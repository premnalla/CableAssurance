#ifndef SNMPROWDEPENDENT_H
#define SNMPROWDEPENDENT_H

#include "snmpRow.H"
#include "snmpDataTable.H"

class snmpRowDependent {
 protected:
   snmpRow *row;              // we don't delete.
   snmpDataTable *table;      // don't even think about it.

 public:
   snmpRowDependent(snmpDataTable *table = NULL, snmpRow *row = NULL);
   snmpRowDependent(const snmpRowDependent &thecopy);
   virtual ~snmpRowDependent();

   /* accessors */
   virtual void set_row(snmpRow  *row);
   virtual snmpRow  *get_row(bool extract=false);
   virtual void set_table(snmpDataTable  *table);
   virtual snmpDataTable  *get_table(bool extract=false);
};

#endif /* SNMPROWDEPENDENT_H */
