/**
 * AddressFinderLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.esri.arcweb.v2;

public class AddressFinderLocator extends org.apache.axis.client.Service implements com.esri.arcweb.v2.AddressFinder {

/**
 * The Address Finder Web Service enables users to input a street
 * address and receive a candidate list of matching addresses and associated
 * coordinates. It also enables users to input x,y-coordinates and receive
 * a street address. ArcWeb Service is intended to support application
 * developers who would like to provide "find an address" and "get an
 * address" functionality within their Internet applications.
 */

    public AddressFinderLocator() {
    }


    public AddressFinderLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AddressFinderLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IAddressFinder
    private java.lang.String IAddressFinder_address = "http://arcweb.esri.com/services/v2/AddressFinder";

    public java.lang.String getIAddressFinderAddress() {
        return IAddressFinder_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IAddressFinderWSDDServiceName = "IAddressFinder";

    public java.lang.String getIAddressFinderWSDDServiceName() {
        return IAddressFinderWSDDServiceName;
    }

    public void setIAddressFinderWSDDServiceName(java.lang.String name) {
        IAddressFinderWSDDServiceName = name;
    }

    public com.esri.arcweb.v2.IAddressFinder getIAddressFinder() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IAddressFinder_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIAddressFinder(endpoint);
    }

    public com.esri.arcweb.v2.IAddressFinder getIAddressFinder(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.esri.arcweb.v2.IAddressFinderStub _stub = new com.esri.arcweb.v2.IAddressFinderStub(portAddress, this);
            _stub.setPortName(getIAddressFinderWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIAddressFinderEndpointAddress(java.lang.String address) {
        IAddressFinder_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.esri.arcweb.v2.IAddressFinder.class.isAssignableFrom(serviceEndpointInterface)) {
                com.esri.arcweb.v2.IAddressFinderStub _stub = new com.esri.arcweb.v2.IAddressFinderStub(new java.net.URL(IAddressFinder_address), this);
                _stub.setPortName(getIAddressFinderWSDDServiceName());
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
        if ("IAddressFinder".equals(inputPortName)) {
            return getIAddressFinder();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://arcweb.esri.com/v2", "AddressFinder");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://arcweb.esri.com/v2", "IAddressFinder"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IAddressFinder".equals(portName)) {
            setIAddressFinderEndpointAddress(address);
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
