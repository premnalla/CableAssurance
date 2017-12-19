#include "config.h"
#include <iostream>

#include "db_cxx.h"

#include "cfgScalarVar.H"

#include "snmpDatabaseObj.H"
#include "VarBindList.H"
#include "Sequence.H"
#include "OID.H"
#include "OctetString.H"
#include "Integer32.H"

ScalarVarOptions::ScalarVarOptions( void )
{
}

ScalarVarOptions::~ScalarVarOptions( void )
{
}

int
ScalarVarOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "Scalar_Variable", (void*)1 );

  return 0; // success
}

int
ScalarVarOptions::option_callback( const string & /*token*/, void* /*data*/,
				   std::deque<string> & parms )
{
  if (parms.size() < 3) {
    cerr << "Scalar_Variable: not enough tokens" << endl;
    cerr << "    Scalar_Variable { <oid> <type> <value> }" << endl;
    return 1;
  }

  if (parms[1].length() != 1 ) {
    cerr << "Scalar_Variable: <type> must be one of <s|i|o|x|c>" << endl;
    return 2;
  }

  asnDataType * val;
  switch( parms[1][0]) {

  case 's': {
    string tmp = parms[2];
    for( unsigned int i = 3; i < parms.size(); ++i ) {
      tmp += ' ';
      tmp += parms[ i ];
    }
    val = new OctetString( tmp );
  }
  break;

  case 'i':
    val = new Integer32( atoi( parms[2].c_str() ) );
    break;
  
  case 'o':
    val = new OID( parms[2] );
    break;
    
  case 'x': {
    val = new OctetString();
    ((OctetString*)val)->fromHexString( parms[2] );
  }
  break;
  
  case 'c' :
    val = new Integer32( atoi( parms[2].c_str() ) );
    break;
    
  default:
    cerr << "Scalar_Variable: unknown flag '" << parms[1] << "'" << endl;
    return 4;
  }
  
  VarBind *vbptr = new VarBind(new OID(parms[0]), val );
  //  VarBindList vbargs;
  //  vbargs.Add(vbptr);
  
  try {
    snmpDatabaseObj*  db = snmpDatabaseObj::getSnmpDatabaseObj();
    
    db->update( *vbptr );
  }
  catch ( DbException & dbe ) {
    cerr << "Database ERROR: " << dbe.what() << "\n";
    return 5;
  }
  catch (...) {
    cerr << "Scalar_Variable: caught unknown exception." << endl;
    return 6;
  }
  
  return 0; // success
}

void
ScalarVarOptions::option_help( const string & /*token*/, void* /*data*/,
			       std::ostream& os )
{
  os <<
    "    Scalar_Variable { <oid> <type> <value> }" << endl <<
    "                    : <type> is one of [s|i|o|x|c]" << endl;
}
