// snmpDataTable.C: implementation for the snmpDataTable mib.

#include "config.h"
#include "snmpDataTable.H"
#include "debug.H"

using std::list;

//
// snmpDataTable
//

snmpDataTable::snmpDataTable(int numIndexes) : indexes(numIndexes) {
}

snmpDataTable::snmpDataTable(const snmpDataTable & ref)
  : indexes( ref.indexes ) {
}

snmpDataTable::snmpDataTable(const snmpIndexes & indexes) : indexes(indexes) {
}

snmpDataTable::~snmpDataTable() {
    // XXX
}

void
snmpDataTable::add_index( berTag type ) {
  indexes.add_index( type );
}

snmpRow *
snmpDataTable::find(const list<asnIndex *> &searchKey) {
    snmpDataTable::iterator ri = find_iterator(searchKey);
    if (ri == data.end())
        return NULL;
    return ri->second;
}

snmpDataTable::iterator
snmpDataTable::find_iterator(const list<asnIndex *> &searchKey) {
    snmpIndexes key = indexes;
    list<berTag> lbt;
    list<asnIndex *>::const_iterator lai;
    int i;
    
    // XXX deal with incorrect search keys where tags don't match or
    // the number of keys passed is wrong.
    for(lai = searchKey.begin(), i=1; lai != searchKey.end(); lai++, i++) {
        key.set_index_number(i, *lai);
    }

    snmpDataTable::iterator ri = data.find(key);
    
    return ri;
}

snmpDataTable::iterator
snmpDataTable::find(const snmpIndexes &key) {
    return data.find(key);
}

asnDataType *
snmpDataTable::find(const list<asnIndex *> &searchKey, unsigned int colnum) {
    snmpRow *row = find(searchKey);
    if (!row)
        return NULL;
    return row->get_column(colnum);
}

OID *
snmpDataTable::createOID(OID *regoid, unsigned int colnum, 
			 std::map<snmpIndexes, snmpRow *>::iterator *ri) {
    OID *retoid;
    retoid = regoid->clone();
    retoid->append(1);           // Entry node
    retoid->append(colnum);
    retoid->append((*ri)->first.get_OID());
    return retoid;
}

bool
snmpDataTable::delete_row(const list<asnIndex *> &searchKey, 
                          bool onlyMarkDeleted) {
  snmpDataTable::iterator it = find_iterator( searchKey );
    return delete_row( it, onlyMarkDeleted );
}

bool
snmpDataTable::delete_row(const snmpIndexes &searchKey, bool onlyMarkDeleted) {
  snmpDataTable::iterator it = find( searchKey );
    return delete_row(it, onlyMarkDeleted);
}

bool
snmpDataTable::delete_row(snmpRow *row, bool onlyMarkDeleted) {
    snmpDataTable::iterator sdti;
    for(sdti = begin(); sdti != end(); sdti++) {
        if (sdti->second == row) {
            return delete_row(sdti, onlyMarkDeleted);
        }
    }
    return false;
}

bool
snmpDataTable::delete_row(snmpDataTable::iterator &row, bool onlyMarkDeleted) {
  if( ( row != data.end() ) && 
      ( canDelete( row->first, row->second) ) )
    {
        if (onlyMarkDeleted) {
            deletedRows.insert(row->first);
        } else {
            delete row->second;
            data.erase(row);
        }
      // didDelete( row->first, row->second );
        return true;
    }
    return false;
}

void
snmpDataTable::clear_delete_marks() {
        deletedRows.clear();
}

bool
snmpDataTable::delete_marked_rows() {
    bool somethingfailed = false;
    std::set <snmpIndexes>::iterator deletedRowIter;
    for(deletedRowIter = deletedRows.begin();
        deletedRowIter != deletedRows.end(); deletedRowIter++) {
        if (!delete_row(*deletedRowIter, true))
            somethingfailed = true;
    }
    deletedRows.clear();
    return !somethingfailed;
}

bool
snmpDataTable::canDelete(const snmpIndexes &indexValue, snmpRow *row) {
  return true;
}

void
snmpDataTable::didDelete(const snmpIndexes &indexValue, snmpRow *row) {
  ;
}

bool
snmpDataTable::add_row(const snmpIndexes &indexValue, snmpRow *row) {
  if( ! canAdd( indexValue, row ) )
    return false;
  data[indexValue] = row;
  didAdd( indexValue, row );
  return true;
}

bool
snmpDataTable::canAdd(const snmpIndexes &indexValue, snmpRow *row) {
  return (data.count(indexValue) == 0);
}

void
snmpDataTable::didAdd(const snmpIndexes &indexValue, snmpRow *row) {
  ;
}

void
snmpDataTable::dump() {
    DEBUGINIT(debugObj, "snmpDataTable");
    std::map <snmpIndexes, snmpRow *>::iterator ri;
    for(ri = data.begin(); ri != data.end(); ri++) {
        DEBUG9(debugObj, "snmpDataTable::dump -- " << ri->first.get_OID().toString() << "\n");
    }
}

snmpDataTable::iterator
snmpDataTable::begin() {
    return data.begin();
}

snmpDataTable::iterator snmpDataTable::end() {
    return data.end();
}

snmpDataTable::iterator
snmpDataTable::lower_bound(list<asnIndex *> &partialSearchKey) {
    snmpIndexes key = indexes;
    key.set_indexes(partialSearchKey);
    return data.lower_bound(key);
}

snmpDataTable::iterator
snmpDataTable::upper_bound(list<asnIndex *> &partialSearchKey) {
    snmpIndexes key = indexes;
    key.set_indexes(partialSearchKey);
    return data.upper_bound(key);
}
