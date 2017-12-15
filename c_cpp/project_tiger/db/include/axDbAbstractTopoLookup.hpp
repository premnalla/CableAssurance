
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractTopoLookup_hpp_
#define _axDbAbstractTopoLookup_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axAbstractDbObject.hpp"
#include "axTopoLookupIntDsTypes.hpp"

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
 * file/class: axDbAbstractTopoLookup.hpp
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

class axDbAbstractTopoLookup : public axAbstractDbObject
{
public:

  /// destructor
  virtual ~axDbAbstractTopoLookup();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  //
  AX_UINT32 m_id;

  //
  DB_RESID_t m_resId;

  //
  std::string m_strId;

  //
  AX_UINT16 m_topoContainerId;

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /**
   * IN: Topo container ID, Res ID
   */
  axDbAbstractTopoLookup(AX_UINT16, DB_RESID_t);

  /**
   * IN: String ID
   */
  axDbAbstractTopoLookup(const AX_INT8 *);

  /// default constructor
  axDbAbstractTopoLookup();

private:

  // Copy disallowed
  axDbAbstractTopoLookup(const axDbAbstractTopoLookup &);

};

#endif // _axDbAbstractTopoLookup_hpp_
