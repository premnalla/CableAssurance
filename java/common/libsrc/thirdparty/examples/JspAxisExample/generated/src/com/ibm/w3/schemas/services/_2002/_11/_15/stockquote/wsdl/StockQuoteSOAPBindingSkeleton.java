/**
 * StockQuoteSOAPBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl;

public class StockQuoteSOAPBindingSkeleton implements com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType, org.apache.axis.wsdl.Skeleton {
    private com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "symbol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getStockQuote", _params, new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", "quote"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", ">quote"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", "getStockQuote"));
        _oper.setSoapAction("getStockQuote");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getStockQuote") == null) {
            _myOperations.put("getStockQuote", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getStockQuote")).add(_oper);
    }

    public StockQuoteSOAPBindingSkeleton() {
        this.impl = new com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuoteSOAPBindingImpl();
    }

    public StockQuoteSOAPBindingSkeleton(com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType impl) {
        this.impl = impl;
    }
    public com.ibm.w3.schemas.services._2002._11._15.stockquote.Quote getStockQuote(java.lang.String symbol) throws java.rmi.RemoteException
    {
        com.ibm.w3.schemas.services._2002._11._15.stockquote.Quote ret = impl.getStockQuote(symbol);
        return ret;
    }

}
