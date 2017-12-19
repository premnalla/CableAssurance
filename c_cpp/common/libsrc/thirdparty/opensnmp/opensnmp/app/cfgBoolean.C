#include "config.h"
#include "debug.H"
#include "cfgBoolean.H"

BooleanOption::BooleanOption( const string s, const string l, bool & i,
			      const string h )
  : var( i )
{
  short_option = s;
  long_option = l;
  help = h;
}

BooleanOption::~BooleanOption( void )
{
}

int
BooleanOption::registration_callback( ConfigMgr & mgr )
{
  if( ! short_option.empty() )
    mgr.register_option( this, short_option, (void*)0, 1, 1 );

  if( ! long_option.empty() )
    mgr.register_option( this, long_option, (void*)0, 1, 1 );

  return 0; // success
}

int
BooleanOption::option_callback( const string & /*token*/, void* data,
				std::deque<string> & parms )
{
  var = not (var);
  return 0; // success
}

void
BooleanOption::option_help( const string &, void* data, std::ostream& os )
{
  os <<    " <switch>           : " << help << ", default '" << var << "'\n";
}
