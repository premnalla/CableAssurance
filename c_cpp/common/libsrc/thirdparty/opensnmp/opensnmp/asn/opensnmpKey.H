#ifndef OPENSNMPKEY_H
#define OPENSNMPKEY_H

#include "asnDataType.H"
#include "OctetString.H"
#include "Integer32.H"
#include "ScopedPDU.H"
#include "HeaderData.H"


class opensnmpKey : public OctetString {

public:
  enum keyType_Enum {
    PASSPHRASE,
    LOCALIZED_KEY,// must be = to hash length, (e.g. SHA 20 octets)
    KEY,          // key length must be >= hash length, it will be
		  // truncated if longer.
    LARGEKEY      // key length must be >= hash length, it will NOT
		  // be truncated if longer.
  };

private:
  keyType_Enum keyType;

  
public:
  opensnmpKey();
  opensnmpKey(char *init, unsigned int len = 0, 
	      keyType_Enum keyT = PASSPHRASE);
  opensnmpKey(const std::string &init, keyType_Enum keyT = PASSPHRASE);
  opensnmpKey(const opensnmpKey &thecopy);
  opensnmpKey(OctetString &OC_Key, keyType_Enum keyT = PASSPHRASE);
  
  virtual opensnmpKey *clone() const 
    { return new opensnmpKey(*this); };

  DEFINE_ACCESSOR(keyType_Enum, keyType, );

};

#endif /* OPENSNMPKEY_H */
