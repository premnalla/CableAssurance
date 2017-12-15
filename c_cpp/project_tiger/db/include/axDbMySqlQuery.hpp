
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMySqlQuery_hpp_
#define _axDbMySqlQuery_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbGenericQuery.hpp"
#include "axDbMySqlConnection.hpp"
// #include "axDbXResource.hpp"
// #include "axDbCmts.hpp"
// #include "axDbChannel.hpp"
// #include "axDbHfc.hpp"
// #include "axDbCm.hpp"

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
 * file/class: axDbMySqlQuery.hpp
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

class axDbMySqlQuery : public axDbGenericQuery
{
public:

  /// data constructor
  axDbMySqlQuery(axDbMySqlConnection *);

  /// destructor
  virtual ~axDbMySqlQuery();

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
  virtual bool execute(const char *);
  virtual bool execute(const char *, axDbXResource &);
  virtual bool execute(const char *, axDbCmts &);
  virtual bool execute(const char *, axDbChannel &);
  virtual bool execute(const char *, axDbHfc &);
  virtual bool execute(const char *, axDbCm &);
  virtual bool execute(const char *, axDbEmta &);
  virtual bool execute(const char *, axDbCmtsCurrentCounts &);
  virtual bool execute(const char *, axDbCmtsCountsLog &);
  virtual bool execute(const char *, axDbHfcCurrentCounts &);
  virtual bool execute(const char *, axDbHfcCountsLog &);
  virtual bool execute(const char *, axDbCurrentCmPerf &);
  virtual bool execute(const char *, axDbCmPerfLog &);
  virtual bool execute(const char *, axDbChannelCurrentCounts &);
  virtual bool execute(const char *, axDbChannelCountsLog &);
  virtual bool execute(const char *, axDbCmStatusLog &);
  virtual bool execute(const char *, axDbMtaPingStatusLog &);
  virtual bool execute(const char *, axDbMtaProvStatusLog &);
  virtual bool execute(const char *, axDbMtaAvailLog &);
  virtual bool execute(const char *, axDbCASoakingAlarm &);
  virtual bool execute(const char *, axDbCAAlarmHistory &);
  virtual bool execute(const char *, axDbCAAlarmDeviceSoaking &);
  virtual bool execute(const char *, axDbCAAlarmDevicePostSoak &);
  virtual bool execute(const char *, axDbCAAlarmCoincidentalSoaking &);
  virtual bool execute(const char *, axDbCAAlarmCoincidentalPostSoak &);
  virtual bool execute(const char *, axDbCAAlarmSecondary &);
  virtual bool execute(const char *, axDbAppConfig &);
  virtual bool execute(const char *, axDbSnmpV2CAttrs &);
  virtual bool execute(const char *, axDbCARootAlarm &);
  virtual bool execute(const char *, axDbCACurrentAlarm &);
  virtual bool execute(const char *, axDbCAHistoricalAlarm &);
  virtual bool execute(const char *, axDbMtaCurrentAvailability &);
  virtual bool execute(const char *, axDbCmCurrentStatus &);
  virtual bool execute(const char *, axDbSummaryFlags &);
  virtual bool execute(const char *, axDbLastCompSummary &);
  virtual bool execute(const char *, axDbHfcCurrentStatus &);
  virtual bool execute(const char *, axDbHfcCurrentTca &);
  virtual bool execute(const char *, axDbHfcStatusLog &);
  virtual bool execute(const char *, axDbHfcTcaLog &);
  virtual bool execute(const char *, axDbAbstractTopoLookup &);
  virtual bool execute(const char *, axDbLocalSystem &);
  virtual bool execute(const char *, axDbAbstractTopoContainer &);
  virtual bool execute(const char *, axDbEmtaSecondary &);

  /**
   *
   */
  virtual AX_INT64 getInsertId(void);

  ///
  virtual void setUseResult(void);
  virtual void setStoreResult(void);
  virtual bool isStoreResult(void);

protected:

  /// default constructor
  axDbMySqlQuery();

private:

  axDbMySqlQuery(const axDbMySqlQuery &);

  bool executeAndCreateResultSet(const char *);

  ///
  bool m_storeResult;

};

#endif // _axDbMySqlQuery_hpp_
