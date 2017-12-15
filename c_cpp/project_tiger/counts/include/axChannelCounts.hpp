
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axChannelCounts_hpp_
#define _axChannelCounts_hpp_

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
 * file/class: axChannelCounts.hpp
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

class axChannelCounts : public axCorrelatedCounts
{
public:

  /// default constructor
  axChannelCounts(axInternalCmts *);

  /// destructor
  virtual ~axChannelCounts();

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
  axChannelCounts();


private:

  axChannelCounts(const axChannelCounts &);

};

#endif // _axChannelCounts_hpp_
