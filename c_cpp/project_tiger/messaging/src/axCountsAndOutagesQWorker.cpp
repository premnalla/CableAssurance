
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axWriteLockOperator.hpp"
#include "axCountsAndOutagesQWorker.hpp"
#include "axCountsAndOutageMsgQ.hpp"
#include "axCAMessage.hpp"
#include "axCountsAndOutageMsg.hpp"
#include "axGlobalData.hpp"
#include "axHfcOutageCollections.hpp"
#include "axHfcOutageDetectionRunnableCollection.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCountsAndOutagesQWorker::axCountsAndOutagesQWorker()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCountsAndOutagesQWorker::~axCountsAndOutagesQWorker()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCountsAndOutagesQWorker::axCountsAndOutagesQWorker()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCountsAndOutagesQWorker::run(void)
{
  bool exitCondition = false;
  axCountsAndOutageMsgQ * q = axCountsAndOutageMsgQ::getInstance();
  axCAMessage * msg;

  while (!exitCondition) {

    while ((msg = (axCAMessage *) q->remove()) != NULL) {

      // do something with the message

      // axCountsAndOutageMsg * outageMsg = 
      //                   dynamic_cast<axCountsAndOutageMsg *> (msg);

      axMessagePayload_s * p1 = msg->getMessagePayload();
      axCountsAndOutagesPayload_s * p = 
              (axCountsAndOutagesPayload_s *) p1->specificPayloadData;
      axIntCmtsKey_t cmtsKey(p->cmtsResId);
      axInternalCmts * intCmts = 
                        axGlobalData::getInstance()->findCmts(cmtsKey);

      bool startOutageDetection = false;

      if (intCmts) {
        axWriteLockOperator wCmtsLock(intCmts->getLock());
        if (!intCmts->isHfcOutageDetectionInProgress()) {
          intCmts->setHfcOutageDetectionInProgress();
          startOutageDetection = true;
        }
      }

      if (intCmts && startOutageDetection) {
        axHfcOutageDetectionRunnableCollection tmpRc(intCmts->getResId());
        axHfcOutageDetectionRunnableCollection * actualRc =
                                (axHfcOutageDetectionRunnableCollection *)
                      axHfcOutageCollections::getInstance()->find(&tmpRc);
        if (actualRc) {
          actualRc->scheduleRunnables();
        }
      }

      // fee message
      delete msg;

    } // inner while

  } // outer while

  return (0);
}


