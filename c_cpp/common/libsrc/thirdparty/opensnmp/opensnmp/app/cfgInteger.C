#include "config.h"
#include "debug.H"
#include "cfgInteger.H"

IntegerOption::IntegerOption( const string s, const string l, int & i,
			      const string h )
  : var( i )
{
  short_option = s;
  long_option = l;
  help = h;
}

IntegerOption::~IntegerOption( void )
{
}

int
IntegerOption::registration_callback( ConfigMgr & mgr )
{
  if( ! short_option.empty() )
    mgr.register_option( this, short_option, (void*)0, 1, 1 );

  if( ! long_option.empty() )
    mgr.register_option( this, long_option, (void*)0, 1, 1 );

  return 0; // success
}

int
IntegerOption::option_callback( const string & /*token*/, void* data,
				std::deque<string> & parms )
{
  var = atoi( parms[0].c_str() );

  return 0; // success
}

void
IntegerOption::option_help( const string &, void* data, std::ostream& os )
{
  os <<    " <number>           : " << help << std::endl;
}
