
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSnmpGenericGetNext.hpp"
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
axSnmpGenericGetNext::axSnmpGenericGetNext()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpGenericGetNext::~axSnmpGenericGetNext()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSnmpGenericGetNext::axSnmpGenericGetNext(axSnmpSession * s) :
//   axAbstractSnmpSingleGetWork(s)
// {
// }


//********************************************************************
// method:
//********************************************************************
axSnmpValueBase_s *
axSnmpGenericGetNext::getDecodedResponse(void)
{
  return (&m_decodedResponse);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpGenericGetNext::setOid(AX_INT8 * sOid)
{
  if (snmp_parse_oid(sOid,
       m_oid.oids[0].nativeOid, &m_oid.oids[0].nativeOidLen)) {
  } else {
    snmp_perror(sOid);
  }
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axSnmpGenericGetNext::getInitialOids(void)
{
  return (&m_oid);
}


#if 0
//********************************************************************
// method:
//********************************************************************
netsnmp_pdu *
axSnmpGenericGetNext::createRequestPdu(void)
{
  return (snmp_pdu_create(SNMP_MSG_GETNEXT));
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpGenericGetNext::addOids(netsnmp_pdu * req, axSnmpOidsBase_s * o)
{
  axSnmpGenericGetNextOid_s * oids = (axSnmpGenericGetNextOid_s *) o;
  for (int i=0; i<oids->numOids; i++) {
    snmp_add_null_var(req, oids->oids[i].nativeOid, 
                                          oids->oids[i].nativeOidLen);
  }
}
#endif


//********************************************************************
// method:
//********************************************************************
bool
axSnmpGenericGetNext::createAndSendInitialRequests(void)
{
  bool ret = true;

  axAbstractIterator * iter;
  axSnmpAsyncCbReqObj * cbRObj;

  axSnmpGenericGetNextOid_s * oids = (axSnmpGenericGetNextOid_s *)
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
      ACE_DEBUG((LM_SNMP_DEBUG, "SnmpGenGetNext: sent req\n"));
    } else {
      snmp_free_pdu(req);
      cbRObj->setStateSendReqFailed();
      ACE_DEBUG((LM_SNMP_DEBUG, "SnmpGenGetNext: unable to send req\n"));
    }

  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpGenericGetNext::createAndSendNextRequests(
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
axSnmpGenericGetNext::decodeResponse(netsnmp_pdu * resp,
                                   axSnmpRespDecodeRC_s & returnCodes)
{
  bool isFailed = false;
  netsnmp_variable_list * var;

  // initialize
  m_decodedResponse.numValues = 0;
  returnCodes.init();

  var = resp->variables;

  if ( var->name_length < m_oid.oids[0].nativeOidLen ||
       memcmp(m_oid.oids[0].nativeOid, var->name, 
              m_oid.oids[0].nativeOidLen * sizeof(oid)) ) {
    isFailed = true;
    goto EXIT_LABEL;
  }

  if ((var->type != SNMP_ENDOFMIBVIEW) &&
      (var->type != SNMP_NOSUCHOBJECT) &&
      (var->type != SNMP_NOSUCHINSTANCE)) {

    /* verify OID's are increasing */
    if (snmp_oid_compare(m_oid.oids[0].nativeOid, m_oid.oids[0].nativeOidLen, 
                         var->name, var->name_length) >= 0) {
      isFailed = true;
      goto EXIT_LABEL;
    }

  } else {
    isFailed = true;
    goto EXIT_LABEL;
  }

  getValue(var);

EXIT_LABEL:

  if (!isFailed) {
    m_decodedResponse.numValues = 1;
    returnCodes.hasData = true;
  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpGenericGetNext::useResponse(axSnmpAsyncCbReqObj * o)
{
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpGenericGetNext::getValue(netsnmp_variable_list * var)
{
  int len;

  switch (var->type) {
    case ASN_INTEGER:
    case ASN_BOOLEAN:
      m_decodedResponse.values[0].data.intVal = *(var->val.integer);
      m_decodedResponse.values[0].dataType = var->type;
      break;

    case ASN_OCTET_STR:
      len = var->name_length % 4;
      m_decodedResponse.values[0].data.strPtr = (AX_INT8 *) 
                                   malloc(var->name_length + (4-len));
      memcpy(m_decodedResponse.values[0].data.strPtr, 
                                   var->val.string, var->name_length);
      m_decodedResponse.values[0].data.strPtr[var->name_length] = 
                                                                 '\0';
      m_decodedResponse.values[0].dataType = var->type;
      break;

    default:
      break;
  }
}


