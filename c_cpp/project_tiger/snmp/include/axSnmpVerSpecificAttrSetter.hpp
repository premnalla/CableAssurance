
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpVerSpecificAttrSetter_hpp_
#define _axSnmpVerSpecificAttrSetter_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpTemplateSession.hpp"
#include "axAbsDbSnmpVerAttrs.hpp"
#include "axDbSnmpV2CAttrs.hpp"

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
 * file/class: axSnmpVerSpecificAttrSetter.hpp
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

class axSnmpVerSpecificAttrSetter 
{
public:

  /**
   * data constructor
   */
  axSnmpVerSpecificAttrSetter(axSnmpTemplateSession *, axAbsDbSnmpVerAttrs *);

  /// destructor
  virtual ~axSnmpVerSpecificAttrSetter();

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
   */
  void setAttributes(void);
  void setIpAddress(AX_INT8 *);

protected:

  /**
   * default constructor
   */
  axSnmpVerSpecificAttrSetter();


private:

  /// not allowed
  axSnmpVerSpecificAttrSetter(const axSnmpVerSpecificAttrSetter &);

  ///
  void setAttrs(axDbSnmpV2CAttrs *);

  axSnmpTemplateSession * m_tmplSession;
  axAbsDbSnmpVerAttrs   * m_dbSnmpVerSpecAttrs;
};

#endif // _axSnmpVerSpecificAttrSetter_hpp_