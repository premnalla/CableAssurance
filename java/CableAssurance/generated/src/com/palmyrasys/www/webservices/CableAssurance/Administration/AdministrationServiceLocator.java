/**
 * AdministrationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Administration;

public class AdministrationServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationService {

    public AdministrationServiceLocator() {
    }


    public AdministrationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AdministrationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AdministrationEP
    private java.lang.String AdministrationEP_address = "http://localhost:8080/CableAssurance/caservices/AdministrationEP";

    public java.lang.String getAdministrationEPAddress() {
        return AdministrationEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AdministrationEPWSDDServiceName = "AdministrationEP";

    public java.lang.String getAdministrationEPWSDDServiceName() {
        return AdministrationEPWSDDServiceName;
    }

    public void setAdministrationEPWSDDServiceName(java.lang.String name) {
        AdministrationEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationEP getAdministrationEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AdministrationEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAdministrationEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationEP getAdministrationEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub(portAddress, this);
            _stub.setPortName(getAdministrationEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAdministrationEPEndpointAddress(java.lang.String address) {
        AdministrationEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub(new java.net.URL(AdministrationEP_address), this);
                _stub.setPortName(getAdministrationEPWSDDServiceName());
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
        if ("AdministrationEP".equals(inputPortName)) {
            return getAdministrationEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Administration", "AdministrationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Administration", "AdministrationEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AdministrationEP".equals(portName)) {
            setAdministrationEPEndpointAddress(address);
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
