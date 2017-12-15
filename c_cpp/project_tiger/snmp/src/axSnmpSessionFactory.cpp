
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axNetSnmpDataTypes.h"
#include "axSnmpSessionFactory.hpp"
#include "axCASystemConfig.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axSnmpSessionFactory * axSnmpSessionFactory::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSnmpSessionFactory::axSnmpSessionFactory()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpSessionFactory::~axSnmpSessionFactory()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSnmpSessionFactory::axSnmpSessionFactory()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSnmpSessionFactory *
axSnmpSessionFactory::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSnmpSessionFactory();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
axSnmpSession *
axSnmpSessionFactory::createSession(axSnmpTemplateSession * ts)
{
#if 1
  static const char * myName="createSnmpSession:";

  axSnmpSession * ret;

  netsnmp_session * templSession = ts->getInternalSession();
  netsnmp_session_list * realSessionList = (netsnmp_session_list *) 
                                         snmp_sess_open(templSession);

#if 1
  ACE_DEBUG((LM_SNMP_DEBUG, "%s ip:%s community:%s\n", myName,
                   templSession->peername, templSession->community));
#endif

  if (realSessionList) {
    ret = new axSnmpSession(realSessionList);
    ACE_DEBUG((LM_SNMP_DEBUG, "%s created session\n", myName));
  } else {
    ret = NULL;
    ACE_DEBUG((LM_SNMP_DEBUG, "%s unable to create session\n", myName));
  }

#if 0 /* only to check for overhead in shared resource locking */
  netsnmp_pdu * req = snmp_pdu_create(SNMP_MSG_GETNEXT);
  snmp_free_pdu(req);
#endif

  return (ret);
#else
  return (NULL);
#endif
}


