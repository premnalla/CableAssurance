/**
 * AlarmSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

// Prem:
import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;

public class AlarmSOAPBindingImpl implements com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP{
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[] getCurrentAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getCurrentAlarms(fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT getCurrentAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getCurrentAlarm(topologyKey, alarmId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT getCurrentAlarmDetails(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getCurrentAlarmDetails(topologyKey, alarmId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[] getCurrentAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getCurrentAlarmsByType(alarmType, alarmSubType, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getHistoricalAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getHistoricalAlarms(fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT getHistoricalAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getHistoricalAlarm(topologyKey, alarmId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT getHistoricalAlarmDetails(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getHistoricalAlarmDetails(topologyKey, alarmId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getHistoricalAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	AlarmEP aEP = LocalSystemSingleton.getInstance().getAlarmHandler();
    	return (aEP.getHistoricalAlarmsByType(alarmType, alarmSubType, fromTime, toTime, batch));
    }

}
