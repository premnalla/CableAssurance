
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSnmpRequestType_hpp_
#define _axAbstractSnmpRequestType_hpp_

//********************************************************************
// include files
//********************************************************************
// #include "axObject.hpp"
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
 * file/class: axAbstractSnmpRequestType.hpp
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

class axAbstractSnmpRequestType
{
public:

  /// destructor
  virtual ~axAbstractSnmpRequestType();

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

  void setMaxRepetitions(AX_INT32);
  AX_INT32 getMaxRepetitions(void);

  void setNonRepeters(AX_INT32);
  AX_INT32 getNonRepeters(void);

protected:

  /// default constructor
  axAbstractSnmpRequestType();


private:

  axAbstractSnmpRequestType(const axAbstractSnmpRequestType &);

  AX_INT32  m_maxRepetitions;
  AX_INT32  m_nonRepeters;
};

#endif // _axAbstractSnmpRequestType_hpp_
