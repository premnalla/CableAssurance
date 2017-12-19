#ifndef SEND_PDU_ARGS_OPTIONS_H
#define SEND_PDU_ARGS_OPTIONS_H


#include "configOption.H"
#include "sendPduArgs.H"

class SendPduArgsOptions : public ConfigOptions {

public:

  // Class SendPduArgsOptions will handle setting most CG options from
  //   the command line. 
  //   
  SendPduArgsOptions( sendPduArgs & );
  ~SendPduArgsOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual int option_finished( void );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:
  bool option_finished_called;

  SendPduArgsOptions( void ); // not implemented
  SendPduArgsOptions( const SendPduArgsOptions & ); // not implemented
  SendPduArgsOptions & operator=( const SendPduArgsOptions & ); // not implemented

  string short_option, long_option, help;
  sendPduArgs & sendArgs;
  virtual bool SendPduArgsOptions::check_key_size(opensnmpKey &key, 
						  OID         &hashType);

};


#endif // SEND_PDU_ARGS_OPTIONS_H
