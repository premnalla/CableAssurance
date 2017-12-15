
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpTemplateSocket_hpp_
#define _axIcmpTemplateSocket_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"

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
 * file/class: axIcmpTemplateSocket.hpp
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

class axIcmpTemplateSocket
{
public:

  /// default constructor
  axIcmpTemplateSocket();

  /// destructor
  virtual ~axIcmpTemplateSocket();

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

  void setDomain(AX_INT32);
  void setType(AX_INT32);
  void setProtocol(AX_INT32);

  AX_INT32 getDomain(void);
  AX_INT32 getType(void);
  AX_INT32 getProtocol(void);

protected:

private:

  axIcmpTemplateSocket(const axIcmpTemplateSocket &);

  AX_INT32    m_socketDomain;
  AX_INT32    m_socketType;
  AX_INT32    m_socketProtocol;

};

#endif // _axIcmpTemplateSocket_hpp_
