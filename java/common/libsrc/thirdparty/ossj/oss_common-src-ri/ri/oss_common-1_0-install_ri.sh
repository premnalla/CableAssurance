#!/bin/sh

WL_HOME=/BEA/wlserver6.1

echo "=> copy the RI in $WL_HOME/config/mydomain/applications"
cp oss_common-1_0-ri_depl.ear $WL_HOME/config/mydomain/applications
