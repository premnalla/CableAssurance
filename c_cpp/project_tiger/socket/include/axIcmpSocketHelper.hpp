
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpSocketHelper_hpp_
#define _axIcmpSocketHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSocketHelper.hpp"
#include "axIcmpCASocket.hpp"
#include "axIcmpSocketFactory.hpp"
#include "axCblAssureConstants.hpp"


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
 * file/class: axIcmpSocketHelper.hpp
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

class axIcmpSocketHelper : public axAbstractSocketHelper
{
public:

  /// data constructor
  axIcmpSocketHelper(axIcmpSocketFactory *, AX_UINT8 ipAddressType=AX_IP_ADDR_TYPE_IPv4);

  /// destructor
  virtual ~axIcmpSocketHelper();

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

  axIcmpCASocket * getSocket(void);

protected:

  /// default constructor
  axIcmpSocketHelper();

  // void setSocket(axIcmpCASocket *);

  // void setSocketFactory(axIcmpSocketFactory *);
  // axIcmpSocketFactory * getSocketFactory(void);

private:

  axIcmpSocketHelper(const axIcmpSocketHelper &);

  axIcmpCASocket      * m_socket;
  axIcmpSocketFactory * m_socketFactory;

};

#endif // _axIcmpSocketHelper_hpp_
