/**
 * LocalSystemSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

import org.apache.axis.utils.Options;
import java.net.URL;

public class LocalSystemSOAPBindingStub extends org.apache.axis.client.Stub
		implements com.nds.www.wsdl.CableAssurance.LocalSystemPort {
	private java.util.Vector cachedSerClasses = new java.util.Vector();

	private java.util.Vector cachedSerQNames = new java.util.Vector();

	private java.util.Vector cachedSerFactories = new java.util.Vector();

	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[3];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("queryLocalSystem");
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "localSystemType"));
		oper
				.setReturnClass(com.nds.www.wsdl.CableAssurance.LocalSystemType.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("queryChildAggregates");
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance",
				"ArrayOfAggregateType"));
		oper
				.setReturnClass(com.nds.www.wsdl.CableAssurance.AggregateType[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("queryChildCmts");
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "ArrayOfCmtsType"));
		oper.setReturnClass(com.nds.www.wsdl.CableAssurance.CmtsType[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "name"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[2] = oper;

	}

	public LocalSystemSOAPBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public LocalSystemSOAPBindingStub(java.net.URL endpointURL,
			javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public LocalSystemSOAPBindingStub(javax.xml.rpc.Service service)
			throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service)
				.setTypeMappingVersion("1.1");
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
		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "aggregateType");
		cachedSerQNames.add(qName);
		cls = com.nds.www.wsdl.CableAssurance.AggregateType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance",
				"ArrayOfAggregateType");
		cachedSerQNames.add(qName);
		cls = com.nds.www.wsdl.CableAssurance.AggregateType[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "aggregateType");
		qName2 = null;
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "ArrayOfCmtsType");
		cachedSerQNames.add(qName);
		cls = com.nds.www.wsdl.CableAssurance.CmtsType[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "cmtsType");
		qName2 = null;
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "cmtsType");
		cachedSerQNames.add(qName);
		cls = com.nds.www.wsdl.CableAssurance.CmtsType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "localSystemType");
		cachedSerQNames.add(qName);
		cls = com.nds.www.wsdl.CableAssurance.LocalSystemType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "systemTypeType");
		cachedSerQNames.add(qName);
		cls = com.nds.www.wsdl.CableAssurance.SystemTypeType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

	}

	protected org.apache.axis.client.Call createCall()
			throws java.rmi.RemoteException {
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
					_call
							.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
					_call
							.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses
								.get(i);
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames
								.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories
									.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories
									.get(i);
							_call
									.registerTypeMapping(cls, qName, sf, df,
											false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
									.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
									.get(i);
							_call
									.registerTypeMapping(cls, qName, sf, df,
											false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault(
					"Failure trying to get the Call object", _t);
		}
	}

	public com.nds.www.wsdl.CableAssurance.LocalSystemType queryLocalSystem()
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call
				.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "queryLocalSystem"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (com.nds.www.wsdl.CableAssurance.LocalSystemType) _resp;
				} catch (java.lang.Exception _exception) {
					return (com.nds.www.wsdl.CableAssurance.LocalSystemType) org.apache.axis.utils.JavaUtils
							.convert(
									_resp,
									com.nds.www.wsdl.CableAssurance.LocalSystemType.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public com.nds.www.wsdl.CableAssurance.AggregateType[] queryChildAggregates()
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call
				.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance",
				"queryChildAggregates"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (com.nds.www.wsdl.CableAssurance.AggregateType[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (com.nds.www.wsdl.CableAssurance.AggregateType[]) org.apache.axis.utils.JavaUtils
							.convert(
									_resp,
									com.nds.www.wsdl.CableAssurance.AggregateType[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public com.nds.www.wsdl.CableAssurance.CmtsType[] queryChildCmts()
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call
				.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://www.nds.com/wsdl/CableAssurance", "queryChildCmts"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (com.nds.www.wsdl.CableAssurance.CmtsType[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (com.nds.www.wsdl.CableAssurance.CmtsType[]) org.apache.axis.utils.JavaUtils
							.convert(
									_resp,
									com.nds.www.wsdl.CableAssurance.CmtsType[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	/*
	 * public static void main(String args[]) throws Exception {
	 * com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub binding; try {
	 * binding = (com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub)
	 * new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator()
	 * .getLocalSystemPort(); } catch (javax.xml.rpc.ServiceException jre) { if
	 * (jre.getLinkedCause() != null) jre.getLinkedCause().printStackTrace();
	 * throw new junit.framework.AssertionFailedError( "JAX-RPC ServiceException
	 * caught: " + jre); } // assertNotNull("binding is null", binding);
	 *  // Time out after a minute binding.setTimeout(60000);
	 *  // Test operation com.nds.www.wsdl.CableAssurance.LocalSystemType value =
	 * null; value = binding.queryLocalSystem(); // TBD - validate results
	 * System.out.println("LocalSystemType: " + value); }
	 */

	public static void main(String args[]) throws Exception {
		Options opts = new Options(args);

		System.out.println("opts.getURL(): " + opts.getURL()); // System.exit(1);

		com.nds.www.wsdl.CableAssurance.LocalSystemService abs = new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator();
		opts.setDefaultURL(abs.getLocalSystemPortAddress()); //
		// opts.setDefaultURL(opts.getURL());
		System.out.println("Service Address: " + abs.getLocalSystemPortAddress());

		URL serviceURL = new URL(opts.getURL());
		System.out.println("serviceURL: " + serviceURL);

		com.nds.www.wsdl.CableAssurance.LocalSystemType ab1 = null;
		if (serviceURL == null) {
			ab1 = abs.getLocalSystemPort().queryLocalSystem();
		} else {
			ab1 = abs.getLocalSystemPort(serviceURL).queryLocalSystem();
		}

		System.out.println("LocalSystemType: " + ab1.getSystemType());
	}

}