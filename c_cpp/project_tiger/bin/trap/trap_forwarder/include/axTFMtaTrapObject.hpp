
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTFMtaTrapObject_hpp_
#define _axTFMtaTrapObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axTFAbstractTrapObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axTFMtaTrapObject.hpp
 * 
 * Design Document:
 * 
 * System:
 *  
 * Sub-system:
 * 
 * History:
 * 
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 * 
 */

class axTFMtaTrapObject : public axTFAbstractTrapObject
{
public:

  /**
   * default constructor
   */
  axTFMtaTrapObject();

  /// destructor
  virtual ~axTFMtaTrapObject();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return 
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed 
   *     \item AX_FAILED  unsuccessfully executed 
   *   \end{itemize}
   * @see
   */

  /**
   *
   * IN:
   *   Char Buffer length
   * OUT:
   *   Char Buffer to hold XML
   *
   */
  virtual bool toXML(AX_INT8 *, AX_UINT32);

  void setEventIndex(AX_INT8 *);
  void setEventLevel(AX_INT8 *);
  void setEventEnterprise(AX_INT8 *);
  void setEventId(AX_INT8 *);
  void setEventText(AX_INT8 *);
  void setEventMac(AX_INT8 *);
  void setEventEndpoint(AX_INT8 *);

protected:


private:

  static const char *  MTA_TRAP_OBJ_XML_NAME;

  /// not allowed
  axTFMtaTrapObject(const axTFMtaTrapObject &);

#define MAX_EVENT_INDEX_LEN 8
  static const char *  MAX_EVENT_INDEX_XML_NAME;
  AX_INT8              m_eventIndex[MAX_EVENT_INDEX_LEN];

#define MAX_EVENT_LEVEL_LEN 8
  static const char *  MAX_EVENT_LEVEL_XML_NAME;
  AX_INT8              m_eventLevel[MAX_EVENT_LEVEL_LEN];

#define MAX_EVENT_ENT_LEN 8
  static const char *  MAX_EVENT_ENT_XML_NAME;
  AX_INT8              m_eventEnterprise[MAX_EVENT_ENT_LEN];

#define MAX_EVENT_ID_LEN 12
  static const char *  MAX_EVENT_ID_XML_NAME;
  AX_INT8              m_eventId[MAX_EVENT_ID_LEN];

#define MAX_EVENT_TEXT_LEN 128
  static const char *  MAX_EVENT_TEXT_XML_NAME;
  AX_INT8              m_eventText[MAX_EVENT_TEXT_LEN];

#define MAX_EVENT_MAC_LEN 18
  static const char *  MAX_EVENT_MAC_XML_NAME;
  AX_INT8              m_mac[MAX_EVENT_MAC_LEN];

#define MAX_EVENT_ENDPOINT_LEN 8
  static const char *  MAX_EVENT_ENDPOINT_XML_NAME;
  AX_INT8              m_eventEndpoint[MAX_EVENT_ENDPOINT_LEN];
};

#endif // _axTFMtaTrapObject_hpp_
