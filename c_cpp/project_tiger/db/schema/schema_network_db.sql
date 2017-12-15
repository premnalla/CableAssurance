
---
--- The Network Database
---

drop database canet;
create database canet;
use canet;

-- **********************************************************************
--
-- **********************************************************************
create table xresource (
  res_id int unsigned not null auto_increment,
  res_type tinyint unsigned not null default 0,

  primary key (res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table xresource_types (
  type tinyint unsigned not null,
  description varchar(255) not null,

  primary key (type)
) type=MyISAM;


-- **********************************************************************
--
-- **********************************************************************
create table snmp_version_types (
  type tinyint unsigned not null,
  description varchar(255) not null,

  primary key (type)
) type=MyISAM;


-- **********************************************************************
--
-- **********************************************************************
create table local_system_types (
  type tinyint unsigned not null,
  description varchar(255) not null,

  primary key (type)
) type=MyISAM;


-- **********************************************************************
-- Scalar (should only be one entry in the table)
-- **********************************************************************
create table local_system (
  system_type tinyint unsigned not null,
  system_name varchar(255) not null,
  parent_ipaddr varchar(40) not null default '',
  parent_ip_type smallint unsigned not null default 1,

  primary key (system_type)
) type=MyISAM;


-- **********************************************************************
--
-- **********************************************************************
create table enterprise (
  enterprise_id smallint unsigned not null auto_increment,
  last_updated timestamp,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (enterprise_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table region (
  region_id smallint not null auto_increment,
  last_updated timestamp,
  name varchar(128) not null default '', 
  ip_address varchar(40) not null default '',
  ip_address_type smallint unsigned not null default 1,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (region_id),
  unique index nm(name)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table market (
  market_id smallint unsigned not null auto_increment,
  last_updated timestamp,
  name varchar(128) not null default '', 
  ip_address varchar(40) not null default '',
  ip_address_type smallint unsigned not null default 1,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (market_id),
  unique index nm(name)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table blade (
  blade_id smallint unsigned not null auto_increment,
  last_updated timestamp,
  name varchar(128) not null default '',
  ip_address varchar(40) not null default '',
  ip_address_type smallint unsigned not null default 1,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (blade_id),
  unique index nm(name)
) type=innodb;


-- **********************************************************************
-- Enterprise, Region and Marker serves will have this table.
-- cm_res_id: resource id of CM in either the Blade or Market server
-- child_server_id: id of the child Retion, Market or Blade server
--   * foreign key to the id field in the respective region, market or blade
--     tables from above
-- mac_address: mac address of the CM
-- **********************************************************************
create table topo_lookup_cm (
  id int unsigned not null auto_increment,

  cm_res_id int unsigned not null,
  mac_address varchar(18) not null default '',
  topo_container_id smallint unsigned not null,

  primary key (id),
  unique index macaddr(mac_address),
  index tci(topo_container_id),
  index cri(cm_res_id)
) type=innodb;


-- **********************************************************************
-- Enterprise, Region and Marker serves will have this table.
-- cm_res_id: resource id of MTA in either the Blade or Market server
-- child_server_id: id of the child Retion, Market or Blade server
--   * foreign key to the id field in the respective region, market or blade
--     tables from above
-- mac_address: mac address of the MTA
-- **********************************************************************
create table topo_lookup_mta (
  id int unsigned not null auto_increment,

  mta_res_id int unsigned not null,
  mac_address varchar(18) not null default '',
  topo_container_id smallint unsigned not null,

  primary key (id),
  unique index macaddr(mac_address),
  index tci(topo_container_id),
  index mri(mta_res_id)
) type=innodb;


-- **********************************************************************
-- OBSOLETED
-- **********************************************************************
create table cms_specifics (
  cms_res_id int unsigned not null auto_increment,
  last_updated timestamp,
  cms_type varchar(64) not null,
  cms_subtype varchar(64) default '',
  cms_vendor_name varchar(128) not null,
  cms_description varchar(256) not null,

  primary key (cms_res_id)
) type=MyISAM;
--


-- **********************************************************************
--
-- **********************************************************************
create table cms (
  id smallint unsigned not null auto_increment,
  cms_res_id int unsigned not null,
  last_updated timestamp,
  name varchar(128) not null default '',
  cms_vendor_name varchar(128) not null default '',
  cms_type varchar(64) not null default '',
  cms_subtype varchar(64) not null default '',
  cms_description varchar(256) not null default '',
  cms_url varchar(256) not null default '',
  cms_loginpw varchar(64) not null default '',
  cms_loginuser varchar(32) not null default '',
  ip_address varchar(40) not null default '',
  ip_address_type smallint unsigned not null default 1,
  alert_level tinyint unsigned default 0,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (id),
  unique index cmsri(cms_res_id),
  unique index cmsnm(name)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table cmts (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,
  last_updated timestamp,
  name varchar(80) not null default '',
  fqdn varchar(80) not null default '',
  ip_address varchar(40) not null default '',
  cmts_snmp_ver tinyint unsigned not null default 2,
  cm_snmp_ver tinyint unsigned not null default 2,
  mta_snmp_ver tinyint unsigned not null default 2,
  online_state tinyint unsigned not null default 0,
  ip_address_type tinyint unsigned default 1,
  alert_level tinyint unsigned default 0,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (id),
  unique index cmtsri(cmts_res_id),
  unique index cmtsnm(name)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table cmts_v2c_attributes (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,

  read_community varchar(32) not null default '',
  write_community varchar(32) not null default '',

  primary key (id),
  unique index cmtsri(cmts_res_id)
) type=innodb;

create table cmts_v3_attributes (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,

-- add here

  primary key (id),
  unique index cmtsri(cmts_res_id)
) type=innodb;

create table cmts_cm_v2c_attributes (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,

  read_community varchar(32) not null default '',
  write_community varchar(32) not null default '',

  primary key (id),
  unique index cmtsri(cmts_res_id)
) type=innodb;

create table cmts_cm_v3_attributes (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,

-- add here

  primary key (id),
  unique index cmtsri(cmts_res_id)
) type=innodb;

create table cmts_mta_v2c_attributes (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,

  read_community varchar(32) not null default '',
  write_community varchar(32) not null default '',

  primary key (id),
  unique index cmtsri(cmts_res_id)
) type=innodb;

create table cmts_mta_v3_attributes (
  id int unsigned not null auto_increment,
  cmts_res_id int unsigned not null,

-- add here

  primary key (id),
  unique index cmtsri(cmts_res_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table channel_types (
  type tinyint unsigned not null,
  description varchar(255) not null,

  primary key (type)
) type=MyISAM;


-- **********************************************************************
-- Channel
-- **********************************************************************
create table channel (
  id int unsigned not null auto_increment,
  channel_res_id int unsigned not null,
  cmts_res_id int unsigned not null default 0,
  channel_index int unsigned not null default 0,
  last_updated timestamp,
  name varchar(32) not null default '',
  channel_type tinyint unsigned not null default 0,
  alert_level tinyint unsigned default 0,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (id),
  unique index chnlri(channel_res_id),
  unique index cmtsrichnm(cmts_res_id,name)
) type=innodb;


-- **********************************************************************
-- HFC
-- **********************************************************************
create table hfc_plant (
  id int unsigned not null auto_increment,
  hfc_res_id int unsigned not null,
  cmts_res_id int unsigned not null default 0,
  last_updated timestamp,
  name varchar(40) not null default '',
  alert_level tinyint unsigned default 0,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (id),
  unique index hfcri(hfc_res_id),
  unique index cmtsrihfcnm(cmts_res_id,name)
) type=innodb;


-- **********************************************************************
-- NOTE: still not sure if we need the table below
-- **********************************************************************
-- create table hfc_plant_secondary (
--   id int unsigned not null auto_increment,
--   hfc_res_id int unsigned not null,
--   nxt_cm_perc_alm_tm int unsigned not null default 0,
--   nxt_pwr_alm_tm int unsigned not null default 0,
--   last_updated timestamp,
-- 
--   primary key (id),
--   unique index hfcri(hfc_res_id)
-- ) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table end_user_dev_types (
  type tinyint unsigned not null,
  description varchar(255) not null,

  primary key (type)
) type=MyISAM;


-- **********************************************************************
-- CM
-- **********************************************************************
create table cable_modem (
  id int unsigned not null auto_increment,
  cm_res_id int unsigned not null,
  cmts_res_id int unsigned not null default 0,
  hfc_res_id int unsigned not null default 0,
  upstream_chnl_res_id int unsigned not null default 0,
  downstream_chnl_res_id int unsigned not null default 0,
  cm_index int unsigned not null default 0,
  last_updated timestamp,
  mac_address varchar(18) not null default '',
  fqdn varchar(80) not null default '',
  ip_address varchar(40) not null default '',
  docsis_state tinyint unsigned not null default 0,
  end_user_dev_type tinyint unsigned not null default 1,
  ip_address_type tinyint unsigned not null default 1,
  alert_level tinyint unsigned default 0,
  is_deleted tinyint(1) unsigned not null default 0,
  is_online tinyint(1) unsigned not null default 1,

  primary key (id),
  unique index cmri(cm_res_id),
  unique index mac(mac_address),
  index hfcri(hfc_res_id),
  index upchnlri(upstream_chnl_res_id),
  index dwnchnlri(downstream_chnl_res_id)
) type=innodb;


-- **********************************************************************
-- eMTA
-- **********************************************************************
create table emta (
  id int unsigned not null auto_increment,
  emta_res_id int unsigned not null,
  cms_res_id int unsigned not null,
  cm_res_id int unsigned not null default 0,
  last_updated timestamp,
  fqdn varchar(128) not null default '',
  ip_address varchar(40) not null default '',
  mac_address varchar(18) not null default '',
  pktcbl_prov_state tinyint unsigned not null default 0,
  ping_state tinyint unsigned not null default 0,
  ip_address_type tinyint unsigned not null default 1,
  alert_level tinyint unsigned default 0,
  is_prov_pass tinyint(1) unsigned not null default 0,
  is_ping_failure tinyint(1) unsigned not null default 0,
  available tinyint(1) unsigned not null default 0,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (id),
  unique index emtari(emta_res_id),
  unique index emtama(mac_address),
  index cmri(cm_res_id)
) type=innodb;


-- **********************************************************************
-- NOTE: still not sure if we need the table below
-- **********************************************************************
-- create table emta_secondary (
--   id int unsigned not null auto_increment,
--   emta_res_id int unsigned not null,
--   pwr_alm_chg_tm int unsigned not null default 0,
--   is_onbatt_alm tinyint unsigned not null default 0,
-- 
--   primary key (id),
--   unique index emtari(emta_res_id)
-- ) type=innodb;


-- **********************************************************************
-- MTA/eMTA Secondary Data
-- **********************************************************************
create table emta_secondary (
  id int unsigned not null auto_increment,
  emta_res_id int unsigned not null,
  phone1 varchar(10) not null default '',
  phone2 varchar(10) not null default '',

  primary key (id),
  unique index emtari(emta_res_id)
) type=innodb;

-- **********************************************************************
-- MTA
-- **********************************************************************
create table mta (
  id int unsigned not null auto_increment,
  mta_res_id int unsigned not null,
  cms_res_id int unsigned not null,
  hfc_res_id int unsigned not null default 0,
  upstream_chnl_res_id int unsigned not null default 0,
  downstream_chnl_res_id int unsigned not null default 0,
  last_updated timestamp,
  fqdn varchar(128) not null default '',
  host varchar(40) not null default '',
  mac_address varchar(18) not null default '',
  pktcbl_prov_state tinyint unsigned not null default 0,
  ping_state tinyint unsigned not null default 0,
  ip_address_type tinyint unsigned not null default 1,
  alert_level tinyint unsigned default 0,
  is_prov_pass tinyint(1) unsigned not null default 0,
  is_ping_failure tinyint(1) unsigned not null default 0,
  available tinyint(1) unsigned not null default 0,
  is_deleted tinyint(1) unsigned not null default 0,

  primary key (id),
  unique index mtari(mta_res_id),
  unique index mtamac(mac_address)
) type=innodb;


-- **********************************************************************
-- Application Configuration
-- **********************************************************************
create table app_config (
  var_name  varchar(128) not null,
  var_value varchar(256) default null,

  primary key (var_name)
) type=MyISAM;


-- **********************************************************************
--
-- **********************************************************************
create table customer_name (
  customer_id int unsigned not null auto_increment,
  account_number varchar(16) not null,
  address_id int unsigned not null,
  last_updated timestamp not null,

  primary key (customer_id),
  unique index an(account_number)
) type=innodb;

-- **********************************************************************
-- How do you specify uniqueness when street_2 is null in most cases?
-- unique index xxx(street_1,street_2,city_1,state,zip)
-- **********************************************************************
create table customer_address (
  address_id int unsigned not null auto_increment,
  last_updated timestamp not null,

  street_1 varchar(32) default null,
  street_2 varchar(32) default null,
  city_1 varchar(32) default null,
  state char(4) default null,
  zip char(8) default null,
  country varchar(32) default null,
  phone_1 varchar(16) default null,
  phone_2 varchar(16) default null,

  primary key (address_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table cust_addr_history (
  id int unsigned not null auto_increment,

  customer_id int unsigned not null,
  address_id int unsigned not null,

  last_updated timestamp not null,

  primary key (id),
  index ciai(customer_id,address_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table customer_resource (
  id int unsigned not null auto_increment,

  customer_id int unsigned not null,
  res_id int unsigned not null,

  primary key (id),
  unique index ci_ri(customer_id,res_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table customer_res_hist (
  id int unsigned not null auto_increment,

  customer_id int unsigned not null,
  res_id int unsigned not null,

  primary key (id),
  index ci_ri(customer_id,res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON canet.* TO root@'192.168.1.0/255.255.255.0';

-- **********************************************************************
--
-- **********************************************************************
insert into xresource_types (type,description) values
  (0,   'Unknown'),
  (1,   'CMTS'),
  (2,   'Channel'),
  (3,   'HFC'),
  (4,   'Cable Modem'),
  (5,   'MTA'),
  (6,  'CMS'),
  (255, 'Last');
--  (1,   'Enterprise'),
--  (2,   'Region'),
--  (3,   'Market'),
--  (4,   'Blade'),


-- **********************************************************************
--
-- **********************************************************************
insert into local_system_types (type,description) values
  (0,   'Unknown'),
  (1,   'Enterprise'),
  (2,   'Region'),
  (3,   'Market'),
  (4,   'Blade'),
  (255, 'Last');

-- **********************************************************************
--
-- **********************************************************************
-- insert into local_system (system_type,system_name) values
--   (3,   'Grafton, MA');

-- **********************************************************************
--
-- **********************************************************************
insert into snmp_version_types (type,description) values
  (0,   "Unknown"),
  (1,   "v1"),
  (2,   "v2c"),
  (3,   "v3"),
  (9,   "Last");


-- **********************************************************************
--
-- **********************************************************************
insert into channel_types (type,description) values
  (0,   'Unknown'),
  (1,   'Upstream'),
  (2,   'Downstream'),
  (9,   "Last");


-- **********************************************************************
--
-- **********************************************************************
insert into end_user_dev_types (type,description) values
  (0,   'Unknown'),
  (1,   'CM'),
  (2,   'eMTA'),
  (9,   'Last');


-- **********************************************************************
--
-- **********************************************************************
insert into app_config(var_name,var_value) values
  ('cmts_poll_interval', '600'),
  ('mta_poll_interval', '600'),
  ('mta_ping_interval', '600'),
  ('cm_poll_interval', '900'),

  ('hfc_cm_offline_alarm_threshold_1_cm', '50'),
  ('hfc_cm_offline_alarm_threshold_1', '10'),
  ('hfc_cm_offline_alarm_threshold_2_cm', '20'),
  ('hfc_cm_offline_alarm_threshold_2', '30'),
  ('hfc_cm_offline_alarm_detection_window', '600'),
  ('hfc_cm_offline_soak_win_1_start_tm', '0'),
  ('hfc_cm_offline_soak_win_2_start_tm', '6'),
  ('hfc_cm_offline_soak_win_1_duration', '1200'),
  ('hfc_cm_offline_soak_win_2_duration', '1200'),

  ('hfc_mta_unavail_alarm_threshold_1', '3'),
  ('hfc_mta_unavail_alarm_detection_window', '600'),
  ('hfc_mta_unavail_soak_win_1_start_tm', '0'),
  ('hfc_mta_unavail_soak_win_2_start_tm', '6'),
  ('hfc_mta_unavail_soak_win_1_duration', '1200'),
  ('hfc_mta_unavail_soak_win_2_duration', '1200'),

  ('hfc_power_alarm_threshold_1', '3'),
  ('hfc_power_alarm_detection_window', '600'),
  ('hfc_power_soak_win_1_start_tm', '0'),
  ('hfc_power_soak_win_2_start_tm', '6'),
  ('hfc_power_soak_win_1_duration', '1200'),
  ('hfc_power_soak_win_2_duration', '1200'),

  ('mta_unavail_soak_win_1_start_tm', '0'),
  ('mta_unavail_soak_win_2_start_tm', '6'),
  ('mta_unavail_soak_win_1_duration', '1200'),
  ('mta_unavail_soak_win_2_duration', '1200'),
  ('mta_onbatt_soak_win_1_start_tm', '0'),
  ('mta_onbatt_soak_win_2_start_tm', '6'),
  ('mta_onbatt_soak_win_1_duration', '1200'),
  ('mta_onbatt_soak_win_2_duration', '1200'),
  ('mta_battmiss_soak_win_1_start_tm', '0'),
  ('mta_battmiss_soak_win_2_start_tm', '6'),
  ('mta_battmiss_soak_win_1_duration', '1200'),
  ('mta_battmiss_soak_win_2_duration', '1200'),
  ('mta_replbatt_soak_win_1_start_tm', '0'),
  ('mta_replbatt_soak_win_2_start_tm', '6'),
  ('mta_replbatt_soak_win_1_duration', '1200'),
  ('mta_replbatt_soak_win_2_duration', '1200'),
  ('mta_cmsloc_soak_win_1_start_tm', '0'),
  ('mta_cmsloc_soak_win_2_start_tm', '6'),
  ('mta_cmsloc_soak_win_1_duration', '1200'),
  ('mta_cmsloc_soak_win_2_duration', '1200'),
  ('mta_hwfail_soak_win_1_start_tm', '0'),
  ('mta_hwfail_soak_win_2_start_tm', '6'),
  ('mta_hwfail_soak_win_1_duration', '1200'),
  ('mta_hwfail_soak_win_2_duration', '1200'),
  ('mta_lcfail_soak_win_1_start_tm', '0'),
  ('mta_lcfail_soak_win_2_start_tm', '6'),
  ('mta_lcfail_soak_win_1_duration', '1200'),
  ('mta_lcfail_soak_win_2_duration', '1200'),

  ('cmts_comms_fail_soak_win_1_start_tm', '0'),
  ('cmts_comms_fail_soak_win_2_start_tm', '6'),
  ('cmts_comms_fail_soak_win_1_duration', '1200'),
  ('cmts_comms_fail_soak_win_2_duration', '1200'),

  ('cms_loc_soak_win_1_start_tm', '0'),
  ('cms_loc_soak_win_2_start_tm', '6'),
  ('cms_loc_soak_win_1_duration', '1200'),
  ('cms_loc_soak_win_2_duration', '1200'),

  ('cm_perf_downstream_snr_lower', '27'),
  ('cm_perf_downstream_power_upper', '10'),
  ('cm_perf_downstream_power_lower', '-10'),
  ('cm_perf_upstream_power_upper', '48'),
  ('cm_perf_upstream_power_lower', '13'),

  ('Last_Name', 'Last_Value');

-- **********************************************************************
-- TEMPORARY
-- **********************************************************************
insert into xresource(res_type) values(10);
insert into cms(cms_res_id,name,ip_address) 
  values(last_insert_id(),"Grafton-MA","1.1.1.1");


