
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpTemplateSession_hpp_
#define _axSnmpTemplateSession_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpSession.hpp"

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
 * file/class: axSnmpTemplateSession.hpp
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

class axSnmpTemplateSession : public axAbstractSnmpSession
{
public:

  /// default constructor
  axSnmpTemplateSession();

  /// destructor
  virtual ~axSnmpTemplateSession();

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
  virtual bool open(void);

  /**
   *
   */
  virtual void close(void);

  /**
   *
   */
  virtual bool isOpen(void);

  /**
   *
   */
  virtual netsnmp_session * getInternalSession(void);

  /**
   *
   */
  virtual netsnmp_session_list * getInternalSessionList(void);

protected:


private:

  axSnmpTemplateSession(const axSnmpTemplateSession &);

  netsnmp_session    m_templateSession;
};

#endif // _axSnmpTemplateSession_hpp_
