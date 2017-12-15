
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCASystemConfig.hpp"
#include "axMultipleReaderLock.hpp"
#include "axDate.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCASystemConfig * axCASystemConfig::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCASystemConfig::axCASystemConfig() :
  axLockableObject(new axMultipleReaderLock()),
  m_initialized(false),
  m_numRunnableExecutors(0),
  m_numCmPerfRunnablesPerRC(0),
  m_numCmPerPerfRunnable(0),
  m_numMtaPollRunnablesPerRC(0),
  m_numMtaPerPollRunnable(0),
  m_numMtaPingRunnablesPerRC(0),
  m_numMtaPerPingRunnable(0),
  m_numTimerWorkers(0),
  m_numSnmpSessions(0),
  m_logMask(0),
  m_cmts_poll_interval(10),
  m_mta_poll_interval(5),
  m_mta_ping_interval(5),
  m_cm_poll_interval(15),

  hfc_cm_offline_alarm_threshold_1_cm(0),
  hfc_cm_offline_alarm_threshold_1(0),
  hfc_cm_offline_alarm_threshold_2_cm(0),
  hfc_cm_offline_alarm_threshold_2(0),
  hfc_cm_offline_alarm_detection_window(0),
  hfc_cm_offline_soak_win_1_start_tm(0),
  hfc_cm_offline_soak_win_2_start_tm(0),
  hfc_cm_offline_soak_win_1_duration(0),
  hfc_cm_offline_soak_win_2_duration(0),

  hfc_mta_unavail_alarm_threshold_1(0),
  hfc_mta_unavail_alarm_detection_window(0),
  hfc_mta_unavail_soak_win_1_start_tm(0),
  hfc_mta_unavail_soak_win_2_start_tm(0),
  hfc_mta_unavail_soak_win_1_duration(0),
  hfc_mta_unavail_soak_win_2_duration(0),

  hfc_power_alarm_threshold_1(0),
  hfc_power_alarm_detection_window(0),
  hfc_power_soak_win_1_start_tm(0),
  hfc_power_soak_win_2_start_tm(0),
  hfc_power_soak_win_1_duration(0),
  hfc_power_soak_win_2_duration(0),

  mta_unavail_soak_win_1_start_tm(0),
  mta_unavail_soak_win_2_start_tm(0),
  mta_unavail_soak_win_1_duration(0),
  mta_unavail_soak_win_2_duration(0),

  mta_onbatt_soak_win_1_start_tm(0),
  mta_onbatt_soak_win_2_start_tm(0),
  mta_onbatt_soak_win_1_duration(0),
  mta_onbatt_soak_win_2_duration(0),

  mta_battmiss_soak_win_1_start_tm(0),
  mta_battmiss_soak_win_2_start_tm(0),
  mta_battmiss_soak_win_1_duration(0),
  mta_battmiss_soak_win_2_duration(0),

  mta_replbatt_soak_win_1_start_tm(0),
  mta_replbatt_soak_win_2_start_tm(0),
  mta_replbatt_soak_win_1_duration(0),
  mta_replbatt_soak_win_2_duration(0),

  mta_cmsloc_soak_win_1_start_tm(0),
  mta_cmsloc_soak_win_2_start_tm(0),
  mta_cmsloc_soak_win_1_duration(0),
  mta_cmsloc_soak_win_2_duration(0),

  mta_hwfail_soak_win_1_start_tm(0),
  mta_hwfail_soak_win_2_start_tm(0),
  mta_hwfail_soak_win_1_duration(0),
  mta_hwfail_soak_win_2_duration(0),

  mta_lcfail_soak_win_1_start_tm(0),
  mta_lcfail_soak_win_2_start_tm(0),
  mta_lcfail_soak_win_1_duration(0),
  mta_lcfail_soak_win_2_duration(0),

  cmts_comms_fail_soak_win_1_start_tm(0),
  cmts_comms_fail_soak_win_2_start_tm(0),
  cmts_comms_fail_soak_win_1_duration(0),
  cmts_comms_fail_soak_win_2_duration(0),

  cms_loc_soak_win_1_start_tm(0),
  cms_loc_soak_win_2_start_tm(0),
  cms_loc_soak_win_1_duration(0),
  cms_loc_soak_win_2_duration(0),

  m_LoadOneCmts(false),
  m_startup_cmts_poll_stagger(0),
  m_startup_mta_poll_delay(0),
  m_startup_mta_ping_delay(0),
  m_startup_mta_poll_stagger(0),
  m_startup_mta_ping_stagger(0),
  m_unchangedLogInterval(60*60*6),
  m_taskPostponeInterval(30),
  m_soap_services_port(9091),
  m_soap_services_backlog(100),
  m_soap_service_req_processors(10),

  m_cm_snr_threshold(0),
  m_cm_dwn_pwr_high_threshold(0),
  m_cm_dwn_pwr_low_threshold(0),
  m_cm_up_pwr_high_threshold(0),
  m_cm_up_pwr_low_threshold(0),

  m_max_db_read_conns(5),
  m_max_db_write_conns(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCASystemConfig::~axCASystemConfig()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCASystemConfig::axCASystemConfig()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCASystemConfig *
axCASystemConfig::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCASystemConfig();
  }
  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setNumRunnableExecutors(const AX_INT8 * val)
{
  m_numRunnableExecutors = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getNumRunnableExecutors(void)
{
  return (m_numRunnableExecutors);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setCmPerfRunnablesPerRC(const AX_INT8 * val)
{
  m_numCmPerfRunnablesPerRC = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getCmPerfRunnablesPerRC(void)
{
  return (m_numCmPerfRunnablesPerRC);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setCmPerPerfRunnable(const AX_INT8 * val)
{
  m_numCmPerPerfRunnable = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getCmPerPerfRunnable(void)
{
  return (m_numCmPerPerfRunnable);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setMtaPollRunnablesPerRc(const AX_INT8 * val)
{
  m_numMtaPollRunnablesPerRC = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getMtaPollRunnablesPerRc(void)
{
  return (m_numMtaPollRunnablesPerRC);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setMtaPerPollRunnable(const AX_INT8 * val)
{
  m_numMtaPerPollRunnable = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getMtaPerPollRunnable(void)
{
  return (m_numMtaPerPollRunnable);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setMtaPingRunnablesPerRc(const AX_INT8 * val)
{
  m_numMtaPingRunnablesPerRC = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getMtaPingRunnablesPerRc(void)
{
  return (m_numMtaPingRunnablesPerRC);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setMtaPerPingRunnable(const AX_INT8 * val)
{
  m_numMtaPerPingRunnable = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getMtaPerPingRunnable(void)
{
  return (m_numMtaPerPingRunnable);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setNumTimerWorkers(const AX_INT8 * val)
{
  m_numTimerWorkers = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getNumTimerWorkers(void)
{
  return (m_numTimerWorkers);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_alarm_threshold(AX_UINT32 totalCm)
{
  AX_UINT32 ret;

  if (totalCm <= hfc_cm_offline_alarm_threshold_1_cm) {
    ret = hfc_cm_offline_alarm_threshold_1;
  } else if (totalCm <= hfc_cm_offline_alarm_threshold_2_cm) {
    ret = hfc_cm_offline_alarm_threshold_2;
  } else {
    ret = hfc_cm_offline_alarm_threshold_2;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_soak_win_duration(AX_UINT32 tm)
{
  AX_UINT32 ret;

  axDate d(tm);

  AX_UINT32 h = d.getHour();
  // int m = d.getMinute();

  if (h >= hfc_cm_offline_soak_win_1_duration &&
      h < hfc_cm_offline_soak_win_2_duration) {
    ret = hfc_cm_offline_soak_win_1_duration;
  } else {
    ret = hfc_cm_offline_soak_win_2_duration;
  }

  return (ret);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_alarm_threshold_1_cm(const AX_INT8 * val)
{
  hfc_cm_offline_alarm_threshold_1_cm = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_alarm_threshold_1_cm(void)
{
  return (hfc_cm_offline_alarm_threshold_1_cm);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_alarm_threshold_1(void)
{
  return (hfc_cm_offline_alarm_threshold_1);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_alarm_threshold_1(const AX_INT8 * val)
{
  hfc_cm_offline_alarm_threshold_1 = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_alarm_threshold_2_cm(void)
{
  return (hfc_cm_offline_alarm_threshold_2_cm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_alarm_threshold_2_cm(const AX_INT8 * val)
{
  hfc_cm_offline_alarm_threshold_2_cm = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_alarm_threshold_2(void)
{
  return (hfc_cm_offline_alarm_threshold_2);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_alarm_threshold_2(const AX_INT8 * val)
{
  hfc_cm_offline_alarm_threshold_2 = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_alarm_detection_window(void)
{
  return (hfc_cm_offline_alarm_detection_window);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_alarm_detection_window(const AX_INT8 * val)
{
  hfc_cm_offline_alarm_detection_window = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_soak_win_1_start_tm(void)
{
  return (hfc_cm_offline_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_soak_win_1_start_tm(const AX_INT8 * val)
{
  hfc_cm_offline_soak_win_1_start_tm = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_soak_win_2_start_tm(void)
{
  return (hfc_cm_offline_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_soak_win_2_start_tm(const AX_INT8 * val)
{
  hfc_cm_offline_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_soak_win_1_duration(void)
{
  return (hfc_cm_offline_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_soak_win_1_duration(const AX_INT8 * val)
{
  hfc_cm_offline_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_cm_offline_soak_win_2_duration(void)
{
  return (hfc_cm_offline_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_cm_offline_soak_win_2_duration(const AX_INT8 * val)
{
  hfc_cm_offline_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
// description: It is redundant at the moment. It will make sense if
//              we have more than one threshold like the CM % offline
//              case.
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_alarm_threshold(AX_UINT32 mtaCount)
{
  AX_UINT32 ret;

  if (!mtaCount) {
    ret = hfc_mta_unavail_alarm_threshold_1;
  } else {
    ret = hfc_mta_unavail_alarm_threshold_1;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_alarm_threshold_1(void)
{
  return (hfc_mta_unavail_alarm_threshold_1);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_mta_unavail_alarm_threshold_1(const AX_INT8 * val)
{
  hfc_mta_unavail_alarm_threshold_1 = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_alarm_detection_window(void)
{
  return (hfc_mta_unavail_alarm_detection_window);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_mta_unavail_alarm_detection_window(const AX_INT8 * val)
{
  hfc_mta_unavail_alarm_detection_window = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_soak_win_duration(AX_UINT32 tmSec)
{
  AX_UINT32 ret;

  axDate d(tmSec);

  AX_UINT32 h = d.getHour();
  // int m = d.getMinute();

  if (h >= hfc_mta_unavail_soak_win_1_duration &&
      h < hfc_mta_unavail_soak_win_2_duration) {
    ret = hfc_mta_unavail_soak_win_1_duration;
  } else {
    ret = hfc_mta_unavail_soak_win_2_duration;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_soak_win_1_start_tm(void)
{
  return (hfc_mta_unavail_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_mta_unavail_soak_win_1_start_tm(const AX_INT8 * val)
{
  hfc_mta_unavail_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_soak_win_2_start_tm(void)
{
  return (hfc_mta_unavail_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_mta_unavail_soak_win_2_start_tm(const AX_INT8 * val)
{
  hfc_mta_unavail_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_soak_win_1_duration(void)
{
  return (hfc_mta_unavail_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_mta_unavail_soak_win_1_duration(const AX_INT8 * val)
{
  hfc_mta_unavail_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_mta_unavail_soak_win_2_duration(void)
{
  return (hfc_mta_unavail_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_mta_unavail_soak_win_2_duration(const AX_INT8 * val)
{
  hfc_mta_unavail_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_alarm_threshold(AX_UINT32 mtaCount)
{
  AX_UINT32 ret;

  if (!mtaCount) {
    ret = hfc_power_alarm_threshold_1;
  } else {
    ret = hfc_power_alarm_threshold_1;
  }

  return (ret);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_alarm_threshold_1(void)
{
  return (hfc_power_alarm_threshold_1);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_power_alarm_threshold_1(const AX_INT8 * val)
{
  hfc_power_alarm_threshold_1 = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_alarm_detection_window(void)
{
  return (hfc_power_alarm_detection_window);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_power_alarm_detection_window(const AX_INT8 * val)
{
  hfc_power_alarm_detection_window = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_soak_win_duration(AX_UINT32 tmSec)
{
  AX_UINT32 ret;

  axDate d(tmSec);

  AX_UINT32 h = d.getHour();
  // int m = d.getMinute();

  if (h >= hfc_power_soak_win_1_duration &&
      h < hfc_power_soak_win_2_duration) {
    ret = hfc_power_soak_win_1_duration;
  } else {
    ret = hfc_power_soak_win_2_duration;
  }

  return (ret);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_soak_win_1_start_tm(void)
{
  return (hfc_power_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_power_soak_win_1_start_tm(const AX_INT8 * val)
{
  hfc_power_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_soak_win_2_start_tm(void)
{
  return (hfc_power_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_power_soak_win_2_start_tm(const AX_INT8 * val)
{
  hfc_power_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_soak_win_1_duration(void)
{
  return (hfc_power_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_power_soak_win_1_duration(const AX_INT8 * val)
{
  hfc_power_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_hfc_power_soak_win_2_duration(void)
{
  return (hfc_power_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_hfc_power_soak_win_2_duration(const AX_INT8 * val)
{
  hfc_power_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_unavail_soak_win_1_start_tm(void)
{
  return (mta_unavail_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_unavail_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_unavail_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_unavail_soak_win_2_start_tm(void)
{
  return (mta_unavail_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_unavail_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_unavail_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_unavail_soak_win_duration(AX_UINT32 tmSec)
{
  AX_UINT32 ret;

  axDate d(tmSec);

  AX_UINT32 h = d.getHour();
  // int m = d.getMinute();

  if (h >= mta_unavail_soak_win_1_duration &&
      h < mta_unavail_soak_win_2_duration) {
    ret = mta_unavail_soak_win_1_duration;
  } else {
    ret = mta_unavail_soak_win_2_duration;
  }

  return (ret);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_unavail_soak_win_1_duration(void)
{
  return (mta_unavail_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_unavail_soak_win_1_duration(const AX_INT8 * val)
{
  mta_unavail_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_unavail_soak_win_2_duration(void)
{
  return (mta_unavail_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_unavail_soak_win_2_duration(const AX_INT8 * val)
{
  mta_unavail_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_onbatt_soak_win_1_start_tm(void)
{
  return (mta_onbatt_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_onbatt_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_onbatt_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_onbatt_soak_win_2_start_tm(void)
{
  return (mta_onbatt_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_onbatt_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_onbatt_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_onbatt_soak_win_1_duration(void)
{
  return (mta_onbatt_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_onbatt_soak_win_1_duration(const AX_INT8 * val)
{
  mta_onbatt_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_onbatt_soak_win_2_duration(void)
{
  return (mta_onbatt_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_onbatt_soak_win_2_duration(const AX_INT8 * val)
{
  mta_onbatt_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_battmiss_soak_win_1_start_tm(void)
{
  return (mta_battmiss_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_battmiss_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_battmiss_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_battmiss_soak_win_2_start_tm(void)
{
  return (mta_battmiss_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_battmiss_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_battmiss_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_battmiss_soak_win_1_duration(void)
{
  return (mta_battmiss_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_battmiss_soak_win_1_duration(const AX_INT8 * val)
{
  mta_battmiss_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_battmiss_soak_win_2_duration(void)
{
  return (mta_battmiss_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_battmiss_soak_win_2_duration(const AX_INT8 * val)
{
  mta_battmiss_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_replbatt_soak_win_1_start_tm(void)
{
  return (mta_replbatt_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_replbatt_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_replbatt_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_replbatt_soak_win_2_start_tm(void)
{
  return (mta_replbatt_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_replbatt_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_replbatt_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_replbatt_soak_win_1_duration(void)
{
  return (mta_replbatt_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_replbatt_soak_win_1_duration(const AX_INT8 * val)
{
  mta_replbatt_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_replbatt_soak_win_2_duration(void)
{
  return (mta_replbatt_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_replbatt_soak_win_2_duration(const AX_INT8 * val)
{
  mta_replbatt_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_cmsloc_soak_win_1_start_tm(void)
{
  return (mta_cmsloc_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_cmsloc_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_cmsloc_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_cmsloc_soak_win_2_start_tm(void)
{
  return (mta_cmsloc_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_cmsloc_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_cmsloc_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_cmsloc_soak_win_1_duration(void)
{
  return (mta_cmsloc_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_cmsloc_soak_win_1_duration(const AX_INT8 * val)
{
  mta_cmsloc_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_cmsloc_soak_win_2_duration(void)
{
  return (mta_cmsloc_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_cmsloc_soak_win_2_duration(const AX_INT8 * val)
{
  mta_cmsloc_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_hwfail_soak_win_1_start_tm(void)
{
  return (mta_hwfail_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_hwfail_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_hwfail_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_hwfail_soak_win_2_start_tm(void)
{
  return (mta_hwfail_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_hwfail_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_hwfail_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_hwfail_soak_win_1_duration(void)
{
  return (mta_hwfail_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_hwfail_soak_win_1_duration(const AX_INT8 * val)
{
  mta_hwfail_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_hwfail_soak_win_2_duration(void)
{
  return (mta_hwfail_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_hwfail_soak_win_2_duration(const AX_INT8 * val)
{
  mta_hwfail_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_lcfail_soak_win_1_start_tm(void)
{
  return (mta_lcfail_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_lcfail_soak_win_1_start_tm(const AX_INT8 * val)
{
  mta_lcfail_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_lcfail_soak_win_2_start_tm(void)
{
  return (mta_lcfail_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_lcfail_soak_win_2_start_tm(const AX_INT8 * val)
{
  mta_lcfail_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_lcfail_soak_win_1_duration(void)
{
  return (mta_lcfail_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_lcfail_soak_win_1_duration(const AX_INT8 * val)
{
  mta_lcfail_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_lcfail_soak_win_2_duration(void)
{
  return (mta_lcfail_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_lcfail_soak_win_2_duration(const AX_INT8 * val)
{
  mta_lcfail_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cmts_comms_fail_soak_win_1_start_tm(void)
{
  return (cmts_comms_fail_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cmts_comms_fail_soak_win_1_start_tm(const AX_INT8 * val)
{
  cmts_comms_fail_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cmts_comms_fail_soak_win_2_start_tm(void)
{
  return (cmts_comms_fail_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cmts_comms_fail_soak_win_2_start_tm(const AX_INT8 * val)
{
  cmts_comms_fail_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cmts_comms_fail_soak_win_1_duration(void)
{
  return (cmts_comms_fail_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cmts_comms_fail_soak_win_1_duration(const AX_INT8 * val)
{
  cmts_comms_fail_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cmts_comms_fail_soak_win_2_duration(void)
{
  return (cmts_comms_fail_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cmts_comms_fail_soak_win_2_duration(const AX_INT8 * val)
{
  cmts_comms_fail_soak_win_2_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cms_loc_soak_win_1_start_tm(void)
{
  return (cms_loc_soak_win_1_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cms_loc_soak_win_1_start_tm(const AX_INT8 * val)
{
  cms_loc_soak_win_1_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cms_loc_soak_win_2_start_tm(void)
{
  return (cms_loc_soak_win_2_start_tm);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cms_loc_soak_win_2_start_tm(const AX_INT8 * val)
{
  cms_loc_soak_win_2_start_tm = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cms_loc_soak_win_1_duration(void)
{
  return (cms_loc_soak_win_1_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cms_loc_soak_win_1_duration(const AX_INT8 * val)
{
  cms_loc_soak_win_1_duration = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cms_loc_soak_win_2_duration(void)
{
  return (cms_loc_soak_win_2_duration);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cms_loc_soak_win_2_duration(const AX_INT8 * val)
{
  cms_loc_soak_win_2_duration = atoi(val);
}


/* new */
//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cmts_poll_interval(void)
{
  return (m_cmts_poll_interval);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cmts_poll_interval(const AX_INT8 * val)
{
  m_cmts_poll_interval = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_poll_interval(void)
{
  return (m_mta_poll_interval);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_poll_interval(const AX_INT8 * val)
{
  m_mta_poll_interval = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_mta_ping_interval(void)
{
  return (m_mta_ping_interval);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_mta_ping_interval(const AX_INT8 * val)
{
  m_mta_ping_interval = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_cm_poll_interval(void)
{
  return (m_cm_poll_interval);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cm_poll_interval(const AX_INT8 * val)
{
  m_cm_poll_interval = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::getNumSnmpSessions(void)
{
  return (m_numSnmpSessions);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setNumSnmpSessions(const AX_INT8 * val)
{
  m_numSnmpSessions = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
bool
axCASystemConfig::getLoadOneCmts(void)
{
  return (m_LoadOneCmts);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::setLoadOneCmts(const AX_INT8 * val)
{
  m_LoadOneCmts = !strcmp(val, "true");
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_startup_cmts_poll_stagger(void)
{
  return (m_startup_cmts_poll_stagger);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_startup_cmts_poll_stagger(const AX_INT8 * val)
{
  m_startup_cmts_poll_stagger = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_startup_mta_poll_delay(void)
{
  return (m_startup_mta_poll_delay);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_startup_mta_poll_delay(const AX_INT8 * val)
{
  m_startup_mta_poll_delay = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_startup_mta_ping_delay(void)
{
  return (m_startup_mta_ping_delay);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_startup_mta_ping_delay(const AX_INT8 * val)
{
  m_startup_mta_ping_delay = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_startup_mta_poll_stagger(void)
{
  return (m_startup_mta_poll_stagger);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_startup_mta_poll_stagger(const AX_INT8 * val)
{
  m_startup_mta_poll_stagger = atoi(val);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_startup_mta_ping_stagger(void)
{
  return (m_startup_mta_ping_stagger);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_startup_mta_ping_stagger(const AX_INT8 * val)
{
  m_startup_mta_ping_stagger = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_unchanged_log_interval(const AX_INT8 * val)
{
   m_unchangedLogInterval = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_unchanged_log_interval(void)
{
  return (m_unchangedLogInterval);
}

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_task_postpone_interval(const AX_INT8 * val)
{
   m_taskPostponeInterval = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_task_postpone_interval(void)
{
  return (m_taskPostponeInterval);
}

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_soap_services_port(const AX_INT8 * val)
{
   m_soap_services_port = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_soap_services_port(void)
{
  return (m_soap_services_port);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_soap_services_backlog(const AX_INT8 * val)
{
   m_soap_services_backlog = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_soap_services_backlog(void)
{
  return (m_soap_services_backlog);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_soap_service_req_processors(const AX_INT8 * val)
{
   m_soap_service_req_processors = atoi(val);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCASystemConfig::get_soap_service_req_processors(void)
{
  return (m_soap_service_req_processors);
}

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cm_snr_threshold(const AX_INT8 * val)
{
   m_cm_snr_threshold = (AX_INT16) (atoi(val) * 10);
}


//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_cm_snr_threshold(void)
{
  return (m_cm_snr_threshold);
}

// checked

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cm_dwn_pwr_high_threshold(const AX_INT8 * val)
{
   m_cm_dwn_pwr_high_threshold = (AX_INT16) (atoi(val) * 10);
}


//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_cm_dwn_pwr_high_threshold(void)
{
  return (m_cm_dwn_pwr_high_threshold);
}

// checked

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cm_dwn_pwr_low_threshold(const AX_INT8 * val)
{
  m_cm_dwn_pwr_low_threshold = (AX_INT16) (atoi(val) * 10);
}

//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_cm_dwn_pwr_low_threshold(void)
{
  return (m_cm_dwn_pwr_low_threshold);
}

// checked

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cm_up_pwr_high_threshold(const AX_INT8 * val)
{
   m_cm_up_pwr_high_threshold = (AX_INT16) (atoi(val) * 10);
}


//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_cm_up_pwr_high_threshold(void)
{
  return (m_cm_up_pwr_high_threshold);
}

// checked

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_cm_up_pwr_low_threshold(const AX_INT8 * val)
{
   m_cm_up_pwr_low_threshold = (AX_INT16) (atoi(val) * 10);
}


//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_cm_up_pwr_low_threshold(void)
{
  return (m_cm_up_pwr_low_threshold);
}


//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_max_db_read_conns(const AX_INT8 * val)
{
   m_max_db_read_conns = (AX_INT16) (atoi(val) * 10);
}


//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_max_db_read_conns(void)
{
  return (m_max_db_read_conns);
}

//********************************************************************
// method:
//********************************************************************
void
axCASystemConfig::set_max_db_write_conns(const AX_INT8 * val)
{
   m_max_db_write_conns = (AX_INT16) (atoi(val) * 10);
}


//********************************************************************
// method:
//********************************************************************
AX_INT16
axCASystemConfig::get_max_db_write_conns(void)
{
  return (m_max_db_write_conns);
}


