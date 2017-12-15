
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpSessionHelper_hpp_
#define _axSnmpSessionHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSessionHelper.hpp"
#include "axSnmpSession.hpp"
#include "axSnmpSessionFactory.hpp"


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
 * file/class: axSnmpSessionHelper.hpp
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

class axSnmpSessionHelper : public axAbstractSessionHelper
{
public:

  /// default constructor
  axSnmpSessionHelper();

  /// destructor
  virtual ~axSnmpSessionHelper();

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

  virtual axSnmpSession * getSession(void);

protected:

  void setSession(axSnmpSession *);

  void setSessionFactory(axSnmpSessionFactory *);
  axSnmpSessionFactory * getSessionFactory(void);

private:

  axSnmpSessionHelper(const axSnmpSessionHelper &);

  axSnmpSession        * m_session;
  axSnmpSessionFactory * m_sessionFactory;

};

#endif // _axSnmpSessionHelper_hpp_
