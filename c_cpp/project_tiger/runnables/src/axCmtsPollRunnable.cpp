
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCountsDataTypes.hpp"
#include "axCALog.hpp"
#include "axCmtsPollRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axSnmpSessionFactory.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axMessageDataTypes.hpp"
#include "axMessageManager.hpp"
#include "axCountsAndAlarmMsg.hpp"
#include "axSubSystemCommon.hpp"
#include "axDbCmts.hpp"
#include "axHighRateCmtsCmStatusTblWalk.hpp"
#include "axHighRateCmtsChannelTblWalk.hpp"
#include "axSnmpGenericGetNext.hpp"
#include "axSnmpUtils.hpp"
#include "axSnmpSessionHolder.hpp"
#include "axSnmpTemplateSession.hpp"
#include "axAbsDbSnmpVerAttrs.hpp"
#include "axDbSnmpAttrHolder.hpp"
#include "axSnmpVerSpecificAttrSetter.hpp"
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
axCmtsPollRunnable::axCmtsPollRunnable() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(CMTS_POLL_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsPollRunnable::~axCmtsPollRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsPollRunnable::axCmtsPollRunnable(
       axAbstractCARunnableCollection * rc, axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(CMTS_POLL_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsPollRunnable::run(void)
{
  static const char * myName="cmtsPollRblRun:";

  AX_INT32              ret = 0;
  AX_INT8               ipAddressIn[64];
  AX_INT8               ipAddressOut[128];
  AX_INT32              ipAddressType;
  axDbCmts              dbCmts;
  axSnmpTemplateSession sessionTmpl;
  axSnmpSession       * snmpSession;
  axAbsDbSnmpVerAttrs * dbSnmpVerAttrs;
  axSnmpAsyncCbReqObj * reqObj;

  pthread_t tid = pthread_self();

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry. tid=%d, cmts=%d...\n", myName,
                                        tid, m_intCmts->getResId()));

  axSnmpSessionFactory * sFact = axSnmpSessionFactory::getInstance();

  {
    axReadLockOperator rCmtsLock(m_intCmts->getLock());
    if (m_intCmts->isDeleted()) {
      goto EXIT_LABEL;
    }
    axIntCmtsNonkey_t * nonkey = (axIntCmtsNonkey_t *)
                                               m_intCmts->getNonKey();
    strcpy(ipAddressIn, nonkey->ipAddress);
#if 0
    if (strlen(ipAddressIn)) {
      ACE_DEBUG((LM_CRITICAL, "%s ip=%s\n", myName, ipAddressIn));
      assert(strlen(ipAddressIn)==0);
    }
#endif
    ipAddressType = nonkey->ipAddressType;
  }

  dbCmts.m_cmtsResId = m_intCmts->getResId();
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  {
    axDbSnmpAttrHolder iH(dbSnmpVerAttrs = 
                                      dbCmts.getCmSnmpVersionAttrs());
    if ( !dbSnmpVerAttrs ) {
      goto EXIT_LABEL;
    }

    {
      axSnmpVerSpecificAttrSetter attrSetter(&sessionTmpl, 
                                                      dbSnmpVerAttrs);
      attrSetter.setAttributes();
      getNetSnmpIpAddress(ipAddressOut, ipAddressIn, ipAddressType);
      attrSetter.setIpAddress(ipAddressOut);
      axSnmpSessionHolder sH(snmpSession=
                                  sFact->createSession(&sessionTmpl));
      if (!snmpSession) {
        goto EXIT_LABEL;
      }

#if 0
      ACE_DEBUG((LM_MISC_DEBUG, "%s ipIn=%s, ipOut=%s\n", myName, 
                                         ipAddressIn, ipAddressOut));
#endif

      {
        axSnmpGenericGetNext cmtsCapability;
        cmtsCapability.setCallback(snmpSession);
        axSnmpAsyncCbReqObj * cbReqObj = new
                       axSnmpAsyncCbReqObj(m_intCmts, &cmtsCapability);
        cbReqObj->setSnmpSession(snmpSession);
        cmtsCapability.setOid(SNMP_CMTS_DOCSIS_CAPABILITY_OID);
        if (cmtsCapability.run()) {
          axSnmpGenericGetResultValue_s * resp =
                                     (axSnmpGenericGetResultValue_s *) 
                                  cmtsCapability.getDecodedResponse();
          m_intCmts->setDocsisCapability(resp->values[0].data.intVal);
        }

      }

      {
        axHighRateCmtsChannelTblWalk cmtsIfTableWalk(m_intCmts);
        cmtsIfTableWalk.setCallback(snmpSession);
        axSnmpAsyncCbReqObj * cbReqObj = new
                     axSnmpAsyncCbReqObj(m_intCmts, &cmtsIfTableWalk);
        cbReqObj->setSnmpSession(snmpSession);
        cmtsIfTableWalk.run();
      }

      {
        axHighRateCmtsCmStatusTblWalk cmtsCmStatusTableWalk(m_intCmts);
        cmtsCmStatusTableWalk.setCallback(snmpSession);
        axSnmpAsyncCbReqObj * cbReqObj = new
               axSnmpAsyncCbReqObj(m_intCmts, &cmtsCmStatusTableWalk);
        cbReqObj->setSnmpSession(snmpSession);
        cmtsCmStatusTableWalk.run();
      }

    }
  }

EXIT_LABEL:

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit. tid=%d, cmts=%d...\n", myName,
                                        tid, m_intCmts->getResId()));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsPollRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  axCountsAndAlarmsPayload_s * p1 = new axCountsAndAlarmsPayload_s();
  p1->cmtsResId = m_intCmts->getResId();
  p1->countsType = AX_COUNTS_TYPE_ALL;
  axMessagePayload_s * p = new axMessagePayload_s();
  p->specificPayloadData = p1;

  axCountsAndAlarmMsg * msg = 
                    new axCountsAndAlarmMsg(AX_SS_TYPE_CMTS_POLL, p);
  axMessageManager::getInstance()->sendMessage(msg);

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsPollRunnable::addSubject(axObject * o)
{
}


