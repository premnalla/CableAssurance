#ifndef CMD_LINE_OPTION_H
#define CMD_LINE_OPTION_H

#include <iostream>
#include <deque>
#include <string>

#include "configMgr.H"

class ConfigOptions {

public:

  ConfigOptions( void ) {};
  virtual ~ConfigOptions( void ) {};

  // callback to register parameters for each token handled by
  // this object. For each option, call
  //
  //    mgr.register_option( this, user_data, min_parms, max_parms )
  //
  // The user_data will be passed as a parameter to the option_callback.
  // The min_parms and max_parms should indicate the minimum and
  // maximum number of parameters that may occur after the token.
  //
  virtual int registration_callback( ConfigMgr & ) = 0;

  virtual int option_callback( const string & token,
			       void * userData,
			       std::deque<string> & parms ) = 0;

  // option_finished will be called after all command line options
  // have been processed. This allows for check for for any
  // required argument. Returns 0 for success, anything else
  // indicates an error.
  virtual int option_finished( void ) { return 0; }

  // print help for the specified token to the specified ostream.
  virtual void option_help( const string & token, void * user_data,
			    std::ostream& os ) = 0;

protected:


private:

  ConfigOptions( const ConfigOptions& ); // not allowed
  ConfigOptions& operator=( const ConfigOptions& ); // not allowed

};


#endif // CMD_LINE_OPTION_H
