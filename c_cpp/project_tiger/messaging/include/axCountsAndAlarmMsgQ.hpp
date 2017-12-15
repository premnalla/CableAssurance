
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCountsAndAlarmMsgQ_hpp_
#define _axCountsAndAlarmMsgQ_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCAMessageQueue.hpp"

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
 * file/class: axCountsAndAlarmMsgQ.hpp
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

class axCountsAndAlarmMsgQ : public axCAMessageQueue
{
public:

  /// destructor
  virtual ~axCountsAndAlarmMsgQ();

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

  ///
  static axCountsAndAlarmMsgQ * getInstance(void);

protected:

  /// default constructor
  axCountsAndAlarmMsgQ();


private:

  axCountsAndAlarmMsgQ(const axCountsAndAlarmMsgQ &);

  static axCountsAndAlarmMsgQ * m_instance;
};

#endif // _axCountsAndAlarmMsgQ_hpp_
