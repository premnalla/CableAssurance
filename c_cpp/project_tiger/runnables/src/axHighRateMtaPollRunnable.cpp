
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axListFree.hpp"
#include "axHighRateMtaPollRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axSnmpSessionFactory.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axAbstractIterator.hpp"
#include "axInternalGenMta.hpp"
#include "axHRMtaPollWork.hpp"
#include "axInternalUtils.hpp"
#include "axIteratorHolder.hpp"
#include "axSnmpSessionFactory.hpp"
#include "axSnmpVerSpecificAttrSetter.hpp"
#include "axSnmpTemplateSession.hpp"
#include "axSnmpUtils.hpp"
#include "axDbSnmpAttrHolder.hpp"
#include "axSnmpAsyncCallbackObj.hpp"
#include "axSnmpCmtsCmStatusTblWalk.hpp"
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
axHighRateMtaPollRunnable::axHighRateMtaPollRunnable() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(HIGHRATE_MTA_POLL_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axHighRateMtaPollRunnable::~axHighRateMtaPollRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHighRateMtaPollRunnable::axHighRateMtaPollRunnable(
    axAbstractCARunnableCollection * rc, axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(HIGHRATE_MTA_POLL_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axHighRateMtaPollRunnable::run(void)
{
  static const char * myName="MtaPollRblRun:";

  AX_INT32                ret = 0;
  AX_INT8                 ipAddress[128];
  axDbCmts                dbCmts;
  axSnmpTemplateSession   sessionTmpl;
  axAbsDbSnmpVerAttrs   * dbSnmpVerAttrs;
  axSnmpSession         * snmpSession;
  axListFree              openSnmpSessionList;
  pthread_t               tid;

  tid = pthread_self();

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry\n", myName));

  axSnmpSessionFactory * sFact = axSnmpSessionFactory::getInstance();

  {
    axReadLockOperator rCmtsLock(m_intCmts->getLock());
    if (m_intCmts->isDeleted()) {
      goto EXIT_LABEL;
    }
  }

  dbCmts.m_cmtsResId = m_intCmts->getResId();
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  {
    axDbSnmpAttrHolder iH(dbCmts.getCmSnmpVersionAttrs());
    if ( !(dbSnmpVerAttrs=iH.getAttrs()) ) {
      goto EXIT_LABEL;
    }

    {
      axHRMtaPollWork mtaPoll(m_intCmts);
      mtaPoll.setCallback(&sessionTmpl);

      axSnmpVerSpecificAttrSetter attrSetter(&sessionTmpl, 
                                                     dbSnmpVerAttrs);
      attrSetter.setAttributes();

      axReadLockOperator rRunnableLock(getLock());

      axIteratorHolder iH(m_mtaList.createIterator());
      axAbstractIterator * mtaIter = iH.getIterator();

      axInternalGenMta * gMta;

      ACE_DEBUG((LM_MISC_DEBUG, "%s iter begin. tid=%d\n", myName, tid));

      for (gMta=dynamic_cast<axInternalGenMta *> (mtaIter->getFirst());
           gMta;
           gMta=dynamic_cast<axInternalGenMta *> (mtaIter->getNext())) {

        axReadLockOperator rMtaLock(gMta->getLock());

        if (!gMta->hasData()) {
          continue;
        }

        /*
         * Don't poll offline eMTA's
         */
        if (gMta->isEmta()) {
          axIntEmtaNonkey_t * eNk = (axIntEmtaNonkey_t *)
                                                   gMta->getNonKey();
          axInternalCm * intCm = eNk->cmPtr;
          axReadLockOperator cmRlock(intCm->getLock());
          axIntCmNonkey_t * cmNk = (axIntCmNonkey_t *)
                                                  intCm->getNonKey();
          if (!intCm->hasData() ||
              !axInternalCm::isCmOnline(cmNk->docsisState)) {
            continue;
          }
        }

        axIntGenMtaNonkey_t * nonkey = (axIntGenMtaNonkey_t *) 
                                                   gMta->getNonKey();
        getNetSnmpIpAddress(ipAddress, nonkey->ipAddress,
                                              nonkey->ipAddressType);
        attrSetter.setIpAddress(ipAddress);

        snmpSession = sFact->createSession(&sessionTmpl);
        if (!snmpSession) {
          continue;
        }

        axSnmpAsyncCbReqObj * cbReqObj = new
                                 axSnmpAsyncCbReqObj(gMta, &mtaPoll);
        /*
         * Needs to be freed. The list's destructor will take care of
         * this.
         */
        openSnmpSessionList.add(snmpSession);

        cbReqObj->setSnmpSession(snmpSession);

      }

      ACE_DEBUG((LM_MISC_DEBUG, "%s iter end. tid=%d\n", myName, tid));

      /*
       * Send SNMP request and process reply
       */

      ACE_DEBUG((LM_MISC_DEBUG, "%s run begin. tid=%d\n", myName, tid));

      mtaPoll.run();

      ACE_DEBUG((LM_MISC_DEBUG, "%s run end. tid=%d\n", myName, tid));

    }

  }

EXIT_LABEL:

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit\n", myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateMtaPollRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateMtaPollRunnable::addSubject(axObject * o)
{
  m_mtaList.add(o);
}


