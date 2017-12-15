
--
-- Stats/Performance/Status-Log Tables
--

drop database casumm;
create database casumm;
use casumm;

-- **********************************************************************
-- This is a Singleton/Scalar table
-- Use: This table helps save state of Summarization processing
-- **********************************************************************
create table summarization_flags (
  id tinyint unsigned not null default 1,

  overall_summ_started tinyint(1) unsigned not null default 0,
  overall_summ_ended tinyint(1) unsigned not null default 1,

  db_cp_started tinyint(1) unsigned not null default 0,
  db_cp_ended tinyint(1) unsigned not null default 1,

  cmts_counts_started tinyint(1) unsigned not null default 0,
  cmts_counts_ended tinyint(1) unsigned not null default 1,

  channel_counts_started tinyint(1) unsigned not null default 0,
  channel_counts_ended tinyint(1) unsigned not null default 1,

  hfc_counts_started tinyint(1) unsigned not null default 0,
  hfc_counts_ended tinyint(1) unsigned not null default 1,

  cm_perf_summ_started tinyint(1) unsigned not null default 0,
  cm_perf_summ_ended tinyint(1) unsigned not null default 1,

  cm_status_summ_started tinyint(1) unsigned not null default 0,
  cm_status_summ_ended tinyint(1) unsigned not null default 1,

  mta_avail_summ_started tinyint(1) unsigned not null default 0,
  mta_avail_summ_ended tinyint(1) unsigned not null default 1,

  alarm_summ_started tinyint(1) unsigned not null default 0,
  alarm_summ_ended tinyint(1) unsigned not null default 1,

  cmperf_thresh_started tinyint(1) unsigned not null default 0,
  cmperf_thresh_ended tinyint(1) unsigned not null default 1,

  hfc_status_started tinyint(1) unsigned not null default 0,
  hfc_status_ended tinyint(1) unsigned not null default 1,

  hfc_tca_started tinyint(1) unsigned not null default 0,
  hfc_tca_ended tinyint(1) unsigned not null default 1,

  primary key (id)
) type=innodb;

insert into summarization_flags values();

-- **********************************************************************
-- This is Singleton/Scalar table as well
-- **********************************************************************
create table last_comp_summary (
  id tinyint unsigned not null default 1,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  primary key (id)
) type=innodb;

insert into last_comp_summary values();

-- **********************************************************************
--
-- **********************************************************************
create table cm_status_summary (
  id int unsigned not null auto_increment,

  cm_res_id int unsigned not null,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  sum_online_tm int unsigned not null default 0,
  sum_offline_tm int unsigned not null default 0,

  state_changes smallint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table mta_status_summary (
  id int unsigned not null auto_increment,

  mta_res_id int unsigned not null,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  sum_avail_tm int unsigned not null default 0,
  sum_unavail_tm int unsigned not null default 0,

  state_changes smallint unsigned not null default 0,

  primary key (id),
  index cri(mta_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table hfc_alarm_summary (
  id int unsigned not null auto_increment,

  hfc_res_id int unsigned not null,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  sum_alarmfree_tm int unsigned not null default 0,
  sum_alarm_tm int unsigned not null default 0,

  state_changes smallint unsigned not null default 0,

  primary key (id),
  index cri(hfc_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table hfc_tca_summary (
  id int unsigned not null auto_increment,

  hfc_res_id int unsigned not null,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  sum_tcafree_tm int unsigned not null default 0,
  sum_tca_tm int unsigned not null default 0,

  state_changes smallint unsigned not null default 0,

  primary key (id),
  index cri(hfc_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table cm_perf_summary (
  id int unsigned not null auto_increment,

  cm_res_id int unsigned not null,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  t1_timeouts int unsigned not null default 0,
  t2_timeouts int unsigned not null default 0,
  t3_timeouts int unsigned not null default 0,
  t4_timeouts int unsigned not null default 0,

  min_dwnstrm_snr smallint not null default 0,
  max_dwnstrm_snr smallint not null default 0,
  avg_dwnstrm_snr smallint not null default 0,

  min_dwnstrm_pwr smallint not null default 0,
  max_dwnstrm_pwr smallint not null default 0,
  avg_dwnstrm_pwr smallint not null default 0,

  min_upstream_pwr smallint not null default 0,
  max_upstream_pwr smallint not null default 0,
  avg_upstream_pwr smallint not null default 0,

  primary key (id),
  index cri(cm_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table cm_perf_thresh_summ (
  id int unsigned not null auto_increment,

  cm_res_id int unsigned not null,

  month tinyint unsigned default 0,
  day tinyint unsigned default 1,
  year smallint unsigned default 1970,

  sum_tcafree_tm int unsigned not null default 0,
  sum_tca_tm int unsigned not null default 0,

  state_changes smallint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table cmts_counts_summary (
  id int unsigned not null auto_increment,

  cmts_res_id int unsigned not null,

  month tinyint unsigned not null default 0,
  day tinyint unsigned not null default 1,
  year smallint unsigned not null default 1970,

  min_cm_total smallint unsigned not null default 0,
  max_cm_total smallint unsigned not null default 0,
  avg_cm_total smallint unsigned not null default 0,

  min_cm_online smallint unsigned not null default 0,
  max_cm_online smallint unsigned not null default 0,
  avg_cm_online smallint unsigned not null default 0,

  min_mta_total smallint unsigned not null default 0,
  max_mta_total smallint unsigned not null default 0,
  avg_mta_total smallint unsigned not null default 0,

  min_mta_avail smallint unsigned not null default 0,
  max_mta_avail smallint unsigned not null default 0,
  avg_mta_avail smallint unsigned not null default 0,

  primary key (id),
  index cri(cmts_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table channel_counts_summary (
  id int unsigned not null auto_increment,

  channel_res_id int unsigned not null,

  month tinyint unsigned not null default 0,
  day tinyint unsigned not null default 1,
  year smallint unsigned not null default 1970,

  min_cm_total smallint unsigned not null default 0,
  max_cm_total smallint unsigned not null default 0,
  avg_cm_total smallint unsigned not null default 0,

  min_cm_online smallint unsigned not null default 0,
  max_cm_online smallint unsigned not null default 0,
  avg_cm_online smallint unsigned not null default 0,

  min_mta_total smallint unsigned not null default 0,
  max_mta_total smallint unsigned not null default 0,
  avg_mta_total smallint unsigned not null default 0,

  min_mta_avail smallint unsigned not null default 0,
  max_mta_avail smallint unsigned not null default 0,
  avg_mta_avail smallint unsigned not null default 0,

  primary key (id),
  index cri(channel_res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table hfc_counts_summary (
  id int unsigned not null auto_increment,

  hfc_res_id int unsigned not null,

  month tinyint unsigned not null default 0,
  day tinyint unsigned not null default 1,
  year smallint unsigned not null default 1970,

  min_cm_total smallint unsigned not null default 0,
  max_cm_total smallint unsigned not null default 0,
  avg_cm_total smallint unsigned not null default 0,

  min_cm_online smallint unsigned not null default 0,
  max_cm_online smallint unsigned not null default 0,
  avg_cm_online smallint unsigned not null default 0,

  min_mta_total smallint unsigned not null default 0,
  max_mta_total smallint unsigned not null default 0,
  avg_mta_total smallint unsigned not null default 0,

  min_mta_avail smallint unsigned not null default 0,
  max_mta_avail smallint unsigned not null default 0,
  avg_mta_avail smallint unsigned not null default 0,

  primary key (id),
  index hri(hfc_res_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON casumm.* TO root@'192.168.1.0/255.255.255.0';


