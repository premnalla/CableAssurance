
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcCounts_hpp_
#define _axHfcCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCorrelatedCounts.hpp"

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
 * file/class: axHfcCounts.hpp
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

class axHfcCounts : public axCorrelatedCounts
{
public:

  /// data constructor
  axHfcCounts(axInternalCmts *);

  /// destructor
  virtual ~axHfcCounts();

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
   *
   */
  virtual bool preSoakAlarmDetermination(void);

protected:

  /// default constructor
  axHfcCounts();

private:

  axHfcCounts(const axHfcCounts &);

};

#endif // _axHfcCounts_hpp_
