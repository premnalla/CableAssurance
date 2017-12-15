
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSoapServiceAcceptor_hpp_
#define _axSoapServiceAcceptor_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"

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
 * file/class: axSoapServiceAcceptor.hpp
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

class axSoapServiceAcceptor : public axAceWorker
{
public:

  /// default constructor
  axSoapServiceAcceptor();

  /// destructor
  virtual ~axSoapServiceAcceptor();

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

  virtual AX_INT32 run(void);

protected:

private:

  axSoapServiceAcceptor(const axSoapServiceAcceptor &);

};

#endif // _axSoapServiceAcceptor_hpp_
