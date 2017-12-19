/**
 * GetQuoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.stock;

public class GetQuoteServiceLocator extends org.apache.axis.client.Service implements samples.stock.GetQuoteService {

    public GetQuoteServiceLocator() {
    }


    public GetQuoteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GetQuoteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GetQuote
    private java.lang.String GetQuote_address = "http://localhost:8080/axis/services/GetQuote";

    public java.lang.String getGetQuoteAddress() {
        return GetQuote_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GetQuoteWSDDServiceName = "GetQuote";

    public java.lang.String getGetQuoteWSDDServiceName() {
        return GetQuoteWSDDServiceName;
    }

    public void setGetQuoteWSDDServiceName(java.lang.String name) {
        GetQuoteWSDDServiceName = name;
    }

    public samples.stock.GetQuote getGetQuote() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetQuote_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetQuote(endpoint);
    }

    public samples.stock.GetQuote getGetQuote(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            samples.stock.GetQuoteBindingStub _stub = new samples.stock.GetQuoteBindingStub(portAddress, this);
            _stub.setPortName(getGetQuoteWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetQuoteEndpointAddress(java.lang.String address) {
        GetQuote_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (samples.stock.GetQuote.class.isAssignableFrom(serviceEndpointInterface)) {
                samples.stock.GetQuoteBindingStub _stub = new samples.stock.GetQuoteBindingStub(new java.net.URL(GetQuote_address), this);
                _stub.setPortName(getGetQuoteWSDDServiceName());
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
        if ("GetQuote".equals(inputPortName)) {
            return getGetQuote();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:GetQuote2", "GetQuoteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:GetQuote2", "GetQuote"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GetQuote".equals(portName)) {
            setGetQuoteEndpointAddress(address);
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
