#include "config.h"
#include "snmpCRRegisterScalarSet.H"

snmpCRRegisterScalarSet::snmpCRRegisterScalarSet(snmpScalarData *data, 
                                                 OID *registerAt, 
                                                 OctetString *context)
    : snmpCRRegisterObj(registerAt, context)
{
    this->data = data;
}

snmpCRRegisterScalarSet::~snmpCRRegisterScalarSet() {
    if (this->data)
        delete this->data;
}

void
snmpCRRegisterScalarSet::set_scalarData(snmpScalarData  *data) {
  if (this->data)
    delete this->data;
  this->data = data;
}

snmpScalarData  *
snmpCRRegisterScalarSet::get_scalarData(bool extract) {
  snmpScalarData  *ret = data;
  if (extract)
    data = NULL;
  return ret;
}

