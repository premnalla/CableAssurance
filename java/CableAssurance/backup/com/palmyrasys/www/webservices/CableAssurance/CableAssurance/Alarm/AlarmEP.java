/**
 * AlarmEP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

public interface AlarmEP extends java.rmi.Remote {
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[] getCurrentAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT getCurrentAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT getCurrentAlarmDetails(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[] getCurrentAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getHistoricalAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT getHistoricalAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT getHistoricalAlarmDetails(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getHistoricalAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException;
}
