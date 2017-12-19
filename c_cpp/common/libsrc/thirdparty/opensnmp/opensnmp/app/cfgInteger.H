#ifndef INTEGER_OPTIONS_H
#define INTEGER_OPTIONS_H


#include "configOption.H"


class IntegerOption : public ConfigOptions {

public:

  // Class IntegerOption will handle setting an integer value from
  //   the command line. Two command line options are supported (typically
  //   a short form and a long form, e.g. "-p" and "--port"). A reference
  //   to the variable to be set is passed to the ctor; it should already be
  //   initialized to any desired default value.
  //   
  IntegerOption( const string short_option, const string long_option,
		 int & variable, const string help );
  ~IntegerOption( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  IntegerOption( void ); // not implemented
  IntegerOption( const IntegerOption & ); // not implemented
  IntegerOption & operator=( const IntegerOption & ); // not implemented

  string short_option, long_option, help;
  int & var;
};


#endif // INTEGER_OPTIONS_H
