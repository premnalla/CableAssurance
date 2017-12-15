
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axCALog.hpp"
#include "axCAFqdnToIpPoller.hpp"
#include "axCAController.hpp"
#include "axDbCmts.hpp"
#include "axListFree.hpp"
#include "axIteratorHolder.hpp"
#include "axAbstractIterator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axAvlTree.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalGenMta.hpp"
#include "axDbEmta.hpp"
#include "axDbUtils.hpp"

#if 0
#include "axAvlIterator.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axCALoadDbConfig.hpp"
#endif

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
axCAFqdnToIpPoller::axCAFqdnToIpPoller()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCAFqdnToIpPoller::~axCAFqdnToIpPoller()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCAFqdnToIpPoller::axCAFqdnToIpPoller()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCAFqdnToIpPoller::run(void)
{
  static const char * myName="FqdnToIpPoller:";

  AX_INT32 ret = 0;

  axCAController * ctlr = axCAController::getInstance();

  while (!ctlr->isExit()) {
    sleep(30*60);
    ACE_DEBUG((LM_TIMING_DEBUG, "%s start\n",myName));
    axListFree l;
    axDbCmts tmpDbCmts;
    tmpDbCmts.getRows(l, axDbCmts::QueryAll);
    axIteratorHolder iH(l.createIterator());
    axAbstractIterator * iter = iH.getIterator();
    axDbCmts * dbCmts = dynamic_cast<axDbCmts *> (iter->getFirst());
    for (; dbCmts; 
         dbCmts = dynamic_cast<axDbCmts *> (iter->getNext()) ) {
      convertFqdnToIp(dbCmts);
    }
    ACE_DEBUG((LM_TIMING_DEBUG, "%s end\n",myName));
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCAFqdnToIpPoller::convertFqdnToIp(axDbCmts * dbCmts)
{
  axGlobalData * gD = axGlobalData::getInstance();

  axIntCmtsKey_t cmtsKey(dbCmts->m_cmtsResId);
  axInternalCmts * intCmts = gD->findCmts(cmtsKey);
  if (!intCmts) {
    goto EXIT_LABEL;
  }

  {
    struct timespec nSlpTm;
    nSlpTm.tv_sec = 0;
    nSlpTm.tv_nsec = 5*10^5; // => (500 * 1000) => 500 mSec;

    axListFree l;
    axDbEmta tmpEmta;
    char sqlStr[1024];
    sprintf(sqlStr, axDbEmta::CmtsEmtaQuery, intCmts->getResId());
    tmpEmta.getRows(l, sqlStr);
    axIteratorHolder iH(l.createIterator());
    axAbstractIterator * iter = iH.getIterator();
    axDbEmta * dbEmta = dynamic_cast<axDbEmta *> (iter->getFirst());
    for (; dbEmta;
               dbEmta = dynamic_cast<axDbEmta *> (iter->getNext()) ) {

      char ipAddress[64];
      if (ipAddressChanged(dbEmta, ipAddress)) {
        copyDbIpAddress(dbEmta->m_mtaMac, ipAddress);
        if (dbEmta->updateRow()) {
          axInternalGenMta * intMta = 
                                     dynamic_cast<axInternalGenMta *>
                                  (intCmts->findMta(dbEmta->m_mtaMac));
          if (intMta) {
            axWriteLockOperator wL(intMta->getLock());
            if (intMta->hasData()) {
              axIntGenMtaNonkey_t * nk = (axIntGenMtaNonkey_t *) 
                                                  intMta->getNonKey();
              copyIntIpAddress(nk->ipAddress, ipAddress);
            }
          }
        }
      }
      nanosleep(&nSlpTm, NULL);
    } // for
  }

EXIT_LABEL:
  return;
}


//********************************************************************
// method:
//********************************************************************
bool
axCAFqdnToIpPoller::ipAddressChanged(axDbEmta * dbEmta, 
                                                  AX_INT8 * ipAddress)
{
  bool ret = false;

  /* TODO */

  return (ret);
}


