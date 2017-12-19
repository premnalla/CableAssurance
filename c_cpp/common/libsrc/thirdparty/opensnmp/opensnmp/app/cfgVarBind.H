#ifndef VARBIND_OPTIONS_H
#define VARBIND_OPTIONS_H


#include "configOption.H"
#include "VarBindList.H"

class VarBindOptions : public ConfigOptions {

public:

  // Class VarBindOption will handle setting an VarBind (OID, type,value)
  //   from the command line.
  //   
  VarBindOptions( VarBindList * & vblist );
  ~VarBindOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  VarBindOptions( void ); // not implemented
  VarBindOptions( const VarBindOptions & ); // not implemented
  VarBindOptions & operator=( const VarBindOptions & ); // not implemented

  VarBindList *  & vbargs;
};


#endif // VARBIND_OPTIONS_H
