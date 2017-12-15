
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCASystemConfig_hpp_
#define _axCASystemConfig_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axLockableObject.hpp"

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
 * file/class: axCASystemConfig.hpp
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

class axCASystemConfig : public axLockableObject
{
public:

  /// destructor
  virtual ~axCASystemConfig();

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
  static axCASystemConfig * getInstance(void);

  AX_UINT32 getNumRunnableExecutors(void);
  void      setNumRunnableExecutors(const AX_INT8 *);

  AX_UINT32 getCmPerfRunnablesPerRC(void);
  void      setCmPerfRunnablesPerRC(const AX_INT8 *);
  AX_UINT32 getCmPerPerfRunnable(void);
  void      setCmPerPerfRunnable(const AX_INT8 *);

  AX_UINT32 getMtaPollRunnablesPerRc(void);
  void      setMtaPollRunnablesPerRc(const AX_INT8 *);
  AX_UINT32 getMtaPerPollRunnable(void);
  void      setMtaPerPollRunnable(const AX_INT8 *);

  AX_UINT32 getMtaPingRunnablesPerRc(void);
  void      setMtaPingRunnablesPerRc(const AX_INT8 *);
  AX_UINT32 getMtaPerPingRunnable(void);
  void      setMtaPerPingRunnable(const AX_INT8 *);

  AX_UINT32 getNumTimerWorkers(void);
  void      setNumTimerWorkers(const AX_INT8 *);

  AX_UINT32 getNumSnmpSessions(void);
  void      setNumSnmpSessions(const AX_INT8 *);

  AX_UINT32 get_cmts_poll_interval(void);
  void      set_cmts_poll_interval(const AX_INT8 *);

  AX_UINT32 get_mta_poll_interval(void);
  void      set_mta_poll_interval(const AX_INT8 *);

  AX_UINT32 get_mta_ping_interval(void);
  void      set_mta_ping_interval(const AX_INT8 *);

  AX_UINT32 get_cm_poll_interval(void);
  void      set_cm_poll_interval(const AX_INT8 *);

  AX_UINT32 get_hfc_cm_offline_alarm_threshold(AX_UINT32);
  AX_UINT32 get_hfc_cm_offline_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_hfc_cm_offline_alarm_threshold_1_cm(void);
  void      set_hfc_cm_offline_alarm_threshold_1_cm(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_alarm_threshold_1(void);
  void      set_hfc_cm_offline_alarm_threshold_1(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_alarm_threshold_2_cm(void);
  void      set_hfc_cm_offline_alarm_threshold_2_cm(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_alarm_threshold_2(void);
  void      set_hfc_cm_offline_alarm_threshold_2(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_alarm_detection_window(void);
  void      set_hfc_cm_offline_alarm_detection_window(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_soak_win_1_start_tm(void);
  void      set_hfc_cm_offline_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_soak_win_2_start_tm(void);
  void      set_hfc_cm_offline_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_soak_win_1_duration(void);
  void      set_hfc_cm_offline_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_hfc_cm_offline_soak_win_2_duration(void);
  void      set_hfc_cm_offline_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_hfc_mta_unavail_alarm_threshold(AX_UINT32 mtaCount=0);
  AX_UINT32 get_hfc_mta_unavail_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_hfc_mta_unavail_alarm_threshold_1(void);
  void      set_hfc_mta_unavail_alarm_threshold_1(const AX_INT8 *);
  AX_UINT32 get_hfc_mta_unavail_alarm_detection_window(void);
  void      set_hfc_mta_unavail_alarm_detection_window(const AX_INT8 *);
  AX_UINT32 get_hfc_mta_unavail_soak_win_1_start_tm(void);
  void      set_hfc_mta_unavail_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_hfc_mta_unavail_soak_win_2_start_tm(void);
  void      set_hfc_mta_unavail_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_hfc_mta_unavail_soak_win_1_duration(void);
  void      set_hfc_mta_unavail_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_hfc_mta_unavail_soak_win_2_duration(void);
  void      set_hfc_mta_unavail_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_hfc_power_alarm_threshold(AX_UINT32 mtaCount=0);
  AX_UINT32 get_hfc_power_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_hfc_power_alarm_threshold_1(void);
  void      set_hfc_power_alarm_threshold_1(const AX_INT8 *);
  AX_UINT32 get_hfc_power_alarm_detection_window(void);
  void      set_hfc_power_alarm_detection_window(const AX_INT8 *);
  AX_UINT32 get_hfc_power_soak_win_1_start_tm(void);
  void      set_hfc_power_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_hfc_power_soak_win_2_start_tm(void);
  void      set_hfc_power_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_hfc_power_soak_win_1_duration(void);
  void      set_hfc_power_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_hfc_power_soak_win_2_duration(void);
  void      set_hfc_power_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_unavail_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_unavail_soak_win_1_start_tm(void);
  void      set_mta_unavail_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_unavail_soak_win_2_start_tm(void);
  void      set_mta_unavail_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_unavail_soak_win_1_duration(void);
  void      set_mta_unavail_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_unavail_soak_win_2_duration(void);
  void      set_mta_unavail_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_onbatt_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_onbatt_soak_win_1_start_tm(void);
  void      set_mta_onbatt_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_onbatt_soak_win_2_start_tm(void);
  void      set_mta_onbatt_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_onbatt_soak_win_1_duration(void);
  void      set_mta_onbatt_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_onbatt_soak_win_2_duration(void);
  void      set_mta_onbatt_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_battmiss_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_battmiss_soak_win_1_start_tm(void);
  void      set_mta_battmiss_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_battmiss_soak_win_2_start_tm(void);
  void      set_mta_battmiss_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_battmiss_soak_win_1_duration(void);
  void      set_mta_battmiss_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_battmiss_soak_win_2_duration(void);
  void      set_mta_battmiss_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_replbatt_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_replbatt_soak_win_1_start_tm(void);
  void      set_mta_replbatt_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_replbatt_soak_win_2_start_tm(void);
  void      set_mta_replbatt_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_replbatt_soak_win_1_duration(void);
  void      set_mta_replbatt_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_replbatt_soak_win_2_duration(void);
  void      set_mta_replbatt_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_cmsloc_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_cmsloc_soak_win_1_start_tm(void);
  void      set_mta_cmsloc_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_cmsloc_soak_win_2_start_tm(void);
  void      set_mta_cmsloc_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_cmsloc_soak_win_1_duration(void);
  void      set_mta_cmsloc_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_cmsloc_soak_win_2_duration(void);
  void      set_mta_cmsloc_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_hwfail_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_hwfail_soak_win_1_start_tm(void);
  void      set_mta_hwfail_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_hwfail_soak_win_2_start_tm(void);
  void      set_mta_hwfail_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_hwfail_soak_win_1_duration(void);
  void      set_mta_hwfail_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_hwfail_soak_win_2_duration(void);
  void      set_mta_hwfail_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_mta_lcfail_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_mta_lcfail_soak_win_1_start_tm(void);
  void      set_mta_lcfail_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_lcfail_soak_win_2_start_tm(void);
  void      set_mta_lcfail_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_mta_lcfail_soak_win_1_duration(void);
  void      set_mta_lcfail_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_mta_lcfail_soak_win_2_duration(void);
  void      set_mta_lcfail_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_cmts_comms_fail_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_cmts_comms_fail_soak_win_1_start_tm(void);
  void      set_cmts_comms_fail_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_cmts_comms_fail_soak_win_2_start_tm(void);
  void      set_cmts_comms_fail_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_cmts_comms_fail_soak_win_1_duration(void);
  void      set_cmts_comms_fail_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_cmts_comms_fail_soak_win_2_duration(void);
  void      set_cmts_comms_fail_soak_win_2_duration(const AX_INT8 *);

  AX_UINT32 get_cms_loc_soak_win_duration(AX_UINT32);
  //
  AX_UINT32 get_cms_loc_soak_win_1_start_tm(void);
  void      set_cms_loc_soak_win_1_start_tm(const AX_INT8 *);
  AX_UINT32 get_cms_loc_soak_win_2_start_tm(void);
  void      set_cms_loc_soak_win_2_start_tm(const AX_INT8 *);
  AX_UINT32 get_cms_loc_soak_win_1_duration(void);
  void      set_cms_loc_soak_win_1_duration(const AX_INT8 *);
  AX_UINT32 get_cms_loc_soak_win_2_duration(void);
  void      set_cms_loc_soak_win_2_duration(const AX_INT8 *);

  bool      getLoadOneCmts(void);
  void      setLoadOneCmts(const AX_INT8 *);
  AX_UINT32 get_startup_cmts_poll_stagger(void);
  void      set_startup_cmts_poll_stagger(const AX_INT8 *);
  AX_UINT32 get_startup_mta_poll_delay(void);
  void      set_startup_mta_poll_delay(const AX_INT8 *);
  AX_UINT32 get_startup_mta_ping_delay(void);
  void      set_startup_mta_ping_delay(const AX_INT8 *);
  AX_UINT32 get_startup_mta_poll_stagger(void);
  void      set_startup_mta_poll_stagger(const AX_INT8 *);
  AX_UINT32 get_startup_mta_ping_stagger(void);
  void      set_startup_mta_ping_stagger(const AX_INT8 *);

  /**
   * See: m_unchangedLogInterval data member for details.
   */
  AX_UINT32 get_unchanged_log_interval(void);
  void      set_unchanged_log_interval(const AX_INT8 *);

  /**
   * See: m_taskRescheduleInterval data member for details.
   */
  AX_UINT32 get_task_postpone_interval(void);
  void      set_task_postpone_interval(const AX_INT8 *);

  /**
   * See: m_set_soap_services_port data member for details.
   */
  AX_UINT32 get_soap_services_port(void);
  void      set_soap_services_port(const AX_INT8 *);

  /**
   * See: m_soap_services_backlog data member for details.
   */
  AX_UINT32 get_soap_services_backlog(void);
  void      set_soap_services_backlog(const AX_INT8 *);

  /**
   * See: m_soap_service_req_processors data member for details.
   */
  AX_UINT32 get_soap_service_req_processors(void);
  void      set_soap_service_req_processors(const AX_INT8 *);

  /**
   * Set/Get CM Performance Threshold params
   */
  AX_INT16 get_cm_snr_threshold(void);
  void     set_cm_snr_threshold(const AX_INT8 *);
  AX_INT16 get_cm_dwn_pwr_high_threshold(void);
  void     set_cm_dwn_pwr_high_threshold(const AX_INT8 *);
  AX_INT16 get_cm_dwn_pwr_low_threshold(void);
  void     set_cm_dwn_pwr_low_threshold(const AX_INT8 *);
  AX_INT16 get_cm_up_pwr_high_threshold(void);
  void     set_cm_up_pwr_high_threshold(const AX_INT8 *);
  AX_INT16 get_cm_up_pwr_low_threshold(void);
  void     set_cm_up_pwr_low_threshold(const AX_INT8 *);

  /**
   * Set/Get Max DB connections
   */
  AX_INT16 get_max_db_read_conns(void);
  void     set_max_db_read_conns(const AX_INT8 *);
  AX_INT16 get_max_db_write_conns(void);
  void     set_max_db_write_conns(const AX_INT8 *);

protected:

  /// default constructor
  axCASystemConfig();

private:

  axCASystemConfig(const axCASystemConfig &);

  static axCASystemConfig * m_instance;

  bool       m_initialized;

  AX_UINT32  m_numRunnableExecutors;

  AX_UINT32  m_numCmPerfRunnablesPerRC;
  AX_UINT32  m_numCmPerPerfRunnable;

  AX_UINT32  m_numMtaPollRunnablesPerRC;
  AX_UINT32  m_numMtaPerPollRunnable;

  AX_UINT32  m_numMtaPingRunnablesPerRC;
  AX_UINT32  m_numMtaPerPingRunnable;

  AX_UINT32  m_numTimerWorkers;

  AX_UINT32  m_numSnmpSessions;

  AX_ULONG   m_logMask;

  AX_UINT32  m_cmts_poll_interval;
  AX_UINT32  m_mta_poll_interval;
  AX_UINT32  m_mta_ping_interval;
  AX_UINT32  m_cm_poll_interval;

  AX_UINT32  hfc_cm_offline_alarm_threshold_1_cm;
  AX_UINT32  hfc_cm_offline_alarm_threshold_1;
  AX_UINT32  hfc_cm_offline_alarm_threshold_2_cm;
  AX_UINT32  hfc_cm_offline_alarm_threshold_2;
  AX_UINT32  hfc_cm_offline_alarm_detection_window;
  AX_UINT32  hfc_cm_offline_soak_win_1_start_tm;
  AX_UINT32  hfc_cm_offline_soak_win_2_start_tm;
  AX_UINT32  hfc_cm_offline_soak_win_1_duration;
  AX_UINT32  hfc_cm_offline_soak_win_2_duration;

  AX_UINT32  hfc_mta_unavail_alarm_threshold_1;
  AX_UINT32  hfc_mta_unavail_alarm_detection_window;
  AX_UINT32  hfc_mta_unavail_soak_win_1_start_tm;
  AX_UINT32  hfc_mta_unavail_soak_win_2_start_tm;
  AX_UINT32  hfc_mta_unavail_soak_win_1_duration;
  AX_UINT32  hfc_mta_unavail_soak_win_2_duration;

  AX_UINT32  hfc_power_alarm_threshold_1;
  AX_UINT32  hfc_power_alarm_detection_window;
  AX_UINT32  hfc_power_soak_win_1_start_tm;
  AX_UINT32  hfc_power_soak_win_2_start_tm;
  AX_UINT32  hfc_power_soak_win_1_duration;
  AX_UINT32  hfc_power_soak_win_2_duration;

  AX_UINT32  mta_unavail_soak_win_1_start_tm;
  AX_UINT32  mta_unavail_soak_win_2_start_tm;
  AX_UINT32  mta_unavail_soak_win_1_duration;
  AX_UINT32  mta_unavail_soak_win_2_duration;

  AX_UINT32  mta_onbatt_soak_win_1_start_tm;
  AX_UINT32  mta_onbatt_soak_win_2_start_tm;
  AX_UINT32  mta_onbatt_soak_win_1_duration;
  AX_UINT32  mta_onbatt_soak_win_2_duration;

  AX_UINT32  mta_battmiss_soak_win_1_start_tm;
  AX_UINT32  mta_battmiss_soak_win_2_start_tm;
  AX_UINT32  mta_battmiss_soak_win_1_duration;
  AX_UINT32  mta_battmiss_soak_win_2_duration;

  AX_UINT32  mta_replbatt_soak_win_1_start_tm;
  AX_UINT32  mta_replbatt_soak_win_2_start_tm;
  AX_UINT32  mta_replbatt_soak_win_1_duration;
  AX_UINT32  mta_replbatt_soak_win_2_duration;

  AX_UINT32  mta_cmsloc_soak_win_1_start_tm;
  AX_UINT32  mta_cmsloc_soak_win_2_start_tm;
  AX_UINT32  mta_cmsloc_soak_win_1_duration;
  AX_UINT32  mta_cmsloc_soak_win_2_duration;

  AX_UINT32  mta_hwfail_soak_win_1_start_tm;
  AX_UINT32  mta_hwfail_soak_win_2_start_tm;
  AX_UINT32  mta_hwfail_soak_win_1_duration;
  AX_UINT32  mta_hwfail_soak_win_2_duration;

  AX_UINT32  mta_lcfail_soak_win_1_start_tm;
  AX_UINT32  mta_lcfail_soak_win_2_start_tm;
  AX_UINT32  mta_lcfail_soak_win_1_duration;
  AX_UINT32  mta_lcfail_soak_win_2_duration;

  AX_UINT32  cmts_comms_fail_soak_win_1_start_tm;
  AX_UINT32  cmts_comms_fail_soak_win_2_start_tm;
  AX_UINT32  cmts_comms_fail_soak_win_1_duration;
  AX_UINT32  cmts_comms_fail_soak_win_2_duration;

  AX_UINT32  cms_loc_soak_win_1_start_tm;
  AX_UINT32  cms_loc_soak_win_2_start_tm;
  AX_UINT32  cms_loc_soak_win_1_duration;
  AX_UINT32  cms_loc_soak_win_2_duration;

  bool       m_LoadOneCmts;
  AX_UINT32  m_startup_cmts_poll_stagger;
  AX_UINT32  m_startup_mta_poll_delay;
  AX_UINT32  m_startup_mta_ping_delay;
  AX_UINT32  m_startup_mta_poll_stagger;
  AX_UINT32  m_startup_mta_ping_stagger;

  /**
   * This is an interval. Usage e.g.: if status of MTA unchanged
   * during this interval, add a new log entry in the DB.
   * Units: minutes
   */
  AX_UINT32  m_unchangedLogInterval;

  /**
   * The interval to postpone a task due to primarily
   * the Summary engine running (or waiting to run)
   * Units: seconds
   */
  AX_UINT32  m_taskPostponeInterval;

  /**
   * The SOAP services port
   */
  AX_UINT32  m_soap_services_port;

  /**
   * The SOAP services backlog for soap_bind() method
   */
  AX_UINT32  m_soap_services_backlog;

  /**
   * Number of SOAP services request processors
   */
  AX_UINT32  m_soap_service_req_processors;

  /**
   * CM Performance thresholds
   */
  AX_INT16  m_cm_snr_threshold;
  AX_INT16  m_cm_dwn_pwr_high_threshold;
  AX_INT16  m_cm_dwn_pwr_low_threshold;
  AX_INT16  m_cm_up_pwr_high_threshold;
  AX_INT16  m_cm_up_pwr_low_threshold;

  AX_INT16  m_max_db_read_conns;
  AX_INT16  m_max_db_write_conns;

};

#endif // _axCASystemConfig_hpp_
