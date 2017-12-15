
--
-- Stats/Performance/Status-Log Tables
--

drop database caperf;
create database caperf;
use caperf;

-- **********************************************************************
-- CM Status (Used in Summarization)
-- **********************************************************************
create table cm_current_status (
  id int unsigned not null auto_increment,

  cm_res_id int unsigned not null,

  sum_online_tm int unsigned not null default 0,
  sum_offline_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  last_online_chg_tm int unsigned not null default 0,
  state_changes smallint unsigned not null default 0,
  online_state tinyint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id)
) type=innodb;

-- **********************************************************************
-- CM Status Log
-- **********************************************************************
create table cm_status_log (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  docsis_state tinyint unsigned not null,

  primary key (id),
  index cri(cm_res_id)
) type=innodb;

-- **********************************************************************
-- CM Performance & TCA
-- **********************************************************************
create table cm_current_perf (
  id bigint unsigned not null auto_increment,

  cm_res_id int unsigned not null,

  sum_tcafree_tm int unsigned not null default 0,
  sum_tca_tm int unsigned not null default 0,

  last_log_tm int unsigned not null default 0,
  tca_change_tm int unsigned not null default 0,

  t1_timeouts int unsigned not null default 0,
  t2_timeouts int unsigned not null default 0,
  t3_timeouts int unsigned not null default 0,
  t4_timeouts int unsigned not null default 0,

  downstream_snr smallint not null default 0,
  downstream_power smallint not null default 0,
  upstream_power smallint not null default 0,

  state_changes smallint unsigned not null default 0,

  primary key (id),
  unique index cri(cm_res_id)
) type=innodb;
-- 
create table cm_perf_log (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,

  t1_timeouts int unsigned not null default 0,
  t2_timeouts int unsigned not null default 0,
  t3_timeouts int unsigned not null default 0,
  t4_timeouts int unsigned not null default 0,
  downstream_snr smallint not null default 0,
  downstream_power smallint not null default 0,
  upstream_power smallint not null default 0,

  primary key (id),
  index cmri(cm_res_id)
) type=innodb;

-- **********************************************************************
-- MTA: Used in Summarization
-- **********************************************************************
create table mta_current_avail (
  id int unsigned not null auto_increment,

  mta_res_id int unsigned not null,

  sum_avail_tm int unsigned not null default 0,
  sum_unavail_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  last_avail_chg_tm int unsigned not null default 0,
  state_changes smallint unsigned not null default 0,
  available tinyint unsigned not null default 0,

  primary key (id),
  index mri(mta_res_id)
) type=innodb;

-- **********************************************************************
-- MTA
-- **********************************************************************
create table mta_availability_log (
  id bigint unsigned not null auto_increment,
  mta_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  availability tinyint(1) unsigned not null,

  primary key (id),
  index eri(mta_res_id)
) type=innodb;

-- **********************************************************************
-- HFC: Used in Summarization
-- **********************************************************************
--
-- Based on Alarm for HFC
--
create table hfc_current_alarm (
  id int unsigned not null auto_increment,

  hfc_res_id int unsigned not null,

  sum_alarmfree_tm int unsigned not null default 0,
  sum_alarm_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  alarm_chg_tm int unsigned not null default 0,
  state_changes smallint unsigned not null default 0,
  hfc_alarm tinyint unsigned not null default 0,

  primary key (id),
  index cri(hfc_res_id)
) type=innodb;
--
create table hfc_alarm_log (
  id int unsigned not null auto_increment,
  hfc_res_id int unsigned not null,
  time_sec int unsigned not null default 0,
  hfc_alarm tinyint unsigned not null default 0,

  primary key (id),
  index cri(hfc_res_id)
) type=innodb;

-- **********************************************************************
-- Based on CM TCA's for HFC
-- **********************************************************************
create table hfc_current_tca (
  id int unsigned not null auto_increment,

  hfc_res_id int unsigned not null,

  sum_tcafree_tm int unsigned not null default 0,
  sum_tca_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  tca_change_tm int unsigned not null default 0,
  state_changes smallint unsigned not null default 0,
  hfc_tca tinyint unsigned not null default 0,

  primary key (id),
  index cri(hfc_res_id)
) type=innodb;
--
create table hfc_tca_log (
  id int unsigned not null auto_increment,
  hfc_res_id int unsigned not null,
  time_sec int unsigned not null default 0,
  hfc_tca tinyint unsigned not null default 0,

  primary key (id),
  index cri(hfc_res_id)
) type=innodb;

-- **********************************************************************
-- CMTS Counts
-- **********************************************************************
create table cmts_current_counts (
  id bigint unsigned not null auto_increment,

  cmts_res_id int unsigned not null,

  last_log_tm int unsigned not null default 0,

  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,

  primary key (id),
  unique index cri(cmts_res_id)
) type=innodb;
--
create table cmts_counts_log (
  id bigint unsigned not null auto_increment,
  cmts_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,

  primary key (id),
  index cri(cmts_res_id)
) type=innodb;

-- **********************************************************************
-- HFC Counts
-- **********************************************************************
create table hfc_current_counts (
  id bigint unsigned not null auto_increment,

  hfc_res_id int unsigned not null,

  last_log_tm int unsigned not null default 0,

  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,

  primary key (id),
  unique index hri(hfc_res_id)
) type=innodb;
--
create table hfc_counts_log (
  id bigint unsigned not null auto_increment,
  hfc_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,

  primary key (id),
  index hri(hfc_res_id)
) type=innodb;

-- **********************************************************************
-- CHANNEL counts
-- **********************************************************************
create table channel_current_counts (
  id bigint unsigned not null auto_increment,

  channel_res_id int unsigned not null,

  last_log_tm int unsigned not null default 0,

  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,

  primary key (id),
  unique index cri(channel_res_id)
) type=innodb;
--
create table channel_counts_log (
  id bigint unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  cm_total smallint unsigned not null,
  cm_online smallint unsigned not null,
  mta_total smallint unsigned not null,
  mta_available smallint unsigned not null,

  primary key (id),
  index cri(channel_res_id)
) type=innodb;

-- **********************************************************************
-- MTA
-- **********************************************************************
create table mta_ping_status_log (
  id bigint unsigned not null auto_increment,
  mta_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  ping_state smallint unsigned not null,

  primary key (id),
  index eri(mta_res_id)
) type=innodb;

-- **********************************************************************
-- MTA
-- **********************************************************************
create table mta_prov_status_log (
  id bigint unsigned not null auto_increment,
  mta_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  prov_state smallint unsigned not null,

  primary key (id),
  index eri(mta_res_id)
) type=innodb;

-- **********************************************************************
-- Channel: Used in Summarization
-- **********************************************************************
--
-- Based on Alarm for Channel
--
create table channel_current_alarm (
  id int unsigned not null auto_increment,

  channel_res_id int unsigned not null,

  sum_alarmfree_tm int unsigned not null default 0,
  sum_alarm_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  alarm_change_tm int unsigned not null default 0,
  state_changes smallint unsigned not null default 0,
  alarm_state tinyint unsigned not null default 0,

  primary key (id),
  index cri(channel_res_id)
) type=innodb;
--
--
create table channel_alarm_log (
  id int unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  time_sec int unsigned not null default 0,
  channel_alarm tinyint unsigned not null default 0,

  primary key (id),
  index cri(channel_res_id)
) type=innodb;
--
-- Based on CM TCA's for Channel
--
create table channel_current_tca (
  id int unsigned not null auto_increment,

  channel_res_id int unsigned not null,

  sum_tcafree_tm int unsigned not null default 0,
  sum_tca_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  tca_change_tm int unsigned not null default 0,
  state_changes smallint unsigned not null default 0,
  tca_state tinyint unsigned not null default 0,

  primary key (id),
  index cri(channel_res_id)
) type=innodb;
--
create table channel_tca_log (
  id int unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  time_sec int unsigned not null default 0,
  channel_tca tinyint unsigned not null default 0,

  primary key (id),
  index cri(channel_res_id)
) type=innodb;

-- **********************************************************************
-- CMTS CM Performance
-- **********************************************************************
create table cmts_cm_current_perf (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,
  last_log_tm int unsigned not null default 0,

  upstream_snr_cmts smallint default 0,
  upstream_power_cmts smallint default 0,

  primary key (id),
  unique index cri(cm_res_id)
) type=innodb;

create table cmts_cm_perf_log (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,

  upstream_snr_cmts smallint default 0,
  upstream_power_cmts smallint default 0,

  primary key (id),
  index cmri(cm_res_id)
) type=innodb;

-- **********************************************************************
-- Channel Performance
-- **********************************************************************
create table channel_current_perf (
  id int unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  last_log_tm int unsigned not null default 0,
  channel_power smallint default 0,

  primary key (id),
  unique index cri(channel_res_id)
) type=innodb;

create table channel_perf_log (
  id int unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  channel_power smallint default 0,

  primary key (id),
  index chnlri(channel_res_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON caperf.* TO root@'192.168.1.0/255.255.255.0';

