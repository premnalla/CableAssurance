
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractCmStatus_hpp_
#define _axDbAbstractCmStatus_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractStatus.hpp"

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
 * file/class: axDbAbstractCmStatus.hpp
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

class axDbAbstractCmStatus : public axDbAbstractStatus
{
public:

  /// destructor
  virtual ~axDbAbstractCmStatus();

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

  AX_UINT8    m_docsisState;

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /**
   * IN: Res ID(any one of: cmts, hfc, channel, etc)
   */
  axDbAbstractCmStatus(DB_RESID_t);

  /// default constructor
  axDbAbstractCmStatus();

private:

  // Copy disallowed
  axDbAbstractCmStatus(const axDbAbstractCmStatus &);

};

#endif // _axDbAbstractCmStatus_hpp_
