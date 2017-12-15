
//********************************************************************
// !!! OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDetectHfcOutageMsg_hpp_
#define _axDetectHfcOutageMsg_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractMessageData.hpp"
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
 * file/class: axDetectHfcOutageMsg.hpp
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

class axDetectHfcOutageMsg : public axAbstractMessageData
{
public:

  /**
   *
   */
  axDetectHfcOutageMsg(axInternalCmts *);

  /// destructor
  virtual ~axDetectHfcOutageMsg();

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
  virtual bool timerCallback(void);

protected:

  /// default constructor
  axDetectHfcOutageMsg();

private:

  axDetectHfcOutageMsg(const axDetectHfcOutageMsg &);

  INTDS_RESID_t  m_cmtsResId;
};

#endif // _axDetectHfcOutageMsg_hpp_
