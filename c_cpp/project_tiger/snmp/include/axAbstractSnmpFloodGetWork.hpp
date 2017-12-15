
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSnmpFloodGetWork_hpp_
#define _axAbstractSnmpFloodGetWork_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbsSnmpAsyncPollWork.hpp"
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
 * file/class: axAbstractSnmpFloodGetWork.hpp
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

class axAbstractSnmpFloodGetWork : public axAbsSnmpAsyncPollWork
{
public:

  /// destructor
  virtual ~axAbstractSnmpFloodGetWork();

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
   */
  axInternalCmts * getInternalCmts(void);

protected:

  /**
   *
   */
  axAbstractSnmpFloodGetWork(axInternalCmts *);

  /// default constructor 
  axAbstractSnmpFloodGetWork();

#if 0
  /**
   *
   * OUT:
   *  int * - numFD's
   *  fd_set * - read fd set
   *  struct timeval * - timeout value for select() sys call
   *  int * - block (net-snmp'ism)
   */
  virtual bool setFdSet(AX_INT32 *, fd_set *, struct timeval *, AX_INT32 *);

  /**
   *
   */
  virtual bool handleTimeout(void);
#endif

private:

  /// copy not allowed
  axAbstractSnmpFloodGetWork(const axAbstractSnmpFloodGetWork &);

  axInternalCmts * m_intCmts;
};

#endif // _axAbstractSnmpFloodGetWork_hpp_
