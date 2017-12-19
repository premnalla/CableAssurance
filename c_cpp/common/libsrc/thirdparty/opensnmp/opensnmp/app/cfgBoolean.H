#ifndef BOOLEAN_OPTIONS_H
#define BOOLEAN_OPTIONS_H


#include "configOption.H"


class BooleanOption : public ConfigOptions {

public:

  // Class BooleanOption will handle setting an boolean value from
  //   the command line. Two command line options are supported (typically
  //   a short form and a long form, e.g. "-p" and "--port"). A reference
  //   to the variable to be set is passed to the ctor; it should already be
  //   initialized to any desired default value.
  //   
  BooleanOption( const string short_option, const string long_option,
		 bool & variable, const string help );
  ~BooleanOption( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  BooleanOption( void ); // not implemented
  BooleanOption( const BooleanOption & ); // not implemented
  BooleanOption & operator=( const BooleanOption & ); // not implemented

  string short_option, long_option, help;
  bool & var;
};


#endif // BOOLEAN_OPTIONS_H
