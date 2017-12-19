#ifndef MIB_OPTIONS_H
#define MIB_OPTIONS_H


#include "configOption.H"


class MibOptions : public ConfigOptions {

public:

  MibOptions( string initial_mibs = "" );
  ~MibOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  MibOptions( const MibOptions & ); // not implemented
  MibOptions & operator=( const MibOptions & ); // not implemented

};


#endif // MIB_OPTIONS_H
