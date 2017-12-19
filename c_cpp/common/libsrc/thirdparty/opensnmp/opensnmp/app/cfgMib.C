#include "config.h"
#include "debug.H"
#include "cfgMib.H"
#include "mibManager.H"

static string default_mibs = "SNMPv2-MIB:SNMP-VIEW-BASED-ACM-MIB:SNMP-USER-BASED-SM-MIB";

// xxx-rks : environment variables? OPENSNMP_MIBS, OPENSNMP_MIBDIRS?

MibOptions::MibOptions( string mibs )
{
  // load default MIBS
  // xxx-rks : what if the user explicitly doesn't want one of these
  //           mibs?
  if( mibs.empty() )
    mibs = default_mibs;
  MibManager::instance().load_modules( mibs );
}

MibOptions::~MibOptions( void )
{
}

int
MibOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "-m", (void*)1, 1, 1 );
  mgr.register_option( this, "--mibs", (void*)1, 1, 1 );
  mgr.register_option( this, "Load_MIBS", (void*)1 );

  mgr.register_option( this, "-M", (void*)2, 1, 1 );
  mgr.register_option( this, "--mib-dirs", (void*)2, 1, 1 );
  mgr.register_option( this, "MIB_Path", (void*)2 );

  mgr.register_option( this, "--list-default-mibs", (void*)3, 0, 0 );

  return 0; // success
}

int
MibOptions::option_callback( const string & /*token*/, void* data,
			     std::deque<string> & parms )
{
  switch( (int)data ) {

  case 1: // -m, --mibs
    MibManager::instance().load_modules( parms.front() );
    break;

  case 2: // -M, --mibdirs
    MibManager::instance().add_path( parms.front() );
    break;

  case 3: // --list-default-mibs
    std::cerr << "Default mibs: " << default_mibs << std::endl;
    break;

  default:
    break;
  }

  return 0; // success
}

void
MibOptions::option_help( const string &, void* data, std::ostream& os )
{
  switch( (int)data ) {

  case 1: // -m, --mibs
    os << " <MIBS>  load MIBS instead the default mib list.\n"
       << "                    : multiple mibs should be seperated by\n"
       << "                      a semi-colon (':'). If the first character\n"
       << "                      is a '+', MIBS will be appended to the\n"
       << "                      default list instead of replacing it.\n";
    break;

  case 2: // -M, --mibdirs
    os << " <MIBDIRS>  use MIBDIRS as the location to look for mibs.\n"
       << "                    : multiple directories should be seperated by\n"
       << "                      a semi-colon (':'). If the first character\n"
       << "                      is a '+', MIBDIRSS will be appended to the\n"
       << "                      default list instead of replacing it.\n";
    break;

  case 3: // --list-default-mibs
    os << "  will list the default MIBS\n";
    break;

  default:
    break;
  }
}
