#include "config.h"
#include "debug.H"
#include "cfgSendPduArgs.H"
#include "snmpUSMData.H"
#include "snmpCrypto.H"


SendPduArgsOptions::SendPduArgsOptions( sendPduArgs & theArgs )
  : sendArgs( theArgs )
{
  option_finished_called = false;
}

SendPduArgsOptions::~SendPduArgsOptions( void )
{
}

int
SendPduArgsOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "-G", (void*)'G', 0, 0 );
  mgr.register_option( this, "--get", (void*)'G', 0, 0 );

  mgr.register_option( this, "-N", (void*)'N', 0, 0 );
  mgr.register_option( this, "--get-next", (void*)'N', 0, 0 );

  mgr.register_option( this, "-W", (void*)'W', 0, 0 );
  mgr.register_option( this, "--walk", (void*)'W', 0, 0 );

  mgr.register_option( this, "-S", (void*)'N', 0, 0 );
  mgr.register_option( this, "--set", (void*)'N', 0, 0 );

  mgr.register_option( this, "-I", (void*)'I', 0, 0 );
  mgr.register_option( this, "--inform", (void*)'I', 0, 0 );

  mgr.register_option( this, "-T", (void*)'T', 0, 0 );
  mgr.register_option( this, "--trap", (void*)'T', 0, 0 );

  mgr.register_option( this, "-A", (void*)'A', 1, 1 );
  mgr.register_option( this, "--auth-pass", (void*)'A', 1, 1 );

  mgr.register_option( this, "-3m", (void*)'3m', 1, 1 );
  mgr.register_option( this, "--auth-main-key", (void*)'3m', 1, 1 );

  mgr.register_option( this, "-3lm", (void*)'3lm', 1, 1 );
  mgr.register_option( this, "--auth-large-main-key", (void*)'3lm', 1, 1 );

  mgr.register_option( this, "-3k", (void*)'3k', 1, 1 );
  mgr.register_option( this, "--auth-localized-key", (void*)'3k', 1, 1 );

  mgr.register_option( this, "-X", (void*)'X', 1, 1 );
  mgr.register_option( this, "--priv-pass", (void*)'X', 1, 1 );

  mgr.register_option( this, "-3M", (void*)'3M', 1, 1 );
  mgr.register_option( this, "--priv-main-key", (void*)'3M', 1, 1 );

  mgr.register_option( this, "-3LM", (void*)'3LM', 1, 1 );
  mgr.register_option( this, "--priv-large-main-key", (void*)'3LM', 1, 1 );

  mgr.register_option( this, "-3K", (void*)'3K', 1, 1 );
  mgr.register_option( this, "--priv-localized-key", (void*)'3K', 1, 1 );

  mgr.register_option( this, "-e", (void*)'e', 1, 1 );
  mgr.register_option( this, "--context-engine-id", (void*)'e', 1, 1 );

  mgr.register_option( this, "-a", (void*)'a', 1, 1 );  
  mgr.register_option( this, "--auth-proto", (void*)'a', 1, 1 );

  mgr.register_option( this, "-x", (void*)'x', 1, 1 );  
  mgr.register_option( this, "--priv-proto", (void*)'x', 1, 1 );

  mgr.register_option( this, "-l", (void*)'l', 1, 1 );
  mgr.register_option( this, "--sec-level", (void*)'l', 1, 1 );

  mgr.register_option( this, "-n", (void*)'n', 1, 1 );
  mgr.register_option( this, "--context-name", (void*)'n', 1, 1 );

  mgr.register_option( this, "-u", (void*)'u', 1, 1 );
  mgr.register_option( this, "--user-name", (void*)'u', 1, 1 );

  mgr.register_option( this, "-t", (void*)'t', 1, 1 );
  mgr.register_option( this, "--timeout", (void*)'t', 1, 1 );

  mgr.register_option( this, "-r", (void*)'r', 1, 1 );
  mgr.register_option( this, "--retries", (void*)'r', 1, 1 );

#if 0
  mgr.register_option( this, "-", (void*)'', ,  );
  mgr.register_option( this, "-", (void*)'', ,  );
  mgr.register_option( this, "-", (void*)'', ,  );
  mgr.register_option( this, "-", (void*)'', ,  );
#endif

  return 0; // success
}

int
SendPduArgsOptions::option_callback( const string & /*token*/, void* data,
				     std::deque<string> & parms )
{
  OctetString *idStr = NULL;
  opensnmpKey *tKey  = NULL;

  switch( (unsigned long)data ) {

  case 'G':
	sendArgs.set_msgPDU(new PDU(BER_TAG_PDU_GET, 0, 0, 0, 0 ));
	break;

  case 'W':
	sendArgs.set_isWalk(true);
	// Notice this falls over into get-next on purpose!

  case 'N':
	sendArgs.set_msgPDU(new PDU(BER_TAG_PDU_GETNEXT, 0, 0, 0, 0 ));
	break;

  case 'S':
	sendArgs.set_msgPDU(new PDU(BER_TAG_PDU_SET, 0, 0, 0, 0 ));
	break;

  case 'T':
	sendArgs.set_msgPDU(new PDU(BER_TAG_PDU_TRAP2, 0, 0, 0, 0 ));
	break;

  case 'I':
	sendArgs.set_msgPDU(new PDU(BER_TAG_PDU_INFORM, 0, 0, 0, 0 ));
	break;

  case 'A':
	sendArgs.set_authKey(new opensnmpKey(parms.front()));
	break;

  case '3m':
        tKey = new opensnmpKey(string(""), opensnmpKey::KEY);
	// will recognize 0x prefix as hexidecimal
	tKey->fromString(parms.front());
        sendArgs.set_authKey(tKey);
	tKey = NULL;
        break;

  case '3lm':
        tKey = new opensnmpKey(string(""), opensnmpKey::LARGEKEY);
	// will regcognize 0x prefix as hexidecimal
	tKey->fromString(parms.front());
        sendArgs.set_authKey(tKey);
	tKey = NULL;
        break;

  case '3k':
        tKey = new opensnmpKey(string(""), opensnmpKey::LOCALIZED_KEY);
	// will recognize 0x prefix as hexidecimal
	tKey->fromString(parms.front());
        sendArgs.set_authKey(tKey);
	tKey = NULL;
        break;

  case 'X':
	sendArgs.set_privKey(new opensnmpKey(parms.front()));
	break;

  case '3M':
        tKey = new opensnmpKey(string(""), opensnmpKey::KEY);
	// will recognize 0x prefix as hexidecimal
	tKey->fromString(parms.front());
	sendArgs.set_privKey(tKey);
	tKey = NULL;
	break;

  case '3LM':
        tKey = new opensnmpKey(string(""), opensnmpKey::LARGEKEY);
	// will recognize 0x prefix as hexidecimal
	tKey->fromString(parms.front());
	sendArgs.set_privKey(tKey);
	tKey = NULL;
	break;

  case '3K':
        tKey = new opensnmpKey(string(""), opensnmpKey::LOCALIZED_KEY);
	// will recognize 0x prefix as hexidecimal
	tKey->fromString(parms.front());
	sendArgs.set_privKey(tKey);
	tKey = NULL;
	break;

  case 'e': {
        idStr = new OctetString();
	// allow for hexidecimal strings (i.e. '0x' prefix)
        idStr->fromString(parms.front());
	sendArgs.set_contextEngId(idStr);
	idStr = NULL;
	break;
  }
  case 'a':
	{
	  char authProto[4];
	  strncpy(authProto, parms.front().c_str(), 3);
	  for (int i=0;i<3;i++) { authProto[i] = toupper(authProto[i]); }
	  authProto[3] = '\0';
	  
	  if (0 == strcmp("SHA", authProto)) {
		sendArgs.set_authOID(new OID(snmpUSMData::usmHMACSHAAuthProtocolOID));
	  }
	  else if (0 == strcmp("MD5", authProto)) {
		sendArgs.set_authOID(new OID(snmpUSMData::usmHMACMD5AuthProtocolOID));
	  }
	  else if (0 == strcmp("NON", authProto)) {
		sendArgs.set_authOID(new OID(snmpUSMData::usmNoAuthProtocolOID));
	  }
	  else {
		cerr << "CG: error: unknown authentication protocol '" <<
		  authProto << "', failing\n";
	  }
          break;
        }

  case 'x':
	{
	  char authProto[6];
	  strncpy(authProto, parms.front().c_str(), 3);
	  for (int i=0;i<3;i++) { authProto[i] = toupper(authProto[i]); }
	  authProto[3] = '\0';
	  
          if (parms.front() == "DES") {
		sendArgs.set_privOID(new OID(snmpUSMData::usmDESPrivProtocolOID));
          }
#ifdef HAVE_AES
          else if (parms.front() == "AES") {
		sendArgs.set_privOID(new OID(snmpUSMData::usmAESPrivProtocolOID));
	  }
#endif
	  else if (parms.front() == "NONE") {
		sendArgs.set_privOID(new OID(snmpUSMData::usmNoPrivProtocolOID));
	  }
	  else {
		cerr << "CG: error: unknown privacy protocol '" <<
		  authProto << "', failing\n";
	  }
          break;
        }

  case 'l':
  {
      const char *s = parms.front().c_str();
      if (strcmp(s,"anp")==0) {
          sendArgs.set_secLevel(new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV));
      } else if (strcmp(s,"ap")==0) {
          sendArgs.set_secLevel(new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV));
      } else { // default
          sendArgs.set_secLevel(new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH));
      }
  }
  break;
	  
  case 'n':
      sendArgs.set_contextName(new OctetString(parms.front()));
      break;

  case 't':
      sendArgs.set_timeout( atoi(parms.front().c_str()) );
      break;
	  
  case 'r':
      sendArgs.set_retries( atoi(parms.front().c_str()) );
      break;
	  
  case 'u':
      sendArgs.set_userName(new OctetString(parms.front()));
      break;
	  
  }

  return 0; // success
}


bool
SendPduArgsOptions::check_key_size(opensnmpKey &key, OID &hashType) {

  // check that key is same as digest length
  if ( opensnmpKey::LOCALIZED_KEY == key.get_keyType()
       &&
       ( (hashType == snmpUSMData::usmHMACMD5AuthProtocolOID  &&
	  key.size() != snmpCrypto::MD5_Length)
	 ||
	 (hashType == snmpUSMData::usmHMACSHAAuthProtocolOID  &&
	  key.size() != snmpCrypto::SHA1_Length) )
       ) {
    cerr << 
      "Error: bad key length given for a localized key,\n" <<
      "    Key Length should be:\n" <<
      "     MD5 : '" << snmpCrypto::MD5_Length << "' octets\n" <<
      "    SHA1 : '" << snmpCrypto::SHA1_Length << "' octets\n\n" <<
      "    failing...\n";
    return false;
  }
  // check that key is at least as long as digest
  // warn if truncation indicated.
  if ( opensnmpKey::KEY == key.get_keyType()
       &&
       ( (hashType == snmpUSMData::usmHMACMD5AuthProtocolOID  &&
	  key.size() != snmpCrypto::MD5_Length)
	 ||
	 (hashType == snmpUSMData::usmHMACSHAAuthProtocolOID  &&
	  key.size() != snmpCrypto::SHA1_Length) )
       ) {
    if ( (hashType == snmpUSMData::usmHMACMD5AuthProtocolOID  &&
	  key.size() > snmpCrypto::MD5_Length)
	 ||
	 (hashType == snmpUSMData::usmHMACSHAAuthProtocolOID  &&
	  key.size() > snmpCrypto::SHA1_Length) )
      {
	cerr << 
	  "Warning: Key length longer than digest.  Key will be\n" <<
	  "    truncated (use long key option to avoid truncation).\n" <<
	  "    truncated to:\n"  <<
	  "     MD5 : '" << snmpCrypto::MD5_Length << "' octets\n" <<
	  "    SHA1 : '" << snmpCrypto::SHA1_Length << "' octets\n";
      }
    else {
      cerr << 
	"Error: key length to short, Key Length should be:\n" <<
	"     MD5 : '" << snmpCrypto::MD5_Length << "' octets\n" <<
	"    SHA1 : '" << snmpCrypto::SHA1_Length << "' octets\n\n" <<
	"    failing...\n";
      return false;
    }
  }
  // check that key is at least as long as digest
  if ( opensnmpKey::LARGEKEY == key.get_keyType()
       &&
       ( (hashType == snmpUSMData::usmHMACMD5AuthProtocolOID  &&
	  key.size() < snmpCrypto::MD5_Length)
	 ||
	 (hashType == snmpUSMData::usmHMACSHAAuthProtocolOID  &&
	  key.size() < snmpCrypto::SHA1_Length) )
       ) {
    cerr << 
      "Error: key length to short for key,\n" <<
      "    Key Length should be at least:\n" <<
      "     MD5 : '" << snmpCrypto::MD5_Length << "' octets\n" <<
      "    SHA1 : '" << snmpCrypto::SHA1_Length << "' octets\n\n" <<
      "    failing...\n";
    return false;
  }

  return true;
} // check_key_size


int
SendPduArgsOptions::option_finished( void )
{
  // This method gets called for evey registered option.  But this
  // checks all options in one call, so just check once.
  if (!option_finished_called) {
    option_finished_called = true;

    if (sendArgs.get_contextEngId() == NULL) {
      // should not default to anything as signal to MP to discover
      sendArgs.set_contextEngId(new OctetString(""));
    }
    if (sendArgs.get_contextName() == NULL) {
      sendArgs.set_contextName(new OctetString(""));
    }
    if (sendArgs.get_secLevel() == NULL) {
      sendArgs.set_secLevel(new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH));
    }
    if (sendArgs.get_userName() == NULL) {
      sendArgs.set_userName(new OctetString("initial"));
    }
    // if we're using a non-persistent user...
    // check and fill in neccessary data.
    if (sendArgs.get_authOID() != NULL) {
      
      // is the msg authenticated/encrypted?
      int secLevel = int(*sendArgs.get_secLevel());
      if ( ( secLevel == SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV) ||
	   ( secLevel == SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV) )
	{
	  if ( (sendArgs.get_authKey() == NULL) || 
	       (*sendArgs.get_authOID() == snmpUSMData::usmNoAuthProtocolOID) ){
	    cerr << 
	      "authentication protocol entered on command\n" <<
	      "    line with a security level requiring authentication, " <<
	      "but either\n" <<
	      "    no authentication pass phrase or a protocol of none was " <<
	      "given,\n" <<
	      "    failing...\n";
	    return(1);
	  }
	  if ( !check_key_size(*sendArgs.get_authKey(), 
			       *sendArgs.get_authOID()) ) {
	    return(1);
	  }

	  if ( secLevel == SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV ) {
	    if ( sendArgs.get_privKey() == NULL ) {
	      sendArgs.set_privKey(new opensnmpKey(*sendArgs.get_authKey()));
	      if (sendArgs.get_privOID() == NULL) 
		sendArgs.set_privOID(new OID(snmpUSMData::usmDESPrivProtocolOID));
	      cerr << "Warning: creating a new user with privacy.\n"
		   << "         No privacy pass phrase given, using\n"
		   << "         DES and auth pass phrase for privacy!";
	    }
	    // check privacy key length. Note: not checking length if
	    // using the auth key because it was already checked.
	    else if ( !check_key_size(*sendArgs.get_privKey(), 
				      *sendArgs.get_authOID()) ) {
	      return(1);
	    }
	  }
	  // just fill empty values
	  else if ( secLevel == SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV) {
	    if (sendArgs.get_privKey() == NULL) 
	      sendArgs.set_privKey(new opensnmpKey(""));
	    if (sendArgs.get_privOID() == NULL) 
	      sendArgs.set_privOID(new OID(snmpUSMData::usmNoPrivProtocolOID));
	  }
	}
      // new user, but msg not auth/encrypt, so make sure we have dummy values.
      else {
	if (sendArgs.get_authKey() == NULL)
	  sendArgs.set_authKey(new opensnmpKey(""));
	if (sendArgs.get_privOID() == NULL) 
	  sendArgs.set_privOID(new OID(snmpUSMData::usmNoPrivProtocolOID));
	if (sendArgs.get_privKey() == NULL) 
	  sendArgs.set_privKey(new opensnmpKey(""));
      }
    }
  }

  return 0;
} // option_finished

void
SendPduArgsOptions::option_help( const string &, void* data, std::ostream& os )
{
  // show help onextwo at a time

  switch( (unsigned long)data ) {

  case 'G':
    os << " : send a get PDU.\n";
    break;
  case 'W':
    os << " : walk (a series of get-next PDU's.\n";
    break;
  case 'N':
    os << " : send a get-next PDU.\n";
    break;
  case 'S':
    os << " : send a set PDU.\n";
    break;
  case 'T':
    os << " : send a trap PDU.\n";
    break;
  case 'I':
    os << " : send a inform PDU.\n";
    break;
  case 'A':
    os << " (string)  : authentication pass phrase (string)\n";
    break;
  case '3m':
    os << " (string)  : authentication main key (string or 0xhexstring, truncated to digest size)\n";
    break;
  case '3lm':
    os << " (string)  : authentication main key (string or 0xhexstring, not truncated)\n";
    break;
  case '3k':
    os << " (string)  : authentication localized key (string or 0xhexstring, truncated to digest size)\n";
    break;
  case 'X':
    os << 
      " (string)  : privacy pass phrase, (string)\n";
    break;
  case '3M':
    os << 
      " (string)  : privacy main key, (string or 0xhexstring)\n";
    break;
  case '3LM':
    os << 
      " (string)  : privacy main key, (string or 0xhexstring, not truncated)\n";
    break;
  case '3K':
    os << 
      " (string)  : privacy localized key, (string or 0xhexstring)\n";
    break;
  case 'e':
    os << " (string)  : context engine ID msg is being sent to.\n";
    break;
  case 'a':
    os << 
      " (MD5|SHA|NONE)  : use authentication protocol MD5, SHA, or NONE\n" <<
      "                   FYI, This also indicates that the command line\n" <<
      "                   info will be used to create a non-persistent\n" <<
      "                   user in the USM Table!\n";
    break;
  case 'x':
    os << 
      " (DES|AES|NONE)  : use privacy protocol DES, AES, or NONE\n" <<
      "                   FYI, This also indicates that the command line\n" <<
      "                   info will be used to create a non-persistent\n" <<
      "                   user in the USM Table!\n";
    break;
  case 'l':
    os << 
      " security_level  : security level to send at, valid values are:\n" <<
      "                   'ap': authPriv, 'anp': authNoPriv, and \n" <<
      "                   'nanp': noAuthNoPriv, default is 'nanp'\n";
    break;
  case 'n':
    os << " (string)  : string is context name to send to, default is \"\"\n";
    break;
  case 'u':
    os << " (string)  : string is user name to send as\n";
    break;
  default:
    break;
  }

} // option_help
