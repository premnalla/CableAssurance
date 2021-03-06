/**
 * LocalSystemServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.LocalSystem;

public class LocalSystemServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemService {

    public LocalSystemServiceLocator() {
    }


    public LocalSystemServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LocalSystemServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LocalSystemEP
    private java.lang.String LocalSystemEP_address = "http://localhost:8080/CableAssurance/caservices/LocalSystemEP";

    public java.lang.String getLocalSystemEPAddress() {
        return LocalSystemEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LocalSystemEPWSDDServiceName = "LocalSystemEP";

    public java.lang.String getLocalSystemEPWSDDServiceName() {
        return LocalSystemEPWSDDServiceName;
    }

    public void setLocalSystemEPWSDDServiceName(java.lang.String name) {
        LocalSystemEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemEP getLocalSystemEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LocalSystemEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLocalSystemEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemEP getLocalSystemEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub(portAddress, this);
            _stub.setPortName(getLocalSystemEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLocalSystemEPEndpointAddress(java.lang.String address) {
        LocalSystemEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub(new java.net.URL(LocalSystemEP_address), this);
                _stub.setPortName(getLocalSystemEPWSDDServiceName());
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
        if ("LocalSystemEP".equals(inputPortName)) {
            return getLocalSystemEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/LocalSystem", "LocalSystemService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/LocalSystem", "LocalSystemEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LocalSystemEP".equals(portName)) {
            setLocalSystemEPEndpointAddress(address);
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
