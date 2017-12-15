
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAceWorker_hpp_
#define _axAceWorker_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axWorker.hpp"

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
 * file/class: axAceWorker.hpp
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

class axAceWorker : public axWorker
{
public:

  /// destructor
  virtual ~axAceWorker();

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
   * flags: THR_NEW_LWP | THR_JOINABLE | THR_INHERIT_SCHED | THR_DETACHED
   * notes: the flags can be OR'd
   */
  void setThreadFlags(AX_ULONG);

  /**
   * Overloaded from axWorker base class
   */
  virtual AX_INT32 start(void);

protected:

  /// default constructor
  axAceWorker();

  ///
  axAceWorker(axControllerProxy *);


private:

  axAceWorker(const axAceWorker &);

  AX_ULONG  m_threadFlags;
};

#endif // _axAceWorker_hpp_
