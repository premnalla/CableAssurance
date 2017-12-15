
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractCurrentCounts_hpp_
#define _axDbAbstractCurrentCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCounts.hpp"

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
 * file/class: axDbAbstractCurrentCounts.hpp
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

class axDbAbstractCurrentCounts : public axDbAbstractCounts
{
public:

  /// destructor
  virtual ~axDbAbstractCurrentCounts();

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

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /// default constructor
  axDbAbstractCurrentCounts();

  /**
   * IN: CMTS Res ID
   */
  axDbAbstractCurrentCounts(DB_RESID_t);

  /**
   * IN: CMTS Res ID; CM Counts; MTA counts
   */
  axDbAbstractCurrentCounts(DB_RESID_t, time_t, axIntCounts_t *);

private:

  // Copy disallowed
  axDbAbstractCurrentCounts(const axDbAbstractCurrentCounts &);

};

#endif // _axDbAbstractCurrentCounts_hpp_
