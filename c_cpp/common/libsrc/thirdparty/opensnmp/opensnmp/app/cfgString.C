#include "config.h"
#include "debug.H"
#include "cfgString.H"

StringOption::StringOption( const string s, const string l, string & str,
			    const string h )
  : var( str )
{
  short_option = s;
  long_option = l;
  help = h;
}

StringOption::~StringOption( void )
{
}

int
StringOption::registration_callback( ConfigMgr & mgr )
{
  if( ! short_option.empty() )
    mgr.register_option( this, short_option, (void*)0, 1, 1 );

  if( ! long_option.empty() )
    mgr.register_option( this, long_option, (void*)0, 1, 1 );

  return 0; // success
}

int
StringOption::option_callback( const string & /*token*/, void* data,
			       std::deque<string> & parms )
{
  var = parms[0];

  return 0; // success
}

void
StringOption::option_help( const string &, void* data, std::ostream& os )
{
  os << " <number>           : " << help << std::endl;
}
