#ifndef SNMPSNMPERROROBJ_H
#define SNMPSNMPERROROBJ_H

#include<string>
#include "snmpErrorObj.H"
#include "VarBindList.H"
#include "Integer32.H"
#include "OctetString.H"

class snmpSNMPErrorObj : public snmpErrorObj {

public:
  int           snmpErrorValue;
  Integer32     *securityLevel;
  OctetString   *contextName;
  OctetString   *contextEngineID;

  VarBindList  *snmpErrorList;  

  snmpSNMPErrorObj(int newError = int(NO_ERROR));
  snmpSNMPErrorObj(VarBindList *newList, int newError = 0);

  virtual ~snmpSNMPErrorObj();
  
  virtual Integer32 *get_securityLevel(bool extract = false);
  virtual void       set_securityLevel(Integer32 *newVal);

  virtual OctetString *get_contextName(bool extract = false);
  virtual void         set_contextName(OctetString *newVal);

  virtual OctetString *get_contextEngineID(bool extract = false);
  virtual void         set_contextEngineID(OctetString *newVal);

  virtual VarBindList *get_varBindList(bool extract = false);
  virtual void         set_varBindList(VarBindList *newList);

  virtual int  get_SNMPError();
  virtual void set_SNMPError(int newError);

  virtual string toString();

};


#endif  // SNMPSNMPERROROBJ_H
