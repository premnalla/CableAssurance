
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbGenMta.hpp"
#include "axInternalGenMta.hpp"
#include "axReadLockOperator.hpp"
#include "axDbUtils.hpp"

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
// method:
//********************************************************************
void
axDbGenMta::initAtInstantiation(void)
{
  DB_UPDATE_TIME_t t = {0};
  m_lastUpdated = t;

  m_mtaMac[0] = '\0';
  m_fqdn[0] = '\0';
  m_ipAddress[0] = '\0';
}


//********************************************************************
// default constructor:
//********************************************************************
axDbGenMta::axDbGenMta() :
  m_id(0), m_mtaResId(0), m_cmsResId(0), 
  m_provState(0), m_isProvStatePass(0), 
  m_pingState(0), m_isPingFailure(0), 
  m_available(0), m_deleted(0),
  m_ipAddressType(DB_IPADDR_TYPE_IPv4),
  m_alertLevel(0)
{
  initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axDbGenMta::~axDbGenMta()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbGenMta::axDbGenMta(DB_RESID_t mtaResId) :
  m_id(0), m_mtaResId(mtaResId), m_cmsResId(0), 
  m_provState(0), m_isProvStatePass(0),
  m_pingState(0), m_isPingFailure(0),
  m_available(0), m_deleted(0),
  m_ipAddressType(DB_IPADDR_TYPE_IPv4),
  m_alertLevel(0)
{
  initAtInstantiation();
}


#if 0
//********************************************************************
// data constructor:
//********************************************************************
axDbGenMta::axDbGenMta(DB_MAC_t mtaMac, DB_RESID_t cmtsResId) :
  m_id(0), m_mtaResId(0), m_cmsResId(0),
  m_provState(0), m_isProvStatePass(0),
  m_pingState(0), m_isPingFailure(0),
  m_available(0), m_deleted(0),
  m_ipAddressType(DB_IPADDR_TYPE_IPv4),
  m_alertLevel(0)
{
  initAtInstantiation();
  copyDbMac(m_mtaMac, mtaMac);
}
#endif


//********************************************************************
// data constructor:
//********************************************************************
axDbGenMta::axDbGenMta(axInternalGenMta * intMta) :
  m_id(0), m_mtaResId(0), m_cmsResId(0),
  m_provState(0), m_isProvStatePass(0),
  m_pingState(0), m_isPingFailure(0),
  m_available(0), m_deleted(0),
  m_ipAddressType(DB_IPADDR_TYPE_IPv4),
  m_alertLevel(0)
{
  initAtInstantiation();
  copyDbMac(m_mtaMac, intMta->getMtaMacAddress());
  {
    axReadLockOperator rLock(intMta->getLock());

    axIntGenMtaNonkey_t * mtaNk = (axIntGenMtaNonkey_t *) 
                                                  intMta->getNonKey();
    if (mtaNk) {
      // m_availUnavailChangeTime = mtaNk->availChangeTime;
      copyDbIpAddress(m_ipAddress, mtaNk->ipAddress);
      m_ipAddressType = mtaNk->ipAddressType;
      m_pingState = mtaNk->pingState;
      m_provState = mtaNk->provState;
      m_available = mtaNk->available;

      m_isProvStatePass = 
                  axInternalGenMta::isProvStatePass(mtaNk->provState);
      m_isPingFailure = 
                  axInternalGenMta::isPingStateFailure(mtaNk->pingState);
    }
  }

}


//********************************************************************
// data constructor:
//********************************************************************
axDbGenMta::axDbGenMta(const axDbGenMta & in) :
  axAbstractDbObject(),
  m_id(in.m_id), m_mtaResId(in.m_mtaResId), m_cmsResId(in.m_cmsResId),
  m_provState(in.m_provState), m_isProvStatePass(in.m_isProvStatePass),
  m_pingState(in.m_pingState), m_isPingFailure(in.m_isPingFailure),
  m_available(in.m_available), m_deleted(in.m_deleted),
  m_ipAddressType(in.m_ipAddressType),
  m_alertLevel(in.m_alertLevel)
{
  initAtInstantiation();
  copyDbMac(m_mtaMac, in.m_mtaMac);
  copyDbFqdn(m_fqdn, in.m_fqdn);
  copyDbIpAddress(m_ipAddress, in.m_ipAddress);
  m_lastUpdated = in.m_lastUpdated;
}


//********************************************************************
// method:
//********************************************************************

