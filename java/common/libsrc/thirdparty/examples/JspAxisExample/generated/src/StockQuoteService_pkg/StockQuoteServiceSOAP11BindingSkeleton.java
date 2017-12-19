/**
 * StockQuoteServiceSOAP11BindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package StockQuoteService_pkg;

public class StockQuoteServiceSOAP11BindingSkeleton implements StockQuoteService_pkg.StockQuoteServicePortType, org.apache.axis.wsdl.Skeleton {
    private StockQuoteService_pkg.StockQuoteServicePortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://StockQuoteService/xsd", "symbol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getPrice", _params, new javax.xml.namespace.QName("http://StockQuoteService/xsd", "return"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://StockQuoteService/xsd", "getPrice"));
        _oper.setSoapAction("urn:getPrice");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPrice") == null) {
            _myOperations.put("getPrice", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPrice")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://StockQuoteService/xsd", "symbol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://StockQuoteService/xsd", "price"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), double.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("update", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://StockQuoteService/xsd", "update"));
        _oper.setSoapAction("urn:update");
        _myOperationsList.add(_oper);
        if (_myOperations.get("update") == null) {
            _myOperations.put("update", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("update")).add(_oper);
    }

    public StockQuoteServiceSOAP11BindingSkeleton() {
        this.impl = new StockQuoteService_pkg.StockQuoteServiceSOAP11BindingImpl();
    }

    public StockQuoteServiceSOAP11BindingSkeleton(StockQuoteService_pkg.StockQuoteServicePortType impl) {
        this.impl = impl;
    }
    public double getPrice(java.lang.String symbol) throws java.rmi.RemoteException
    {
        double ret = impl.getPrice(symbol);
        return ret;
    }

    public void update(java.lang.String symbol, double price) throws java.rmi.RemoteException
    {
        impl.update(symbol, price);
    }

}
