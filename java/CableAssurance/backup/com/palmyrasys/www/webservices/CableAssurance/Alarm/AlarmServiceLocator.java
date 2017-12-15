/**
 * AlarmServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

public class AlarmServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmService {

    public AlarmServiceLocator() {
    }


    public AlarmServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AlarmServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AlarmEP
    private java.lang.String AlarmEP_address = "http://localhost:8080/CableAssurance/caservices/AlarmEP";

    public java.lang.String getAlarmEPAddress() {
        return AlarmEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AlarmEPWSDDServiceName = "AlarmEP";

    public java.lang.String getAlarmEPWSDDServiceName() {
        return AlarmEPWSDDServiceName;
    }

    public void setAlarmEPWSDDServiceName(java.lang.String name) {
        AlarmEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP getAlarmEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AlarmEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAlarmEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP getAlarmEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub(portAddress, this);
            _stub.setPortName(getAlarmEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAlarmEPEndpointAddress(java.lang.String address) {
        AlarmEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub(new java.net.URL(AlarmEP_address), this);
                _stub.setPortName(getAlarmEPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AlarmEP".equals(inputPortName)) {
            return getAlarmEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "AlarmService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "AlarmEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AlarmEP".equals(portName)) {
            setAlarmEPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
