#ifndef SNMPKSMARCHREGOBJ_H
#define SNMPKSMARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpKSMArchObj.H"

class snmpKSMArchRegObj : public snmpArchRegObj {

protected:

public:

  snmpKSMArchRegObj(snmpEngine *enginePtr);
  virtual ~snmpKSMArchRegObj();

  virtual void init();

};


#endif /* SNMPKSMARCHREGOBJ_H ifndef */
