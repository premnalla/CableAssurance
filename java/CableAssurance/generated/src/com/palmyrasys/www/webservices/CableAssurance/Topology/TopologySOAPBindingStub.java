/**
 * TopologySOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Topology;

public class TopologySOAPBindingStub extends org.apache.axis.client.Stub implements com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyEP {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[28];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getResource");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.ResourceT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getRegions");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfRegionsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.RegionT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMarkets");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMarketsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MarketT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getBlades");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfBladesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.BladeT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getBlade");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "regionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "marketId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "bladeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "BladeT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.BladeT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getBladeByName");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "regionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "marketId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "bladeName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "BladeT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.BladeT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmses");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmsesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmsT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCms");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmtses");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmtsesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmtsT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmts");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmtsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmtsByName");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CmtsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmtsSnmpV2CAttributes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpV2CAttributesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmSnmpV2CAttributes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpV2CAttributesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMtaSnmpV2CAttributes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpV2CAttributesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCmtsAllSnmpV2CAttributes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfSnmpV2CAttributesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChannels");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfChannelsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.ChannelT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChannel");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "channelResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ChannelT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.ChannelT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfcs");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmtsResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfHfcsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HfcT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfc");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "hfcResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HfcT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCableModem");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CableModemT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CableModemT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChannelCmes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "channelResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCableModemsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CableModemT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfcCmes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "hfcResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCableModemsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CableModemT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getEmta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.EmtaT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getEmtaSecondary");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emtaResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaSecondaryT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChannelEmtas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "channelResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfEmtasT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.EmtaT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHfcEmtas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "hfcResId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfEmtasT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.EmtaT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDevicesForCsrPortal");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cmMac"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mtaMac"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MappedEuDevicesT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCustomerForResource");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CACustomerT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CACustomerT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[27] = oper;

    }

    public TopologySOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public TopologySOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public TopologySOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfBladesT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.BladeT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "BladeT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCableModemsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CableModemT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CableModemT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfChannelsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ChannelT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ChannelT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmsesT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmsT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmsT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCmtsesT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmtsT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfEmtasT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.EmtaT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfHfcsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HfcT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfMarketsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MarketT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MarketT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfRegionsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.RegionT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "RegionT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfSnmpV2CAttributesT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpV2CAttributesT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "BladeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.BladeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CableModemT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CableModemT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CACustomerT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CACustomerT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ChannelT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ChannelT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ChannelTypeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ChannelTypeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmsT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CmtsT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaSecondaryT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.EmtaT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.GenericCountsT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HfcT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MappedEuDevicesT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MarketT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.MarketT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "RegionT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.RegionT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ResourceT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpV2CAttributesT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpVersionT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.StatusColorT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

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

    public com.palmyrasys.www.webservices.CableAssurance.ResourceT getResource(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resourceId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getResource"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resourceId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.ResourceT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.ResourceT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.ResourceT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.RegionT[] getRegions() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getRegions"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.RegionT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.RegionT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.RegionT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MarketT[] getMarkets() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getMarkets"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MarketT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MarketT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MarketT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.BladeT[] getBlades() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getBlades"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.BladeT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.BladeT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.BladeT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.BladeT getBlade(java.math.BigInteger regionId, java.math.BigInteger marketId, java.math.BigInteger bladeId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getBlade"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {regionId, marketId, bladeId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.BladeT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.BladeT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.BladeT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.BladeT getBladeByName(java.math.BigInteger regionId, java.math.BigInteger marketId, java.lang.String bladeName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getBladeByName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {regionId, marketId, bladeName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.BladeT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.BladeT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.BladeT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmsT[] getCmses() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmses"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmsT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmsT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmsT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmsT getCms(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCms"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmtsT[] getCmtses() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmtses"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmtsT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmtsT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmtsT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmtsT getCmts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmts"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmtsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmtsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmtsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmtsT getCmtsByName(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.lang.String cmtsName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmtsByName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CmtsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CmtsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CmtsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT getCmtsSnmpV2CAttributes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmtsSnmpV2CAttributes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT getCmSnmpV2CAttributes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmSnmpV2CAttributes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT getMtaSnmpV2CAttributes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getMtaSnmpV2CAttributes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[] getCmtsAllSnmpV2CAttributes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCmtsAllSnmpV2CAttributes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.ChannelT[] getChannels(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getChannels"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.ChannelT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.ChannelT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.ChannelT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.ChannelT getChannel(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getChannel"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, channelResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.ChannelT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.ChannelT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.ChannelT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HfcT[] getHfcs(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getHfcs"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmtsResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HfcT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HfcT getHfc(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getHfc"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, hfcResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HfcT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HfcT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT getCableModem(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCableModem"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, cmResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CableModemT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CableModemT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CableModemT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT[] getChannelCmes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getChannelCmes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, channelResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CableModemT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CableModemT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CableModemT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT[] getHfcCmes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getHfcCmes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, hfcResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CableModemT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CableModemT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CableModemT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT getEmta(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger emtaResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getEmta"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, emtaResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.EmtaT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT getEmtaSecondary(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger emtaResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getEmtaSecondary"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, emtaResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT[] getChannelEmtas(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getChannelEmtas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, channelResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.EmtaT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT[] getHfcEmtas(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getHfcEmtas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, hfcResId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.EmtaT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.EmtaT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT getDevicesForCsrPortal(java.lang.String cmMac, java.lang.String mtaMac) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getDevicesForCsrPortal"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cmMac, mtaMac});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CACustomerT getCustomerForResource(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger resourceId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Topology", "getCustomerForResource"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, resourceId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CACustomerT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CACustomerT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CACustomerT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
