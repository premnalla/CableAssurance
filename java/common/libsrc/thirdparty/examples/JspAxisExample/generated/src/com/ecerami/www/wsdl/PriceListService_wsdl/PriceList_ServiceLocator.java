/**
 * PriceList_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ecerami.www.wsdl.PriceListService_wsdl;

public class PriceList_ServiceLocator extends org.apache.axis.client.Service implements com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_Service {

    public PriceList_ServiceLocator() {
    }


    public PriceList_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PriceList_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PriceList_Port
    private java.lang.String PriceList_Port_address = "http://localhost:8080/soap/servlet/rpcrouter";

    public java.lang.String getPriceList_PortAddress() {
        return PriceList_Port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PriceList_PortWSDDServiceName = "PriceList_Port";

    public java.lang.String getPriceList_PortWSDDServiceName() {
        return PriceList_PortWSDDServiceName;
    }

    public void setPriceList_PortWSDDServiceName(java.lang.String name) {
        PriceList_PortWSDDServiceName = name;
    }

    public com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_PortType getPriceList_Port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PriceList_Port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPriceList_Port(endpoint);
    }

    public com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_PortType getPriceList_Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_BindingStub _stub = new com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_BindingStub(portAddress, this);
            _stub.setPortName(getPriceList_PortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPriceList_PortEndpointAddress(java.lang.String address) {
        PriceList_Port_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_BindingStub _stub = new com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_BindingStub(new java.net.URL(PriceList_Port_address), this);
                _stub.setPortName(getPriceList_PortWSDDServiceName());
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
        if ("PriceList_Port".equals(inputPortName)) {
            return getPriceList_Port();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.ecerami.com/wsdl/PriceListService.wsdl", "PriceList_Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.ecerami.com/wsdl/PriceListService.wsdl", "PriceList_Port"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PriceList_Port".equals(portName)) {
            setPriceList_PortEndpointAddress(address);
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
