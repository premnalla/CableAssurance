#ifndef DEBUG_OPTIONS_H
#define DEBUG_OPTIONS_H


#include "configOption.H"


class DebugOptions : public ConfigOptions {

public:

  DebugOptions( void );
  ~DebugOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  DebugOptions( const DebugOptions & ); // not implemented
  DebugOptions & operator=( const DebugOptions & ); // not implemented

};


#endif // DEBUG_OPTIONS_H
