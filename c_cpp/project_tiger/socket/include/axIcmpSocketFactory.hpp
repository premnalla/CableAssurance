
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIcmpSocketFactory_hpp_
#define _axIcmpSocketFactory_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractFactory.hpp"
#include "axIcmpCASocket.hpp"
#include "axIcmpTemplateSocket.hpp"

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
 * file/class: axIcmpSocketFactory.hpp
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

class axIcmpSocketFactory : public axAbstractFactory
{
public:

  /// destructor
  virtual ~axIcmpSocketFactory();

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

   static axIcmpSocketFactory * getInstance(void);

   axIcmpCASocket * createSocket(axIcmpTemplateSocket *);

protected:

  /// default constructor
  axIcmpSocketFactory();


private:

  axIcmpSocketFactory(const axIcmpSocketFactory &);

  static axIcmpSocketFactory * m_instance;
};

#endif // _axIcmpSocketFactory_hpp_
