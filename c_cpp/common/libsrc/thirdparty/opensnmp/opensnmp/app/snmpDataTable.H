#ifndef SNMPDATATABLE_H
#define SNMPDATATABLE_H
// snmpDataTable.H

// A basic "show how it works" table implementation class.

#include "snmpIndexes.H"
#include "snmpRow.H"
#include <asnDataTypes.H>
#include "snmpRowAllocator.H"
#include <set>

class snmpDataTable : public snmpObj {

 public:
   typedef std::map<snmpIndexes, snmpRow *>::iterator iterator;
   // XXX: shouldn't go here, but can't go in snmpTable.H
   enum dataStorageType
   {
       READ_ONLY,
       SOMEWRITABLE,
       ALLWRITABLE
   };

 protected:
   // data
   snmpIndexes indexes;
   std::map <snmpIndexes, snmpRow *> data;
   std::set <snmpIndexes> deletedRows;
   bool delayRowDeletions;

 public:
   snmpDataTable( int numIndexes ); // allocate space for N indexes
   snmpDataTable(const snmpIndexes & indexes);
   snmpDataTable(const snmpDataTable & ref);
   virtual ~snmpDataTable();

   //----------------------------------------------------------------------
   // setup convienence methods
   //----------------------------------------------------------------------

   // After using the snmpDataTable( int ) CTOR, add_index is used
   // to specify the data types of the indexes for this table.
   virtual void add_index( berTag type );
   virtual bool get_delayRowDeletions() { return delayRowDeletions; }
   virtual void set_delayRowDeletions(bool val) { delayRowDeletions = val; }
   
   

   //----------------------------------------------------------------------
   // Accessors to the data
   //----------------------------------------------------------------------

   asnDataType *find(const std::list<asnIndex *> &searchKey, unsigned int colnum);
   snmpRow     *find(const std::list<asnIndex *> &searchKey);

   virtual bool add_row(const snmpIndexes &indexValue, snmpRow *row);

   // delete rows or mark them for deletion later.
   virtual bool delete_row(const std::list<asnIndex *> &searchKey,
                           bool onlyMarkDeleted = false);
   virtual bool delete_row(const snmpIndexes &searchKey,
                           bool onlyMarkDeleted = false);
   virtual bool delete_row(snmpRow *row,  // warning: linear search
                           bool onlyMarkDeleted = false);
   virtual bool delete_row(snmpDataTable::iterator &row,
                           bool onlyMarkDeleted = false);

   // delete the marked rows
   virtual bool delete_marked_rows();
   virtual void clear_delete_marks();

   virtual OID *createOID(OID *regoid, unsigned int colnum,
                          std::map <snmpIndexes, snmpRow *>::iterator *ri);
   void dump();

   const snmpIndexes & get_snmpIndexes() { return indexes; };

   virtual snmpDataTable *clone() const { return new snmpDataTable(*this); };

   //----------------------------------------------------------------------
   // allow sub-classes to override behavior
   //----------------------------------------------------------------------
  virtual bool canAdd( const snmpIndexes &indexValue, snmpRow *row );
  virtual bool canDelete( const snmpIndexes &indexValue, snmpRow *row );

  virtual void didAdd( const snmpIndexes &indexValue, snmpRow *row );
  virtual void didDelete( const snmpIndexes &indexValue, snmpRow *row );

   //----------------------------------------------------------------------
   iterator begin();
   iterator end();
   iterator lower_bound(std::list<asnIndex *> &partialSearchKey);
   iterator upper_bound(std::list<asnIndex *> &partialSearchKey);
   iterator find_iterator(const std::list<asnIndex *> &searchKey);
   iterator find(const snmpIndexes &key);
};
#endif /* SNMPDATATABLE_H */
