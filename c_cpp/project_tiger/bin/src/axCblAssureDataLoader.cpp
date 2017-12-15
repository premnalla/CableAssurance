
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axCblAssureDataLoader.hpp"
#include "axDbReadConnHelper.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axInternalCmts.hpp"
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCm.hpp"
#include "axInternalChannel.hpp"
#include "axInternalHfc.hpp"
#include "axInternalEmta.hpp"
#include "axCmtsPollTimer.hpp"
#include "axCmPerfPollTimer.hpp"
#include "axGlobalTimer.hpp"
#include "axPoppedTimerQ.hpp"
#include "axTimerQProcessor.hpp"
#include "axPoppedTimerProcessor.hpp"
#include "axCmtsPoller.hpp"
#include "axDbCmts.hpp"
#include "axDbChannel.hpp"
#include "axDbCm.hpp"
#include "axMtaPoller.hpp"
#include "axMtaPinger.hpp"
#include "axCmtsCmPerfPoller.hpp"
#include "axHfcAlarmCollectionofRCs.hpp"
#include "axHfcMtaAlarmCollectionOfRCs.hpp"
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbResultSet.hpp"
#include "axDbResult.hpp"
#include "axIteratorHolder.hpp"
#include "axDbCACurrentAlarm.hpp"
#include "axDbCAHistoricalAlarm.hpp"
#include "axDbDataTypes.hpp"
// #include "axDbSnmpAttrHolder.hpp"
#include "axHRMtaPollTimer.hpp"
#include "axHRMtaPingTimer.hpp"
#include "axDbCmCurrentStatus.hpp"
#include "axDbCurrentCmPerf.hpp"
#include "axDbMtaCurrentAvailability.hpp"
#include "axDbHfcCurrentStatus.hpp"
#include "axDbHfcCurrentTca.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCblAssureDataLoader * axCblAssureDataLoader::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCblAssureDataLoader::axCblAssureDataLoader() :
  m_dataLoaded(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCblAssureDataLoader::~axCblAssureDataLoader()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCblAssureDataLoader::axCblAssureDataLoader()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCblAssureDataLoader *
axCblAssureDataLoader::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCblAssureDataLoader();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
bool
axCblAssureDataLoader::loadData(void)
{
  bool ret = true;

  if (!m_dataLoaded) {

    loadCmtses();
    // LOG_INFO("loaded CMTS's\n");
    ACE_DEBUG((LM_MISC_DEBUG,"loaded CMTS's\n"));
    // exit(0);

    loadCableModems();
    ACE_DEBUG((LM_MISC_DEBUG,"loaded CM's\n"));

    loadMTAs();
    ACE_DEBUG((LM_MISC_DEBUG,"loaded MTA's\n"));

    loadChannels();
    ACE_DEBUG((LM_MISC_DEBUG,"loaded Channel's\n"));

    loadHfcs();
    ACE_DEBUG((LM_MISC_DEBUG,"loaded HFC's\n"));

    createCmToChannelAssociation();
    ACE_DEBUG((LM_MISC_DEBUG,"created CM/MTA to Channel Association \n"));

    createCmToHfcAssociation();
    ACE_DEBUG((LM_MISC_DEBUG,"created CM/MTA to HFC Association \n"));

    updateCurrentAlarms();

#if 0
    // get some timing
    int i = 0;
    while (i++ < 50) {
      timeTraversal();
    }
    exit(0);
#endif

    // finally ...
    m_dataLoaded = true;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCblAssureDataLoader::createRunnableCollections(void)
{
  bool ret = false;

  // printf("axCblAssureDataLoader::createRunnableCollections(): entry\n");

  if (!m_dataLoaded) {
    goto EXIT_LABEL;
  }

  axCmtsPoller::getInstance();
  axMtaPoller::getInstance();
  axMtaPinger::getInstance();
  axCmtsCmPerfPoller::getInstance();
  axHfcAlarmCollectionofRCs::getInstance();
  axHfcMtaAlarmCollectionOfRCs::getInstance();

  ret = true;

EXIT_LABEL:

  // printf("axCblAssureDataLoader::createRunnableCollections(): exit\n");

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCblAssureDataLoader::queueTimer(void)
{
  bool ret = false;

  // printf("axCblAssureDataLoader::queueTimer(): entry\n");

  srand(time(0));

  axGlobalData * gD;
  axGlobalCmtsTable * gCmtsTable;
  synchronizedTable_t * syncCmtsTbl;

  if (!m_dataLoaded) {
    goto EXIT_LABEL;
  }

  gD = axGlobalData::getInstance();
  gCmtsTable = gD->getCmtsTable();
  syncCmtsTbl = gCmtsTable->getTable();

  {
    axReadLockOperator rLockCmtsTbl(syncCmtsTbl->lock);

    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    struct timeval currTime;
    gettimeofday(&currTime, NULL);

    int i = 0;
    time_t timeToStartPolling = currTime.tv_sec;
    time_t timeToStartMtaPolling = currTime.tv_sec + 20;
    time_t timeToStartMtaPinging = currTime.tv_sec + 25;
    time_t timeToStartCmPerfPolling = currTime.tv_sec + 60;

    axGlobalTimer * timer = axGlobalTimer::getInstance();

    while (cmts) {

      axReadLockOperator rLockCmts(cmts->getLock());

      timeToStartPolling += 3;
      timeToStartMtaPolling += 3;
      timeToStartMtaPinging += 3;
      timeToStartCmPerfPolling += 13;

#if 0
      axCmtsPollTimer * poll = new 
                            axCmtsPollTimer(cmts, timeToStartPolling);
      timer->add(poll);
#endif

#if 0
      axHRMtaPollTimer * mtaPoll =
                new axHRMtaPollTimer(cmts, timeToStartMtaPolling);
      timer->add(mtaPoll);
#endif

#if 0
      axHRMtaPingTimer * mtaPing =
                new axHRMtaPingTimer(cmts, timeToStartMtaPinging);
      timer->add(mtaPing);
#endif

#if 0
      axCmPerfPollTimer * cmPerfPoll = 
                new axCmPerfPollTimer(cmts, timeToStartCmPerfPolling);
      timer->add(cmPerfPoll);
#endif

      i++;

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  ret = true;

EXIT_LABEL:

  // printf("axCblAssureDataLoader::queueTimer(): exit\n");

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::loadCmtses(void)
{
  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    axDbCmts cmts;
    axDbCmtsCurrentCounts counts;
    bool qrc = query->execute(axDbCmts::QueryAllStartup, cmts);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                             (query->getResultSet());

    axGlobalData * gD = axGlobalData::getInstance();
    axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
    synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

    {
      // No need to lock here, it's Startup
      axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                               (syncCmtsTbl->table);

      int i = 0;
      int maxCmts;
      if (sysCfg->getLoadOneCmts()) {
        maxCmts = 1;
      } else {
        maxCmts = 1000;
      }

      while (i<maxCmts && rs->getNext(cmts, counts)) {
      // while (rs->getNext(cmts)) {
        axInternalCmts * intCmts  =  new axInternalCmts(cmts, counts);
        if (!cmtsTbl->add(intCmts)) {
          ACE_DEBUG((LM_ERROR, "Unable to add CMTS: resId=%d\n", 
                                              intCmts->getResId()));
          delete intCmts;
        }
        i++;
        // axDbSnmpV2CAttrs * snmpVer2Attrs;
        // axDbSnmpAttrHolder iH(cmts.getCmSnmpVersionAttrs());
        // snmpVer2Attrs = (axDbSnmpV2CAttrs *) iH.getAttrs();
        // printf("community=%s\n", snmpVer2Attrs->m_readCommunity);
      }

      ACE_DEBUG((LM_MISC_DEBUG,"CMTS table size : %d\n", 
                                                 cmtsTbl->size()));
    }

  }

EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::loadChannels(void)
{
  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      synchronizedTable_t * syncChannelTable = cmts->getChannelTable();

      char sqlBuf[512];

      sprintf(sqlBuf, axDbChannel::CmtsChannelQueryStartup, 
                                                     cmts->getResId());

      axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
      axDbGenericQuery * query = queryH.getQuery();

      axDbChannel dbChannel;
      axDbChannelCurrentCounts counts;
      bool qrc = query->execute(sqlBuf, dbChannel);
      if (!qrc) {
        // continue;
        goto GET_NEXT_CMTS;
      }

      {
        // axWriteLockOperator wLock(syncChannelTable->lock);
        axAvlTree * channelTbl = dynamic_cast<axAvlTree *> 
                                            (syncChannelTable->table);

        axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                              (query->getResultSet());
        while (rs->getNext(dbChannel, counts)) {
          axInternalChannel * intChannel  =  
                             new axInternalChannel(dbChannel, counts);
          channelTbl->add(intChannel);
        }

        ACE_DEBUG((LM_MISC_DEBUG, 
                          "Total Channels loaded for CMTS(%d) = %d\n", 
                               cmts->getResId(), channelTbl->size()));
      }

GET_NEXT_CMTS:

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::loadHfcs(void)
{
  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator cmtsTblLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      synchronizedTable_t * syncHfcTable = cmts->getHfcTable();

      char sqlBuf[1024];

      struct timeval currTm;
      gettimeofday(&currTm, NULL);
      time_t anHourPrior = currTm.tv_sec - 3600;

      sprintf(sqlBuf, axDbHfc::CmtsHfcQueryStartup, cmts->getResId());

      axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
      axDbGenericQuery * query = queryH.getQuery();

      axDbHfc dbHfc;
      axDbHfcCurrentCounts counts;
      axDbHfcCurrentStatus hfcSt;
      axDbHfcCurrentTca hfcTca;
      bool qrc = query->execute(sqlBuf, dbHfc);
      if (!qrc) {
        // continue;
        goto GET_NEXT_CMTS;
      }

      {
        // axWriteLockOperator wLock(syncHfcTable->lock);
        axAvlTree * hfcTbl = dynamic_cast<axAvlTree *> 
                                                 (syncHfcTable->table);
        axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                               (query->getResultSet());
        while (rs->getNext(dbHfc, counts, hfcSt, hfcTca)) {
          axInternalHfc * intHfc  =  new axInternalHfc(
             dbHfc, counts, hfcSt, hfcTca);
          axIntHfcNonkey_t * nk = (axIntHfcNonkey_t *) 
                                                   intHfc->getNonKey();
          // nk->nextPercentAlmStartTm = anHourPrior;
          // nk->nextPowerAlmStartTm = anHourPrior;

          if (!intHfc->isAddable() || !hfcTbl->add(intHfc)) {
            delete intHfc;
          }
        }

        ACE_DEBUG((LM_MISC_DEBUG, 
                       "Total HFC's loaded for CMTS(resId=%d) = %d\n", 
                                    cmts->getResId(), hfcTbl->size()));
      }

GET_NEXT_CMTS:

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::loadCableModems(void)
{
  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      synchronizedTable_t * syncCmTable = cmts->getCmTable();

      char sqlBuf[1024];

      sprintf(sqlBuf, axDbCm::CmtsCmQueryStartup, cmts->getResId());

      axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
      axDbGenericQuery * query = queryH.getQuery();

      axDbCm dbCm;
      axDbCmCurrentStatus status;
      axDbCurrentCmPerf perf;
      bool qrc = query->execute(sqlBuf, dbCm);
      if (!qrc) {
        // continue;
        goto GET_NEXT_CMTS;
      }

      {
        // axWriteLockOperator wLock(syncCmTable->lock);
        axAvlTree * cmTbl = dynamic_cast<axAvlTree *> 
                                                 (syncCmTable->table);

        axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                              (query->getResultSet());
        while (rs->getNext(dbCm,status,perf)) {
          axInternalCm * intCm  =  new axInternalCm(dbCm,status,perf);
          if (!intCm->isAddable() || !cmTbl->add(intCm)) {
            ACE_DEBUG((LM_ERROR, "CM not addable: mac=%s\n", 
                                             intCm->getMacAddress()));
            delete intCm;
          }
        }

        ACE_DEBUG((LM_MISC_DEBUG,
                       "Total CM's loaded for CMTS(resId=%d) = %d\n", 
                                   cmts->getResId(), cmTbl->size()));
      }

GET_NEXT_CMTS:

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::loadMTAs(void)
{
  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();
  // int totalMTAs = 0;

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                 (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                   (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      synchronizedTable_t * syncMtaTable = cmts->getMtaTable();

      char sqlBuf[1024];

      struct timeval currTm;
      gettimeofday(&currTm, NULL);
      time_t anHourPrior = currTm.tv_sec - 3600;

      sprintf(sqlBuf, axDbEmta::CmtsEmtaQueryStartup, cmts->getResId());

      axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
      axDbGenericQuery * query = queryH.getQuery();

      axDbEmta dbEmta;
      axDbMtaCurrentAvailability st;
      bool qrc = query->execute(sqlBuf, dbEmta);
      if (!qrc) {
        // continue;
        goto GET_NEXT_CMTS;
      }

      {
        // axWriteLockOperator wLock(syncMtaTable->lock);
        axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> 
                                                (syncMtaTable->table);

        axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                              (query->getResultSet());
        while (rs->getNext(dbEmta, st)) {
          axInternalEmta * intMta  =  new axInternalEmta(dbEmta, st, cmts);
          axIntGenMtaNonkey_t * nk = (axIntGenMtaNonkey_t *) 
                                                  intMta->getNonKey();
          nk->powerAlarmChangeTime = anHourPrior;
          /*
           * TODO: set ...
           */
          if (!intMta->isAddable() || !mtaTbl->add(intMta)) {
            ACE_DEBUG((LM_ERROR, "Unable to add MTA (mac=%s)\n",
                                         intMta->getMtaMacAddress()));
            delete intMta;
          }
        }

        ACE_DEBUG((LM_MISC_DEBUG, 
                       "Total MTA's loaded for CMTS(resId=%d) = %d\n",
                                    cmts->getResId(), mtaTbl->size()));
      }

GET_NEXT_CMTS:

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  // printf("Total eMTA's loaded = %d\n", totalMTAs);

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::createCmToChannelAssociation(void)
{
  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator cmtsRlock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                  (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * cmtsIter = dynamic_cast<axAvlIterator *> 
                                                    (iH.getIterator());

    axInternalCmts * cmts = dynamic_cast<axInternalCmts *> 
                                                (cmtsIter->getFirst());

    while (cmts) {

      synchronizedTable_t * syncChannelTable = cmts->getChannelTable();
      // axReadLockOperator channelRlock(syncChannelTable->lock);
      axAvlTree * channelTbl = dynamic_cast<axAvlTree *> 
                                              (syncChannelTable->table);
      axIteratorHolder iChH(channelTbl->createIterator());
      axAvlIterator * channelIter = dynamic_cast<axAvlIterator *> 
                                                   (iChH.getIterator());
      axInternalChannel * intChannel = dynamic_cast<axInternalChannel *> 
                                              (channelIter->getFirst());

      synchronizedTable_t * syncCmTable = cmts->getCmTable();
      // axReadLockOperator cmRlock(syncCmTable->lock);
      axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (syncCmTable->table);
      axInternalCm * actualIntCm;

      synchronizedTable_t * syncMtaTable = cmts->getMtaTable();
      // axReadLockOperator mtaRlock(syncMtaTable->lock);
      axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> (syncMtaTable->table);
      axInternalEmta * actualIntEmta;

      while (intChannel) {

        char sqlBuf[512];
        char sqlBufEmta[512];

        if (intChannel->isUpstreamChannel()) {
          sprintf(sqlBuf, axDbCm::UpstreamChannelCmQuery, 
                                                 intChannel->getResId());
          sprintf(sqlBufEmta, axDbEmta::UpstreamChannelEmtaQuery, 
                                                 intChannel->getResId());
        } else if (intChannel->isDownstreamChannel()) {
          sprintf(sqlBuf, axDbCm::DownstreamChannelCmQuery, 
                                                 intChannel->getResId());
          sprintf(sqlBufEmta, axDbEmta::DownstreamChannelEmtaQuery, 
                                                 intChannel->getResId());
        } else {
          // continue;
          goto GET_NEXT_CHANNEL;
        }

        {
          axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
          axDbGenericQuery * query = queryH.getQuery();

          axDbCm dbCm;
          bool qrc = query->execute(sqlBuf, dbCm);
          if (!qrc) {
            // continue;
            goto GET_NEXT_CHANNEL;
          }

          axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                                (query->getResultSet());
          while (rs->getNext(dbCm)) {
            axInternalCm tmpCm(dbCm.m_cmMac);
            actualIntCm = dynamic_cast<axInternalCm *> 
                                                  (cmTbl->find(&tmpCm));
            if (actualIntCm) {
              intChannel->addCmExtLock(actualIntCm);
            }

          }
        }

        {
          axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
          axDbGenericQuery * query = queryH.getQuery();

          axDbEmta dbEmta;
          bool qrc = query->execute(sqlBufEmta, dbEmta);
          if (!qrc) {
            // continue;
            goto GET_NEXT_CHANNEL;
          }

          axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                              (query->getResultSet());

          while (rs->getNext(dbEmta)) {
            // axInternalEmta tmpEmta(dbEmta.m_mtaMac);
            axInternalEmta tmpEmta(dbEmta, cmts);
            // printf("MAC = %s\n", tmpEmta.getMtaMacAddress());
            if (tmpEmta.isAddable()) {
              actualIntEmta = dynamic_cast<axInternalEmta *> 
                                             (mtaTbl->find(&tmpEmta));
              if (actualIntEmta) {
                intChannel->addMtaExtLock(actualIntEmta);
              }
            }
          } // while
        }

GET_NEXT_CHANNEL:

        intChannel = dynamic_cast<axInternalChannel *> 
                                            (channelIter->getNext());
      }

      cmts = (axInternalCmts *) cmtsIter->getNext();

    } // while

  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::createCmToHfcAssociation(void)
{
  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator cmtsRlock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * cmtsIter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());
    axInternalCmts * cmts = dynamic_cast<axInternalCmts *> 
                                              (cmtsIter->getFirst());
    while (cmts) {

      synchronizedTable_t * syncHfcTable = cmts->getHfcTable();

      // axReadLockOperator hfcRlock(syncHfcTable->lock);
      axAvlTree * hfcTbl = dynamic_cast<axAvlTree *> 
                                               (syncHfcTable->table);
      axIteratorHolder iHfcH(hfcTbl->createIterator());
      axAvlIterator * hfcIter = dynamic_cast<axAvlIterator *> 
                                               (iHfcH.getIterator());

      axInternalHfc * intHfc = dynamic_cast<axInternalHfc *> 
                                               (hfcIter->getFirst());

      synchronizedTable_t * syncCmTable = cmts->getCmTable();
      // axReadLockOperator cmRlock(syncCmTable->lock);
      axAvlTree * cmTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmTable->table);
      axInternalCm * actualIntCm;

      synchronizedTable_t * syncMtaTable = cmts->getMtaTable();
      // axReadLockOperator mtaRlock(syncMtaTable->lock);
      axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> 
                                               (syncMtaTable->table);
      axInternalEmta * actualIntEmta;

      while (intHfc) {

        char sqlBuf[512];

        {
          sprintf(sqlBuf, axDbCm::HfcCmQuery, intHfc->getResId());

          axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
          axDbGenericQuery * query = queryH.getQuery();

          axDbCm dbCm;
          bool qrc = query->execute(sqlBuf, dbCm);
          if (!qrc) {
            // continue;
            goto GET_NEXT_HFC;
          }

          axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                           (query->getResultSet());
          while (rs->getNext(dbCm)) {

            axInternalCm tmpCm(dbCm.m_cmMac);
            actualIntCm = dynamic_cast<axInternalCm *> 
                                             (cmTbl->find(&tmpCm));

            if (actualIntCm) {
              intHfc->addCmExtLock(actualIntCm);
            }

          }
        }

        {
          sprintf(sqlBuf, axDbEmta::HfcEmtaQuery, 
                                               intHfc->getResId());

          axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
          axDbGenericQuery * query = queryH.getQuery();

          axDbEmta dbEmta;
          bool qrc = query->execute(sqlBuf, dbEmta);
          if (!qrc) {
            // continue;
            goto GET_NEXT_HFC;
          }

          axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                           (query->getResultSet());

          while (rs->getNext(dbEmta)) {

            axInternalEmta tmpEmta(dbEmta, cmts);
            if (tmpEmta.isAddable()) {
              actualIntEmta = dynamic_cast<axInternalEmta *> 
                                          (mtaTbl->find(&tmpEmta));

              if (actualIntEmta) {
                intHfc->addMtaExtLock(actualIntEmta);
              }
            }

          } // while
        }

GET_NEXT_HFC:

        intHfc = dynamic_cast<axInternalHfc *> (hfcIter->getNext());
      }

      cmts = (axInternalCmts *) cmtsIter->getNext();

    } // while

  }

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::updateCurrentAlarms(void)
{
  axDbCACurrentAlarm      currAlm;
  axDbCACurrentAlarm    * currAlmPtr;
  axListBase              soakingAlarms;
  char                    sqlStr[256];

  sprintf(sqlStr, axDbCACurrentAlarm::QueryByState, 
                                             DB_ALARM_STATE_SOAKING);
  currAlm.getRows(soakingAlarms, sqlStr);

  while ( (currAlmPtr = (axDbCACurrentAlarm *) soakingAlarms.remove()) ) {

    currAlmPtr->m_alarmState = DB_ALARM_STATE_RESTART_DISCARD;

    if (currAlmPtr->updateRow()) {
      axDbCAHistoricalAlarm   histAlm(currAlmPtr->m_rootAlarmId);
      if (histAlm.getRow()) {
        histAlm.m_alarmState = currAlmPtr->m_alarmState;
        histAlm.updateRow();
      }
      axDbCAAlarmHistory almHist(*currAlmPtr);
      almHist.insertRow();
    }

    // finally
    delete currAlmPtr;
  }

}


//********************************************************************
// method:
//********************************************************************
void
axCblAssureDataLoader::timeTraversal(void)
{
  static const char * myName="timeTraversal:";

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator cmtsRlock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *>
                                                  (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * cmtsIter = dynamic_cast<axAvlIterator *>
                                                    (iH.getIterator());

    axInternalCmts * cmts;

    for (cmts = dynamic_cast<axInternalCmts *>(cmtsIter->getFirst());
         cmts;
         cmts = dynamic_cast<axInternalCmts *>(cmtsIter->getNext())) {

      synchronizedTable_t * syncCmTbl = cmts->getCmTable();
      // axReadLockOperator channelRlock(syncCmTbl->lock);
      axAvlTree * cmTbl = dynamic_cast<axAvlTree *>
                                                   (syncCmTbl->table);
      axIteratorHolder iChH(cmTbl->createIterator());
      axAvlIterator * cmIter = dynamic_cast<axAvlIterator *>
                                                   (iChH.getIterator());
      axInternalCm * intCm;

      ACE_DEBUG((LM_DEBUG, "%s start cm traversal\n", myName));
      int i = 0;
      for (intCm = dynamic_cast<axInternalCm *>(cmIter->getFirst());
           intCm;
           intCm = dynamic_cast<axInternalCm *>(cmIter->getNext())) {
        axReadLockOperator(intCm->getLock());
        i++;
      }
      ACE_DEBUG((LM_DEBUG, "%s end traversal %d cm's\n", myName, i));
    }
  }
}


