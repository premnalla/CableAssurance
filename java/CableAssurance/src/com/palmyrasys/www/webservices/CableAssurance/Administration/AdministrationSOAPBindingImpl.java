/**
 * AdministrationSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Administration;

// Prem
import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;

public class AdministrationSOAPBindingImpl
		implements
		com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationEP {
	public short updateCmts(
			com.palmyrasys.www.webservices.CableAssurance.CmtsT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmts(cmts));
	}

	public short addCmts(
			com.palmyrasys.www.webservices.CableAssurance.CmtsT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addCmts(cmts));
	}

	public short deleteCmts(
			com.palmyrasys.www.webservices.CableAssurance.CmtsT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.deleteCmts(cmts));
	}

	public short addCmtsAllSnmpV2CAttributes(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger cmtsResId,
			com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[] attributes)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId,
				attributes));
	}

	public short updateCmtsAllSnmpV2CAttributes(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger cmtsResId,
			com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[] attributes)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId,
				attributes));
	}

	public short updateCms(
			com.palmyrasys.www.webservices.CableAssurance.CmsT cms)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCms(cms));
	}

	public short addCms(com.palmyrasys.www.webservices.CableAssurance.CmsT cms)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addCms(cms));
	}

	public short deleteCms(
			com.palmyrasys.www.webservices.CableAssurance.CmsT cms)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.deleteCms(cms));
	}

	public com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT getPollingIntervals()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getPollingIntervals());
	}

	public short updatePollingIntervals(
			com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT pollintInterval)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updatePollingIntervals(pollintInterval));
	}

	public com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT getMtaStatusThreshold()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getMtaStatusThreshold());
	}

	public short updateMtaStatusThreshold(
			com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT pollintInterval)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateMtaStatusThreshold(pollintInterval));
	}

	public com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT getHfcStatusThreshold()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getHfcStatusThreshold());
	}

	public short updateHfcStatusThreshold(
			com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT pollintInterval)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateHfcStatusThreshold(pollintInterval));
	}

	public com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT getChannelStatusThreshold()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getChannelStatusThreshold());
	}

	public short updateChannelStatusThreshold(
			com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT pollintInterval)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateChannelStatusThreshold(pollintInterval));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT getCmtsStatusThreshold()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getCmtsStatusThreshold());
	}

	public short updateCmtsStatusThreshold(
			com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT pollintInterval)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmtsStatusThreshold(pollintInterval));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT getCmsStatusThreshold()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getCmsStatusThreshold());
	}

	public short updateCmsStatusThreshold(
			com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT pollintInterval)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmsStatusThreshold(pollintInterval));
	}

	public com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT getMtaAlarmConfig()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getMtaAlarmConfig());
	}

	public short updateMtaAlarmConfig(
			com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT alarmConfig)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateMtaAlarmConfig(alarmConfig));
	}

	public com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT getHfcAlarmConfig()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getHfcAlarmConfig());
	}

	public short updateHfcAlarmConfig(
			com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT alarmConfig)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateHfcAlarmConfig(alarmConfig));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT getCmtsAlarmConfig()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getCmtsAlarmConfig());
	}

	public short updateCmtsAlarmConfig(
			com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT alarmConfig)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmtsAlarmConfig(alarmConfig));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT getCmsAlarmConfig()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getCmsAlarmConfig());
	}

	public short updateCmsAlarmConfig(
			com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT alarmConfig)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmsAlarmConfig(alarmConfig));
	}

	public short updateLocalSystem(
			com.palmyrasys.www.webservices.CableAssurance.LocalSystemT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateLocalSystem(cmts));
	}

	public short updateRegion(
			com.palmyrasys.www.webservices.CableAssurance.RegionT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateRegion(cmts));
	}

	public short addRegion(
			com.palmyrasys.www.webservices.CableAssurance.RegionT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addRegion(cmts));
	}

	public short updateMarket(
			com.palmyrasys.www.webservices.CableAssurance.MarketT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateMarket(cmts));
	}

	public short addMarket(
			com.palmyrasys.www.webservices.CableAssurance.MarketT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addMarket(cmts));
	}

	public short updateBlade(
			com.palmyrasys.www.webservices.CableAssurance.BladeT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateBlade(cmts));
	}

	public short addBlade(
			com.palmyrasys.www.webservices.CableAssurance.BladeT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addBlade(cmts));
	}

	public short deleteBlade(
			com.palmyrasys.www.webservices.CableAssurance.BladeT cmts)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.deleteBlade(cmts));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT getCmPerfConfig()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getCmPerfConfig());
	}

	public short updateCmPerfConfig(
			com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT cmPerf)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateCmPerfConfig(cmPerf));
	}

	public short addUser(
			com.palmyrasys.www.webservices.CableAssurance.UserT user)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addUser(user));
	}

	public com.palmyrasys.www.webservices.CableAssurance.UserT[] getUsers()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getUsers());
	}

	public com.palmyrasys.www.webservices.CableAssurance.UserT getUser(
			java.lang.String loginName) throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getUser(loginName));
	}

	public short updateUser(
			com.palmyrasys.www.webservices.CableAssurance.UserT user)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateUser(user));
	}

	public short updateUserPassword(java.lang.String loginName,
			java.lang.String newPassword) throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateUserPassword(loginName, newPassword));
	}

	public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] getApplicationDomains()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getApplicationDomains());
	}

	public com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] getAccessRights()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getAccessRights());
	}

	public com.palmyrasys.www.webservices.CableAssurance.RoleT[] getRoles()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getRoles());
	}

	public com.palmyrasys.www.webservices.CableAssurance.RoleT getRole(
			java.lang.String roleName) throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getRole(roleName));
	}

	public short addRole(
			com.palmyrasys.www.webservices.CableAssurance.RoleT role)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.addRole(role));
	}

	public short updateRole(
			com.palmyrasys.www.webservices.CableAssurance.RoleT role)
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.updateRole(role));
	}

	public short downloadConfigFromParent() throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.downloadConfigFromParent());
	}

	public com.palmyrasys.www.webservices.CableAssurance.ConfigDownloadT getConfig()
			throws java.rmi.RemoteException {
		AdministrationEP t = LocalSystemSingleton.getInstance()
				.getAdminHandler();
		return (t.getConfig());
	}

}
