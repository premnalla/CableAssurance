
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbsDbSnmpVerAttrs_hpp_
#define _axAbsDbSnmpVerAttrs_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"

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
 * file/class: axAbsDbSnmpVerAttrs.hpp
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

class axAbsDbSnmpVerAttrs : public axAbstractDbObject
{
public:

  /// destructor
  virtual ~axAbsDbSnmpVerAttrs();

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

  //
  DB_OBJ_AUTO_INC_ID_t m_id;

  //
  DB_RESID_t m_cmtsResId;

  /**
   * 
   */

protected:

  /**
   * default constructor
   */
  axAbsDbSnmpVerAttrs();

  /**
   * default constructor
   */
  axAbsDbSnmpVerAttrs(const axAbsDbSnmpVerAttrs &);

private:

};

#endif // _axAbsDbSnmpVerAttrs_hpp_
