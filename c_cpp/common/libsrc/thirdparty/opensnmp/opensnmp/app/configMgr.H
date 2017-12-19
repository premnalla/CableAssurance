#ifndef CONFIG_MGR_H
#define CONFIG_MGR_H

#include <map>
#include <deque>

#include "debug.H"

class ConfigOptions;
class ConfigStruct;

class ConfigMgr {

public:

  static const int help_return;

  // ctor - pass in default path to config file
  ConfigMgr( bool registerDefaultOptions = true,
	     string default_file_name = "" );
  // dtor
  virtual ~ConfigMgr();

  // return name of config file;
  const string & get_config_file_name( void ) {
    return file_name; }

  // parse command line options. returns 0 if successful.
  // argcount will be set to the index of the first unprocessed
  // command line argument.
  int parse_command_line( int & argc, char** & argv, int & argcount );
  int parse_command_line( int & argc, char** & argv ) {
	int x; return parse_command_line( argc, argv, x ); };

  // parse configuration file options. returns 0 if successful.
  int parse_config_file( void );

  // act on parsed options
  enum { PHASE_I, PHASE_II };
  int activate_config( int phase );

  // print help for registered options
  enum { CMD_LINE, FILE };
  void print_help( int source );

  // register a configuration option.
  // calls opt->registration_callback();
  // returns 0 if successful.
  int register_option( ConfigOptions * );

  // pair token with option.
  // usually called from within CmdLineOption::registration_callback()
  int register_option( ConfigOptions *, const string & token,
		       void * user_data, int min_parm = 0, int max_parms = 0 );

private:

  // config file name
  string file_name;

  // container for registered options
  std::map< string, ConfigStruct * > options;

  // container for parsed command line options
  //multimap< string, string >;

  //container for parsed config file options
  std::map< std::string, std::deque<std::string>* > config_tokens;

  DEBUGDECLARE(debugObj);
  DEBUG_ERR_WARN_FLOW_DETAIL(debugObj);

  ConfigMgr( void ); // not implemented
  ConfigMgr( const ConfigMgr & ); // not implemented
  ConfigMgr& operator=( const ConfigMgr& ); // not implemented
};

#endif // CONFIG_MGR_H
