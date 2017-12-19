#ifndef SNMPREGOBJ_H
#define SNMPREGOBJ_H

#include "snmpObj.H"
#include "snmpFIFOObj.H"
#include "snmpArchRegObj.H"
#include "snmpMutexObj.H"

#include <iostream>
#include <map>
#include <string>

using std::string;

// regObj class;//   This is the object that controls access to the registry.
//   It will be included as part of objects that need to make calls
//   to the registry or it will be created for the spefic task...


class snmpRegObj : public snmpObj  {

private:
  static  std::map<string, snmpArchRegObj *>  regMap;
  static  snmpMutexObj                  *regMap_mutex;
  static  void *initializeArch(snmpArchRegObj *archRegPtr);
  static  const int exitTimeOut;
  static  bool      exiting;

public:
  snmpRegObj();
  virtual ~snmpRegObj();

  static  void exitArches(std::list<string> &archNames);
  static  void exitAllArches();

  
  // clone not supported by reg object;
  virtual snmpRegObj *clone() const {
    cerr << "ERROR:snmpRegOjb::clone() isn't implemented! " << endl;
    throw;
  }

  virtual string toString();

  virtual bool addNewArchInit(string  &newName, snmpArchRegObj  *newObj);
 
  snmpFIFOObj *findArchFIFO(const string &searchName);
  
  virtual void dumpMap();

};

#endif  /* SNMPREGOBJ_H */
