#ifndef SNMPVACMARCHREGOBJ_H
#define SNMPVACMARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpVACMArchObj.H"

#define SNMP_VACM_ARCH_NAME "VACM"

class snmpVACMArchRegObj : public snmpArchRegObj {

protected:

public:

  snmpVACMArchRegObj();
  virtual ~snmpVACMArchRegObj();

  virtual void init();

};


#endif /* SNMPVACMARCHREGOBJ_H ifndef */
