
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSocket_hpp_
#define _axAbstractSocket_hpp_

//********************************************************************
// include files
//********************************************************************
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include "axObject.hpp"

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
 * file/class: axAbstractSocket.hpp
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

class axAbstractSocket : public axObject
{
public:

  /// destructor
  virtual ~axAbstractSocket();

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

   virtual void initialize(void);

   virtual bool openSocket(void);
   virtual bool closeSocket(void);
   virtual bool testSocket(void);
   virtual bool isOpen(void);

   // void        setHandle(AX_INT32);
   AX_INT32    getHandle(void);

protected:

  /**
   * Data constructor:
   * 
   * IN: socket (Domain, Type, Protocol)
   * 
   * 
   */
  axAbstractSocket(AX_INT32, AX_INT32, AX_INT32);


private:

  /// default constructor
  axAbstractSocket();

  axAbstractSocket(const axAbstractSocket &);

  AX_INT32    m_socketDomain;
  AX_INT32    m_socketType;
  AX_INT32    m_socketProtocol;

  AX_INT32    m_socketFd;

  bool        m_opened;
};

#endif // _axAbstractSocket_hpp_
