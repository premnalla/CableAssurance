#ifndef SNMPDATABASENDBM_H
#define SNMPDATABASENDBM_H

#include "snmpDatabaseObj.H"

class snmpDatabase_NDBM : public snmpDatabaseObj {

private:
   friend class snmpDatabaseObj;

   snmpDatabase_NDBM( const snmpDatabase_NDBM & ); // not implemented
   snmpDatabase_NDBM& operator=( const snmpDatabase_NDBM &); // not implemented
   
   DEBUGDECLARE(pDebug);
   DEBUG_ERR_WARN_FLOW_DETAIL(pDebug);

 protected:
   snmpDatabase_NDBM( void ) {}
   virtual ~snmpDatabase_NDBM() {}
   
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

#endif /* SNMPDATABASENDBM_H */
