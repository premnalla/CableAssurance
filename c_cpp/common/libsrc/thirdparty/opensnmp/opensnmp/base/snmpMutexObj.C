// snmpMutexObj.c
//

#include "config.h"
#include <errno.h>
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/time.h>

#include "snmpMutexObj.H"


snmpMutexObj::snmpMutexObj() {
  this->mutex = new pthread_mutex_t;
  pthread_mutex_init(this->mutex, NULL);
  this->is_locked = false;
}


snmpMutexObj::~snmpMutexObj() {
  pthread_mutex_destroy(this->mutex);
  delete this->mutex;
}


bool
snmpMutexObj::lock(const string &errMsg) {
  int errorNum;

  // lock memory 
  if ( 0 != (errorNum = pthread_mutex_lock(this->mutex)) ) {
    if (errorNum == EINVAL) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpMutex->lock : mutex is invalid\n";
    }
    else if (errorNum == EDEADLK) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpMutex->lock : deadlock would occur\n";
    }
    else  {
      cerr << "ERROR: snmp:: " << errMsg <<
	" : snmpMutex->lock : unknown error\n";
    }
    return(false);
  }
  this->is_locked = true;
  return(true);
} // lock


bool
snmpMutexObj::trylock(const string &errMsg) {
  int errorNum;

  // try to lock memory 
  if ( 0 != (errorNum = pthread_mutex_trylock(this->mutex)) ) {
    if (errorNum == EINVAL) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpMutex->trylock : mutex is invalid\n";
    }
    else if (errorNum == EDEADLK) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpMutex->trylock : deadlock would occur\n";
    }
//     else if (errorNum == EBUSY) {
//       cerr << "ERROR: snmp:: " << errMsg <<
// 	" : snmpMutex->trylock : mutex already locked\n";
//     }
    else {
      cerr << "ERROR: snmp:: " << errMsg <<
	" : snmpMutex->trylock : unknown error\n";
    }
    return(false);
  }
  this->is_locked = true;
  return(true);
} // trylock


bool
snmpMutexObj::unlock(const string &errMsg) {
  int errorNum;
  
  if ( 0 != (errorNum = pthread_mutex_unlock(this->mutex)) ) {
    if (errorNum == EINVAL) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpMutex->unlock : mutex is invalid\n";
    }
    else if (errorNum == EDEADLK) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpMutex->unlock : mutex isn't owned!\n";
    }
    else {
      cerr << "ERROR: snmp:: " << errMsg <<
	" : snmpMutex->unlock : unknown error\n";
    }
    return(false);
  }
  this->is_locked = false;
  return(true);
} // unlock


bool
snmpMutexObj::isLocked() {
  return this->is_locked;
} // isLocked


//  --------------------  Condition Class  ----------------------

snmpConditionObj::snmpConditionObj() {
  this->condition = new pthread_cond_t;
  pthread_cond_init(this->condition, NULL);
}


snmpConditionObj::~snmpConditionObj() {
  pthread_cond_destroy(this->condition);
  delete this->condition;
}


bool
snmpConditionObj::wait(snmpMutexObj *theMutex, 
		       const string &errMsg) {
  int errorNum;

  // unlocks theMutex and block wating on condition
  if ( 0 != (errorNum = 
	     pthread_cond_wait(this->condition, theMutex->mutex)) ) {
    if (errorNum == EINVAL) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpCondition->wait : mutex and/or condition is invalid, " << 
	"or mutex not locked by current thread\n";
    }
    else {
      cerr << "ERROR: snmp::" << errMsg << 
	" : snmpCondition->wait : unknown error\n";
    }
    return(false);
  }
  
  return(true);
} // wait


snmpConditionObj::conditionWaitReturnEnum
snmpConditionObj::wait(snmpMutexObj *theMutex, 
		       int seconds,
		       const string &errMsg) {
  int errorNum;

  timespec cond_time;
  timeval  gettod_time;
  gettimeofday(&gettod_time, NULL);
  cond_time.tv_sec = gettod_time.tv_sec + seconds;
  cond_time.tv_nsec = 1000 * gettod_time.tv_usec;

  // unlocks theMutex and block wating on condition
  if ( 0 != (errorNum = 
	     pthread_cond_timedwait(this->condition, theMutex->mutex, &cond_time)) ) {
    if (errorNum == EINVAL) {
      cerr << "ERROR: snmp:: " << errMsg << 
	" : snmpCondition->wait : mutex and/or condition is invalid, " << 
	"or mutex not locked by current thread\n";
      return(sigerror);
    }
    else if (errorNum == ETIMEDOUT) {
      return(sigtimeout);
    }
    else {
      cerr << "ERROR: snmp::" << errMsg << 
	" : snmpCondition->wait : unknown error: " << errorNum << endl;
    }
    return(sigerror);
  }
  
  return(signalled);
} // wait


bool
snmpConditionObj::signal(const string &errMsg) {
  int errorNum;

  if ( 0 != (errorNum = pthread_cond_signal(this->condition)) ) {
    if (errorNum == EINVAL) {
      cerr << "RROR: snmp:: " << errMsg << 
	" : snmpCondition->signal : condition variable is invalid\n";
    }
    else {
      cerr << "ERROR: snmp::" << errMsg << 
	" : snmpCondition->wait : unknown error\n";
    }
    return(false);
  }
  return(true);
} // signal


bool
snmpConditionObj::broadcast(const string &errMsg) {
  int errorNum;

  if ( 0 != (errorNum = pthread_cond_broadcast(this->condition)) ) {
    if (errorNum == EINVAL) {
      cerr << "RROR: snmp:: " << errMsg << 
	" : snmpCondition->signal : condition variable is invalid\n";
    }
    else {
      cerr << "ERROR: snmp::" << errMsg << 
	" : snmpCondition->wait : unknown error\n";
    }
    return(false);
  }
  return(true);
} // broadcast
