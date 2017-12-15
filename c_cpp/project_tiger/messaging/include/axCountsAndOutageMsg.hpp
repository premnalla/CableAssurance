
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCountsAndOutageMsg_hpp_
#define _axCountsAndOutageMsg_hpp_

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
 * file/class: axCountsAndOutageMsg.hpp
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

class axCountsAndOutageMsg : public axCAMessage
{
public:

  /// data constructor
  axCountsAndOutageMsg(AX_UINT8 , axMessagePayload_s *);

  /// destructor
  virtual ~axCountsAndOutageMsg();

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
  axCountsAndOutageMsg();


private:

  axCountsAndOutageMsg(const axCountsAndOutageMsg &);

};

#endif // _axCountsAndOutageMsg_hpp_
