
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpAsyncCallbackObj_hpp_
#define _axSnmpAsyncCallbackObj_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axAbstractInternalObject.hpp"
#include "axAbstractSnmpFloodGetWork.hpp"

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
 * file/class: axSnmpAsyncCallbackObj.hpp
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

class axSnmpAsyncCallbackObj : public axObject
{
public:

  /**
   * default constructor
   */
  axSnmpAsyncCallbackObj(axAbstractInternalObject *, 
                                      axAbstractSnmpFloodGetWork *);

  /// destructor
  virtual ~axSnmpAsyncCallbackObj();

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
  axObject                   * getObject(void);
  axAbstractSnmpFloodGetWork * getProcessingClass(void);

protected:

  /**
   * default constructor
   */
  axSnmpAsyncCallbackObj();

private:

  /// not allowed
  axSnmpAsyncCallbackObj(const axSnmpAsyncCallbackObj &);

  axObject                   * m_obj;
  axAbstractSnmpFloodGetWork * m_pClass;
};

#endif // _axSnmpAsyncCallbackObj_hpp_
