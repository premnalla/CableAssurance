
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcCurrentAlarmTable_hpp_
#define _axHfcCurrentAlarmTable_hpp_

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
 * file/class: axHfcCurrentAlarmTable.hpp
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

class axHfcCurrentAlarmTable : public axCAAlarmCollection
{
public:

  /// destructor
  virtual ~axHfcCurrentAlarmTable();

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

  static axHfcCurrentAlarmTable * getInstance(void);

protected:

  /// default constructor
  axHfcCurrentAlarmTable();

private:

  axHfcCurrentAlarmTable(const axHfcCurrentAlarmTable &);

  static axHfcCurrentAlarmTable * m_instance;
};

#endif // _axHfcCurrentAlarmTable_hpp_
