#ifndef OPENSNMPKEY
#define OPENSNMPKEY

#include "opensnmpKey.H"

opensnmpKey::opensnmpKey() {
  keyType = PASSPHRASE;
}

opensnmpKey::opensnmpKey(char *init, unsigned int len, 
		 keyType_Enum keyT) : 
  OctetString(init, len) 
{
  keyType = keyT;
}


opensnmpKey::opensnmpKey(const std::string &init, keyType_Enum keyT) :
  OctetString(init)
{
  keyType = keyT;
}

opensnmpKey::opensnmpKey(const opensnmpKey &thecopy) :
  OctetString(thecopy)
{
  this->keyType = thecopy.keyType;
}

opensnmpKey::opensnmpKey(OctetString &OC_Key, keyType_Enum keyT) :
  OctetString(OC_Key)
{
  keyType = keyT;
}


#endif  /* ifndef OPENSNMPKEY */
