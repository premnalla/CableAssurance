#include "config.h"
#include <iostream>

//
#include <string>
#include <stack>

//
#include "debug.H"
#include "OID.H"
#include "configMgr.H"
#include "cfgTableEntry.H"
#include "cfgScalarVar.H"
#include "snmpDatabaseObj.H"
#include "cfgMib.H"

#define ERR( x ) DEBUG0( debugObj, x  )
#define WARN( x ) DEBUG1( debugObj, x  )
#define FLOW( x ) DEBUG5( debugObj, x  )
#define DETAIL( x ) DEBUG9( debugObj, x  )


int
main( int argc, char ** argv )
{
  DEBUGINIT(debugObj, "ConfigApp");

  // create configuration manager with default path
  ConfigMgr config( false, "/var/opensnmp/config.txt" );

  config.register_option( new ScalarVarOptions() );
  config.register_option( new TableEntryOptions() );
  config.register_option( new MibOptions() );

  // check command line
  if( config.parse_command_line(argc, argv) ) {
    config.print_help( ConfigMgr::CMD_LINE );
    return 1;
  }
  config.activate_config( ConfigMgr::PHASE_I );

  snmpDatabaseObj * theDatabase = NULL;
  try {
    theDatabase = snmpDatabaseObj::openSnmpDatabaseObj( "/var/opensnmp" );
  }
  catch( ... ) {
    assert(theDatabase==NULL);
  }
  if( theDatabase == NULL ) {
    cerr << "Could not open database in" << endl;
    exit( 99 );
  }

  // check config file.
  if( config.parse_config_file() ) {
    ERR( "error parsing configuration file '" <<
	 config.get_config_file_name() << "'." );
    config.print_help( ConfigMgr::FILE );
    return 1;
  }

  config.activate_config( ConfigMgr::PHASE_II );

  return 0;
}
