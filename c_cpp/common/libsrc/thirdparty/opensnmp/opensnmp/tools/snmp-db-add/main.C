#include "config.h"
#include <iostream>

#include "snmpDatabaseObj.H"
#include "VarBindList.H"
//#include "PersistentVarBind.H"
#include "Sequence.H"
#include "OID.H"
#include "OctetString.H"
#include "Integer32.H"
#include "Null.H"

#include "db_cxx.h"

int main( int argc, char** argv )
{
  int rc = 0;
  VarBindList vbargs;
  //VarBind *vb = NULL;
  //PersistentVarBind *pvb = NULL;

  if (argc > 2) {
    int argcount = 2;
    VarBind *vbptr;
    Sequence *sptr = NULL;

    while(argcount < argc) {

      switch(argv[argcount][0]) {

      case '.':
	vbptr = new VarBind(new OID(argv[argcount]), new Null());
	vbargs.Add(vbptr);
	sptr = NULL;
	cout << "CG: cgmain: added" << argv[argcount] << endl;
	break;

      case 's':
	if( sptr )
	  sptr->Add( new OctetString(argv[++argcount]) );
	else
	  vbptr->set_value(new OctetString(argv[++argcount]));
	break;

      case 'i':
	if( sptr )
	  sptr->Add( new Integer32(argv[++argcount]) );
	else
	  vbptr->set_value(new Integer32(argv[++argcount]));
	break;

      case 'o':
	if( sptr )
	  sptr->Add( new OID(argv[++argcount]) );
	else
	  vbptr->set_value(new OID(argv[++argcount]));
	break;
                      
      case 'x': {
	OctetString *pStr = new OctetString();
	pStr->fromHexString(argv[++argcount]);
	if( sptr )
	  sptr->Add( pStr );
	else
	  vbptr->set_value(pStr);
      }
      break;

      case 'c' :
	if( sptr )
	  sptr->Add( new Integer32(argv[++argcount]) );
	else
	  vbptr->set_value( sptr = new Sequence( new Integer32(argv[++argcount]) ) );
	break;

      default:
	cerr << "unknown flag: " << argv[argcount] << endl;
	exit(1);
      }
      
      ++argcount;
    }
  }
 
  cout << "adding items to database: " << endl;

  try {
    snmpDatabaseObj*  db = snmpDatabaseObj::openSnmpDatabaseObj( argv[1] );
    
    list<VarBind *>::iterator i;
    for( i = vbargs.begin(); i != vbargs.end(); ++i ) {
      cout << '>' << **i << endl;
      db->update( **i );
    }
  }
  catch (DbException &dbe) {
    cerr << "Database ERROR: " << dbe.what() << "\n";
    rc = 1;
  }

  cout << "done." << endl;
  return rc;
}
