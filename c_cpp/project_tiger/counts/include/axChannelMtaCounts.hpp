
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axChannelMtaCounts_hpp_
#define _axChannelMtaCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axChannelCounts.hpp"

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
 * file/class: axChannelMtaCounts.hpp
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

class axChannelMtaCounts : public axChannelCounts
{
public:

  /// default constructor
  axChannelMtaCounts(axInternalCmts *);

  /// destructor
  virtual ~axChannelMtaCounts();

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
  axChannelMtaCounts();

private:

  axChannelMtaCounts(const axChannelMtaCounts &);

};

#endif // _axChannelMtaCounts_hpp_
