
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpVerSpecificAttrSetter.hpp"
#include "axDbSnmpV2CAttrs.hpp"
// #include "axDbSnmpV3Attrs.hpp"

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
axSnmpVerSpecificAttrSetter::axSnmpVerSpecificAttrSetter() :
  m_tmplSession(NULL), m_dbSnmpVerSpecAttrs(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpVerSpecificAttrSetter::~axSnmpVerSpecificAttrSetter()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axSnmpVerSpecificAttrSetter::axSnmpVerSpecificAttrSetter(
                  axSnmpTemplateSession * t, axAbsDbSnmpVerAttrs * s) :
  m_tmplSession(t), m_dbSnmpVerSpecAttrs(s)
{
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpVerSpecificAttrSetter::setAttributes(void)
{
  axDbSnmpV2CAttrs * v2cAttrs = dynamic_cast<axDbSnmpV2CAttrs *> 
                                               (m_dbSnmpVerSpecAttrs);
  if (v2cAttrs) {
    setAttrs(v2cAttrs);
  }
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpVerSpecificAttrSetter::setIpAddress(AX_INT8 * ip)
{
  netsnmp_session * sess = m_tmplSession->getInternalSession();
  sess->peername = ip;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpVerSpecificAttrSetter::setAttrs(axDbSnmpV2CAttrs * dbAttrs)
{
  netsnmp_session * session = m_tmplSession->getInternalSession();
  session->version = SNMP_VERSION_2c;
  session->community = (AX_UINT8 *) dbAttrs->m_readCommunity;
  session->community_len = strlen(dbAttrs->m_readCommunity);

  // timeout in milli-seconds
  session->timeout = 5 * (10^6);

  session->retries = 0;
}


