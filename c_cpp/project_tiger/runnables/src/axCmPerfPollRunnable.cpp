//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axListFree.hpp"
#include "axCmPerfPollRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axCmPerfCm.hpp"
#include "axInternalCm.hpp"
#include "axIteratorHolder.hpp"
#include "axDbCmts.hpp"
#include "axSnmpSessionFactory.hpp"
#include "axSnmpVerSpecificAttrSetter.hpp"
#include "axSnmpTemplateSession.hpp"
#include "axSnmpUtils.hpp"
#include "axDbSnmpAttrHolder.hpp"
#include "axSnmpAsyncCbReqObj.hpp"


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
axCmPerfPollRunnable::axCmPerfPollRunnable() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(CM_PERF_POLL_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfPollRunnable::~axCmPerfPollRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmPerfPollRunnable::axCmPerfPollRunnable(
       axAbstractCARunnableCollection * rc, axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(CM_PERF_POLL_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmPerfPollRunnable::run(void)
{
  static const char * myName="cmPerfRblRun:";

  AX_INT32                ret = 0;
  AX_INT8                 ipAddress[128];
  axDbCmts                dbCmts;
  axSnmpTemplateSession   sessionTmpl;
  axSnmpAsyncCbReqObj   * reqObj;
  axAbsDbSnmpVerAttrs   * dbSnmpVerAttrs;
  axSnmpSession         * snmpSession;
  axListFree              openSnmpSessionList;
  pthread_t               tid;

  tid = pthread_self();

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry...\n", myName));

  axSnmpSessionFactory * sFact = axSnmpSessionFactory::getInstance();

  {
    axReadLockOperator rCmtsLock(m_intCmts->getLock());
    if (m_intCmts->isDeleted()) {
      goto EXIT_LABEL;
    }
  }

  // ACE_DEBUG((LM_MISC_DEBUG, "%s getting cmts\n", myName));

  dbCmts.m_cmtsResId = m_intCmts->getResId();
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  // ACE_DEBUG((LM_MISC_DEBUG, "%s getting ver spec attrs\n", myName));

  {
    axDbSnmpAttrHolder iH(dbSnmpVerAttrs = 
                                      dbCmts.getCmSnmpVersionAttrs());
    if (!dbSnmpVerAttrs) {
      goto EXIT_LABEL;
    }

    // ACE_DEBUG((LM_MISC_DEBUG, "%s doing the poll \n", myName));

    {
      axCmPerfCm cmPerfPoll(m_intCmts);

      cmPerfPoll.setCallback(&sessionTmpl);

      axSnmpVerSpecificAttrSetter attrSetter(&sessionTmpl, 
                                                      dbSnmpVerAttrs);
      attrSetter.setAttributes();

      axReadLockOperator rRunnableLock(getLock());

      axIteratorHolder iH(m_cmList.createIterator());
      axAbstractIterator * cmIter = iH.getIterator();

      axInternalCm * intCm;

      ACE_DEBUG((LM_MISC_DEBUG, "%s iter begin. tid=%d\n", 
                                                        myName, tid));

      for (intCm=dynamic_cast<axInternalCm *>(cmIter->getFirst());
           intCm;
           intCm=dynamic_cast<axInternalCm *>(cmIter->getNext())) {

        axReadLockOperator rCmLock(intCm->getLock());

        if (!intCm->hasData()) {
          continue;
        }

        axIntCmNonkey_t * nonkey = (axIntCmNonkey_t *)
                                                   intCm->getNonKey();
        getNetSnmpIpAddress(ipAddress, nonkey->ipAddress, 
                                               nonkey->ipAddressType);
        attrSetter.setIpAddress(ipAddress);

        snmpSession = sFact->createSession(&sessionTmpl);
        if (!snmpSession) {
          ACE_DEBUG((LM_MISC_DEBUG, "%s unable to create session \n", 
                                                             myName));
          continue;
        }

        axSnmpAsyncCbReqObj * cbReqObj = new 
                             axSnmpAsyncCbReqObj(intCm, &cmPerfPoll);

        /*
         * Needs to be freed. The list's destructor will take care of
         * this.
         */
        openSnmpSessionList.add(snmpSession);

        cbReqObj->setSnmpSession(snmpSession);

      } // for ...

      ACE_DEBUG((LM_MISC_DEBUG, "%s iter end. tid=%d\n", 
                                                       myName, tid));

      /*
       * SNMP Send and Wait for Reply processing
       */

      ACE_DEBUG((LM_MISC_DEBUG, "%s run begin. tid=%d\n", 
                                                       myName, tid));

      cmPerfPoll.run();

      ACE_DEBUG((LM_MISC_DEBUG, "%s run end. tid=%d\n", 
                                                       myName, tid));

    }
  }

EXIT_LABEL:

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit...\n", myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmPerfPollRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axCmPerfPollRunnable::addSubject(axObject * o)
{
  m_cmList.add(o);
}


//********************************************************************
// method:
//********************************************************************
size_t
axCmPerfPollRunnable::size(void)
{
  return (m_cmList.size());
}


