/**
 * TopologyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.Topology;

public class TopologyServiceLocator extends org.apache.axis.client.Service implements com.nds.www.wsdl.CableAssurance.Topology.TopologyService {

    public TopologyServiceLocator() {
    }


    public TopologyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TopologyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TopologyEP
    private java.lang.String TopologyEP_address = "http://localhost:8080/CableAssurance/caservices/TopologyEP";

    public java.lang.String getTopologyEPAddress() {
        return TopologyEP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TopologyEPWSDDServiceName = "TopologyEP";

    public java.lang.String getTopologyEPWSDDServiceName() {
        return TopologyEPWSDDServiceName;
    }

    public void setTopologyEPWSDDServiceName(java.lang.String name) {
        TopologyEPWSDDServiceName = name;
    }

    public com.nds.www.wsdl.CableAssurance.Topology.TopologyEP getTopologyEP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TopologyEP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTopologyEP(endpoint);
    }

    public com.nds.www.wsdl.CableAssurance.Topology.TopologyEP getTopologyEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub _stub = new com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub(portAddress, this);
            _stub.setPortName(getTopologyEPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTopologyEPEndpointAddress(java.lang.String address) {
        TopologyEP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.nds.www.wsdl.CableAssurance.Topology.TopologyEP.class.isAssignableFrom(serviceEndpointInterface)) {
                com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub _stub = new com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub(new java.net.URL(TopologyEP_address), this);
                _stub.setPortName(getTopologyEPWSDDServiceName());
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
        if ("TopologyEP".equals(inputPortName)) {
            return getTopologyEP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance/Topology", "TopologyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance/Topology", "TopologyEP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TopologyEP".equals(portName)) {
            setTopologyEPEndpointAddress(address);
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
