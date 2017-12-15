
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axOutageTrackingMgr_hpp_
#define _axOutageTrackingMgr_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axCAOutageCollection.hpp"
#include "axHfcOutage.hpp"

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
 * file/class: axOutageTrackingMgr.hpp
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

class axOutageTrackingMgr 
{
public:

  /// default constructor
  axOutageTrackingMgr();

  /// destructor
  virtual ~axOutageTrackingMgr();

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
  static axOutageTrackingMgr * getInstance();

  ///
  bool          isCurrentHfcOutage(axHfcOutage *);
  axHfcOutage * addCurrentHfcOutage(axHfcOutage *);
  axHfcOutage * findCurrentHfcOutage(axHfcOutage *);
  axHfcOutage * removeCurrentHfcOutage(axHfcOutage *);

protected:


private:

  axOutageTrackingMgr(const axOutageTrackingMgr &);

  static axOutageTrackingMgr * m_instance;
  bool                         m_initialized;

  axCAOutageCollection         m_currentHfcOutageTable;
  axCAOutageCollection         m_currentMtaOutageTable;
};

#endif // _axOutageTrackingMgr_hpp_
