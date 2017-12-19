#ifndef SNMPARCHREGOBJ_H
#define SNMPARCHREGOBJ_H

#include "snmpObj.H"
#include "snmpFIFOObj.H"
#include "snmpMutexObj.H"

#include <iostream>
#include <map>
#include <string>

using namespace std;

// ArchRegObj class;
//   This is the that is used to start a object from the registry.
class snmpEngine;

class snmpArchRegObj  {

private:
public:

  // snmpFIFOObj
  snmpFIFOObj      *fifo;
  snmpEngine       *enginePtr;

  snmpMutexObj     *exitMutex;
  snmpConditionObj *exitCondition;

  string            archName;

  // public:
  snmpArchRegObj();
  snmpArchRegObj(snmpEngine *enginePtr);
  virtual ~snmpArchRegObj();

  virtual void init()=0;

  // creates the this->fifo object, can be overwritten to create  your own 
  // FIFOObj. Returns a pointer to the fifo.
  virtual snmpFIFOObj *create_fifo();
  
  virtual snmpFIFOObj *get_fifo() { return(this->fifo); };

};
  
#endif  /* SNMPARCHREGOBJ_H */
