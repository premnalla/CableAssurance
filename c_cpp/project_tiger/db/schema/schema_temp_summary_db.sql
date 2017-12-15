drop database if exists catmpsumm;
create database catmpsumm;
create table catmpsumm.cmts_resources (
  cmts_res_id int unsigned not null,
  primary key (cmts_res_id)
) type=MyISAM;
insert into catmpsumm.cmts_resources (select cmts_res_id from canet.cmts);
create table catmpsumm.channel_resources (
  channel_res_id int unsigned not null,
  primary key (channel_res_id)
) type=MyISAM;
insert into catmpsumm.channel_resources (select channel_res_id from canet.channel);
create table catmpsumm.hfc_resources (
  hfc_res_id int unsigned not null,
  primary key (hfc_res_id)
) type=MyISAM;
insert into catmpsumm.hfc_resources (select hfc_res_id from canet.hfc_plant);
create table catmpsumm.cm_resources (
  cm_res_id int unsigned not null,
  primary key (cm_res_id)
) type=MyISAM;
insert into catmpsumm.cm_resources (select cm_res_id from canet.cable_modem);
create table catmpsumm.mta_resources (
  mta_res_id int unsigned not null,
  primary key (mta_res_id)
) type=MyISAM;
insert into catmpsumm.mta_resources (select emta_res_id from canet.emta);
create table catmpsumm.cm_current_status (
  cm_res_id int unsigned not null,
  online_tm int unsigned not null default 0,
  offline_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  docsis_state tinyint unsigned not null default 0,
  primary key (cm_res_id)
) type=MyISAM;
insert into catmpsumm.cm_current_status (select 
  cm_res_id,online_tm,offline_tm,last_log_tm,docsis_state from caperf.cm_current_status);
create table catmpsumm.mta_current_avail (
  mta_res_id int unsigned not null,
  avail_tm int unsigned not null default 0,
  unavail_tm int unsigned not null default 0,
  last_log_tm int unsigned not null default 0,
  available tinyint(1) unsigned not null default 0,
  primary key (mta_res_id)
) type=MyISAM;
insert into catmpsumm.mta_current_avail (select 
  mta_res_id,avail_tm,unavail_tm,last_log_tm,available from caperf.mta_current_avail);
create table catmpsumm.cm_perf_log (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  downstream_snr smallint default 0,
  downstream_power smallint default 0,
  upstream_power smallint default 0,
  t1_timeouts smallint unsigned default 0,
  t2_timeouts smallint unsigned default 0,
  t3_timeouts smallint unsigned default 0,
  t4_timeouts smallint unsigned default 0,
  primary key (id),
  index cmri(cm_res_id)
) type=MyISAM;
create table catmpsumm.cmts_counts_log (
  id bigint unsigned not null auto_increment,
  cmts_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,
  primary key (id),
  index cri(cmts_res_id)
) type=MyISAM;
create table catmpsumm.hfc_counts_log (
  id bigint unsigned not null auto_increment,
  hfc_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  cm_total smallint unsigned not null default 0,
  cm_online smallint unsigned not null default 0,
  mta_total smallint unsigned not null default 0,
  mta_available smallint unsigned not null default 0,
  primary key (id),
  index hri(hfc_res_id)
) type=MyISAM;
create table catmpsumm.channel_counts_log (
  id bigint unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  tm_sec int unsigned not null default 0,
  cm_total smallint unsigned not null,
  cm_online smallint unsigned not null,
  mta_total smallint unsigned not null,
  mta_available smallint unsigned not null,
  primary key (id),
  index cri(channel_res_id)
) type=MyISAM;
-- GRANT ALL PRIVILEGES ON catmpsumm.* TO root@'192.168.1.0/255.255.255.0';
