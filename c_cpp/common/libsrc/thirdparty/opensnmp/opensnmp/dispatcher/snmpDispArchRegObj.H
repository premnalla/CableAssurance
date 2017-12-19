#ifndef SNMPDISPARCHREGOBJ_H
#define SNMPDISPARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpDispArchObj.H"

#define SNMP_DISP_ARCH_NAME "Disp"

class snmpEngine;

class snmpDispArchRegObj : public snmpArchRegObj {
protected:

public:

  snmpDispArchRegObj(snmpEngine *enginePtr);
  virtual ~snmpDispArchRegObj();

  virtual void init();

  virtual snmpDispArchRegObj *clone() const {
    cerr << "ERROR:snmpDispArchRegObj::clone not implemented!" << endl;
    throw;
  }
};


#endif /* SNMPDISPARCHREGOBJ_H ifndef */
