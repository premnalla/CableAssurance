#ifndef SNMPROW_H
#define SNMPROW_H

#include <map>

#include <asnDataTypes.H>
#include <OID.H>
#include <OctetString.H>
#include <Integer32.H>
#include <snmpColumnAllocator.H>
#include <snmpIndexes.H>

typedef std::map<int, snmpColumnAllocator *> snmpColumnAllocatorList;
typedef std::pair<snmpIndexes, snmpRow*> snmpRowKey;

class snmpRowManager;

class snmpRow : public snmpObj {
 protected:
   // deliberately signed so data could be stored in a location below
   // that which could be requested through SNMP (non-accessible data, etc).
   std::map<int, asnDataType *> data;
   snmpColumnAllocatorList *columnAllocators;
   friend class snmpRowManager; // needs access to column storage & data

 public:
   typedef std::map<int, asnDataType *> defaultValueList;
   typedef std::map<int, asnDataType *>::iterator iterator;
   snmpRow( const snmpRow & );
   snmpRow(std::map<int, snmpColumnAllocator *> *columnAllocators = NULL,
           defaultValueList *defValues = NULL);
   virtual ~snmpRow();

   asnDataType *get_column(int col, bool extract=false);

  // use default values to fill in any empty colums
  void allocateMissingDefaults( const defaultValueList & );

   // typed 'get_columns' that do casting for you. 
   // It would be nice to not clog up the namespace, but in a 
   // 'OO language' that doesn't overload using its required return type,
   // what are you going to do...
   OID         *get_column_OID(int col, bool extract=false)  {
     return dynamic_cast<OID *>(this->get_column(col, extract)); 
   };

   OctetString *get_column_OctetString(int col, bool extract=false)  {
     return dynamic_cast<OctetString *>(this->get_column(col,extract));
   };

   Integer32   *get_column_Integer32(int col, bool extract=false) {
     return dynamic_cast<Integer32 *>(this->get_column(col, extract));
   };
   
   // pass in the asnDataType * of a value in this row, this returns its 
   // column number. (So an object in a row can find what column it's in)
   // If not found, get_column_number returns 0;
   int  get_column_number(asnDataType *val);

   // set a column to a new value
   virtual void set_column(int col, const asnDataType &);

   // create a column with a starting value
   virtual void create_column(int col, const asnDataType &);

   virtual string toString() const;
   virtual snmpRow *clone() const { return new snmpRow(*this); }

   // Not to be used by the general public if avoidable.  Use
   // set_column instead, which forces usage semantics for the object.
   // this is only used for undo type cases where usage semantics need
   // to be over-ridden.
   void set_columnForced(int col, asnDataType *thedata);

   snmpRow::iterator begin() {
       return data.begin();
   }
   snmpRow::iterator end() {
       return data.end();
   }
};

#endif /* SNMPROW_H */
