
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALoadDbConfig.hpp"
#include "axListFree.hpp"
#include "axDbAppConfig.hpp"
#include "axCASystemConfig.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axCALoadDbConfig::cmts_poll_interval = "cmts_poll_interval";
AX_INT8 * axCALoadDbConfig::mta_poll_interval = "mta_poll_interval";
AX_INT8 * axCALoadDbConfig::mta_ping_interval = "mta_ping_interval";
AX_INT8 * axCALoadDbConfig::cm_poll_interval = "cm_poll_interval";

AX_INT8 * axCALoadDbConfig::hfc_cm_offline_alarm_threshold_1_cm = "hfc_cm_offline_alarm_threshold_1_cm";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_alarm_threshold_1 = "hfc_cm_offline_alarm_threshold_1";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_alarm_threshold_2_cm = "hfc_cm_offline_alarm_threshold_2_cm";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_alarm_threshold_2 = "hfc_cm_offline_alarm_threshold_2";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_alarm_detection_window = "hfc_cm_offline_alarm_detection_window";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_soak_win_1_start_tm = "hfc_cm_offline_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_soak_win_2_start_tm = "hfc_cm_offline_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_soak_win_1_duration = "hfc_cm_offline_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::hfc_cm_offline_soak_win_2_duration = "hfc_cm_offline_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::hfc_mta_unavail_alarm_threshold_1 = "hfc_mta_unavail_alarm_threshold_1";
AX_INT8 * axCALoadDbConfig::hfc_mta_unavail_alarm_detection_window = "hfc_mta_unavail_alarm_detection_window";
AX_INT8 * axCALoadDbConfig::hfc_mta_unavail_soak_win_1_start_tm = "hfc_mta_unavail_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::hfc_mta_unavail_soak_win_2_start_tm = "hfc_mta_unavail_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::hfc_mta_unavail_soak_win_1_duration = "hfc_mta_unavail_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::hfc_mta_unavail_soak_win_2_duration = "hfc_mta_unavail_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::hfc_power_alarm_threshold_1 = "hfc_power_alarm_threshold_1";
AX_INT8 * axCALoadDbConfig::hfc_power_alarm_detection_window = "hfc_power_alarm_detection_window";
AX_INT8 * axCALoadDbConfig::hfc_power_soak_win_1_start_tm = "hfc_power_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::hfc_power_soak_win_2_start_tm = "hfc_power_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::hfc_power_soak_win_1_duration = "hfc_power_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::hfc_power_soak_win_2_duration = "hfc_power_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_unavail_soak_win_1_start_tm = "mta_unavail_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_unavail_soak_win_2_start_tm = "mta_unavail_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_unavail_soak_win_1_duration = "mta_unavail_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_unavail_soak_win_2_duration = "mta_unavail_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_onbatt_soak_win_1_start_tm = "mta_onbatt_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_onbatt_soak_win_2_start_tm = "mta_onbatt_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_onbatt_soak_win_1_duration = "mta_onbatt_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_onbatt_soak_win_2_duration = "mta_onbatt_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_battmiss_soak_win_1_start_tm = "mta_battmiss_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_battmiss_soak_win_2_start_tm = "mta_battmiss_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_battmiss_soak_win_1_duration = "mta_battmiss_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_battmiss_soak_win_2_duration = "mta_battmiss_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_replbatt_soak_win_1_start_tm = "mta_replbatt_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_replbatt_soak_win_2_start_tm = "mta_replbatt_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_replbatt_soak_win_1_duration = "mta_replbatt_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_replbatt_soak_win_2_duration = "mta_replbatt_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_cmsloc_soak_win_1_start_tm = "mta_cmsloc_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_cmsloc_soak_win_2_start_tm = "mta_cmsloc_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_cmsloc_soak_win_1_duration = "mta_cmsloc_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_cmsloc_soak_win_2_duration = "mta_cmsloc_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_hwfail_soak_win_1_start_tm = "mta_hwfail_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_hwfail_soak_win_2_start_tm = "mta_hwfail_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_hwfail_soak_win_1_duration = "mta_hwfail_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_hwfail_soak_win_2_duration = "mta_hwfail_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::mta_lcfail_soak_win_1_start_tm = "mta_lcfail_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::mta_lcfail_soak_win_2_start_tm = "mta_lcfail_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::mta_lcfail_soak_win_1_duration = "mta_lcfail_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::mta_lcfail_soak_win_2_duration = "mta_lcfail_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::cmts_comms_fail_soak_win_1_start_tm = "cmts_comms_fail_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::cmts_comms_fail_soak_win_2_start_tm = "cmts_comms_fail_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::cmts_comms_fail_soak_win_1_duration = "cmts_comms_fail_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::cmts_comms_fail_soak_win_2_duration = "cmts_comms_fail_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::cms_loc_soak_win_1_start_tm = "cms_loc_soak_win_1_start_tm";
AX_INT8 * axCALoadDbConfig::cms_loc_soak_win_2_start_tm = "cms_loc_soak_win_2_start_tm";
AX_INT8 * axCALoadDbConfig::cms_loc_soak_win_1_duration = "cms_loc_soak_win_1_duration";
AX_INT8 * axCALoadDbConfig::cms_loc_soak_win_2_duration = "cms_loc_soak_win_2_duration";

AX_INT8 * axCALoadDbConfig::cm_perf_downstream_snr_lower = "cm_perf_downstream_snr_lower";
AX_INT8 * axCALoadDbConfig::cm_perf_downstream_power_upper = "cm_perf_downstream_power_upper";
AX_INT8 * axCALoadDbConfig::cm_perf_downstream_power_lower = "cm_perf_downstream_power_lower";
AX_INT8 * axCALoadDbConfig::cm_perf_upstream_power_upper = "cm_perf_upstream_power_upper";
AX_INT8 * axCALoadDbConfig::cm_perf_upstream_power_lower = "cm_perf_upstream_power_lower";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCALoadDbConfig::axCALoadDbConfig()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCALoadDbConfig::~axCALoadDbConfig()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCALoadDbConfig::axCALoadDbConfig()
// {
// }


//********************************************************************
// method:
//********************************************************************
bool
axCALoadDbConfig::loadConfig(void)
{
  bool ret = false;

  axCASystemConfig * sysCfg;
  axDbAppConfig * dbConfig;
  axListFree l;
  axDbAppConfig tmpAppConfig;
  if (!tmpAppConfig.getRows(l)) {
    goto EXIT_LABEL;
  }

  sysCfg = axCASystemConfig::getInstance();

  tmpAppConfig.m_varName = cmts_poll_interval;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cmts_poll_interval(dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_poll_interval;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_poll_interval(dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_ping_interval;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_ping_interval(dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cm_poll_interval;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cm_poll_interval(dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_alarm_threshold_1_cm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_alarm_threshold_1_cm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_alarm_threshold_1;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_alarm_threshold_1(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_alarm_threshold_2_cm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_alarm_threshold_2_cm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_alarm_threshold_2;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_alarm_threshold_2(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_alarm_detection_window;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_alarm_detection_window(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_cm_offline_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_cm_offline_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_mta_unavail_alarm_threshold_1;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_mta_unavail_alarm_threshold_1(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_mta_unavail_alarm_detection_window;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_mta_unavail_alarm_detection_window(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_mta_unavail_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_mta_unavail_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_mta_unavail_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_mta_unavail_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_mta_unavail_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_mta_unavail_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_mta_unavail_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_mta_unavail_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_power_alarm_threshold_1;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_power_alarm_threshold_1(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_power_alarm_detection_window;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_power_alarm_detection_window(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_power_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_power_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_power_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_power_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_power_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_power_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = hfc_power_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_hfc_power_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_unavail_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_unavail_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_unavail_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_unavail_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_unavail_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_unavail_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_unavail_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_unavail_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_onbatt_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_onbatt_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_onbatt_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_onbatt_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_onbatt_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_onbatt_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_onbatt_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_onbatt_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_battmiss_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_battmiss_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_battmiss_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_battmiss_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_battmiss_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_battmiss_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_battmiss_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_battmiss_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_replbatt_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_replbatt_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_replbatt_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_replbatt_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_replbatt_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_replbatt_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_replbatt_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_replbatt_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_cmsloc_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_cmsloc_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_cmsloc_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_cmsloc_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_cmsloc_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_cmsloc_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_cmsloc_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_cmsloc_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_hwfail_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_hwfail_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_hwfail_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_hwfail_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_hwfail_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_hwfail_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_hwfail_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_hwfail_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_lcfail_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_lcfail_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_lcfail_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_lcfail_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_lcfail_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_lcfail_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = mta_lcfail_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_mta_lcfail_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cms_loc_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cms_loc_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cms_loc_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cms_loc_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cms_loc_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cms_loc_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cms_loc_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cms_loc_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cmts_comms_fail_soak_win_1_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cmts_comms_fail_soak_win_1_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cmts_comms_fail_soak_win_2_start_tm;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cmts_comms_fail_soak_win_2_start_tm(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cmts_comms_fail_soak_win_1_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cmts_comms_fail_soak_win_1_duration(
                                        dbConfig->m_varValue.c_str());
  }

  tmpAppConfig.m_varName = cmts_comms_fail_soak_win_2_duration;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cmts_comms_fail_soak_win_2_duration(
                                        dbConfig->m_varValue.c_str());
  }

  /*
   * CM Perf threshold values
   */
  tmpAppConfig.m_varName = cm_perf_downstream_snr_lower;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cm_snr_threshold(dbConfig->m_varValue.c_str());
  }
  tmpAppConfig.m_varName = cm_perf_downstream_power_upper;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cm_dwn_pwr_high_threshold(dbConfig->m_varValue.c_str());
  }
  tmpAppConfig.m_varName = cm_perf_downstream_power_lower;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cm_dwn_pwr_low_threshold(dbConfig->m_varValue.c_str());
  }
  tmpAppConfig.m_varName = cm_perf_upstream_power_upper;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cm_up_pwr_high_threshold(dbConfig->m_varValue.c_str());
  }
  tmpAppConfig.m_varName = cm_perf_upstream_power_lower;
  dbConfig = (axDbAppConfig *) l.find(&tmpAppConfig);
  if (dbConfig) {
    sysCfg->set_cm_up_pwr_low_threshold(dbConfig->m_varValue.c_str());
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}



