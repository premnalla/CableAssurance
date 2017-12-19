//
// snmpUSMArchObj
//


#include "config.h"
#include "snmpUSMArchObj.H"
#include "snmpRegObj.H"
#include "snmpV3Message.H"
#include "snmpConstants.H"
#include "snmpStats.H"
#include "snmpCRRegisterTable.H"
#include "snmpCRRegisterScalarSet.H"
#include "snmpStorageType.H"
#include "snmpRowStatus.H"
#include "snmpRowStatusColumnAllocator.H"
#include "snmpRowStorageColumnAllocator.H"
#include "snmpPersistentRowManager.H"
#include "snmpRowStatusCheckColsExist.H"
#include "snmpKeyChangeColumnAllocator.H"
#include "usmAddUserMsg.H"
#include "usmDelUserMsg.H"
#include "usmCheckUserMsg.H"
#include "opensnmpKey.H"

#include <stdio.h>
#include <iostream>
#include <string>
#include <typeinfo>

const int snmpUSMArchObj::BUFFER_SIZE       = 2048;
const int snmpUSMArchObj::MESSAGE_OVERHEAD  = 160;
const OID snmpUSMArchObj::usmStatsOID       = OID(".1.3.6.1.6.3.15.1.1");

snmpUSMArchObj::snmpUSMArchObj() : usmStatistics(6) {
  this->fifoPtr = NULL;
  this->crFIFO  = NULL;
  this->mpFIFO  = NULL;
  this->crFIFO  = NULL;

  DEBUGCREATE(usmDebug, "USM");
  DEBUG9(usmDebug, "snmpUSMArchObj Constructor");
} // snmpUSMArchObj (constructor)

snmpUSMArchObj::snmpUSMArchObj(snmpEngine *engPtr) : usmStatistics(6) {
  this->fifoPtr   = NULL;
  this->mpFIFO    = NULL;
  this->crFIFO    = NULL;
  this->enginePtr = engPtr;
  // place random number in pre_salt
  snmpCrypto::generate_random_bytes((unsigned char *)&pre_salt, 4);

  DEBUGCREATE(usmDebug, "USM");
  DEBUG9(usmDebug, "snmpUSMArchObj Constructor");
} // snmpUSMArchObj (constructor)

snmpUSMArchObj::~snmpUSMArchObj()  {
  DEBUG9(usmDebug, "snmpUSMArchObj Deconstructor");
  DEBUGDESTROY(usmDebug);
} // ~snmpUSMArchObj


void
snmpUSMArchObj::AddUserUSM(usmAddUserMsg *addUser) {
  snmpStatusInfo *theStatus = new snmpStatusInfo(true);
  addUser->set_statusInformation(theStatus);

  DEBUG9(usmDebug, "addUserUSM");

  OctetString          *engID = addUser->get_engID();
  OctetString       *userName = addUser->get_userName();
  OID        *authProtocolOID = addUser->get_authProtocolOID();
  OID        *privProtocolOID = addUser->get_privProtocolOID();
  opensnmpKey        *authKey = addUser->get_authKey();
  opensnmpKey        *privKey = addUser->get_privKey(); 
  usmAddUserMsg::AddUserMethod_Enum add = addUser->get_addMethod();
  int               storeType = addUser->get_storageType();
  

  if ( (engID == NULL)           || (userName == NULL)        || 
       (authProtocolOID == NULL) || (privProtocolOID == NULL) || 
       (authKey == NULL)         || (privKey == NULL)        )  {
    DEBUG0(usmDebug, "addUserUSM: Error, incorrect parameter data ('0' " 
	   << "pointer value(s)), failure message returned");
    theStatus->set_success(false);
    theStatus->pushErrorObj
      (new snmpErrorObj(snmpErrorObj::ADD_USM_USER_ERROR, 
			string("invalid parameters")));
  }
  else if ( (storeType != SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE) &&
	    (storeType != SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE) &&
	    (storeType != SNMP_CONSTANTS::SNMP_STORAGE_PERMANENT) &&
	    (storeType != SNMP_CONSTANTS::SNMP_STORAGE_READONLY) ) {
    DEBUG0(usmDebug, "addUserUSM: Error, incorrect parameter data. " 
	   << "The storage type '" << storeType << "' is uknown, "
	   << "failure message returned");
    theStatus->set_success(false);
    theStatus->pushErrorObj
      (new snmpErrorObj(snmpErrorObj::ADD_USM_USER_ERROR, 
			string("invalid parameters")));
  }
  else if ( (add == usmAddUserMsg::DO_NOT_OVER_WRITE_USER) &&
	    (0 != this->usmData.searchUSMTable(engID->clone(), 
					       userName->clone())) ) {
    DEBUG9(usmDebug, "addUserUSM: user '" << string(*userName)
	   << "' found in USM Table and do not over write requested. " 
	   << "Failure message returned");
    theStatus->set_success(false);
    theStatus->pushErrorObj
      (new snmpErrorObj(snmpErrorObj::USM_USER_EXISTS_ERROR, 
			string("user exists and no-overwrite selected")));
  }
  else {
    
    // create storage type
    snmpStorageType *storageTypeCol = new snmpStorageType(storeType);

    // XXX probably should be checking all sorts of things (have an
    // auth oid if you have a priv oid, etc,...) and return
    // appropriate errors.  Caveat Codor

    // get localized auth key from opensnmpKey.
    OctetString *localizedAuthKey = new OctetString();
    if (authKey->size() != 0 && 
        *authProtocolOID != snmpUSMData::usmNoAuthProtocolOID) {
      this->crypto.generate_Kul(*authProtocolOID,
				*engID,
				*authKey,
				localizedAuthKey);
    }

    // get localized priv key from opensnmpKey.
    OctetString *localizedPrivKey = new OctetString();
    if (*privProtocolOID != snmpUSMData::usmNoPrivProtocolOID) {
        opensnmpKey *ppKey = privKey;
        if (ppKey->size() == 0)  ppKey = authKey;
        if (ppKey->size() > 0)
            this->crypto.generate_Kul(*authProtocolOID,
                                      *engID,
                                      *ppKey,
                                      localizedPrivKey);
    } 

    DEBUG9(usmDebug,
	    "addUserUSM: Adding\n" <<
	    "   User   '" << string(*userName) << "'\n" <<
	    "   Eng ID '" << string(*engID)    << "'\n" <<
	    "   auth   '" << string(*authProtocolOID)  << "'\n" <<
	    "   priv   '" << string(*privProtocolOID) << "'\n" <<
	    "   add    '" << add        << "'\n" <<
	    "   storT  '" << storeType  << "'\n");

    // add row, get neccessary data from message.
    this->usmData.addRow(addUser->get_engID(true),    // eng. ID
			 addUser->get_userName(true), // userName
			 userName->clone(),           // userSecurityName

			 addUser->get_authProtocolOID(true),
			 addUser->get_privProtocolOID(true),

			 localizedAuthKey,
			 localizedPrivKey,
			 storageTypeCol);
  }

  if ( DEBUGIF(usmDebug, 20) ) {
    DEBUGN(20, usmDebug, "The USM table is currently:");
    DEBUGN(20, usmDebug, this->usmData.toString());
  }

  this->return_message(addUser);
} // AddUserUSM


void
snmpUSMArchObj::CheckUserUSM(usmCheckUserMsg *checkUser) {
  snmpStatusInfo *theStatus = new snmpStatusInfo(true);
  checkUser->set_statusInformation(theStatus);
  snmpRow *userRow;
 
  DEBUG9(usmDebug, "CheckUserUSM");

  OctetString          *engID = checkUser->get_engID();
  OctetString       *userName = checkUser->get_userName();
  OID        *authProtocolOID = checkUser->get_authProtocolOID();
  OID        *privProtocolOID = checkUser->get_privProtocolOID();
  opensnmpKey        *authKey = checkUser->get_authKey();
  opensnmpKey        *privKey = checkUser->get_privKey(); 

  if ( (engID == NULL)           || (userName == NULL)        || 
       (authProtocolOID == NULL) || (privProtocolOID == NULL) || 
       (authKey == NULL)         || (privKey == NULL)    )  {
    DEBUG0(usmDebug, "checkUserUSM: Error, incorrect parameter data ('0' " 
	   << "pointer value(s)), failure message returned");
    theStatus->set_success(false);
  }
  else if (0 == (userRow = this->usmData.searchUSMTable(engID->clone(), 
							userName->clone())) ) {
    DEBUG9(usmDebug, "checkUserUSM: user not in USM DB, false returned");
    theStatus->set_success(false);
  }
  else {  // check authentication and privacy info, 
          // 
          // This purposely uses the length of the auth key for
          // if-then looping.  This is mainly because the crypto
          // calls use a large amount of CPU so the keys are the
          // last things to be generated and checked.
 
    // check that authentication info matches.
    if (0 != authKey->size()) {
      if (*authProtocolOID != 
	  *(userRow->get_column_OID(snmpUSMData::usmUserAuthProtocolCol))) {
	DEBUG9(usmDebug, "checkUserUSM: Authentication Digest Algorithm " <<
	       "does not match, false returned");
	theStatus->set_success(false);
      }
      else {
	// create localized auth key from pass phrase and compare.
	OctetString localizedAuthKey;
	this->crypto.generate_Kul(*authProtocolOID,
				  *engID,
				  *authKey,
				  &localizedAuthKey);

	if (localizedAuthKey != 
	    *(userRow->get_column_OctetString(snmpUSMData::usmUserAuthKeyCol)))
	  {
	    DEBUG9(usmDebug, "checkUserUSM: Authentication Key " <<
		   "does not match, false returned");
	    theStatus->set_success(false);
	  }
      }
    }
    
    // check that privacy info matches and we're still in success mode.
    if ( (0 != privKey->size()) && (theStatus->get_success()) )  {
      if (*privProtocolOID != 
	  *(userRow->get_column_OID(snmpUSMData::usmUserPrivProtocolCol))) {
	DEBUG9(usmDebug, "checkUserUSM: Privacy Digest Algorithm " <<
	       "does not match, false returned");
	theStatus->set_success(false);
      }
      else {
	// get localized priv key from privKey and compare.
	OctetString localizedPrivKey;
	// authentication hash is used to generate the privacy key
	// Note: this allows checking the priv key without checking
	// the auth key,  
	// XXX: possible security problem?
	this->crypto.generate_Kul
	  ( *(userRow->get_column_OID(snmpUSMData::usmUserAuthProtocolCol)),
	    *engID,
	    *privKey,
	    &localizedPrivKey );

	if (localizedPrivKey != 
	    *(userRow->get_column_OctetString(snmpUSMData::usmUserPrivKeyCol)))
	    {
	      DEBUG9(usmDebug, "checkUserUSM: Privacy Key does " <<
		     "not match, false returned");
	      theStatus->set_success(false);
	    }
      }
    }

  }  // else check auth/privacy info.

  this->return_message(checkUser);
} // CheckUserUSM


void 
snmpUSMArchObj::return_message(snmpMessageObj *theMessage) {
  snmpFIFOObj             *returnFIFO;

  DEBUG9(usmDebug, "return_message");
  // return message to its return FIFO, if it exists.
  if ( NULL != (returnFIFO = theMessage->get_returnFIFO()) ) {
    DEBUG9(usmDebug, "return_message: message being returned to retFIFO");
    returnFIFO->push(theMessage);
  }
  // no return FIFO, check if it is a defaulted message type...
  else if ( (typeid(*theMessage) == typeid(snmpUSMGenerateRequestMsgASI)) ||
	    (typeid(*theMessage) == typeid(snmpUSMGenerateResponseMsgASI)) ||
	    (typeid(*theMessage) == typeid(snmpUSMProcessIncomingASI)) ) {

    if ( (NULL != this->mpFIFO) || 
	 (NULL != (this->mpFIFO = this->theReg.findArchFIFO("MP3"))) ) {
      DEBUG5(usmDebug, "return_message: msg sent to MP3 (def. for msg type)");
      this->mpFIFO->push(theMessage);
    }

  }

  // no known return FIFO.
  else {
    DEBUG0(usmDebug, "return_message: Unknown return fifo for the current message, message deleted");
    delete theMessage;
  }

} // return_message


snmpSNMPErrorObj*
snmpUSMArchObj::processStatsError(usmStatsEnum theError) {
  // get the statistics OID
  OID *errorOID  = new OID(this->usmStatsOID);
  errorOID->append(theError);
  errorOID->append(0); // OID scalers -> .0
  
  // increment and get the statistics new value
  Integer32 *errorValue = new Integer32
    (int(*(usmStatistics.increment(theError))));
  
  // create the varbind list of one varbind representing the error.
  VarBindList *errVBList  = new VarBindList(new VarBind(errorOID, errorValue));

  // return the child object of snmpErrorObj with the VarBindList
  return(new snmpSNMPErrorObj(errVBList, snmpErrorObj::SNMP_ERROR));
} // processStatsError


// This will be encryption. For now, not doing anything
// should have the affect of just sending the Scoped PDU
// unencrypted. 
bool
snmpUSMArchObj::encryptData(snmpSecurityStateReference *userData, 
			    ScopedPDU *sPDU, 
			    OctetString *privParams,
                            unsigned int secEngineBoots,
                            unsigned int secEngineTime) {
  char spdu_buffer[BUFFER_SIZE], *begin_buffer;
  unsigned int  msg_length, iv_len = 8;
  const int max_iv_len = 32;
  char salt_array[max_iv_len], key_salt_array[max_iv_len], iv_array[max_iv_len];
  char *iv_ptr;
  int iv_ptr_len;

  DEBUG9(usmDebug, "encryptData");
  // asn encode scoped pdu
  try {
    begin_buffer = sPDU->asnEncode(spdu_buffer + BUFFER_SIZE - 1);
    begin_buffer++; // Get to the real data location, since encoding
                    // returns the next free byte.
    msg_length = spdu_buffer + BUFFER_SIZE - begin_buffer;
  }  
  catch (snmpStringException &except) {
    DEBUG0(usmDebug, "encryptData: while asn encoding " << 
	   "caught exception '" << except.get_errorMessage() << "', failing");
    return(false);
  }
  catch (...) {
    DEBUG0(usmDebug, "encryptData: while asn encoding " << 
	   "caught UNKNOWN exception, failing");
    return(false);
  }

  if (userData->privProtocol == snmpUSMData::usmDESPrivProtocolOID) {
      int eboots = htonl(int(*this->enginePtr->get_snmpEngineBoots()));
      int psalt  = htonl(this->pre_salt);
      iv_len = 8;
      // get salt value
      memcpy(salt_array, (char *)&eboots, 4);
      memcpy(salt_array + 4, (char *)&psalt, 4);
      this->pre_salt++;

      unsigned int key_copy_len = iv_len;
      userData->privKey.get_string(key_salt_array, 8, &key_copy_len, false);
      if (key_copy_len != iv_len) {
          DEBUG5(usmDebug, "encryptData: Warning: bad copy length while"
                 << " creating salt from key, maybe bad key length?");
      }

      // Finally, XOR and create the iv.
      unsigned int i;
      for (i=0; i<iv_len; i++) {
          iv_array[i] = salt_array[i] ^ key_salt_array[i];
      }
      iv_ptr = salt_array;
      iv_ptr_len = iv_len;
#ifdef HAVE_AES
  } else if (userData->privProtocol == snmpUSMData::usmAESPrivProtocolOID) {
      secEngineBoots = htonl(secEngineBoots);
      secEngineTime = htonl(secEngineTime);
      memcpy(iv_array, &secEngineBoots, 4);
      memcpy(iv_array+4, &secEngineTime, 4);
      unsigned int x = this->pre_salt;
      memcpy(iv_array+8, &x, 4);
      memcpy(iv_array+12, &x, 4);
      this->pre_salt++;

      iv_len = 16;
      // only the last 8 bytes go out in the privParams
      iv_ptr = iv_array+8;
      iv_ptr_len = 8;
#endif
  } else {
      DEBUG5(usmDebug, "encryptData: Warning: unknown privacy type.");
      return false;
  }
  
  privParams->assign(iv_ptr, iv_ptr_len);

//   DEBUG9(usmDebug, "encyptData: key, salt, iv \n";)
//   printf("key_salt_array is \n0x ");
//   for (i=0;i< (int)iv_len;i++)  printf("%02X ", key_salt_array[i] );
//   printf("\n");
//   printf("salt_array is \n0x ");
//   for (i=0;i< (int)iv_len;i++)  printf("%02X ", salt_array[i] );
//   printf("\n");
//   DEBUG9(usmDebug, "privParameters thinks the salt is '" << privParams->toHexString())
//        << "'\n";
//   printf("iv_array is \n0x ");
//   for (i=0;i< (int)iv_len;i++)  printf("%02X ", iv_array[i] );
//   printf("\n\n");

  // encrypt the plaintext data
  OctetString *ciphertext = new OctetString();

  if ( !this->crypto.encrypt(userData->privProtocol,
			     userData->privKey,
			     iv_array, iv_len,
			     begin_buffer, msg_length,
			     ciphertext) ) {
    DEBUG5(usmDebug, "encryptData: Warning: crypto encryption failed.");
    delete ciphertext;
    return (false);
  }
  sPDU->set_encryptedData(ciphertext);
  
//   printf("plaintext is \n0x ");
//   for (i=0;i< (int)msg_length;i++)  printf("%02X ", begin_buffer[i] );
//   printf("\n");
//   DEBUG9(usmDebug, "ciphertext '" << ciphertext->toHexString() << "'\n";)

  // waa...hooo, time to clean up,
  memset(salt_array,     0, iv_len);
  memset(key_salt_array, 0, iv_len);
  memset(iv_array,       0, iv_len);
  memset(begin_buffer,   0, msg_length);

  return (true);
}  // encryptData


// This will be decryption. For now, not doing anything
bool
snmpUSMArchObj::decryptData(snmpSecurityStateReference *userData,
			    USMSecurityParameters      *secParams,
			    char *payload, int payloadLength,
			    string *sPDU_string) {
  bool retVal = true;
  OctetString ciphertext;
  const int max_iv_len = 32;
  int  iv_len = 8;
  char salt_array[max_iv_len], key_salt_array[max_iv_len], iv_array[max_iv_len];
  unsigned int  key_copy_len = max_iv_len, salt_len = max_iv_len;



  DEBUG9(usmDebug, "decryptData");
  try  {
    ciphertext.asnDecode(payload, payloadLength);
  }
  catch (snmpStringException &except) {
    DEBUG0(usmDebug, "decryptData: while asn decoding scoped PDU, caught "
	   << "exception '" << except.get_errorMessage() <<
	   "', failure returned");
    this->enginePtr->stats.increment(SNMP_STATS::SNMP_IN_ASN_PARSE_ERRORS);
    return false;
  }
  catch (...) {
    DEBUG0(usmDebug, "decryptData: while asn decoding scoped PDU caught "
	   << "UNKNOWN exception, failure returned");
    return false;
  }

  if (secParams->get_msgPrivacyParameters(false)->size() != 8) {
    DEBUG0(usmDebug, "decryptData: Privacy Parameters not 8 octets long, "
	   << "failing!");
    return(false);
  }

  if (userData->privProtocol == snmpUSMData::usmDESPrivProtocolOID) {
      iv_len = 8;
      userData->privKey.get_string(key_salt_array, 8, &key_copy_len, false);
      secParams->get_msgPrivacyParameters(false)->
          get_string(salt_array, &salt_len, false);
      
      if ( (int(key_copy_len) != iv_len) || (int(salt_len) != iv_len) ) {
          DEBUG5(usmDebug, "decryptData: Warning bad copy length while creating"
                 << " iv from key and salt, maybe bad key &/or salt length?");
      }
      int i;
      // Finally, XOR and create the iv.
      for (i=0; i<iv_len; i++) {
          iv_array[i] = salt_array[i] ^ key_salt_array[i];
      }
#ifdef HAVE_AES
  } else if (userData->privProtocol == snmpUSMData::usmAESPrivProtocolOID) {
      secParams->get_msgPrivacyParameters(false)->
          get_string(salt_array, &salt_len, false);
      if (salt_len != 8) {
          DEBUG5(usmDebug, "decryptData: Warning bad salt length != 8");
          return false;
      }
      int x = htonl(int(*secParams->get_msgAuthoritativeEngineBoots()));
      iv_len = 16;
      memcpy(iv_array, &x, 4);
      x = htonl(int(*secParams->get_msgAuthoritativeEngineTime()));
      memcpy(iv_array+4, &x, 4);
      memcpy(iv_array+8, salt_array, 8);
#endif
  } else {
      DEBUG5(usmDebug, "decryptData: unknown privacy type.");
      return false;
  }
  
//   DEBUG9(usmDebug, "decyptData: key, salt, iv \n";)
//   printf("key_salt_array is \n0x ");
//   for (i=0;i< (int)iv_len;i++)  printf("%02X ", key_salt_array[i] );
//   printf("\n");
//   printf("salt_array is \n0x ");
//   for (i=0;i< (int)iv_len;i++)  printf("%02X ", salt_array[i] );
//   printf("\n");
//   printf("iv_array is \n0x ");
//   for (i=0;i< (int)iv_len;i++)  printf("%02X ", iv_array[i] );
//   printf("\n\n");

  retVal = this->crypto.decrypt(userData->privProtocol,
				userData->privKey,
				iv_array, iv_len,
				ciphertext,
				sPDU_string);
//   printf("plaintext is \n0x ");
//   char *plain = new char[sPDU_string->size()];
//   memcpy(plain, sPDU_string->data(), sPDU_string->size());
//   for (i=0;i< (int)sPDU_string->size();i++)  printf("%02X ", plain[i] );
//   printf("\n");
//   delete plain;
//   DEBUG9(usmDebug, "ciphertext '" << ciphertext.toHexString() << "'\n";)

  
  // waa...hooo, time to clean up,
  memset(salt_array,     0, iv_len);
  memset(key_salt_array, 0, iv_len);
  memset(iv_array,       0, iv_len);

  return(retVal);
} // decryptData


// This will be authentication. For now, not doing anything
// should have the affect of just sending the Scoped PDU
// unauthenticated (i.e. with just 12 zero value octets for auth parameters) 
bool
snmpUSMArchObj::authenticateMessage(snmpSecurityStateReference *userData, 
				    USMSecurityParameters *secParams, 
				    char *theSerialMsg, int msgLength) {
  // get location in serial message for auth. parameters.
  OctetString *authParams = secParams->get_msgAuthenticationParameters();
  char *authParamsSerialLocation = secParams->get_serialLocation(authParams);
  OctetString keyed_hash;

  DEBUG9(usmDebug, "authenticateMessage");
  // move the serial location past the asn header.
  authParamsSerialLocation = authParamsSerialLocation + 
    asnDataType::asnHeaderLength(authParamsSerialLocation);

  if ( !this->crypto.generate_keyed_hash(userData->authProtocol,
					 userData->authKey,
					 (u_char *)theSerialMsg, 
					 u_int(msgLength),
					 &keyed_hash) ) {
    return(false);
  }
  
  // write hash into message
  keyed_hash.get_string(authParamsSerialLocation, keyed_hash.size(), false);

  // clean up local copy.
  keyed_hash.assign(int(keyed_hash.size()), '\0');

  return (true);
}  // authenticateMessage


// This will check the authentication on a message.
// unauthenticated (i.e. with just 12 zero value octets for auth parameters) 
bool
snmpUSMArchObj::checkAuthentication(snmpSecurityStateReference *userData, 
				    USMSecurityParameters      *secParams, 
				    snmpV3Message              &theMsg) {
  int  authMACLen = 12;
  OctetString authMAC;
  char dummyParams[authMACLen];
  for (int i=0;i<authMACLen;i++) dummyParams[i] = '\0'; // zero'd values

  DEBUG9(usmDebug, "checkAuthentication");
  // get auth. parameters from message, and get serial location
  OctetString *authParams = secParams->get_msgAuthenticationParameters();
  char *authParamsSerial  = secParams->get_serialLocation(authParams);

  // move authParamsSerial past asn header
  authParamsSerial = authParamsSerial + 
    asnDataType::asnHeaderLength(authParamsSerial);

  // copy MAC out of message into authMAC, replace with dummy zero
  // values. 
  authMAC.assign(authParamsSerial, authMACLen);
  memcpy(authParamsSerial, dummyParams, authMACLen);

  // get serial message and length.
  char       *serialMsg = theMsg.get_serialLocation();
  u_int serialMsgLength = asnDataType::asnLengthWithHeader(serialMsg);

  // check the hash
  if (this->crypto.check_keyed_hash(userData->authProtocol,
				    userData->authKey,
				    (u_char *)serialMsg, serialMsgLength,
				    authMAC)) {
    DEBUG9(usmDebug, "checkAuthentication: message is Authentic!");
    return (true);
  }
  else {
    DEBUG9(usmDebug, "checkAuthentication: message is NOT! Authentic");
    return(false);
  }
  
}  // checkAuthentication


// EoP: 3.2,7: process/set the boots and time values
// for the athoritative engine. 
void
snmpUSMArchObj::processBootsTime(USMSecurityParameters *secParams,
				 snmpStatusInfo        *theStatus,
				 bool                   is_auth) {
  OctetString  *msgEngID = secParams->get_msgAuthoritativeEngineID();
  Integer32    *msgBoots = secParams->get_msgAuthoritativeEngineBoots();
  Integer32     *msgTime = secParams->get_msgAuthoritativeEngineTime();
  DEBUG9(usmDebug, "processBootsTime: local engine is '" << 
	 string(*this->enginePtr->get_snmpEngineId()) << "'  msg engine is '" 
	 << string(*msgEngID) << "' or '" << msgEngID->toHexString()
         << "'");
  
  // EoP: 3.2,7, a): if the messages authoritative engine ID is this
  // engine's ID (i.e. this is the authoritative engine)...
  if (*this->enginePtr->get_snmpEngineId() == *msgEngID) {
    if (is_auth) {
      timeval currTime;
      gettimeofday(&currTime, NULL);
      int engineTime = currTime.tv_sec - 
	this->enginePtr->get_engineStartTime().tv_sec;
      
      if ( (int(*this->enginePtr->get_snmpEngineBoots()) >= 2147483647)  ||
	   (int(*this->enginePtr->get_snmpEngineBoots()) != int(*msgBoots)) ||
	   (abs(engineTime - int(*msgTime)) > 150.0)
	   )  {
	// failure usmStatsNotInTimeWindow is incremented, the OID, the value
	// of the incremented counter, AND an indication that the report
	// is to be send with a security level of authNoPriv!!!!!!
	snmpSNMPErrorObj *pError = this->processStatsError
	  (usmStatsNotInTimeWindows);
	pError->set_securityLevel(new Integer32
				  (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV));
	theStatus->set_success(false);
	theStatus->pushErrorObj(pError);
      }
    }
  }
  else {  // this engine is not the authoritative engine
    // EoP: 3.2,7, b): check and update boots, time
    // 1) update local notions of boots, time, & latestTime if
    map<OctetString, usmLCDEntry>::iterator itr_m;

    if ( this->lcdMap.end() != (itr_m = this->lcdMap.find(*msgEngID)) ) {
      // if the current values are unauthenticated guesses, accept the 
      // new values whether auth or not.
      if (!itr_m->second.is_auth) {
	itr_m->second.boots      = int(*msgBoots);
	itr_m->second.time       = int(*msgTime);
	itr_m->second.latestTime = int(*msgTime);
	itr_m->second.is_auth    = is_auth;
      }
      // else current values are authenticated, if incoming is too, time sync.
      else if (is_auth) {
	if ( (int(*msgBoots) > itr_m->second.boots) ||
	     ( (int(*msgBoots) == itr_m->second.boots) &&
	       (int(*msgTime) > itr_m->second.latestTime)
	       )
	     )  {
	  itr_m->second.boots      = int(*msgBoots);
	  itr_m->second.time       = int(*msgTime);
	  itr_m->second.latestTime = int(*msgTime);
	  itr_m->second.is_auth    = is_auth;
	}
      }
      
      // if message is authenticated, fail if it is not in the time window.
      if (is_auth) { 
	// EoP: 3.2,7, b) 2) If any of the following are true, the
	// message is outside of the time window.
	if ( (itr_m->second.boots >= 2147483647)    ||
	     (int(*msgBoots) < itr_m->second.boots) ||
	     ( (int(*msgBoots) == itr_m->second.boots) &&
	       ( (int(*msgTime) + 150) < itr_m->second.time)
	       )
	     )  {
	  theStatus->set_success(false);
	  theStatus->pushErrorObj(this->processStatsError
				  (usmStatsNotInTimeWindows));
	}
      }
    }
    else { // the engine ID is not in the LCD, this should never happen
           // but I'm paranoid and I don't like segfaults. 
           // XXX --maybe this should be an exception?
      DEBUG0(usmDebug, "processBootsTime: ERROR, engine ID not in LCD "
	     << "after athenticating message, this should never happen!");
    }
  }
} // processBootsTime


// look up boots and time in the local engineID <-> boots, time map
void
snmpUSMArchObj::getBootsTime(OctetString *engID, int *boots, int *time) {
  map<OctetString, usmLCDEntry>::iterator itr_m;

  DEBUG9(usmDebug, "getBootsTime");
  // if the msg engine ID is this engine's ID
  if (*this->enginePtr->get_snmpEngineId() == *engID) {
    timeval      currTime;
    gettimeofday(&currTime, NULL);
    *time = currTime.tv_sec - this->enginePtr->get_engineStartTime().tv_sec;
    *boots = int(*(this->enginePtr->get_snmpEngineBoots()));
  }
  // else if the msg engine ID doesn't exist in the LCD
  else if ( this->lcdMap.end() == (itr_m = this->lcdMap.find(*engID)) ) {
    *boots = 0;
    *time  = 0;
  }
  else { // msg engine ID exists in the LCD
    *boots = itr_m->second.boots;
    *time  = itr_m->second.time;
  }
}  // getBootsTime


// checks validity of engine ID
bool
snmpUSMArchObj::isValidEngineID(OctetString *engID) {
  DEBUG9(usmDebug, "isValidEngineID");

  // if the engine ID is invalid (not a 5-32 length OctetString), return false
  if ( ((*engID).size() < 5) || ((*engID).size() > 32) ) {
    DEBUG5(usmDebug, "isValidEngineID: Engine ID '" + string(*engID) 
	   + "' is invalid.");
    return false;
  }

  map<OctetString, usmLCDEntry>::iterator itr_m;
  // if the Engine ID doesn't exist in the LCD, and this isn't the 
  // authoritative engine, add it with boots, time = zero, is_auth = false.
  if ( ( this->lcdMap.end() == (itr_m = this->lcdMap.find(*engID)) ) &&
       ( *(this->enginePtr->get_snmpEngineId()) != *engID) )  {
    usmLCDEntry newEntry = { 0, 0, 0, false};
    this->lcdMap[*engID] = newEntry;
  }

  return(true);
}  // isValidEngineID
  

bool
snmpUSMArchObj::findUserData(OctetString *userName, OctetString *engID,
			     snmpSecurityStateReference *userData) {
  DEBUG9(usmDebug, "findUserData");
  snmpRow   *userRow  = 0;

  if ( ( ( userRow = this->usmData.searchUSMTable(engID->clone(), 
						 userName->clone()) ) 
	 == 0) ) {
    DEBUG9(usmDebug, "User Data Not Found: \n  userName: '" << 
	   string(*userName) << "'\n  Eng. ID: '" << 
	   string(*engID) << "'\n");
    return (false);
  }
  else { // get data from the usmUserTable to populate userData;
    userData->userName = *userName;

    userData->authProtocol = 
      *(userRow->get_column_OID(snmpUSMData::usmUserAuthProtocolCol));
    OctetString *tempOS = 
      (userRow->get_column_OctetString(snmpUSMData::usmUserAuthKeyCol));
    userData->authKey = *tempOS;
    //      *(userRow->get_column_OctetString(snmpUSMData::usmUserAuthKeyCol));
    userData->privProtocol = 
      *(userRow->get_column_OID(snmpUSMData::usmUserPrivProtocolCol));
    userData->privKey = 
      *(userRow->get_column_OctetString(snmpUSMData::usmUserPrivKeyCol));
    DEBUG9(usmDebug, " userName: '" << string(*userName) << "'\n"
	   << "authProto: '" << string(userData->authProtocol) << "'\n"
	   << "privProto: '" << string(userData->privProtocol) << "'");
  }
  
  return (true);
} // findUserData



void
snmpUSMArchObj::GenerateRequestMessage(snmpUSMGenerateRequestMsgASI *genReqMsg)
{
  DEBUG9(usmDebug, "GenerateRequestMessage");

  snmpStatusInfo  *theStatus = new snmpStatusInfo(true);
  genReqMsg->set_statusInformation(theStatus);

  OctetString      *locSecName = genReqMsg->get_securityName();
  OctetString     *locSecEngID = genReqMsg->get_securityEngineID();
  Integer32       *locSecLevel = genReqMsg->get_securityLevel();

  snmpSecurityStateReference *userData = new snmpSecurityStateReference();

  // If all the data doesn't exist in the incoming message, fail.
  if ( (locSecName == 0) || (locSecEngID == 0) || (locSecLevel == 0) ) {
    DEBUG0(usmDebug, "GenerateRequestMessage: security name or security "
	   << "engine ID does not exist in message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
  }
  // Elements of procedure: RFC 2574: 3.1,1),
  // (interesting numbering scheme, eh? %) 
  // a): get cache data, not aplicable here
  // b): lookup the user/secEngID in the configuration store.
  // fail if not found AND we're not doing engine ID discovery.
  else if (!(this->findUserData(locSecName, locSecEngID, userData)) &&
	   !( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH == int(*locSecLevel)) &&
	      (*locSecEngID == 
	          OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_SEC_NAME)) )
	   ) {
    DEBUG1(usmDebug, "GenerateRequestMessage: user name '" << 
	   string(*locSecName) << "' &/or engine ID '" << 
	   string(*locSecEngID) << "'" << "not found in usm MIB!");
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processStatsError
			    (usmStatsUnknownEngineIDs));
  }
  else { 
    // do all the shared request/response work...
    this->GenerateOutMessage(userData, genReqMsg);
  }
  
  delete userData;
  return_message(genReqMsg);
} // GenerateRequestMessage


void
snmpUSMArchObj::GenerateResponseMessage(snmpUSMGenerateResponseMsgASI *genRespMsg) {
  DEBUG9(usmDebug, "GenerateResponseMessage");

  // default status to true, change it later if neccesary.
  snmpStatusInfo  *theStatus = new snmpStatusInfo(true);
  genRespMsg->set_statusInformation(theStatus);

  // Elements of procedure: RFC 2574: 3.1,1), 
  // (interesting numbering scheme, eh? %) 
  // a): get cache data
  snmpSecurityStateReference *userData;
  if (!(userData = dynamic_cast<snmpSecurityStateReference *>
                     (genRespMsg->get_securityStateReference()))) {
    DEBUG0(usmDebug, "GenerateResponseMessage: security state reference "
	   << "casting failed, USM failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
  }
  else {
    // do all the shared request/response work...
    this->GenerateOutMessage(userData, genRespMsg);
  }
  
  return_message(genRespMsg);
}  // GenerateResponseMsg



void
snmpUSMArchObj::GenerateOutMessage(snmpSecurityStateReference *userData,
				   snmpStandardMessage  *genOutMsg) {
  DEBUG9(usmDebug, "GenerateOutMsg");

  // get a ptr to the status object (added to msg, & =true, in prev. routine)
  snmpStatusInfo  *theStatus = genOutMsg->get_statusInformation();

  // Pull the data out of the  message.
  Integer32   *locMsgProcModel = genOutMsg->get_messageProcessingModel();
  Integer32       *locSecModel = genOutMsg->get_securityModel();
  OctetString      *locSecName = genOutMsg->get_securityName();
  Integer32       *locSecLevel = genOutMsg->get_securityLevel();
  OctetString     *locSecEngID = genOutMsg->get_securityEngineID();

  ScopedPDU  *locScopedPDU  = genOutMsg->get_scopedPDU();
  Integer32  *locMaxMsgSize = genOutMsg->get_maxMsgSize();
  HeaderData *locGlobalData = genOutMsg->get_globalData();

  // If all the data doesn't exist in the incoming message, fail.
  if ( (locMsgProcModel == 0) || (locSecModel == 0) || (locSecName == 0) || 
       (locSecLevel == 0)   || (locScopedPDU == 0) || (locSecEngID == 0) ||
       (locMaxMsgSize == 0) || (locGlobalData == 0) ) {

    DEBUG0(usmDebug, "GenerateOutMsg: not all the data exists in incoming "
	   << "message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
  }

  // Elements of procedure: RFC 2574: 3.1,2) check the security level.
  // If it is auth-priv, fail if the user dosen't support both
  // privacy and authentication.

  else if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel)) 
	    &&
	    ( ( userData->authProtocol == this->usmData.usmNoAuthProtocolOID ) 
	      ||
	      ( userData->privProtocol == this->usmData.usmNoPrivProtocolOID ) 
	    )
	  )  {
    DEBUG5(usmDebug, "GenerateOutMsg: Warning: usmStatsUnsupportedSecLevel "
	   << "message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processStatsError
			    (usmStatsUnsupportedSecLevels));
  }

  // Elements of procedure: RFC 2574: 3.1,3) check the security level.
  // If it is auth-noPriv, fail if the user dosen't support authentication.

  else if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV == int(*locSecLevel)) &&
	    (userData->authProtocol == this->usmData.usmNoAuthProtocolOID) 
	  )  {
    DEBUG5(usmDebug, "GenerateOutMsg: Warning: usmStatsUnsupportedSecLevels "
	   << "message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processStatsError
			    (usmStatsUnsupportedSecLevels));
  }

  // **************** 
  // The user's info in userData (from cache data or usmUserTable) should 
  // provide the neccessary info for any desired auth./encrypt.

  else {

    OctetString *privParams = new OctetString();

    // always boots/time of the authoratative engine
    int secEngBoots = 0;
    int secEngTime  = 0;
    this->getBootsTime(locSecEngID, &secEngBoots, &secEngTime);
    unsigned int usecEngBoots = secEngBoots;
    unsigned int usecEngTime  = secEngTime;

    // EoF: 3.1,4 a) if the security level is AuthPriv, encrypt the scoped PDU
    // with the user's privKey.
    if (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel)) { 
      if (!(this->encryptData(userData, locScopedPDU, privParams,
                              usecEngBoots, usecEngTime))) {
	DEBUG5(usmDebug, "GenerateOutMsg: Warning: encryption failed, "
	       << "message failing");
	theStatus->set_success(false);
	theStatus->pushErrorObj(new snmpErrorObj
				(snmpErrorObj::ENCRYPTION_ERROR));
      }
    }
    // Eof: 3.1,4 b) if the security level is noPriv, use zero length
    // privacy parameters, which it already is...

    
    // EoF: 3.1,5 securityEngineID is encoded into msgAuthoritativeEngineID
    // of the security parameters. We already have the OctetString, it will
    // be used when we create the security parameters.

    // EoF: 3.1,6 a) if this is an authenticated message. Get the proper
    // values for engine boots and engine time corresponding to the security
    // EngineID. If unknown, use '0'.

    // NOTE boots/time moved up from here by Wes

    // EoF: 3.1,7 encode user name for security params, already available,
    // but it is time to serialize the message.

    // set the authentication parameters appropriately...
    // If Msg is Authenticated, authParams is 12 zero value octets.
    OctetString *authParams;
    if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel))
	 ||
	 (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV == int(*locSecLevel)) ) {
      authParams = new OctetString( string(12, '\0') );
    }
    // Otherwise, authParams is zero length.
    else {
      authParams = new OctetString();
    }
    
    USMSecurityParameters *secParams = new USMSecurityParameters
      ( locSecEngID->clone(),       // Auth. Eng. ID
	new Integer32(secEngBoots), // Eng. Boots
	new Integer32(secEngTime),  // Eng. Time
	locSecName->clone(),        // User Name
	authParams,                 // Auth. Parameters
	privParams);                // Privacy Parameters
    
    snmpV3Message &theMsg = genOutMsg->get_v3Message_ref();
    theMsg.set_msgSecurityParameters(secParams);

    char* buffer = (char *) calloc(sizeof(char), BUFFER_SIZE);
    char* outMsg;
    Integer32*  outMsgLength = 0;
  
    try {
      outMsg = theMsg.asnEncode(buffer + BUFFER_SIZE - 1);
      if (outMsg)
        outMsg++; // Get to the real data location, since encoding
                  // returns the next free byte.
      outMsgLength = new Integer32(buffer + BUFFER_SIZE - outMsg);
      
      BufferClass *theWholeOutMsg = 
	new BufferClass(buffer, BUFFER_SIZE,
			outMsg, (buffer + BUFFER_SIZE - outMsg));

      DEBUG9(usmDebug, "The Outgoing message is: \n" << theMsg.toString());
 
      genOutMsg->set_outMsg(theWholeOutMsg);
      genOutMsg->set_outMsgLength(outMsgLength);
    }
    
    catch (snmpStringException &except) {
      DEBUG0(usmDebug, "GenerateOutMsg: while asn encoding V3 message " << 
	     "caught exception '" << except.get_errorMessage() 
	     << "', failing");
      delete []  buffer;
      theStatus->set_success(false);
      theStatus->pushErrorObj
	( new snmpErrorObj(snmpErrorObj::ASN_ENCODE_ERROR,
			   " Exception Caught") );
    }
    catch (...) {
      DEBUG0(usmDebug, "GenerateOutMsg: while asn encoding V3 message " << 
	     "caught UNKNOWN exception, failing");
      delete []  buffer;
      theStatus->set_success(false);
      theStatus->pushErrorObj
	( new snmpErrorObj(snmpErrorObj::ASN_ENCODE_ERROR,
			   " Exception Caught") );
    }

    // EoF: 3.1,8 a) if the security level specifies authenticatation,
    // authenticate according to the user's authentication protocol...

    if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel))
	 ||
	 (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV == int(*locSecLevel)) ) {

      if (!(this->authenticateMessage(userData, secParams, 
				      outMsg,   int(*outMsgLength)))) {
	DEBUG0(usmDebug, "GenerateOutMsg: Error authenticating  message, "
	       << "failing");
	theStatus->set_success(false);
	theStatus->pushErrorObj
	  (new snmpErrorObj(snmpErrorObj::AUTHENTICATION_ERROR));
      }
    }

  }
  
}  // generate out message


void
snmpUSMArchObj::ProcessIncoming(snmpUSMProcessIncomingASI  *procIncoming) {
  DEBUG9(usmDebug, "ProcessIncoming");
  // create a status, default it to true, change later if neccessary
  snmpStatusInfo *theStatus = new snmpStatusInfo(true);
  procIncoming->set_statusInformation(theStatus);

  snmpV3Message            &theMsg = procIncoming->get_v3Message_ref();
  USMSecurityParameters *secParams = new USMSecurityParameters();

  int length;
  // Elements of Procedure: 3.2,1: parse security parameters
  try  {
    char *SecSerLoc = theMsg.get_msgSecurityParametersSerialLocation();
    length = (int)*(procIncoming->get_outMsgLength()) - 
      (int)(SecSerLoc - (theMsg.get_serialLocation()));
    secParams->asnDecode(SecSerLoc, length);
  }
  catch (snmpStringException &except) {
    DEBUG5(usmDebug, "ProcessIncoming: while asn decoding security " 
	   << "parameters caught exception '" << except.get_errorMessage() 
	   << "', failure returned, length was " << length);
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj
			    (snmpErrorObj::ASN_PARSE_ERROR));
    this->enginePtr->stats.increment(SNMP_STATS::SNMP_IN_ASN_PARSE_ERRORS);
    delete secParams;
    return_message(procIncoming);
    return;
  }
  catch (...) {
    DEBUG5(usmDebug, "ProcessIncoming: while asn decoding security " 
	   << "parameters caught UNKNOWN exception, failure returned");
    theStatus->set_success(false);
    delete secParams;
    return_message(procIncoming);
    return;
  }

  // EoP: 3.2,2: prepare security engine ID, security state reference
  // set outgoing values in hopes of success.
  OctetString *loc_secName = secParams->get_msgUserName(true);
  procIncoming->set_securityName(loc_secName);

  OctetString *loc_secEngineID = 
    secParams->get_msgAuthoritativeEngineID()->clone();
  procIncoming->set_securityEngineID(loc_secEngineID);

  Integer32 *loc_secLevel = procIncoming->get_securityLevel();

  // If all the data doesn't exist in the incoming message, fail.
  if ( (loc_secName == 0) || (loc_secLevel == 0) || 
       (loc_secEngineID == 0) ) {
    DEBUG5(usmDebug, "ProcessIncomingMsg: not all the data exists in "
	   << "incoming message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
    delete secParams;
    return_message(procIncoming);
    return;
  }

  int secLevel = int(*loc_secLevel); // used for speed later.
  bool is_discovery = ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH == secLevel) &&
   (*loc_secName == OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_SEC_NAME)) ); 

  snmpSecurityStateReference *userData = new snmpSecurityStateReference();
  procIncoming->set_securityStateReference(userData);

  // copy payload into sPDU_string, this allows for attempts to asn
  // decode and get PDU info in case of a failure below.
  // XXX This should probably be optional depending on whether or not
  //     extra-tight security is desired, i.e.
  //     With auth or priv failures, SNMP engine shouldn't even bother 
  //     looking at the PDU info.
  //     vs.
  //     Being able to detect errors on user name or other auth failures.
  //
  //     Recognizing the imperfectness of man, or my imperfectness anyway,
  //     I'm going with being able to detect errors for now... :}
  char       *payload = theMsg.get_scopedPDUSerialLocation();
  int   payloadLength = asnDataType::asnLengthWithHeader(payload);
  string *sPDU_string = new string(payload, payloadLength);
  procIncoming->set_scopedPDU_string(sPDU_string);


  // EoP: 3.2,9: calculate maxSizeResponseScopedPDU
  // This is a fairly arbitrary number at the moment...
  // Done here, because it may be needed un a report message even if 
  // processing fails, say in 3.2,3...
  procIncoming->set_maxSizeResponsePDU(new Integer32
     ( int(*(procIncoming->get_maxMsgSize())) - this->MESSAGE_OVERHEAD) );

  // EoP: 3.2,3: if Authoritative engine id is unknown (or null),
  // return error, w/OID and incremented counter (unknownEngineID)
  if (!(this->isValidEngineID(loc_secEngineID))) {
    DEBUG5(usmDebug,"Invalid Engine ID, returning 'usmStatsUnknownEngineIDs'");
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processStatsError
			    (usmStatsUnknownEngineIDs));
  }
  // EoP: 3.2,4: get user (usmUserTable) information, if unknown AND
  // not doing eng. ID discovery, respond as error usmStatsUnknownUserNames.
  else if ( !is_discovery &&
	    !(this->findUserData(loc_secName, loc_secEngineID, userData))
	  ) {
    DEBUG5(usmDebug,"Unknown User Data, returning 'usmStatsUnknownUserNames'");
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processStatsError
			    (usmStatsUnknownUserNames));
  }
  // EoP: 3.2,5: if this isn't engine id discovery, check if the requested 
  // security level is supported by the user's local data (usmUserTable)
  else if ( !is_discovery &&
	    ( ( (userData->authProtocol == this->usmData.usmNoAuthProtocolOID)
		&&
		(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH != secLevel) )
	      ||
	      ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == secLevel)
		&&
		(userData->privProtocol == this->usmData.usmNoPrivProtocolOID) ) ) 
	  ) {
    DEBUG5(usmDebug,"Security Level not supported, returning " << \
	   "'usmStatsUnsupportedSecLevels'");
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processStatsError
			    (usmStatsUnsupportedSecLevels));
  }
  else {

    // EoP: 3.2,6: If the security level specifies authentication, 
    // check the messages authentication.
    if (SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH != secLevel) {
      if (!(this->checkAuthentication(userData, secParams, theMsg)))  {
	DEBUG5(usmDebug, "ProcessIncoming: Error, Message NOT Authenticated!");
	theStatus->set_success(false);
	theStatus->pushErrorObj(this->processStatsError
				(usmStatsWrongDigests));
      }
      else  { // successfully athenticated an athenticated message...
	// EoP: 3.2,7: process/set the boots and time values
	// for the athoritative engine. 
	DEBUG9(usmDebug, "ProcessIncoming: Message Authenticated!");
	this->processBootsTime(secParams, theStatus, true);
      }
    }
    else {  // processBootsTime as an unauthenticated message. (save as guess)
      this->processBootsTime(secParams, theStatus, false);
    }
     
    //  FOLLOWING has been moved above, see reasons above.
    //    char       *payload = theMsg.get_scopedPDUSerialLocation();
    //    int   payloadLength = asnDataType::asnLengthWithHeader(payload);
    //    string *sPDU_string = new string();

    // EoP: 3.2,8 a): if the securiyt level indicates the message is protected
    // from disclosure (i.e. encrypted), decrypt the message (if we are 
    // still valid from above).
    if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == secLevel) &&
	 (theStatus->get_success())
       )  {
      if ( !(this->decryptData(userData, secParams, 
			       payload, payloadLength, 
			       sPDU_string)) ) {
	DEBUG5(usmDebug, "ProcessIncoming: Error, decrypt failed!");
	theStatus->set_success(false);
	theStatus->pushErrorObj(this->processStatsError
				(usmStatsDecryptionErrors));
      }
      else DEBUG9(usmDebug,"ProcessIncoming: Scoped PDU succesfully decrypted");
    }
    // else  // payload already assigned to sPDU_string and procIncoming.

    
    // EoP: 3.2,9: calculate maxSizeResponseScopedPDU
    // This is a fairly arbitrary number at the moment...
    // 
    // 3.2,9 is completed above, as the data may be needed for a report
    // if processing failed above.
    //   procIncoming->set_maxSizeResponseScopedPDU(new Integer32(
    //    int(*(procIncoming->get_maxMessageSize())) - this->MESSAGE_OVERHEAD) );

    // EoP: 3.2,10: Put the security name in the outgoing message :{...
    // this was done in 3.2,2 above as userName = securityName in the USM

    // EoP: 3.2,11: Put the security state reference in the message
    // already done for memory control purposes => the memory is released
    // when the MP deletes the procIncoming message...
    // if processing was unsuccessful, it may not have any data.

    // EoP: 3.2,12: statusInformation set to true (already done as 
    // this procedures logic was 'true until set false')
    // In any case, return to the MP
  }
  
  delete secParams;
  return_message(procIncoming);
}; // ProcessIncoming


void 
snmpUSMArchObj::main_loop()  {
  snmpMessageObj          *newMsg;
  int                      count = 0;
  
  createTables();
  passTablesToCR();
  DEBUG9(usmDebug, "The usm MIB table:\n" << this->usmData.toString()
	 << "\nEnd usm MIB table");
  // fifo->pop will block and wait for an snmpMessageObj to 
  // be placed on the fifo
  while (NULL != (newMsg = this->fifoPtr->pop())) {
    DEBUG9(usmDebug, "top of main loop\nNew message is class '" 
	   << typeid(*newMsg).name() << "'");

    if (snmpUSMGenerateRequestMsgASI *genReqMsg = 
	dynamic_cast<snmpUSMGenerateRequestMsgASI *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an snmpUSMGenerateRequestMsgASI");
      GenerateRequestMessage(genReqMsg);
    }
    else if (snmpUSMGenerateResponseMsgASI *genRespMsg = 
	     dynamic_cast<snmpUSMGenerateResponseMsgASI *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an snmpUSMGenerateResponseMsgASI");
      GenerateResponseMessage(genRespMsg);
    }
    else if (snmpUSMProcessIncomingASI *procIncoming = 
	     dynamic_cast<snmpUSMProcessIncomingASI *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an snmpUSMProcessIncomingASI");
      ProcessIncoming(procIncoming);
    }
    else if (usmAddUserMsg *addUser = 
	     dynamic_cast<usmAddUserMsg *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an usmAddUserMsg");
      AddUserUSM(addUser);
    }
    else if (usmDelUserMsg *delUser = 
	     dynamic_cast<usmDelUserMsg *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an usmAddUserMsg");
      usmData.delRow(delUser->get_engID(true), delUser->get_userName(true));
      delete delUser;
    }
    else if (usmCheckUserMsg *checkUser = 
	     dynamic_cast<usmCheckUserMsg *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an usmCheckUserMsg");
      CheckUserUSM(checkUser);
    }
    else if (snmpExitMessage *xMsg = 
	     dynamic_cast<snmpExitMessage *>(newMsg)) {
      DEBUG9(usmDebug, "NewMsg is an snmpExitMessage, exiting USM thread");
      // do any neccessary clean up here
      delete xMsg;
      return;
    }

    // or not an acceptable message type...
    else {
      DEBUG0(usmDebug, "main_loop: Error, received unknown message type");
    }

    count++;
  } // while ( (NULL != (newMsg = this->fifoPtr->pop())) && (count < 10) )

}  // main_loop


void 
snmpUSMArchObj::handleInFIFO(snmpMessageObj *message)  {
  DEBUG9(usmDebug, "handle in FIFO from USM Arch");
}

void
snmpUSMArchObj::createTables() {
  DEBUG9(usmDebug, "createTable");
  
#ifdef NO_PERSISTENCE
  usmData.addRow(new OctetString
		 (*(enginePtr->get_snmpEngineId())),
		 new OctetString("initial"));

  usmData.addRow(new OctetString
		 (*(enginePtr->get_snmpEngineId())),
		 new OctetString("initial_auth"),
		 new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),
		 new OID(snmpUSMData::usmHMACMD5AuthProtocolOID),
		 new OctetString("kissthesky"));

  usmData.addRow(new OctetString
		 (*(enginePtr->get_snmpEngineId())),
		 new OctetString("fomike"),
		 new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),
		 new OID(snmpUSMData::usmHMACMD5AuthProtocolOID),
		 new OctetString("kissthesky"),
		 new OID(snmpUSMData::usmDESPrivProtocolOID),
		 new OctetString("zonkerharris"));

  usmData.addRow(new OctetString
		 (*(enginePtr->get_snmpEngineId())),
		 new OctetString("newUser"),
		 new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),
		 new OID(snmpUSMData::usmHMACMD5AuthProtocolOID),
		 new OctetString("kissthesky"),
		 new OID(snmpUSMData::usmDESPrivProtocolOID),
		 new OctetString("zonkerharris"));
  
  OctetString *newEngID = new OctetString;
  newEngID->fromHexString("800007E50454455354"); // test for ucd-snmp
  usmData.addRow(newEngID, new OctetString("newUser"),
		 new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),
		 new OID(snmpUSMData::usmHMACMD5AuthProtocolOID),
		 new OctetString("kissthesky"),
		 new OID(snmpUSMData::usmDESPrivProtocolOID),
		 new OctetString("zonkerharris"));

  // for use in cg -> cr communication, we need different eng ID's
  OctetString *oldEngineID = new OctetString("localEngineID");
  if (*oldEngineID != *(enginePtr->get_snmpEngineId())) {
    usmData.addRow(oldEngineID, new OctetString("newUser"),
		   new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE),
		   new OID(snmpUSMData::usmHMACMD5AuthProtocolOID),
		   new OctetString("kissthesky"),
		   new OID(snmpUSMData::usmDESPrivProtocolOID),
		   new OctetString("zonkerharris"));
  }
  else delete oldEngineID;

#endif
}  // createTables

void
snmpUSMArchObj::passTablesToCR() {
    snmpCRRegisterTable *regMsg;
    snmpCRRegisterScalarSet *regScalarMsg;
    DEBUG9(usmDebug, "passTablesToCR");

    // assign default values for newly created rows
    snmpRow::defaultValueList *dvlist = new snmpRow::defaultValueList();
    (*dvlist)[snmpUSMData::usmUserStorageTypeCol] =
        new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE);
    (*dvlist)[snmpUSMData::usmUserPublicCol] = new OctetString("");

    // create and setup a column allocator list.
    snmpColumnAllocatorList *calist = new snmpColumnAllocatorList();
    (*calist)[snmpUSMData::usmUserStatusCol] =
        new snmpRowStatusColumnAllocator(
            new snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT, 
                              &usmData, NULL, 
                              // XXX: fix checkColsExist, currently not legal
                              new snmpRowStatusCheckColsExist(
                                  snmpUSMData::usmUserCloneFromCol,
                                  snmpUSMData::usmOwnAuthKeyChangeCol)));
    (*calist)[snmpUSMData::usmUserStorageTypeCol] =
      new snmpRowStorageColumnAllocator();

    (*calist)[snmpUSMData::usmUserKeyChangeCol] =
        new snmpKeyChangeColumnAllocator(new KeyChange());

    // assign default values for persistent rows
    columnStorageMap *csmap = new columnStorageMap();
    (*csmap)[snmpUSMData::usmUserEngineIDCol] = true;
    (*csmap)[snmpUSMData::usmUserNameCol] = true;
    (*csmap)[snmpUSMData::usmUserSecurityNameCol] = true;
    (*csmap)[snmpUSMData::usmUserCloneFromCol] = true;
    (*csmap)[snmpUSMData::usmUserAuthProtocolCol] = true;
    (*csmap)[snmpUSMData::usmUserKeyChangeCol] = true;
    (*csmap)[snmpUSMData::usmOwnAuthKeyChangeCol] = true;
    (*csmap)[snmpUSMData::usmUserPrivProtocolCol] = true;
    (*csmap)[snmpUSMData::usmUserPrivKeyChangeCol] = true;
    (*csmap)[snmpUSMData::usmUserOwnPrivKeyChangeCol] = true;
    (*csmap)[snmpUSMData::usmUserPublicCol] = true;
    (*csmap)[snmpUSMData::usmUserStorageTypeCol] = true;
    (*csmap)[snmpUSMData::usmUserStatusCol] = true;
    (*csmap)[snmpUSMData::usmUserAuthKeyCol] = true;
    (*csmap)[snmpUSMData::usmUserPrivKeyCol] = true;

    // if the Command Receiver is available, register the usm table with it.
    if (crFIFO == NULL) {
        if ((crFIFO = this->theReg.findArchFIFO(SNMP_CR_ARCH_NAME)) == NULL) {
            DEBUG5(usmDebug, "passTableToCR: failed to find the CR FIFO");
            return;
        }
    }

    // register usmUserTable
    DEBUG9(usmDebug, "passTableToCR: registering usmUserTable");
    regMsg = new snmpCRRegisterTable
      (&usmData,
       new OID(snmpUSMData::usmUserTableOID),
       new OctetString("none"),
       13, 1, snmpDataTable::ALLWRITABLE,
       new snmpPersistentRowManager( snmpUSMData::usmUserStorageTypeCol,
                           csmap, NULL, 
			   new snmpRowAllocator(calist, dvlist)));
    
    crFIFO->push(regMsg);


    // register usmStats
    DEBUG9(usmDebug, "passTableToCR: registering usmStats");
    regScalarMsg = new snmpCRRegisterScalarSet
      (&usmStatistics,
       new OID(usmStatsOID),
       new OctetString("none"));
    
    crFIFO->push(regScalarMsg);

} // passTableToCR
