
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpSocketHolder_hpp_
#define _axIcmpSocketHolder_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axIcmpCASocket.hpp"

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
 * file/class: axIcmpSocketHolder.hpp
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

class axIcmpSocketHolder 
{
public:

  /**
   * default constructor
   */
  axIcmpSocketHolder(axIcmpCASocket *);

  /// destructor
  virtual ~axIcmpSocketHolder();

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
  axIcmpCASocket * getSocket(void);

protected:

  /**
   * default constructor
   */
  axIcmpSocketHolder();


private:

  /// not allowed
  axIcmpSocketHolder(const axIcmpSocketHolder &);

  axIcmpCASocket * m_sock;
};

#endif // _axIcmpSocketHolder_hpp_
