#ifndef SNMPFIFOOBJ_H
#define SNMPFIFOOBJ_H

#include "snmpObj.H"
#include "snmpMessageObj.H"
#include "snmpMutexObj.H"
#include "debug.H"

#include <iostream>
#include <queue>
#include <pthread.h>

using namespace std;

// class snmpMessageObj may be defined after snmpFIFOObj;
class snmpMessageObj;

class snmpFIFOObj : virtual public snmpObj {
  
protected:

private:

  bool                    isWaiting;
  queue<snmpMessageObj *> fifoQueue;
  snmpConditionObj       *fifoCond;
  snmpMutexObj           *fifoMutex;
  DEBUGDECLARE(debugObj);

public:

  snmpFIFOObj();
  snmpFIFOObj(const snmpFIFOObj & ref);
  void dump(void);
  virtual ~snmpFIFOObj();

  // place messaeg into the FIFO
  virtual void push(snmpMessageObj *);

  // returns message or waits for a message to be placed on FIFO
  virtual snmpMessageObj *pop();

  // like pop() but pop(int) will time out after int seconds. It
  // will return a null pointer if nothing was in the queue.
  virtual snmpMessageObj *pop(int seconds);

  // MessageWaiting returns true if there are message in the queue
  bool  messageWating();

  virtual snmpObj * clone() const { return new snmpFIFOObj(*this); };
};

#endif /* SNMPFIFOOBJ_H */
