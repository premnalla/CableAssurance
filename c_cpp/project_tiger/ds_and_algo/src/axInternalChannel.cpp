
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCblAssureConstants.hpp"
#include "axInternalChannel.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalUtils.hpp"
#include "axListIterator.hpp"

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
axInternalChannel::initAtInstantiation(void)
{
}


//********************************************************************
// method : helper
//********************************************************************
void
axInternalChannel::init(axDbChannel & dbChannel)
{
  copyIntChannelName(m_channelData.keyPart.name,
                                             dbChannel.m_channelName);
  m_channelData.nonkeyPart = new axIntChannelNonkey_t();
  m_channelData.nonkeyPart->resId = dbChannel.m_channelResId;
  m_channelData.nonkeyPart->channelIndex = dbChannel.m_channelIndex;
  m_channelData.nonkeyPart->channelType = dbChannel.m_channelType;
}


//********************************************************************
// default constructor:
//********************************************************************
axInternalChannel::axInternalChannel() :
  m_channelData()
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axInternalChannel::~axInternalChannel()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalChannel::axInternalChannel(axDbChannel & dbChannel)
{
  // initAtInstantiation();

  init(dbChannel);
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalChannel::axInternalChannel(axDbChannel & dbChannel,
                                   axDbAbstractCurrentCounts & counts)
{
  // initAtInstantiation();

  init(dbChannel);

  m_channelData.nonkeyPart->currentCounts.cm.total = counts.m_totalCm;
  m_channelData.nonkeyPart->currentCounts.cm.online = counts.m_onlineCm;
  m_channelData.nonkeyPart->currentCounts.mta.total = counts.m_totalMta;
  m_channelData.nonkeyPart->currentCounts.mta.available = 
                                                counts.m_availableMta;
  m_channelData.nonkeyPart->currentCounts.lastLogTime = counts.m_timeSec;
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalChannel::axInternalChannel(
                                axSnmpCmtsChannelResultRow_s * snmpCm)
{
  // initAtInstantiation();

  copyIntChannelName(m_channelData.keyPart.name, 
                                               snmpCm->ifDescription);
  m_channelData.nonkeyPart = new axIntChannelNonkey_t();
  m_channelData.nonkeyPart->channelIndex = snmpCm->ifIndex;

  switch (snmpCm->ifType) {
    case IF_TYPE_DOWN_STREAM:
      m_channelData.nonkeyPart->channelType = 
                                        AX_CA_CHANNEL_TYPE_DOWNSTREAM;
      break;

    case IF_TYPE_UP_STREAM_1X:
    case IF_TYPE_UP_STREAM_2X:
      m_channelData.nonkeyPart->channelType = 
                                         AX_CA_CHANNEL_TYPE_UPSTREAM;
      break;

    default:
      m_channelData.nonkeyPart->channelType = 
                                          AX_CA_CHANNEL_TYPE_UNKNOWN;
      break;
  }

}


//********************************************************************
// data constructor:
//********************************************************************
axInternalChannel::axInternalChannel(axIntChannelKey_t & k) :
  m_channelData(k)
{
  // initAtInstantiation();
}


//********************************************************************
// data constructor:
//********************************************************************
axInternalChannel::axInternalChannel(AX_INT8 * channelName)
{
  // initAtInstantiation();

  copyIntChannelName(m_channelData.keyPart.name, channelName);
}


//********************************************************************
// method: 
//********************************************************************
AX_INT32
axInternalChannel::keyCompare(axObject * o)
{
  int ret;

  axInternalChannel * b = dynamic_cast<axInternalChannel *> (o);

  ret = strcmp(getChannelName(),b->getChannelName());

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
AX_INT8 *
axInternalChannel::getChannelName(void)
{
  return (m_channelData.keyPart.name);
}


//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axInternalChannel::getResId(void)
{
  INTDS_RESID_t ret = 0;

  if (m_channelData.nonkeyPart) {
    ret = m_channelData.nonkeyPart->resId;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalChannel::addCmExtLock(axObject * in)
{
  if (m_channelData.nonkeyPart) {
    m_channelData.nonkeyPart->cmList.add(in);
  }
  return (in);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalChannel::removeCmExtLock(axObject * in)
{
  axObject * ret;

  if (m_channelData.nonkeyPart) {
    ret = m_channelData.nonkeyPart->cmList.remove(in);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalChannel::addMtaExtLock(axObject * in)
{
  if (m_channelData.nonkeyPart) {
    m_channelData.nonkeyPart->mtaList.add(in);
  }
  return (in);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalChannel::findMtaExtLock(axObject * in)
{
  axObject * ret;

  if (m_channelData.nonkeyPart) {
    ret = m_channelData.nonkeyPart->mtaList.find(in);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axInternalChannel::getCableModems(void)
{
  axAbstractIterator * ret;

  if (m_channelData.nonkeyPart) {
    ret = m_channelData.nonkeyPart->cmList.createIterator();
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axInternalChannel::getMTAs(void)
{
  axAbstractIterator * ret;

  if (m_channelData.nonkeyPart) {
    ret = m_channelData.nonkeyPart->mtaList.createIterator();
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method : hashString
//********************************************************************
auto_ptr<string>
axInternalChannel::hashString(void)
{
  auto_ptr<string> str(new string(m_channelData.keyPart.name));
  return (str);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axInternalChannel::isEqual(axObject * o)
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
axInternalChannel::isKeyEqual(axObject * o)
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

  axInternalChannel * b = dynamic_cast<axInternalChannel *> (o);

  if (!strcmp(getChannelName(),b->getChannelName())) {
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
axInternalChannel::getKey(void)
{
  return (&m_channelData.keyPart);
}


//********************************************************************
//
//********************************************************************
axIntNonkey_t *
axInternalChannel::getNonKey(void)
{
  return (m_channelData.nonkeyPart);
}


//********************************************************************
//
//********************************************************************
axAbstractLock *
axInternalChannel::getLock(void)
{
  return (&m_channelData.lock);
}


//********************************************************************
//
//********************************************************************
bool
axInternalChannel::isAddable(void)
{
  return ((m_channelData.keyPart.name[0] != '\0' ? true : false));
}


//********************************************************************
//
//********************************************************************
bool
axInternalChannel::isUpstreamChannel(void)
{
  bool ret = false;

  if (m_channelData.nonkeyPart &&
      m_channelData.nonkeyPart->channelType == 
        AX_CA_CHANNEL_TYPE_UPSTREAM) {
    ret = true;
  }

  return (ret);
}


//********************************************************************
//
//********************************************************************
bool
axInternalChannel::isDownstreamChannel(void)
{
  bool ret = false;

  if (m_channelData.nonkeyPart &&
      m_channelData.nonkeyPart->channelType ==
        AX_CA_CHANNEL_TYPE_DOWNSTREAM) {
    ret = true;
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalChannel::hasData(void)
{
  return (m_channelData.hasData());
}


