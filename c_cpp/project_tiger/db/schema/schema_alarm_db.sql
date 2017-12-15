
---
---
---

drop database caalarm;
create database caalarm;
use caalarm;

-- **********************************************************************
--
-- **********************************************************************
create table alarm_types (
  type tinyint unsigned not null,
  sub_type tinyint unsigned not null,
  type_desc varchar(16) not null,
  subtype_desc varchar(64) not null,

  primary key (type,sub_type)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table alarm_states (
  state tinyint unsigned not null,
  description varchar(64) not null,

  primary key (state)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table root_alarm (
  root_alarm_id bigint unsigned not null auto_increment,
  res_id int unsigned not null,
  detection_time int unsigned not null default 0,
  det_time_usec int unsigned not null default 0,
  alarm_type tinyint unsigned not null default 0,
  alarm_sub_type tinyint unsigned not null default 0,

  primary key (root_alarm_id),
  index ri(res_id)
) type=innodb;

-- **********************************************************************
-- Why does the 'id' field have to be 'bigint'. Isn't 'int' sufficient?
-- **********************************************************************
create table current_alarm (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  soak_duration smallint unsigned not null default 0,
  alarm_state tinyint unsigned not null default 1,

  primary key (id),
  unique index rai(root_alarm_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table historical_alarm (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  cleared_user_id int unsigned not null default 0,
  soak_duration smallint unsigned not null default 1,
  alarm_state tinyint unsigned not null default 0,

  primary key (id),
  unique index rai(root_alarm_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table coincidental_alarm (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  rt_cncdnt_alm_id bigint unsigned not null,

  primary key (id),
  unique index rai(root_alarm_id,rt_cncdnt_alm_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table alarm_secondary (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  presoak_total smallint unsigned not null default 0,
  presoak_count smallint unsigned not null default 0,
  postsoak_total smallint unsigned not null default 0,
  postsoak_count smallint unsigned not null default 0,
  alarm_det_window smallint unsigned not null default 0,
  alarm_threshold smallint not null default 0,

  primary key (id),
  unique index rai(root_alarm_id)
) type=innodb;

-- **********************************************************************
-- This table Should be OBSOLETED. Instead use the combination of
-- coincidental_alarm and alarm_state_history tables to generate details
-- Details are generated for GUI only and on a per alarm basis. Thus
-- don't need to store them here.
-- 
-- Use the conbination of:
-- coincidental_alarm, alarm_state_history, alarm_coincidental_soaking,
-- alarm_coincidental_postsoak, etc.
-- **********************************************************************
create table alarm_details (
  id integer unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  alarm_details blob default null,

  primary key (id),
  unique index rai(root_alarm_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table soaking_alarms (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,

  primary key (id),
  unique index rai(root_alarm_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table alarm_state_history (
  ash_id int unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  alarm_ts timestamp not null,
  alarm_state tinyint unsigned not null default 0,

  primary key (ash_id),
  index rai(root_alarm_id)
) type=innodb;


-- **********************************************************************
--
-- Use this instead of alarm_details. The details will be in field meta_data
-- ash_id from this table points to id from alarm_state_history 
--
-- **********************************************************************
create table alarm_state_metadata (
  id int unsigned not null auto_increment,
  ash_id int unsigned not null,
  meta_data varchar(2048) default NULL,

  primary key (id),
  unique index ashi(ash_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table alarm_devices_soaking (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  res_id integer unsigned not null,
  alarm_ts timestamp not null,

  primary key (id),
  unique index airi(root_alarm_id,res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table alarm_devices_postsoak (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  res_id integer unsigned not null,
  alarm_ts timestamp not null,

  primary key (id),
  unique index airi(root_alarm_id,res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table alarm_coincidental_soaking (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  res_id integer unsigned not null,
  alarm_ts timestamp not null,

  primary key (id),
  unique index airi(root_alarm_id,res_id)
) type=innodb;


-- **********************************************************************
--
-- **********************************************************************
create table alarm_coincidental_postsoak (
  id bigint unsigned not null auto_increment,
  root_alarm_id bigint unsigned not null,
  res_id integer unsigned not null,
  alarm_ts timestamp not null,

  primary key (id),
  unique index airi(root_alarm_id,res_id)
) type=innodb;


--
-- Exception tables
--

-- **********************************************************************
--
-- **********************************************************************
create table exception_types (
  type smallint unsigned not null,
  description varchar(64) not null,

  primary key (type)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table exception_basic (
  exception_id bigint unsigned not null auto_increment,
  res_id int unsigned not null,
  exception_type smallint unsigned not null,
  detection_time int unsigned not null,

  primary key (exception_id),
  index rri(res_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table exception_secondary (
  id bigint unsigned not null auto_increment,
  exception_id bigint unsigned not null,
  description varchar(128) default null,

  primary key (id),
  unique index ei(exception_id)
) type=innodb;


--
-- Service outage tables
--

-- **********************************************************************
--
-- **********************************************************************
create table service_outage_types (
  type smallint unsigned not null,
  description varchar(64) not null,

  primary key (type)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table service_outage_basic (
  outage_id bigint unsigned not null auto_increment,
  res_id int unsigned not null,
  outage_type smallint unsigned not null,
  detection_time int unsigned not null,

  primary key (outage_id),
  index rri(res_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table service_outage_secondary (
  id bigint unsigned not null auto_increment,
  outage_id bigint unsigned not null,
  description varchar(128) default null,

  primary key (id),
  unique index oi(outage_id)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table service_outage_details (
  id bigint unsigned not null auto_increment,
  outage_id bigint unsigned not null,
  outage_details blob default null,

  primary key (id),
  unique index ai(outage_id)
) type=innodb;--

--
-- Alarm Root Cause Analysis tables
--

-- **********************************************************************
--
-- **********************************************************************
create table alarm_root_cause (
  id bigint unsigned not null auto_increment,
  alarm_id bigint unsigned not null,
  alarm_rootcause blob default null,

  primary key (id),
  unique index ai(alarm_id)
) type=innodb;


--
-- Service Root Cause Analysis tables
--

-- **********************************************************************
--
-- **********************************************************************
create table service_root_cause (
  id bigint unsigned not null auto_increment,
  outage_id bigint unsigned not null,
  outage_rootcause blob default null,

  primary key (id),
  unique index oi(outage_id)
) type=innodb;


-- **********************************************************************
-- Used to keep data in one place, instead of in a header file in code.
-- Can be used for following purposes:
-- 1. to keep where clause of a query
-- 2. anything else that you can think of
--
-- **********************************************************************
create table query_metadata (
  id smallint unsigned not null,
  description varchar(128) not null default ' ',
  meta_data varchar(128) not null default ' ',

  primary key (id)
) type=MyISAM;


-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON caalarm.* TO root@'192.168.1.0/255.255.255.0';

-- **********************************************************************
--
-- **********************************************************************
insert into alarm_types(type,sub_type,type_desc,subtype_desc) values
 (0,  0, "Unknown", "Unknown"),

 (1,  1, "HFC", "Power"),
 (1,  2, "HFC", "Percent"),
 (1,  3, "HFC", "MTA Count"),

 (2,  1, "MTA", "Unavailable"),
 (2,  2, "MTA", "On Battery"),
 (2,  3, "MTA", "Battery Missing"),
 (2,  4, "MTA", "Replace Battery"),
 (2,  5, "MTA", "CMS LOC"),
 (2,  6, "MTA", "Hardware Failure"),
 (2,  7, "MTA", "Line Card Failure"),

 (3,  1, "CMTS", "Communication Failure"),

 (4,  1, "CMS", "Communication Failure"),

 (99, 0, "Last", "Last");

-- **********************************************************************
--
-- **********************************************************************
insert into alarm_states(state,description) values
 (0, "Unknown"),
 (1, "Soaking"),
 (2, "Soak Complete"),
 (3, "Ticketing"),
 (4, "Ticketed"),
 (5, "Ticketing Failed"),
 (6, "Cleared"),
 (7, "Coincidental"),
 (8, "Restart Discard"),
 (999, "Last");

-- **********************************************************************
--
-- **********************************************************************
insert into service_outage_types(type,description) values
 (0, "Unknown"),
 (1, "CMTS"),
 (2, "CMS"),
 (3, "HFC"),
 (4, "Channel"),
 (5, "CM"),
 (6, "MTA"),
 (999, "Last");

-- **********************************************************************
--
-- **********************************************************************
insert into exception_types(type,description) values
 (0, "Unknown"),
 (1, "Signal-to-noise ratio is below safe operational range of 30 dB"),
 (2, "Upstream power is not within safe operational range (-15 to +15 dBmV)"),
 (3, "Upstream power is not within safe operational range (-15 to +15 dBmV)"),
 (999, "Last");


-- **********************************************************************
--
-- **********************************************************************
insert into query_metadata(id, description, meta_data) values
 (1, "States that indicate a current alarm", " not in(0,4,5,6,7,8) ");

