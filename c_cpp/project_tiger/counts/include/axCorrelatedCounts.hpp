
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCorrelatedCounts_hpp_
#define _axCorrelatedCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCACounts.hpp"
#include "axInternalCmts.hpp"

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
 * file/class: axCorrelatedCounts.hpp
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

class axCorrelatedCounts : public axAbstractCACounts
{
public:

  /// destructor
  virtual ~axCorrelatedCounts();

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
  virtual bool preSoakAlarmDetermination(void)=0;

protected:

  /// data constructor
  axCorrelatedCounts(axInternalCmts *);

  /// default constructor
  axCorrelatedCounts();

  axInternalCmts * getInternalCmts(void);

private:

  axCorrelatedCounts(const axCorrelatedCounts &);

  axInternalCmts * m_intCmts;
};

#endif // _axCorrelatedCounts_hpp_
