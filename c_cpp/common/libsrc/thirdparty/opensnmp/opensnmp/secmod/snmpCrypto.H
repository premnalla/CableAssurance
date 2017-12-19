#ifndef SNMPCRYPTO_H
#define SNMPCRYPTO_H

#include "snmpObj.H"
#include "asnDataTypes.H"
#include "BufferClass.H"
#include "snmpMutexObj.H"
#include "debug.H"
#include "opensnmpKey.H"

#include <openssl/hmac.h>
#include <openssl/evp.h>
#include <openssl/rand.h>
#include <openssl/des.h>
#ifdef HAVE_AES
#include <openssl/aes.h>
#endif

class snmpCrypto : public snmpObj {
private:
  static  snmpMutexObj* cryptoMutex;
  DEBUGDECLARE(cryptoDebug);

public:
  snmpCrypto();
  snmpCrypto(const snmpCrypto&);

  virtual ~snmpCrypto();

  // constants
  static const u_int     MD5_Length = 16;
  static const u_int    SHA1_Length = 20;

  // methods
  static void   generate_random_bytes(unsigned char *buf, int num) 
                  { RAND_bytes(buf, num);}

  bool  generate_Kul(OID         &hashtype,
		     OctetString &engineID,
		     opensnmpKey &Key,
		     OctetString *Kul);
 
  bool  passphrase_to_key(OID         &hashtype,
			  OctetString &Pass,
			  OctetString *Key);
 
  bool  generate_Kul_from_Ku(OID         &hashtype, 
			     OctetString &engineID,
			     OctetString &Ku,
			     OctetString *Kul);

  bool  encode_keychange(OID    &hashtype, 
			 string &oldKey,
			 string &newKey,
			 string *keychange);

  bool  decode_keychange(OID    &hashtype, 
			 string &oldKey,
			 string &keychange,
			 string *newKey);

  bool  generate_keyed_hash(OID         &hashtype,
			    OctetString &key,
			    u_char      *message, u_int msg_len,
			    OctetString *MAC);

  bool  check_keyed_hash(OID         &hashtype,
			 OctetString &key,
			 u_char      *message, u_int msg_len,
			 OctetString &MAC);

  bool  encrypt(OID         &privtype,
		OctetString &key,
		char        *iv_array,  int iv_len,
		char        *plaintext, int ptext_len,
		OctetString *ciphertext);

  bool  decrypt(OID         &privtype,
		OctetString &key,
		char        *iv_array,  int iv_len,
		OctetString &ciphertext,
		string      *plaintext);

  virtual snmpCrypto *clone() const { return new snmpCrypto(*this); }

};

#endif /* SNMPCRYPTO_H */
