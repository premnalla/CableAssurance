#ifndef SNMPCRREGISTEROBJ_H
#define SNMPCRREGISTEROBJ_H

#include "snmpMessageObj.H"
#include "asnDataTypes.H"

#define SNMP_CR_ARCH_NAME "CR"

class snmpCRRegisterObj : public snmpMessageObj {
  
 private:
   OID *registerAt;
   OctetString *context;

 public:
   snmpCRRegisterObj(OID *registerAt, OctetString *context);
      
   virtual ~snmpCRRegisterObj();

   void set_registerAt(OID  *registerAt);
   OID  *get_registerAt(bool extract=false);
   void set_context(OctetString  *context);
   OctetString  *get_context(bool extract=false);
};

#endif /* SNMPCRREGISTEROBJ_H */
