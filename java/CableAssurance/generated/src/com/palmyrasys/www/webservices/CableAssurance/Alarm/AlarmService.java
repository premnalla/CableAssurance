/**
 * AlarmService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

public interface AlarmService extends javax.xml.rpc.Service {
    public java.lang.String getAlarmEPAddress();

    public com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP getAlarmEP() throws javax.xml.rpc.ServiceException;

    public com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP getAlarmEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
