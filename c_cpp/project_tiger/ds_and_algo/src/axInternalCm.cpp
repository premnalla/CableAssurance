
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axInternalCm.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalUtils.hpp"
#include "axDbCmStatusLog.hpp"

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
axInternalCm::initAtInstantiation(void)
{
}


//********************************************************************
// method : helper
//********************************************************************
void
axInternalCm::init(axDbCm & dbCm)
{
  copyIntMac(m_cmData.keyPart.mac, dbCm.m_cmMac);
  m_cmData.nonkeyPart = new axIntCmNonkey_t();
  // printf("CM: %s\n", m_cmData.keyPart.mac);

  m_cmData.nonkeyPart->resId = dbCm.m_cmResId;
  m_cmData.nonkeyPart->modemIndex = dbCm.m_modemIndex;
  m_cmData.nonkeyPart->upstreamChannelIndex =
                                          dbCm.m_upstreamChannelIndex;
  m_cmData.nonkeyPart->downstreamChannelIndex =
                                        dbCm.m_downstreamChannelIndex;
  m_cmData.nonkeyPart->ipAddressType = dbCm.m_ipAddressType;
  copyIntIpAddress(m_cmData.nonkeyPart->ipAddress, dbCm.m_ipAddress);
  m_cmData.nonkeyPart->docsisState = dbCm.m_docsisState;
  m_cmData.nonkeyPart->euDeviceType = dbCm.m_enduserDeviceType;
}


//********************************************************************
// default constructor:
//********************************************************************
axInternalCm::axInternalCm() :
  m_cmData()
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axInternalCm::~axInternalCm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCm::axInternalCm(axDbCm & dbCm)
{
  // initAtInstantiation();

  init(dbCm);
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCm::axInternalCm(axDbCm & dbCm, axDbCmCurrentStatus & st,
                                             axDbCurrentCmPerf & perf)
{
  // initAtInstantiation();

  init(dbCm);

  m_cmData.nonkeyPart->lastStateLogTime = st.m_lastLogTime;
  m_cmData.nonkeyPart->onlineChangeTime =
                                       st.m_lastStateChangeTime;

  m_cmData.nonkeyPart->lastPerfLogTime = perf.m_lastLogTime;
  m_cmData.nonkeyPart->downstreamSNR = perf.m_downstreamSnr;
  m_cmData.nonkeyPart->downstreamPower = perf.m_downstreamPower;
  m_cmData.nonkeyPart->upstreamPower = perf.m_upstreamPower;
  m_cmData.nonkeyPart->t1Timeouts = perf.m_t1Timeouts;
  m_cmData.nonkeyPart->t2Timeouts = perf.m_t2Timeouts;
  m_cmData.nonkeyPart->t3Timeouts = perf.m_t3Timeouts;
  m_cmData.nonkeyPart->t4Timeouts = perf.m_t4Timeouts;
}

//********************************************************************
// data constructor:
//********************************************************************
axInternalCm::axInternalCm(axSnmpCmtsCmResultRow_s * snmpCm)
{
  // initAtInstantiation();

  copyIntMac(m_cmData.keyPart.mac, snmpCm->mac);
  m_cmData.nonkeyPart = new axIntCmNonkey_t();
  m_cmData.nonkeyPart->modemIndex = snmpCm->modemIndex;
  m_cmData.nonkeyPart->upstreamChannelIndex = snmpCm->upstreamChannelIndex;
  m_cmData.nonkeyPart->downstreamChannelIndex = snmpCm->downstreamChannelIndex;
  m_cmData.nonkeyPart->docsisState = snmpCm->docsisState;
  // m_cmData.nonkeyPart->upstreamSNRatCmts = snmpCm->upstreamSNR;
  // m_cmData.nonkeyPart->upstreamPowerAtCmts = snmpCm->upstreamPower;
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCm::axInternalCm(axIntCmKey_t & k) :
  m_cmData(k)
{
  // initAtInstantiation();
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalCm::axInternalCm(AX_INT8 * cmMac)
{
  // initAtInstantiation();

  copyIntMac(m_cmData.keyPart.mac, cmMac);
}


//********************************************************************
// method: public static compare function
//********************************************************************
int
axInternalCm::CompareFunction(const void * a, const void * b, void * p)
{
  int ret;

  axInternalCm * cmA = (axInternalCm *) a;
  axInternalCm * cmB = (axInternalCm *) b;

#if 0
  printf("macA=%s		macB=%s\n", 
                          cmA->getMacAddress(), cmB->getMacAddress());
#endif
 
  ret = strcmp(cmA->getMacAddress(),cmB->getMacAddress());

  return (ret);
}



//********************************************************************
// method: 
//********************************************************************
int
axInternalCm::keyCompare(axObject * o)
{
  int ret;

  axInternalCm * b = dynamic_cast<axInternalCm *> (o);

#if 0
  printf("macA=%s		macB=%s\n", getMacAddress(), b->getMacAddress());
#endif

  ret = strcmp(getMacAddress(),b->getMacAddress());

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
AX_INT8 *
axInternalCm::getMacAddress(void)
{
  return (m_cmData.keyPart.mac);
}


//********************************************************************
// method : hashString
//********************************************************************
auto_ptr<string>
axInternalCm::hashString(void)
{
  auto_ptr<string> str(new string(m_cmData.keyPart.mac));
  return (str);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axInternalCm::isEqual(axObject * o)
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
axInternalCm::isKeyEqual(axObject * o)
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

  axInternalCm * b = dynamic_cast<axInternalCm *> (o);

  if (!strcmp(getMacAddress(),b->getMacAddress())) {
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
axInternalCm::getKey(void)
{
  return (&m_cmData.keyPart);
}


//********************************************************************
//
//********************************************************************
axIntNonkey_t *
axInternalCm::getNonKey(void)
{
  return (m_cmData.nonkeyPart);
}


//********************************************************************
//
//********************************************************************
axAbstractLock *
axInternalCm::getLock(void)
{
  return (&m_cmData.lock);
}


//********************************************************************
//
//********************************************************************
bool
axInternalCm::isAddable(void)
{
  return ((isValidMacLen(m_cmData.keyPart.mac) ? true : false));
}


//********************************************************************
//
//********************************************************************
bool
axInternalCm::isCmOnline(AX_UINT8 docsisState)
{
  return ((docsisState==DOCSIS_CM_STATE_ONLINE) ? true : false);
}


//********************************************************************
//
//********************************************************************
bool
axInternalCm::isOnline(void)
{
  bool ret;

  if (hasData()) {
    ret = (m_cmData.nonkeyPart->docsisState == DOCSIS_CM_STATE_ONLINE);
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCm::hasData(void)
{
  return (m_cmData.hasData());
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCm::isEmta(void)
{
  bool ret;

  if (hasData()) {
    ret = (m_cmData.nonkeyPart->euDeviceType == AX_INT_EU_DEVIE_TYPE_EMTA);
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCm::isCmEndUserDevice(void)
{
  bool ret;

  if (hasData()) {
    ret = (m_cmData.nonkeyPart->euDeviceType == AX_INT_EU_DEVIE_TYPE_CM);
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
INTDS_RESID_t
axInternalCm::getResId(void)
{
  INTDS_RESID_t ret;

  if (hasData()) {
    ret = m_cmData.nonkeyPart->resId;
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalCm::isStatusOperational(void)
{
  return (isOnline());
}



