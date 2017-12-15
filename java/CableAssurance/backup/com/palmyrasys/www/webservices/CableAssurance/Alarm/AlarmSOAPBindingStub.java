/**
 * AlarmSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

public class AlarmSOAPBindingStub extends org.apache.axis.client.Stub implements com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmEP {
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
        oper.setName("getCurrentAlarms");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCurrentAlarmsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentAlarm");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentAlarmDetails");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmDetailsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentAlarmsByType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmSubType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCurrentAlarmsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHistoricalAlarms");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfHistoricalAlarmsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHistoricalAlarm");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHistoricalAlarmDetails");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "topologyKey"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"), com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmDetailsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHistoricalAlarmsByType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "alarmSubType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fromTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"), com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "batch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"), com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfHistoricalAlarmsT"));
        oper.setReturnClass(com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

    }

    public AlarmSOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public AlarmSOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public AlarmSOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AbstractAlarmT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfCurrentAlarmsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ArrayOfHistoricalAlarmsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmT");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmDetailsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmDetailsT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.InputTimeT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT");
            cachedSerQNames.add(qName);
            cls = com.palmyrasys.www.webservices.CableAssurance.ResultBatchT.class;
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

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[] getCurrentAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getCurrentAlarms"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {fromTime, toTime, batch});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT getCurrentAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getCurrentAlarm"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, alarmId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT getCurrentAlarmDetails(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getCurrentAlarmDetails"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, alarmId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmDetailsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[] getCurrentAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getCurrentAlarmsByType"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {alarmType, alarmSubType, fromTime, toTime, batch});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getHistoricalAlarms(com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getHistoricalAlarms"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {fromTime, toTime, batch});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT getHistoricalAlarm(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getHistoricalAlarm"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, alarmId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT getHistoricalAlarmDetails(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger alarmId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getHistoricalAlarmDetails"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {topologyKey, alarmId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmDetailsT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getHistoricalAlarmsByType(java.lang.String alarmType, java.lang.String alarmSubType, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance/Alarm", "getHistoricalAlarmsByType"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {alarmType, alarmSubType, fromTime, toTime, batch});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
