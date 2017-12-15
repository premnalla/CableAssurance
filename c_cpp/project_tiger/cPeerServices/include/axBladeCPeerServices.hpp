
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axBladeCPeerServices_hpp_
#define _axBladeCPeerServices_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCPeerServices.hpp"

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
 * file/class: axBladeCPeerServices.hpp
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

class axBladeCPeerServices : public axAbstractCPeerServices
{
public:

  /**
   * default constructor
   */
  axBladeCPeerServices();

  /// destructor
  virtual ~axBladeCPeerServices();

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

protected:


private:

  /// not allowed
  axBladeCPeerServices(const axBladeCPeerServices &);

};

#endif // _axBladeCPeerServices_hpp_
