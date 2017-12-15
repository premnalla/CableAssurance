
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractCmPerf_hpp_
#define _axDbAbstractCmPerf_hpp_

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
 * file/class: axDbAbstractCmPerf.hpp
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

class axDbAbstractCmPerf : public axAbstractDbObject
{
public:

  /// destructor
  virtual ~axDbAbstractCmPerf();

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

  /**
   * Running DB id
   */
  AX_UINT64  m_id;

  /**
   * Resource ID
   */
  DB_RESID_t m_resId;

  /**
   * Last Log time
   */
  AX_UINT32  m_lastLogTime;

  //
  AX_UINT32 m_downstreamSnr;
  AX_UINT32 m_downstreamPower;
  AX_UINT32 m_upstreamPower;
  AX_UINT32 m_t1Timeouts;
  AX_UINT32 m_t2Timeouts;
  AX_UINT32 m_t3Timeouts;
  AX_UINT32 m_t4Timeouts;

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /**
   * IN: CM Res ID;
   * IN: time_t (seconds since 01/1/70
   * IN: Internal CM;
   */
  axDbAbstractCmPerf(DB_RESID_t, time_t, axIntCmNonkey_t *);

  /**
   * IN: Res ID(any one of: cmts, hfc, channel, etc)
   */
  axDbAbstractCmPerf(DB_RESID_t);

  /// default constructor
  axDbAbstractCmPerf();

private:

  // Copy disallowed
  axDbAbstractCmPerf(const axDbAbstractCmPerf &);

  void initAtInstantiation(void);
};

#endif // _axDbAbstractCmPerf_hpp_
