/**
 * AdministrationEP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Administration;

public interface AdministrationEP extends java.rmi.Remote {
    public short updateLocalSystem(com.palmyrasys.www.webservices.CableAssurance.LocalSystemT cmts) throws java.rmi.RemoteException;
    public short updateRegion(com.palmyrasys.www.webservices.CableAssurance.RegionT cmts) throws java.rmi.RemoteException;
    public short addRegion(com.palmyrasys.www.webservices.CableAssurance.RegionT cmts) throws java.rmi.RemoteException;
    public short updateMarket(com.palmyrasys.www.webservices.CableAssurance.MarketT cmts) throws java.rmi.RemoteException;
    public short addMarket(com.palmyrasys.www.webservices.CableAssurance.MarketT cmts) throws java.rmi.RemoteException;
    public short updateBlade(com.palmyrasys.www.webservices.CableAssurance.BladeT cmts) throws java.rmi.RemoteException;
    public short addBlade(com.palmyrasys.www.webservices.CableAssurance.BladeT cmts) throws java.rmi.RemoteException;
    public short deleteBlade(com.palmyrasys.www.webservices.CableAssurance.BladeT cmts) throws java.rmi.RemoteException;
    public short updateCmts(com.palmyrasys.www.webservices.CableAssurance.CmtsT cmts) throws java.rmi.RemoteException;
    public short addCmts(com.palmyrasys.www.webservices.CableAssurance.CmtsT cmts) throws java.rmi.RemoteException;
    public short deleteCmts(com.palmyrasys.www.webservices.CableAssurance.CmtsT cmts) throws java.rmi.RemoteException;
    public short addCmtsAllSnmpV2CAttributes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId, com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[] attributes) throws java.rmi.RemoteException;
    public short updateCmtsAllSnmpV2CAttributes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId, com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[] attributes) throws java.rmi.RemoteException;
    public short updateCms(com.palmyrasys.www.webservices.CableAssurance.CmsT cms) throws java.rmi.RemoteException;
    public short addCms(com.palmyrasys.www.webservices.CableAssurance.CmsT cms) throws java.rmi.RemoteException;
    public short deleteCms(com.palmyrasys.www.webservices.CableAssurance.CmsT cms) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT getPollingIntervals() throws java.rmi.RemoteException;
    public short updatePollingIntervals(com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT pollintInterval) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT getMtaStatusThreshold() throws java.rmi.RemoteException;
    public short updateMtaStatusThreshold(com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT pollintInterval) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT getHfcStatusThreshold() throws java.rmi.RemoteException;
    public short updateHfcStatusThreshold(com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT pollintInterval) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT getChannelStatusThreshold() throws java.rmi.RemoteException;
    public short updateChannelStatusThreshold(com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT pollintInterval) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT getCmtsStatusThreshold() throws java.rmi.RemoteException;
    public short updateCmtsStatusThreshold(com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT pollintInterval) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT getCmsStatusThreshold() throws java.rmi.RemoteException;
    public short updateCmsStatusThreshold(com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT pollintInterval) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT getMtaAlarmConfig() throws java.rmi.RemoteException;
    public short updateMtaAlarmConfig(com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT alarmConfig) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT getHfcAlarmConfig() throws java.rmi.RemoteException;
    public short updateHfcAlarmConfig(com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT alarmConfig) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT getCmtsAlarmConfig() throws java.rmi.RemoteException;
    public short updateCmtsAlarmConfig(com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT alarmConfig) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT getCmsAlarmConfig() throws java.rmi.RemoteException;
    public short updateCmsAlarmConfig(com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT alarmConfig) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT getCmPerfConfig() throws java.rmi.RemoteException;
    public short updateCmPerfConfig(com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT cmPerf) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.UserT[] getUsers() throws java.rmi.RemoteException;
    public short addUser(com.palmyrasys.www.webservices.CableAssurance.UserT user) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.UserT getUser(java.lang.String loginName) throws java.rmi.RemoteException;
    public short updateUser(com.palmyrasys.www.webservices.CableAssurance.UserT user) throws java.rmi.RemoteException;
    public short updateUserPassword(java.lang.String loginName, java.lang.String newPassword) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] getApplicationDomains() throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] getAccessRights() throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.RoleT[] getRoles() throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.RoleT getRole(java.lang.String roleName) throws java.rmi.RemoteException;
    public short addRole(com.palmyrasys.www.webservices.CableAssurance.RoleT role) throws java.rmi.RemoteException;
    public short updateRole(com.palmyrasys.www.webservices.CableAssurance.RoleT role) throws java.rmi.RemoteException;
    public short downloadConfigFromParent() throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.ConfigDownloadT getConfig() throws java.rmi.RemoteException;
}
