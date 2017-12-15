
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractTimerData_hpp_
#define _axAbstractTimerData_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axTimerDataTypes.hpp"

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
 * file/class: axAbstractTimerData.hpp
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

class axAbstractTimerData : public axObject
{
public:

  /// data constructor
  axAbstractTimerData(time_t);

  /// destructor
  virtual ~axAbstractTimerData();

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

  time_t     getPopTime(void);

  AX_UINT32  getCorrelator(void);
  void       setCorrelator(AX_UINT32);

protected:

  /// default constructor
  axAbstractTimerData();

private:

  axAbstractTimerData(const axAbstractTimerData &);

  axTimerHeader_s  m_timerHeader;

};

#endif // _axAbstractTimerData_hpp_
