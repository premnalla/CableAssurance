// snmpCRRegisterData.H

#ifndef SNMPCRREGISTERDATA_H
#define SNMPCRREGISTERDATA_H

#include "snmpCRRegisterObj.H"
#include "snmpScalarData.H"

#define SNMP_CR_ARCH_NAME "CR"

class snmpCRRegisterScalarSet : public snmpCRRegisterObj {
  
 private:
   snmpScalarData *data;

 public:
   snmpCRRegisterScalarSet(snmpScalarData *data,
                           OID *registerAt,
                           OctetString *context);
      
   virtual ~snmpCRRegisterScalarSet();

   void set_scalarData(snmpScalarData  *data);
   snmpScalarData  *get_scalarData(bool extract=false);
};

#endif /*  SNMPCRREGISTERDATA_H  */
