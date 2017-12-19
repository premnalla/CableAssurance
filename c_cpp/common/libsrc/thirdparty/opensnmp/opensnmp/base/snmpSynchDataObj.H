#ifndef SNMPSYNCHDATAOBJ_H
#define SNMPSYNCHDATAOBJ_H

#include "snmpObj.H"
#include "snmpMessageObj.H"
#include <iostream>

// class snmpMessagoObj declaration needed for inclusion recursion. 
// Both snmpMessageObj and snmpSynchData include each other. So depending 
// on #include order, one or the other will not be defined before it is used.
class snmpMessageObj;


class snmpSynchDataObj : public snmpObj {
  

private:
  snmpMessageObj    *msgObject;
  //  asnDataType       *extraData1;
  snmpObj           *extraData1;

public:
  snmpSynchDataObj(snmpMessageObj *newMsgObj  = NULL,
		   asnDataType    *extraData1 = NULL);

  snmpSynchDataObj(snmpSynchDataObj const &synchObj);

  virtual ~snmpSynchDataObj();

  virtual snmpSynchDataObj *clone() const {
    return (new snmpSynchDataObj(*this));
  };

  snmpMessageObj *get_msgObject(bool extract = false);
  void set_msgObject(snmpMessageObj *msgObject);

  //  DEFINE_PTR_ACCESSOR(snmpMessageObj,  msgObject, );
  DEFINE_PTR_ACCESSOR(snmpObj,         extraData1, );

};

#endif /* SNMPSYNCHDATAOBJ_H */
