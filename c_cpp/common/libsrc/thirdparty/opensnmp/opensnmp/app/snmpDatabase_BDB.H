#ifndef SNMPDATABASEBDB_H
#define SNMPDATABASEBDB_H

#include "snmpDatabaseObj.H"

#include "db_cxx.h"



class snmpDatabase_BDB : public snmpDatabaseObj {

private:
   friend class snmpDatabaseObj;

   snmpDatabase_BDB( const snmpDatabase_BDB & ); // not implemented
   snmpDatabase_BDB& operator= ( const snmpDatabase_BDB &); // not implemented
   
   DEBUGDECLARE(pDebug);
   DEBUG_ERR_WARN_FLOW_DETAIL(pDebug);
   
 protected:
   snmpDatabase_BDB( void ) {}
   virtual ~snmpDatabase_BDB() {}
   
 public:
   
   // database operations
   virtual snmpDatabaseContext open( const string & path );
   virtual void close( snmpDatabaseContext & );
   
   // database operations
   virtual void _firstkey( snmpDatabaseContext &, snmpDatabaseRec & );
   virtual void _nextkey( snmpDatabaseContext &, snmpDatabaseRec & );
   virtual void _store( snmpDatabaseContext &, snmpDatabaseRec &,
                        int flags = snmpDatabaseObj::insert );

   virtual void _fetch( snmpDatabaseContext &, snmpDatabaseRec &);
   virtual void _deleterec( snmpDatabaseContext &, snmpDatabaseRec &,
                            snmpDatabaseRec *putHere = NULL);

};

#endif /* SNMPDATABASEBDB_H */
