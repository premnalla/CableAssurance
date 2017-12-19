#ifndef SNMPKEYCHANGE_H
#define SNMPKEYCHANGE_H

#include <OctetString.H>
#include <snmpRowDependent.H>

#include "snmpCrypto.H"

class KeyChange : public OctetString, public snmpRowDependent {
private:
  snmpCrypto  crypto;

public:
  KeyChange(snmpDataTable *table = NULL, snmpRow *row = NULL);
  KeyChange(KeyChange const &thecopy);
  virtual ~KeyChange();
  
  virtual KeyChange *clone() const { return new KeyChange(*this); };

  virtual KeyChange &change_value(const asnDataType &newValue);
};

#endif /* SNMPKEYCHANGE_H */
