/************************************************************************* 
 * Copyright (c) 2007 by Premraj Nallasivampillai. All rights reserved.
 *************************************************************************
 *
 * cPeerServImpl.cpp      
 *
 *************************************************************************
 * Modification History:
 *			
 *************************************************************************
 * Description:
 *
 *************************************************************************
 * Modification History:
 *
 *    4/13/07    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

/*************************************************************************
 * include files
 *************************************************************************/
#include "axCALog.hpp"
#include "cPeerServH.h"
#include "axMyGsoapConstants.hpp"
#include "axLocalSystemSingleton.hpp"
#include "axDbCmts.hpp"
#include "axGlobalData.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalCmts.hpp"
#include "axCALoadDbConfig.hpp"

/*************************************************************************
 * definitions/macros
 *************************************************************************/


/*************************************************************************
 * constants
 *************************************************************************/

/*************************************************************************
 * static member initialization
 *************************************************************************/


/*************************************************************************
 * forward declerations
 *************************************************************************/


/*************************************************************************
 * functions
 *************************************************************************/

/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
int cpeer__pingMta(struct soap*, ns1__TopoHierarchyKeyT *topologyKey, 
                                 std::string mtaResId, std::string &result)
{
  static const char * myName="cpeer__pingMta:";

  int ret = MY_SOAP_ERROR;

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  axLocalSystemSingleton * lss = axLocalSystemSingleton::getInstance();
  axAbstractCPeerServices * svc = lss->getCPeerServices();
  if (!svc) {
    goto EXIT_LABEL;
  }

  ret = svc->pingMta(topologyKey, mtaResId, result);

EXIT_LABEL:

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return (ret);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
int cpeer__getMtaData(struct soap*, ns1__TopoHierarchyKeyT *topologyKey, 
         std::string mtaResId, struct cpeer__getMtaDataResponse &_param_1)
{
  static const char * myName="cpeer__getMtaData:";

  int ret = MY_SOAP_ERROR;

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  axLocalSystemSingleton * lss = axLocalSystemSingleton::getInstance();
  axAbstractCPeerServices * svc = lss->getCPeerServices();
  if (!svc) {
    goto EXIT_LABEL;
  }

  ret = svc->getMtaData(topologyKey, mtaResId, _param_1);

EXIT_LABEL:

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return (ret);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
int cpeer__getCmData(struct soap*, ns1__TopoHierarchyKeyT *topologyKey, 
           std::string cmResId, struct cpeer__getCmDataResponse &_param_2)
{
  static const char * myName="cpeer__getCmData:";

  int ret = MY_SOAP_ERROR;

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  axLocalSystemSingleton * lss = axLocalSystemSingleton::getInstance();
  axAbstractCPeerServices * svc = lss->getCPeerServices();
  if (!svc) {
    goto EXIT_LABEL;
  }

  ret = svc->getCmData(topologyKey, cmResId, _param_2);

EXIT_LABEL:

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return (ret);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
int cpeer__getCmtsCmData(struct soap*, ns1__TopoHierarchyKeyT *topologyKey, 
                                std::string cmtsResId, std::string cmResId, 
                             struct cpeer__getCmtsCmDataResponse &_param_3)
{
  static const char * myName="cpeer__getCmtsCmData:";

  int ret = MY_SOAP_ERROR;

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  axLocalSystemSingleton * lss = axLocalSystemSingleton::getInstance();
  axAbstractCPeerServices * svc = lss->getCPeerServices();
  if (!svc) {
    goto EXIT_LABEL;
  }

  ret = svc->getCmtsCmData(topologyKey, cmtsResId, cmResId, _param_3);

EXIT_LABEL:

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return (ret);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
int cpeer__sendEvent(soap *, ns1__EventMessageT * ec, short & rc)
{
  static const char * myName="cpeer__getCmtsCmData:";

  rc = -1;

  int ret = MY_SOAP_ERROR;

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  axGlobalData * gData = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTble = gData->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTble->getTable();

  if (!ec) {
    goto EXIT_LABEL;
  }

  if (ec->eventCategory == ns1__EventCategoryT__Resource) {
    if (strcmp(ec->eventSubCategory.c_str(), "CMTS")==0) {
      if (ec->objectId != NULL) {
        axDbCmts dbCmts;
        dbCmts.m_cmtsResId = 
                (AX_UINT32)strtoul(ec->objectId->c_str(), NULL, 10);
        if (dbCmts.getRow()) {
          if (ec->eventType == ns1__EventTypeT__Add) {
            axWriteLockOperator wL(syncCmtsTbl->lock);
            axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *>
                                               (syncCmtsTbl->table);
            axInternalCmts * intCmts = new axInternalCmts(dbCmts);
            if (!cmtsTbl->add(intCmts)) {
              ACE_DEBUG((LM_ERROR, "Unable to add CMTS: resId=%d\n",
                                              intCmts->getResId()));
              delete intCmts;
            }
          } else if (ec->eventType == ns1__EventTypeT__Delete) {
            axIntCmtsKey_t cmtsKey(dbCmts.m_cmtsResId);
            axInternalCmts * intCmts = gData->findCmts(cmtsKey);
            if (intCmts) {
              axWriteLockOperator wLock(intCmts->getLock());
              axIntCmtsNonkey_t * cmtsNk = (axIntCmtsNonkey_t *) 
                                               intCmts->getNonKey();
              cmtsNk->isDeleted = 1;
            }
          } else if (ec->eventType == ns1__EventTypeT__Update) {
            axIntCmtsKey_t cmtsKey(dbCmts.m_cmtsResId);
            axInternalCmts * intCmts = gData->findCmts(cmtsKey);
            if (intCmts) {
              axWriteLockOperator wLock(intCmts->getLock());
              axIntCmtsNonkey_t * cmtsNk = (axIntCmtsNonkey_t *)
                                               intCmts->getNonKey();
#if 0
              axInternalCmts iCmts(dbCmts);
              axIntCmtsNonkey_t * iNk = (axIntCmtsNonkey_t *) 
                                                  iCmts.getNonKey();
              *cmtsNk = *iNk;
#endif
              copyIntFqdn(cmtsNk->fqdn, dbCmts.m_fqdn);
              copyIntIpAddress(cmtsNk->ipAddress, dbCmts.m_ipAddress);
              cmtsNk->ipAddressType = dbCmts.m_ipAddressType;
            }
          } else {
          }
        }
      }
    // end-if CMTS
    } else {
    }
  } else if (ec->eventCategory == ns1__EventCategoryT__Configuration) {
#if 0
    if (strcmp(ec->eventSubCategory.c_str(), "CMTS_ALARM")==0) {
    } else if (strcmp(ec->eventSubCategory.c_str(), "CMS_ALARM")==0) {
    } else if (strcmp(ec->eventSubCategory.c_str(), "HFC_ALARM")==0) {
    } else if (strcmp(ec->eventSubCategory.c_str(), "MTA_ALARM")==0) {
    } else if (strcmp(ec->eventSubCategory.c_str(), "GENERAL")==0) {
    } else if (strcmp(ec->eventSubCategory.c_str(), "CM_PERF")==0) {
    }
#endif
    axCALoadDbConfig dbConfig;
    dbConfig.loadConfig();
  } else {
    goto EXIT_LABEL;
  }

  rc = 0;

EXIT_LABEL:

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return (ret);
}


