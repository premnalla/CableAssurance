#ifndef SNMPMPARCHREGOBJ_H
#define SNMPMPARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpMPArchObj.H"

class snmpMPArchRegObj : public snmpArchRegObj {

protected:

public:

  snmpMPArchRegObj(snmpEngine *theEngine);
  virtual ~snmpMPArchRegObj();

  virtual void init();

};


#endif /* SNMPMPARCHREGOBJ_H ifndef */
