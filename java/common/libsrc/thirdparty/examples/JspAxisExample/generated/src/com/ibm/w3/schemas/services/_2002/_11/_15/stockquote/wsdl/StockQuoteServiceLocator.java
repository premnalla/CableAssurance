/**
 * StockQuoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl;

public class StockQuoteServiceLocator extends org.apache.axis.client.Service implements com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuoteService {

    public StockQuoteServiceLocator() {
    }


    public StockQuoteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StockQuoteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StockQuoteSOAPPort
    private java.lang.String StockQuoteSOAPPort_address = "http://localhost:8080/axis2/services/StockQuoteService";

    public java.lang.String getStockQuoteSOAPPortAddress() {
        return StockQuoteSOAPPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StockQuoteSOAPPortWSDDServiceName = "StockQuoteSOAPPort";

    public java.lang.String getStockQuoteSOAPPortWSDDServiceName() {
        return StockQuoteSOAPPortWSDDServiceName;
    }

    public void setStockQuoteSOAPPortWSDDServiceName(java.lang.String name) {
        StockQuoteSOAPPortWSDDServiceName = name;
    }

    public com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType getStockQuoteSOAPPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StockQuoteSOAPPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStockQuoteSOAPPort(endpoint);
    }

    public com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType getStockQuoteSOAPPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuoteSOAPBindingStub _stub = new com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuoteSOAPBindingStub(portAddress, this);
            _stub.setPortName(getStockQuoteSOAPPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStockQuoteSOAPPortEndpointAddress(java.lang.String address) {
        StockQuoteSOAPPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuoteSOAPBindingStub _stub = new com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuoteSOAPBindingStub(new java.net.URL(StockQuoteSOAPPort_address), this);
                _stub.setPortName(getStockQuoteSOAPPortWSDDServiceName());
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
        if ("StockQuoteSOAPPort".equals(inputPortName)) {
            return getStockQuoteSOAPPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote/wsdl", "StockQuoteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote/wsdl", "StockQuoteSOAPPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StockQuoteSOAPPort".equals(portName)) {
            setStockQuoteSOAPPortEndpointAddress(address);
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
