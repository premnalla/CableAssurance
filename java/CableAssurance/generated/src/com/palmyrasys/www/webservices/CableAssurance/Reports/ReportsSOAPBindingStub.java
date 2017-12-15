/**
 * ReportsSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Reports;

public class ReportsSOAPBindingStub extends org.apache.axis.client.Stub implements com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsEP {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[8];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfcStatusSummary1");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfcStatusSummary2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMtaStatusSummary1");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMtaStatusSummary2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmStatusSummary1");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmStatusSummary2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmTcaStatusSummary1");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmTcaStatusSummary2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT"), com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryRespT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "returnValue"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

    }

    public ReportsSOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ReportsSOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ReportsSOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmStatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfHfcStatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMtaStatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusSummaryT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfQueryStateT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.QueryStateT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT");
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

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryRespT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryRespT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusSummaryRespT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.QueryStateT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

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

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusSummaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT.class;
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

    public com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT getHfcStatusSummary1(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getHfcStatusSummary1"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT getHfcStatusSummary2(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getHfcStatusSummary2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT getMtaStatusSummary1(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getMtaStatusSummary1"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT getMtaStatusSummary2(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getMtaStatusSummary2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmStatusSummary1(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getCmStatusSummary1"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmStatusSummary2(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getCmStatusSummary2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmTcaStatusSummary1(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getCmTcaStatusSummary1"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmTcaStatusSummary2(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resId, com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch, com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Reports", "getCmTcaStatusSummary2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resId, resType, fromTime, toTime, batch, queryState});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
