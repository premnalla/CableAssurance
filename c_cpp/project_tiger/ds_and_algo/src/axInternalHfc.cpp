
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axInternalHfc.hpp"
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
axInternalHfc::initAtInstantiation(void)
{
}


//********************************************************************
// method : helper
//********************************************************************
void
axInternalHfc::init(axDbHfc & dbHfc)
{
  struct timeval tm;
  gettimeofday(&tm, NULL);
  copyIntHfcName(m_hfcData.keyPart.name, dbHfc.m_hfcName);
  m_hfcData.nonkeyPart = new axIntHfcNonkey_t();
  m_hfcData.nonkeyPart->resId = dbHfc.m_hfcResId;
  // m_hfcData.nonkeyPart->nextPercentAlmStartTm = tm.tv_sec;
  // m_hfcData.nonkeyPart->nextPowerAlmStartTm = tm.tv_sec;
}


//********************************************************************
// default constructor:
//********************************************************************
axInternalHfc::axInternalHfc() :
  m_hfcData()
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axInternalHfc::~axInternalHfc()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalHfc::axInternalHfc(axDbHfc & dbHfc)
{
  // initAtInstantiation();

  init(dbHfc);
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalHfc::axInternalHfc(axDbHfc & dbHfc,
        axDbAbstractCurrentCounts & counts, axDbHfcCurrentStatus & st, 
                                              axDbHfcCurrentTca & tca)
{
  // initAtInstantiation();

  init(dbHfc);

  m_hfcData.nonkeyPart->currentCounts.cm.total = counts.m_totalCm;
  m_hfcData.nonkeyPart->currentCounts.cm.online = counts.m_onlineCm;
  m_hfcData.nonkeyPart->currentCounts.mta.total = counts.m_totalMta;
  m_hfcData.nonkeyPart->currentCounts.mta.available =
                                                counts.m_availableMta;
  m_hfcData.nonkeyPart->currentCounts.lastLogTime = counts.m_timeSec;
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalHfc::axInternalHfc(axIntHfcKey_t & k) :
  m_hfcData(k)
{
  // initAtInstantiation();
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalHfc::axInternalHfc(AX_INT8 * hfcName)
{
  // initAtInstantiation();

  copyIntHfcName(m_hfcData.keyPart.name, hfcName);
}


//********************************************************************
// method: 
//********************************************************************
int
axInternalHfc::keyCompare(axObject * o)
{
  int ret;

  axInternalHfc * b = dynamic_cast<axInternalHfc *> (o);

  ret = strcmp(getHfcName(),b->getHfcName());

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
AX_INT8 *
axInternalHfc::getHfcName(void)
{
  return (m_hfcData.keyPart.name);
}


//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axInternalHfc::getResId(void)
{
  INTDS_RESID_t ret = 0;

  if (m_hfcData.nonkeyPart) {
    ret = m_hfcData.nonkeyPart->resId;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalHfc::addCmExtLock(axObject * in)
{
  if (m_hfcData.nonkeyPart) {
    m_hfcData.nonkeyPart->cmList.add(in);
  }
  return (in);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalHfc::removeCmExtLock(axObject * in)
{
  axObject * ret;

  if (m_hfcData.nonkeyPart) {
    ret = m_hfcData.nonkeyPart->cmList.remove(in);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalHfc::addMtaExtLock(axObject * in)
{
  if (m_hfcData.nonkeyPart) {
    m_hfcData.nonkeyPart->mtaList.add(in);
  }
  return (in);
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axInternalHfc::getCableModems(void)
{
  axAbstractIterator * ret;

  if (m_hfcData.nonkeyPart) {
    ret = m_hfcData.nonkeyPart->cmList.createIterator();
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axInternalHfc::getMTAs(void)
{
  axAbstractIterator * ret;

  if (m_hfcData.nonkeyPart) {
    ret = m_hfcData.nonkeyPart->mtaList.createIterator();
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalHfc::findMta(axObject * o)
{
  axObject * ret;
  axReadLockOperator rLock(getLock());
  ret = this->findMtaExtLock(o);
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalHfc::findMtaExtLock(axObject * o)
{
  axObject * ret;

  if (m_hfcData.nonkeyPart) {
    ret = m_hfcData.nonkeyPart->mtaList.find(o);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method : hashString
//********************************************************************
auto_ptr<string>
axInternalHfc::hashString(void)
{
  auto_ptr<string> str(new string(m_hfcData.keyPart.name));
  return (str);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axInternalHfc::isEqual(axObject * o)
{
  bool ret = false;

#if 0
  auto_ptr<string> str1 = hashString();
  auto_ptr<string> str2 = o->hashString();

  if (!strcmp(str1->c_str(), str2->c_str())) {
    ret = true;
  }
#endif

  if (!isKeyEqual(o)) {
    return (ret);
  }

  /**** NEED to COMPARE other ATTRS as well, not just the KEY fields ***/

  return (ret);
}


//********************************************************************
// method: isKeyEqual
//********************************************************************
bool
axInternalHfc::isKeyEqual(axObject * o)
{
  bool ret;

#if 0
  auto_ptr<string> str1 = hashString();
  auto_ptr<string> str2 = o->hashString();

  if (!strcmp(str1->c_str(), str2->c_str())) {
    ret = true;
  } else {
    ret = false;
  }
#endif

  axInternalHfc * b = dynamic_cast<axInternalHfc *> (o);

  if (!strcmp(getHfcName(),b->getHfcName())) {
    ret = true;
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
//
//********************************************************************
axIntKey_t *
axInternalHfc::getKey(void)
{
  return (&m_hfcData.keyPart);
}


//********************************************************************
//
//********************************************************************
axIntNonkey_t *
axInternalHfc::getNonKey(void)
{
  return (m_hfcData.nonkeyPart);
}


//********************************************************************
//
//********************************************************************
axAbstractLock *
axInternalHfc::getLock(void)
{
  return (&m_hfcData.lock);
}


//********************************************************************
//
//********************************************************************
bool
axInternalHfc::isAddable(void)
{
  return ((m_hfcData.keyPart.name[0] != '\0' ? true : false));
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalHfc::hasData(void)
{
  // return ((m_hfcData.nonkeyPart ? true : false));
  return (m_hfcData.hasData());
}


#if 0
//********************************************************************
// method :
//********************************************************************
time_t
axInternalHfc::getPowerAlarmDetectStartTime(void)
{
  time_t ret = 0;

  if (hasData()) {
    struct timeval currTm;
    gettimeofday(&currTm, NULL);
    time_t t = currTm.tv_sec - (60*60*12);
    if (m_hfcData.nonkeyPart->nextPowerAlmStartTm < t) {
      ret = t;
      goto EXIT_LABEL;
    }

    ret = m_hfcData.nonkeyPart->nextPowerAlmStartTm;
  }

EXIT_LABEL:

  return (ret);
}
#endif


#if 0
//********************************************************************
// method :
//********************************************************************
time_t
axInternalHfc::getPercentAlarmDetectStartTime(void)
{
  time_t ret = 0;

  if (hasData()) {
    struct timeval currTm;
    gettimeofday(&currTm, NULL);
    time_t t = currTm.tv_sec - (60*60*12);
    if (m_hfcData.nonkeyPart->nextPercentAlmStartTm < t) {
      ret = t;
    } else {
      ret = m_hfcData.nonkeyPart->nextPercentAlmStartTm;
    }
  }

  return (ret);
}
#endif


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalHfc::addCm(axObject * o)
{
  axWriteLockOperator wL(getLock());
  axObject * ret = addCmExtLock(o);
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalHfc::findCmExtLock(axObject * o)
{
  axObject * ret;

  if (m_hfcData.nonkeyPart) {
    ret = m_hfcData.nonkeyPart->cmList.find(o);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axObject *
axInternalHfc::findCm(axObject * o)
{
  axReadLockOperator rL(getLock());
  axObject * ret = findCmExtLock(o);
  return (ret);
}


