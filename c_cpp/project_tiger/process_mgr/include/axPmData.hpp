
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axPmData_hpp_
#define _axPmData_hpp_

//********************************************************************
// include files
//********************************************************************
#include <sys/types.h>
#include "axAll.h"
#include "axProcessMgrDSTypes.hpp"

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
 * file/class: axPmData.hpp
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

class axPmData 
{
public:

  /// destructor
  virtual ~axPmData();

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
  static axPmData * getInstance(void);

  /**
   * 
   */
  axPmInittabEntries_s * getInittabEntries(void);
  void dumpInittabEntries(void);

  /**
   *
   */
  void setChildDied(pid_t);

protected:

  /**
   * default constructor
   */
  axPmData();

private:

  /// not allowed
  axPmData(const axPmData &);

  ///
  static axPmData       * m_instance;

  ///
  axPmInittabEntries_s    m_entries;
};

#endif // _axPmData_hpp_
