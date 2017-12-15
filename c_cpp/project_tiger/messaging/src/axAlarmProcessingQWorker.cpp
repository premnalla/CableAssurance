
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAlarmProcessingQWorker.hpp"
#include "axAlarmProcessingMsgQ.hpp"
#include "axCAMessage.hpp"

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
axAlarmProcessingQWorker::axAlarmProcessingQWorker()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAlarmProcessingQWorker::~axAlarmProcessingQWorker()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAlarmProcessingQWorker::axAlarmProcessingQWorker()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAlarmProcessingQWorker::run(void)
{
  bool exitCondition = false;
  axAlarmProcessingMsgQ * q = axAlarmProcessingMsgQ::getInstance();
  axCAMessage * msg;

  while (!exitCondition) {

    while ((msg = (axCAMessage *) q->remove()) != NULL) {

      axMessagePayload_s * p1 = msg->getMessagePayload();
      axAlarmProcessingPayload_s * p = 
              (axAlarmProcessingPayload_s *) p1->specificPayloadData;

      // axAbstractCAAlarm * absAlm = p->alarm;

      if (!p->alarm->addAlarm()) {
        delete p->alarm;
      }

      // fee message
      delete msg;

    } // inner while

  } // outer while

  return (0);
}


