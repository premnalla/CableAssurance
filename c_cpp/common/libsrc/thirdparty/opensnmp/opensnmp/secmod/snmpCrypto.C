#include "config.h"
#include "snmpCrypto.H"
#include "snmpUSMData.H"

#ifdef STRUCT_DES_KS_STRUCT_HAS_WEAK_KEY
/* these are older names for newer structures that exist in openssl .9.7 */
#define DES_key_schedule    des_key_schedule 
#define DES_cblock          des_cblock 
#define DES_key_sched       des_key_sched 
#define DES_ncbc_encrypt    des_ncbc_encrypt
#define DES_cbc_encrypt    des_cbc_encrypt
#define OLD_DES
#endif

snmpMutexObj*
snmpCrypto::cryptoMutex = new snmpMutexObj();

enum encr_types
{
    EDES, EAES
};

snmpCrypto::snmpCrypto(const snmpCrypto &) {
  DEBUGCREATE(cryptoDebug,"CRYPTO");
}

snmpCrypto::snmpCrypto() {
  DEBUGCREATE(cryptoDebug,"CRYPTO");
}

snmpCrypto::~snmpCrypto() {
  DEBUGDESTROY(cryptoDebug);
}

bool
snmpCrypto::generate_Kul(OID         &hashtype,
			 OctetString &engineID,
			 opensnmpKey &Key,
			 OctetString *Kul)  {
  DEBUG9(cryptoDebug, "generate_Kul");
  // check incoming pointer
  if (!Kul) {
    DEBUG0(cryptoDebug, "generate_Kul: input parameter Kul is NULL, failing");
    return(false);
  }
  
  const EVP_MD    *EVP_HashType = 0;
  u_int           digest_length = 0;
  
  // begin mutex section...
  if (!this->cryptoMutex->lock("snmpCrypto::generate_Kul")) {
    return(false);
  }
  
  // set values for authentication type, Sha1 and MD5 currently supported.
  if (hashtype == snmpUSMData::usmHMACMD5AuthProtocolOID) {
    EVP_HashType = EVP_md5();
    digest_length = this->MD5_Length;
  }
  else if (hashtype == snmpUSMData::usmHMACSHAAuthProtocolOID) {
    EVP_HashType = EVP_sha1();
    digest_length = this->SHA1_Length;
  }
  else {
    DEBUG0(cryptoDebug, "generate_Kul: unknown hashtype, failing");
    this->cryptoMutex->unlock("snmpCrypto::generate_Kul");
    return(false);
  }
  
  // check key length, all keys must be at least digest_length long
  if (Key.size() < digest_length) {
    this->cryptoMutex->unlock("snmpCrypto::generate_Kul");
    DEBUG0(cryptoDebug, "ERROR: generate_Kul: key length shorter than digest");
    return false;
  }

  // if parameter Key is already the localized key, just return it.
  if (Key.get_keyType() == opensnmpKey::LOCALIZED_KEY) {
    this->cryptoMutex->unlock("snmpCrypto::generate_Kul");
    // check size, localized key must be = to digest_length
    if (Key.size() == digest_length) {
      *Kul = OctetString(string(Key));
      return true;
    }
    else {
      DEBUG0(cryptoDebug, "ERROR: generate_Kul: bad key length on provided localized key");
      return false;
    }
  }

  // Check whether or not we have a localized key, key or a pass
  // phrase to generate a key from.  Either use the provided key
  // (preferred), or generate a key from the pass phrase.

  // set up variables for key value and localized key value
  u_char    *uc_key;          // key array
  u_int      key_length = 0;  // key length
  u_char     localized_key[digest_length];
  HMAC_CTX  *hmac_info = new HMAC_CTX;

  // if the parameter Key is a pass phrase, generate a main key
  // value from it.
  if (Key.get_keyType() == opensnmpKey::PASSPHRASE) {

    // Generate key from pass phrase using the suggested algorithm
    // in Appendix A.2 of STD 62, RFC 3414.

    // create data for the next part of the algorithm.
    const int block_size = 64; // this is a multiple for 1048576 below!
    u_char    block[block_size];

    // create space for generated key material
    key_length = digest_length;
    uc_key     = new u_char[key_length];

    int       i, length = 0;
  
    int Pass_Len = Key.size();
    char  c_pass[Pass_Len+1];
    Key.get_string(c_pass, Pass_Len, false);
  
    // create initial digest
    EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
  
    while (length < 1048576) {
      for( i=0; i < block_size; i++) {
	block[i] = c_pass[length++ % Pass_Len];
      }
      EVP_DigestUpdate(&(hmac_info->md_ctx), block, block_size);
    }
    EVP_DigestFinal(&(hmac_info->md_ctx), uc_key, &key_length);
    
    // paranoia self destroya
    memset(block,  0, block_size);
    memset(c_pass, 0, Pass_Len);
  
    //     DEBUG9(cryptoDebug, "snmpCrypto::generate_Kul: Ku is '";)
    //     for ( i=0; i < int(digest_length); i++) {
    //       printf("%02X ", int(digest[i]));
    //     }
    //     DEBUG9(cryptoDebug, "'");
  
  }
  // if the parameter Key is a main key, set up data for
  // localization.
  else if (Key.get_keyType() == opensnmpKey::KEY) {
    // note: already checked that key length >= digest length.
    // create space for passed in key value.
    // if key is longer than digest, this will truncate it.
    key_length = digest_length;
    uc_key = new u_char[key_length];
    // copy key value into array 
    Key.get_string((char *)uc_key, key_length, false);
  }
  // if the parameter Key is a main key and large, set up data for
  // localization.
  else if (Key.get_keyType() == opensnmpKey::LARGEKEY) {
    // create space for passed in key value.
    key_length = Key.size();
    uc_key = new u_char[key_length];
    // note: already checked that key length >= digest length.
    // This will use the full key length with no truncation.
    Key.get_string((char *)uc_key, key_length, false);
  }
  else {
    DEBUG0(cryptoDebug, "generate_Kul: unknown opensnmpKey type, failing");
    this->cryptoMutex->unlock("snmpCrypto::generate_Kul");
    return(false);
  }

  // Generate the Localized Key Value

  int     block2_size = key_length + engineID.size() + key_length;
  u_char  block2[block2_size];
  memcpy(block2, uc_key, key_length);
  // copy the char values of engine ID, it gets a 'c' string,
  // i.e. get_string will write a '\0' at the end, truncating if
  // neccessary.
  engineID.get_string((char *)(block2 + key_length), (engineID.size() + 1));
  memcpy((block2 + key_length + engineID.size()), uc_key, key_length);
  
  // create final digest, i.e. key.
  EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
  EVP_DigestUpdate(&(hmac_info->md_ctx), block2, block2_size);
  EVP_DigestFinal(&(hmac_info->md_ctx), localized_key, &digest_length);
  
  // end of mutex section
  this->cryptoMutex->unlock("snmpCrypto::generate_Kul");
  
  Kul->assign((char *)localized_key, int(digest_length)); 

  DEBUG9(cryptoDebug, "generate_Kul: Kul is '" << Kul->toHexString() << "'");

  // clean up
  memset(block2, 0, block2_size);
  memset(uc_key, 0, key_length);
  delete [] uc_key;
  memset(localized_key, 0, digest_length);
  delete hmac_info;

  return(true);
}  // generate_Kul


// passphrase_to_key generates a key value from a passphrase 
// This procedure uses the suggested algorithm in the IETF's STD 62,
// RFC 3414, Appendix A.2.
bool
snmpCrypto::passphrase_to_key(OID         &hashtype,
			      OctetString &Pass,
			      OctetString *Key)  {
  DEBUG9(cryptoDebug, "passphrase_to_key");
  // check incoming pointer
  if (!Key) {
    DEBUG0(cryptoDebug, "passphrase_to_key: input parameter Key is NULL, failing");
    return(false);
  }

  const EVP_MD    *EVP_HashType = 0;
  u_int           digest_length = 0;
  
  // begin mutex section...
  if (!this->cryptoMutex->lock("snmpCrypto::passphrase_to_key")) {
    return(false);
  }
  
  // set values for authentication type, Sha1 and MD5 currently supported.
  if (hashtype == snmpUSMData::usmHMACMD5AuthProtocolOID) {
    EVP_HashType = EVP_md5();
    digest_length = this->MD5_Length;
  }
  else if (hashtype == snmpUSMData::usmHMACSHAAuthProtocolOID) {
    EVP_HashType = EVP_sha1();
    digest_length = this->SHA1_Length;
  }
  else {
    DEBUG0(cryptoDebug, "passphrase_to_key: unknown hashtype, failing");
    this->cryptoMutex->unlock("snmpCrypto::passphrase_to_key");
    return(false);
  }
  
  // create openssl variable
  HMAC_CTX  *hmac_info = new HMAC_CTX;

  // create password array
  char c_pass[Pass.size()+1];
  Pass.get_string(c_pass, Pass.size(), false);

  // create hashing block for the next part of the algorithm.
  const int block_size = 64; // this is a multiple for 1048576 below!
  u_char    block[block_size];

  // create space for generated key material
  u_char    uc_key[digest_length];

  // initialize digest 
  EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
  
  // do repeated hash  
  int i, length = 0;
  while (length < 1048576) {
    for( i=0; i < block_size; i++) {
      block[i] = c_pass[length++ % Pass.size()];
    }
    EVP_DigestUpdate(&(hmac_info->md_ctx), block, block_size);
  }
  EVP_DigestFinal(&(hmac_info->md_ctx), uc_key, &digest_length);

  // unlock mutex.
  this->cryptoMutex->unlock("snmpCrypto::passphrase_to_key");

  Key->assign((char *)uc_key, int(digest_length)); 

  DEBUG9(cryptoDebug, "passphrase_to_key: Key is '" 
	 << Key->toHexString() << "'");

  // clean up, paranoia self destroya
  memset(block,  0, block_size);
  memset(c_pass, 0, Pass.size());
  memset(uc_key, 0, digest_length);
  delete hmac_info;

  return(true);
}  // passphrase_to_key



// generate_Kul_from_Ku geneartes a localized key based on the
// keying material, Ku, and the SNMP Engine ID value, engineID. This
// follows the key localization method given in STD 62, RFC 3414.
bool
snmpCrypto::generate_Kul_from_Ku(OID          &hashtype, 
				 OctetString  &engineID,
				 OctetString  &Ku,
				 OctetString  *Kul)  {
  DEBUG9(cryptoDebug, "generate_Kul_from_Ku");
  // check incoming pointer
  if (!Kul) {
    DEBUG0(cryptoDebug, "generate_Kul_from_Ku: input parameter Kul is NULL, failing");
    return(false);
  }

  const EVP_MD    *EVP_HashType = 0;
  u_int           digest_length = 0;
  
  // begin mutex section...
  if (!this->cryptoMutex->lock("snmpCrypto::generate_Kul_from_Ku")) {
    return(false);
  }

  // set values for authentication type, Sha1 and MD5 currently supported.
  if (hashtype == snmpUSMData::usmHMACMD5AuthProtocolOID) {
    EVP_HashType = EVP_md5();
    digest_length = this->MD5_Length;
  }
  else if (hashtype == snmpUSMData::usmHMACSHAAuthProtocolOID) {
    EVP_HashType = EVP_sha1();
    digest_length = this->SHA1_Length;
  }
  else {
    DEBUG0(cryptoDebug, "generate_Kul_from_Ku: unknown hashtype, failing");
    this->cryptoMutex->unlock("snmpCrypto::generate_Kul_from_Ku");
    return(false);
  }

  // create data for the next part of the algorithm.
  u_char      localized_key[digest_length];
  HMAC_CTX   *hmac_info = new HMAC_CTX;

  int     concat_length = Ku.size() + engineID.size() + Ku.size();
  u_char  concat_block[concat_length];

  // copy the char values of of the key
  Ku.get_string((char *)concat_block, Ku.size(), false);
  // concatenate on the char values of engine ID,
  engineID.get_string((char *)(concat_block + Ku.size()), 
		      (engineID.size() + 1));
  // concatenate on the char values of the key again
  Ku.get_string((char *)(concat_block + Ku.size() + engineID.size()), 
		Ku.size(), false);
  
  // create final digest, i.e. key.
  EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
  EVP_DigestUpdate(&(hmac_info->md_ctx), concat_block, concat_length);
  EVP_DigestFinal(&(hmac_info->md_ctx), localized_key, &digest_length);

  // end of mutex section
  this->cryptoMutex->unlock("snmpCrypto::generate_Kul_from_Ku");

  Kul->assign((char *)localized_key, int(digest_length)); 

  DEBUG9(cryptoDebug, "generate_Kul_from_Ku: Kul is '" << Kul->toHexString()
	 << "'");

  // clean up.
  memset(localized_key, 0, digest_length);
  memset(concat_block,  0, concat_length);

  return(true);
} // generate_Kul_from_Ku


bool
snmpCrypto::encode_keychange(OID    &hashtype, 
			     string &oldKey,
			     string &newKey,
			     string *keychange)  {
  const EVP_MD    *EVP_HashType = 0;
  DEBUG9(cryptoDebug, "encode_keychange");
  // rand_len is also the max key length, hash_len is length of the hash
  // value returned by protocol 'hashtype', delta_len is also the newkey length
  u_int rand_len=0, delta_len=0, hash_len = 0;
  u_int nkey_len = newKey.size(), okey_len = oldKey.size();
  u_char nkey[nkey_len], okey[okey_len];
  memcpy(okey, oldKey.data(), okey_len);
  memcpy(nkey, newKey.data(), nkey_len);

  // check parameters
  if (!keychange) {
    DEBUG0(cryptoDebug, "encode_keychange: keychange pointer is NULL, "
	   << "failing");
    return (false);
  }

  // make sure we support the requested hash protocol before we lock!
  if ( (hashtype != snmpUSMData::usmHMACMD5AuthProtocolOID)  &&
       (hashtype != snmpUSMData::usmHMACSHAAuthProtocolOID) ) {
    DEBUG0(cryptoDebug, "encode_keychange: unknown hashtype, failing");
    return(false);
  }

  // begin mutex section...
  if (this->cryptoMutex->lock("snmpCrypto::encode_keychange")) {

    if (hashtype == snmpUSMData::usmHMACMD5AuthProtocolOID) {
      EVP_HashType = EVP_md5();
      rand_len = hash_len = this->MD5_Length;
    }
    //  else if (hashtype == snmpUSMData::usmHMACSHAAuthProtocolOID) {
    else {  // we know from the check earlier that it is md5 or sha...
      EVP_HashType = EVP_sha1();
      rand_len = hash_len = this->SHA1_Length;
    }
    
    // we only support MD5/Sha1 at the moment.
    // both require a static key of 16/20 byte length respectively
    // If/when we support a variable length, this will have to turn ugly
    if ((hash_len != nkey_len) || (hash_len != okey_len)){
      DEBUG0(cryptoDebug, "encode_keychange: new key value has a length of "
	     << nkey_len << ",\nold key value has a length of " << okey_len
	     << ",\nrequired length is " << hash_len << ", keychange failed");
      this->cryptoMutex->unlock("snmpCrypto::encode_keychange");
      return(false);
    }
    else {
      delta_len = nkey_len;
    
      u_int buffT1_len;
      buffT1_len = (okey_len>hash_len ? (rand_len+okey_len) 
		    : (rand_len+hash_len));
      u_char rand_comp[rand_len], delta_comp[delta_len], 
	bufferT1[buffT1_len], bufferT2[hash_len];
    
      memcpy(bufferT1, okey, okey_len);
    
      // libcrypto function
      // RAND_bytes(rand_comp, rand_len);
      this->generate_random_bytes(rand_comp, rand_len);

      memcpy((bufferT1+okey_len), rand_comp, rand_len);
      u_int buffT1_used = rand_len + okey_len;
    
      u_int bytes_computed = 0;

      HMAC_CTX *hmac_info = new HMAC_CTX;
      EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
      EVP_DigestUpdate(&(hmac_info->md_ctx), bufferT1, buffT1_used);
      EVP_DigestFinal(&(hmac_info->md_ctx), bufferT2, &hash_len);

      //      HMAC(EVP_HashType, okey, okey_len, bufferT1, buffT1_used,
      //	   bufferT2, &hash_len);
    
      if (hash_len > delta_len) bytes_computed = delta_len;
      else bytes_computed = hash_len;
    
      for (u_int i=0; i < bytes_computed; i++) 
	delta_comp[i] = nkey[i] ^ bufferT2[i];
    
      if (bytes_computed < delta_len) {
	memcpy(bufferT1, bufferT2, hash_len);
	memcpy((bufferT1+hash_len), rand_comp, rand_len);
	buffT1_used = rand_len + hash_len;
      
	while ((bytes_computed+hash_len) < delta_len) {
	  EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
	  EVP_DigestUpdate(&(hmac_info->md_ctx), bufferT1, buffT1_used);
	  EVP_DigestFinal(&(hmac_info->md_ctx), bufferT2, &hash_len);
	  // 	  HMAC(EVP_HashType, okey, okey_len, bufferT1, buffT1_used,
	  // 	       bufferT2, &hash_len);
	  for (u_int i=bytes_computed; i < hash_len + bytes_computed; i++) 
	    delta_comp[i] = nkey[i] ^ bufferT2[i - bytes_computed];
	  memcpy(bufferT1, bufferT2, hash_len);
	  memcpy((bufferT1+hash_len), rand_comp, rand_len);
	  bytes_computed += hash_len;
	}
	EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
	EVP_DigestUpdate(&(hmac_info->md_ctx), bufferT1, buffT1_used);
	EVP_DigestFinal(&(hmac_info->md_ctx), bufferT2, &hash_len);
	// 	HMAC(EVP_HashType, okey, okey_len, bufferT1, buffT1_used,
	// 	     bufferT2, &hash_len);
	for (u_int i=bytes_computed; i < delta_len; i++) 
	  delta_comp[i] = nkey[i] ^ bufferT2[i - bytes_computed];    
      }
      
      keychange->assign((char *)rand_comp, int(rand_len));
      keychange->append((char *)delta_comp, int(delta_len));
    }  // else of if key hash_len != nkey_len

    this->cryptoMutex->unlock("snmpCrypto::encode_keychange");
  } // if cryptoMutex->lock
  else return(false);
    
  // clean up
  memset(nkey, 0, nkey_len);
  memset(okey, 0, okey_len);
  
  return(true);
} // encode_keychange


bool
snmpCrypto::decode_keychange(OID    &hashtype, 
			     string &oldKey,
			     string &keychange,
			     string *newKey) {
  const EVP_MD    *EVP_HashType = 0;
  DEBUG9(cryptoDebug, "decode_keychaneg");
  // rand_len is also the max key length, hash_len is length of the hash
  // value returned by protocol 'hashtype', delta_len is also the newkey length
  u_int rand_len=0, delta_len=0, hash_len = 0;
  u_int keych_len = keychange.size(), okey_len = oldKey.size();
  u_char keych[keych_len], okey[okey_len];
  memcpy(okey, oldKey.data(), okey_len);
  memcpy(keych, keychange.data(), keych_len);

  // check parameters
  if (!newKey) {
    DEBUG0(cryptoDebug, "decode_keychange: newkey pointer is NULL, failing");
    return (false);
  }

  // make sure we support the requested hash protocol before we lock!
  if ( (hashtype != snmpUSMData::usmHMACMD5AuthProtocolOID)  &&
       (hashtype != snmpUSMData::usmHMACSHAAuthProtocolOID) ) {
    DEBUG9(cryptoDebug, "decode_keychange: unknown hashtype, failing");
    return(false);
  }

  // begin mutex section...
  if (this->cryptoMutex->lock("snmpCrypto::decode_keychange")) {

    if (hashtype == snmpUSMData::usmHMACMD5AuthProtocolOID) {
      EVP_HashType = EVP_md5();
      rand_len = hash_len = this->MD5_Length;
    }
    //  else if (hashtype == snmpUSMData::usmHMACSHAAuthProtocolOID) {
    else {  // we know from the check earlier that it is md5 or sha...
      EVP_HashType = EVP_sha1();
      rand_len = hash_len = this->SHA1_Length;
    }
    
    delta_len = keych_len - rand_len;
    // we only support MD5/Sha1 at the moment.
    // both require a static length key = hash value length, 16/20 bytes 
    // respectively. Vvariable length support will change the following.
    if (delta_len != hash_len)  {
      DEBUG0(cryptoDebug, "decode_keychange: keychange variable does not\n"
	     << "            provide a new key value of the proper length.\n"
	     << "            " << hash_len << " bytes required," 
	     << delta_len << " bytes provided, failing");
      this->cryptoMutex->unlock("snmpCrypto::decode_keychange");
      return(false);
    }

    u_int buffT1_len;
    buffT1_len = (okey_len>hash_len ? (rand_len+okey_len) 
		  : (rand_len+hash_len));
    u_char rand_comp[rand_len], delta_comp[delta_len], 
      bufferT1[buffT1_len], bufferT2[hash_len];
    
    memcpy(bufferT1, okey, okey_len);
    memcpy(rand_comp, keych, rand_len);
    
    memcpy((bufferT1+okey_len), rand_comp, rand_len);
    u_int buffT1_used = rand_len + okey_len;
    
    HMAC_CTX *hmac_info = new HMAC_CTX;
    EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
    EVP_DigestUpdate(&(hmac_info->md_ctx), bufferT1, buffT1_used);
    EVP_DigestFinal(&(hmac_info->md_ctx), bufferT2, &hash_len);
    //     HMAC(EVP_HashType, okey, okey_len, bufferT1, buffT1_used,
    // 	 bufferT2, &hash_len);
    
    u_int bytes_computed = 0;
    if (hash_len > delta_len) bytes_computed = delta_len;
    else bytes_computed = hash_len;
    
    for (u_int i=0; i < bytes_computed; i++) 
      delta_comp[i] = keych[i+rand_len] ^ bufferT2[i];
    
    if (bytes_computed < delta_len) {
      DEBUG0(cryptoDebug, "decode_keychange: key length is different"
	     << "than hash type, what the hash is going on?");

      memcpy(bufferT1, bufferT2, hash_len);
      memcpy((bufferT1+hash_len), rand_comp, rand_len);
      buffT1_used = rand_len + hash_len;
      
      while ((bytes_computed+hash_len) < delta_len) {
	EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
	EVP_DigestUpdate(&(hmac_info->md_ctx), bufferT1, buffT1_used);
	EVP_DigestFinal(&(hmac_info->md_ctx), bufferT2, &hash_len);
	// 	HMAC(EVP_HashType, okey, okey_len, bufferT1, buffT1_used,
	// 	     bufferT2, &hash_len);
	for (u_int i=bytes_computed; i < hash_len + bytes_computed; i++) 
	  delta_comp[i] = keych[i+rand_len] ^ bufferT2[i - bytes_computed];
	memcpy(bufferT1, bufferT2, hash_len);
	memcpy((bufferT1+hash_len), rand_comp, rand_len);
	bytes_computed += hash_len;
      }
      EVP_DigestInit(&(hmac_info->md_ctx), EVP_HashType);
      EVP_DigestUpdate(&(hmac_info->md_ctx), bufferT1, buffT1_used);
      EVP_DigestFinal(&(hmac_info->md_ctx), bufferT2, &hash_len);
      //       HMAC(EVP_HashType, okey, okey_len, bufferT1, buffT1_used,
      // 	   bufferT2, &hash_len);
      for (u_int i=bytes_computed; i < delta_len; i++) 
	delta_comp[i] = keych[i+rand_len] ^ bufferT2[i - bytes_computed];    
    }
      
    this->cryptoMutex->unlock("snmpCrypto::decode_keychange");
    newKey->assign((char *)delta_comp, int(delta_len));
    // clean up
    memset(delta_comp, 0, delta_len);
    memset(okey, 0, okey_len);
  } // if cryptoMutex->lock
  else return(false);
  
  return(true);
} // decode_keychange


bool
snmpCrypto::generate_keyed_hash(OID         &hashtype,
				OctetString &key,
				u_char      *message, u_int msg_len,
				OctetString *MAC)  {
  DEBUG9(cryptoDebug, "generate_keyed_hash");
  // check incoming pointer
  if ((!MAC) || (!message)) {
    DEBUG0(cryptoDebug, "generate_keyed_hash: input parameter 'MAC' or "
	   << "'message' is NULL, failing");
    return(false);
  }

  const EVP_MD    *EVP_HashType = 0;
  u_int      mac_length = 12, hmac_out_length = EVP_MAX_MD_SIZE;
  u_char     hmac_output[hmac_out_length];

  // begin mutex section...
  if (!this->cryptoMutex->lock("snmpCrypto::generate_keyed_hash")) {
    return(false);
  }

  // set values for authentication type, Sha1 and MD5 currently supported.
  if (hashtype == snmpUSMData::usmHMACMD5AuthProtocolOID) {
    EVP_HashType = EVP_md5();
  }
  else if (hashtype == snmpUSMData::usmHMACSHAAuthProtocolOID) {
    EVP_HashType = EVP_sha1();
  }
  else {
    DEBUG0(cryptoDebug, "generate_keyed_hash: unknown hashtype, failing");
    this->cryptoMutex->unlock("snmpCrypto::generate_keyed_hash");
    return(false);
  }

  u_int   key_len = key.size();
  u_char   *u_key = new u_char[key_len+1];
  key.get_string((char *)u_key, int(key_len), false);

  HMAC(EVP_HashType, u_key, key_len, message, msg_len,
       hmac_output, &hmac_out_length);
  
  // end of mutex section
  this->cryptoMutex->unlock("snmpCrypto::generate_keyed_hash");

  if (mac_length > hmac_out_length) mac_length = hmac_out_length;

  MAC->assign((char *)hmac_output, int(mac_length));

  // cleanup
  memset(u_key, 0, key_len);
  delete [] u_key;
  
  return(true);
}  // generate_keyed_hash


bool
snmpCrypto::check_keyed_hash(OID         &hashtype,
			     OctetString &key,
			     u_char      *message, u_int msg_len,
			     OctetString &MAC) {

  DEBUG9(cryptoDebug, "check_keyed_hash");

  OctetString newMAC;

  generate_keyed_hash(hashtype, key, message, msg_len, &newMAC);

  return (MAC == newMAC);
} // check_keyed_hash


bool
snmpCrypto::encrypt(OID         &privtype,
		    OctetString &key,
		    char        *iv_array,   int iv_len,
		    char        *plaintext,  int ptext_len,
		    OctetString *ciphertext)  {
  DEBUG9(cryptoDebug, "encrypt");

  // according ssleay.txt, an array of eight bytes. 
  // let's just hope it doesn't change? yuck.
  DES_cblock        key_array, loc_iv;
#ifdef OLD_DES
    DES_key_schedule loc_key_sched;
#else
    DES_key_schedule key_sched_store;
    DES_key_schedule *loc_key_sched = &key_sched_store;
#endif

  unsigned int  cblock_len;
  unsigned int  key_len; 
  unsigned char end_block[256];
  int  end_data_len, total_msg_len;
  long resized_msg_len;
  encr_types etype;
  u_char *output;
#ifdef HAVE_AES
  u_char aes_init_key[32];
  AES_KEY aes_key;
  int new_ivlen = 0;
#endif
    
  if (privtype == snmpUSMData::usmDESPrivProtocolOID) {
      key_len = cblock_len = sizeof(DES_cblock);
      etype = EDES;
      if ( ((unsigned int)(iv_len) != cblock_len) || (key_len != cblock_len) ||
           (!ciphertext) || (!iv_array) ) {
          DEBUG0(cryptoDebug, "encrypt: Bad parameter(s)!: iv_len: '"
                 << iv_len << "' key_len: '" << key_len << "' *ciphertext: '" 
                 << int(ciphertext) 
                 << "' iv_array '" << int(iv_array) << "', failing");
          return(false);
      }
#ifdef HAVE_AES
  } else if (privtype == snmpUSMData::usmAESPrivProtocolOID) {
      etype = EAES;
      cblock_len = 128;
      // XXX: param checking
#endif
  } else {
      DEBUG0(cryptoDebug, "encrypt: Unknown privacy type, failing");
      return(false);
  }

  memcpy(loc_iv, iv_array, iv_len);

  // begin mutex section...
  if (!this->cryptoMutex->lock("snmpCrypto::encrypt")) {
    return(false);
  }

  switch(etype) {
  case EDES:
      // extract the key
      key.get_string((char *)key_array, &key_len, false);

      // get cpu specific key
      DES_key_sched(&key_array, loc_key_sched);
    
      // do message end padding computations. DES CBC needs 
      // a buffer that is a multiple of 8 bytes long...
      end_data_len    = ptext_len % cblock_len;
      resized_msg_len = ptext_len - end_data_len;
      total_msg_len   = resized_msg_len + cblock_len; 

      // do we care about pad data?, is random data better?
      // RFC says it's 'irrelevant' so...
      // memset(end_block[end_data_len], 0, (cblock_len - end_data_len));
      memcpy(end_block, (plaintext + resized_msg_len), end_data_len);

      // make a buffer for ciphertext
      output = new u_char[total_msg_len];

      // encrypt resized message
      DES_ncbc_encrypt((u_char *)plaintext, 
                       (u_char *)output, 
                       resized_msg_len, loc_key_sched, // CPU specific key value
                       &loc_iv, DES_ENCRYPT);
      // DES_ENCRYPT specifies encryption and is non-zero 
      // zero specifices decryption.
      // encrypt end block
      DES_ncbc_encrypt(end_block, 
                       (u_char *)(output + resized_msg_len), 
                       cblock_len, loc_key_sched, 
                       &loc_iv, DES_ENCRYPT);

      /* cleanup */
      memset(key_array, 0, cblock_len);
      memset(loc_key_sched, 0, sizeof(DES_key_schedule));
      memset(end_block, 0, cblock_len);
      break;

#ifdef HAVE_AES
  case EAES:
      // set up the encryption key
      key_len = 16; 
      key.get_string((char *)aes_init_key, &key_len, false);
      (void) AES_set_encrypt_key(aes_init_key, 128, &aes_key);

      // set up the resulting output length
      total_msg_len = ptext_len;

      // make a buffer for ciphertext
      output = new u_char[total_msg_len];

      // do the encryption
      AES_cfb128_encrypt((u_char *) plaintext, output, ptext_len,
                         &aes_key, loc_iv, &new_ivlen, AES_ENCRYPT);

      // clear the key from memory
      memset(&aes_key, 0, sizeof(aes_key));
      break;
#endif
  }
  
  // end of mutex section
  this->cryptoMutex->unlock("snmpCrypto::encrypt");

  // put the ciphertext in the return var
  ciphertext->assign((char *) output, total_msg_len);

  // clean up
  delete [] output;

  return(true);
} // encrypt


bool
snmpCrypto::decrypt(OID         &privtype,
		    OctetString &key,
		    char        *iv_array, int iv_len,
		    OctetString &ciphertext,
		    string      *plaintext)  {
  DEBUG9(cryptoDebug, "decrypt");

  // according ssleay.txt, an array of eight bytes. 
  // let's just hope it doesn't change? yuck.
  DES_cblock        key_array, loc_iv;
#ifdef OLD_DES
    DES_key_schedule loc_key_sched;
#else
    DES_key_schedule key_sched_store;
    DES_key_schedule *loc_key_sched = &key_sched_store;
#endif

  int  cblock_len = sizeof(DES_cblock); // should be 8
  unsigned int  key_len = cblock_len; 
  encr_types etype;
  u_char *ptext, *ctext;
  int buff_length;

#ifdef HAVE_AES
  u_char aes_init_key[32];
  int new_ivlen = 0;
  AES_KEY aes_key;
#endif
    
  if (privtype == snmpUSMData::usmDESPrivProtocolOID) {
      cblock_len = sizeof(DES_cblock);
      etype = EDES;
      if ( (iv_len != cblock_len) || (key_len != cblock_len) ||
           (!plaintext) || (!iv_array) ) {
          DEBUG0(cryptoDebug, "decrypt: Bad parameter(s)!: iv_len: '" << iv_len <<
                 "' key_len: '" << key_len << "' *plaintext: '" << int(plaintext) 
                 << "' iv_array: '" << int(iv_array) << "', failing");
          return(false);
      }
#ifdef HAVE_AES
  } else if (privtype == snmpUSMData::usmAESPrivProtocolOID) {
      etype = EAES;
      cblock_len = 128;
      // XXX: param checking
#endif
  } else {
    DEBUG0(cryptoDebug, "decrypt: Unknown privacy type, failing");
    return(false);
  }

  memcpy(loc_iv, iv_array, iv_len);

  // begin mutex section...
  if (!this->cryptoMutex->lock("snmpCrypto::decrypt")) {
    return(false);
  }

  switch(etype) {
  case EDES:
      // get cpu specific key
      key.get_string((char *)key_array, (unsigned int *)&key_len, false);
      DES_key_sched(&key_array, loc_key_sched);
 
      // make a buffer for plaintext & ciphertext (DES is one to one)
      buff_length = ciphertext.size();
      ctext = new u_char[buff_length];
      ptext = new u_char[buff_length];
      ciphertext.get_string((char *)ctext, buff_length, false);

      DES_ncbc_encrypt(ctext, 
                       ptext, 
                       buff_length, loc_key_sched, 
                       &loc_iv, DES_DECRYPT);
      break;
      
#ifdef HAVE_AES
  case EAES:
      // set up the encryption key
      key_len = 16; 
      key.get_string((char *)aes_init_key, &key_len, false);
      (void) AES_set_encrypt_key(aes_init_key, 128, &aes_key);

      // set up the resulting output
      buff_length = ciphertext.size();
      ctext = new u_char[buff_length];
      ptext = new u_char[buff_length];
      ciphertext.get_string((char *)ctext, buff_length, false);

      // do the encryption
      AES_cfb128_encrypt(ctext, ptext, buff_length,
                         &aes_key, loc_iv, &new_ivlen, AES_DECRYPT);

      // clear the key from memory
      memset(&aes_key, 0, sizeof(aes_key));
      break;

#endif
  }
  
  // end of mutex section
  this->cryptoMutex->unlock("snmpCrypto::decrypt");

  // put the plaintext in the return var
  plaintext->assign((char *)ptext, buff_length);
  
  // clean up
  memset(ptext, 0, buff_length);
  delete [] ptext;
  delete [] ctext;

  return(true);
} // decrypt

