#ifndef SNMPGLOBALDATA_H
#define SNMPGLOBALDATA_H

#include "snmpObj.H"


class snmpGlobalData : public snmpObj {

private:

public:

  snmpGlobalData();
  virtual ~snmpGlobalData();

  virtual snmpGlobalData *clone() const { return new snmpGlobalData(*this); };

};


#endif /* SNMPGLOBALDATA_H ifndef */
