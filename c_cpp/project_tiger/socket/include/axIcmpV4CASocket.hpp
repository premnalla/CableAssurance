
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpV4CASocket_hpp_
#define _axIcmpV4CASocket_hpp_

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
 * file/class: axIcmpV4CASocket.hpp
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

class axIcmpV4CASocket : public axIcmpCASocket
{
public:

  /// default constructor
  axIcmpV4CASocket();

  /// destructor
  virtual ~axIcmpV4CASocket();

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

  axIcmpV4CASocket(const axIcmpV4CASocket &);

};

#endif // _axIcmpV4CASocket_hpp_
