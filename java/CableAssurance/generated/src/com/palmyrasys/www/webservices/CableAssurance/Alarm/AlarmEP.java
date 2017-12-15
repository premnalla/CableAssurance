/**
 * AlarmEP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

public interface AlarmEP extends java.rmi.Remote {
    public com.palmyrasys.www.webservices.CableAssurance.AlarmHistoryT[] getAlarmHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT getCurrentAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT getCurrentAlarmsForResource(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resourceId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT getCurrentAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT getCurrentAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT getHistoricalAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT getHistoricalAlarmsForResource(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resourceId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT getHistoricalAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT getHistoricalAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException;
    public short clearAlarms(com.palmyrasys.www.webservices.CableAssurance.AlarmIdT[] alarm) throws java.rmi.RemoteException;
}
