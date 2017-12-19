#ifndef SNMPCGARCHREGOBJ_H
#define SNMPCGARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpCGArch.H"

#define SNMP_CG_ARCH_NAME "CGARCH"

class snmpCGArchRegObj : public snmpArchRegObj {

protected:

public:

  snmpCGArchRegObj(snmpEngine *theEngine);
  virtual ~snmpCGArchRegObj();

  virtual void init();

};


#endif /* SNMPCGARCHREGOBJ_H ifndef */
