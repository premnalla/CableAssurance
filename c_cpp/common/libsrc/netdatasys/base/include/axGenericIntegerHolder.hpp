
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axGenericIntegerHolder_hpp_
#define _axGenericIntegerHolder_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractObjectHolder.hpp"

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
 * file/class: axGenericIntegerHolder.hpp
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

class axGenericIntegerHolder : public axAbstractObjectHolder
{
public:

  /// default constructor
  axGenericIntegerHolder(axObject *, AX_INT64);

  /// destructor
  virtual ~axGenericIntegerHolder();

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
   * Override from axObject
   *
   * @return
   * @see
   */
  virtual AX_INT64  hashCode(void);

  /**
   * Describe here ...
   *
   * @return
   * @see
   */
  AX_INT64 getNumber(void);

protected:

  /// default constructor
  axGenericIntegerHolder();


private:

  axGenericIntegerHolder(const axGenericIntegerHolder &);

  AX_INT64 m_number;
};

#endif // _axGenericIntegerHolder_hpp_
