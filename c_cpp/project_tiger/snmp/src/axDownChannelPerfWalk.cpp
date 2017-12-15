
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDownChannelPerfWalk.hpp"
#include "axSnmpUtils.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalChannel.hpp"
#include "axAvlTree.hpp"
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
axDownChannelPerfWalk::axDownChannelPerfWalk() : 
  m_reqType(NULL), m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDownChannelPerfWalk::~axDownChannelPerfWalk()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDownChannelPerfWalk::axDownChannelPerfWalk(
                                           axInternalCmts * intCmts) :
  m_intCmts(intCmts)
{
  m_reqType = axSnmpDownChannelPerfReqType::getInstance();  
  m_nextOids.numOids = m_reqType->getOids()->numOids;
}


#if 0
//********************************************************************
// method:
//********************************************************************
axSnmpValueBase_s *
axDownChannelPerfWalk::getDecodedResponse(void)
{
  return (&m_decodedResponse);
}
#endif


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axDownChannelPerfWalk::getInitialOids(void)
{
  return (m_reqType->getOids());
}


#if 0
//********************************************************************
// method:
//********************************************************************
netsnmp_pdu *
axDownChannelPerfWalk::createRequestPdu(void)
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
axDownChannelPerfWalk::addOids(netsnmp_pdu * req, axSnmpOidsBase_s * o)
{
  axSnmpDownChannelPerfOid_s * oids = (axSnmpDownChannelPerfOid_s *) o;
  for (int i=0; i<oids->numOids; i++) {
    snmp_add_null_var(req, oids->oids[i].nativeOid, oids->oids[i].nativeOidLen);
  }
}
#endif


//********************************************************************
// method:
//********************************************************************
bool
axDownChannelPerfWalk::createAndSendInitialRequests(void)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpDownChannelPerfOid_s * oids = (axSnmpDownChannelPerfOid_s *)
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
      ACE_DEBUG((LM_SNMP_DEBUG, "unable to send initial req\n"));
    }

  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDownChannelPerfWalk::createAndSendNextRequests(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpDownChannelPerfOid_s * oids = (axSnmpDownChannelPerfOid_s *)
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
axDownChannelPerfWalk::decodeResponse(netsnmp_pdu * resp, 
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

  axSnmpDownChannelPerfOid_s * initialOids = 
                 (axSnmpDownChannelPerfOid_s *) m_reqType->getOids();

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
axDownChannelPerfWalk::useResponse(axSnmpAsyncCbReqObj * cbReqObj)
{
  // char oidBuf[128];
  synchronizedTable_t * channelTblStruct = m_intCmts->getChannelTable();

  axReadLockOperator cmTblLock(channelTblStruct->lock);

  // axAvlTree * channelTbl = dynamic_cast<axAvlTree *> (channelTblStruct->table);

  axInternalChannel * actualIntChannel;

  for (int i=0; i<m_decodedResponse.numValues; i++) {

    // axSnmpDownChannelPerfResultRow_s * r = &m_decodedResponse.values[i];

    /*
     * Locate Channel
     */
    actualIntChannel = NULL;

    if (!actualIntChannel) {

      // handle new cm
      // axInternalChannel * newChannel = new axInternalChannel(r);
      // newChannelList.add(newChannel);

    } else {

      // do a diff/cmp of attr values and take necessary actions
      // axInternalChannel channel(r);
      // actualIntChannel->compareAndUpdate(&channel, m_intCmts->getResId());

    }

  } // for()

}


#if 0
//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axDownChannelPerfWalk::getNextOids(void)
{
  return (&m_nextOids);
}
#endif


//********************************************************************
// method:
//********************************************************************
void
axDownChannelPerfWalk::getValue(AX_INT32 rowNum, AX_INT32 attrId,
  netsnmp_variable_list * var)
{
  // ifIndex is part of OID
  m_decodedResponse.values[rowNum].ifIndex = var->name[var->name_length-1];

  switch (attrId) {
    case 0: /* downChannelPower */
      m_decodedResponse.values[rowNum].downstreamPower = *(var->val.integer);
      break;

    default:
      break;
  }
}


