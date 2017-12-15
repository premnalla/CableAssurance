
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbResultSet_hpp_
#define _axDbResultSet_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSqlResultSet.hpp"
#include "axDbConnection.hpp"
#include "axDbCmts.hpp"
#include "axDbChannel.hpp"
#include "axDbHfc.hpp"
#include "axDbCm.hpp"
#include "axDbEmta.hpp"
#include "axDbCmtsCurrentCounts.hpp"
#include "axDbCmtsCountsLog.hpp"
#include "axDbHfcCurrentCounts.hpp"
#include "axDbHfcCountsLog.hpp"
#include "axDbCurrentCmPerf.hpp"
#include "axDbCmPerfLog.hpp"
#include "axDbChannelCurrentCounts.hpp"
#include "axDbChannelCountsLog.hpp"
#include "axDbCmStatusLog.hpp"
#include "axDbMtaPingStatusLog.hpp"
#include "axDbMtaProvStatusLog.hpp"
#include "axDbMtaAvailLog.hpp"
#include "axDbCASoakingAlarm.hpp"
#include "axDbCAAlarmHistory.hpp"
#include "axDbCAAlarmDeviceSoaking.hpp"
#include "axDbCAAlarmDevicePostSoak.hpp"
#include "axDbCAAlarmCoincidentalSoaking.hpp"
#include "axDbCAAlarmCoincidentalPostSoak.hpp"
#include "axDbCAAlarmSecondary.hpp"
#include "axDbAppConfig.hpp"
#include "axDbSnmpV2CAttrs.hpp"
#include "axDbCARootAlarm.hpp"
#include "axDbCACurrentAlarm.hpp"
#include "axDbCAHistoricalAlarm.hpp"
#include "axDbMtaCurrentAvailability.hpp"
#include "axDbCmCurrentStatus.hpp"
#include "axDbSummaryFlags.hpp"
#include "axDbLastCompSummary.hpp"
#include "axDbHfcCurrentStatus.hpp"
#include "axDbHfcCurrentTca.hpp"
#include "axDbHfcStatusLog.hpp"
#include "axDbHfcTcaLog.hpp"
#include "axDbAbstractTopoLookup.hpp"
#include "axDbLocalSystem.hpp"
#include "axDbAbstractTopoContainer.hpp"
#include "axDbEmtaSecondary.hpp"

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
 * file/class: axDbResultSet.hpp
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

class axDbResultSet : public axSqlResultSet
{
public:

  /// destructor
  virtual ~axDbResultSet();

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

  //***************************
  //
  //***************************

  /**
   *
   */
  virtual bool getNext(axDbCmts &)=0;
  virtual bool getNext(axDbCmts &, axDbCmtsCurrentCounts &)=0;

  /**
   *
   */
  virtual bool getNext(axDbChannel &)=0;
  virtual bool getNext(axDbChannel &, axDbChannelCurrentCounts &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfc &)=0;
  virtual bool getNext(axDbHfc &, axDbHfcCurrentCounts &, 
                     axDbHfcCurrentStatus &, axDbHfcCurrentTca &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCm &)=0;
  virtual bool getNext(axDbCm &, axDbCmCurrentStatus &, axDbCurrentCmPerf &)=0;

  /**
   *
   */
  virtual bool getNext(axDbEmta &)=0;
  virtual bool getNext(axDbEmta &, axDbMtaCurrentAvailability &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCmtsCurrentCounts &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCmtsCountsLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfcCurrentCounts &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfcCountsLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCurrentCmPerf &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCmPerfLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbChannelCurrentCounts &)=0;

  /**
   *
   */
  virtual bool getNext(axDbChannelCountsLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCmStatusLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbMtaPingStatusLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbMtaProvStatusLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbMtaAvailLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCASoakingAlarm &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmHistory &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmDeviceSoaking &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmDevicePostSoak &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmCoincidentalSoaking &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmCoincidentalPostSoak &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmSecondary &)=0;

  /**
   *
   */
  virtual bool getNext(axDbAppConfig &)=0;

  /**
   *
   */
  virtual bool getNext(axDbSnmpV2CAttrs &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCARootAlarm &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCACurrentAlarm &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCAHistoricalAlarm &)=0;

  /**
   *
   */
  virtual bool getNext(axDbMtaCurrentAvailability &)=0;

  /**
   *
   */
  virtual bool getNext(axDbCmCurrentStatus &)=0;

  /**
   *
   */
  virtual bool getNext(axDbSummaryFlags &)=0;

  /**
   *
   */
  virtual bool getNext(axDbLastCompSummary &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfcCurrentStatus &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfcCurrentTca &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfcStatusLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbHfcTcaLog &)=0;

  /**
   *
   */
  virtual bool getNext(axDbAbstractTopoLookup &)=0;

  /**
   *
   */
  virtual bool getNext(axDbLocalSystem &)=0;

  /**
   *
   */
  virtual bool getNext(axDbAbstractTopoContainer &)=0;

  /**
   *
   */
  virtual bool getNext(axDbEmtaSecondary &)=0;

  //***************************
  //
  //***************************

  // virtual bool getNext(cmts &);
  // virtual bool getNext(cm &);
  // virtual bool getNext(emta &);
  // ...

protected:

  /// data constructor
  axDbResultSet(axDbConnection *);

  /// default constructor
  axDbResultSet();

private:

  axDbResultSet(const axDbResultSet &);

};

#endif // _axDbResultSet_hpp_
