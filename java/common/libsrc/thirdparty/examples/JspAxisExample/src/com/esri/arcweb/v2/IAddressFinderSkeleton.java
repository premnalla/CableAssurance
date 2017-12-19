/**
 * IAddressFinderSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.esri.arcweb.v2;

public class IAddressFinderSkeleton implements com.esri.arcweb.v2.IAddressFinder, org.apache.axis.wsdl.Skeleton {
    private com.esri.arcweb.v2.IAddressFinder impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "point"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "Point"), com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "addressFinderOptions"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.glue.v2.addressfinder/", "AddressFinderOptions"), com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.AddressFinderOptions.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getAddress", _params, new javax.xml.namespace.QName("", "Result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.glue.v2.addressfinder/", "Address"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://arcweb.esri.com/v2", "getAddress"));
        _oper.setSoapAction("getAddress");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAddress") == null) {
            _myOperations.put("getAddress", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAddress")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "address"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.glue.v2.addressfinder/", "Address"), com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.Address.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "addressFinderOptions"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.glue.v2.addressfinder/", "AddressFinderOptions"), com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.AddressFinderOptions.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("findAddress", _params, new javax.xml.namespace.QName("", "Result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2/", "LocationInfo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://arcweb.esri.com/v2", "findAddress"));
        _oper.setSoapAction("findAddress");
        _myOperationsList.add(_oper);
        if (_myOperations.get("findAddress") == null) {
            _myOperations.put("findAddress", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("findAddress")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "datasource"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCountries", _params, new javax.xml.namespace.QName("", "Result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/java.lang/", "ArrayOfstring"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://arcweb.esri.com/v2", "getCountries"));
        _oper.setSoapAction("getCountries");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCountries") == null) {
            _myOperations.put("getCountries", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCountries")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getVersion", _params, new javax.xml.namespace.QName("", "Result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://arcweb.esri.com/v2", "getVersion"));
        _oper.setSoapAction("getVersion");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVersion") == null) {
            _myOperations.put("getVersion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVersion")).add(_oper);
    }

    public IAddressFinderSkeleton() {
        this.impl = new com.esri.arcweb.v2.IAddressFinderImpl();
    }

    public IAddressFinderSkeleton(com.esri.arcweb.v2.IAddressFinder impl) {
        this.impl = impl;
    }
    public com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.Address getAddress(com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point point, com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.AddressFinderOptions addressFinderOptions, java.lang.String token) throws java.rmi.RemoteException
    {
        com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.Address ret = impl.getAddress(point, addressFinderOptions, token);
        return ret;
    }

    public com.themindelectric.www._package.com_esri_is_services_common_v2.LocationInfo findAddress(com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.Address address, com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.AddressFinderOptions addressFinderOptions, java.lang.String token) throws java.rmi.RemoteException
    {
        com.themindelectric.www._package.com_esri_is_services_common_v2.LocationInfo ret = impl.findAddress(address, addressFinderOptions, token);
        return ret;
    }

    public java.lang.String[] getCountries(java.lang.String datasource, java.lang.String token) throws java.rmi.RemoteException
    {
        java.lang.String[] ret = impl.getCountries(datasource, token);
        return ret;
    }

    public java.lang.String getVersion() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getVersion();
        return ret;
    }

}
