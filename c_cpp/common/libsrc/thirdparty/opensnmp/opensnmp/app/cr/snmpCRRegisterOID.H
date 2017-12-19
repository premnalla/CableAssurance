// snmpCRRegisterOID.H

#ifndef SNMPCRREGISTEROID_H
#define SNMPCRREGISTEROID_H

#include "snmpMessageObj.H"
#include "MIBRegistration.H"
#include "asnDataTypes.H"

class snmpCRRegisterOID : virtual public snmpMessageObj {
  
 private:

   MIBRegistration *theMIBRegistration;
   
 public:
   
   snmpCRRegisterOID(MIBRegistration *mibRegistration);
   virtual ~snmpCRRegisterOID();

   void set_MIBRegistration(MIBRegistration  *MIBRegistration);
   MIBRegistration  *get_MIBRegistration(bool extract=false);
};

#endif /*  SNMPCRREGISTEROID_H  */
