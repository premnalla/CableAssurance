#ifndef SNMPSECURITYSTATEREFERENCE_H
#define SNMPSECURITYSTATEREFERENCE_H

#include "snmpObj.H"

#include "OctetString.H"
#include "OID.H"

class snmpSecurityStateReference : public snmpObj {
  
  friend class snmpUSMArchObj;

private:
  
  snmpSecurityStateReference() {};

  snmpSecurityStateReference(const snmpSecurityStateReference& ref) 
      : snmpObj(ref) {
    userName = ref.userName;
    authProtocol = ref.authProtocol;
    authKey = ref.authKey;
    privProtocol = ref.privProtocol;
    privKey = ref.privKey;
  }

  OctetString     userName;

  OID             authProtocol;
  OctetString     authKey;

  OID             privProtocol;
  OctetString     privKey;

  virtual snmpSecurityStateReference* clone() const {
    return new snmpSecurityStateReference( *this );
  }
};

#endif /* SNMPSECURITYSTATEREFERENCE_H */
