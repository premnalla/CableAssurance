#ifndef SNMP_CFG_HELP_H
#define SNMP_CFG_HELP_H


#include "configOption.H"

class cfgHelp : public ConfigOptions {

public:

  cfgHelp( void );
  ~cfgHelp( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual int option_finished( void );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

};


#endif // SNMP_CFG_HELP_H
