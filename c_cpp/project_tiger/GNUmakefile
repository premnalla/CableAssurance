# -*- Makefile -*-
#----------------------------------------------------------------------------
#       GNU Makefile
#
#----------------------------------------------------------------------------
MAKEFILE         = GNUmakefile
DEPENDENCY_FILE  = .depend.libaxcblassure

## LIB may be set to empty later on in this file
LIB_UNCHECKED    = libaxcblassure.a
LIB              = $(LIB_UNCHECKED)

## SHLIB may be set to empty later on in this file
SHLIB_UNCHECKED  = libaxcblassure.$(SOEXT)
SHLIB            = $(SHLIB_UNCHECKED)

FILES = \
  common/src/axCommonStringUtils.cpp \
  common/src/axCASystemConfig.cpp \
  common/src/axConfigLoader.cpp \
  common/src/axCAController.cpp \
  common/src/axAceWorker.cpp \
  common/src/axCATaskCoordHelper.cpp \
  common/src/axDate.cpp \
  common/src/axAbstractTopoIntObject.cpp \
  common/src/axTopoLookupIntGenMta.cpp \
  common/src/axTopoLookupTables.cpp \
  common/src/axAbstractTopoContainerObj.cpp \
  common/src/axTopoBladeContainer.cpp \
  common/src/axSocketFailInterface.cpp \
  db/src/axAbstractDbObject.cpp \
  db/src/axDbXResource.cpp \
  db/src/axDbCmts.cpp \
  db/src/axDbCm.cpp \
  db/src/axDbGenMta.cpp \
  db/src/axDbEmta.cpp \
  db/src/axDbMta.cpp \
  db/src/axDbConnection.cpp \
  db/src/axDbMySqlConnection.cpp \
  db/src/axDbConnectionFactory.cpp \
  db/src/axDbConnectionHelper.cpp \
  db/src/axDbReadConnHelper.cpp \
  db/src/axDbWriteConnHelper.cpp \
  db/src/axDbMySqlConnFactory.cpp \
  db/src/axDbGenericQuery.cpp \
  db/src/axDbMySqlQuery.cpp \
  db/src/axDbMySqlModify.cpp \
  db/src/axDbMySqlResult.cpp \
  db/src/axDbMySqlResultSet.cpp \
  db/src/axMySqlDb.cpp \
  db/src/axDbUtils.cpp \
  db/src/axDbChannel.cpp \
  db/src/axDbHfc.cpp \
  db/src/axDbExternalizer.cpp \
  db/src/axDbQueryHelper.cpp \
  db/src/axDbResult.cpp \
  db/src/axDbResultSet.cpp \
  db/src/axDbCAAlarm.cpp \
  db/src/axDbCAAlarmSecondary.cpp \
  db/src/axDbCASoakingAlarm.cpp \
  db/src/axDbCAAlarmHistory.cpp \
  db/src/axDbCAAlarmDeviceSoaking.cpp \
  db/src/axDbCAAlarmDevicePostSoak.cpp \
  db/src/axDbCAAlarmCoincidentalSoaking.cpp \
  db/src/axDbCAAlarmCoincidentalPostSoak.cpp \
  db/src/axDbCmtsCurrentCounts.cpp \
  db/src/axDbCmtsCountsLog.cpp \
  db/src/axDbHfcCurrentCounts.cpp \
  db/src/axDbHfcCountsLog.cpp \
  db/src/axDbChannelCurrentCounts.cpp \
  db/src/axDbChannelCountsLog.cpp \
  db/src/axDbAbstractCmPerf.cpp \
  db/src/axDbCurrentCmPerf.cpp \
  db/src/axDbCmPerfLog.cpp \
  db/src/axDbAbstractStatus.cpp \
  db/src/axDbCmStatusLog.cpp \
  db/src/axDbMtaPingStatusLog.cpp \
  db/src/axDbMtaProvStatusLog.cpp \
  db/src/axDbMtaAvailLog.cpp \
  db/src/axDbAppConfig.cpp \
  db/src/axAbsDbSnmpVerAttrs.cpp \
  db/src/axDbSnmpV2CAttrs.cpp \
  db/src/axDbSnmpAttrHolder.cpp \
  db/src/axDbCARootAlarm.cpp \
  db/src/axDbCACurrentAlarm.cpp \
  db/src/axDbCAHistoricalAlarm.cpp \
  db/src/axDbAbstractCurrentCounts.cpp \
  db/src/axDbMtaCurrentAvailability.cpp \
  db/src/axDbCmCurrentStatus.cpp \
  db/src/axDbSummaryFlags.cpp \
  db/src/axDbLastCompSummary.cpp \
  db/src/axDbAbstractCurrentStatus.cpp \
  db/src/axDbHfcCurrentStatus.cpp \
  db/src/axDbHfcCurrentTca.cpp \
  db/src/axDbHfcStatusLog.cpp \
  db/src/axDbHfcTcaLog.cpp \
  db/src/axDbAbstractCounts.cpp \
  db/src/axDbAbstractTopoLookup.cpp \
  db/src/axDbTopoLookupGenMta.cpp \
  db/src/axDbLocalSystem.cpp \
  db/src/axDbAbstractTopoContainer.cpp \
  db/src/axDbBlade.cpp \
  db/src/axDbEmtaSecondary.cpp \
  ds_and_algo/src/axAbstractInternalObject.cpp \
  ds_and_algo/src/axInternalObjList.cpp \
  ds_and_algo/src/axInternalCmts.cpp \
  ds_and_algo/src/axInternalCm.cpp \
  ds_and_algo/src/axInternalEmta.cpp \
  ds_and_algo/src/axInternalGenMta.cpp \
  ds_and_algo/src/axInternalMta.cpp \
  ds_and_algo/src/axGlobalData.cpp \
  ds_and_algo/src/axGlobalCmtsTable.cpp \
  ds_and_algo/src/axInternalChannel.cpp \
  ds_and_algo/src/axInternalHfc.cpp \
  ds_and_algo/src/axInternalUtils.cpp \
  pollers/src/axAbstractPoller.cpp \
  pollers/src/axCmtsPoller.cpp \
  pollers/src/axMtaPoller.cpp \
  pollers/src/axMtaPinger.cpp \
  pollers/src/axCmtsCmPerfPoller.cpp \
  pollers/src/axCmPerfPoller.cpp \
  pollers/src/axPollerUtils.cpp \
  pollers/src/axCADbConfigChangePoller.cpp \
  pollers/src/axCAFqdnToIpPoller.cpp \
  runnables/src/axAbstractCARunnable.cpp \
  runnables/src/axAbstractRunnableCollection.cpp \
  runnables/src/axAbstractHfcAlarmDetectionRun.cpp \
  runnables/src/axCmtsPollRunnable.cpp \
  runnables/src/axSimpleRunnableCollection.cpp \
  runnables/src/axCmtsPollRunnableCollection.cpp \
  runnables/src/axAbstractCARunnableCollection.cpp \
  runnables/src/axHighRateMtaPollRunnable.cpp \
  runnables/src/axHRMtaPollRunnableCollection.cpp \
  runnables/src/axHighRateMtaPingRunnable.cpp \
  runnables/src/axHRMtaPingRunnableCollection.cpp \
  runnables/src/axCmtsCmPerfPollRunnable.cpp \
  runnables/src/axCmPerfPollRunnable.cpp \
  runnables/src/axCmtsCmPerfPollRunnableCollection.cpp \
  runnables/src/axHfcAlarmDetectionRunnable.cpp \
  runnables/src/axHfcAlarmDetectionRunnableCollection.cpp \
  runnables/src/axCmPerfPollRunnableCollection.cpp \
  runnables/src/axCmPerfPollRunnable.cpp \
  runnables/src/axHfcMtaAlarmDetectionRC.cpp \
  runnables/src/axHfcMtaAlarmDetectionRunnable.cpp \
  runnables/src/axDailySummaryRC.cpp \
  runnables/src/axDailySummaryRunnable.cpp \
  scheduler/src/axCAScheduler.cpp \
  scheduler/src/axSchedTaskExecutor.cpp \
  scheduler/src/axSchedPriRunnableQ.cpp \
  snmp/src/axSnmpSubSystem.cpp \
  snmp/src/axSnmpSession.cpp \
  snmp/src/axSnmpSessionHelper.cpp \
  snmp/src/axSnmpSessionFactory.cpp \
  snmp/src/axAbstractSnmpRequestType.cpp \
  snmp/src/axSnmpMultiRowMultiVarReqType.cpp \
  snmp/src/axSnmpMultiRowOneVarReqType.cpp \
  snmp/src/axSnmpMultipleRowsRequestType.cpp \
  snmp/src/axSnmpOneRowMultiVarReqType.cpp \
  snmp/src/axSnmpOneRowOneVarReqType.cpp \
  snmp/src/axSnmpOneRowRequestType.cpp \
  snmp/src/axSnmpCmtsCmStatusReqType.cpp \
  snmp/src/axSnmpUtils.cpp \
  snmp/src/axAbstractSnmpPollWork.cpp \
  snmp/src/axAbstractSnmpMultipleGetWork.cpp \
  snmp/src/axAbstractSnmpSingleGetWork.cpp \
  snmp/src/axHighRateCmtsCmStatusTblWalk.cpp \
  snmp/src/axSnmpCmtsChannelReqType.cpp \
  snmp/src/axHighRateCmtsChannelTblWalk.cpp \
  snmp/src/axSnmpGenericGetNext.cpp \
  snmp/src/axSnmpHRMtaReqType.cpp \
  snmp/src/axHRMtaPollWork.cpp \
  snmp/src/axSnmpCmPerfCmReqType.cpp \
  snmp/src/axCmPerfCm.cpp \
  snmp/src/axSnmpDownChannelPerfReqType.cpp \
  snmp/src/axDownChannelPerfWalk.cpp \
  snmp/src/axSnmpTemplateSession.cpp \
  snmp/src/axSnmpVerSpecificAttrSetter.cpp \
  snmp/src/axAbstractSnmpFloodGetWork.cpp \
  snmp/src/axAbsSnmpAsyncPollWork.cpp \
  snmp/src/axSnmpSessionHolder.cpp \
  snmp/src/axAbstractSnmpSession.cpp \
  snmp/src/axSnmpAsyncCbReqObj.cpp \
  snmp/src/axCsrMtaPollWork.cpp \
  snmp/src/axCsrCmPoll.cpp \
  snmp/src/axCsrCmtsCmPoll.cpp \
  timer/src/axGlobalTimer.cpp \
  timer/src/axTimerQProcessor.cpp \
  timer/src/axPoppedTimerQ.cpp \
  timer/src/axPoppedTimerProcessor.cpp \
  timer/src/axAbstractTimerCallback.cpp \
  timer/src/axAbstractTimerData.cpp \
  timer/src/axAbstractTimerObject.cpp \
  timer/src/axCmtsPollTimer.cpp \
  timer/src/axHRMtaPollTimer.cpp \
  timer/src/axHRMtaPingTimer.cpp \
  timer/src/axCmtsCmPerfPollTimer.cpp \
  timer/src/axGenericTimerQ.cpp \
  timer/src/axSoakTimerQ.cpp \
  timer/src/axSoakTimerQProcessor.cpp \
  timer/src/axAlarmTimerEntry.cpp \
  timer/src/axHfcAlarmTimerEntry.cpp \
  timer/src/axMtaAlarmTimerEntry.cpp \
  timer/src/axCmPerfPollTimer.cpp \
  timer/src/axDailySummaryTimer.cpp \
  messaging/src/axAbstractCAMessage.cpp \
  messaging/src/axMessageSubSystem.cpp \
  messaging/src/axMessageManager.cpp \
  messaging/src/axAbstractMessageSubscriber.cpp \
  messaging/src/axCAMessageSubscriber.cpp \
  messaging/src/axAbstractMessageQueue.cpp \
  messaging/src/axCAMessageQueue.cpp \
  messaging/src/axCAMessage.cpp \
  messaging/src/axMessageQCollection.cpp \
  messaging/src/axCountsAndAlarmMsgQ.cpp \
  messaging/src/axCountsAndAlarmsQWorker.cpp \
  messaging/src/axCountsAndAlarmMsg.cpp \
  messaging/src/axAlarmProcessingMsg.cpp \
  messaging/src/axAlarmProcessingMsgQ.cpp \
  messaging/src/axAlarmProcessingQWorker.cpp \
  counts/src/axAbstractCACounts.cpp \
  counts/src/axCorrelatedAlarmDetection.cpp \
  counts/src/axCorrelatedCounts.cpp \
  counts/src/axChannelCounts.cpp \
  counts/src/axChannelMtaCounts.cpp \
  counts/src/axHfcCounts.cpp \
  counts/src/axHfcMtaCounts.cpp \
  counts/src/axCountsSubSystem.cpp \
  counts/src/axCmtsCounts.cpp \
  counts/src/axCmtsMtaCounts.cpp \
  counts/src/axHfcAlarmDetection.cpp \
  counts/src/axChannelAlarmDetection.cpp \
  alarms/src/axAbstractCAAlarmCollection.cpp \
  alarms/src/axAbstractCAAlarm.cpp \
  alarms/src/axAlarmSubSystem.cpp \
  alarms/src/axHfcCurrentAlarmTable.cpp \
  alarms/src/axMtaCurrentAlarmTable.cpp \
  alarms/src/axCAAlarmCollection.cpp \
  alarms/src/axHfcAlarmCollectionofRCs.cpp \
  alarms/src/axHfcAlarm.cpp \
  alarms/src/axHfcAlarmMtaCount.cpp \
  alarms/src/axHfcAlarmPercent.cpp \
  alarms/src/axHfcAlarmPower.cpp \
  alarms/src/axMtaAlarm.cpp \
  alarms/src/axMtaAlarmUnavail.cpp \
  alarms/src/axMtaAlarmOnBattery.cpp \
  alarms/src/axMtaAlarmBattMissing.cpp \
  alarms/src/axHfcMtaAlarmCollectionOfRCs.cpp \
  socket/src/axAbstractCASocket.cpp \
  socket/src/axIcmpCASocket.cpp \
  socket/src/axIcmpV4CASocket.cpp \
  socket/src/axIcmpSocketFactory.cpp \
  socket/src/axIcmpV6CASocket.cpp \
  socket/src/axIcmpTemplateSocket.cpp \
  socket/src/axIcmpSocketHolder.cpp \
  socket/src/axSocketUtils.cpp \
  ping/src/axPinger.cpp \
  misc/src/axDeviceClassification.cpp \
  misc/src/axDcData.cpp \
  misc/src/axCALoadDbConfig.cpp \
  misc/src/axConfigDownloader.cpp \
  summary/src/axAbstractSummary.cpp \
  summary/src/axAbstractCountsSummary.cpp \
  summary/src/axCmtsCountsSummary.cpp \
  summary/src/axHfcCountsSummary.cpp \
  summary/src/axChannelCountsSummary.cpp \
  summary/src/axCmPerfSummary.cpp \
  summary/src/axDailySummary.cpp \
  summary/src/axAbstractStatusSummary.cpp \
  summary/src/axCmStatusSummary.cpp \
  summary/src/axMtaAvailabilitySummary.cpp \
  summary/src/axCmPerfThreshSummary.cpp \
  summary/src/axHfcStatusSummary.cpp \
  summary/src/axHfcTcaSummary.cpp \
  cPeerServices/src/cPeerServServer.cpp \
  cPeerServices/src/cPeerServClient.cpp \
  cPeerServices/src/CmsServClient.cpp \
  cPeerServices/src/CteServClient.cpp \
  cPeerServices/src/allC.cpp \
  cPeerServices/src/cPeerServImpl.cpp \
  cPeerServices/src/axAllNsmap.cpp \
  cPeerServices/src/axSoapServiceAcceptor.cpp \
  cPeerServices/src/axSoapServiceProcessor.cpp \
  cPeerServices/src/axSoapRequestQ.cpp \
  cPeerServices/src/axAbstractCPeerServices.cpp \
  cPeerServices/src/axLocalSystemSingleton.cpp \
  cPeerServices/src/axBladeCPeerServices.cpp \
  cPeerServices/src/axMarketCPeerServices.cpp \
  cPeerServices/src/AdminServClient.cpp \
  utils/src/axCmPerfUtils.cpp \
  utils/src/axHfcAlarmUtils.cpp \
  utils/src/axMtaStatusUtils.cpp \
  xml/src/axAbstractWrappedStream.cpp \
  xml/src/axBinSocketInputStream.cpp \
  xml/src/axTcpSocketInputSource.cpp \
  xml/src/axWrappedInputStream.cpp \
  xml/src/axWrappedOutputStream.cpp \
  xml/src/axXmlErrorHandler.cpp \
  trap/src/axXmlTrapReceiver.cpp \
#  cPeerServices/src/CteServC.cpp \
#  cPeerServices/src/CmsServC.cpp \
#  cPeerServices/src/cPeerServC.cpp \
#  cPeerServices/src/cPeerServClient.cpp \
#  snmp/src/axSnmpRequestObj.cpp \
#  snmp/src/axSnmpAsyncCallbackObj.cpp \
#  snmp/src/axAbsSnmpSyncPollWork.cpp \
#  socket/src/axIcmpSocketHelper.cpp \
#  socket/src/axSocketHelper.cpp \
#  snmp/src/axSnmpV2cSessionHelper.cpp \
#  snmp/src/axSnmpV3SessionHelper.cpp \
#  snmp/src/axSnmpV1Session.cpp \
#  snmp/src/axSnmpV2cSession.cpp \
#  snmp/src/axSnmpV3Session.cpp \
#  scheduler/src/axSchedTaskExecutorQ.cpp \
#  scheduler/src/axTaskScheduler.cpp \
#  runnables/src/axCmtsPollDataProcRunnable.cpp \
#  alarms/src/axAlarmTrackingMgr.cpp \
#  alarms/src/axHfcAlarmCollections.cpp \
#  db/src/axDbCACurrentAlarm.cpp \
#  timer/src/axOutageTimerEntry.cpp \
#  timer/src/axHfcOutageTimerEntry.cpp \
#  timer/src/axMtaOutageTimerEntry.cpp \
#  runnables/src/axHfcOutageDetectionRunnable.cpp \
#  runnables/src/axHfcOutageDetectionRunnableCollection.cpp \
#  outages/src/axAbstractCAOutage.cpp \
#  outages/src/axAbstractCAOutageCollection.cpp \
#  outages/src/axHfcOutage.cpp \
#  outages/src/axCAOutageCollection.cpp \
#  outages/src/axMtaOutage.cpp \
#  outages/src/axOutageSubSystem.cpp \
#  outages/src/axHfcOutageCollections.cpp \
#  outages/src/axOutageTrackingMgr.cpp \
#  outages/src/axHfcOutagePercent.cpp \
#  outages/src/axHfcOutageMtaCount.cpp \
#  messaging/src/axCountsAndOutageMsgQ.cpp \
#  messaging/src/axCountsAndOutagesQWorker.cpp \
#  messaging/src/axCountsAndOutageMsg.cpp \
#  messaging/src/axDetectHfcOutageMsg.cpp \
#  db/src/axDbCAOutage.cpp \
#  db/src/axDbCACurrentOutage.cpp \
#  db/src/axDbAbstractCmStatus.cpp \
#  db/src/axDbCmtsCounts.cpp \
#  db/src/axDbHfcCounts.cpp \
#  db/src/axDbSampleQuery.cpp \
#  messaging/src/axAbstractMessageCallback.cpp \
#  messaging/src/axAbstractMessageData.cpp \
#  snmp/src/axSnmpCmtsCmStatusTblWalk.cpp \
#

#----------------------------------------------------------------------------
#       Include macros and targets
#----------------------------------------------------------------------------
ACE_SHLIBS =

INSLIB ?= ../lib
ifeq ($(INSLIB),.)
  ifeq ($(PWD),)
    PWD=$(shell pwd)
  endif
  INSLIB = $(PWD)
endif
OUTPUT_DIRECTORY = $(INSLIB)

include $(PROJECT_ROOT)/include/makeinclude/wrapper_macros.GNU
## We don't need the ACELIB setting from wrapper_macros.GNU
ACELIB =

ifneq ($(OUTPUT_DIRECTORY),)
all: $(OUTPUT_DIRECTORY)
$(OUTPUT_DIRECTORY):
	-@$(MKDIR) "$(OUTPUT_DIRECTORY)"
endif

LIBCHECK = 1
ifneq ($(LIBCHECK), 1)
  LIB   =
  SHLIB =
  all: lib_warning
endif

LSRC   = $(FILES)

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.common.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.nonested.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.lib.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.local.GNU

CPPFLAGS     += -I$(PROJECT_ROOT)/include -I$(ACE_ROOT) \
                -I$(PROJECT_ROOT)/common/c_cpp/libsrc/netdatasys/base/include \
                -I$(PROJECT_ROOT)/project_tiger/common/include \
                -I$(PROJECT_ROOT)/project_tiger/db/include \
                -I$(PROJECT_ROOT)/project_tiger/ds_and_algo/include \
                -I$(PROJECT_ROOT)/project_tiger/pollers/include \
                -I$(PROJECT_ROOT)/project_tiger/runnables/include \
                -I$(PROJECT_ROOT)/project_tiger/scheduler/include \
                -I$(PROJECT_ROOT)/project_tiger/snmp/include \
                -I$(PROJECT_ROOT)/project_tiger/timer/include \
                -I$(PROJECT_ROOT)/project_tiger/messaging/include \
                -I$(PROJECT_ROOT)/project_tiger/counts/include \
                -I$(PROJECT_ROOT)/project_tiger/alarms/include \
                -I$(PROJECT_ROOT)/project_tiger/socket/include \
                -I$(PROJECT_ROOT)/project_tiger/ping/include \
                -I$(PROJECT_ROOT)/project_tiger/misc/include \
                -I$(PROJECT_ROOT)/project_tiger/summary/include \
                -I$(PROJECT_ROOT)/project_tiger/cPeerServices/include \
                -I$(PROJECT_ROOT)/project_tiger/utils/include \
                -I$(PROJECT_ROOT)/project_tiger/xml/include \
                -I$(PROJECT_ROOT)/project_tiger/trap/include \
                -I$(PROJECT_ROOT)/common/c_cpp/libsrc/thirdparty/ds_and_algos/gnu_avl/include \
                -I$(PROJECT_ROOT)/common/c_cpp/libsrc/thirdparty/netsnmp/netsnmp/include \
                -I$(PROJECT_ROOT)/common/c_cpp/lib/thirdparty/mysql/mysql/include \
                -I$(PROJECT_ROOT)/common/c_cpp/lib/thirdparty/gsoap/include \
                -I$(PROJECT_ROOT)/common/c_cpp/libsrc/thirdparty/xerces/xerces/include \
#

CPPFLAGS     += -DACE_HAS_ACE_TOKEN -DACE_HAS_ACE_SVCCONF
ifeq ($(shared_libs),1)
  ifneq ($(SHLIB),)
    CPPFLAGS += -DACE_BUILD_DLL
  endif
endif
ifeq ($(static_libs),1)
  CPPFLAGS += -DACE_AS_STATIC_LIBS
endif

#----------------------------------------------------------------------------
#       Local targets
#----------------------------------------------------------------------------
lib_warning:
	@echo This project will not be built due to the following missing library:
	@echo $(LIBCHECK)

## Some OS's have /bin/test others only have /usr/bin/test
ifeq ($(wildcard /bin/test), /bin/test)
  TEST_EXE = /bin/test
else
ifeq ($(wildcard /usr/bin/test), /usr/bin/test)
  TEST_EXE = /usr/bin/test
endif
endif

realclean: clean
	-$(RM) -rf .obj
	-$(RM) -rf .shobj
	-$(RM) $(LIB_UNCHECKED)
	-$(RM) $(SHLIB_UNCHECKED)
	-$(RM) $(DEPENDENCY_FILE)
	-$(RM) lib*
ifneq ($(GENERATED_DIRTY),)
	-$(RM) -r $(GENERATED_DIRTY)
endif

