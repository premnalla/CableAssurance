
---
--- User & Roles database
---

drop database caappuser;
create database caappuser;
use caappuser;

-- **********************************************************************
--
-- **********************************************************************
create table app_user (
  app_user_id int unsigned not null auto_increment,

  first_name varchar(32) not null default '',
  last_name varchar(32) not null default '',
  middle_initial varchar(4) not null default '',
  user_id varchar(32) not null default '',
  user_pw varchar(64) not null default '',
  user_location varchar(64) not null default '',
  role_id smallint unsigned not null default 5,
  is_active tinyint(1) unsigned not null default 1,

  primary key (app_user_id),
--  unique key fml(first_name,middle_initial,last_name),
  unique key ui(user_id)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table user_role (
  role_id smallint unsigned not null auto_increment,
  role_name varchar(32) not null,

  primary key (role_id),
  unique key rn(role_name)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table sys_object (
  sys_object_id smallint unsigned not null auto_increment,
  sys_object_name varchar(32) not null,

  primary key (sys_object_id),
  unique key sonm(sys_object_name)
) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table sys_access (
  sys_access_id smallint unsigned not null auto_increment,
  sys_access_name varchar(32) not null,

  primary key (sys_access_id),
  unique key saccn(sys_access_name)
) type=MyISAM;

-- **********************************************************************
-- OBSOLETED: NOT currently used
-- **********************************************************************
-- create table user_oa_map (
--   app_user_id int unsigned not null,
--   sys_object_id smallint unsigned not null,
--   sys_access_id smallint unsigned not null,
-- 
--   primary key (app_user_id,sys_object_id,sys_access_id)
-- ) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
create table role_oa_map (
  role_id smallint unsigned not null,
  sys_object_id smallint unsigned not null,
  sys_access_id smallint unsigned not null,

  primary key (role_id,sys_object_id,sys_access_id)
) type=MyISAM;

-- **********************************************************************
-- OBSOLETED: role_id moved to app_user table
-- **********************************************************************
-- create table user_role_map (
--   app_user_id int unsigned not null,
--   role_id smallint unsigned not null,
-- 
--   primary key (app_user_id)
-- ) type=MyISAM;

-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON caappuser.* TO root@'192.168.1.0/255.255.255.0';

-- **********************************************************************
--
-- **********************************************************************
insert into user_role(role_name) values 
  ('Administrator'),
  ('Operator'),
  ('CSR'),
  ('Custom'),
  ('Guest');

-- **********************************************************************
--
-- **********************************************************************
insert into sys_object(sys_object_name) values 
  ('Alarm'),
  ('CSR Portal'),
  ('System Administration'),
  ('User Administration');

-- **********************************************************************
--
-- **********************************************************************
insert into sys_access(sys_access_name) values
  ('Read'),
  ('Write'),
  ('Execute');

-- **********************************************************************
--
-- **********************************************************************
insert into role_oa_map(role_id,sys_object_id,sys_access_id) values
  (1,1,1),
  (1,1,2),
  (1,2,1),
  (1,2,2),
  (1,2,3),
  (1,3,1),
  (1,3,2),
  (1,4,1),
  (1,4,2),

  (2,1,1),
  (2,1,2),
  (2,2,1),
  (2,2,2),
  (2,2,3),
  (2,3,1),
  (2,3,2),
  (2,4,1),
  (2,4,2),

  (3,1,1),
  (3,1,2),
  (3,2,1),
  (3,2,2),
  (3,2,3),

  (4,1,1),
  (4,2,1),

  (5,1,1),
  (5,2,1);

-- **********************************************************************
--
-- **********************************************************************
-- Temporary - for user prem & shiromi
-- insert into user_role_map(app_user_id,role_id) values (4,1);

-- **********************************************************************
--
-- **********************************************************************
insert into app_user(first_name,last_name,user_id,user_pw,role_id,user_location) values
  ('Admin','Admin','admin',PASSWORD('manager'),1,'Boston'),
  ('Operator','Operator','operator',PASSWORD('manager'),2,'Boston'),
  ('Guest','Guest','guest',PASSWORD('guest'),5,'Boston');

-- Temporary
insert into
  app_user(first_name,middle_initial,last_name,user_id,user_pw,role_id,user_location)
  values
    ('Prem','N','Nallasivampillai','prem',PASSWORD('bar'),1,'Boston'),
    ('Shiromi','M','Premraj','shiromi',PASSWORD('bar'),5,'Boston');

