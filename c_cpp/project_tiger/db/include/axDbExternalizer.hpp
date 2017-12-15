
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbExternalizer_hpp_
#define _axDbExternalizer_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"

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
 * file/class: axDbExternalizer.hpp
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

class axDbExternalizer 
{
public:

  /// default constructor
  axDbExternalizer();

  /// destructor
  virtual ~axDbExternalizer();

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

  ///
  static axDbConnectionFactory * getConnectionFactory(void);

  ///
  static axDbGenericQuery * getQuery(axDbConnection *);

  ///
  // static void * getConnectionFactory(void);

protected:


private:

  axDbExternalizer(const axDbExternalizer &);

};

#endif // _axDbExternalizer_hpp_
