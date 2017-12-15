
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmCollectionofRCs_hpp_
#define _axHfcAlarmCollectionofRCs_hpp_

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
 * file/class: axHfcAlarmCollectionofRCs.hpp
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

class axHfcAlarmCollectionofRCs : 
                       public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axHfcAlarmCollectionofRCs();

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
  static axHfcAlarmCollectionofRCs * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);

protected:

  /// default constructor
  axHfcAlarmCollectionofRCs();

private:

  axHfcAlarmCollectionofRCs(const axHfcAlarmCollectionofRCs &);

  bool loadRunnableCollection(void);

  static axHfcAlarmCollectionofRCs * m_instance;

  bool m_initialized;
};

#endif // _axHfcAlarmCollectionofRCs_hpp_
