
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractObjectHolder_hpp_
#define _axAbstractObjectHolder_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axAbstractObjectHolder.hpp
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

class axAbstractObjectHolder : public axObject
{
public:

  /// destructor
  virtual ~axAbstractObjectHolder();

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


  axObject * getObject(void);

protected:

  /// default constructor
  axAbstractObjectHolder(axObject *);


private:

  /// default constructor
  axAbstractObjectHolder();

  axAbstractObjectHolder(const axAbstractObjectHolder &);

  axObject * m_obj;
};

#endif // _axAbstractObjectHolder_hpp_
