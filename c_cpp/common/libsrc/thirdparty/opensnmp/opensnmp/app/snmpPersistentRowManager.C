#include "config.h"
#include "snmpPersistentRowManager.H"

#include <assert.h>

#include "snmpStorageType.H"
#include "snmpDatabaseObj.H"
#include "debug.H"

snmpPersistentRowManager::snmpPersistentRowManager( int storageTypeCol,
						    map <int, int> *colStorage,
						    map <int, bool> *setable_cols,
						    snmpRowAllocator *allocator)
  : snmpRowManager( setable_cols, allocator )
{
  this->storageTypeCol = storageTypeCol;
  this->columnStorage = colStorage;
}

snmpPersistentRowManager::~snmpPersistentRowManager( void )
{
  delete columnStorage;
}

void snmpPersistentRowManager::row_changed( OID *regoid,
					    const snmpRowKey & rowKey,
					    map<int,VarBind*> *cols )
{
  DEBUGINIT(debugObj, "snmpPersistentRowManager:row_changed" );

  const snmpIndexes & indexes = rowKey.first;
  snmpRow* changed = rowKey.second;
  asnDataType *asndt = changed->get_column( storageTypeCol );
  snmpStorageType* storageType = dynamic_cast<snmpStorageType*>(asndt);
  if( storageType == NULL ) {
    // column wasn't created as a snmpStorageType - probably was
    // created from database at startup for a table which doesn't
    // have a column allocator for the snmpStorageType column.
    DEBUG0( debugObj, "rowStorage column isn't really a rowStorage object!" );
    assert( false ); // debug catch
    return;
  }
  map<int, asnDataType *> & data = get_row_data( changed );

  // make sure we have persistent rows
  if( (columnStorage == NULL) || (columnStorage->size()==0) ) {
    DEBUG9( debugObj, "no persistent rows" );
    return;
  }

  // make sure we have a database
  snmpDatabaseObj * pDb = snmpDatabaseObj::getSnmpDatabaseObj();
  if( pDb == NULL ) {
    DEBUG0( debugObj, "no database!" );
    return;
  }

  // build database key. This is the OID, including indexes
  // for a column, with the column replaced with 0.
  OID key( *regoid ); // copy oid
  // xxx-rks - a tableEntry ALWAYS has an oid of 1, right?
  key.append( 1 ); // add tableEntry
  key.append( 0 ); // dummy col
  key.append( indexes.get_OID() );
  DEBUG0( debugObj, "save row w/oid " << key.get_numericStr() );

  Sequence * seqptr = new Sequence();
  VarBind vb( new OID( key ), seqptr );

  // should this row be stored to table storage?
  if( ! storageType->should_store() ) {
    // no - if it is set to volatile, remove it from the database
    if( storageType->get_value() == SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE )
      pDb->deleterec( key );

    return;
  }

  // xxx-rks : determining buffer size?
  map<int, asnDataType *>::iterator i;
  map<int, int>::iterator j;
  bool needUpdate = false; // do we need to update record?
  for( i = data.begin(); i != data.end(); ++i )
    {
      // make sure column has data
      if( i->second == NULL ) {
	DEBUG0( debugObj, "----->" << i->first << ": column is null" );
	continue;
      }
      // check if column is persistent
      if( ((j=columnStorage->find( i->first )) == columnStorage->end() ) ||
	  ( j->second == 0 ) ) {
	DEBUG0( debugObj, "----->" << i->first << ": not persistent" );
	continue;
      }
      needUpdate = true;
      DEBUG0( debugObj, "----->" << i->first << ':' << *i->second );
      seqptr->Add( new Integer32( i->first ) ); // col
      seqptr->Add( i->second->clone() ); // data
    }

  if( needUpdate ) {
    pDb->update( vb );
    DEBUG0( debugObj, "-----> updated" );
  }
  else {
    DEBUG0( debugObj, "-----> no update needed" );
  }
}


