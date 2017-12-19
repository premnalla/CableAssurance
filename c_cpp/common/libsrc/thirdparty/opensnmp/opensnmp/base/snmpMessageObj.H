#ifndef SNMPMESSAGEOBJ_H
#define SNMPMESSAGEOBJ_H

#include "snmpConstants.H" // snmp constants lifted from ucd-snmp
#include "snmpObj.H"
#include "snmpMutexObj.H"
#include "snmpSynchDataObj.H"
#include "snmpFIFOObj.H"
#include "debug.H"
#include <iostream>

// class snmpSynchDataObj declaration needed for inclusion recursion. 
// Both snmpMessageObj and snmpSynchData include each other. So depending 
// on #include order, one or the other will not be defined before it is used.
class snmpSynchDataObj;

// class snmpFIFOObj defined later.
class snmpFIFOObj;

class snmpMessageObj : public snmpObj {
  
private:
  int                   internalMsgID;
  snmpSynchDataObj     *synchData;
  snmpFIFOObj          *returnFIFO;

  static snmpMutexObj  *msgCountMutex;
  static int            currentMsgCount;

  DEBUGDECLARE_L(msgDebugObj);

public:
  // you can pass in the value for the internalMsgID during construction.
  // Otherwise, the internalMsgID of the copyFromMsg will be used. If this
  // is NULL, a unique ID will be generated.
  // The synch data object can also be passed in at construction time.
  snmpMessageObj(snmpMessageObj   *copyFromMsg = NULL,
		 int               newID       = 0,
		 snmpSynchDataObj *newData     = NULL,
		 snmpFIFOObj      *theFIFO     = NULL);
  snmpMessageObj(const snmpMessageObj & );


  virtual ~snmpMessageObj();

  // returns the message ID
  virtual int get_ID();

  bool  addSynchData(snmpSynchDataObj *newData);

  snmpSynchDataObj *get_synchData(bool extract=false);

  bool  set_returnFIFO(snmpFIFOObj *returnFIFO);

  snmpFIFOObj *get_returnFIFO(bool extract=false);

  virtual snmpMessageObj *clone() const;
};

// generic class used to signal exiting
// NOTE: after receiving this message, do not 
//       lock the regMap mutex!
class snmpExitMessage : public snmpMessageObj {
};

#endif /* SNMPMESSAGEOBJ_H */
