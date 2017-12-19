#ifndef SNMPINDEXES_H
#define SNMPINDEXES_H

#include <list>
#include <vector>

#include <asnDataType.H>
#include <asnDataTypes.H>

#include "snmpException.H"

class snmpIndexesWrongDataTypeError : public snmpException {
 public:
   int problemIndexNum;
   snmpIndexesWrongDataTypeError(int problemIndexNum) {
       this->problemIndexNum = problemIndexNum;
   }
   virtual string what() {
       // XXX: make better error.
       return "Wrong data type passed to indexes";
   }
};


class snmpIndexes : public snmpObj {
 private:
   unsigned int _size;
   std::list<berTag> indexTypes;
   bool lastimplied;
   asnIndex **values;
   
   snmpIndexes();

   void allocateArray();

public:
   // allocate space for size indexes
   snmpIndexes(int size, bool lastimplied = false);
   snmpIndexes(const std::list<berTag> & indexTypes, bool lastimplied = false);
   snmpIndexes(const snmpIndexes &ref);
   ~snmpIndexes();

   virtual OID get_OID(OID *prefix = NULL) const;
   virtual OID& append_OID(OID &to) const;
   virtual bool set_fromOID(const OID & theoid, const OID & prefix);
   virtual bool set_fromOID(const OID &theoid, unsigned int prefixlen = 0);

   // add index of specified type. Used to add index types when
   // the snmpIndex( size ) CTOR was used.
   void add_index( berTag type ); 
  
   // [gs]et_index_number( X ) all deal with column X,
   //    where X is 1-N (1 based indexing)
   virtual asnIndex *get_index_number(unsigned int num);
   virtual const asnIndex *get_index_number(unsigned int num) const;
   // this class assumes responsibility for the value pointer
   virtual void set_index_number(unsigned int num, asnIndex *value);
 
   virtual void set_indexes(std::list<asnIndex *> &siref);
   virtual void set_indexes_cloned(std::list<asnIndex *> &siref);

   virtual void set_last_implied(bool val = true) {
       lastimplied = val;
   }

   virtual bool get_last_implied() {
       return lastimplied;
   }

   // used by <map>
   virtual bool operator < (const snmpIndexes &ref) const;

   // convenience functions.
   unsigned int size() const { return _size; }
   void deleteData();
   virtual string toString() const;
   virtual snmpIndexes *clone() const;
   virtual snmpIndexes *cloneEmpty() const;
   virtual snmpIndexes &copy(const snmpIndexes &ref); // returns self
   virtual void operator = (const snmpIndexes &ref) {
       this->copy(ref);
   }
};

#endif /* SNMPINDEXES_H */
