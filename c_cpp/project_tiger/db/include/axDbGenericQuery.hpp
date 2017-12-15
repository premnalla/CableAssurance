
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbGenericQuery_hpp_
#define _axDbGenericQuery_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSqlQueryOperation.hpp"
#include "axDbConnection.hpp"
#include "axDbXResource.hpp"
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
 * file/class: axDbGenericQuery.hpp
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

class axDbGenericQuery : public axSqlQueryOperation
{
public:

  /// destructor
  virtual ~axDbGenericQuery();

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
  virtual bool execute(const char *)=0;
  virtual bool execute(const char *, axDbXResource &)=0;
  virtual bool execute(const char *, axDbCmts &)=0;
  virtual bool execute(const char *, axDbChannel &)=0;
  virtual bool execute(const char *, axDbHfc &)=0;
  virtual bool execute(const char *, axDbCm &)=0;
  virtual bool execute(const char *, axDbEmta &)=0;
  virtual bool execute(const char *, axDbCmtsCurrentCounts &)=0;
  virtual bool execute(const char *, axDbCmtsCountsLog &)=0;
  virtual bool execute(const char *, axDbHfcCurrentCounts &)=0;
  virtual bool execute(const char *, axDbHfcCountsLog &)=0;
  virtual bool execute(const char *, axDbCurrentCmPerf &)=0;
  virtual bool execute(const char *, axDbCmPerfLog &)=0;
  virtual bool execute(const char *, axDbChannelCurrentCounts &)=0;
  virtual bool execute(const char *, axDbChannelCountsLog &)=0;
  virtual bool execute(const char *, axDbCmStatusLog &)=0;
  virtual bool execute(const char *, axDbMtaPingStatusLog &)=0;
  virtual bool execute(const char *, axDbMtaProvStatusLog &)=0;
  virtual bool execute(const char *, axDbMtaAvailLog &)=0;
  virtual bool execute(const char *, axDbCASoakingAlarm &)=0;
  virtual bool execute(const char *, axDbCAAlarmHistory &)=0;
  virtual bool execute(const char *, axDbCAAlarmDeviceSoaking &)=0;
  virtual bool execute(const char *, axDbCAAlarmDevicePostSoak &)=0;
  virtual bool execute(const char *, axDbCAAlarmCoincidentalSoaking &)=0;
  virtual bool execute(const char *, axDbCAAlarmCoincidentalPostSoak &)=0;
  virtual bool execute(const char *, axDbCAAlarmSecondary &)=0;
  virtual bool execute(const char *, axDbAppConfig &)=0;
  virtual bool execute(const char *, axDbSnmpV2CAttrs &)=0;
  virtual bool execute(const char *, axDbCARootAlarm &)=0;
  virtual bool execute(const char *, axDbCACurrentAlarm &)=0;
  virtual bool execute(const char *, axDbCAHistoricalAlarm &)=0;
  virtual bool execute(const char *, axDbMtaCurrentAvailability &)=0;
  virtual bool execute(const char *, axDbCmCurrentStatus &)=0;
  virtual bool execute(const char *, axDbSummaryFlags &)=0;
  virtual bool execute(const char *, axDbLastCompSummary &)=0;
  virtual bool execute(const char *, axDbHfcCurrentStatus &)=0;
  virtual bool execute(const char *, axDbHfcCurrentTca &)=0;
  virtual bool execute(const char *, axDbHfcStatusLog &)=0;
  virtual bool execute(const char *, axDbHfcTcaLog &)=0;
  virtual bool execute(const char *, axDbAbstractTopoLookup &)=0;
  virtual bool execute(const char *, axDbLocalSystem &)=0;
  virtual bool execute(const char *, axDbAbstractTopoContainer &)=0;
  virtual bool execute(const char *, axDbEmtaSecondary &)=0;

  /**
   *
   */
  virtual AX_INT64 getInsertId(void)=0;

  ///
  virtual void setUseResult(void)=0;
  virtual void setStoreResult(void)=0;
  virtual bool isStoreResult(void)=0;

protected:

  /// default constructor
  axDbGenericQuery();

  /// data constructor
  axDbGenericQuery(axDbConnection *);

private:

  axDbGenericQuery(const axDbGenericQuery &);

  // bool executeAndCreateResultSet(const char *);
  // bool m_storeResult;
};

#endif // _axDbGenericQuery_hpp_
