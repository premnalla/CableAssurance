/**
 * GetQuoteBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.stock;

public class GetQuoteBindingSkeleton implements samples.stock.GetQuote, org.apache.axis.wsdl.Skeleton {
    private samples.stock.GetQuote impl;
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
        _oper = new org.apache.axis.description.OperationDesc("getQuote", _params, new javax.xml.namespace.QName("", "result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:GetQuote2", "getQuote"));
        _oper.setSoapAction("getQuote");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getQuote") == null) {
            _myOperations.put("getQuote", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getQuote")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("test", _params, new javax.xml.namespace.QName("", "testResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:GetQuote2", "test"));
        _oper.setSoapAction("test");
        _myOperationsList.add(_oper);
        if (_myOperations.get("test") == null) {
            _myOperations.put("test", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("test")).add(_oper);
    }

    public GetQuoteBindingSkeleton() {
        this.impl = new samples.stock.GetQuoteBindingImpl();
    }

    public GetQuoteBindingSkeleton(samples.stock.GetQuote impl) {
        this.impl = impl;
    }
    public float getQuote(java.lang.String symbol) throws java.rmi.RemoteException
    {
        float ret = impl.getQuote(symbol);
        return ret;
    }

    public java.lang.String test() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.test();
        return ret;
    }

}
