
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <assert.h>
#include "axCALog.hpp"
#include "axInternalCmts.hpp"
#include "axAvlTree.hpp"
#include "axMultipleReaderLock.hpp"
#include "axInternalChannel.hpp"
#include "axInternalHfc.hpp"
#include "axInternalCm.hpp"
#include "axInternalEmta.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalUtils.hpp"

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
// method : helper
//********************************************************************
void
axInternalCmts::initAtInstantiation(void)
{
  m_channelTable.table = new axAvlTree();
  m_channelTable.lock = new axMultipleReaderLock();

  m_hfcTable.table = new axAvlTree();
  m_hfcTable.lock = new axMultipleReaderLock();

  // m_cmTable.table = new axAvlTree(axInternalCm::CompareFunction);
  m_cmTable.table = new axAvlTree();
  m_cmTable.lock = new axMultipleReaderLock();

  m_mtaTable.table = new axAvlTree();
  m_mtaTable.lock = new axMultipleReaderLock();
}


//********************************************************************
// method : helper
//********************************************************************
void
axInternalCmts::initPointers(void)
{
  m_channelTable.table = NULL;
  m_channelTable.lock = NULL;

  m_hfcTable.table = NULL;
  m_hfcTable.lock = NULL;

  m_cmTable.table = NULL;
  m_cmTable.lock = NULL;

  m_mtaTable.table = NULL;
  m_mtaTable.lock = NULL;
}


//********************************************************************
// default constructor:
//********************************************************************
axInternalCmts::axInternalCmts() :
  m_cmtsData() // , m_docsisCapability(DOCSIS_CAPABILITY_1_1)
{
  initPointers();
}


//********************************************************************
// destructor:
//********************************************************************
axInternalCmts::~axInternalCmts()
{
  if (m_cmTable.table) {
    delete m_cmTable.table;
    // m_cmTable.table = NULL;
  }

  if (m_cmTable.lock) {
    delete m_cmTable.lock;
    // m_cmTable.lock = NULL;
  }

  if (m_mtaTable.table) {
    delete m_mtaTable.table;
    // m_mtaTable.table = NULL;
  }

  if (m_mtaTable.lock) {
    delete m_mtaTable.lock;
    // m_mtaTable.lock = NULL;
  }

  if (m_channelTable.table) {
    delete m_channelTable.table;
    // delete m_channelTable.lock;
  }

  if (m_hfcTable.table) {
    delete m_hfcTable.table;
    // delete m_hfcTable.lock;
  }

}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCmts::axInternalCmts(axIntCmtsKey_t & key) :
  m_cmtsData(key) // , m_docsisCapability(DOCSIS_CAPABILITY_1_1)
{
  initPointers();
}


//********************************************************************
// method:
//********************************************************************
void
axInternalCmts::init(axDbCmts & dbCmts)
{
  m_cmtsData.keyPart.resId = dbCmts.m_cmtsResId;
  m_cmtsData.nonkeyPart = new axIntCmtsNonkey_t();

  m_cmtsData.nonkeyPart->onlineState = dbCmts.m_onlineState;
  copyIntFqdn(m_cmtsData.nonkeyPart->fqdn, dbCmts.m_fqdn);
  copyIntIpAddress(m_cmtsData.nonkeyPart->ipAddress, dbCmts.m_ipAddress);

#if 0
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->cmtsReadCommunity,
                                          dbCmts.m_cmtsReadCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->cmReadCommunity,
                                            dbCmts.m_cmReadCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->cmWriteCommunity,
                                           dbCmts.m_cmWriteCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->mtaReadCommunity,
                                           dbCmts.m_mtaReadCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->mtaWriteCommunity,
                                          dbCmts.m_mtaWriteCommunity);
#endif

  m_cmtsData.nonkeyPart->docsisCapability = DOCSIS_CAPABILITY_1_1;
  m_cmtsData.nonkeyPart->ipAddressType = dbCmts.m_ipAddressType;
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCmts::axInternalCmts(axDbCmts & dbCmts)
{
  initAtInstantiation();

  init(dbCmts);
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCmts::axInternalCmts(axDbCmts & dbCmts, 
                                   axDbAbstractCurrentCounts & counts)
{
  initAtInstantiation();

  init(dbCmts);

  m_cmtsData.nonkeyPart->currentCounts.cm.total = counts.m_totalCm;
  m_cmtsData.nonkeyPart->currentCounts.cm.online = counts.m_onlineCm;
  m_cmtsData.nonkeyPart->currentCounts.mta.total = counts.m_totalMta;
  m_cmtsData.nonkeyPart->currentCounts.mta.available = counts.m_availableMta;
  m_cmtsData.nonkeyPart->currentCounts.lastLogTime = counts.m_timeSec;
}


//********************************************************************
// method: 
//********************************************************************
int
axInternalCmts::keyCompare(axObject * o)
{
  int ret;

  axInternalCmts * b = dynamic_cast<axInternalCmts *> (o);

  // printf("resA=%d   resB=%d\n", getResId(), b->getResId());

  ret = getResId() - b->getResId();

  // printf("ret = %d\n", ret);

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
INTDS_RESID_t
axInternalCmts::getResId(void)
{
  INTDS_RESID_t ret;

  /**
   * No need to lock as read will happen in on CPU cycle (native data type)
   */

  ret = m_cmtsData.keyPart.resId;

  return (ret);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axInternalCmts::isEqual(axObject * o)
{
  bool ret = false;

  assert(o != NULL);

  return (ret);
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axInternalCmts::hashCode(void)
{
  return ((AX_INT64) m_cmtsData.keyPart.resId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axInternalCmts::hashUInt32(void)
{
  return (m_cmtsData.keyPart.resId);
}


//********************************************************************
// method : 
//********************************************************************
synchronizedTable_t *
axInternalCmts::getChannelTable(void)
{
  return (&m_channelTable);
}


//********************************************************************
// method :
//********************************************************************
synchronizedTable_t *
axInternalCmts::getHfcTable(void)
{
  return (&m_hfcTable);
}


//********************************************************************
// method : 
//********************************************************************
synchronizedTable_t *
axInternalCmts::getCmTable(void)
{
  return (&m_cmTable);
}


//********************************************************************
// method : 
//********************************************************************
synchronizedTable_t *
axInternalCmts::getMtaTable(void)
{
  return (&m_mtaTable);
}


//********************************************************************
// method : 
//********************************************************************
axIntKey_t *
axInternalCmts::getKey(void)
{
  return (&m_cmtsData.keyPart);
}


//********************************************************************
// method :
//********************************************************************
axIntNonkey_t *
axInternalCmts::getNonKey(void)
{
  return (m_cmtsData.nonkeyPart);
}


//********************************************************************
// method :
//********************************************************************
axAbstractLock *
axInternalCmts::getLock(void)
{
  return (&m_cmtsData.lock);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::compareAndUpdate(axObject * o)
{
  static const char * myName="intCmtsCmpAndUpd:";

  bool ret = true;

  axDbCmts * dbCmts = dynamic_cast<axDbCmts *> (o);

  /* CMTS always has data */
#if 0
  if (!hasData()) {
    goto EXIT_LABEL;
  }
#endif

  copyIntFqdn(m_cmtsData.nonkeyPart->fqdn, dbCmts->m_fqdn);

#if 0
  if (strlen(dbCmts->m_ipAddress)) {
    ACE_DEBUG((LM_CRITICAL, "%s, ip=%s\n", myName,
                                                dbCmts->m_ipAddress));
    assert(strlen(dbCmts->m_ipAddress)==0);
  }

  if (strlen(m_cmtsData.nonkeyPart->ipAddress)) {
    ACE_DEBUG((LM_CRITICAL, "%s, ip=%s\n", myName,
                                   m_cmtsData.nonkeyPart->ipAddress));
    assert(strlen(m_cmtsData.nonkeyPart->ipAddress)==0);
  }
#endif

  copyIntIpAddress(m_cmtsData.nonkeyPart->ipAddress, 
                                                 dbCmts->m_ipAddress);

#if 0
  if (strlen(m_cmtsData.nonkeyPart->ipAddress)) {
    ACE_DEBUG((LM_CRITICAL, "%s, ip=%s\n", myName,
                                   m_cmtsData.nonkeyPart->ipAddress));
    assert(strlen(m_cmtsData.nonkeyPart->ipAddress)==0);
  }
#endif

#if 0
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->cmtsReadCommunity,
                                         dbCmts->m_cmtsReadCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->cmReadCommunity,
                                           dbCmts->m_cmReadCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->cmWriteCommunity,
                                          dbCmts->m_cmWriteCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->mtaReadCommunity,
                                          dbCmts->m_mtaReadCommunity);
  copyIntSnmpCommunity(m_cmtsData.nonkeyPart->mtaWriteCommunity,
                                         dbCmts->m_mtaWriteCommunity);
#endif

  m_cmtsData.nonkeyPart->ipAddressType = dbCmts->m_ipAddressType;

// EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::isDocsis20Capable(void)
{
  bool ret;

  if (m_cmtsData.nonkeyPart->docsisCapability == DOCSIS_CAPABILITY_2_0) {
    ret = true;
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axInternalCmts::setDocsisCapability(AX_INT32 cap)
{
  m_cmtsData.nonkeyPart->docsisCapability = (AX_UINT8) cap;
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::hasData(void)
{
  return (m_cmtsData.hasData());
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::isAddable(void)
{
  return ((m_cmtsData.keyPart.resId ? true : false));
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::isHfcAlarmDetectionInProgress(void)
{
  bool ret;

  ret = 
    m_cmtsData.nonkeyPart->hfcAlarmDetectionInProgress ? true : false;

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axInternalCmts::setHfcAlarmDetectionInProgress(void)
{
  m_cmtsData.nonkeyPart->hfcAlarmDetectionInProgress = 1;
}


//********************************************************************
// method :
//********************************************************************
void
axInternalCmts::clearHfcAlarmDetectionInProgress(void)
{
  m_cmtsData.nonkeyPart->hfcAlarmDetectionInProgress = 0;
}


//********************************************************************
// method :
//********************************************************************
axInternalCm *
axInternalCmts::findCm(AX_INT8 * cmMac)
{
  axInternalCm * ret;

  synchronizedTable_t * syncCmTable = getCmTable();
  axReadLockOperator rL(syncCmTable->lock);
  ret = this->findCmExtLock(cmMac);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axInternalCm *
axInternalCmts::findCmExtLock(AX_INT8 * cmMac)
{
  axInternalCm * ret;

  axInternalCm tmpCm(cmMac);
  synchronizedTable_t * syncCmTable = getCmTable();
  axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (syncCmTable->table);
  ret = (axInternalCm *) cmTbl->find(&tmpCm);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::findHfc(axIntHfcKey_t & hfcKey)
{
  axObject * ret;

  synchronizedTable_t * syncHfcTable = getHfcTable();
  axReadLockOperator rLock(syncHfcTable->lock);
  ret = this->findHfcExtLock(hfcKey);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::findHfcExtLock(axIntHfcKey_t & hfcKey)
{
  axObject * ret;

  synchronizedTable_t * syncHfcTable = getHfcTable();
  axAvlTree * hfcTbl = dynamic_cast<axAvlTree *> (syncHfcTable->table);
  axInternalHfc tmpHfc(hfcKey);
  ret = hfcTbl->find(&tmpHfc);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::findChannelExtLock(axIntChannelKey_t & channelKey)
{
  axObject * ret;

  synchronizedTable_t * syncChannelTable = getChannelTable();
  axAvlTree * chnlTbl = dynamic_cast<axAvlTree *>
                                            (syncChannelTable->table);
  axInternalChannel tmpIntChnl(channelKey);
  ret = chnlTbl->find(&tmpIntChnl);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::findChannel(axIntChannelKey_t & channelKey)
{
  axObject * ret;

  synchronizedTable_t * syncChannelTable = getChannelTable();
  axReadLockOperator rLock(syncChannelTable->lock);
  ret = this->findChannelExtLock(channelKey);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::findMtaExtLock(AX_INT8 * mtaMac)
{
  axObject * ret;

  synchronizedTable_t * syncMtaTable = getMtaTable();
  axAvlTree * mtaTbl = dynamic_cast<axAvlTree *>
                                               (syncMtaTable->table);
  axInternalEmta tmpIntMta(mtaMac);
  ret = mtaTbl->find(&tmpIntMta);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::findMta(AX_INT8 * mtaMac)
{
  axObject * ret;

  synchronizedTable_t * syncMtaTable = getMtaTable();
  axReadLockOperator rLock(syncMtaTable->lock);
  ret = this->findMtaExtLock(mtaMac);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::canDoCounts(void)
{
  bool ret = false;

  axIntCmtsNonkey_t * cmtsNonkey = (axIntCmtsNonkey_t *) getNonKey();

  if (!cmtsNonkey->cmStatusPoll ||
      !cmtsNonkey->mtaPoll ||
      !cmtsNonkey->mtaPing) {
    goto EXIT_LABEL;
  }

  ret = !isDeleted();

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::isDeleted(void)
{
  bool ret;

  ret = m_cmtsData.nonkeyPart->isDeleted;

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axInternalCmts::setDeleted(bool isDeleted)
{
  m_cmtsData.nonkeyPart->isDeleted = (isDeleted ? 1 : 0);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCmts::isReconciled(void)
{
  bool ret;

  ret = m_cmtsData.nonkeyPart->isReconciled;

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axInternalCmts::setReconciled(bool isReconciled)
{
  m_cmtsData.nonkeyPart->isReconciled = (isReconciled ? 1 : 0);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addHfc(axObject * o)
{
  synchronizedTable_t * syncHfcTable = getHfcTable();
  axWriteLockOperator rLock(syncHfcTable->lock);
  return(this->addHfcExtLock(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addHfcExtLock(axObject * o)
{
  synchronizedTable_t * syncHfcTable = getHfcTable();
  axAvlTree * hfcTbl = dynamic_cast<axAvlTree *> (syncHfcTable->table);
  return(hfcTbl->add(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addMtaExtLock(axObject * o)
{
  synchronizedTable_t * syncMtaTable = getMtaTable();
  axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> (syncMtaTable->table);
  return(mtaTbl->add(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addMta(axObject * o)
{
  synchronizedTable_t * syncMtaTable = getMtaTable();
  axWriteLockOperator wLock(syncMtaTable->lock);
  return(this->addMtaExtLock(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addCmExtLock(axObject * o)
{
  synchronizedTable_t * syncCmTable = getCmTable();
  axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (syncCmTable->table);
  return(cmTbl->add(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addCm(axObject * o)
{
  synchronizedTable_t * syncCmTable = getCmTable();
  axWriteLockOperator wLock(syncCmTable->lock);
  return(this->addCmExtLock(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::addCountsMessage(axObject * o)
{
  return (m_countsMessages.add(o));
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalCmts::removeCountsMessage(void)
{
  return (m_countsMessages.remove());
}


