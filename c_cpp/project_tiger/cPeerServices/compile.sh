#!/bin/sh

##
GSOAP_SRC_HOME=$THIRDPTYBASE/gsoap/gsoap

########################################
# All services need to be compiled to get common files
########################################
wsdl2h -o include/allServices.h wsdl/CA_CPeerServices.wsdl wsdl/CA_CTEservices.wsdl wsdl/CA_CMSservices.wsdl wsdl/CA_Administration.wsdl
soapcpp2 -d generated/all -n -w -x -L -p all -I$GSOAP_SRC_HOME/soapcpp2/import include/allServices.h


########################################
# cPeerServices
########################################
wsdl2h -o include/cPeerServices.h wsdl/CA_CPeerServices.wsdl
soapcpp2 -d generated/client_and_server -n -w -x -L -p cPeerServ -I$GSOAP_SRC_HOME/soapcpp2/import include/cPeerServices.h

########################################
# CteServices
########################################
wsdl2h -o include/CteServices.h wsdl/CA_CTEservices.wsdl
soapcpp2 -d generated/server -S -n -w -x -L -p CteServ -I$GSOAP_SRC_HOME/soapcpp2/import include/CteServices.h
soapcpp2 -d generated/client -C -n -w -x -L -p CteServ -I$GSOAP_SRC_HOME/soapcpp2/import include/CteServices.h

########################################
# CmsServices
########################################
wsdl2h -o include/CmsServices.h wsdl/CA_CMSservices.wsdl
soapcpp2 -d generated/server -S -n -w -x -L -p CmsServ -I$GSOAP_SRC_HOME/soapcpp2/import include/CmsServices.h
soapcpp2 -d generated/client -C -n -w -x -L -p CmsServ -I$GSOAP_SRC_HOME/soapcpp2/import include/CmsServices.h

########################################
# AdminServices
########################################
wsdl2h -o include/AdminServices.h wsdl/CA_Administration.wsdl
soapcpp2 -d generated/server -S -n -w -x -L -p AdminServ -I$GSOAP_SRC_HOME/soapcpp2/import include/AdminServices.h
soapcpp2 -d generated/client -C -n -w -x -L -p AdminServ -I$GSOAP_SRC_HOME/soapcpp2/import include/AdminServices.h

echo "###############################################################"
echo "# NOTE: Remember to combine all nampespaces => axAllNsmap.cpp #"
echo "###############################################################"

