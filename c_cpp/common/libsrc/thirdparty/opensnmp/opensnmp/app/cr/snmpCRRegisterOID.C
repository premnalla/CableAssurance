#include "config.h"
#include "snmpCRRegisterOID.H"

snmpCRRegisterOID::snmpCRRegisterOID(MIBRegistration *mibRegistration) {
    this->theMIBRegistration = mibRegistration;
}

snmpCRRegisterOID::~snmpCRRegisterOID() {
    if (this->theMIBRegistration)
        delete this->theMIBRegistration;
}

void
snmpCRRegisterOID::set_MIBRegistration(MIBRegistration  *theMIBRegistration) {
  if (this->theMIBRegistration)
    delete this->theMIBRegistration;
  this->theMIBRegistration = theMIBRegistration;
}

MIBRegistration  *
snmpCRRegisterOID::get_MIBRegistration(bool extract) {
  MIBRegistration  *ret = theMIBRegistration;
  if (extract)
    theMIBRegistration = NULL;
  return ret;
}
