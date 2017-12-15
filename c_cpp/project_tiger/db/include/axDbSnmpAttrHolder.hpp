
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbSnmpAttrHolder_hpp_
#define _axDbSnmpAttrHolder_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbsDbSnmpVerAttrs.hpp"

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
 * file/class: axDbSnmpAttrHolder.hpp
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

class axDbSnmpAttrHolder 
{
public:

  /**
   * data constructor
   */
  axDbSnmpAttrHolder(axAbsDbSnmpVerAttrs *);

  /// destructor
  virtual ~axDbSnmpAttrHolder();

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
  axAbsDbSnmpVerAttrs * getAttrs(void);

protected:

  /**
   * default constructor
   */
  axDbSnmpAttrHolder();

private:

  /// not allowed
  axDbSnmpAttrHolder(const axDbSnmpAttrHolder &);

  axAbsDbSnmpVerAttrs * m_attrs;
};

#endif // _axDbSnmpAttrHolder_hpp_
