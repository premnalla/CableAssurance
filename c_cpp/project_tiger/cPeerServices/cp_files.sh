#!/bin/sh

#
# Common files
#
/bin/cp generated/all/allC.cpp src
/bin/cp generated/all/allH.h include
/bin/cp generated/all/allStub.h include

#
# cPeerServices (both client & server)
#
/bin/cp generated/client_and_server/cPeerServH.h include
/bin/cp generated/client_and_server/cPeerServStub.h include
#/bin/cp generated/client_and_server/cPeerServC.cpp src
/bin/cp generated/client_and_server/cPeerServServer.cpp src
/bin/cp generated/client_and_server/cPeerServClient.cpp src

#
# CTE Client files
#
/bin/cp generated/client/CteServStub.h include
/bin/cp generated/client/CteServH.h include
#/bin/cp generated/client/CteServC.cpp src
/bin/cp generated/client/CteServClient.cpp src

#
# CMS Client files
#
/bin/cp generated/client/CmsServStub.h include
/bin/cp generated/client/CmsServH.h include
#/bin/cp generated/client/CmsServC.cpp src
/bin/cp generated/client/CmsServClient.cpp src

#
# Administration Client files
#
/bin/cp generated/client/AdminServStub.h include
/bin/cp generated/client/AdminServH.h include
#/bin/cp generated/client/AdminServC.cpp src
/bin/cp generated/client/AdminServClient.cpp src

