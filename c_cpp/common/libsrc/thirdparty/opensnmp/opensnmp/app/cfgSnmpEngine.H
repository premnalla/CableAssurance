#ifndef ENGINE_OPTIONS_H
#define ENGINE_OPTIONS_H


#include "configOption.H"
#include "snmpEngine.H"

class SnmpEngineOptions : public ConfigOptions {

public:

  // Class SnmpEngineOption will handle setting an engine id from
  //   the command line.
  //   
  SnmpEngineOptions( snmpEngine * & engine );
  ~SnmpEngineOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    ostream& os );

private:

  SnmpEngineOptions( void ); // not implemented
  SnmpEngineOptions( const SnmpEngineOptions & ); // not implemented
  SnmpEngineOptions & operator=( const SnmpEngineOptions & ); // not implemented

  snmpEngine * & theEngine;
};


#endif // ENGINE_OPTIONS_H
