#include "debug.H"
#include "cfgHelp.H"
#include "configMgr.H"

cfgHelp::cfgHelp()
{
}

cfgHelp::~cfgHelp( void )
{
}

int
cfgHelp::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "-h", (void*)'h', 0, 0 );
  mgr.register_option( this, "--help", (void*)'h', 0, 0 );
  mgr.register_option( this, "-H", (void*)'h', 0, 0 );
  mgr.register_option( this, "--Help", (void*)'h', 0, 0 );

  return 0; // success
}

int
cfgHelp::option_callback( const string & /*token*/, void* data,
			  std::deque<string> & parms )
{
  return (ConfigMgr::help_return);
}

int
cfgHelp::option_finished( void )
{
  return 0;
}

void
cfgHelp::option_help( const string &, void* data, std::ostream& os )
{
  os << " : outputs this printout\n";
}
