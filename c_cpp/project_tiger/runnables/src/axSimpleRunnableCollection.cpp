
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSimpleRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axAbstractCARunnable.hpp"
#include "axCAScheduler.hpp"
#include "axIteratorHolder.hpp"

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
axSimpleRunnableCollection::axSimpleRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSimpleRunnableCollection::~axSimpleRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSimpleRunnableCollection::axSimpleRunnableCollection()
// {
// }



//********************************************************************
// method:
//********************************************************************
bool
axSimpleRunnableCollection::areAllRunnablesComplete(void)
{
  static const char * myName="areAllRunnablesComplete:";

  bool ret = true;

  axListBase * runnableList;
  axAbstractCARunnable * r;

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry\n", myName));

  {
    axReadLockOperator rCollLock(getLock());
    runnableList = cloneList_u();
  }


  while ((r=(axAbstractCARunnable *)runnableList->remove()) && ret) {

    // Not locking here as it is really holding things back
    // axReadLockOperator rblRlock(r->getLock());

    if (r->isRunning()) {
      ret = false;
      break;
    }

  }

  delete runnableList;

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit\n", myName));

  return (ret);
}



//********************************************************************
// method:
//********************************************************************
void
axSimpleRunnableCollection::addSubject(axListBase & devList)
{
  axWriteLockOperator rwLock(getLock());

  const int absoluteMax = getMaxDevicesPerRunnable();

  axIteratorHolder iH(createIterator());
  axAbstractIterator * iter = iH.getIterator();
  axAbstractCARunnable * runnable = (axAbstractCARunnable *)
                                                     iter->getLast();
  if (!runnable) {
    runnable = createNewRunnable();
    if (runnable) {
      axWriteLockOperator wLock(getLock());
      add(runnable);
    }
  }

  bool isDone = false;
  axObject * dev = NULL;

  while (!isDone) {

    axWriteLockOperator wL(runnable->getLock());

    for (dev = devList.remove(); dev;
         dev = devList.remove()) {

      int val = runnable->size();

      if (val > absoluteMax) {
        axAbstractCARunnable * newRunn = createNewRunnable();
        newRunn->addSubject(dev);
        {
          axWriteLockOperator wLock(getLock());
          add(newRunn);
        }
        runnable = newRunn;
        break;
      } else {
        runnable->addSubject(dev);
      }

    } // for

    if (!dev) {
      isDone = true;
    }

  } // while

}


//********************************************************************
// method:
//********************************************************************
void
axSimpleRunnableCollection::addSubject(axObject * dev)
{
  axListBase l;
  l.add(dev);
  addSubject(l);
}



//********************************************************************
// method:
//********************************************************************
bool
axSimpleRunnableCollection::scheduleRunnables(void)
{
  static const char * myName="SimpleSchedRbl:";

  bool ret = true;

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry\n", myName));

  axCAScheduler * scheduler = axCAScheduler::getInstance();

  if (canScheduleRunnables()) {

    // ACE_DEBUG((LM_MISC_DEBUG, "%s canScheduleRunnables \n", myName));

    axWriteLockOperator rwLock(getLock());

    bool stillRunning = isInProgress();

    if (!stillRunning) {

      setInProgress();

      struct timeval tm;
      gettimeofday(&tm, NULL);

      // setWorkStartTime(tm.tv_sec);
      setKickoffTime(tm.tv_sec);
      clearNextCycleScheduled();
      setWorkStartTime(0);

      axAbstractIterator * iter;
      axIteratorHolder iH(iter = createIterator());
      axAbstractCARunnable * r = dynamic_cast<axAbstractCARunnable *> 
                                                    (iter->getFirst());
      if (r) {
        // ACE_DEBUG((LM_MISC_DEBUG, "%s enQRunnable \n", myName));
        scheduler->enQRunnable(r);
      }

    }

  } else {

    postponeTask();

  }

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit \n", myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axSimpleRunnableCollection::getMaxDevicesPerRunnable(void)
{
  return (0);
}


//********************************************************************
// method:
//********************************************************************
axAbstractCARunnable *
axSimpleRunnableCollection::getNextRunnable(axAbstractCARunnable * curr)
{
  static const char * myName="smplGetNextRbl:";

  axAbstractCARunnable * ret = NULL;

  axAbstractIterator * iter;
  axAbstractCARunnable * r;

  ACE_DEBUG((LM_MISC_DEBUG, "%s curr rbl id=%d\n", myName,
                                                 curr->hashUInt32()));
  axReadLockOperator rLock(getLock());

  axIteratorHolder iH(iter = createIterator());

  for (r = dynamic_cast<axAbstractCARunnable *> (iter->getFirst());
       r;
       r = dynamic_cast<axAbstractCARunnable *> (iter->getNext())) {
    if (r->isKeyEqual(curr)) {
      ret = dynamic_cast<axAbstractCARunnable *> (iter->getNext());
      break;
    }
  }

  if (ret) {
    ACE_DEBUG((LM_MISC_DEBUG, "%s next rbl id=%d\n", myName,
                                                  ret->hashUInt32()));
  } else {
    ACE_DEBUG((LM_MISC_DEBUG, "%s next rbl id=null\n", myName));
  }

  return (ret);
}



