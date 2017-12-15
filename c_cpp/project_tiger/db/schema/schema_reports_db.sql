
---
--- The Reports Database
---

drop database careports;
create database careports;
use careports;

-- **********************************************************************
--
-- **********************************************************************
create table mta_oos_unavail (
  id bigint unsigned not null auto_increment,
  mta_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  unavail_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index mri(mta_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table mta_oos_cmsloc (
  id bigint unsigned not null auto_increment,
  mta_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  loc_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index mri(mta_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table cm_oos_offline (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  offline_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table cm_tca_snr (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  snr_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table cm_tca_dwnpwr (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  dwnpwr_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table cm_tca_uppwr (
  id bigint unsigned not null auto_increment,
  cm_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  uppwr_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index cri(cm_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table hfc_oos_outage (
  id bigint unsigned not null auto_increment,
  hfc_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  outage_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index hri(hfc_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
create table hfc_tca (
  id bigint unsigned not null auto_increment,
  hfc_res_id int unsigned not null,

  m_day tinyint unsigned not null default 0,
  m_month tinyint unsigned not null default 0,
  m_year smallint unsigned not null default 0,

  tca_duration int unsigned not null default 0,
  num_xsitions smallint unsigned not null default 0,

  primary key (id),
  index hri(hfc_res_id),
  index day(m_day),
  index month(m_month),
  index year(m_year)
) type=innodb;

-- **********************************************************************
--
-- **********************************************************************
-- GRANT ALL PRIVILEGES ON careports.* TO root@'192.168.1.0/255.255.255.0';

