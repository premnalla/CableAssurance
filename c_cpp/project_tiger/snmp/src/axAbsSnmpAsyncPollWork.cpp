
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axAbsSnmpAsyncPollWork.hpp"
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
axAbsSnmpAsyncPollWork::axAbsSnmpAsyncPollWork() :
  m_doneGetting(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbsSnmpAsyncPollWork::~axAbsSnmpAsyncPollWork()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbsSnmpAsyncPollWork::axAbsSnmpAsyncPollWork()
// {
// }

//********************************************************************
// method:
//********************************************************************
void
axAbsSnmpAsyncPollWork::setCallback(axAbstractSnmpSession * snmpSession)
{
  snmpSession->getInternalSession()->callback = 
                              axAbsSnmpAsyncPollWork::CallbackHandler;
}


//********************************************************************
// method:
//********************************************************************
void
axAbsSnmpAsyncPollWork::addCallBackReqObj(axSnmpAsyncCbReqObj * o)
{
  m_cbReqObjList.add(o);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbsSnmpAsyncPollWork::isDoneGetting(void)
{
  return (m_doneGetting);
}


//********************************************************************
// method:
//********************************************************************
axListBase *
axAbsSnmpAsyncPollWork::getCbReqObjList(void)
{
  return (&m_cbReqObjList);
}


//********************************************************************
// method:
//********************************************************************
void
axAbsSnmpAsyncPollWork::setDoneGetting(void)
{
  m_doneGetting = true;
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbsSnmpAsyncPollWork::CallbackHandler(AX_INT32          op,
                                        netsnmp_session * intSession,
                                        AX_INT32          reqId,
                                        netsnmp_pdu     * resp,
                                        void            * arg)
{
  AX_INT32 ret = 0;

  axSnmpAsyncCbReqObj * cbObj = (axSnmpAsyncCbReqObj *) arg;
  axAbsSnmpAsyncPollWork * pClass = cbObj->getProcessingClass();

  if (op != NETSNMP_CALLBACK_OP_RECEIVED_MESSAGE) {
    /* timeout */
    cbObj->setStateTimeout();
    goto EXIT_LABEL;
  }

  cbObj->setStateReplyReceived();

  /*
   * This call will set if there are further requests to be sent or
   * whether we are done
   */
  pClass->decodeAndUseResponse(resp, cbObj);

  pClass->createAndSendNextRequests(cbObj);

EXIT_LABEL:

  pClass->freeResponse(&resp);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbsSnmpAsyncPollWork::run(void)
{
  bool ret = true;

  /*
   * Pure virtual function:
   */
  createAndSendInitialRequests();

  struct timeval timeoutTm;
  struct timeval netsnmpTm;
  memset(&timeoutTm, 0, sizeof(timeoutTm));
  timeoutTm.tv_sec = 1;
  bool moreFds = true;

  /*
   * Wait for replies
   * Pure virtual function:
   */
  while (!isDoneGetting() && moreFds) {

    int numFds = 0;
    int netsnmpBlock = 0;
    int block = 0;

    fd_set readFdSet;
    FD_ZERO(&readFdSet);

    /*
     * Pure virtual function:
     */
    setFdSet(&numFds, &readFdSet, &netsnmpTm, &block);

    if (numFds) {

      numFds = select(numFds, &readFdSet, NULL, NULL, &netsnmpTm);

      if (numFds < 0) {

        if (errno == EINTR) {
          continue;
        } else {
          setDoneGetting();
        }

      } else if (numFds) {

        /*
         * The callback will be invoked here by snmp_read()
         */
        snmpRead(&readFdSet);

      } else {

        /*
         * Pure virtual function:
         */
        handleTimeout();

      }

    } else {
      moreFds = false;
    }

  } // while

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axAbsSnmpAsyncPollWork::decodeAndUseResponse(netsnmp_pdu * resp,
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  decodeResponse(resp, m_responseCode);
  if (m_responseCode.hasData) {
    useResponse(cbReqObj);
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axAbsSnmpAsyncPollWork::setFdSet(AX_INT32 * numFds,
         fd_set * readFdSet, struct timeval * tmOut, AX_INT32 * block)
{
  bool ret = true;

  axSnmpAsyncCbReqObj * reqObj;
  axAbstractIterator * iter;

  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  int maxFdNum = 0;

  for (reqObj=dynamic_cast<axSnmpAsyncCbReqObj *>(iter->getFirst());
       reqObj;
       reqObj=dynamic_cast<axSnmpAsyncCbReqObj *>(iter->getNext())) {

    int tmpFdNum = 0;

    axAbstractSnmpSession * snmpSession = reqObj->getSnmpSession();

    if (reqObj->isReqSent()) {
      snmp_sess_select_info(snmpSession->getInternalSessionList(),
                                  &tmpFdNum, readFdSet, tmOut, block);
      if (tmpFdNum > maxFdNum) {
        maxFdNum = tmpFdNum;
      }
    }

  }

  *numFds = maxFdNum;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbsSnmpAsyncPollWork::handleTimeout(void)
{
  bool ret = true;

  axSnmpAsyncCbReqObj * reqObj;
  axAbstractIterator * iter;
  bool atleastOne = false;

  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  for (reqObj=dynamic_cast<axSnmpAsyncCbReqObj *>(iter->getFirst());
       reqObj;
       reqObj=dynamic_cast<axSnmpAsyncCbReqObj *>(iter->getNext())) {

    axAbstractSnmpSession * snmpSession = reqObj->getSnmpSession();

    if (reqObj->isReqSent()) {
      atleastOne = true;
      snmp_sess_timeout(snmpSession->getInternalSessionList());
    }
  }

  if (!atleastOne) {
    setDoneGetting();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbsSnmpAsyncPollWork::snmpRead(fd_set * readFdSet)
{
  bool ret = true;

  axSnmpAsyncCbReqObj * reqObj;
  axAbstractIterator * iter;

  axIteratorHolder iH(iter=getCbReqObjList()->createIterator());

  for (reqObj=dynamic_cast<axSnmpAsyncCbReqObj *>(iter->getFirst());
       reqObj;
       reqObj=dynamic_cast<axSnmpAsyncCbReqObj *>(iter->getNext())) {

    axAbstractSnmpSession * snmpSession = reqObj->getSnmpSession();

    if (reqObj->isReqSent()) {
      snmp_sess_read(snmpSession->getInternalSessionList(), readFdSet);
    }
  }

  return (ret);
}


