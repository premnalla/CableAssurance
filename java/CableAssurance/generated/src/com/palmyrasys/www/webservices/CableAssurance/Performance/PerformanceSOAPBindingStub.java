/**
 * PerformanceSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Performance;

public class PerformanceSOAPBindingStub extends org.apache.axis.client.Stub implements com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceEP {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[16];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentCmtsCounts");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmtsCountsHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfGenericCountsHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentChannelCounts");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "channelResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChannelCountsHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "channelResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfGenericCountsHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentHfcCounts");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "hfcResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfcCountsHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "hfcResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfGenericCountsHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentCmStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmStatusT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmStatusHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmStatusHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentCmPerformance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmCurrentPerformanceT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmPerformanceHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmPerformanceHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentMtaAvailability");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMtaAvailabilityHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaAvailabilityHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentMtaProvisionedStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaProvStatusT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMtaProvisionedStatusHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaProvStatusHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentMtaPingStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaPingStatusT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMtaPingStatusHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaPingStatusHistoryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

    }

    public PerformanceSOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public PerformanceSOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public PerformanceSOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.1");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmPerformanceHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceHistoryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmStatusHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusHistoryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfGenericCountsHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsHistoryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaAvailabilityHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityHistoryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaPingStatusHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaPingStatusHistoryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaProvStatusHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaProvStatusHistoryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfScrollPageT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ScrollPageT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ScrollPageT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmCurrentPerformanceT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmStatusT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaPingStatusHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaPingStatusT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaProvStatusHistoryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaProvStatusT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ScrollPageT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ScrollPageT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCurrentCmtsCounts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentCmtsCounts"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] getCmtsCountsHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCmtsCountsHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCurrentChannelCounts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentChannelCounts"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, channelResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] getChannelCountsHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getChannelCountsHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, channelResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCurrentHfcCounts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentHfcCounts"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, hfcResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] getHfcCountsHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getHfcCountsHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, hfcResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusT getCurrentCmStatus(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentCmStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmStatusT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[] getCmStatusHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCmStatusHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT getCurrentCmPerformance(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentCmPerformance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[] getCmPerformanceHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCmPerformanceHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT getCurrentMtaAvailability(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentMtaAvailability"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, mtaResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[] getMtaAvailabilityHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getMtaAvailabilityHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, mtaResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT getCurrentMtaProvisionedStatus(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentMtaProvisionedStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, mtaResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[] getMtaProvisionedStatusHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getMtaProvisionedStatusHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, mtaResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT getCurrentMtaPingStatus(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getCurrentMtaPingStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, mtaResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[] getMtaPingStatusHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Performance", "getMtaPingStatusHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, mtaResId, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
