// snmpRegObj.c
//

#include "config.h"

#include <errno.h>
#include <iostream>
#include <iterator>
#include <typeinfo>
#include <algorithm>
#include <stdlib.h>
#include <stdio.h>
#include <sstream>
#include <pthread.h>
// #include <signal.h>

#include <signal.h>
#include "snmpRegObj.H"
#include "snmpMessageObj.H"
#include "debug.H"
#include "opensnmpUtilities.H"

using std::string;
using std::map;
using std::list;

// bascially rename OPENSNMP_UTILITIES
namespace OSU {
  using OPENSNMP_UTILITIES::intToString;
}

// STATIC -----------

map<string, snmpArchRegObj *> snmpRegObj::regMap;
snmpMutexObj                 *snmpRegObj::regMap_mutex = new snmpMutexObj;
const int                     snmpRegObj::exitTimeOut  = 10;
bool                          snmpRegObj::exiting      = false;

// false function used to initialize arch threads
void *
snmpRegObj::initializeArch(snmpArchRegObj *archRegPtr) {
  DEBUGCREATE_L(debugObj, "snmpRegObj");
  static int status=0;
  
  //  DEBUG9_L(debugObj, string("Architecture block '") 
  //	   + archRegPtr->archName + string("' starting \n"));

  // set up default signal filter, block most signals.
  sigset_t blocked_sigs;
  sigemptyset(&blocked_sigs);
  sigaddset(&blocked_sigs, SIGHUP);
  sigaddset(&blocked_sigs, SIGINT);
  sigaddset(&blocked_sigs, SIGQUIT);
  sigaddset(&blocked_sigs, SIGABRT);
  sigaddset(&blocked_sigs, SIGTERM);
  sigaddset(&blocked_sigs, SIGSTOP);
  pthread_sigmask(SIG_BLOCK, &blocked_sigs, NULL);
  
  if (NULL != archRegPtr)  {
    archRegPtr->init();
    archRegPtr->exitMutex->lock("snmpRegObj::initializeArch: exiting lock");
    archRegPtr->exitMutex->unlock
      ("snmpRegObj::initializeArch: exiting unlock");
    DEBUG9_L(debugObj, string("initializeArch: exiting '") + 
	     archRegPtr->archName + string("', sending signal"));
    archRegPtr->exitCondition->signal
      ("snmpRegObj::initializeArch: exiting signal");
  }
  else {
    DEBUG0_L(debugObj, "ERROR: initialize Arch: archRegPtr is null?");
  }
  
  DEBUG9_L(debugObj, string("It's DEAD, Jim!, specifically id '") 
	   + OSU::intToString(pthread_self()) + string("'\n"));
  
  pthread_exit((void *) &status);
  return(NULL);
}  // initialiazeArch


void
snmpRegObj::exitArches(list<string> &archNames) {
  DEBUGCREATE_L(debugObj, "snmpRegObj");
  list<string>::const_iterator itr;
  map<string, snmpArchRegObj *>::iterator itr_m;
  snmpFIFOObj  *theFIFO = NULL;
  
  regMap_mutex->lock("exitArches:");
  exiting = true;

  // tell list of arches to exit
  for (itr = archNames.begin(); itr != archNames.end(); itr++) {
    if (regMap.end() != (itr_m = regMap.find(*itr)) ) {
      if (NULL != (theFIFO = itr_m->second->get_fifo())) {
	snmpMessageObj *xMsg = new snmpExitMessage;
	itr_m->second->exitMutex->lock("exitArches: lock");
	DEBUG9_L(debugObj, string("exitArches:telling '") + 
		 itr_m->second->archName + string("' to exit"));
	theFIFO->push(xMsg);
      }
      else {
	DEBUG9_L(debugObj, string("exitArches: found '") +
		 itr_m->second->archName +
		 string("', FIFO is Null thread shouldn't exist\n"));
      }
    }
    else {
	DEBUG9_L(debugObj, string("exitArches: couldn't find '") + 
		 itr_m->second->archName + "' in regMap");
    }
  }

  // wait for achers to signal their exiting, timeout if we
  // don't here from them in two seconds.
  for (itr = archNames.begin(); itr != archNames.end(); itr++) {
    if (regMap.end() != (itr_m = regMap.find(*itr)) ) {
      if (NULL != (theFIFO = itr_m->second->get_fifo())) {

	DEBUG9_L(debugObj, string("exitArches:waiting for '") + 
		 itr_m->second->archName + "' to exit");

	snmpConditionObj::conditionWaitReturnEnum retVal;
	retVal = itr_m->second->exitCondition->wait
	  (itr_m->second->exitMutex, exitTimeOut, "exitArches: waiting");

	if (retVal == snmpConditionObj::signalled) {
	  DEBUG9_L(debugObj, string("exitArches: caught signal for '")
		   + itr_m->second->archName + "'");
	}
	else if (retVal == snmpConditionObj::sigtimeout) {
	  DEBUG9_L(debugObj, string("exitArches: timeout out for '") 
		   + itr_m->second->archName + 
		   "', nuts, I'm continuing to exit anyway");
	}
	else {
	  DEBUG0_L(debugObj, string("exitArches: error waiting for '") 
		   + itr_m->second->archName + 
		   "', I'm continuing to exit anyway");
	}
	
	itr_m->second->exitMutex->unlock("exitArches: unlock");
      }
    }
  }

  regMap_mutex->unlock("exitArches:");
    
  DEBUG9_L(debugObj, "exitArches: returning \n");
}  // exitArches


void
snmpRegObj::exitAllArches() {
  DEBUGCREATE_L(debugObj, "snmpRegObj");
  list<string>::const_iterator itr;
  map<string, snmpArchRegObj *>::iterator itr_m;
  snmpFIFOObj  *theFIFO = NULL;
  
  regMap_mutex->lock("exitAllArches:");
  exiting = true;

  // tell all arches in registry to exit
  for (itr_m = regMap.begin(); itr_m != regMap.end(); itr_m++) {
    if (NULL != (theFIFO = itr_m->second->get_fifo())) {
      snmpMessageObj *xMsg = new snmpExitMessage;
      itr_m->second->exitMutex->lock("exitAllArches: lock");
      DEBUG9_L(debugObj, string("exitAllArches: telling '") + 
	       itr_m->second->archName + "' to exit");
      theFIFO->push(xMsg);
    }
    else {
      DEBUG9_L(debugObj, string("exitAllArches: found '") +
	       itr_m->second->archName +
	       "', FIFO is Null thread shouldn't exist\n");
    }
  }

  // wait for achers to signal their exiting, timeout if we
  // don't here from them in two seconds.
  for (itr_m = regMap.begin(); itr_m != regMap.end(); itr_m++) {
    if (NULL != (theFIFO = itr_m->second->get_fifo())) {

      DEBUG9_L(debugObj, string("exitAllArches:waiting for '") +  
	       itr_m->second->archName + "' to exit");

      snmpConditionObj::conditionWaitReturnEnum retVal;
      retVal = itr_m->second->exitCondition->wait
	(itr_m->second->exitMutex, exitTimeOut, "exitAllArches: waiting");

      if (retVal == snmpConditionObj::signalled) {
	DEBUG9_L(debugObj, string("exitAllArches: caught signal for '") 
		 + itr_m->second->archName + "'");
      }
      else if (retVal == snmpConditionObj::sigtimeout) {
	DEBUG9_L(debugObj, string("exitAllArches: timeout out for '")
		 + itr_m->second->archName +
		 "', nuts, I'm continuing to exit anyway");
      }
      else {
	DEBUG0_L(debugObj, string("exitAllArches: error waiting for '")
		 + itr_m->second->archName +
		 "', I'm continuing to exit anyway");
      }
	
      itr_m->second->exitMutex->unlock("exitAllArches: unlock");
    }
  }

  regMap_mutex->unlock("exitAllArches:");
    
  DEBUG9_L(debugObj, "exitAllArches: returning \n");
}  // exitAllArches



//  NON-STATIC ------------------

snmpRegObj::snmpRegObj() {
}


snmpRegObj::~snmpRegObj() {
}


string
snmpRegObj::toString() {
  map<string, snmpArchRegObj *>::iterator mit;
  string  retString = string("");

  mit = this->regMap.begin();
  while (mit != this->regMap.end()) {
    retString = retString + "'" + mit->first + "' \n";
    mit++;
  }
  return(retString);
} // toString


// addNewArchInit is used to register a new architecture block
// (i.e. a block or module that runs in its own thread and receives
// messages from other blocks via its own FIFO) to the registration
// map.
//
//   newName   the blocks name
//   newObj    the registration object to add to the map
//
bool
snmpRegObj::addNewArchInit(string &newName, snmpArchRegObj  *newObj) {
  DEBUGCREATE_L(debugObj, "snmpRegObj");
  map<string, snmpArchRegObj *>::iterator itr_m;
  bool                                    ret_bool=false;

  itr_m = this->regMap.find(newName);

  if (exiting) {
    DEBUG9_L(debugObj, "addNewArchInit: In process of exiting, arch not added.\n");
  }
  else if (itr_m == this->regMap.end()) {
    DEBUG9_L(debugObj, "Begin memory lock\n");
    // lock memory (i.e. regMap) and recheck.
    if (this->regMap_mutex->lock("snmpRegObj::addNewArchInit")) {

      if ( this->regMap.end() == (itr_m = this->regMap.find(newName)) ) {
	this->regMap[newName] = newObj;
	newObj->archName = newName;
	DEBUG9_L(debugObj, newName + 
		 string(" NOT found, added to Registration Map\n"));
	ret_bool = true; // the new snmpArchRegObj was added to the map.
      }
      else {
	DEBUG9_L(debugObj, itr_m->first + 
		 string("     found, it was added while this thread was checking for it!\n"));
      }
      DEBUG9_L(debugObj, "End memory lock\n");
      this->regMap_mutex->unlock( "snmpRegObj::addNewArchInit");
    } // end of memory lock
  } // already exists
  else {
    DEBUG9_L(debugObj, itr_m->first + 
	     string("     already exists in Registration Map, not added.\n"));
  }
  
  return(ret_bool); 
} // addNewArchInit


snmpFIFOObj
*snmpRegObj::findArchFIFO(const string &searchName) {
  DEBUGCREATE_L(debugObj, "snmpRegObj");
  map<string, snmpArchRegObj *>::iterator itr_m;
  int                                     errNum=0;
  pthread_t                               sep_thread;
  snmpFIFOObj                            *aFIFO = NULL;

  itr_m = this->regMap.find(searchName);

  if (exiting) {
    DEBUG9_L(debugObj, "findArchFIFO: In process of exiting, returning NULL.\n");
    return(NULL);
  }
  else if (itr_m == this->regMap.end()) {    
    DEBUG9_L(debugObj, searchName + 
	     string(" NOT found in Registration Map \n"));
    return(NULL);
  }
  else {
    DEBUG9_L(debugObj, itr_m->first + 
	     string("     found in Registration Map\n"));
    if ((aFIFO = itr_m->second->get_fifo()) == NULL) {
      DEBUG9_L(debugObj, "fifo is NULL, starting arch.\n");
      DEBUG9_L(debugObj, "locking memory (regMap) and rechecking ...\n");
      // lock memory for regMap access
      if (this->regMap_mutex->lock("snmpRegObj::findArchFIFO")) {
	// in case someone has started it between checking above and locking!
	// or we've started exiting.
	if ((aFIFO = itr_m->second->get_fifo()) == NULL) {
	  // if we started exiting between checking earlier and now, stop
	  if (exiting) {
	    DEBUG5_L(debugObj, "findArchFIFO: in mutex lock, started exiting, returning NULL.");
	    aFIFO = NULL;
	  }
	  else {
	    // have the arch reg object create the snmpFIFOObj
	    if ((aFIFO = itr_m->second->create_fifo()) == NULL) {
	      DEBUG9_L(debugObj, 
		       string("failed to create FIFOObj, communication to '") 
		       + itr_m->first + "' will not be possible.");
	      return(NULL);
	    }
	
	    // start new thread
	    // set_ local archRegPtr to the snmpArchRegObj of the starting
	    // thread in order to run its initializing procedure in the
	    // nested 'initializeArch' procedure above.

	    // if error starting thread.
	    if ( 0 != (errNum = 
		       pthread_create(&sep_thread, 
				      (pthread_attr_t *) NULL, 
				      (void *(*) (void *)) this->initializeArch, 
				      itr_m->second) ) ) {
	      DEBUG9_L(debugObj, "ERROR: the error is '" + 
		       OSU::intToString(errNum) + 
		       ((errNum == EAGAIN) ? "' : Not enough resources\n" : \
			"' : an attr invalid or other\n") );
	    }
	  }
	}
	// unlock mutex.
	this->regMap_mutex->unlock("snmpRegObj::findeArchFIFO");	
      }
    }

    return(aFIFO);
  }

} // findArchFIFO


void
snmpRegObj::dumpMap() {
    DEBUGCREATE_L(debugObj, "snmpRegObj");
    DEBUG9_L(debugObj, toString());
} // dumpMap

