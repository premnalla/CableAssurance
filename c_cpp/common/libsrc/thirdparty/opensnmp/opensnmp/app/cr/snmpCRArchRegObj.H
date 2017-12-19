#ifndef SNMPCRARCHREGOBJ_H
#define SNMPCRARCHREGOBJ_H

#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpEngine.H"
#include "TransportAddress.H"
#include <set>

class snmpCRArchRegObj : public snmpArchRegObj {
 private:
  snmpEngine *theEngine;
  set<TransportAddress> *listenTAddresses;

 protected:

 public:
  snmpCRArchRegObj(snmpEngine *theEngine, 
		   std::set<TransportAddress> *listenTAddresses = NULL);
  virtual ~snmpCRArchRegObj();

  virtual void init();

};


#endif /* SNMPCRARCHREGOBJ_H ifndef */
