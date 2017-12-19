#include "config.h"
#include <iostream>
#include <sstream>
#include <fstream>
#include <memory>
#include <assert.h>

#include "snmpDatabaseObj.H"
#include "snmpDatabase_BDB.H"
#include "snmpDatabase_NDBM.H"
#include "OctetString.H"
#include "OID.H"
#include "Sequence.H"
#include "snmpIndexes.H"
#include "debug.H"
#include "opensnmpUtilities.H"


//======================================================================
// singleton instance pointer
snmpDatabaseObj * snmpDatabaseObj::instance = NULL;
// used for string initialzation, hacky but seems to work, sigh
bool   snmpDatabaseObj::initialized = false;
// default path
string snmpDatabaseObj::dbPath = "/var/opensnmp";
// mutex
snmpMutexObj snmpDatabaseObj::dbMutex = snmpMutexObj();


//======================================================================
//
// DbHelper
//
//   this helper class will open the database in it's ctor, and close
//   the database in it's dtor. Creating a local DbHelp object will
//   open the database, and ensure that it is closed when the local
//   object goes out of scope.
class DbHelper {
 private:
   DbHelper( void ); // not implemented
   DbHelper( const DbHelper& ); // not implemented

   DEBUGDECLARE(hDebug);
   DEBUG_ERR_WARN_FLOW_DETAIL(hDebug);
   snmpDatabaseContext ctx;
   snmpDatabaseObj & db;

public:


  DbHelper( const char *path, snmpDatabaseObj & db );
  ~DbHelper( void );

   //------------------------------------------------------------
   // database operations
   //
   void fetch( snmpDatabaseRec &rec ) {
       db._fetch( ctx, rec );
   };
   
   void store( snmpDatabaseRec &rec, int flags ) {
       db._store( ctx, rec, flags );
   };
   void store( snmpDatabaseRec &rec ) {
       db._store( ctx, rec );
   };
   
   void deleterec( snmpDatabaseRec &rec, snmpDatabaseRec * value = NULL) {
       db._deleterec( ctx, rec, value );
   };
   
   // firstkey() and nextkey() can be used to traverse the entire
   // database.
   //
   // NOTE WELL: while these two routine can be used to traverse
   // the entire database, no gurantees are made as to what order
   // the data will be presented. DO NOT ASSUME ANY ORDERING.
   //
   void firstkey(snmpDatabaseRec &putHere) {
       return db._firstkey(ctx, putHere);
   };
   void nextkey(snmpDatabaseRec &putHere) {
       return db._nextkey(ctx, putHere);
   };

};

DbHelper::DbHelper( const char *path, snmpDatabaseObj & theDB )
    : db( theDB )
{

  DEBUGCREATE(this->hDebug, "DbHelper");
  
  flow( string("acquiring lock") );
  db.dbMutex.lock("DbHelper:opening DB");

  flow( string("opening database") );
  ctx = db.open( path );
}

DbHelper::~DbHelper( void )
{
  flow( string("closing database") );
  db.close( ctx );

  flow( string("releasing lock") );
  db.dbMutex.unlock("DBHelper:closing DB");
  DEBUGDESTROY(this->hDebug);
}

//======================================================================
//
//
// snmpDatabaseObj methods
//
snmpDatabaseObj::snmpDatabaseObj () {
  if (!snmpDatabaseObj::initialized) {
    snmpDatabaseObj::dbMutex.lock("snmpDatabaseObj constructor");
    if (!snmpDatabaseObj::initialized) {
      // default path
      snmpDatabaseObj::initialized = true;
      snmpDatabaseObj::dbPath      = string("/var/opensnmp");
    }
    snmpDatabaseObj::dbMutex.unlock("snmpDatabaseObj constructor");
  }
    
  DEBUGCREATE(pDebug, string("snmpDatabase"));
  flow( string("snmpDatabaseObj ctor") );
}

snmpDatabaseObj::~snmpDatabaseObj( void )
{
  flow( string("snmpDatabaseObj dtor") );
  DEBUGDESTROY(pDebug);
}

// this object is a singleton. The main application should
// Use the getSnmpDatabaseObj()
// method to get a reference to the one and only instance.
// The instance will be bound to the database file specified
// by sdbPath.
snmpDatabaseObj * 
snmpDatabaseObj::openSnmpDatabaseObj( string dbPathIn )
{
  // do any neccessary static init before object created :}
  if (!snmpDatabaseObj::initialized) {
    snmpDatabaseObj::dbMutex.lock("openSnmpDatabaseObj initializing");
    if (!snmpDatabaseObj::initialized) {
      // default path
      snmpDatabaseObj::initialized = true;
      snmpDatabaseObj::dbPath      = string("/var/opensnmp");
    }
    snmpDatabaseObj::dbMutex.unlock("openSnmpDatabaseObj initializing");
  }
  // static functions won't work with debug objects
  //  flow( string("openSnmpDatabaseObj( " << dbPath << " )") );

  // should we throw an exception on multiple open attempts?
  // for now, just ignore them and return existing instance.
  if( instance == NULL ) {
    if( dbPathIn.empty() )
      dbPathIn = dbPath;
    else
      dbPath = dbPathIn;

    if(dbPath[dbPath.size()] != '/')
        dbPath.append("/");

#if defined(HAVE_DB1_NDBM_H) && defined(OPENSNMP_USE_NDBM)
    instance = new snmpDatabase_NDBM();
#else
    instance = new snmpDatabase_BDB();
#endif
  }

  return instance;
}

snmpDatabaseRec *
snmpDatabaseObj::newRecord( void )
{
    return new snmpDatabaseRec();
}

//----------------------------------------------------------------------
int 
snmpDatabaseObj::traverseDatabase( snmpDatabaseTraverser & processor )
{
    // it would be silly to call us with the done flag already set,
    // but I'm sure someone will do it.
    if(processor.done())
        return snmpDatabaseRec::noError;

    // the DbHelper will ensure we have a mutex lock and open the database.
    // when it goes out of scope, the dtor will release the mutex locak
    // and close the database.
    DbHelper dbHelper( dbPath.c_str(), *this );
    auto_ptr<snmpDatabaseRec> rec( newRecord() );
    VarBind vb;

    // get the first record
    dbHelper.firstkey( *rec );

    // while there are no database error, loop for each record
    while( rec->getError() == snmpDatabaseRec::noError ) {

        // extract raw data into a varbind
        rec->get( vb );
        
        // pass vb to traverser
        processor.processRecord( vb );

        // make sure the processor doesn't want to quit
        if(processor.done())
            break;

        // get the next record
        dbHelper.nextkey( *rec );
    }

    return rec->getError();
}

int 
snmpDatabaseObj::store( VarBind & vb, int flags )
{
    // the DbHelper will ensure we have a mutex lock and open the database.
    // when it goes out of scope, the dtor will release the mutex locak
    // and close the database.
    DbHelper dbHelper( dbPath.c_str(), *this );
    auto_ptr<snmpDatabaseRec> rec( newRecord() );
    rec->set( vb ); 
    
    dbHelper.store( *rec );

    return rec->getError();
}

int 
snmpDatabaseObj::fetch( VarBind & vb )
{
    // the DbHelper will ensure we have a mutex lock and open the database.
    // when it goes out of scope, the dtor will release the mutex locak
    // and close the database.
    DbHelper dbHelper( dbPath.c_str(), *this );
    auto_ptr<snmpDatabaseRec> rec( newRecord() );
    rec->set( vb, snmpDatabaseRec::key );
    
    dbHelper.fetch( *rec );

    return rec->getError();
}

int 
snmpDatabaseObj::deleterec( const OID & oid, VarBind * vb )
{
    // the DbHelper will ensure we have a mutex lock and open the database.
    // when it goes out of scope, the dtor will release the mutex locak
    // and close the database.
    DbHelper dbHelper( dbPath.c_str(), *this );
    auto_ptr<snmpDatabaseRec> rec( newRecord() );
    snmpDatabaseRec* val = vb ? newRecord() : NULL;

    rec->setKey( oid );
    dbHelper.deleterec( *rec, val );

    if(val && (rec->getError() == snmpDatabaseRec::noError) ) {
        val->get( *vb );
        delete val;
    }

    return rec->getError();
}


//----------------------------------------------------------------------
// update() will update or add a PersistentVarBind to the database
void
snmpDatabaseObj::getInitialValue( PersistentVarBind & vb )
{
  //  flow( string("getInitialValue( " << vb << " )") );

  // the DbHelper will ensure we have a mutex lock and open the database.
  // when it goes out of scope, the dtor will release the mutex locak
  // and close the database.
  DbHelper dbHelper( dbPath.c_str(), *this );
  flow( string("getInitialValue( " + string(vb) + " )") );

  // create key from vb. No data, yet, since we don't need it when
  // when we are getting data from the database.
  auto_ptr<snmpDatabaseRec> rec( newRecord() );
  rec->set( vb, snmpDatabaseRec::key );

  dbHelper.fetch( *rec );
  if( rec->getError() == snmpDatabaseRec::noError ) {
      flow( string("updating record.") );
      asnDataType *pData; // hmm, not sure if auto_ptr would work here...
      asnDataType::asnDecodeUnknown( (char*)rec->getData(), &pData,
                                     rec->getDataLen() );
      vb.set_value_no_update( *pData ); 
      delete pData; //asnDecodeUnknown always creates new object
      return;
  }

  flow( string("record not found. saving initial value.") );

  // ok, now we need the data too.
  rec->set( vb, snmpDatabaseRec::data );
  
  // update the record
  dbHelper.store( *rec );
}

// update() will update or add a VarBind to the database
void
snmpDatabaseObj::update( const VarBind & vb )
{
  flow( string("update( " + string(vb) + " )") );

  // the DbHelper will ensure we have a mutex lock and open the database.
  // when it goes out of scope, the dtor will release the mutex locak
  // and close the database.
  DbHelper dbHelper( dbPath.c_str(), *this );
  auto_ptr<snmpDatabaseRec> rec( newRecord() );
  rec->set( vb );

  dbHelper.store( *rec, snmpDatabaseObj::replace );
}

// return the values stored for the specified OID prefix.
void
snmpDatabaseObj::get_storedValues( const OID & prefix, 
				   const snmpIndexes & indexes,
				   map<snmpIndexes, Sequence*> & values )
{
  flow( string("get_storedValues( " + string(prefix) + ", ... )") );

  // the DbHelper will ensure we have a mutex lock and open the database.
  // when it goes out of scope, the dtor will release the mutex locak
  // and close the database.
  DbHelper dbHelper( dbPath.c_str(), *this );
  auto_ptr<snmpDatabaseRec> rec( newRecord() );

  dbHelper.firstkey( *rec );

  while( rec->getError() == snmpDatabaseRec::noError ) {

      // decode asn key data data
      asnDataType *pKey;
      asnDataType::asnDecodeUnknown( (char*)rec->getKey(), &pKey,
                                     rec->getKeyLen() );
      
      OID *pOid = dynamic_cast<OID*> (pKey);
      if( pOid->mincompare( prefix ) == 0 ) {
          flow( string("found match.") );
          snmpIndexes idx( indexes );
          detail( string("setting index from ") + string(*pOid) );
          // skip entry+col+?
          bool ok = idx.set_fromOID( *pOid, prefix.length() + 3 );
          assert( ok == true );

          // decode asn value data
          asnDataType *pData;
          asnDataType::asnDecodeUnknown( (char*)rec->getData(), &pData,
                                         rec->getDataLen() );
          Sequence *pSeq = dynamic_cast<Sequence*>(pData);
          detail( string("Found idx [") + string(idx) + "] with value ["
		  + string(*pSeq) + ']' );
          values[ idx ] = pSeq;
      }
      delete pOid; //asnDecodeUnkown always creates new object

      // next record
      dbHelper.nextkey( *rec );
  }

  if( rec->getError() != snmpDatabaseRec::noSuchRec ) {
    err( string("ERROR in get_storedValues; record reports error ") \
	 + OPENSNMP_UTILITIES::intToString(rec->getError()) );
  }
}
