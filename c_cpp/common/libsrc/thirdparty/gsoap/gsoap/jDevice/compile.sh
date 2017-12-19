#!/bin/sh
#wsdl2h -N foo -o jDevice.h JDevice.wsdl
#wsdl2h -n jDevice -o include/jDevice.h wsdl/JDevice.wsdl
wsdl2h -N jDevice -o include/jDevice.h wsdl/JDevice.wsdl
soapcpp2 -d generated -n -p jDevice -I../soapcpp2/import include/jDevice.h
wsdl2h -N Device -o include/Device.h wsdl/Device.wsdl
soapcpp2 -d generated -n -p Device -I../soapcpp2/import include/Device.h
