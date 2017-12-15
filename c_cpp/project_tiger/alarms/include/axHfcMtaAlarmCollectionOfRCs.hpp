
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcMtaAlarmCollectionOfRCs_hpp_
#define _axHfcMtaAlarmCollectionOfRCs_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSubSystem.hpp"
#include "axListExtLock.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used in a similar fashion as the axCmtsPoller, 
 * axMtaPoller, etc., classes. It stores a collection of Runnable-
 * Collections.
 * 
 * 
 * file/class: axHfcMtaAlarmCollectionOfRCs.hpp
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

class axHfcMtaAlarmCollectionOfRCs : 
                       public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axHfcMtaAlarmCollectionOfRCs();

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
  static axHfcMtaAlarmCollectionOfRCs * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);

protected:

  /// default constructor
  axHfcMtaAlarmCollectionOfRCs();

private:

  axHfcMtaAlarmCollectionOfRCs(const axHfcMtaAlarmCollectionOfRCs &);

  bool loadRunnableCollection(void);

  static axHfcMtaAlarmCollectionOfRCs * m_instance;

  bool m_initialized;
};

#endif // _axHfcMtaAlarmCollectionOfRCs_hpp_
