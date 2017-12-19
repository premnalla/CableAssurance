/**
 * PriceList_BindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ecerami.www.wsdl.PriceListService_wsdl;

public class PriceList_BindingSkeleton implements com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_PortType, org.apache.axis.wsdl.Skeleton {
    private com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_PortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sku_list"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.ecerami.com/schema", "ArrayOfString"), java.lang.String[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getPriceList", _params, new javax.xml.namespace.QName("", "price_list"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.ecerami.com/schema", "ArrayOfDouble"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:examples:pricelistservice", "getPriceList"));
        _oper.setSoapAction("urn:examples:pricelistservice");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPriceList") == null) {
            _myOperations.put("getPriceList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPriceList")).add(_oper);
    }

    public PriceList_BindingSkeleton() {
        this.impl = new com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_BindingImpl();
    }

    public PriceList_BindingSkeleton(com.ecerami.www.wsdl.PriceListService_wsdl.PriceList_PortType impl) {
        this.impl = impl;
    }
    public double[] getPriceList(java.lang.String[] sku_list) throws java.rmi.RemoteException
    {
        double[] ret = impl.getPriceList(sku_list);
        return ret;
    }

}
