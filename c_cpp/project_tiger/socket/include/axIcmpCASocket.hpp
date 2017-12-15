
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpCASocket_hpp_
#define _axIcmpCASocket_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCASocket.hpp"

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
 * file/class: axIcmpCASocket.hpp
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

class axIcmpCASocket : public axAbstractCASocket
{
public:

  /// destructor
  virtual ~axIcmpCASocket();

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


protected:

  /**
   * Data constructor:
   *
   * IN: socket (Domain, Type, Protocol)
   *
   *
   */
  axIcmpCASocket(AX_INT32, AX_INT32, AX_INT32);

private:

  /// default constructor
  axIcmpCASocket();

  axIcmpCASocket(const axIcmpCASocket &);

};

#endif // _axIcmpCASocket_hpp_
