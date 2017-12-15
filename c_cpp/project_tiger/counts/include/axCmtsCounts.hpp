
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsCounts_hpp_
#define _axCmtsCounts_hpp_

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
 * file/class: axCmtsCounts.hpp
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

class axCmtsCounts : public axAbstractCACounts
{
public:

  /// default constructor
  axCmtsCounts(axInternalCmts *);

  /// destructor
  virtual ~axCmtsCounts();

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
  virtual bool run(void);

protected:

  /// default constructor
  axCmtsCounts();

  /**
   *
   *
   */
  virtual void cableModemCounts(void);
  virtual void mtaCounts(void);

private:

  axCmtsCounts(const axCmtsCounts &);

  axInternalCmts * m_intCmts;

  AX_UINT32        m_totalCm;
  AX_UINT32        m_onlineCm;
  AX_UINT32        m_totalMta;
  AX_UINT32        m_availableMta;

};

#endif // _axCmtsCounts_hpp_
