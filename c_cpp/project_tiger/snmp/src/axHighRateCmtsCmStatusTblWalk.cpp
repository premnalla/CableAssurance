
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHighRateCmtsCmStatusTblWalk.hpp"
#include "axCASystemConfig.hpp"
#include "axSnmpUtils.hpp"
#include "axCAScheduler.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalCm.hpp"
#include "axList.hpp"
#include "axInternalChannel.hpp"
#include "axDbCmStatusLog.hpp"
#include "axCmPerfPoller.hpp"
#include "axCmPerfPollRunnableCollection.hpp"
#include "axIteratorHolder.hpp"
#include "axDbCmCurrentStatus.hpp"
#include "axDbCurrentCmPerf.hpp"
#include "axCmPerfUtils.hpp"
// #include "axDbCmStatusLog.hpp"
// #include "axDbCmPerfLog.hpp"

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
axHighRateCmtsCmStatusTblWalk::axHighRateCmtsCmStatusTblWalk() : 
  m_reqType(NULL), m_intCmts(NULL), m_modemIndex(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHighRateCmtsCmStatusTblWalk::~axHighRateCmtsCmStatusTblWalk()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHighRateCmtsCmStatusTblWalk::axHighRateCmtsCmStatusTblWalk(
                                            axInternalCmts * intCmts) :
  m_intCmts(intCmts), m_modemIndex(0)
{
  m_reqType = axSnmpCmtsCmStatusReqType::getInstance();  
  m_nextOids.numOids = m_reqType->getOids()->numOids;
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axHighRateCmtsCmStatusTblWalk::getInitialOids(void)
{
  return (m_reqType->getOids());
}


#if 0
//********************************************************************
// method:
//********************************************************************
netsnmp_pdu *
axHighRateCmtsCmStatusTblWalk::createRequestPdu(void)
{
  netsnmp_pdu * req = snmp_pdu_create(SNMP_MSG_GETBULK);
  req->non_repeaters = m_reqType->getNonRepeters();
  req->max_repetitions = m_reqType->getMaxRepetitions();
  return (req);
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateCmtsCmStatusTblWalk::addOids(netsnmp_pdu * req, axSnmpOidsBase_s * o)
{
  // axSnmpCmtsCmOids_s * oids = dynamic_cast<axSnmpCmtsCmOids_s *> (o);
  axSnmpCmtsCmOids_s * oids = (axSnmpCmtsCmOids_s *) o;
  for (int i=0; i<oids->numOids; i++) {
    snmp_add_null_var(req, oids->oids[i].nativeOid, oids->oids[i].nativeOidLen);
  }
}
#endif


//********************************************************************
// method:
//********************************************************************
bool
axHighRateCmtsCmStatusTblWalk::createAndSendInitialRequests(void)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpCmtsCmOids_s * oids;
  axSnmpCmtsCmOids_s   singleGetOids;

  if (m_modemIndex) {
    oids = &singleGetOids;
    m_reqType->getOids(m_modemIndex, singleGetOids);
  } else {
    oids = (axSnmpCmtsCmOids_s *) getInitialOids();
  }

  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  for (cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getFirst());
       cbRObj;
       cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getNext())) {

    axAbstractSnmpSession * sS = cbRObj->getSnmpSession();

    if (!sS) {
      continue;
    }

    netsnmp_pdu * req;

    if (m_modemIndex) {
      req = snmp_pdu_create(SNMP_MSG_GET);
    } else {
      req = snmp_pdu_create(SNMP_MSG_GETBULK);
      req->non_repeaters = m_reqType->getNonRepeters();
      req->max_repetitions = m_reqType->getMaxRepetitions();
    }

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
axHighRateCmtsCmStatusTblWalk::createAndSendNextRequests(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpCmtsCmOids_s * oids = (axSnmpCmtsCmOids_s *) &m_nextOids;

  /*
   * NOTE: don't have to use iterator since there is only one in the list
   * Instead can use the param into this method
   */

  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  for (cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getFirst());
       cbRObj;
       cbRObj=dynamic_cast<axSnmpAsyncCbReqObj*> (iter->getNext())) {

    axAbstractSnmpSession * sS = cbRObj->getSnmpSession();

    if (!sS) {
      continue;
    }

    netsnmp_pdu * req = snmp_pdu_create(SNMP_MSG_GETBULK);
    req->non_repeaters = m_reqType->getNonRepeters();
    req->max_repetitions = m_reqType->getMaxRepetitions();

    for (int i=0; i<oids->numOids; i++) {
      snmp_add_null_var(req, oids->oids[i].nativeOid,
                                           oids->oids[i].nativeOidLen);
    }

    if (snmp_sess_send(sS->getInternalSessionList(), req)) {
      cbRObj->setStateReqSent();
      ACE_DEBUG((LM_SNMP_DEBUG, "sent next req\n"));
    } else {
      snmp_free_pdu(req);
      cbRObj->setStateSendReqFailed();
      ACE_DEBUG((LM_SNMP_DEBUG, "unable to send next req\n"));
    }

  }

  return (ret);
}


//********************************************************************
// method:
// return:
//********************************************************************
void
axHighRateCmtsCmStatusTblWalk::decodeResponse(netsnmp_pdu * resp, 
                                   axSnmpRespDecodeRC_s & returnCodes)
{
  netsnmp_variable_list * var;
  size_t baseLen;
  int    numVarsInResult;
  int    numBatchesInResult;
  int    i, j;
  bool   done;

  // initialize
  m_decodedResponse.numValues = 0;
  returnCodes.init();

  axSnmpCmtsCmOids_s * initialOids = (axSnmpCmtsCmOids_s *) m_reqType->getOids();

  /*
   * Assumes we are getting 'n' vars from the same table, meaning
   * all 'n' vars have the same base length. The (-1) is to get oid
   * from the start up to and NOT including the column in the table.
   */
  baseLen = initialOids->oids[0].nativeOidLen - 1;

  numVarsInResult = 0;
  for (var = resp->variables; var; var = var->next_variable) {
    ++numVarsInResult;
  }

  numBatchesInResult = numVarsInResult / initialOids->numOids;

  if ( !numBatchesInResult ) {
    goto EXIT_LABEL;
  }

  if (numBatchesInResult > m_decodedResponse.maxValues) {
    numBatchesInResult = m_decodedResponse.maxValues;
  }

  var = resp->variables;
  done = false;

  for (i=0; i<numBatchesInResult && !done; i++) {

    for (j=0; j<initialOids->numOids && !done;
         j++, var=var->next_variable) {

      axSnmpNativeOid_s * p = &initialOids->oids[j];

      /* check to verify that var is from the same tree */
      if ( (var->name_length < baseLen) ||
           memcmp(p->nativeOid, var->name, (baseLen * sizeof(oid))) )
      {
        done = true;
        continue;
      }

      if ((var->type != SNMP_ENDOFMIBVIEW) &&
          (var->type != SNMP_NOSUCHOBJECT) &&
          (var->type != SNMP_NOSUCHINSTANCE)) {

        /* verify OID's are increasing */
        if (snmp_oid_compare(p->nativeOid, p->nativeOidLen, var->name,
                             var->name_length) >= 0) {
          done = true;
          continue;
        }

      } else {
        done = true;
        continue;
      }

      /*
       * Finally we get to process the result
       */
      getValue(i, j, var);

      /*
       * IF Last Set in the Batch, store for Next Get
       */
      if (i == (numBatchesInResult-1)) {
        m_nextOids.oids[j].nativeOidLen = var->name_length;
        memcpy(m_nextOids.oids[j].nativeOid, var->name,
               (var->name_length * sizeof(oid)));
      }

    } // inner for; foreach var in a batch

  } // outer for; foreach batch

  /*
   * Set number of complete values in the decoded response
   */
  if (!done) {
    m_decodedResponse.numValues = i;
    returnCodes.moreDataPossible = true;
    returnCodes.hasData = true;
  } else {
    if (i>0) {
      m_decodedResponse.numValues = i-1;
      returnCodes.hasData = true;
    }
  }


EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateCmtsCmStatusTblWalk::useResponse(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  synchronizedTable_t * cmTblStruct = m_intCmts->getCmTable();

  axInternalCm * cm;

  axList newCmList;
  axList addedCmList;

  {
    axReadLockOperator cmTblLock(cmTblStruct->lock);

    axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (cmTblStruct->table);

    axInternalCm * actualIntCm;

    for (int i=0; i<m_decodedResponse.numValues; i++) {

      axSnmpCmtsCmResultRow_s * r = &m_decodedResponse.values[i];
      axInternalCm tmpIntCm(r->mac);
      actualIntCm = dynamic_cast<axInternalCm *> (cmTbl->find(&tmpIntCm));

      if (!actualIntCm) {

        // handle new cm
        axInternalCm * newCm = new axInternalCm(r);
        newCmList.add(newCm);

      } else {

        // do a diff/cmp of attr values and take necessary actions
        axInternalCm cm(r);
        // actualIntCm->compareAndUpdate(&cm, m_intCmts->getResId());
        axWriteLockOperator actCmWlock(actualIntCm->getLock());
        compareAndUpdate(actualIntCm, &cm);

      }

    } // for()

  }

  /*
   * add cm to db
   */
  if (newCmList.size()) {

    struct timeval tm;
    gettimeofday(&tm, NULL);

    synchronizedTable_t * channelTblStruct = m_intCmts->getChannelTable();
    axReadLockOperator chnlTblLock(channelTblStruct->lock);
    axAvlTree * channelTbl = dynamic_cast<axAvlTree *> 
                                                (channelTblStruct->table);

    axWriteLockOperator cmTblLock(cmTblStruct->lock);
    axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (cmTblStruct->table);

    for (cm = (axInternalCm *) newCmList.remove(); cm;
         cm = (axInternalCm *) newCmList.remove()) {

      /*
       * No need to lock here since the cm is not yet added to the
       * global tree
       */
      axIntCmNonkey_t * cmNk = (axIntCmNonkey_t *) cm->getNonKey();

      /*
       *
       */
      cmNk->lastStateLogTime = cmNk->lastPerfLogTime = tm.tv_sec;

      if (cm->isAddable()) {

        /*
         * m_intCmts->getResId() WILL be zero
         */
        axDbCm dbCm(cm, m_intCmts->getResId());

        if (dbCm.insertRow()) {

          /* get up/down channel name, etc., */
          dbCm.getRow();

          cmNk->resId = dbCm.m_cmResId;
          // dbAddedCmList.add(cm);

          axDbCmCurrentStatus cmCurrStatus(cmNk->resId, tm.tv_sec, cmNk);
          // cmCurrStatus.m_lastLogTime = tm.tv_sec;
          cmCurrStatus.m_lastStateChangeTime = tm.tv_sec;
          cmCurrStatus.insertRow();

          // axDbCmStatusLog stLog(cmNk->resId, tm.tv_sec, cmNk);
          // stLog.insertRow();

          axDbCurrentCmPerf cmPerf(cmNk->resId, tm.tv_sec, cmNk);
          // cmPerf.m_lastLogTime = tm.tv_sec;
          cmPerf.m_lastTcaChangeTime = tm.tv_sec;
          cmPerf.insertRow();

          // axDbCmPerfLog perfLog(cmNk->resId, tm.tv_sec, cmNk);
          // perfLog.insertRow();

          /*
           * Add to internal tree
           */
          if (!cmTbl->add(cm)) {
            delete cm;
            continue;
          }

          /*
           * Locate channel and add CM to channel's child CM list
           */
          axInternalChannel tmpUpChnl(const_cast<AX_INT8 *> 
                                 (dbCm.m_upstreamChannelName.c_str()));
          axInternalChannel * intChnl;
          intChnl = (axInternalChannel *) channelTbl->find(&tmpUpChnl);
          if (intChnl) {
            axWriteLockOperator chnlWlock(intChnl->getLock());
            intChnl->addCmExtLock(cm);
          }

          /*
           * TODO: add to downstream channel as well
           */




          // finally
          addedCmList.add(cm);

        } else {
          delete cm;
        }

      } else {
        delete cm;
      }

    } // for

  }

  /*
   * add cm to appropriate Runnable(s)
   */
  if (addedCmList.size()) {

    axCmPerfPoller * cmPerfPoller = axCmPerfPoller::getInstance();
    axCmPerfPollRunnableCollection tmpRC(m_intCmts->getResId());
    axCmPerfPollRunnableCollection * actRc = 
      (axCmPerfPollRunnableCollection *) cmPerfPoller->find(&tmpRC);

    if (actRc) {
      actRc->addSubject(addedCmList);
    }

  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateCmtsCmStatusTblWalk::getValue(AX_INT32 rowNum, AX_INT32 attrId,
  netsnmp_variable_list * var)
{
  switch (attrId) {
    case 0: /* modemIndex */
      m_decodedResponse.values[rowNum].modemIndex = *(var->val.integer);
      break;

    case 1: /* mac */
      snmpMacToString(m_decodedResponse.values[rowNum].mac, var);;
      break;

    case 2: /* ip addr */
      snmpIPv4ToString(m_decodedResponse.values[rowNum].ipAddress, var);;
      break;

    case 3: /* downstream channel ifIndex */
      m_decodedResponse.values[rowNum].downstreamChannelIndex = *(var->val.integer);
      break;

    case 4: /* upstream channel ifIndex */
      m_decodedResponse.values[rowNum].upstreamChannelIndex = *(var->val.integer);
      break;

    case 5: /* cm status */
      m_decodedResponse.values[rowNum].docsisState = *(var->val.integer);
      break;

    case 6: /* upstream power */
      // m_decodedResponse.values[rowNum].upstreamPower = *(var->val.integer);
      break;

    case 7: /* upstream SNR */
      // m_decodedResponse.values[rowNum].upstreamSNR = *(var->val.integer);
      break;

    default:
      break;
  }
}


//********************************************************************
// method : compareAndUpdate
// in     : other CM with Possibily changed attributes
// return :
//    true : updates done
//    false: no updates done
//********************************************************************
bool
axHighRateCmtsCmStatusTblWalk::compareAndUpdate(axInternalCm * origCm,
                                                 axInternalCm * newCm)
{
  static const char * myName="CmtsCmWalk::compareAndUpdate:";

  bool ret = false;
  bool updateRequired = false;

#if 0
  INTDS_RESID_t cmtsResId = m_intCmts->getResId();
#endif

  axIntCmNonkey_t * newCmNk = (axIntCmNonkey_t *) newCm->getNonKey();

  axIntCmNonkey_t * origCmNk = (axIntCmNonkey_t *) origCm->getNonKey();

  struct timeval tm;
  gettimeofday(&tm, NULL);

  if (!origCm->hasData()) {

#if 0
    // new modem; or a modem that was inactive for a period of time

    if (!isCmOnline(newCmNk->docsisState)) {
      goto EXIT_LABEL;
    }

    // origCmNk = new axIntCmNonkey_t();
    origCm->allocateNonkey();

    origCmNk->docsisState = newCmNk->docsisState;
    origCmNk->modemIndex = newCmNk->modemIndex;
    origCmNk->upstreamChannelIndex = newCmNk->upstreamChannelIndex;
    origCmNk->downstreamChannelIndex =
                                  newCmNk->downstreamChannelIndex;
    copyIntIpAddress(origCmNk->ipAddress, newCmNk->ipAddress);

    axDbCm dbCm(this, cmtsResId);
    if (!dbCm.insertRow()) {
      origCm->freeNonkey();
      goto EXIT_LABEL;
    }

    ret = true;
#endif

  } else {

    // existing modem

    bool onlineStatusChanged = false;
    bool statusChanged = false;

    if (newCmNk->docsisState != origCmNk->docsisState) {

      statusChanged = true;

      if ( axInternalCm::isCmOnline(newCmNk->docsisState) ||
           axInternalCm::isCmOnline(origCmNk->docsisState) ) {
        onlineStatusChanged = true;
      }

      updateRequired = true;

      /*
       * Perhaps generate a message to see if eMTA or Set-Top needs to
       * be set to unavailable
       */
    }

    if (newCmNk->modemIndex != origCmNk->modemIndex) {
      updateRequired = true;
    }

    if (newCmNk->upstreamChannelIndex !=
                                     origCmNk->upstreamChannelIndex) {
      updateRequired = true;
    }

    if (newCmNk->downstreamChannelIndex !=
                                   origCmNk->downstreamChannelIndex) {
      updateRequired = true;
    }

    if (strcmp(newCmNk->ipAddress, origCmNk->ipAddress)) {
      updateRequired = true;
    }

    if (origCmNk->ipAddressType != newCmNk->ipAddressType) {
      updateRequired = true;
    }

#if 0
    if (newCmNk->upstreamSNRatCmts != origCmNk->upstreamSNRatCmts) {
      updateRequired = true;
    }

    if (newCmNk->upstreamPowerAtCmts !=
                                      origCmNk->upstreamPowerAtCmts) {
      updateRequired = true;
    }
#endif

    /*
     * Update DB
     */

    if (updateRequired) {

      /*
       *
       */

      axDbCm dbCm(origCm->getResId());

      if (!dbCm.getRow()) {
        ACE_DEBUG((LM_WARNING, "%s get failed", myName));
        goto EXIT_LABEL;
      }

      dbCm.m_upstreamChannelIndex = newCmNk->upstreamChannelIndex;
      dbCm.m_downstreamChannelIndex = newCmNk->downstreamChannelIndex;
      copyIntIpAddress(dbCm.m_ipAddress, newCmNk->ipAddress);
      dbCm.m_ipAddressType = newCmNk->ipAddressType;
      dbCm.m_docsisState = newCmNk->docsisState;
      dbCm.m_modemIndex = newCmNk->modemIndex;
      // dbCm.m_upstreamSNRatCmts = newCmNk->upstreamSNRatCmts;
      // dbCm.m_upstreamPowerAtCmts = newCmNk->upstreamPowerAtCmts;
      // dbCm.m_onlineOfflineChangeTime = newCmNk->onlineChangeTime;
      dbCm.m_isOnline = axInternalCm::isCmOnline(newCmNk->docsisState);

      /*
       * Update DB row
       */

      ret = dbCm.updateRow();

      if (!ret) {

        ACE_DEBUG((LM_WARNING, "%s update failed", myName));

      } else {

        origCmNk->modemIndex = newCmNk->modemIndex;
        origCmNk->upstreamChannelIndex =
                                      newCmNk->upstreamChannelIndex;
        origCmNk->downstreamChannelIndex =
                                    newCmNk->downstreamChannelIndex;
        copyIntIpAddress(origCmNk->ipAddress, newCmNk->ipAddress);
        origCmNk->ipAddressType = newCmNk->ipAddressType;
        // origCmNk->upstreamSNRatCmts = newCmNk->upstreamSNRatCmts;
        // origCmNk->upstreamPowerAtCmts = newCmNk->upstreamPowerAtCmts;

#if 0
        AX_UINT32 statusChgTimeDiff = 0;

        if (onlineStatusChanged) {
          origCmNk->onlineChangeTime = tm.tv_sec;
          statusChgTimeDiff = tm.tv_sec - origCmNk->lastStateLogTime;
          origCmNk->lastStateLogTime = tm.tv_sec;
        } else {
          statusChgTimeDiff = tm.tv_sec - origCmNk->lastStateLogTime;
          if (statusChgTimeDiff >
              (axCASystemConfig::getInstance()->
                               get_unchanged_log_interval() * 60)) {
            origCmNk->lastStateLogTime = tm.tv_sec;
          } else {
            statusChgTimeDiff = 0;
          }
        }

        if (statusChanged || statusChgTimeDiff) {
          axDbCmStatusLog sLog(origCmNk->resId, tm.tv_sec, origCmNk);
          sLog.insertRow();
        }

        if (statusChgTimeDiff) {
          axDbCmCurrentStatus currStatus(origCmNk->resId);
          if (currStatus.getRow()) {
            if (axInternalCm::isCmOnline(origCmNk->docsisState)) {
              if (onlineStatusChanged) {
                currStatus.m_time2 += statusChgTimeDiff;
              } else {
                currStatus.m_time1 += statusChgTimeDiff;
              }
            } else {
              if (onlineStatusChanged) {
                currStatus.m_time1 += statusChgTimeDiff;
              } else {
                currStatus.m_time2 += statusChgTimeDiff;
              }
            }
            currStatus.m_lastLogTime = origCmNk->lastStateLogTime;
            currStatus.m_lastStateChangeTime = newCmNk->onlineChangeTime;
            currStatus.updateRow();
          }
        }
#endif

        if (onlineStatusChanged) {

          origCmNk->onlineStateChanges++;

          /*
           * Insert Status log
           */
          axDbCmStatusLog sLog(origCmNk->resId, tm.tv_sec, origCmNk);
          if (sLog.insertRow()) {
            origCmNk->lastStateLogTime = tm.tv_sec;
          } else {
            ACE_DEBUG((LM_WARNING, "%s insert failed", myName));
          }

          /*
           * Update Current status
           */
          axDbCmCurrentStatus currStatus(origCmNk->resId);
          if (currStatus.getRow()) {
            if (axInternalCm::isCmOnline(origCmNk->docsisState)) {
              currStatus.m_currentValue = 1;
              currStatus.m_time2 += tm.tv_sec - origCmNk->onlineChangeTime;
            } else {
              currStatus.m_currentValue = 0;
              currStatus.m_time1 += tm.tv_sec - origCmNk->onlineChangeTime;
            }
            currStatus.m_stateChanges = origCmNk->onlineStateChanges;
            currStatus.m_lastLogTime = tm.tv_sec;
            currStatus.m_lastStateChangeTime = tm.tv_sec;
            if (currStatus.updateRow()) {
              origCmNk->onlineChangeTime = tm.tv_sec;
            } else {
              ACE_DEBUG((LM_WARNING, "%s current update failed", myName));
            }
          } else {
            ACE_DEBUG((LM_WARNING, "%s current get failed", myName));
          }

        }

      } // if (dbUpdate successful)

    } // if (updateRequired)
    else
    {
      axCmPerfUtils::LogStatusWork(tm.tv_sec, origCmNk);
    }

  } // if cm-hadData

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *  
axHighRateCmtsCmStatusTblWalk::getMac(void)
{
  return (m_decodedResponse.values[0].mac);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 * 
axHighRateCmtsCmStatusTblWalk::getIpAddress(void)
{
  return (m_decodedResponse.values[0].ipAddress);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32  
axHighRateCmtsCmStatusTblWalk::getDownstreamChannelIndex(void)
{
  return (m_decodedResponse.values[0].downstreamChannelIndex);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32  
axHighRateCmtsCmStatusTblWalk::getUpstreamChannelIndex(void)
{
  return (m_decodedResponse.values[0].upstreamChannelIndex);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32  
axHighRateCmtsCmStatusTblWalk::getModemIndex(void)
{
  return (m_decodedResponse.values[0].modemIndex);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16 
axHighRateCmtsCmStatusTblWalk::getUpstreamSNR(void)
{
  return (m_decodedResponse.values[0].upstreamSNR);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16 
axHighRateCmtsCmStatusTblWalk::getUpstreamPower(void)
{
  return (m_decodedResponse.values[0].upstreamPower);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8   
axHighRateCmtsCmStatusTblWalk::getDocsisState(void)
{
  return (m_decodedResponse.values[0].docsisState);
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateCmtsCmStatusTblWalk::setModemIndex(AX_INT32 mi)
{
  m_modemIndex = mi;
}


