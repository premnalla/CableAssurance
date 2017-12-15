
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpSessionFactory_hpp_
#define _axSnmpSessionFactory_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axAbstractFactory.hpp"
#include "axSnmpSession.hpp"
#include "axSnmpTemplateSession.hpp"

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
 * file/class: axSnmpSessionFactory.hpp
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

class axSnmpSessionFactory : public axAbstractFactory
{
public:

  /// destructor
  virtual ~axSnmpSessionFactory();

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
  static axSnmpSessionFactory * getInstance(void);

  /**
   * 
   */
  axSnmpSession * createSession(axSnmpTemplateSession *);

protected:

  /// default constructor
  axSnmpSessionFactory();


private:

  axSnmpSessionFactory(const axSnmpSessionFactory &);

  static axSnmpSessionFactory * m_instance;
};

#endif // _axSnmpSessionFactory_hpp_
