#!/bin/bash

/opt/OpenNMS/bin/send-event.pl http://uei.opennms.org/nodes/nodeLostService -n 14 -i 192.168.0.189 -s FTP
sleep 60
/opt/OpenNMS/bin/send-event.pl http://uei.opennms.org/nodes/nodeRegainedService -n 14 -i 192.168.0.189 -s FTP
