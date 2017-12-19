#include "config.h"
#include <iostream>

#include "snmpDatabaseObj.H"

class outputTraversal : public snmpDatabaseTraverser {
 private:
   outputTraversal( const outputTraversal& ); // not impl

 public:
   outputTraversal( void ) : snmpDatabaseTraverser(false) {}
   ~outputTraversal( void ) {}

   virtual void     _processRecord( const VarBind & vb ) {
       cout << "Found OID [" << vb.get_OID()->toDisplayString(NUMERIC) 
            << "] with value [" << *vb.get_value() << ']' << endl;
   }
};

int
main( int argc, char** argv )
{
  if( argc != 2 ) {
    cerr << "usage: crdbdump <database directory>" << endl;
    exit( 1 );
  }

  snmpDatabaseObj*  db = snmpDatabaseObj::openSnmpDatabaseObj( argv[1] );
  
  VarBind vb;
  outputTraversal ot;

  db->traverseDatabase( ot );
  
  cout << "done." << endl;
  return 0;
}
