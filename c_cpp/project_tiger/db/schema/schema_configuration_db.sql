
---
---
---

drop database cacfg;
create database cacfg;
use cacfg;

-- **********************************************************************
-- 
-- **********************************************************************
create table serverity_colors (
  severity_color tinyint unsigned not null,
  description varchar(64) not null,
  primary key (severity_color)
) type=MyISAM;
insert into serverity_colors(severity_color,description) values
  (0, "Unknown"),
  (1, "Green"),
  (2, "Yellow"),
  (3, "Orange"),
  (4, "Red");

-- **********************************************************************
--
-- **********************************************************************
create table mta_alarm_sev_def (
  severity_color tinyint unsigned not null,
  alarm_type tinyint unsigned not null,
  alarm_state tinyint unsigned not null,
  primary key (severity_color,alarm_type)
) type=MyISAM;
-- From caalarm.alarm_types and caalarm.alarm_states
insert into mta_alarm_sev_def(severity_color,alarm_type,alarm_state) values
-- RED, Type, Soak Complete
  (4,1,2),
-- Orange, Type, Soak Complete
  (3,2,2),
  (3,3,2),
  (3,4,2),
  (3,5,2),
-- Yellow, Type, Soaking
  (2,1,1),
  (2,2,1),
  (2,3,1),
  (2,4,1),
  (2,5,1);

-- **********************************************************************
--
-- **********************************************************************
create table hfc_alarm_sev_def (
  severity_color tinyint unsigned not null,
  alarm_type tinyint unsigned not null,
  alarm_state tinyint unsigned not null,
  primary key (severity_color,alarm_type)
) type=MyISAM;
insert into hfc_alarm_sev_def(severity_color,alarm_type,alarm_state) values
-- RED, Type, Soak Complete
  (4,1,2),
  (4,2,2),
  (4,3,2),
-- Yellow, Type, Soaking
  (2,1,2),
  (2,2,1),
  (2,3,1);
-- **********************************************************************
create table hfc_child_sev_def (
  severity_color tinyint unsigned not null,
  child_fault_color tinyint unsigned not null,
  child_fault_percent tinyint unsigned not null,
  primary key (severity_color,child_fault_color)
) type=MyISAM;
insert into hfc_child_sev_def(severity_color,child_fault_color,child_fault_percent) values
-- RED, RED, 50%
  (4,4,50),
-- RED, RED, 50%
  (2,2,50);

-- **********************************************************************
--
-- **********************************************************************
create table cmts_alarm_sev_def (
  severity_color tinyint unsigned not null,
  alarm_type tinyint unsigned not null,
  alarm_state tinyint unsigned not null,
  primary key (severity_color,alarm_type)
) type=MyISAM;
insert into cmts_alarm_sev_def(severity_color,alarm_type,alarm_state) values
-- RED, Type, Soak Complete
  (4,1,2),
-- Yellow, Type, Soaking
  (2,1,2);
-- **********************************************************************
create table cmts_child_sev_def (
  severity_color tinyint unsigned not null,
  child_fault_color tinyint unsigned not null,
  child_fault_percent tinyint unsigned not null,
  primary key (severity_color,child_fault_color)
) type=MyISAM;
insert into cmts_child_sev_def(severity_color,child_fault_color,child_fault_percent) values
-- RED, RED, 50%
  (4,4,50),
-- RED, RED, 50%
  (2,2,50);

-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON cacfg.* TO root@'192.168.1.0/255.255.255.0';

