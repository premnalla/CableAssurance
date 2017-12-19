#include "config.h"
#include <iostream>
#include <fstream>

#include "configMgr.H"
#include "configOption.H"
#include "opensnmpUtilities.H"
// standard command line options for library
#include "cfgDebug.H"
#include "cfgPersistent.H"
#include "cfgMib.H"

using std::cerr;
using std::endl;

class ConfigStruct {
public:
  ConfigStruct( ConfigOptions * opt, void * user_data,
		 int min_parms, int max_parms )
    : opt( opt ), data(  user_data ),
      min( min_parms ), optional( max_parms - min_parms ) {};
  ~ConfigStruct( void ) {};

  ConfigOptions * opt;
  void * data;
  int min;
  int optional;

private: // these methods not implemented
  ConfigStruct( void );
  ConfigStruct( const ConfigStruct&);
  ConfigStruct& operator=( const ConfigStruct& );
};

const int ConfigMgr::help_return = -100;

ConfigMgr::ConfigMgr( bool registerDefaults, string default_file_name )
{
  DEBUGCREATE(debugObj, "ConfigMgr:clo" );

  this->file_name = default_file_name;

  if( registerDefaults ) {
    register_option( new DebugOptions() );
    register_option( new PersistentOptions() );
    register_option( new MibOptions() );
  }
}

ConfigMgr::~ConfigMgr() {
  DEBUGDESTROY(this->debugObj);
}

int
ConfigMgr::parse_command_line( int& argc, char**& argv, int & argcount )
{
  DEBUGCREATE_L(debugObj, "ConfigMgr:clo" );

  // parse cmd line
  if (argc <= 1)
    return 0; // success, nothing to parse

  argcount = 1;
  int rc;
  string token;
  ConfigStruct *opt_data;
  std::map< string, ConfigStruct* >::iterator it;
  std::deque< string > que;
  que.clear();

  while(argcount < argc) {

    // options should all start with '-'; single '-' indicates end of options
    if( ( argv[argcount][0] != '-' ) || ( argv[argcount][1] == 0 ) )
      break;

    // -? = help
    if( argv[argcount][1] == '?' ) {
      print_help( 0 );
      return -1;
    }

    // look for token in map
    token = argv[argcount++];
    it = options.find( token );
    if( it == options.end() ) {
      cerr << "Unknown option " << token << endl;
      return -1;
    }
    opt_data = it->second;

    // check for minimum no. of parameters
    if((argcount + opt_data->min) > argc) {  // min. no arguments options
      cerr << "not enough parameters for " << token << endl;
      return -1;
    }
    for( int i = 0; i < opt_data->min; ++i ) {
      if( argv[argcount][0] == '-' ) {
	cerr << "not enough parameters for " << token << endl;
	return -1;
      }
      que.push_back( argv[argcount++] );
    }
    
    // check for maximum no. or parameters
    for( int j = 0; j < opt_data->optional; ++j ) {
      if( ( argcount >= argc ) || ( argv[argcount][0] == '-' ) )
	break;
      que.push_back( argv[argcount++] );
    }
   
    // call callback
    try {
      rc = opt_data->opt->option_callback( token, opt_data->data, que );
      if (rc == this->help_return) {
	print_help( 0 );
	return rc;
      }
    }
    catch(...) {
      rc = -1;
      cerr << "unknown error processing " << token << endl;
    }
    if( rc )
      return rc;

    // clear que
    que.clear();

  } // while argcount < argc

  // xxx-rks: should we shuffle argc & argv?

  // check for missing required parameters or other errors
  it = options.begin();
  do {
    if( it->second->opt->option_finished() != 0 ) //error
	  return 1;
  } while( ++it != options.end() );

  return 0; // success
}

int
ConfigMgr::parse_config_file( void )
{
  const int MAX_LINE_LEN = 120;
  // +1 to allow for newline char
  const int LINE_BUF_SIZE = (MAX_LINE_LEN + 1 );

  // parse config file
  flow( "ConfigMgr::parse_config_file()" );

  std::ifstream theFile( file_name.c_str() );
  if( theFile.fail() ) {
    cerr << "Could not open configuration file '" << file_name << "'" << endl;
    return 1;
  }

  detail( "parsing configuration file: " + file_name );

  int lines = 0;
  char buf[ LINE_BUF_SIZE ];
  string line, token;
  std::deque<string> tokens;
  char* strtok_info;

  while( true ) {

    // increment line count and try to read a line; break on error
    ++lines;
    theFile.getline( buf, LINE_BUF_SIZE );
    if( theFile.fail() )
      break;

    // skip comment lines
    if( buf[0] == '#' )
      continue;

    // check line for comments, and truncate them if found
    char *pos = strchr( buf, '#' );
    if( pos )
      *pos = 0;

    detail( " line " + OPENSNMP_UTILITIES::intToString(lines) + " '" 
	    + buf + "'" );

    pos = strtok_r( buf, " \t\n", &strtok_info );
    while( pos ) {
      detail( "  token '" + string(pos) + "'" );
      // xxx-rks : combine parsing file and parsing tokens,
      //           so errors can have line numbers?
      tokens.push_back( pos );
      pos = strtok_r( NULL, " \t\n", &strtok_info );
    }
  }
  if( ! theFile.eof() ) {
    cerr << " error reading configuration file around line "
	 << lines << endl << " (Does a line exceed "
	 << MAX_LINE_LEN << " characters?)" << endl;
    return 1; // error
  }

  //------------------------------------------------------------
  ConfigStruct *opt_data = NULL;
  std::map< string, ConfigStruct* >::iterator it;
  std::deque<string> current;
  int rc, depth = 0;

  while( ! tokens.empty() ) {

    // grab next token
    token = tokens.front();
    tokens.pop_front();
    detail( "token: " + token );

    // if no current option, this should be a new one
    if( opt_data == NULL ) {

      // look for token in map
      it = options.find( token );
      if( it == options.end() ) {
	cerr << "Unknown option " << token << endl;
	return 1;
      }
      opt_data = it->second;
      continue;
    }

    if( token == "{" ) {
      ++depth;
      if( depth > 1 ) {
	detail( "  nested parameter level " + 
		OPENSNMP_UTILITIES::intToString(depth) );
	current.push_back( token );
      }
    }
    else if( token == "}" ) {
      if( depth == 1 ) {
	detail( " end of option" );
   
	// call callback
	try {
	  rc = opt_data->opt->option_callback( it->first, opt_data->data,
					       current );
	}
	catch(...) {
	  rc = -1;
	  cerr << "unknown error processing " << token << endl;
	}
	if( rc )
	  return rc;

	// set up for next option
	current.clear();
	opt_data = NULL;
      }
      else {
	detail( " end of nested level " + 
		OPENSNMP_UTILITIES::intToString(depth) );
	current.push_back( token );
      }
      if( --depth < 0 )
	break; // mismatched braces
    }
    else
      current.push_back( token );

  }

  if( depth != 0 ) {
    cerr << "mismatched braces in configuration file" << endl;
    return 1; // error
  }

  return 0; // success

}

void
ConfigMgr::print_help( int source )
{
  cerr << "-? : this help text." << endl;

  std::map< string, ConfigStruct* >::iterator it = options.begin();
  do {
    if( source == CMD_LINE ) {
      if( it->first[0] != '-' )
	continue;
    } else if( it->first[0] == '-' )
      continue;
    cerr << "  " << it->first << ' ';
    it->second->opt->option_help( it->first, it->second->data, cerr );
  } while( ++it != options.end() );
}

int
ConfigMgr::activate_config( int phase )
{
  // xxx-rks
  cerr << "no activation yet" << endl;
  return 0;
}

int
ConfigMgr::register_option( ConfigOptions * opt )
{
  return opt->registration_callback( *this );
}

int
ConfigMgr::register_option( ConfigOptions * opt, const string & token,
			    void* user_data, int min_parms, int max_parms )
{
  if( opt == NULL )
    return 1;

  // tokens must start with '-'
  //if( token[0] != '-' )
  //  return 2;

  // max can't exceed min
  if( min_parms > max_parms )
    return 3;

  if( options[ token ] != NULL ) {
    cerr << "duplicate token '" << token << "' registered" << endl;
    return 4;
  }

  options[ token ] = new ConfigStruct( opt, user_data, min_parms, max_parms );

  return 0; // success
}
