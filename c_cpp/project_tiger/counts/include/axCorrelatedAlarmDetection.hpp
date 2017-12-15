
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCorrelatedAlarmDetection_hpp_
#define _axCorrelatedAlarmDetection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCACounts.hpp"
#include "axSortedList.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axCorrelatedAlarmDetection.hpp
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

class axCorrelatedAlarmDetection : public axAbstractCACounts
{
public:

  /// destructor
  virtual ~axCorrelatedAlarmDetection();

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
  void setAlarmDetectionStartTime(time_t);
  void setAlarmThreshold(AX_UINT16);
  void setAlarmDetectionWindow(AX_UINT16);

  /**
   * IN:
   * OUT: all problem devices, counts
   */
  void determineCmCounts(axSortedList &, axIntCmCounts_t &);
  void determineMtaCounts(axSortedList &, axIntMtaCounts_t &);

  /**
   * IN: all problem devices
   * OUT: counts, alarm causing problem devices
   * RETURN: true if alarm condition exisits
   */
  bool determineCmPecentAlarm(
             axSortedList &, axIntCmCounts_t &, axListBase &);
  bool determineMtaCountAlarm(
             axSortedList &, axIntMtaCounts_t &, axListBase &);

  /**
   * IN: 
   * OUT: alarm causing problem devices
   * RETURN: true if alarm condition exisits
   */
  bool determineMtaPowerAlarm(axListBase &);

protected:

  /// default constructor
  axCorrelatedAlarmDetection();

  /**
   *
   */
  virtual bool hasData(void)=0;
  virtual axAbstractIterator * getCableModems(void)=0;
  virtual axAbstractIterator * getMTAs(void)=0;

private:

  axCorrelatedAlarmDetection(const axCorrelatedAlarmDetection &);

  time_t     m_alarmDetectStartTime;
  AX_UINT16  m_alarmThreshold;
  AX_UINT16  m_alarmDetectWindow;

};

#endif // _axCorrelatedAlarmDetection_hpp_
