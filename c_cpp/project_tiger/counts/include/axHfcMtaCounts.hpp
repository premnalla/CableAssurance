
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcMtaCounts_hpp_
#define _axHfcMtaCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axHfcCounts.hpp"

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
 * file/class: axHfcMtaCounts.hpp
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

class axHfcMtaCounts : public axHfcCounts
{
public:

  /// default constructor
  axHfcMtaCounts(axInternalCmts *);

  /// destructor
  virtual ~axHfcMtaCounts();

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
  axHfcMtaCounts();

private:

  axHfcMtaCounts(const axHfcMtaCounts &);

};

#endif // _axHfcMtaCounts_hpp_
