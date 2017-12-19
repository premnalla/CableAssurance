#ifndef PERSISTENT_OPTIONS_H
#define PERSISTENT_OPTIONS_H


#include "configOption.H"


class PersistentOptions : public ConfigOptions {

public:

  PersistentOptions( void );
  ~PersistentOptions( void );

  virtual int registration_callback( ConfigMgr & );
  virtual int option_callback( const string & token, void* user_data,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void* user_data,
			    std::ostream& os );

private:

  PersistentOptions( const PersistentOptions & ); // not implemented
  PersistentOptions & operator=( const PersistentOptions & ); // not implemented

};


#endif // PERSISTENT_OPTIONS_H
