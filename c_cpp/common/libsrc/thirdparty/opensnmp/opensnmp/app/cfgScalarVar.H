#ifndef SCALAR_VAR_OPTIONS_H
#define SCALAR_VAR_OPTIONS_H


#include "configOption.H"


class ScalarVarOptions : public ConfigOptions {

public:

  ScalarVarOptions( void );
  ~ScalarVarOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );


private:

  ScalarVarOptions( const ScalarVarOptions & ); // not implemented
  ScalarVarOptions & operator=( const ScalarVarOptions & ); // not implemented

};


#endif // DEBUG_OPTIONS_H
