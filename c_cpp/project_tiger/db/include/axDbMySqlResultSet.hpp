
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMySqlResultSet_hpp_
#define _axDbMySqlResultSet_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbResultSet.hpp"
#include "axDbConnection.hpp"
// #include "axDbCmts.hpp"
// #include "axDbChannel.hpp"
// #include "axDbHfc.hpp"
// #include "axDbCm.hpp"
// #include "axDbEmta.hpp"

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
 * file/class: axDbMySqlResultSet.hpp
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

class axDbMySqlResultSet : public axDbResultSet
{
public:

  /// data constructor
  axDbMySqlResultSet(axDbConnection *, MYSQL_RES *);

  /// destructor
  virtual ~axDbMySqlResultSet();

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
  virtual bool getNext(axDbCmts &);
  virtual bool getNext(axDbCmts &, axDbCmtsCurrentCounts &);

  /**
   *
   */
  virtual bool getNext(axDbChannel &);
  virtual bool getNext(axDbChannel &, axDbChannelCurrentCounts &);

  /**
   *
   */
  virtual bool getNext(axDbHfc &);
  virtual bool getNext(axDbHfc &, axDbHfcCurrentCounts &,
                     axDbHfcCurrentStatus &, axDbHfcCurrentTca &);

  /**
   *
   */
  virtual bool getNext(axDbCm &);
  virtual bool getNext(axDbCm &, axDbCmCurrentStatus &, axDbCurrentCmPerf &);

  /**
   *
   */
  virtual bool getNext(axDbEmta &);
  virtual bool getNext(axDbEmta &, axDbMtaCurrentAvailability &);

  /**
   *
   */
  virtual bool getNext(axDbCmtsCurrentCounts &);

  /**
   *
   */
  virtual bool getNext(axDbCmtsCountsLog &);

  /**
   *
   */
  virtual bool getNext(axDbHfcCurrentCounts &);

  /**
   *
   */
  virtual bool getNext(axDbHfcCountsLog &);

  /**
   *
   */
  virtual bool getNext(axDbChannelCurrentCounts &);

  /**
   *
   */
  virtual bool getNext(axDbChannelCountsLog &);

  /**
   *
   */
  virtual bool getNext(axDbCurrentCmPerf &);

  /**
   *
   */
  virtual bool getNext(axDbCmPerfLog &);

  /**
   *
   */
  virtual bool getNext(axDbCmStatusLog &);

  /**
   *
   */
  virtual bool getNext(axDbMtaPingStatusLog &);

  /**
   *
   */
  virtual bool getNext(axDbMtaProvStatusLog &);

  /**
   *
   */
  virtual bool getNext(axDbMtaAvailLog &);

  /**
   *
   */
  virtual bool getNext(axDbCASoakingAlarm &);

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmHistory &);

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmDeviceSoaking &);

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmDevicePostSoak &);

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmCoincidentalSoaking &);

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmCoincidentalPostSoak &);

  /**
   *
   */
  virtual bool getNext(axDbCAAlarmSecondary &);

  /**
   *
   */
  virtual bool getNext(axDbAppConfig &);

  /**
   *
   */
  virtual bool getNext(axDbSnmpV2CAttrs &);

  /**
   *
   */
  virtual bool getNext(axDbCARootAlarm &);

  /**
   *
   */
  virtual bool getNext(axDbCACurrentAlarm &);

  /**
   *
   */
  virtual bool getNext(axDbCAHistoricalAlarm &);

  /**
   *
   */
  virtual bool getNext(axDbMtaCurrentAvailability &);

  /**
   *
   */
  virtual bool getNext(axDbCmCurrentStatus &);

  /**
   *
   */
  virtual bool getNext(axDbSummaryFlags &);

  /**
   *
   */
  virtual bool getNext(axDbLastCompSummary &);

  /**
   *
   */
  virtual bool getNext(axDbHfcCurrentStatus &);

  /**
   *
   */
  virtual bool getNext(axDbHfcCurrentTca &);

  /**
   *
   */
  virtual bool getNext(axDbHfcStatusLog &);

  /**
   *
   */
  virtual bool getNext(axDbHfcTcaLog &);

  /**
   *
   */
  virtual bool getNext(axDbAbstractTopoLookup &);

  /**
   *
   */
  virtual bool getNext(axDbLocalSystem &);

  /**
   *
   */
  virtual bool getNext(axDbAbstractTopoContainer &);

  /**
   *
   */
  virtual bool getNext(axDbEmtaSecondary &);

  //***************************
  //
  //***************************

  // virtual bool getNext(cmts &);
  // virtual bool getNext(cm &);
  // virtual bool getNext(emta &);
  // ...

protected:

  /// default constructor
  axDbMySqlResultSet();

  ///
  void getRow(axDbCm &, int &, MYSQL_ROW &, MYSQL_ROWCOL_LEN_t &);

  ///
  void getNextCurrentCounts(axDbAbstractCounts &, MYSQL_ROW &, 
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbCmts &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbChannel &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbHfc &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbAbstractCurrentStatus &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbCurrentCmPerf &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbEmta &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);

  ///
  void getNext_I(axDbHfcCurrentStatus &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);
  ///
  void getNext_I(axDbHfcCurrentTca &, MYSQL_ROW &,
                                     MYSQL_ROWCOL_LEN_t &, int &);

private:

  axDbMySqlResultSet(const axDbMySqlResultSet &);

  MYSQL_RES * m_rsHandle;
};

#endif // _axDbMySqlResultSet_hpp_
