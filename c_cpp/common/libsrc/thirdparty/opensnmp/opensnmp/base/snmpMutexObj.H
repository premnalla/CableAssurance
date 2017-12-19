#ifndef SNMPMUTEXOBJ_H
#define SNMPMUTEXOBJ_H

#include <iostream>
#include <pthread.h>
#include <string>

using namespace std;

// snmpMutexObj class;
//   This is a wrapper around the mutex for the pthreads locking.
//   To start, it is just goin to inclued a simple mutex with locking.
//   In the long run, it may have cond worked in as well and 
//   maybe a sibling class that has instantiation -> lock
//   deletion -> unlock capabilities.


class snmpMutexObj  {

  friend class snmpConditionObj;  // to share 'mutex'

protected:
  pthread_mutex_t        *mutex;

private:
  bool   is_locked;

public:
  snmpMutexObj();
  virtual ~snmpMutexObj();
  
  // blocks waiting until the mutex is available, then locks it and returns
  // returns true if it get_s a succesful lock, false otherwise.
  // It also prints out an error message on failure.
  virtual bool lock(const string &errMsg = "Unknown");
 
  // Locks the mutex, if it is available. Fails if it is already locked.
  // Returns 'true' if it locks the mutex.
  // Returns 'false' if mutex is already locked or on error.
  // And yes this leaves no way to differ from an error and an already
  // locked mutex. 
  // It does print out an error message on failure.
  virtual bool trylock(const string &errMsg = "Unknown");
 
  // unlocks the mutex.
  // returns true if it succesfully unlocks, false otherwise.
  // It also prints out an error message on failure.
  virtual bool unlock(const string &errMsg = "Unknown");

  // is the mutex currently locked?
  virtual bool isLocked();
  
};


// ----------------------  Condition Class  --------------------------

// snmpConditionObj class;
//   This is a wrapper around the 'cond' struct for pthreads locking.
//   This would be used in conjunction with a mutex object to control 
//   locking/signaling between threads.


class snmpConditionObj  {

private:
  pthread_cond_t        *condition;

public:
  snmpConditionObj();
  virtual ~snmpConditionObj();
  
  enum conditionWaitReturnEnum { signalled, sigtimeout, sigerror };

  // This routine atomically blocks the current thread waiting on 
  // condition variable (this object), and unlocks the mutex specified by 
  // theMutex. The waiting thread unblocks only after another thread
  // calls condition->signal() or condition->broadcast().
  // The current thread reaquires the lock on 'theMutex'.
  // Returns true if succesful, false if there is an errror, an error 
  // message is printed to std error.
  virtual bool wait(snmpMutexObj *, const string &errMsg = "Unknown");
  virtual conditionWaitReturnEnum wait(snmpMutexObj *, int seconds, 
				       const string &errMsg = "Unknown");
 
  // unblocks ONE thread waiting on this condition (this object).
  // true if no errors, false otherwise. An error message is printed
  // out on error.
  virtual bool signal(const string &errMsg = "Unknown");

  // unblocks ALL threads waiting on this condition (this object).
  // true if no errors, false otherwise. An error message is printed
  // out on error.
  virtual bool broadcast(const string &errMsg = "Unknown");

};


#endif  /* SNMPMUTEXOBJ_H */
