#include "config.h"
#include "snmpCRRegisterScalarSet.H"

snmpCRRegisterObj::snmpCRRegisterObj(OID *registerAt, OctetString *context) {
    this->registerAt = registerAt;
    this->context = context;
}

snmpCRRegisterObj::~snmpCRRegisterObj() {
    if (this->registerAt)
        delete this->registerAt;
    if (this->context)
        delete this->context;
}

void
snmpCRRegisterObj::set_registerAt(OID  *registerAt) {
  if (this->registerAt)
    delete this->registerAt;
  this->registerAt = registerAt;
}

OID  *
snmpCRRegisterObj::get_registerAt(bool extract) {
  OID  *ret = registerAt;
  if (extract)
    registerAt = NULL;
  return ret;
}

void
snmpCRRegisterObj::set_context(OctetString  *context) {
  if (this->context)
    delete this->context;
  this->context = context;
}

OctetString  *
snmpCRRegisterObj::get_context(bool extract) {
  OctetString  *ret = context;
  if (extract)
    context = NULL;
  return ret;
}
