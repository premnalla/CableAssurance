
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCALoadDbConfig_hpp_
#define _axCALoadDbConfig_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"

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
 * file/class: axCALoadDbConfig.hpp
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

class axCALoadDbConfig 
{
public:

  /**
   * 
   */
  static AX_INT8 * cmts_poll_interval;
  static AX_INT8 * mta_poll_interval;
  static AX_INT8 * mta_ping_interval;
  static AX_INT8 * cm_poll_interval;

  static AX_INT8 * hfc_cm_offline_alarm_threshold_1_cm;
  static AX_INT8 * hfc_cm_offline_alarm_threshold_1;
  static AX_INT8 * hfc_cm_offline_alarm_threshold_2_cm;
  static AX_INT8 * hfc_cm_offline_alarm_threshold_2;
  static AX_INT8 * hfc_cm_offline_alarm_detection_window;
  static AX_INT8 * hfc_cm_offline_soak_win_1_start_tm;
  static AX_INT8 * hfc_cm_offline_soak_win_2_start_tm;
  static AX_INT8 * hfc_cm_offline_soak_win_1_duration;
  static AX_INT8 * hfc_cm_offline_soak_win_2_duration;

  static AX_INT8 * hfc_mta_unavail_alarm_threshold_1;
  static AX_INT8 * hfc_mta_unavail_alarm_detection_window;
  static AX_INT8 * hfc_mta_unavail_soak_win_1_start_tm;
  static AX_INT8 * hfc_mta_unavail_soak_win_2_start_tm;
  static AX_INT8 * hfc_mta_unavail_soak_win_1_duration;
  static AX_INT8 * hfc_mta_unavail_soak_win_2_duration;

  static AX_INT8 * hfc_power_alarm_threshold_1;
  static AX_INT8 * hfc_power_alarm_detection_window;
  static AX_INT8 * hfc_power_soak_win_1_start_tm;
  static AX_INT8 * hfc_power_soak_win_2_start_tm;
  static AX_INT8 * hfc_power_soak_win_1_duration;
  static AX_INT8 * hfc_power_soak_win_2_duration;

  static AX_INT8 * mta_unavail_soak_win_1_start_tm;
  static AX_INT8 * mta_unavail_soak_win_2_start_tm;
  static AX_INT8 * mta_unavail_soak_win_1_duration;
  static AX_INT8 * mta_unavail_soak_win_2_duration;

  static AX_INT8 * mta_onbatt_soak_win_1_start_tm;
  static AX_INT8 * mta_onbatt_soak_win_2_start_tm;
  static AX_INT8 * mta_onbatt_soak_win_1_duration;
  static AX_INT8 * mta_onbatt_soak_win_2_duration;

  static AX_INT8 * mta_battmiss_soak_win_1_start_tm;
  static AX_INT8 * mta_battmiss_soak_win_2_start_tm;
  static AX_INT8 * mta_battmiss_soak_win_1_duration;
  static AX_INT8 * mta_battmiss_soak_win_2_duration;

  static AX_INT8 * mta_replbatt_soak_win_1_start_tm;
  static AX_INT8 * mta_replbatt_soak_win_2_start_tm;
  static AX_INT8 * mta_replbatt_soak_win_1_duration;
  static AX_INT8 * mta_replbatt_soak_win_2_duration;

  static AX_INT8 * mta_cmsloc_soak_win_1_start_tm;
  static AX_INT8 * mta_cmsloc_soak_win_2_start_tm;
  static AX_INT8 * mta_cmsloc_soak_win_1_duration;
  static AX_INT8 * mta_cmsloc_soak_win_2_duration;

  static AX_INT8 * mta_hwfail_soak_win_1_start_tm;
  static AX_INT8 * mta_hwfail_soak_win_2_start_tm;
  static AX_INT8 * mta_hwfail_soak_win_1_duration;
  static AX_INT8 * mta_hwfail_soak_win_2_duration;

  static AX_INT8 * mta_lcfail_soak_win_1_start_tm;
  static AX_INT8 * mta_lcfail_soak_win_2_start_tm;
  static AX_INT8 * mta_lcfail_soak_win_1_duration;
  static AX_INT8 * mta_lcfail_soak_win_2_duration;

  static AX_INT8 * cmts_comms_fail_soak_win_1_start_tm;
  static AX_INT8 * cmts_comms_fail_soak_win_2_start_tm;
  static AX_INT8 * cmts_comms_fail_soak_win_1_duration;
  static AX_INT8 * cmts_comms_fail_soak_win_2_duration;

  static AX_INT8 * cms_loc_soak_win_1_start_tm;
  static AX_INT8 * cms_loc_soak_win_2_start_tm;
  static AX_INT8 * cms_loc_soak_win_1_duration;
  static AX_INT8 * cms_loc_soak_win_2_duration;

  static AX_INT8 * cm_perf_downstream_snr_lower;
  static AX_INT8 * cm_perf_downstream_power_upper;
  static AX_INT8 * cm_perf_downstream_power_lower;
  static AX_INT8 * cm_perf_upstream_power_upper;
  static AX_INT8 * cm_perf_upstream_power_lower;

  /**
   * default constructor
   */
  axCALoadDbConfig();

  /// destructor
  virtual ~axCALoadDbConfig();

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
  bool loadConfig(void);

protected:


private:

  /// not allowed
  axCALoadDbConfig(const axCALoadDbConfig &);

};

#endif // _axCALoadDbConfig_hpp_
