
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axList.hpp"
#include "axHighRateCmtsChannelTblWalk.hpp"
#include "axSnmpGenericGetNext.hpp"
#include "axSnmpUtils.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalChannel.hpp"
#include "axIteratorHolder.hpp"
#include "axDbChannelCurrentCounts.hpp"

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
axHighRateCmtsChannelTblWalk::axHighRateCmtsChannelTblWalk() : 
  m_reqType(NULL), m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHighRateCmtsChannelTblWalk::~axHighRateCmtsChannelTblWalk()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHighRateCmtsChannelTblWalk::axHighRateCmtsChannelTblWalk(
                                           axInternalCmts * intCmts) :
  m_intCmts(intCmts)
{
  m_reqType = axSnmpCmtsChannelReqType::getInstance();  
  m_nextOids.numOids = m_reqType->getOids()->numOids;
}


#if 0
//********************************************************************
// method:
//********************************************************************
axSnmpValueBase_s *
axHighRateCmtsChannelTblWalk::getDecodedResponse(void)
{
  return (&m_decodedResponse);
}
#endif


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axHighRateCmtsChannelTblWalk::getInitialOids(void)
{
  return (m_reqType->getOids());
}


#if 0
//********************************************************************
// method:
//********************************************************************
netsnmp_pdu *
axHighRateCmtsChannelTblWalk::createRequestPdu(void)
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
axHighRateCmtsChannelTblWalk::addOids(netsnmp_pdu * req, axSnmpOidsBase_s * o)
{
  axSnmpCmtsChannelOids_s * oids = (axSnmpCmtsChannelOids_s *) o;
  for (int i=0; i<oids->numOids; i++) {
    snmp_add_null_var(req, oids->oids[i].nativeOid, oids->oids[i].nativeOidLen);
  }
}
#endif


//********************************************************************
// method:
//********************************************************************
bool
axHighRateCmtsChannelTblWalk::createAndSendInitialRequests(void)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpCmtsChannelOids_s * oids = (axSnmpCmtsChannelOids_s *)
                                                     getInitialOids();
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
axHighRateCmtsChannelTblWalk::createAndSendNextRequests(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpCmtsChannelOids_s * oids = (axSnmpCmtsChannelOids_s *)
                                                          &m_nextOids;

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
axHighRateCmtsChannelTblWalk::decodeResponse(netsnmp_pdu * resp, 
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

  axSnmpCmtsChannelOids_s * initialOids = 
                        (axSnmpCmtsChannelOids_s *) getInitialOids();

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
axHighRateCmtsChannelTblWalk::useResponse(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  char oidBuf[128];
  synchronizedTable_t * channelTblStruct = m_intCmts->getChannelTable();

  axList newChannelList;
  axList dbAddedChannelList;

  {
    axReadLockOperator cmTblLock(channelTblStruct->lock);

    axAvlTree * channelTbl = dynamic_cast<axAvlTree *> 
                                           (channelTblStruct->table);

    axInternalChannel * actualIntChannel;

    for (int i=0; i<m_decodedResponse.numValues; i++) {

      axSnmpCmtsChannelResultRow_s * r = &m_decodedResponse.values[i];

      /*
       * Filter uninterested interfaces
       */

      if (r->ifType != IF_TYPE_DOWN_STREAM &&
          r->ifType != IF_TYPE_UP_STREAM_1X &&
          r->ifType != IF_TYPE_UP_STREAM_2X) {
        continue;
      }

      if (r->ifType == IF_TYPE_UP_STREAM_2X &&
          !m_intCmts->isDocsis20Capable()) {
        continue;
      }

      if (r->ifType == IF_TYPE_UP_STREAM_1X &&
          m_intCmts->isDocsis20Capable()) {

        /*
         * Need to check ifStackTable lower layer
         */

        axSnmpGenericGetNext ifStackLowerLayer;
        axAbstractSnmpSession * snmpSession = 
                                          cbReqObj->getSnmpSession();
        ifStackLowerLayer.setCallback(snmpSession);
        axSnmpAsyncCbReqObj * ifCbReqObj = new
                  axSnmpAsyncCbReqObj(m_intCmts, &ifStackLowerLayer);
        ifCbReqObj->setSnmpSession(snmpSession);
        sprintf(oidBuf, "%s%d", SNMP_IFSTACK_LOWER_LAYER_BASE_OID, 
                                                         r->ifIndex);
        ifStackLowerLayer.setOid(oidBuf);
        if (ifStackLowerLayer.run()) {
          axSnmpGenericGetResultValue_s * resp = 
                                    (axSnmpGenericGetResultValue_s *)
                              ifStackLowerLayer.getDecodedResponse();
          if (!resp->values[0].data.intVal) {
            continue;
          }
        }
      }

      axInternalChannel tmpIntChannel(r->ifDescription);
      actualIntChannel = dynamic_cast<axInternalChannel *> 
                                 (channelTbl->find(&tmpIntChannel));

      if (!actualIntChannel) {

        // handle new cm
        axInternalChannel * newChannel = new axInternalChannel(r);
        newChannelList.add(newChannel);

      } else {

        // do a diff/cmp of attr values and take necessary actions
        axInternalChannel channel(r);
        // actualIntChannel->compareAndUpdate(&channel, m_intCmts->getResId());
        axWriteLockOperator cWlock(actualIntChannel->getLock());
        compareAndUpdate(actualIntChannel, &channel);

      }

    } // for()

  }

  /*
   * add channel to db
   */
  if (newChannelList.size()) {

    axInternalChannel * channel;

    struct timeval tm;
    gettimeofday(&tm, NULL);

    for (channel = (axInternalChannel *) newChannelList.remove(); channel;
         channel = (axInternalChannel *) newChannelList.remove()) {

      if (channel->isAddable()) {

        axDbChannel dbChannel(channel, m_intCmts->getResId());
        if (dbChannel.insertRow()) {
          /*
           * No need to lock here since the cm is not yet added to the
           * global tree
           */
          axIntChannelNonkey_t * chNk = 
                            (axIntChannelNonkey_t *) channel->getNonKey();
          chNk->resId = dbChannel.m_channelResId;
          dbAddedChannelList.add(channel);

          axDbChannelCurrentCounts counts(chNk->resId);
          counts.m_timeSec = tm.tv_sec;
          counts.insertRow();

        } else {
          delete channel;
        }

      } else {
        delete channel;
      }

    } // for

  }

  /*
   * add channel to internal tree
   */
  if (dbAddedChannelList.size()) {

    axWriteLockOperator channelTblLock(channelTblStruct->lock);

    axAvlTree * channelTbl = dynamic_cast<axAvlTree *> (channelTblStruct->table);

    axInternalChannel * channel;

    for (channel = (axInternalChannel *) dbAddedChannelList.remove(); channel;
         channel = (axInternalChannel *) dbAddedChannelList.remove()) {

      if (!channelTbl->add(channel)) {
        delete channel;
      }

    } // for

  }


}


#if 0
//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axHighRateCmtsChannelTblWalk::getNextOids(void)
{
  return (&m_nextOids);
}
#endif


//********************************************************************
// method:
//********************************************************************
void
axHighRateCmtsChannelTblWalk::getValue(AX_INT32 rowNum, 
                         AX_INT32 attrId, netsnmp_variable_list * var)
{
  switch (attrId) {
    case 0: /* ifIndex */
      m_decodedResponse.values[rowNum].ifIndex = *(var->val.integer);
      break;

    case 1: /* ifDescription */
      snmpIfDescriptionToString(
                 m_decodedResponse.values[rowNum].ifDescription, var);
      break;

    case 2: /* ifType */
      m_decodedResponse.values[rowNum].ifType = *(var->val.integer);
      break;

    default:
      break;
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axHighRateCmtsChannelTblWalk::compareAndUpdate(
                  axInternalChannel * origC, axInternalChannel * newC)
{
  bool ret = false;
  bool updateRequired = false;

  axIntChannelNonkey_t * newNk = (axIntChannelNonkey_t *) 
                                                    newC->getNonKey();

  axIntChannelNonkey_t * origNk = (axIntChannelNonkey_t *) 
                                                   origC->getNonKey();

  if (!origC->hasData()) {

#if 0 // NOTE: Dont' Remove

    // new modem; or re-activating a previously deleted channel

    origC->allocateData();
    origNk = (axIntChannelNonkey_t *) origC->getNonKey();
    origNk->channelIndex = newNk->channelIndex;
    origNk-->channelType = newNk->channelType;

    axDbChannel dbChannel(ocmKey->name, cmtsResId);
    if (!dbChannel.insertRow()) {
      origC->deallocateData();
      goto EXIT_LABEL;
    }

    if (dbChannel.getRow()) {
      origNk->resId = dbChannel.m_channelResId;
    }

    ret = true;

#endif

  } else {

    // existing channel

    if (newNk->channelIndex != origNk->channelIndex) {
      // origNk->channelIndex = newNk->channelIndex;
      updateRequired = true;
    }

    if (newNk->channelType != origNk->channelType) {
      // origNk->channelType = newNk->channelType;
      updateRequired = true;
    }

    if (updateRequired) {

      axDbChannel dbChannel(origC->getResId());

      if (!dbChannel.getRow()) {
        goto EXIT_LABEL;
      }

      dbChannel.m_channelIndex = newNk->channelIndex;
      dbChannel.m_channelType = newNk->channelType;

      /*
       * Update DB row
       */
      ret = dbChannel.updateRow();

      if (ret) {
        origNk->channelIndex = newNk->channelIndex;
        origNk->channelType = newNk->channelType;
      }

    }

  }


EXIT_LABEL:

  return (ret);
}



