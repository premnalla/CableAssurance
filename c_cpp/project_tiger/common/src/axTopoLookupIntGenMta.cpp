
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axTopoLookupIntGenMta.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
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
// default constructor:
//********************************************************************
axTopoLookupIntGenMta::axTopoLookupIntGenMta()
{
}


//********************************************************************
// destructor:
//********************************************************************
axTopoLookupIntGenMta::~axTopoLookupIntGenMta()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axTopoLookupIntGenMta::axTopoLookupIntGenMta(AX_INT8 * mac) :
  m_gMtaData(mac)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axTopoLookupIntGenMta::axTopoLookupIntGenMta(axDbTopoLookupGenMta & db) :
  m_gMtaData(const_cast<AX_INT8 *>(db.m_strId.c_str()))
{
  m_gMtaData.nonkeyPart = new axTopoIntGenMtaNonkey_t();
  m_gMtaData.nonkeyPart->resId = db.m_resId;
  m_gMtaData.nonkeyPart->containerId = db.m_topoContainerId;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axTopoLookupIntGenMta::getMtaMacAddress(void)
{
  return (m_gMtaData.keyPart.mac);
}


//********************************************************************
// method : hashString
//********************************************************************
auto_ptr<string>
axTopoLookupIntGenMta::hashString(void)
{
  auto_ptr<string> str(new string(m_gMtaData.keyPart.mac));
  return (str);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axTopoLookupIntGenMta::isEqual(axObject * o)
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
axTopoLookupIntGenMta::isKeyEqual(axObject * o)
{
  bool ret;

  axTopoLookupIntGenMta * b = dynamic_cast<axTopoLookupIntGenMta *> (o);

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
axTopoLookupIntGenMta::getKey(void)
{
  return (&m_gMtaData.keyPart);
}


//********************************************************************
//
//********************************************************************
axIntNonkey_t *
axTopoLookupIntGenMta::getNonKey(void)
{
  return (m_gMtaData.nonkeyPart);
}


//********************************************************************
//
//********************************************************************
axAbstractLock *
axTopoLookupIntGenMta::getLock(void)
{
  return (&m_gMtaData.lock);
}


//********************************************************************
//
//********************************************************************
bool
axTopoLookupIntGenMta::isAddable(void)
{
  return ((isValidMacLen(m_gMtaData.keyPart.mac) ? true : false));
}


//********************************************************************
// method:
//********************************************************************
int
axTopoLookupIntGenMta::keyCompare(axObject * o)
{
  int ret;

  axTopoLookupIntGenMta * b = dynamic_cast<axTopoLookupIntGenMta *> (o);

  ret = strcmp(getMtaMacAddress(),b->getMtaMacAddress());

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axTopoLookupIntGenMta::hasData(void)
{
  return (m_gMtaData.hasData());
}


//********************************************************************
// method :
//********************************************************************
INTDS_RESID_t
axTopoLookupIntGenMta::getResId(void)
{
  INTDS_RESID_t ret;

  if (hasData()) {
    ret = m_gMtaData.nonkeyPart->resId;
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
AX_UINT16
axTopoLookupIntGenMta::getContainerId(void)
{
  AX_UINT16 ret;
  if (hasData()) {
    ret = m_gMtaData.nonkeyPart->containerId;
  } else {
    ret = 0;
  }
  return (ret);
}


