
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSession_hpp_
#define _axAbstractSession_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
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
 * file/class: axAbstractSession.hpp
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

class axAbstractSession : public axObject
{
public:

  /// destructor
  virtual ~axAbstractSession();

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

   virtual bool open(void)=0;
   virtual bool isOpen(void)=0;
   virtual void close(void)=0;

protected:

  /// default constructor
  axAbstractSession();


private:

  axAbstractSession(const axAbstractSession &);

};

#endif // _axAbstractSession_hpp_
