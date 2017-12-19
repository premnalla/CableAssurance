#ifndef SNMPCGARCH_H
#define SNMPCGARCH_H

#include <stdio.h>
#include <iostream>
#include <typeinfo>
#include <vector>
#include <unistd.h>
#include <pthread.h>
#include <list>


#include "snmpRegObj.H"
#include "snmpCGArchRegObj.H"
#include "snmpDispArchObj.H"
#include "snmpEngine.H"
#include "snmpDatabaseObj.H"
#include "snmpUSMArchObj.H"
#include "asnDataTypes.H"
#include "debug.H"
#include "sendPduArgs.H"

#include "snmpDispSendPduASI.H"
#include "snmpAppProcessResponsePduASI.H"
#include "snmpCGtoCGArchMsg.H"

class snmpCGArch : public snmpArchObj {

private:
  static  snmpEngine *theEngine;
  static  bool        cgArchInited;
  static  const int   MAX_INT;
 
  DEBUGDECLARE(cgDebug);
  // global var
  //  debug            *cgDebug;
  string            dbPath;
  snmpDatabaseObj  *theDatabase;
  string            cgRegName, dispRegName;

  snmpRegObj       *theReg;


  // class intRef holds current state information and the values of
  // an extant SNMP message.

  class intRef {
  public:
    bool               discovery;
    int                attemptedRetries;
    unsigned long      pduHandle;
    snmpCGtoCGArchMsg *ctcMsg;

    intRef(bool disc, snmpCGtoCGArchMsg *msg, 
           unsigned long handle=0, int retries=0) {
      discovery        = disc;
      ctcMsg           = msg;
      pduHandle        = handle;
      attemptedRetries = retries;
    };
  };
  
  typedef map<unsigned long, intRef *>           stateMap_Type;
  typedef map<unsigned long, intRef *>::iterator stateMap_iterator;

  // The internalSentMsgStatMap is used when sending a message
  // through the engine.  A message remains in this map until it has
  // either been sent onto the wire succesfully or this engine
  // failed to process it.  It would then be placed in either the
  // sentMsgStateMap on success or returned to the app with an error
  // on failure.  
  //
  // indexes used: initial discovery msg ID or request msg ID, 
  stateMap_Type  internalSentMsgStateMap;

  // The sentMsgStateMap is used to hold messages currently sent out
  // across the wire and awaiting replies.
  // 
  // indexes used: send PDU handle from succesfully sent message
  stateMap_Type  sentMsgStateMap;

  // The addUserStateMap is used in casses where a user needs to be
  // added to the USM.  It holds the message until the USM returns
  // either success or failure. 
  // 
  // index used; usmAddUserMsg msg ID,
  stateMap_Type  addUserStateMap;
  
public:
  snmpFIFOObj      *dispFifo, *cgArchFifo;

  snmpCGArch(snmpEngine *ourEngine);
  virtual ~snmpCGArch();

  // this is a generic init function.
  // It will do a lot of work for you in a particilar way.
  // If you want to change it, or do some of its functions
  // look at it carefully, particularly at the ordering of how
  // things are processed (i.e. command line read befor engine->init, etec
  virtual  void init();

  // shutdown will do run any necesary engine shutdown calls.
  virtual void  shutdown();

  // discover the engine id given for a CG to CGArch message.
  virtual  void discover_engine_id(snmpCGtoCGArchMsg *ctcMsg, 
				   unsigned long      pduHandle    = 0,
				   int                retryAttempt = 0);

  // add a user to the usm (good for command line entered users!
  virtual  void add_user_to_usm(snmpCGtoCGArchMsg *ctcMsg,
				bool               persistant,
				bool               overwrite);

  // delete a user from the usm table
  virtual void delete_user(sendPduArgs& sendArgs);
   
  // register with the dispatche to receive pduType
  // registers from all engineID vals!
  virtual  bool register_with_dispatcher(set<PDU_Type> *pduType = NULL);

  // unregister with the dispatche to receive pduType
  // if your nice, you'll do this before you exit :{
  virtual  bool unregister_with_dispatcher(set<PDU_Type> *pduType = NULL);
  
  virtual bool open_db(string path);

  virtual bool deleteFromStateMap(stateMap_Type &sMap, 
                                  unsigned long index);

  virtual void internalSendError(intRef *iRef, snmpStatusInfo *status);

  // handles incoming CG to CGArch messages.
  virtual void handleCGtoCGArchMsg(snmpCGtoCGArchMsg *ctcMsg);

  // handles incoming snmpDispSendPduASI, i.e. responses from
  // attempts to send messages out to the network.
  virtual void handleDispSendPduASI(snmpDispSendPduASI *Msg);

  // handles incoming usmAddUserMsg's
  virtual void handleAddUserMsg(usmAddUserMsg *uauMsg);

  // Find message in sent state map.
  // for discovery if error return to calling CG object
  //               if success re-send original mesasge
  // for non-discovery return to calling CG object.
  virtual void 
  snmpCGArch::handleAppProcessResponseMsg(snmpAppProcessResponsePduASI *msg);

  // get_time returns the current time in seconds.
  virtual long get_time();

  // checkTimeout examines the state maps for any messages that have
  // timed out and processes them.
  // It also calculates the minimum timeout value and returns it or
  // zero if none exist.
  virtual int checkTimeout();

  // stateMapTimeCheck is a utility function used by checkTimeout to
  // search through each individual state map.
  virtual int stateMapTimeCheck(stateMap_Type  & sMap, 
				bool             internalMessage,
				long             currSec, 
				string           errorText);

  // processTimeout is a utility function used by stateMapTimeCheck
  // to process and return to the CG object timed out messages.
  virtual void processTimeout(intRef  *reference, 
			      bool     internalMessage,
			      string   errorText);

  // sendRetry is used by stateMapTimeCheck() to send retry messages
  // for timed out SNMP messages.
  virtual void sendRetry(intRef        *ref,
			 unsigned long  pduHandle);

  // mainloop
  void main_loop(void);

};

#endif /* SNMPCGARCH_H */
