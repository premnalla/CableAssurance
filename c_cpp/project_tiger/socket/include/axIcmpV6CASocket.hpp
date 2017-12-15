
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpV6CASocket_hpp_
#define _axIcmpV6CASocket_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axIcmpV6CASocket.hpp
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

class axIcmpV6CASocket : public axIcmpCASocket
{
public:

  /// default constructor
  axIcmpV6CASocket();

  /// destructor
  virtual ~axIcmpV6CASocket();

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


private:

  axIcmpV6CASocket(const axIcmpV6CASocket &);

};

#endif // _axIcmpV6CASocket_hpp_
