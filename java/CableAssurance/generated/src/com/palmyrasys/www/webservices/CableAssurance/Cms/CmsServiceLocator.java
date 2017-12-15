/**
 * CmsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Cms;

public class CmsServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.Cms.CmsService {

    public CmsServiceLocator() {
    }


    public CmsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CmsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CmsEP
    private java.lang.String CmsEP_address = "http://localhost:8080/CableAssurance/caservices/CmsEP";

    public java.lang.String getCmsEPAddress() {
        return CmsEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CmsEPWSDDServiceName = "CmsEP";

    public java.lang.String getCmsEPWSDDServiceName() {
        return CmsEPWSDDServiceName;
    }

    public void setCmsEPWSDDServiceName(java.lang.String name) {
        CmsEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.Cms.CmsEP getCmsEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CmsEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCmsEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.Cms.CmsEP getCmsEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub(portAddress, this);
            _stub.setPortName(getCmsEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCmsEPEndpointAddress(java.lang.String address) {
        CmsEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.Cms.CmsEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub(new java.net.URL(CmsEP_address), this);
                _stub.setPortName(getCmsEPWSDDServiceName());
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
        if ("CmsEP".equals(inputPortName)) {
            return getCmsEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Cms", "CmsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Cms", "CmsEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CmsEP".equals(portName)) {
            setCmsEPEndpointAddress(address);
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
