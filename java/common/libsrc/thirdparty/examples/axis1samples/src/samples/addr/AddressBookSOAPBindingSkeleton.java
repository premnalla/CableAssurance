/**
 * AddressBookSOAPBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.addr;

public class AddressBookSOAPBindingSkeleton implements samples.addr.AddressBook, org.apache.axis.wsdl.Skeleton {
    private samples.addr.AddressBook impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "name"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/1999/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "address"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:AddressFetcher2", "address"), samples.addr.Address.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("addEntry", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("urn:AddressFetcher2", "addEntry"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("addEntry") == null) {
            _myOperations.put("addEntry", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("addEntry")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "name"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/1999/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getAddressFromName", _params, new javax.xml.namespace.QName("", "address"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:AddressFetcher2", "address"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:AddressFetcher2", "getAddressFromName"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAddressFromName") == null) {
            _myOperations.put("getAddressFromName", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAddressFromName")).add(_oper);
    }

    public AddressBookSOAPBindingSkeleton() {
        this.impl = new samples.addr.AddressBookSOAPBindingImpl();
    }

    public AddressBookSOAPBindingSkeleton(samples.addr.AddressBook impl) {
        this.impl = impl;
    }
    public void addEntry(java.lang.String name, samples.addr.Address address) throws java.rmi.RemoteException
    {
        impl.addEntry(name, address);
    }

    public samples.addr.Address getAddressFromName(java.lang.String name) throws java.rmi.RemoteException
    {
        samples.addr.Address ret = impl.getAddressFromName(name);
        return ret;
    }

}
