
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpSessionHolder_hpp_
#define _axSnmpSessionHolder_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axSnmpSession.hpp"

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
 * file/class: axSnmpSessionHolder.hpp
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

class axSnmpSessionHolder 
{
public:

  /**
   * default constructor
   */
  axSnmpSessionHolder(axSnmpSession *);

  /// destructor
  virtual ~axSnmpSessionHolder();

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
  axSnmpSession * getSession(void);

protected:

  /**
   * default constructor
   */
  axSnmpSessionHolder();


private:

  /// not allowed
  axSnmpSessionHolder(const axSnmpSessionHolder &);

  axSnmpSession * m_openSession;
};

#endif // _axSnmpSessionHolder_hpp_
