
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCACfgLoader.hpp"
#include "axConfigLoader.hpp"
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

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCACfgLoader::axCACfgLoader()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCACfgLoader::~axCACfgLoader()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCACfgLoader::axCACfgLoader(AX_INT8 * fname) :
  m_fileName(fname)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCACfgLoader::loadConfig(void)
{
  bool ret = false;

  static const char * system_section = "system";
  static const char * runnable_executors = "runnable_executors";
  static const char * cm_perf_runnables_per_rc = 
                                       "cm_perf_runnables_per_rc";
  static const char * cm_per_perf_runnable = 
                                           "cm_per_perf_runnable";
  static const char * mta_poll_runnables_per_rc = 
                                      "mta_poll_runnables_per_rc";
  static const char * mta_per_poll_runnable = 
                                          "mta_per_poll_runnable";
  static const char * mta_ping_runnables_per_rc = 
                                      "mta_ping_runnables_per_rc";
  static const char * mta_per_ping_runnable = 
                                          "mta_per_ping_runnable";
  static const char * timer_workers = "timer_workers";
  static const char * snmp_sessions = "snmp_sessions";
  static const char * log_mask = "log_mask";
  static const char * load_one_cmts = "load_one_cmts";
  static const char * startup_cmts_poll_stagger = "startup_cmts_poll_stagger";
  static const char * startup_mta_poll_delay = "startup_mta_poll_delay";
  static const char * startup_mta_ping_delay = "startup_mta_ping_delay";
  static const char * startup_mta_poll_stagger = "startup_mta_poll_stagger";
  static const char * startup_mta_ping_stagger = "startup_mta_ping_stagger";
  static const char * unchange_log_interval = "unchanged_log_interval";
  static const char * task_postpone_interval = "task_postpone_interval";
  static const char * soap_services_port = "soap_services_port";
  static const char * soap_services_backlog = "soap_services_backlog";
  static const char * soap_service_req_processors = "soap_service_req_processors";
  static const char * max_db_read_conns = "max_db_read_conns";
  static const char * max_db_write_conns = "max_db_write_conns";

  axCASystemConfig * sc = axCASystemConfig::getInstance();

  std::string pv;

  axConfigLoader loader(const_cast<AX_INT8 *> (m_fileName.c_str()));
  if (!loader.loadConfig()) {
    goto EXIT_LABEL;
  }

  if (loader.getValue(system_section, runnable_executors, pv)) {
    sc->setNumRunnableExecutors(pv.c_str());
  }

  if (loader.getValue(system_section, cm_perf_runnables_per_rc, 
                                                              pv)) {
    sc->setCmPerfRunnablesPerRC(pv.c_str());
  }

  if (loader.getValue(system_section, cm_per_perf_runnable, pv)) {
    sc->setCmPerPerfRunnable(pv.c_str());
  }

  if (loader.getValue(system_section, mta_poll_runnables_per_rc,
                                                              pv)) {
    sc->setMtaPollRunnablesPerRc(pv.c_str());
  }

  if (loader.getValue(system_section, mta_per_poll_runnable, pv)) {
    sc->setMtaPerPollRunnable(pv.c_str());
  }

  if (loader.getValue(system_section, mta_ping_runnables_per_rc,
                                                              pv)) {
    sc->setMtaPingRunnablesPerRc(pv.c_str());
  }

  if (loader.getValue(system_section, mta_per_ping_runnable, pv)) {
    sc->setMtaPerPingRunnable(pv.c_str());
  }

  if (loader.getValue(system_section, timer_workers, pv)) {
    sc->setNumTimerWorkers(pv.c_str());
  }

  if (loader.getValue(system_section, snmp_sessions, pv)) {
    sc->setNumSnmpSessions(pv.c_str());
  }

  if (loader.getValue(system_section, log_mask, pv)) {
    // sc->setNumTimerWorkers(pv.c_str());
  }

  if (loader.getValue(system_section, load_one_cmts, pv)) {
    sc->setLoadOneCmts(pv.c_str());
  }

  if (loader.getValue(system_section, startup_cmts_poll_stagger, pv)) {
    sc->set_startup_cmts_poll_stagger(pv.c_str());
  }

  if (loader.getValue(system_section, startup_mta_poll_delay, pv)) {
    sc->set_startup_mta_poll_delay(pv.c_str());
  }

  if (loader.getValue(system_section, startup_mta_ping_delay, pv)) {
    sc->set_startup_mta_ping_delay(pv.c_str());
  }

  if (loader.getValue(system_section, startup_mta_poll_stagger, pv)) {
    sc->set_startup_mta_poll_stagger(pv.c_str());
  }

  if (loader.getValue(system_section, startup_mta_ping_stagger, pv)) {
    sc->set_startup_mta_ping_stagger(pv.c_str());
  }

  if (loader.getValue(system_section, unchange_log_interval, pv)) {
    sc->set_unchanged_log_interval(pv.c_str());
  }

  if (loader.getValue(system_section, task_postpone_interval, pv)) {
    sc->set_task_postpone_interval(pv.c_str());
  }

  if (loader.getValue(system_section, soap_services_port, pv)) {
    sc->set_soap_services_port(pv.c_str());
  }

  if (loader.getValue(system_section, soap_services_backlog, pv)) {
    sc->set_soap_services_backlog(pv.c_str());
  }

  if (loader.getValue(system_section, soap_service_req_processors, pv)) {
    sc->set_soap_service_req_processors(pv.c_str());
  }

  if (loader.getValue(system_section, max_db_read_conns, pv)) {
    sc->set_max_db_read_conns(pv.c_str());
  }
  if (loader.getValue(system_section, max_db_write_conns, pv)) {
    sc->set_max_db_write_conns(pv.c_str());
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


