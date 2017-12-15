
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axTFMtaTrapObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
const char * axTFMtaTrapObject::MTA_TRAP_OBJ_XML_NAME = "mtaTrap";
const char * axTFMtaTrapObject::MAX_EVENT_INDEX_XML_NAME = "eventIndex";
const char * axTFMtaTrapObject::MAX_EVENT_LEVEL_XML_NAME = "eventLevel";
const char * axTFMtaTrapObject::MAX_EVENT_ENT_XML_NAME = "eventEnterprise";
const char * axTFMtaTrapObject::MAX_EVENT_ID_XML_NAME = "eventId";
const char * axTFMtaTrapObject::MAX_EVENT_TEXT_XML_NAME = "eventText";
const char * axTFMtaTrapObject::MAX_EVENT_MAC_XML_NAME = "eventMac";
const char * axTFMtaTrapObject::MAX_EVENT_ENDPOINT_XML_NAME = "eventEndpoint";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axTFMtaTrapObject::axTFMtaTrapObject()
{
}


//********************************************************************
// destructor:
//********************************************************************
axTFMtaTrapObject::~axTFMtaTrapObject()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTFMtaTrapObject::axTFMtaTrapObject()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventIndex(AX_INT8 * in)
{
  strncpy(m_eventIndex, in, MAX_EVENT_INDEX_LEN);
  m_eventIndex[MAX_EVENT_INDEX_LEN-1] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventLevel(AX_INT8 * in)
{
  strncpy(m_eventLevel, in, MAX_EVENT_LEVEL_LEN);
  m_eventLevel[MAX_EVENT_LEVEL_LEN-1] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventEnterprise(AX_INT8 * in)
{
  strncpy(m_eventEnterprise, in, MAX_EVENT_ENT_LEN);
  m_eventEnterprise[MAX_EVENT_ENT_LEN-1] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventId(AX_INT8 * in)
{
  strncpy(m_eventId, in, MAX_EVENT_ID_LEN);
  m_eventId[MAX_EVENT_ID_LEN-1] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventText(AX_INT8 * in)
{
  strncpy(m_eventText, in, MAX_EVENT_TEXT_LEN);
  m_eventText[MAX_EVENT_TEXT_LEN-1] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventMac(AX_INT8 * in)
{
  strncpy(m_mac, in, MAX_EVENT_MAC_LEN);
  m_mac[MAX_EVENT_MAC_LEN-1] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axTFMtaTrapObject::setEventEndpoint(AX_INT8 * in)
{
  strncpy(m_eventEndpoint, in, MAX_EVENT_ENDPOINT_LEN);
  m_eventEndpoint[MAX_EVENT_ENDPOINT_LEN-1] = '\0';
}


//********************************************************************
// method:
// <x>
//   <eventIndex>...</eventIndex>
//               ...
// </x>
// OR
// <x eventIndex="value"
//    ... >
// </x>
//********************************************************************
bool
axTFMtaTrapObject::toXML(AX_INT8 * retBuf, AX_UINT32 bufLen)
{
  bool ret = true;

  AX_INT8 * s = retBuf;
  AX_UINT32 l = 0;

  sprintf(retBuf, "<%s %s=\"%s\" "
                      "%s=\"%s\" "
                      "%s=\"%s\" "
                      "%s=\"%s\" "
                      "%s=\"%s\" "
                      "%s=\"%s\" "
                      "%s=\"%s\" "
                  "></%s>",
           MTA_TRAP_OBJ_XML_NAME,
           MAX_EVENT_INDEX_XML_NAME, m_eventIndex,
           MAX_EVENT_LEVEL_XML_NAME, m_eventLevel,
           MAX_EVENT_ENT_XML_NAME, m_eventEnterprise,
           MAX_EVENT_ID_XML_NAME, m_eventId,
           MAX_EVENT_TEXT_XML_NAME, m_eventText,
           MAX_EVENT_MAC_XML_NAME, m_mac,
           MAX_EVENT_ENDPOINT_XML_NAME, m_eventEndpoint,
           MTA_TRAP_OBJ_XML_NAME
         );

  return (ret);
}


