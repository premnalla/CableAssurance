
//********************************************************************
// OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpCmtsCmStatusTblWalk.hpp"
#include "axSnmpCmtsCmStatusReqType.hpp"
#include "axSnmpDataTypes.hpp"
#include "axSnmpSessionFactory.hpp"
// #include "axSnmpV2cSession.hpp"
// #include "axSnmpV2cSessionHelper.hpp"
#include "axSnmpV3SessionHelper.hpp"
#include "axSnmpUtils.hpp"

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
axSnmpCmtsCmStatusTblWalk::axSnmpCmtsCmStatusTblWalk() :
  m_snmpSession(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpCmtsCmStatusTblWalk::~axSnmpCmtsCmStatusTblWalk()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axSnmpCmtsCmStatusTblWalk::axSnmpCmtsCmStatusTblWalk(axSnmpSession * s) :
  m_snmpSession(s)
{
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpCmtsCmStatusTblWalk::run(void)
{
  netsnmp_pdu           * pdu;
  netsnmp_pdu           * response;
  axSnmpCmtsCmOids_s        oidsForNextGet;
  // netsnmp_variable_list * vars;

  // init();
  axSnmpCmtsCmStatusReqType * reqType = axSnmpCmtsCmStatusReqType::getInstance();
  reqType->getOids(m_initialCmtsOids);
  reqType->getOids(oidsForNextGet);

  // get 'A' session from the factory
  // axSnmpV2cSessionHelper sh(axSnmpSessionFactory::getInstance());
  // axSnmpV2cSession * s = dynamic_cast<axSnmpV2cSession *> (sh.getSession());
  // assert (s != NULL);
  // s->setSessionPeer("foo");
  netsnmp_session * intS = m_snmpSession->getInternalSession();

  // netsnmp_session * retS = snmp_sess_open(intS);
  // if (!retS) {
  //   goto EXIT_LABEL;
  // }

  bool done = false;

  while (!done) {

    pdu = snmp_pdu_create(SNMP_MSG_GETBULK);
    pdu->non_repeaters = reqType->getNonRepeters();
    pdu->max_repetitions = reqType->getMaxRepetitions();

    for (int i=0; i<oidsForNextGet.numOids; i++) {
      snmp_add_null_var(pdu, oidsForNextGet.oids[i].nativeOid, 
                        oidsForNextGet.oids[i].nativeOidLen);
    }

    // send request and get response

    // decode raw response

    // update internal structures

    if (!sendRequestAndProcessResponse(intS, pdu, &response, oidsForNextGet)) {
      done = true;
    }

  } // while


// EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpCmtsCmStatusTblWalk::init(void)
{
  axSnmpCmtsCmStatusReqType * ins = axSnmpCmtsCmStatusReqType::getInstance();
  ins->getOids(m_initialCmtsOids);
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpCmtsCmStatusTblWalk::createPdu(void)
{
  bool ret = true;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpCmtsCmStatusTblWalk::addOidsToPdu(void)
{
  bool ret = true;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpCmtsCmStatusTblWalk::sendRequestAndProcessResponse(
  netsnmp_session * sess, netsnmp_pdu * req, netsnmp_pdu ** outResp,
  axSnmpCmtsCmOids_s & oidsForNextGet)
{
  bool ret = false;
  bool done;
  netsnmp_pdu * resp = NULL;
  netsnmp_variable_list * vars;
  size_t baseLen = 0;
  int  numVarsInResult = 0;
  int  numBatchesInResult = 0;
  axSnmpCmtsCmResultValues_s myResults;
  int  i, j;

  int status = snmp_synch_response(sess, req, &resp);
  if (status == STAT_TIMEOUT) {
    // process timeout

    goto EXIT_LABEL;
  } else if (status != STAT_SUCCESS) {
    goto EXIT_LABEL;
  }

  if (resp->errstat != SNMP_ERR_NOERROR) {
    goto EXIT_LABEL;
  }

  /* 
   * Assumes we are getting 'n' vars from the same table, meaning
   * all 'n' vars have the same base length. The (-1) is to get oid
   * from the start up to and NOT including the column in the table.
   */
  baseLen = m_initialCmtsOids.oids[0].nativeOidLen - 1;

  for (vars = resp->variables; vars; vars = vars->next_variable) {
    ++numVarsInResult;
  }

  numBatchesInResult = numVarsInResult / m_initialCmtsOids.numOids;

  if ( !numBatchesInResult ) {
    goto EXIT_LABEL;
  }

  if (numBatchesInResult > myResults.maxValues) {
    numBatchesInResult = myResults.maxValues;
  }

  vars = resp->variables;
  done = false;
  for (i=0; i<numBatchesInResult && !done; i++) {

    for (j=0; j<m_initialCmtsOids.numOids && !done; 
         j++, vars=vars->next_variable) {
      axSnmpNativeOid_s * p = &m_initialCmtsOids.oids[j];

      /* check to verify that var is from the same tree */
      if ( (vars->name_length < baseLen) ||
           memcmp(p->nativeOid, vars->name, (baseLen * sizeof(oid))) ) 
      {
        done = true;
        continue;
      }

      if ((vars->type != SNMP_ENDOFMIBVIEW) &&
          (vars->type != SNMP_NOSUCHOBJECT) &&
          (vars->type != SNMP_NOSUCHINSTANCE)) {

        /* verify OID's are increasing */
        if (snmp_oid_compare(p->nativeOid, p->nativeOidLen, vars->name,
                             vars->name_length) >= 0) {
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
      getValue(i, j, myResults, vars);

      /*
       * IF Last Set in the Batch, store for Next Get
       */
      if (i == (numBatchesInResult-1)) {
        oidsForNextGet.oids[j].nativeOidLen = vars->name_length;
        memcpy(oidsForNextGet.oids[j].nativeOid, vars->name, 
               (vars->name_length * sizeof(oid)));
      }

    } // inner for; foreach var in a batch

  } // outer for; foreach batch

  if (!done) {
    myResults.numValues = i;
  } else {
    if (i>0) {
      myResults.numValues = i-1;
    }
  }

  if (myResults.numValues) {
    /*
     * Update Internal Structures
     */
  }

  if (myResults.numValues != myResults.maxValues) {
    /*
     * Done getting
     */
    goto EXIT_LABEL;
  }

// EXIT_OK:

  ret = true;

EXIT_LABEL:

  if (resp) {
    *outResp = resp; 
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpCmtsCmStatusTblWalk::getValue(AX_INT32 rowNum, AX_INT32 attrId, 
  axSnmpCmtsCmResultValues_s & myRes, netsnmp_variable_list * vars)
{
  switch (attrId) {
    case 0: /* mac */
      snmpMacToString(myRes.values[rowNum].mac, vars);;
      break;

    case 1: /* ip addr */
      snmpIPv4ToString(myRes.values[rowNum].ipAddr, vars);;
      break;

    case 2: /* downstream channel ifIndex */
      myRes.values[rowNum].downChannelIndex = *(vars->val.integer);
      break;

    case 3: /* upstream channel ifIndex */
      myRes.values[rowNum].upChannelIndex = *(vars->val.integer);
      break;

    case 4: /* cm status */
      myRes.values[rowNum].cmStatus = *(vars->val.integer);
      break;

    default:
      break;
  }
}


