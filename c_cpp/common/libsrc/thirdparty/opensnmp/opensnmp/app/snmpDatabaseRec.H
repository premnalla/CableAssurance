#ifndef SNMPDATABASEREC_H
#define SNMPDATABASEREC_H

#include <string>
#include <iostream>
#include <fstream>

#include "snmpObj.H"
#include "VarBind.H"
#include "debug.H"


#ifndef MIN
#define MIN(a,b) (a < b) ? (a) : (b)
#endif

//======================================================================
//
// snmpDatabaseRec
//
//   An abstract base class for containing a database key and the
//   data associated with that key. This class only defines an
//   interface. Data storage is up to the sub-classes.
//
class snmpDatabaseRec {

 private:
   friend class snmpDatabaseObj;

   //------------------------------------------------------------
   snmpDatabaseRec( const snmpDatabaseRec & that); // not impl
   snmpDatabaseRec & operator =( const snmpDatabaseRec & from ); // not impl

   int dbError;

   char *k, *d;
   size_t k_len, d_len;

 protected:
   //------------------------------------------------------------
   snmpDatabaseRec( void )
       : dbError(0), k(0), d(0), k_len(0), d_len(0) {};

 public:
   //------------------------------------------------------------
   enum { key = 0x1, data = 0x2, both = 0x3 };
   enum { noError = 0, unknownError, noSuchRec, duplicateRec };

   virtual ~snmpDatabaseRec(void);

   //------------------------------------------------------------
   void setError(int err) { dbError = err; }
   int  getError(void) const { return dbError; }

   //------------------------------------------------------------
   void set( const VarBind & vb, int flags = snmpDatabaseRec::both);
   void get( VarBind & vb, int flags = snmpDatabaseRec::both);

   //------------------------------------------------------------
   void setKey( void *key, size_t key_len );
   void setKey( const OID & );

   const void * getKey(void) const { return k; }
   size_t       getKeyLen( void ) const { return k_len; }
   void         getKey( VarBind & );

   bool keyMatches( const snmpDatabaseRec & rhs ) const {
       return (k_len!=rhs.k_len) ? false : (memcmp(k,rhs.k,k_len)==0);
   }

   //------------------------------------------------------------
   void setData( void *data, size_t data_len );
   void setData( const asnDataType & );

   const void * getData( void ) const { return d; }
   size_t       getDataLen( void ) const { return d_len; }
   void         getData( VarBind & );

   bool dataMatches( const snmpDatabaseRec & rhs ) {
       return (d_len!=rhs.d_len) ? false : (memcmp(d,rhs.d,d_len)==0);
   }

};


#endif
