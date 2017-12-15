
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axAbstractCPeerServices.hpp"
#include "axMyGsoapConstants.hpp"
#include "axDbEmta.hpp"
#include "axDbCm.hpp"
#include "axPinger.hpp"
#include "axIcmpSocketHolder.hpp"
#include "axIcmpSocketFactory.hpp"
#include "axIcmpCASocket.hpp"
#include "axIcmpTemplateSocket.hpp"
#include "axSocketUtils.hpp"
#include "axSnmpUtils.hpp"
#include "axInternalGenMta.hpp"
#include "axInternalGenMta.hpp"
#include "axInternalEmta.hpp"
#include "axCsrMtaPollWork.hpp"
#include "axCsrCmPoll.hpp"
#include "axCsrCmtsCmPoll.hpp"
#include "axGlobalData.hpp"
#include "axSnmpTemplateSession.hpp"
#include "axAbsDbSnmpVerAttrs.hpp"
#include "axSnmpVerSpecificAttrSetter.hpp"
#include "axSnmpSession.hpp"
#include "axReadLockOperator.hpp"
#include "axSnmpSessionFactory.hpp"
#include "axDbSnmpAttrHolder.hpp"

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
axAbstractCPeerServices::axAbstractCPeerServices()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCPeerServices::~axAbstractCPeerServices()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractCPeerServices::axAbstractCPeerServices()
// {
// }


//********************************************************************
// method:
//********************************************************************
int
axAbstractCPeerServices::pingMta(ns1__TopoHierarchyKeyT *topologyKey,
                            std::string mtaResId, std::string &result)
{
  int ret = MY_SOAP_ERROR;

  // result = mtaResId;
  // result.append(" is alive");

  axIcmpCASocket * sock;
  axIcmpTemplateSocket sockTemplate;
  axDbEmta dbMta;

  axIcmpSocketFactory * sFact = axIcmpSocketFactory::getInstance();

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  dbMta.m_mtaResId = strtoul(mtaResId.c_str(), NULL, 10);
  if (!dbMta.getRow()) {
    goto EXIT_LABEL;
  }

  sockTemplate.setDomain(dbMta.m_ipAddressType);

  {
    axIcmpSocketHolder sH(sock=sFact->createSocket(&sockTemplate));
    if (!sock) {
      goto EXIT_LABEL;
    }

    {
      axPinger pinger(sock);
      pinger.setIpAddress(dbMta.m_ipAddressType, dbMta.m_ipAddress);
      AX_INT8 pingRc = pinger.ping();
      if (axInternalGenMta::isPingStatePass(pingRc)) {
        result = MY_PING_PASS;
      } else if (!axInternalGenMta::isPingStateFailure(pingRc)) {
        result = MY_PING_UNK;
      } else {
        result = MY_PING_FAIL;
      }
    }
  }

  ret = SOAP_OK;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int 
axAbstractCPeerServices::getMtaData(ns1__TopoHierarchyKeyT *topologyKey,
     std::string mtaResId, struct cpeer__getMtaDataResponse &_param_1)
{
  static const char * myName="abs.getMtaData:";

  int ret = MY_SOAP_ERROR;

  AX_INT8                 ipAddress[128];
  axDbEmta                dbMta;
  axDbCmts                dbCmts;
  axInternalCmts        * intCmts;
  axInternalGenMta      * intMta;
  axSnmpTemplateSession   sessionTmpl;
  axAbsDbSnmpVerAttrs   * dbSnmpVerAttrs;
  axSnmpSession         * snmpSession;
  axSnmpAsyncCbReqObj   * cbReqObj;

  axSnmpSessionFactory * sFact = axSnmpSessionFactory::getInstance();

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  dbMta.m_mtaResId = strtoul(mtaResId.c_str(), NULL, 10);
  if (!dbMta.getRow()) {
    goto EXIT_LABEL;
  }

  dbCmts.m_cmtsResId = dbMta.m_cm.m_cmtsResId;
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  {
    axIntCmtsKey_t cmtsKey(dbMta.m_cm.m_cmtsResId);
    intCmts = axGlobalData::getInstance()->findCmts(cmtsKey);
    if (!intCmts) {
      goto EXIT_LABEL;
    }
  }

  {
    axDbSnmpAttrHolder iH(dbCmts.getCmSnmpVersionAttrs());
    if ( !(dbSnmpVerAttrs=iH.getAttrs()) ) {
      goto EXIT_LABEL;
    }

    {
      axCsrMtaPollWork mtaPoll(intCmts);
      intMta = dynamic_cast<axInternalGenMta *> 
                                   (intCmts->findMta(dbMta.m_mtaMac));
      if (!intMta) {
        goto EXIT_LABEL;
      }

      mtaPoll.setCallback(&sessionTmpl);

      {
        axSnmpVerSpecificAttrSetter attrSetter(&sessionTmpl,
                                                      dbSnmpVerAttrs);
        attrSetter.setAttributes();

        /*
         * Don't poll offline eMTA's
         */
        if (intMta->isEmta()) {
          axIntEmtaNonkey_t * eNk = (axIntEmtaNonkey_t *)
                                                  intMta->getNonKey();
          axInternalCm * intCm = eNk->cmPtr;
          axReadLockOperator cmRlock(intCm->getLock());
          axIntCmNonkey_t * cmNk = (axIntCmNonkey_t *)
                                                   intCm->getNonKey();
          if (!intCm->hasData() ||
              !axInternalCm::isCmOnline(cmNk->docsisState)) {
            goto EXIT_LABEL;
          }
        }

        getNetSnmpIpAddress(ipAddress, dbMta.m_ipAddress, 
                                               dbMta.m_ipAddressType);
        attrSetter.setIpAddress(ipAddress);

        snmpSession = sFact->createSession(&sessionTmpl);
        if (!snmpSession) {
          goto EXIT_LABEL;
        }

        cbReqObj = new axSnmpAsyncCbReqObj(intMta, &mtaPoll);

        cbReqObj->setSnmpSession(snmpSession);

        ACE_DEBUG((LM_MISC_DEBUG, "%s run begin.\n", myName));

        mtaPoll.run();

        AX_UINT8 provStatus = mtaPoll.getProvStatus();
        if (axInternalGenMta::isProvStatePass(provStatus)) {
          _param_1.result->provStatus = "Pass";
          _param_1.result->provCounter = "N/A";
        } else if (!provStatus) {
          _param_1.result->provStatus = "Fail";
          _param_1.result->provCounter = "N/A";
        } else {
          _param_1.result->provStatus = "Timed-out";
          _param_1.result->provCounter = "N/A";
        }

        ACE_DEBUG((LM_MISC_DEBUG, "%s run end.\n", myName));

      }

    }

  }

  ret = SOAP_OK;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int 
axAbstractCPeerServices::getCmData(
                                  ns1__TopoHierarchyKeyT *topologyKey,
       std::string cmResId, struct cpeer__getCmDataResponse &_param_2)
{
  static const char * myName="abs.getCmData:";

  int ret = MY_SOAP_ERROR;

  AX_INT8                 ipAddress[128];
  AX_INT8                 buf[16];
  axDbCm                  dbCm;
  axDbCmts                dbCmts;
  axInternalCmts        * intCmts;
  axInternalCm          * intCm;
  axSnmpTemplateSession   sessionTmpl;
  axAbsDbSnmpVerAttrs   * dbSnmpVerAttrs;
  axSnmpSession         * snmpSession;
  axSnmpAsyncCbReqObj   * cbReqObj;

  axSnmpSessionFactory * sFact = axSnmpSessionFactory::getInstance();

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  dbCm.m_cmResId = strtoul(cmResId.c_str(), NULL, 10);
  if (!dbCm.getRow()) {
    goto EXIT_LABEL;
  }

  dbCmts.m_cmtsResId = dbCm.m_cmtsResId;
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  {
    axIntCmtsKey_t cmtsKey(dbCm.m_cmtsResId);
    intCmts = axGlobalData::getInstance()->findCmts(cmtsKey);
    if (!intCmts) {
      goto EXIT_LABEL;
    }
  }

  {
    axDbSnmpAttrHolder iH(dbCmts.getCmSnmpVersionAttrs());
    if ( !(dbSnmpVerAttrs=iH.getAttrs()) ) {
      goto EXIT_LABEL;
    }

    {
      axCsrCmPoll cmPoll(intCmts);
      intCm = dynamic_cast<axInternalCm *>
                                     (intCmts->findMta(dbCm.m_cmMac));
      if (!intCm) {
        goto EXIT_LABEL;
      }

      cmPoll.setCallback(&sessionTmpl);

      {
        axSnmpVerSpecificAttrSetter attrSetter(&sessionTmpl,
                                                      dbSnmpVerAttrs);
        attrSetter.setAttributes();

        /*
         * Don't poll if offline
         */
        {
          axReadLockOperator cmRlock(intCm->getLock());
          axIntCmNonkey_t * cmNk = (axIntCmNonkey_t *)
                                                   intCm->getNonKey();
          if (!intCm->hasData() ||
              !axInternalCm::isCmOnline(cmNk->docsisState)) {
            goto EXIT_LABEL;
          }
        }

        getNetSnmpIpAddress(ipAddress, dbCm.m_ipAddress,
                                                dbCm.m_ipAddressType);
        attrSetter.setIpAddress(ipAddress);

        snmpSession = sFact->createSession(&sessionTmpl);
        if (!snmpSession) {
          goto EXIT_LABEL;
        }

        cbReqObj = new axSnmpAsyncCbReqObj(intCm, &cmPoll);

        cbReqObj->setSnmpSession(snmpSession);

        ACE_DEBUG((LM_MISC_DEBUG, "%s run begin.\n", myName));

        cmPoll.run();

        _param_2.result->mac = dbCm.m_cmMac;
        _param_2.result->host = dbCm.m_ipAddress;
        _param_2.result->cmIndex = dbCm.m_modemIndex;

        sprintf(buf, "%d", cmPoll.getDownstreamSNR());
        _param_2.result->downstreamSNR = buf;

        sprintf(buf, "%d", cmPoll.getDownstreamPower());
        _param_2.result->downstreamPower = buf;

        sprintf(buf, "%d", cmPoll.getUpstreamPower());
        _param_2.result->upstreamPower = buf;

        sprintf(buf, "%d", cmPoll.getT1Timeouts());
        _param_2.result->t1Count = buf;

        sprintf(buf, "%d", cmPoll.getT2Timeouts());
        _param_2.result->t2Count = buf;

        sprintf(buf, "%d", cmPoll.getT3Timeouts());
        _param_2.result->t3Count = buf;

        sprintf(buf, "%d", cmPoll.getT4Timeouts());
        _param_2.result->t4Count = buf;

        ACE_DEBUG((LM_MISC_DEBUG, "%s run end.\n", myName));

      }

    }

  }

  ret = SOAP_OK;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int 
axAbstractCPeerServices::getCmtsCmData(
                                  ns1__TopoHierarchyKeyT *topologyKey,
                           std::string cmtsResId, std::string cmResId,
                        struct cpeer__getCmtsCmDataResponse &_param_3)
{
  static const char * myName="abs.getCmtsCmData:";

  int ret = MY_SOAP_ERROR;

  AX_INT8                 ipAddress[128];
  AX_INT8                 buf[16];
  axDbCm                  dbCm;
  axDbCmts                dbCmts;
  axInternalCmts        * intCmts;
  axInternalCm          * intCm;
  axSnmpTemplateSession   sessionTmpl;
  axAbsDbSnmpVerAttrs   * dbSnmpVerAttrs;
  axSnmpSession         * snmpSession;
  axSnmpAsyncCbReqObj   * cbReqObj;

  axSnmpSessionFactory * sFact = axSnmpSessionFactory::getInstance();

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  dbCm.m_cmResId = strtoul(cmResId.c_str(), NULL, 10);
  if (!dbCm.getRow()) {
    goto EXIT_LABEL;
  }

  dbCmts.m_cmtsResId = dbCm.m_cmtsResId;
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  {
    axIntCmtsKey_t cmtsKey(dbCm.m_cmtsResId);
    intCmts = axGlobalData::getInstance()->findCmts(cmtsKey);
    if (!intCmts) {
      goto EXIT_LABEL;
    }
  }

  {
    axDbSnmpAttrHolder iH(dbCmts.getCmSnmpVersionAttrs());
    if ( !(dbSnmpVerAttrs=iH.getAttrs()) ) {
      goto EXIT_LABEL;
    }

    {
      axCsrCmtsCmPoll cmtsCmPoll(intCmts);

      cmtsCmPoll.setCallback(&sessionTmpl);

      {
        axSnmpVerSpecificAttrSetter attrSetter(&sessionTmpl,
                                                      dbSnmpVerAttrs);
        attrSetter.setAttributes();

        getNetSnmpIpAddress(ipAddress, dbCm.m_ipAddress,
                                                dbCm.m_ipAddressType);
        attrSetter.setIpAddress(ipAddress);

        snmpSession = sFact->createSession(&sessionTmpl);
        if (!snmpSession) {
          goto EXIT_LABEL;
        }

        cbReqObj = new axSnmpAsyncCbReqObj(intCmts, &cmtsCmPoll);

        cbReqObj->setSnmpSession(snmpSession);

        ACE_DEBUG((LM_MISC_DEBUG, "%s run begin.\n", myName));

        cmtsCmPoll.setModemIndex(dbCm.m_modemIndex);

        cmtsCmPoll.run();

        _param_3.result->cmMac = cmtsCmPoll.getMac();
        _param_3.result->cmIpAddress = cmtsCmPoll.getIpAddress();

        sprintf(buf, "%d", cmtsCmPoll.getUpstreamChannelIndex());
        _param_3.result->upstreamChannelIndex = buf;

        sprintf(buf, "%d", cmtsCmPoll.getDownstreamChannelIndex());
        _param_3.result->downstreamChannelIndex = buf;

        sprintf(buf, "%d", cmtsCmPoll.getModemIndex());
        _param_3.result->cmIndex = buf;

        if (axInternalCm::isCmOnline(cmtsCmPoll.getDocsisState())) {
          _param_3.result->cmStatus = "online";
        } else {
          _param_3.result->cmStatus = "offline";
        }

        ACE_DEBUG((LM_MISC_DEBUG, "%s run end.\n", myName));

      }

    }

  }

  ret = SOAP_OK;

EXIT_LABEL:

  return (ret);
}


