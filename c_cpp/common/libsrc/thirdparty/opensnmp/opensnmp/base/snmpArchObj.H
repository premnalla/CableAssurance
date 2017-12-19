#ifndef SNMPARCHOBJ_H
#define SNMPARCHOBJ_H

#include "snmpObj.H"

class snmpArchObj : public snmpObj {
  
protected:

  int kind;

public:
   
  snmpArchObj();
  virtual ~snmpArchObj();

  virtual snmpArchObj *clone() const;

};

#endif /* SNMPARCHOBJ_H */
