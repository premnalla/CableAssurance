
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <string.h>
#include "axCblAssureConstants.hpp"
#include "axDeviceClassification.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axInternalHfc.hpp"
#include "axInternalChannel.hpp"
#include "axInternalCm.hpp"
#include "axInternalEmta.hpp"
#include "axDbCmts.hpp"
#include "axDbHfc.hpp"
#include "axDbChannel.hpp"
#include "axDbCm.hpp"
#include "axDbEmta.hpp"
#include "axDbUtils.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axMtaPinger.hpp"
#include "axMtaPoller.hpp"
#include "axHRMtaPingRunnableCollection.hpp"
#include "axHRMtaPollRunnableCollection.hpp"
#include "axDbMtaCurrentAvailability.hpp"
#include "axDbHfcCurrentCounts.hpp"
#include "axDbHfcCurrentStatus.hpp"
#include "axDbHfcCurrentTca.hpp"
#include "axDbEmtaSecondary.hpp"

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
axDeviceClassification::axDeviceClassification() :
  m_dcData(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDeviceClassification::~axDeviceClassification()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDeviceClassification::axDeviceClassification(axDcData * dcData) :
  m_dcData(dcData)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDeviceClassification::classifyDevice(void)
{
  bool ret = false;

  axDbCm   dbCm;
  axDbCmts dbCmts;
  axIntCmtsKey_t intCmtsKey;
  axInternalCmts * intCmts = NULL;
  axInternalCm * intCm = NULL;
  axInternalHfc * intHfc = NULL;
  axInternalGenMta * intMta = NULL;
  axInternalChannel * intUpChannel = NULL;
  axInternalChannel * intDnChannel = NULL;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  /* 
   * Locate CM in DB
   */
  copyDbMac(dbCm.m_cmMac, m_dcData->m_cmMac.c_str());
  if (!dbCm.getRow()) {
    goto EXIT_LABEL;
  }

  /* 
   * Get CMTS from DB
   */
  dbCmts.m_cmtsResId = dbCm.m_cmtsResId;
  if (!dbCmts.getRow()) {
    goto EXIT_LABEL;
  }

  /* 
   * Locate in-memory CMTS 
   */
  intCmtsKey.resId = dbCmts.m_cmtsResId;
  intCmts = axGlobalData::getInstance()->findCmts(intCmtsKey);
  if (!intCmts) {
    goto EXIT_LABEL;
  }

  /* 
   * Locate in-memory CM 
   */
  intCm = (axInternalCm *) intCmts->findCm(dbCm.m_cmMac);
  if (!intCm) {
    goto EXIT_LABEL;
  }

  /* 
   * Find and process HFC
   */
  {
    axIntHfcKey_t hfcKey(
                    const_cast<char *> (m_dcData->m_hfcName.c_str()));
    axDbHfc dbHfc(dbCmts.m_cmtsResId, hfcKey.name);
    intHfc = (axInternalHfc *) intCmts->findHfc(hfcKey);

    if (!intHfc) {

      if (!dbHfc.insertRow() && !dbHfc.getRow()) {
        goto EXIT_LABEL;
      }
      /* we successfully inserted OR got HFC */

      {
        axDbHfcCurrentCounts counts(dbHfc.m_hfcResId);
        counts.m_timeSec = tm.tv_sec;
        counts.insertRow();

        intHfc = new axInternalHfc(dbHfc);
        if (!intHfc->isAddable() || !intCmts->addHfc(intHfc)) {
          delete intHfc;
          goto EXIT_LABEL;
        }

        axDbHfcCurrentStatus hfcSt(dbHfc.m_hfcResId, tm.tv_sec, 
                             (axIntHfcNonkey_t *)intHfc->getNonKey());
        hfcSt.insertRow();

        axDbHfcCurrentTca hfcTa(dbHfc.m_hfcResId, tm.tv_sec, 
                             (axIntHfcNonkey_t *)intHfc->getNonKey());
        hfcTa.insertRow();
      }

    } else {
      /*
       * Update HFC
       */
    }

    dbCm.m_hfcResId = dbHfc.m_hfcResId;

    axWriteLockOperator hfcWlock(intHfc->getLock());
    if (!intHfc->findCm(intCm)) {
      intHfc->addCm(intCm);
    }
    /* located or added in-memory HFC */

  }

  /* 
   * Find and process Channel
   */
  {
    axIntChannelKey_t upChannelKey(
          const_cast<char *> (dbCm.m_upstreamChannelName.c_str()));
    axDbChannel dbChnl(upChannelKey.name, dbCmts.m_cmtsResId);
    // axIntChannelKey_t dnChannelKey(
    //     const_cast<char *> (dbCm.m_downstreamChannelName.c_str()));
    intUpChannel = (axInternalChannel *)
                                intCmts->findChannel(upChannelKey);
    if (!intUpChannel) {
      goto EXIT_LABEL;
    }
    /* located or added in-memory Channel */
  }

  {

    /*
     * Is this device an eMTA?
     */

    if (strlen(m_dcData->m_mtaMac.c_str())) {

      /*
       * This device is an eMTA
       */

      intMta = (axInternalGenMta *) intCmts->findMta(
                    const_cast<char *> (m_dcData->m_mtaMac.c_str()));

      if (!intMta) {

        axDbEmta dbEmta(
                    const_cast<char *> (m_dcData->m_mtaMac.c_str()),
                    dbCmts.m_cmtsResId);
        dbEmta.m_cm = dbCm;
        /* TODO: set MTA attributes before insert below */
        // dbEmta.m_fqdn = foo;
        // ...etc...

        if (!dbEmta.insertRow() || !dbEmta.getRow()) {
          goto EXIT_LABEL;
        }

        {
          axDbEmtaSecondary mtaExtra;
          mtaExtra.m_emta_res_id = dbEmta.m_mtaResId;
          /* TODO: set MTA phone# before insert below */
          mtaExtra.m_phone1 = "774-348-4000";
          mtaExtra.m_phone2 = "774-348-4001";
          mtaExtra.insertRow();

          axDbMtaCurrentAvailability mtaCurrAvail(dbEmta.m_mtaResId);
          mtaCurrAvail.m_lastLogTime = tm.tv_sec;
          mtaCurrAvail.m_lastStateChangeTime = tm.tv_sec;
          mtaCurrAvail.m_currentValue = AX_AVAIL_ST_UNAVAILABLE;
          mtaCurrAvail.insertRow();

          intMta = new axInternalEmta(dbEmta, mtaCurrAvail, intCmts);
          if (!intMta->isAddable() || !intCmts->addMta(intMta)) {
            delete intMta;
            goto EXIT_LABEL;
          }
        }

        {
          axReadLockOperator cmRlock(intCm->getLock());
          axIntCmNonkey_t * cmNk = 
                              (axIntCmNonkey_t *) intCm->getNonKey();
          if (intCm->hasData()) {
            cmNk->euDeviceType = AX_INT_EU_DEVIE_TYPE_EMTA;
            dbCm.m_enduserDeviceType = DB_EU_DEVIE_TYPE_EMTA;
          }
        }

      } else {

        /*
         * TODO: Update scenario
         */

      }

      {
        axWriteLockOperator hfcWlock(intHfc->getLock());
        if (!intHfc->findMtaExtLock(intMta)) {
          intHfc->addMtaExtLock(intMta);
        } 
        intHfc->removeCmExtLock(intCm);
      }

      {
        axWriteLockOperator chnlWlock(intUpChannel->getLock());
        if (!intUpChannel->findMtaExtLock(intMta)) {
          intUpChannel->addMtaExtLock(intMta);
        } 
        intUpChannel->removeCmExtLock(intCm);
      }

      /*
       * ADD to Ping & Poll runnables
       */
      {
        axMtaPoller * mtaPoller = axMtaPoller::getInstance();
        axMtaPinger * mtaPinger = axMtaPinger::getInstance();
        axHRMtaPollRunnableCollection tmpPollRc(intCmts->getResId());
        axHRMtaPingRunnableCollection tmpPingRc(intCmts->getResId());
        axHRMtaPollRunnableCollection * actPollRc;
        axHRMtaPingRunnableCollection * actPingRc;

        actPollRc = (axHRMtaPollRunnableCollection *) 
                                          mtaPoller->find(&tmpPollRc);
        if (actPollRc) {
          actPollRc->addSubject(intMta);
        }
        actPingRc = (axHRMtaPingRunnableCollection *) 
                                          mtaPinger->find(&tmpPingRc);
        if (actPingRc) {
          actPingRc->addSubject(intMta);
        }
      }

    } // if eMTA
  }

  dbCm.updateRow();
  dbCm.getRow();

  ret = true;

EXIT_LABEL:

  return (ret);
}




