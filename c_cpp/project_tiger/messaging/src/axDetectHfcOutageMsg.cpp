
//********************************************************************
// !!! OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDetectHfcOutageMsg.hpp"
#include "axGlobalData.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"

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
axDetectHfcOutageMsg::axDetectHfcOutageMsg() :
  m_cmtsResId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDetectHfcOutageMsg::~axDetectHfcOutageMsg()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDetectHfcOutageMsg::axDetectHfcOutageMsg(axInternalCmts * intCmts) :
  m_cmtsResId(intCmts->getResId())
{
}


//********************************************************************
// method:
//********************************************************************
bool 
axDetectHfcOutageMsg::timerCallback(void)
{
  bool ret = true;

  axIntCmtsKey_t tmpCmtsKey(m_cmtsResId);

  axInternalCmts * cmts = axGlobalData::getInstance()->findCmts(tmpCmtsKey);

  if (cmts) {

    {
      axWriteLockOperator cmtsWlock(cmts->getLock());

      if (cmts->isHfcOutageDetectionInProgress()) {
        goto EXIT_LABEL;
      }

      cmts->setHfcOutageDetectionInProgress();
    }

    // instantiate HFC outage detection for this CMTS
    // perhaps a runnable collection ...

  } // if


EXIT_LABEL:

  return (ret);
}


