
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmPerfCm.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axSnmpCmPerfCmReqType.hpp"
#include "axIteratorHolder.hpp"
#include "axInternalCm.hpp"
#include "axDbCmPerfLog.hpp"
#include "axCmPerfUtils.hpp"

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
axCmPerfCm::axCmPerfCm()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfCm::~axCmPerfCm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmPerfCm::axCmPerfCm(axInternalCmts * cmts) :
  axAbstractSnmpFloodGetWork(cmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfCm::createAndSendInitialRequests(void)
{
  static const char * myName="cmPerfCrtAndSndInitReqs:";

  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  ACE_DEBUG((LM_SNMP_DEBUG, "%s entry \n", myName));

  axSnmpCmPerfCmGetNextOid_s * oids = (axSnmpCmPerfCmGetNextOid_s *)
                                                     getInitialOids();
  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  for (cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getFirst());
       cbRObj;
       cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getNext())) {

    axAbstractSnmpSession * sS = cbRObj->getSnmpSession();

    if (!sS) {
      ACE_DEBUG((LM_SNMP_DEBUG, "%s continuing \n", myName));
      continue;
    }

    // ACE_DEBUG((LM_SNMP_DEBUG, "%s creating pdu... \n", myName));

    netsnmp_pdu * req = snmp_pdu_create(SNMP_MSG_GETNEXT);

    for (int i=0; i<oids->numOids; i++) {
      snmp_add_null_var(req, oids->oids[i].nativeOid,
                                           oids->oids[i].nativeOidLen);
    }

    if (snmp_sess_send(sS->getInternalSessionList(), req)) {
      cbRObj->setStateReqSent();
      ACE_DEBUG((LM_SNMP_DEBUG, "%s sent inital req \n", myName));
    } else {
      snmp_free_pdu(req);
      cbRObj->setStateSendReqFailed();
      ACE_DEBUG((LM_SNMP_DEBUG, "%s unable to send req \n", myName));
    }

  }

  ACE_DEBUG((LM_SNMP_DEBUG, "%s exit \n", myName));

  return (ret);
}


//********************************************************************
// method:
// return:
//********************************************************************
void
axCmPerfCm::decodeResponse(netsnmp_pdu * resp, axSnmpRespDecodeRC_s & rc)
{
  // initialize
  rc.init();
  m_decodedResponse.numValues = 0;

  axSnmpCmPerfCmGetNextOid_s * initialOids =
                     (axSnmpCmPerfCmGetNextOid_s *) getInitialOids();

  netsnmp_variable_list * var = resp->variables;
  bool isDone = false;

  for (int j=0; j<initialOids->numOids && !isDone;
       j++, var=var->next_variable) {

    axSnmpNativeOid_s * p = &initialOids->oids[j];

    /* check to verify that var is from the same tree */
    if ( (var->name_length < p->nativeOidLen) ||
         memcmp(p->nativeOid, var->name, (p->nativeOidLen * sizeof(oid))) )
    {
      isDone = true;
      continue;
    }


    if ((var->type != SNMP_ENDOFMIBVIEW) &&
        (var->type != SNMP_NOSUCHOBJECT) &&
        (var->type != SNMP_NOSUCHINSTANCE)) {

      /* verify OID's are increasing */
      if (snmp_oid_compare(p->nativeOid, p->nativeOidLen, var->name,
                           var->name_length) >= 0) {
        isDone = true;
        continue;
      }

    } else {
      isDone = true;
      continue;
    }

    /*
     * Finally we get to process the result
     */
    getValue(j, var);

  } // for

  if (!isDone) {
    rc.hasData = true;
    m_decodedResponse.numValues = 1;
  }

  return;
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfCm::createAndSendNextRequests(axSnmpAsyncCbReqObj * cbReqObj)
{
  bool ret = true;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axCmPerfCm::getInitialOids(void)
{
  return (axSnmpCmPerfCmReqType::getInstance()->getOids());
}


//********************************************************************
// method:
//********************************************************************
void
axCmPerfCm::useResponse(axSnmpAsyncCbReqObj * cbReqObj)
{
  // static const char * myName="CmPerf:useResponse:";

  bool updateReq = false;

  axIntCmNonkey_t * cmData;

  axInternalCm * intCm = dynamic_cast<axInternalCm *> 
                                      (cbReqObj->getInternalObject());

  axWriteLockOperator cmRlock(intCm->getLock());

  if (!intCm->hasData()) {
    goto EXIT_LABEL;
  }

  cmData = (axIntCmNonkey_t *) intCm->getNonKey();
  if (cmData->downstreamSNR != m_decodedResponse.values[0].downstreamSNR) {
    cmData->downstreamSNR = m_decodedResponse.values[0].downstreamSNR;
    updateReq = true;
  }
  if (cmData->downstreamPower != m_decodedResponse.values[0].downstreamPower) {
    cmData->downstreamPower = m_decodedResponse.values[0].downstreamPower;
    updateReq = true;
  }
  if (cmData->upstreamPower != m_decodedResponse.values[0].upstreamPower) {
    cmData->upstreamPower = m_decodedResponse.values[0].upstreamPower;
    updateReq = true;
  }
  if (cmData->t1Timeouts != m_decodedResponse.values[0].t1Timeouts) {
    cmData->t1Timeouts = m_decodedResponse.values[0].t1Timeouts;
    updateReq = true;
  }
  if (cmData->t2Timeouts != m_decodedResponse.values[0].t2Timeouts) {
    cmData->t2Timeouts = m_decodedResponse.values[0].t2Timeouts;
    updateReq = true;
  }
  if (cmData->t3Timeouts != m_decodedResponse.values[0].t3Timeouts) {
    cmData->t3Timeouts = m_decodedResponse.values[0].t3Timeouts;
    updateReq = true;
  }
  if (cmData->t4Timeouts != m_decodedResponse.values[0].t4Timeouts) {
    cmData->t4Timeouts = m_decodedResponse.values[0].t4Timeouts;
    updateReq = true;
  }

#if 1
  struct timeval tm;
  gettimeofday(&tm, NULL);

  if (updateReq) {

    bool isTca = axCmPerfUtils::IsCmThresholdCrossed(cmData->downstreamSNR,
       cmData->downstreamPower, cmData->upstreamPower);

    if (isTca && !cmData->tca) {
      axCmPerfUtils::NewTcaUpdateWork(true, tm.tv_sec, cmData);
    } else if (isTca && cmData->tca) {
      axCmPerfUtils::NewTcaUpdateWork(false, tm.tv_sec, cmData);
    }

  } else {
    axCmPerfUtils::LogTcaWork(tm.tv_sec, cmData);
  }
#endif

EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCmPerfCm::getValue(int attrNum, netsnmp_variable_list * var)
{

  switch (attrNum) {
    case 0: /* SNMP_CM_PERF_CM_DN_SNR_OID */
      m_decodedResponse.values[0].downstreamSNR = *(var->val.integer);
      break;

    case 1: /* SNMP_CM_PERF_CM_DN_PWR_OID */
      m_decodedResponse.values[0].downstreamPower = *(var->val.integer);
      break;

    case 2: /* SNMP_CM_PERF_CM_UP_SNR_OID */
      m_decodedResponse.values[0].upstreamPower = *(var->val.integer);
      break;

    case 3: /* SNMP_CM_PERF_CM_T1_TO_OID */
      m_decodedResponse.values[0].t1Timeouts = *(var->val.integer);
      break;

    case 4: /* SNMP_CM_PERF_CM_T2_TO_OID */
      m_decodedResponse.values[0].t2Timeouts = *(var->val.integer);
      break;

    case 5: /* SNMP_CM_PERF_CM_T3_TO_OID */
      m_decodedResponse.values[0].t3Timeouts = *(var->val.integer);
      break;

    case 6: /* SNMP_CM_PERF_CM_T4_TO_OID */
      m_decodedResponse.values[0].t4Timeouts = *(var->val.integer);
      break;

    default:
      break;
  }
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axCmPerfCm::getDownstreamSNR(void)
{
  return (m_decodedResponse.values[0].downstreamSNR);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axCmPerfCm::getDownstreamPower(void)
{
  return (m_decodedResponse.values[0].downstreamPower);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axCmPerfCm::getUpstreamPower(void)
{
  return (m_decodedResponse.values[0].upstreamPower);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCmPerfCm::getT1Timeouts(void)
{
  return (m_decodedResponse.values[0].t1Timeouts);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCmPerfCm::getT2Timeouts(void)
{
  return (m_decodedResponse.values[0].t2Timeouts);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCmPerfCm::getT3Timeouts(void)
{
  return (m_decodedResponse.values[0].t3Timeouts);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCmPerfCm::getT4Timeouts(void)
{
  return (m_decodedResponse.values[0].t4Timeouts);
}


