
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCountsAndAlarmMsg_hpp_
#define _axCountsAndAlarmMsg_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCAMessage.hpp"
#include "axMessageDataTypes.hpp"

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
 * file/class: axCountsAndAlarmMsg.hpp
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

class axCountsAndAlarmMsg : public axCAMessage
{
public:

  /// data constructor
  axCountsAndAlarmMsg(AX_UINT8 , axMessagePayload_s *);

  /// destructor
  virtual ~axCountsAndAlarmMsg();

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
  axCountsAndAlarmMsg();


private:

  axCountsAndAlarmMsg(const axCountsAndAlarmMsg &);

};

#endif // _axCountsAndAlarmMsg_hpp_
