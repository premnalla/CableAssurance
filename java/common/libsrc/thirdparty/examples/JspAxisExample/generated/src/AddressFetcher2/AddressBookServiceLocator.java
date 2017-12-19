/**
 * AddressBookServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package AddressFetcher2;

public class AddressBookServiceLocator extends org.apache.axis.client.Service implements AddressFetcher2.AddressBookService {

    public AddressBookServiceLocator() {
    }


    public AddressBookServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AddressBookServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AddressBook
    private java.lang.String AddressBook_address = "http://localhost:8080/axis/services/AddressBook";

    public java.lang.String getAddressBookAddress() {
        return AddressBook_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AddressBookWSDDServiceName = "AddressBook";

    public java.lang.String getAddressBookWSDDServiceName() {
        return AddressBookWSDDServiceName;
    }

    public void setAddressBookWSDDServiceName(java.lang.String name) {
        AddressBookWSDDServiceName = name;
    }

    public AddressFetcher2.AddressBook getAddressBook() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AddressBook_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAddressBook(endpoint);
    }

    public AddressFetcher2.AddressBook getAddressBook(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AddressFetcher2.AddressBookSOAPBindingStub _stub = new AddressFetcher2.AddressBookSOAPBindingStub(portAddress, this);
            _stub.setPortName(getAddressBookWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAddressBookEndpointAddress(java.lang.String address) {
        AddressBook_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (AddressFetcher2.AddressBook.class.isAssignableFrom(serviceEndpointInterface)) {
                AddressFetcher2.AddressBookSOAPBindingStub _stub = new AddressFetcher2.AddressBookSOAPBindingStub(new java.net.URL(AddressBook_address), this);
                _stub.setPortName(getAddressBookWSDDServiceName());
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
        if ("AddressBook".equals(inputPortName)) {
            return getAddressBook();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:AddressFetcher2", "AddressBookService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:AddressFetcher2", "AddressBook"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AddressBook".equals(portName)) {
            setAddressBookEndpointAddress(address);
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
