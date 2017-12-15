
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaCurrentAlarmTable_hpp_
#define _axMtaCurrentAlarmTable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCAAlarmCollection.hpp"

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
 * file/class: axMtaCurrentAlarmTable.hpp
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

class axMtaCurrentAlarmTable : public axCAAlarmCollection
{
public:

  /// destructor
  virtual ~axMtaCurrentAlarmTable();

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

  static axMtaCurrentAlarmTable * getInstance(void);

protected:

  /// default constructor
  axMtaCurrentAlarmTable();

private:

  axMtaCurrentAlarmTable(const axMtaCurrentAlarmTable &);

  static axMtaCurrentAlarmTable * m_instance;
};

#endif // _axMtaCurrentAlarmTable_hpp_
