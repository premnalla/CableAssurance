
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAlarmProcessingMsgQ_hpp_
#define _axAlarmProcessingMsgQ_hpp_

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
 * file/class: axAlarmProcessingMsgQ.hpp
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

class axAlarmProcessingMsgQ : public axCAMessageQueue
{
public:

  /// destructor
  virtual ~axAlarmProcessingMsgQ();

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
  static axAlarmProcessingMsgQ * getInstance(void);

protected:

  /// default constructor
  axAlarmProcessingMsgQ();


private:

  axAlarmProcessingMsgQ(const axAlarmProcessingMsgQ &);

  static axAlarmProcessingMsgQ * m_instance;
};

#endif // _axAlarmProcessingMsgQ_hpp_
