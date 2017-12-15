
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axInternalEmta.hpp"
#include "axGlobalData.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axPingDataTypes.hpp"
#include "axPinger.hpp"
#include "axAlarmDataTypes.hpp"
#include "axMtaAlarmUnavail.hpp"
#include "axAlarmProcessingMsg.hpp"
#include "axMessageManager.hpp"
#include "axSubSystemCommon.hpp"
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
axInternalEmta::initAtInstantiation(void)
{
}

//********************************************************************
// method : helper
//********************************************************************
void
axInternalEmta::init(axDbEmta & dbEmta, axInternalCmts * intCmts)
{
  copyIntMac(m_emtaData.keyPart.mac, dbEmta.m_mtaMac);

  m_emtaData.nonkeyPart = new axIntEmtaNonkey_t();
  m_emtaData.nonkeyPart->pingState = dbEmta.m_pingState;
  m_emtaData.nonkeyPart->provState = dbEmta.m_provState;
  m_emtaData.nonkeyPart->available = dbEmta.m_available;
  m_emtaData.nonkeyPart->resId = dbEmta.m_mtaResId;
  copyIntIpAddress(m_emtaData.nonkeyPart->ipAddress,dbEmta.m_ipAddress);
  m_emtaData.nonkeyPart->ipAddressType = dbEmta.m_ipAddressType;

  if (intCmts) {
    axInternalCm * intCm = intCmts->findCm(dbEmta.m_cm.m_cmMac);
    if (intCm) {
      m_emtaData.nonkeyPart->cmPtr = intCm;
    }
  }

  /**
   * Use the CM Mac to locate the CM and associate it with this eMTA
   */
}


//********************************************************************
// default constructor:
//********************************************************************
axInternalEmta::axInternalEmta()
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axInternalEmta::~axInternalEmta()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalEmta::axInternalEmta(AX_INT8 * cmMac, AX_INT8 * mtaMac)
{
  // initAtInstantiation();

  copyIntMac(m_emtaData.keyPart.mac, mtaMac);

  /**
   * Use the CM Mac to locate the CM and associate it with this eMTA
   */
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalEmta::axInternalEmta(AX_INT8 * mtaMac)
{
  // initAtInstantiation();

  copyIntMac(m_emtaData.keyPart.mac, mtaMac);

}


//********************************************************************
// data constructor:
//********************************************************************
axInternalEmta::axInternalEmta(axIntEmtaKey_t & k) :
  m_emtaData(k)
{
  // initAtInstantiation();
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalEmta::axInternalEmta(axDbEmta & dbEmta, 
                                             axInternalCmts * intCmts)
{
  // initAtInstantiation();

  init(dbEmta, intCmts);
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalEmta::axInternalEmta(axDbEmta & dbEmta, 
            axDbMtaCurrentAvailability & st, axInternalCmts * intCmts)
{
  // initAtInstantiation();

  init(dbEmta, intCmts);

  m_emtaData.nonkeyPart->lastAvailLogTime = st.m_lastLogTime;
  m_emtaData.nonkeyPart->availChangeTime = st.m_lastStateChangeTime;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axInternalEmta::getMtaMacAddress(void)
{
  return (m_emtaData.keyPart.mac);
}


//********************************************************************
// method : hashString
//********************************************************************
auto_ptr<string>
axInternalEmta::hashString(void)
{
  auto_ptr<string> str(new string(m_emtaData.keyPart.mac));
  return (str);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axInternalEmta::isEqual(axObject * o)
{
  bool ret = false;

  if (!isKeyEqual(o)) {
    return (ret);
  }

  /** NEED TO COMPARE OTHER ATTRS OF THIS OBJECT AS WELL **/

  return (ret);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axInternalEmta::isKeyEqual(axObject * o)
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

  axInternalEmta * b = dynamic_cast<axInternalEmta *> (o);

  if (!strcmp(getMtaMacAddress(),b->getMtaMacAddress())) {
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
axInternalEmta::getKey(void)
{
  return (&m_emtaData.keyPart);
}


//********************************************************************
//
//********************************************************************
axIntNonkey_t *
axInternalEmta::getNonKey(void)
{
  return (m_emtaData.nonkeyPart);
}


//********************************************************************
//
//********************************************************************
axAbstractLock *
axInternalEmta::getLock(void)
{
  return (&m_emtaData.lock);
}


#if 0
//********************************************************************
//
//********************************************************************
bool
axInternalEmta::isAddable(void)
{
  return ((isValidMacLen(m_emtaData.keyPart.mac) ? true : false));
}
#endif


#if 0
//********************************************************************
// method:
//********************************************************************
int
axInternalEmta::keyCompare(axObject * o)
{
  int ret;

  axInternalEmta * b = dynamic_cast<axInternalEmta *> (o);

  ret = strcmp(getMtaMacAddress(),b->getMtaMacAddress());

  return (ret);
}
#endif


//********************************************************************
// method : compareAndUpdate
// in     : other eMTA with Possibily changed attributes
// return :
//    true : updates done
//    false: no updates done
//********************************************************************
bool
axInternalEmta::compareAndUpdate(axObject * o, INTDS_RESID_t cmtsResId)
{
  bool ret = true;

  return (ret);
}


#if 0
//********************************************************************
// method : updateDb
// in     : 
// return :
//    true : 
//    false: 
// note/assumption(s) : a read lock is held by caller of this method
//********************************************************************
bool
axInternalEmta::updateDb(void)
{
  bool ret = false;

  axDbEmta dbEmta(getResId());

  if (!dbEmta.getRow()) {
    goto EXIT_LABEL;
  }

  axIntEmtaNonkey_t * eMtaNonkey = (axIntEmtaNonkey_t *) getNonKey();

  dbEmta.m_available = eMtaNonkey->available;
  dbEmta.m_availUnavailChangeTime = eMtaNonkey->availChangeTime;

  dbEmta.m_provState = eMtaNonkey->provState;
  dbEmta.m_isProvStatePass = axInternalGenMta::isProvStatePass(
                                            eMtaNonkey->provState);

  dbEmta.m_pingState = eMtaNonkey->pingState;
  dbEmta.m_isPingFailure = axInternalGenMta::isPingStateFailure(
                                            eMtaNonkey->pingState);

  copyIntIpAddress(dbEmta.m_ipAddress, eMtaNonkey->ipAddress);
  dbEmta.m_ipAddressType = eMtaNonkey->ipAddressType;

  ret = dbEmta.updateRow();

EXIT_LABEL:

  return (ret);
}
#endif


//********************************************************************
// data constructor:
//********************************************************************
bool
axInternalEmta::isEmta(void)
{
  return (true);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalEmta::hasData(void)
{
  return (m_emtaData.hasData());
}


#if 0
//********************************************************************
// method :
//********************************************************************
bool
axInternalEmta::isAvailable(void)
{
  return ((m_emtaData.nonkeyPart->available ? true : false));
}
#endif


//********************************************************************
// method :
//********************************************************************
axInternalCm *
axInternalEmta::getCm(void)
{
  return (m_emtaData.nonkeyPart->cmPtr);
}


//********************************************************************
// method :
//********************************************************************
INTDS_RESID_t
axInternalEmta::getResId(void)
{
  INTDS_RESID_t ret;

  if (hasData()) {
    ret = m_emtaData.nonkeyPart->resId;
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalEmta::isStatusOperational(void)
{
  bool ret = false;
  if (hasData()) {
    ret = (m_emtaData.nonkeyPart->available ? true : false);
  }
  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalEmta::isAlarmable(void)
{
  return (true);
}


//********************************************************************
// method:
//********************************************************************
bool
axInternalEmta::generateAlarm(AX_UINT8 code, INTDS_RESID_t cmtsResId,
                                            time_t alarmDetectionTime)
{
  bool ret = true;

  switch (code) {
    case AX_CORRELATED_ALARM_PERCENT:
    case AX_CORRELATED_ALARM_COUNT:
      {
        axMtaAlarmUnavail * alm = new 
                                    axMtaAlarmUnavail(cmtsResId, this);
        alm->setAlarmDetectionTime(alarmDetectionTime);
        axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                        AX_SS_TYPE_COUNTS_ALARMS, alm);
        axMessageManager::getInstance()->sendMessage(msg);
      }
      break;

    case AX_CORRELATED_ALARM_POWER:
      break;

    default:
      break;
  }

  return (ret);
}


