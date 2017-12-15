
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsMtaCounts_hpp_
#define _axCmtsMtaCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCmtsCounts.hpp"

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
 * file/class: axCmtsMtaCounts.hpp
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

class axCmtsMtaCounts : public axCmtsCounts
{
public:

  /// default constructor
  axCmtsMtaCounts(axInternalCmts *);

  /// destructor
  virtual ~axCmtsMtaCounts();

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


protected:

  /// default constructor
  axCmtsMtaCounts();

  /**
   *
   *
   */
  virtual void cableModemCounts(void);

private:

  axCmtsMtaCounts(const axCmtsMtaCounts &);

};

#endif // _axCmtsMtaCounts_hpp_
