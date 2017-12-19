#ifndef SNMPDATABASEOBJ_H
#define SNMPDATABASEOBJ_H

#include <string>
#include <iostream>
#include <fstream>

#include "snmpObj.H"
#include "snmpMutexObj.H"
#include "OID.H"
#include "PersistentVarBind.H"
#include "VarBindList.H"
#include "debug.H"

#include "snmpDatabaseRec.H"

class snmpIndexes;
class Sequence;
class PersistentOption;
class snmpDatabaseTraverser;

// a void pointer for classes that need context between db calls
typedef void * snmpDatabaseContext ;


//======================================================================
//
// snmpDatabaseObj
//  
class snmpDatabaseObj {

private:
  snmpDatabaseObj( const snmpDatabaseObj & ); // not implemented
  snmpDatabaseObj& operator= ( const snmpDatabaseObj &); // not implemented

  static snmpDatabaseObj * instance; // singleton instance pointer

  DEBUGDECLARE(pDebug);
  DEBUG_ERR_WARN_FLOW_DETAIL(pDebug);
   
  // directory path for the database
  static string dbPath;

  // mutex to protect agains multiple threads
  static snmpMutexObj dbMutex;
  static bool initialized;

  friend class PersistentOptions; // to set dbPath
  friend class DbHelper;

protected:
   virtual ~snmpDatabaseObj();
   snmpDatabaseObj( void );

   // database operations
   virtual snmpDatabaseContext open( const string & path ) = 0;
   virtual void close( snmpDatabaseContext & ) = 0;

   // database operations
   virtual void _firstkey( snmpDatabaseContext &, snmpDatabaseRec & ) = 0;
   virtual void _nextkey( snmpDatabaseContext &, snmpDatabaseRec & ) = 0;
   virtual void _store( snmpDatabaseContext &, snmpDatabaseRec &,
                        int flags = snmpDatabaseObj::insert ) =  0;
   virtual void _fetch( snmpDatabaseContext &, snmpDatabaseRec & ) = 0;
   virtual void _deleterec( snmpDatabaseContext &, snmpDatabaseRec &,
                            snmpDatabaseRec * putHere = NULL ) = 0;

   virtual snmpDatabaseRec * newRecord( void );

public:

   enum { insert = 0, replace };

  // this object is a singleton. The main application should
  //Use the getSnmpDatabaseObj()
  // method to get a reference to the one and only instance.
  // The instance will be bound to the database file specified
  // by sDbPath.
  static snmpDatabaseObj * openSnmpDatabaseObj( string sDbPath = "" );
  static snmpDatabaseObj * getSnmpDatabaseObj( void ) { return instance; };

  // returns the name of the database file currently in use.
  static const string get_dbPath( void ) { return dbPath; };

   // database operations
   int store( VarBind &, int flags = snmpDatabaseObj::replace );
   int fetch( VarBind & );
   int deleterec( const OID &, VarBind * = NULL ); // returns value from db
   int traverseDatabase( snmpDatabaseTraverser & );
   

   //------------------------------------------------------------

  // getInitialValue() will read a PersistentVarBind from the database,
  // or add it if it has no value.
  void getInitialValue( PersistentVarBind & vb );

  // return the values stored for the specified OID prefix.
  void get_storedValues( const OID &, const snmpIndexes &,
			 map<snmpIndexes, Sequence*> & );

  void update( const VarBind & vb );

  // remove all records for the specified OID from the database
  void remove( const OID & oid );
};



//======================================================================
//
// snmpDatabaseTraverser
//
//   The snmpDatabaseObj class does not allow one to open the database
//   and peruse records one at a time. To process records sequentially,
//   you must create a subclass of this object, and implement the 
//   processRecord method.
//
//   For each record in the database, the recordFilter method will be
//   called to allow the class to filter out records that it is not
//   interested in processing. If the recordFilter method returns true,
//   the processRecord method will be called for the record.
//
//   Due to limitations of some databases, there is no guarantee that
//   the records will be processed in any particular order.
//
class snmpDatabaseTraverser {

 private:
   snmpDatabaseTraverser(void); // not impl
   snmpDatabaseTraverser(const snmpDatabaseTraverser&); // not impl
   snmpDatabaseTraverser& operator =(const snmpDatabaseTraverser&); // not impl
   
 protected:
   bool     _done; // set this if you want to stop processing
   bool     _filter; // set this if you want to filter records

   // if filter is true, recordFilter will be called for each record,
   // and processRecord will not be called if recordFilter returns false.
   virtual bool     _recordFilter( const VarBind & ) { return true; }

   virtual void     _processRecord( const VarBind & ) = 0;
   
 public:
   snmpDatabaseTraverser( bool Filter ) 
       : _done(false), _filter(Filter) {};

   virtual ~snmpDatabaseTraverser(void) {};

   bool done(void) const { return _done; }
   bool filter(void) const { return _filter; }

   void     processRecord( const VarBind &vb ) {
       if(!_filter || _recordFilter( vb ))
           _processRecord( vb );
   }
       

};

#endif /* SNMPDATABASEOBJ_H */
