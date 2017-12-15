
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractCounts_hpp_
#define _axDbAbstractCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"
#include "axListBase.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axDbAbstractCounts.hpp
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

class axDbAbstractCounts : public axAbstractDbObject
{
public:

  /// destructor
  virtual ~axDbAbstractCounts();

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
  AX_UINT64 m_id;

  //
  DB_RESID_t m_resId;

  //
  AX_UINT32 m_totalCm;
  AX_UINT32 m_onlineCm;

  //
  AX_UINT32 m_totalMta;
  AX_UINT32 m_availableMta;

  //
  AX_UINT32 m_timeSec;

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /**
   * IN: Res ID(any one of: cmts, hfc, channel, etc)
   */
  axDbAbstractCounts(DB_RESID_t);

  /**
   * IN: CMTS Res ID; CM Counts; MTA counts
   */
  axDbAbstractCounts(DB_RESID_t, time_t, axIntCounts_t *);

  /// default constructor
  axDbAbstractCounts();

private:

  // Copy disallowed
  axDbAbstractCounts(const axDbAbstractCounts &);

  void initAtInstantiation(void);
};

#endif // _axDbAbstractCounts_hpp_
