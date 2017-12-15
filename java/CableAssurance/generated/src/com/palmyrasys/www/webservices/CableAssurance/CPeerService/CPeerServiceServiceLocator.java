/**
 * CPeerServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.CPeerService;

public class CPeerServiceServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceService {

    public CPeerServiceServiceLocator() {
    }


    public CPeerServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CPeerServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CPeerServiceEP
    private java.lang.String CPeerServiceEP_address = "http://localhost:9091/CableAssurance/caservices/CPeerServiceEP";

    public java.lang.String getCPeerServiceEPAddress() {
        return CPeerServiceEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CPeerServiceEPWSDDServiceName = "CPeerServiceEP";

    public java.lang.String getCPeerServiceEPWSDDServiceName() {
        return CPeerServiceEPWSDDServiceName;
    }

    public void setCPeerServiceEPWSDDServiceName(java.lang.String name) {
        CPeerServiceEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceEP getCPeerServiceEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CPeerServiceEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCPeerServiceEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceEP getCPeerServiceEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub(portAddress, this);
            _stub.setPortName(getCPeerServiceEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCPeerServiceEPEndpointAddress(java.lang.String address) {
        CPeerServiceEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub(new java.net.URL(CPeerServiceEP_address), this);
                _stub.setPortName(getCPeerServiceEPWSDDServiceName());
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
        if ("CPeerServiceEP".equals(inputPortName)) {
            return getCPeerServiceEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/CPeerService", "CPeerServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/CPeerService", "CPeerServiceEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CPeerServiceEP".equals(portName)) {
            setCPeerServiceEPEndpointAddress(address);
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
