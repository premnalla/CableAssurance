#ifndef STRING_OPTIONS_H
#define STRING_OPTIONS_H


#include "configOption.H"


class StringOption : public ConfigOptions {

public:

  // Class StringOption will handle setting an string value from
  //   the command line. Two command line options are supported (typically
  //   a short form and a long form, e.g. "-p" and "--port"). A reference
  //   to the variable to be set is passed to the ctor; it should already be
  //   initialized to any desired default value.
  //   
  StringOption( const string short_option, const string long_option,
		string & str, const string help );
  ~StringOption( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  StringOption( void ); // not implemented
  StringOption( const StringOption & ); // not implemented
  StringOption & operator=( const StringOption & ); // not implemented

  string short_option, long_option, help;
  string & var;
};


#endif // STRING_OPTIONS_H
