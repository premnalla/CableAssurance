#!/bin/sh

mysql -f -u root -pmanager < schema_init.sql
mysql -f -u root -pmanager < schema_network_db.sql
mysql -f -u root -pmanager < schema_alarm_db.sql
mysql -f -u root -pmanager < schema_app_user.sql
mysql -f -u root -pmanager < schema_configuration_db.sql
mysql -f -u root -pmanager < schema_performance_db.sql
mysql -f -u root -pmanager < schema_reports_db.sql
mysql -f -u root -pmanager < schema_summary_db.sql
mysql -f -u root -pmanager < schema_grant.sql
