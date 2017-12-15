
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractStatus_hpp_
#define _axDbAbstractStatus_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"

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
 * file/class: axDbAbstractStatus.hpp
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

class axDbAbstractStatus : public axAbstractDbObject
{
public:

  /// destructor
  virtual ~axDbAbstractStatus();

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

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /**
   * IN: Res ID(any one of: cmts, hfc, channel, etc)
   */
  axDbAbstractStatus(DB_RESID_t);

  /**
   * IN: Res ID(any one of: cmts, hfc, channel, etc)
   * IN: time_t (time in seconds since 01/1/70
   */
  axDbAbstractStatus(DB_RESID_t, time_t);

  /// default constructor
  axDbAbstractStatus();

private:

  // Copy disallowed
  axDbAbstractStatus(const axDbAbstractStatus &);

  void initAtInstantiation(void);
};

#endif // _axDbAbstractStatus_hpp_
