/**
 * ReportsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Reports;

public class ReportsServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsService {

    public ReportsServiceLocator() {
    }


    public ReportsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReportsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Prem:
    private String EP_Protocol = "http://";
    private String EP_HostPort = "localhost:8080";
    public void setHost(String s) {
    	this.EP_HostPort = s;
    }
    private String EP_Path = "/CableAssurance/caservices/ReportsEP";

    // Use to get a proxy class for ReportsEP
    // Prem
    // private java.lang.String ReportsEP_address = "http://localhost:8080/CableAssurance/caservices/ReportsEP";

    public java.lang.String getReportsEPAddress() {
    	// Prem
        return (EP_Protocol+EP_HostPort+EP_Path);
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ReportsEPWSDDServiceName = "ReportsEP";

    public java.lang.String getReportsEPWSDDServiceName() {
        return ReportsEPWSDDServiceName;
    }

    public void setReportsEPWSDDServiceName(java.lang.String name) {
        ReportsEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsEP getReportsEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getReportsEPAddress());
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getReportsEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsEP getReportsEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub(portAddress, this);
            _stub.setPortName(getReportsEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setReportsEPEndpointAddress(java.lang.String address) {
    	// Prem
        // ReportsEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub(new java.net.URL(getReportsEPAddress()), this);
                _stub.setPortName(getReportsEPWSDDServiceName());
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
        if ("ReportsEP".equals(inputPortName)) {
            return getReportsEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "ReportsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "ReportsEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ReportsEP".equals(portName)) {
            setReportsEPEndpointAddress(address);
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
