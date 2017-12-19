/**
 * StockQuoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package StockQuoteService_pkg;

public class StockQuoteServiceLocator extends org.apache.axis.client.Service implements StockQuoteService_pkg.StockQuoteService {

    public StockQuoteServiceLocator() {
    }


    public StockQuoteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StockQuoteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StockQuoteServiceSOAP11port
    private java.lang.String StockQuoteServiceSOAP11port_address = "http://localhost:8080/axis2/services/StockQuoteService";

    public java.lang.String getStockQuoteServiceSOAP11portAddress() {
        return StockQuoteServiceSOAP11port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StockQuoteServiceSOAP11portWSDDServiceName = "StockQuoteServiceSOAP11port";

    public java.lang.String getStockQuoteServiceSOAP11portWSDDServiceName() {
        return StockQuoteServiceSOAP11portWSDDServiceName;
    }

    public void setStockQuoteServiceSOAP11portWSDDServiceName(java.lang.String name) {
        StockQuoteServiceSOAP11portWSDDServiceName = name;
    }

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP11port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StockQuoteServiceSOAP11port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStockQuoteServiceSOAP11port(endpoint);
    }

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP11port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            StockQuoteService_pkg.StockQuoteServiceSOAP11BindingStub _stub = new StockQuoteService_pkg.StockQuoteServiceSOAP11BindingStub(portAddress, this);
            _stub.setPortName(getStockQuoteServiceSOAP11portWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStockQuoteServiceSOAP11portEndpointAddress(java.lang.String address) {
        StockQuoteServiceSOAP11port_address = address;
    }


    // Use to get a proxy class for StockQuoteServiceSOAP12port
    private java.lang.String StockQuoteServiceSOAP12port_address = "http://localhost:8080/axis2/services/StockQuoteService";

    public java.lang.String getStockQuoteServiceSOAP12portAddress() {
        return StockQuoteServiceSOAP12port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StockQuoteServiceSOAP12portWSDDServiceName = "StockQuoteServiceSOAP12port";

    public java.lang.String getStockQuoteServiceSOAP12portWSDDServiceName() {
        return StockQuoteServiceSOAP12portWSDDServiceName;
    }

    public void setStockQuoteServiceSOAP12portWSDDServiceName(java.lang.String name) {
        StockQuoteServiceSOAP12portWSDDServiceName = name;
    }

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP12port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StockQuoteServiceSOAP12port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStockQuoteServiceSOAP12port(endpoint);
    }

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP12port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            StockQuoteService_pkg.StockQuoteServiceSOAP12BindingStub _stub = new StockQuoteService_pkg.StockQuoteServiceSOAP12BindingStub(portAddress, this);
            _stub.setPortName(getStockQuoteServiceSOAP12portWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStockQuoteServiceSOAP12portEndpointAddress(java.lang.String address) {
        StockQuoteServiceSOAP12port_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (StockQuoteService_pkg.StockQuoteServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                StockQuoteService_pkg.StockQuoteServiceSOAP11BindingStub _stub = new StockQuoteService_pkg.StockQuoteServiceSOAP11BindingStub(new java.net.URL(StockQuoteServiceSOAP11port_address), this);
                _stub.setPortName(getStockQuoteServiceSOAP11portWSDDServiceName());
                return _stub;
            }
            if (StockQuoteService_pkg.StockQuoteServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                StockQuoteService_pkg.StockQuoteServiceSOAP12BindingStub _stub = new StockQuoteService_pkg.StockQuoteServiceSOAP12BindingStub(new java.net.URL(StockQuoteServiceSOAP12port_address), this);
                _stub.setPortName(getStockQuoteServiceSOAP12portWSDDServiceName());
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
        if ("StockQuoteServiceSOAP11port".equals(inputPortName)) {
            return getStockQuoteServiceSOAP11port();
        }
        else if ("StockQuoteServiceSOAP12port".equals(inputPortName)) {
            return getStockQuoteServiceSOAP12port();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://StockQuoteService", "StockQuoteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://StockQuoteService", "StockQuoteServiceSOAP11port"));
            ports.add(new javax.xml.namespace.QName("http://StockQuoteService", "StockQuoteServiceSOAP12port"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StockQuoteServiceSOAP11port".equals(portName)) {
            setStockQuoteServiceSOAP11portEndpointAddress(address);
        }
        else 
if ("StockQuoteServiceSOAP12port".equals(portName)) {
            setStockQuoteServiceSOAP12portEndpointAddress(address);
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
