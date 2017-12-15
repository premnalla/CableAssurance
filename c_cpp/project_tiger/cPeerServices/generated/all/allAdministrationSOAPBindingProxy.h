/* allAdministrationSOAPBindingProxy.h
   Generated by gSOAP 2.7.9c from include/allServices.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/

#ifndef allAdministrationSOAPBinding_H
#define allAdministrationSOAPBinding_H
#include "allH.h"
extern SOAP_NMAC struct Namespace all_namespaces[];
class AdministrationSOAPBinding
{   public:
	struct soap *soap;
	const char *endpoint;
	AdministrationSOAPBinding() { soap = soap_new(); if (soap) soap->namespaces = all_namespaces; endpoint = "http://localhost:8080/CableAssurance/caservices/AdministrationEP"; };
	virtual ~AdministrationSOAPBinding() { if (soap) { soap_destroy(soap); soap_end(soap); soap_free(soap); } };
	virtual int adm__updateCmts(ns1__CmtsT *cmts, short &result) { return soap ? soap_call_adm__updateCmts(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__addCmts(ns1__CmtsT *cmts, short &result) { return soap ? soap_call_adm__addCmts(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__deleteCmts(ns1__CmtsT *cmts, short &result) { return soap ? soap_call_adm__deleteCmts(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__addCmtsAllSnmpV2CAttributes(ns1__TopoHierarchyKeyT *topologyKey, std::string cmtsResId, ArrayOfSnmpV2CAttributesT *attributes, short &result) { return soap ? soap_call_adm__addCmtsAllSnmpV2CAttributes(soap, endpoint, NULL, topologyKey, cmtsResId, attributes, result) : SOAP_EOM; };
	virtual int adm__updateCmtsAllSnmpV2CAttributes(ns1__TopoHierarchyKeyT *topologyKey, std::string cmtsResId, ArrayOfSnmpV2CAttributesT *attributes, short &result) { return soap ? soap_call_adm__updateCmtsAllSnmpV2CAttributes(soap, endpoint, NULL, topologyKey, cmtsResId, attributes, result) : SOAP_EOM; };
	virtual int adm__updateCms(ns1__CmsT *cms, short &result) { return soap ? soap_call_adm__updateCms(soap, endpoint, NULL, cms, result) : SOAP_EOM; };
	virtual int adm__addCms(ns1__CmsT *cms, short &result) { return soap ? soap_call_adm__addCms(soap, endpoint, NULL, cms, result) : SOAP_EOM; };
	virtual int adm__deleteCms(ns1__CmsT *cms, short &result) { return soap ? soap_call_adm__deleteCms(soap, endpoint, NULL, cms, result) : SOAP_EOM; };
	virtual int adm__getPollingIntervals(struct adm__getPollingIntervalsResponse &_param_1) { return soap ? soap_call_adm__getPollingIntervals(soap, endpoint, NULL, _param_1) : SOAP_EOM; };
	virtual int adm__updatePollingIntervals(ns1__PollingIntervalsT *pollintInterval, short &result) { return soap ? soap_call_adm__updatePollingIntervals(soap, endpoint, NULL, pollintInterval, result) : SOAP_EOM; };
	virtual int adm__getMtaStatusThreshold(struct adm__getMtaStatusThresholdResponse &_param_2) { return soap ? soap_call_adm__getMtaStatusThreshold(soap, endpoint, NULL, _param_2) : SOAP_EOM; };
	virtual int adm__updateMtaStatusThreshold(ns1__MtaStatusThresholdT *pollintInterval, short &result) { return soap ? soap_call_adm__updateMtaStatusThreshold(soap, endpoint, NULL, pollintInterval, result) : SOAP_EOM; };
	virtual int adm__getHfcStatusThreshold(struct adm__getHfcStatusThresholdResponse &_param_3) { return soap ? soap_call_adm__getHfcStatusThreshold(soap, endpoint, NULL, _param_3) : SOAP_EOM; };
	virtual int adm__updateHfcStatusThreshold(ns1__HfcStatusThresholdT *pollintInterval, short &result) { return soap ? soap_call_adm__updateHfcStatusThreshold(soap, endpoint, NULL, pollintInterval, result) : SOAP_EOM; };
	virtual int adm__getChannelStatusThreshold(struct adm__getChannelStatusThresholdResponse &_param_4) { return soap ? soap_call_adm__getChannelStatusThreshold(soap, endpoint, NULL, _param_4) : SOAP_EOM; };
	virtual int adm__updateChannelStatusThreshold(ns1__ChannelStatusThresholdT *pollintInterval, short &result) { return soap ? soap_call_adm__updateChannelStatusThreshold(soap, endpoint, NULL, pollintInterval, result) : SOAP_EOM; };
	virtual int adm__getCmtsStatusThreshold(struct adm__getCmtsStatusThresholdResponse &_param_5) { return soap ? soap_call_adm__getCmtsStatusThreshold(soap, endpoint, NULL, _param_5) : SOAP_EOM; };
	virtual int adm__updateCmtsStatusThreshold(ns1__CmtsStatusThresholdT *pollintInterval, short &result) { return soap ? soap_call_adm__updateCmtsStatusThreshold(soap, endpoint, NULL, pollintInterval, result) : SOAP_EOM; };
	virtual int adm__getCmsStatusThreshold(struct adm__getCmsStatusThresholdResponse &_param_6) { return soap ? soap_call_adm__getCmsStatusThreshold(soap, endpoint, NULL, _param_6) : SOAP_EOM; };
	virtual int adm__updateCmsStatusThreshold(ns1__CmsStatusThresholdT *pollintInterval, short &result) { return soap ? soap_call_adm__updateCmsStatusThreshold(soap, endpoint, NULL, pollintInterval, result) : SOAP_EOM; };
	virtual int adm__getMtaAlarmConfig(struct adm__getMtaAlarmConfigResponse &_param_7) { return soap ? soap_call_adm__getMtaAlarmConfig(soap, endpoint, NULL, _param_7) : SOAP_EOM; };
	virtual int adm__updateMtaAlarmConfig(ns1__MtaAlarmConfigT *alarmConfig, short &result) { return soap ? soap_call_adm__updateMtaAlarmConfig(soap, endpoint, NULL, alarmConfig, result) : SOAP_EOM; };
	virtual int adm__getHfcAlarmConfig(struct adm__getHfcAlarmConfigResponse &_param_8) { return soap ? soap_call_adm__getHfcAlarmConfig(soap, endpoint, NULL, _param_8) : SOAP_EOM; };
	virtual int adm__updateHfcAlarmConfig(ns1__HfcAlarmConfigT *alarmConfig, short &result) { return soap ? soap_call_adm__updateHfcAlarmConfig(soap, endpoint, NULL, alarmConfig, result) : SOAP_EOM; };
	virtual int adm__getCmtsAlarmConfig(struct adm__getCmtsAlarmConfigResponse &_param_9) { return soap ? soap_call_adm__getCmtsAlarmConfig(soap, endpoint, NULL, _param_9) : SOAP_EOM; };
	virtual int adm__updateCmtsAlarmConfig(ns1__CmtsAlarmConfigT *alarmConfig, short &result) { return soap ? soap_call_adm__updateCmtsAlarmConfig(soap, endpoint, NULL, alarmConfig, result) : SOAP_EOM; };
	virtual int adm__getCmsAlarmConfig(struct adm__getCmsAlarmConfigResponse &_param_10) { return soap ? soap_call_adm__getCmsAlarmConfig(soap, endpoint, NULL, _param_10) : SOAP_EOM; };
	virtual int adm__updateCmsAlarmConfig(ns1__CmsAlarmConfigT *alarmConfig, short &result) { return soap ? soap_call_adm__updateCmsAlarmConfig(soap, endpoint, NULL, alarmConfig, result) : SOAP_EOM; };
	virtual int adm__updateLocalSystem(ns1__LocalSystemT *cmts, short &result) { return soap ? soap_call_adm__updateLocalSystem(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__updateRegion(ns1__RegionT *cmts, short &result) { return soap ? soap_call_adm__updateRegion(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__addRegion(ns1__RegionT *cmts, short &result) { return soap ? soap_call_adm__addRegion(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__updateMarket(ns1__MarketT *cmts, short &result) { return soap ? soap_call_adm__updateMarket(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__addMarket(ns1__MarketT *cmts, short &result) { return soap ? soap_call_adm__addMarket(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__updateBlade(ns1__BladeT *cmts, short &result) { return soap ? soap_call_adm__updateBlade(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__addBlade(ns1__BladeT *cmts, short &result) { return soap ? soap_call_adm__addBlade(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__deleteBlade(ns1__BladeT *cmts, short &result) { return soap ? soap_call_adm__deleteBlade(soap, endpoint, NULL, cmts, result) : SOAP_EOM; };
	virtual int adm__getCmPerfConfig(struct adm__getCmPerfConfigResponse &_param_11) { return soap ? soap_call_adm__getCmPerfConfig(soap, endpoint, NULL, _param_11) : SOAP_EOM; };
	virtual int adm__updateCmPerfConfig(ns1__CmPerformanceConfigT *cmPerf, short &result) { return soap ? soap_call_adm__updateCmPerfConfig(soap, endpoint, NULL, cmPerf, result) : SOAP_EOM; };
	virtual int adm__addUser(ns1__UserT *user, short &result) { return soap ? soap_call_adm__addUser(soap, endpoint, NULL, user, result) : SOAP_EOM; };
	virtual int adm__getUsers(struct adm__getUsersResponse &_param_12) { return soap ? soap_call_adm__getUsers(soap, endpoint, NULL, _param_12) : SOAP_EOM; };
	virtual int adm__getUser(std::string loginName, struct adm__getUserResponse &_param_13) { return soap ? soap_call_adm__getUser(soap, endpoint, NULL, loginName, _param_13) : SOAP_EOM; };
	virtual int adm__updateUser(ns1__UserT *user, short &result) { return soap ? soap_call_adm__updateUser(soap, endpoint, NULL, user, result) : SOAP_EOM; };
	virtual int adm__updateUserPassword(std::string loginName, std::string newPassword, short &result) { return soap ? soap_call_adm__updateUserPassword(soap, endpoint, NULL, loginName, newPassword, result) : SOAP_EOM; };
	virtual int adm__getRoles(struct adm__getRolesResponse &_param_14) { return soap ? soap_call_adm__getRoles(soap, endpoint, NULL, _param_14) : SOAP_EOM; };
	virtual int adm__getRole(std::string roleName, struct adm__getRoleResponse &_param_15) { return soap ? soap_call_adm__getRole(soap, endpoint, NULL, roleName, _param_15) : SOAP_EOM; };
	virtual int adm__updateRole(ns1__RoleT *role, short &result) { return soap ? soap_call_adm__updateRole(soap, endpoint, NULL, role, result) : SOAP_EOM; };
	virtual int adm__downloadConfigFromParent(short &rc) { return soap ? soap_call_adm__downloadConfigFromParent(soap, endpoint, NULL, rc) : SOAP_EOM; };
	virtual int adm__getConfig(struct adm__getConfigResponse &_param_16) { return soap ? soap_call_adm__getConfig(soap, endpoint, NULL, _param_16) : SOAP_EOM; };
};
#endif
