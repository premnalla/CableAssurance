/**
 * LocalSystemSOAPBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public class LocalSystemSOAPBindingSkeleton implements com.nds.www.wsdl.CableAssurance.LocalSystemPort, org.apache.axis.wsdl.Skeleton {
    private com.nds.www.wsdl.CableAssurance.LocalSystemPort impl;
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
        };
        _oper = new org.apache.axis.description.OperationDesc("queryLocalSystem", _params, new javax.xml.namespace.QName("", "name"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "localSystemType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "queryLocalSystem"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("queryLocalSystem") == null) {
            _myOperations.put("queryLocalSystem", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("queryLocalSystem")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("queryChildAggregates", _params, new javax.xml.namespace.QName("", "name"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "ArrayOfAggregateType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "queryChildAggregates"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("queryChildAggregates") == null) {
            _myOperations.put("queryChildAggregates", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("queryChildAggregates")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("queryChildCmts", _params, new javax.xml.namespace.QName("", "name"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "ArrayOfCmtsType"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "queryChildCmts"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("queryChildCmts") == null) {
            _myOperations.put("queryChildCmts", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("queryChildCmts")).add(_oper);
    }

    public LocalSystemSOAPBindingSkeleton() {
        this.impl = new com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingImpl();
    }

    public LocalSystemSOAPBindingSkeleton(com.nds.www.wsdl.CableAssurance.LocalSystemPort impl) {
        this.impl = impl;
    }
    public com.nds.www.wsdl.CableAssurance.LocalSystemType queryLocalSystem() throws java.rmi.RemoteException
    {
        com.nds.www.wsdl.CableAssurance.LocalSystemType ret = impl.queryLocalSystem();
        return ret;
    }

    public com.nds.www.wsdl.CableAssurance.AggregateType[] queryChildAggregates() throws java.rmi.RemoteException
    {
        com.nds.www.wsdl.CableAssurance.AggregateType[] ret = impl.queryChildAggregates();
        return ret;
    }

    public com.nds.www.wsdl.CableAssurance.CmtsType[] queryChildCmts() throws java.rmi.RemoteException
    {
        com.nds.www.wsdl.CableAssurance.CmtsType[] ret = impl.queryChildCmts();
        return ret;
    }

}
