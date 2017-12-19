#ifndef SNMPUSMARCHREGOBJ_H
#define SNMPUSMARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpUSMArchObj.H"

class snmpUSMArchRegObj : public snmpArchRegObj {

protected:

public:

  snmpUSMArchRegObj(snmpEngine *enginePtr);
  virtual ~snmpUSMArchRegObj();

  virtual void init();

};


#endif /* SNMPUSMARCHREGOBJ_H ifndef */
