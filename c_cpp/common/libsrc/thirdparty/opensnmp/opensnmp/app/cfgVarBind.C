#include "config.h"
#include "debug.H"
#include "cfgVarBind.H"
#include "asnDataTypes.H"

VarBindOptions::VarBindOptions( VarBindList * & list  )
  : vbargs( list )
{
}

VarBindOptions::~VarBindOptions( void )
{
}

int
VarBindOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "--varbind", (void*)0, 3, 3 );

  return 0; // success
}

int
VarBindOptions::option_callback( const string & /*token*/, void* data,
				 std::deque<string> & parms )
{
  std::deque<string>::iterator it = parms.begin();

  OID * oid = new OID( *it++ );
  asnDataType * val;

  string type = *it++;
  switch( type[0] ) {

  case 's':
  case 'x':
	// allow hex values for OctetStrings...
	{ 
	  OctetString *setOString = new OctetString();
	  setOString->fromString(*it++);
	  val = setOString;
	}
	break;

  case 'i':
	val = new Integer32( *it++ );
	break;

  case 'o':
	val = new OID( *it++ );
	break;

  case 'n':
        val = new Null();
	break;
  default:
	std::cerr << "unknown type '" << type << "'\n";
	return 1;
	break;
  }

  vbargs->Add( new VarBind( oid, val) );

  return 0; // success
}

void
VarBindOptions::option_help( const string &, void* data, std::ostream& os )
{
  if( data == (void*)'t' ) {
    os << "    : add timestamp to debug messages" << std::endl;
  }
  else if( data == (void*)'q' ) {
    os << ": debug quiet mode" << std::endl;
  }
  else
  os <<
    " (OID) (type) (value)  : create a varbind with:\n" <<
    "                         OID is 'OID'\n" <<
    "                         type is one of: 'i':Integer32 ; 'o':OID ;\n" <<
    "                           'x' or 's' : string (hex values 0x prefix)\n" <<
    "                         value is a value of previous type.\n";
}
