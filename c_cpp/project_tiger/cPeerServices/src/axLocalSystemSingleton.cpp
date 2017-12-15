
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axLocalSystemSingleton.hpp"
#include "axDbLocalSystem.hpp"
#include "axMarketCPeerServices.hpp"
#include "axBladeCPeerServices.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axLocalSystemSingleton * axLocalSystemSingleton::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axLocalSystemSingleton::axLocalSystemSingleton() :
  m_locSysType(0)
{
  axDbLocalSystem dbLs;
  if (dbLs.getRow()) {
    m_locSysType = dbLs.m_systemType;
  }
}


//********************************************************************
// destructor:
//********************************************************************
axLocalSystemSingleton::~axLocalSystemSingleton()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axLocalSystemSingleton::axLocalSystemSingleton()
// {
// }


//********************************************************************
// method:
//********************************************************************
axLocalSystemSingleton *
axLocalSystemSingleton::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axLocalSystemSingleton();
  }
  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
axAbstractCPeerServices *
axLocalSystemSingleton::getCPeerServices(void)
{
  axAbstractCPeerServices * ret = NULL;

  switch (m_locSysType) {
    case DB_LOCAL_SYSTEM_MARKET:
      ret = new axMarketCPeerServices();
      break;
    case DB_LOCAL_SYSTEM_BLADE:
      ret = new axBladeCPeerServices();
      break;
    default:
      break;
  }

  return (ret);
}


