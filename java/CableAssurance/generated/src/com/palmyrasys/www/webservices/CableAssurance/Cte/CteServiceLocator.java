/**
 * CteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Cte;

public class CteServiceLocator extends org.apache.axis.client.Service implements com.palmyrasys.www.webservices.CableAssurance.Cte.CteService {

    public CteServiceLocator() {
    }


    public CteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CteEP
    private java.lang.String CteEP_address = "http://localhost:8080/CableAssurance/caservices/CteEP";

    public java.lang.String getCteEPAddress() {
        return CteEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CteEPWSDDServiceName = "CteEP";

    public java.lang.String getCteEPWSDDServiceName() {
        return CteEPWSDDServiceName;
    }

    public void setCteEPWSDDServiceName(java.lang.String name) {
        CteEPWSDDServiceName = name;
    }

    public com.palmyrasys.www.webservices.CableAssurance.Cte.CteEP getCteEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CteEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCteEP(endpoint);
    }

    public com.palmyrasys.www.webservices.CableAssurance.Cte.CteEP getCteEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub(portAddress, this);
            _stub.setPortName(getCteEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCteEPEndpointAddress(java.lang.String address) {
        CteEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.palmyrasys.www.webservices.CableAssurance.Cte.CteEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub _stub = new com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub(new java.net.URL(CteEP_address), this);
                _stub.setPortName(getCteEPWSDDServiceName());
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
        if ("CteEP".equals(inputPortName)) {
            return getCteEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Cte", "CteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Cte", "CteEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CteEP".equals(portName)) {
            setCteEPEndpointAddress(address);
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
