
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axWriteLockOperator.hpp"
#include "axCountsAndAlarmsQWorker.hpp"
#include "axCountsAndAlarmMsgQ.hpp"
#include "axCAMessage.hpp"
#include "axCountsAndAlarmMsg.hpp"
#include "axGlobalData.hpp"
#include "axHfcAlarmCollectionofRCs.hpp"
#include "axHfcAlarmDetectionRunnableCollection.hpp"
#include "axRunnableDataTypes.hpp"
#include "axHfcMtaAlarmDetectionRC.hpp"
#include "axHfcMtaAlarmCollectionOfRCs.hpp"
#include "axCountsDataTypes.hpp"

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
axCountsAndAlarmsQWorker::axCountsAndAlarmsQWorker()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCountsAndAlarmsQWorker::~axCountsAndAlarmsQWorker()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCountsAndAlarmsQWorker::axCountsAndAlarmsQWorker()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCountsAndAlarmsQWorker::run(void)
{
  bool exitCondition = false;
  axCountsAndAlarmMsgQ * q = axCountsAndAlarmMsgQ::getInstance();
  axCAMessage * msg;

  while (!exitCondition) {

    while ((msg = (axCAMessage *) q->remove()) != NULL) {

      // do something with the message

      // axCountsAndAlarmMsg * alarmMsg = 
      //                   dynamic_cast<axCountsAndAlarmMsg *> (msg);

      axMessagePayload_s * p1 = msg->getMessagePayload();
      axCountsAndAlarmsPayload_s * p = 
              (axCountsAndAlarmsPayload_s *) p1->specificPayloadData;
      axIntCmtsKey_t cmtsKey(p->cmtsResId);
      axInternalCmts * intCmts = 
                        axGlobalData::getInstance()->findCmts(cmtsKey);

      bool startAlarmDetection = false;
      bool canFreeMsg = true;

      if (intCmts) {
        axWriteLockOperator wCmtsLock(intCmts->getLock());
        if (intCmts->isHfcAlarmDetectionInProgress()) {
          intCmts->addCountsMessage(msg);
          canFreeMsg = false;
        } else {
          intCmts->setHfcAlarmDetectionInProgress();
          startAlarmDetection = true;
        }
      }

      if (intCmts && startAlarmDetection) {

        if (p->countsType == AX_COUNTS_TYPE_ALL) {

          axHfcAlarmDetectionRunnableCollection tmpRc(
                                                 intCmts->getResId());
          axHfcAlarmDetectionRunnableCollection * actualRc =
                             (axHfcAlarmDetectionRunnableCollection *)
               axHfcAlarmCollectionofRCs::getInstance()->find(&tmpRc);

          if (actualRc) {
            actualRc->scheduleRunnables();
          }

        } else if (p->countsType == AX_COUNTS_TYPE_MTA) {

          axHfcMtaAlarmDetectionRC tmpRc(intCmts->getResId());
          axHfcMtaAlarmDetectionRC * actualRc =
                                          (axHfcMtaAlarmDetectionRC *)
               axHfcMtaAlarmCollectionOfRCs::getInstance()->find(&tmpRc);

          if (actualRc) {
            actualRc->scheduleRunnables();
          }

        }

      }

      if (canFreeMsg) {
        // fee message
        delete msg;
      }

    } // inner while

  } // outer while

  return (0);
}


