
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHRMtaPollWork.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axInternalGenMta.hpp"
#include "axSnmpHRMtaReqType.hpp"
#include "axIteratorHolder.hpp"
#include "axDbEmta.hpp"
#include "axDbMtaProvStatusLog.hpp"


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
axHRMtaPollWork::axHRMtaPollWork()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHRMtaPollWork::~axHRMtaPollWork()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHRMtaPollWork::axHRMtaPollWork(axInternalCmts * cmts) :
  axAbstractSnmpFloodGetWork(cmts)
{
  // reqType = axSnmpHRMtaReqType::getInstance();
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axHRMtaPollWork::getInitialOids(void)
{
  return (axSnmpHRMtaReqType::getInstance()->getOids());
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPollWork::createAndSendInitialRequests(void)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpHRMtaPollOids_s * oids = (axSnmpHRMtaPollOids_s *)
                                                     getInitialOids();
  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  for (cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getFirst());
       cbRObj;
       cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getNext())) {

    axAbstractSnmpSession * sS = cbRObj->getSnmpSession();

    if (!sS) {
      continue;
    }

    netsnmp_pdu * req = snmp_pdu_create(SNMP_MSG_GETNEXT);

    for (int i=0; i<oids->numOids; i++) {
      snmp_add_null_var(req, oids->oids[i].nativeOid,
                                           oids->oids[i].nativeOidLen);
    }

    if (snmp_sess_send(sS->getInternalSessionList(), req)) {
      cbRObj->setStateReqSent();
      ACE_DEBUG((LM_SNMP_DEBUG, "sent inital req\n"));
    } else {
      snmp_free_pdu(req);
      cbRObj->setStateSendReqFailed();
      ACE_DEBUG((LM_SNMP_DEBUG, "unable to send inital req\n"));
    }

  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPollWork::createAndSendNextRequests(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  bool ret = true;

  return (ret);
}


//********************************************************************
// method:
// return:
//********************************************************************
void
axHRMtaPollWork::decodeResponse(netsnmp_pdu * resp, 
                                            axSnmpRespDecodeRC_s & rc)
{
  // initialize
  rc.init();
  m_decodedResponse.numValues = 0;

  axSnmpHRMtaPollOids_s * initialOids = 
                            (axSnmpHRMtaPollOids_s *) getInitialOids();

  netsnmp_variable_list * var = resp->variables;

  bool isDone = false;

  for (int j=0; j<initialOids->numOids && !isDone;
       j++, var=var->next_variable) {

    axSnmpNativeOid_s * p = &initialOids->oids[j];

    /* check to verify that var is from the same tree */
    if ( (var->name_length < p->nativeOidLen) ||
         memcmp(p->nativeOid, var->name, 
                                    (p->nativeOidLen * sizeof(oid))) )
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

// EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axHRMtaPollWork::useResponse(axSnmpAsyncCbReqObj * cbReqObj)
{
  bool updateReq = false;
  axDbEmta dbEmta;
  struct timeval tm;
  INTDS_RESID_t mtaResId;

  axIntGenMtaNonkey_t * nk;

  axInternalGenMta * intMta = dynamic_cast<axInternalGenMta *> 
                                     (cbReqObj->getInternalObject());

  axWriteLockOperator mtaWlock(intMta->getLock());

  if (!intMta->hasData()) {
    goto EXIT_LABEL;
  }

  mtaResId = intMta->getResId();

  nk = (axIntGenMtaNonkey_t *) intMta->getNonKey();

  if (nk->provState == m_decodedResponse.values[0].provisionedStatus) {
    goto EXIT_LABEL;
  }

  dbEmta.m_mtaResId = mtaResId;
  if (!dbEmta.getRow()) {
    goto EXIT_LABEL;
  }

  gettimeofday(&tm, NULL);

  /*
   * Keep it nice and simple. First update obvious
   */

  dbEmta.m_provState = m_decodedResponse.values[0].provisionedStatus;
  dbEmta.m_isProvStatePass = axInternalGenMta::isProvStatePass(
                                                 dbEmta.m_provState);
  if (dbEmta.updateRow()) {
    nk->provState = m_decodedResponse.values[0].provisionedStatus;
  }

  {
    axDbMtaProvStatusLog pLog(mtaResId, tm.tv_sec, nk);
    pLog.insertRow();
  }

EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axHRMtaPollWork::getValue(int attrId, netsnmp_variable_list * var)
{
  switch (attrId) {
    case 0:  /* provisioned status */
      m_decodedResponse.values[0].provisionedStatus = *(var->val.integer);
      break;

    default:
      break;
  }
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axHRMtaPollWork::getProvStatus(void)
{
  return (m_decodedResponse.values[0].provisionedStatus);
}


