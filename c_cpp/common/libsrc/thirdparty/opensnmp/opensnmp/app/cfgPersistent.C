#include "config.h"
#include "cfgPersistent.H"

#include "snmpDatabaseObj.H"

PersistentOptions::PersistentOptions( void )
{
}

PersistentOptions::~PersistentOptions( void )
{
}

int
PersistentOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "-P", (void*)'P', 1, 1 );
  mgr.register_option( this, "--persistent-dir", (void*)'p', 1, 1 );

  return 0; // success
}

int
PersistentOptions::option_callback( const string & /*token*/, void* /*data*/,
				   deque<string> & parms )
{
  snmpDatabaseObj::getSnmpDatabaseObj()->dbPath = parms.front();
  return 0; // success
}

void
PersistentOptions::option_help( const string & /*token*/, void* /*data*/,
			       ostream& os )
{
  os <<
    " <directory>\n" <<
    "                    : directory containing persistent database;\n" <<
    "                      default is " << snmpDatabaseObj::dbPath << endl;
}
