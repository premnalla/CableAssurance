#include "config.h"
#include "debug.H"
#include "cfgSnmpEngine.H"
#include "OctetString.H"

SnmpEngineOptions::SnmpEngineOptions( snmpEngine * & engine )
  : theEngine( engine )
{
}

SnmpEngineOptions::~SnmpEngineOptions( void )
{
}

int
SnmpEngineOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "-E", (void*)0, 1, 1 );
  mgr.register_option( this, "--engine-id", (void*)0, 1, 1 );

  return 0; // success
}

int
SnmpEngineOptions::option_callback( const string & /*token*/, void* data,
			      deque<string> & parms )
{
  OctetString *newEngID = new OctetString();
  // allow for hexidecimal (i.e. '0x' prefix)
  newEngID->fromString(parms.front());
  if ( (newEngID->size() < 5) || (newEngID->size() >32) ) {
	cerr << "error: invalid new engine ID, failing\n";
	return(1);
  }

  theEngine->set_snmpEngineId_noUpdate(newEngID);

  return 0; // success
}

void
SnmpEngineOptions::option_help( const string &, void* data, ostream& os )
{
  os <<
    "  new_engine_ID  : change this (local) engine's ID to new_engine_ID,\n" <<
    "                      but only for this use! (non-persistent)\n";
}
