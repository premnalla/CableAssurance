/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */
package com.nokia.oss.ossj.sa.ri.xml;

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.order.*;
import com.nokia.oss.ossj.sa.ri.service.*;

import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.rmi.*;
import java.text.*;

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class XmlCoDecBean implements SessionBean {

    public static class MapEntryImpl implements Map.Entry {
        private String key;
        private String value;
        public MapEntryImpl() {};
        public MapEntryImpl(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public Object getKey() { return key; }
        public void setKey(String key) {
            this.key = key;
        }
        public String getName() { return key; }
        public void setName(String name) {
            this.key = name;
        }
        public Object getValue() { return value; }
        public Object setValue(Object value) { 
            String old = value.toString();
            this.value = value.toString(); 
            return old;
        }
        public boolean equals(Object o) { 
            if (o instanceof Map.Entry) 
                return key.equals(((Map.Entry)o).getKey()) && value.equals(((Map.Entry)o).getValue());
            else 
                return false;
        }
        public int hashCode() {
            return key.hashCode()^value.hashCode();
        }
    }
    
    private BeaTrace myLog = null;
    private boolean isLoggingEnabled = false;
    private DocumentBuilder myDocumentBuilder;
    private Context myNamingContext;
        
    public static final String XML_BASE_PATH = "com/nokia/oss/ossj/sa/XML/";
    
    public static final String CO_NS = "http://www.ossj.org/Common";
    public static final String SA_NS = "http://www.ossj.org/ServiceActivation";
    public static final String RI_NS = "http://www.ossj.org/RiServiceActivation";
    public static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String ALL_NS = "*";
    public static final String NO_NS = "NONE";

    // for encoding
    public static final String CO_NS_PREFIX = "co:";
    public static final String SA_NS_PREFIX = "sa:";
    public static final String RI_NS_PREFIX = "ri:";
    
    // for decoding ns prefixes have to be calculated
    public static final int CO = 0;
    public static final int SA = 1;
    public static final int RI = 2;
    public static final int SCHEMA = 3;
    public static final int NS_COUNT = 4;
    
    public static final Class[] CoDefinedArrays = {String[].class, ManagedEntityValue[].class};
    public static final Class[] SaDefinedArrays = {OrderValue[].class, ServiceValue[].class, OrderKey[].class, ServiceKeyResult[].class };
    
    public static final Map type2Interface;

    /**
     * Defines the format string how a java.util.Date has to be represented in xml. As the time in java.util.Date 
     * is UTC per default, and xml time zone extension (-00:00 or +00:00) defines the difference to UT, it is set
     * to zero per default.
     */
    //public static final SimpleDateFormat xmlDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:hh:ss.SSS'-00:00'");
    public static final SimpleDateFormat xmlDateTime;
    
    /** Holds value of property jvtActivationSession. */
    private javax.oss.order.JVTActivationSession jvtActivationSession;
    
    static {
        
        // initialize DateFormat
        int h2ms = 60*60*1000; // how many milliseconds is an hour
        int m2ms = 60*1000;
        java.util.TimeZone tz = java.util.TimeZone.getDefault();
        int utcOffset = tz.getRawOffset();
        if (utcOffset>12*h2ms) utcOffset-=24*h2ms;
        int offMins = utcOffset/m2ms;
        int hours = Math.abs(offMins/60);
        int minutes = Math.abs(offMins%60);
        String offset = (offMins<0?"-":"+")+(Math.abs(hours)<10?"0":"")+hours+":"+(Math.abs(minutes)<10?"0":"")+minutes;
        xmlDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'"+offset+"'");
        xmlDateTime.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        
        // initialize type 2 interface mapping
        type2Interface = new TreeMap();
        type2Interface.put("ManagedEntityValue", javax.oss.ManagedEntityValue.class);
        type2Interface.put("OrderValue", javax.oss.order.OrderValue.class);
        type2Interface.put("CreateOrderValue", javax.oss.order.CreateOrderValue.class);
        type2Interface.put("ModifyOrderValue", javax.oss.order.ModifyOrderValue.class);
        type2Interface.put("CancelOrderValue", javax.oss.order.CancelOrderValue.class);
        type2Interface.put("ServiceValue", javax.oss.service.ServiceValue.class);
        type2Interface.put("ManagedEntityKey", javax.oss.ManagedEntityKey.class);
        type2Interface.put("OrderKey", javax.oss.order.OrderKey.class);
        type2Interface.put("ServiceKey", javax.oss.service.ServiceKey.class);
        type2Interface.put("ManagedEntityKeyResult", javax.oss.ManagedEntityKeyResult.class);
        type2Interface.put("OrderKeyResult", javax.oss.order.OrderKeyResult.class);
        type2Interface.put("ServiceKeyResult", javax.oss.service.ServiceKeyResult.class);
        type2Interface.put("Exception", java.lang.Exception.class);
        type2Interface.put("IllegalArgumentException", javax.oss.IllegalArgumentException.class);
        type2Interface.put("UnsupportedOperationException", javax.oss.UnsupportedOperationException.class);
        type2Interface.put("SetException", javax.oss.SetException.class);
        type2Interface.put("RemoteException", RemoteException.class);
        type2Interface.put("ObjectNotFoundException", ObjectNotFoundException.class);
        type2Interface.put("FinderException", FinderException.class);
        type2Interface.put("CreateException", CreateException.class);
        type2Interface.put("RemoveException", RemoveException.class);
        type2Interface.put("DuplicateKeyException", DuplicateKeyException.class);
    }
    
    /** Creates new XmlCoDec */
    public XmlCoDecBean() {
    }

    public XmlCoDecBean(BeaTrace logger,boolean logginEnabled) {
        myLog = logger;
        isLoggingEnabled = logginEnabled;
    }
    
    protected boolean containsElement(Element anElement, String nsURI, String tag) {
        if (nsURI == null) throw new java.lang.IllegalArgumentException("Namespace may not be null, use constant NO_NS instead");
        return getChildNodes(anElement, nsURI, tag).getLength()>0;
    }
    
    /** use this method for parameter decoding if you want to use the first
     *element with that tag and know it is a class or subclass of
     *ManagedEntityValue, ManagedEntityKey oder ManagedEntityKeyResult*/
    public Object decodeParameter(Object newParameter, Element rootElement, String tag) throws javax.oss.IllegalArgumentException {
        return decodeParameter(newParameter, rootElement, tag, 0);
    }
    
    /** use this method for parameter decoding if you know it is a class or subclass of
     *ManagedEntityValue, ManagedEntityKey oder ManagedEntityKeyResult*/
    public Object decodeParameter(Object newParameter, Element rootElement, String tag, int position) throws javax.oss.IllegalArgumentException {
        return decodeParameter(newParameter, rootElement, NO_NS, tag, position, null);
    }
    
    public Object decodeParameter(Object newParameter, Element rootElement, String tag, Class type) throws javax.oss.IllegalArgumentException {
        return decodeParameter(newParameter, rootElement, NO_NS, tag, 0, type);
    }

/**
 * @param newParameter  if specified this object is used and filled with values according to document, 
 *                      null if a new object shall be created.
 * @param rootElement   the xml element which contains the element which shall be decoded
 * @param tag           the tag of the element which shall be decoded
 * @param nsURI         the namespace URI of the tag
 * @param position      use the n-th element which name is "tag", if not specified, first element is used
 * @param type          type of java object which shall be decoded, if null xsi:type is used to determine type dynamically
 */    
    public Object decodeParameter(Object newParameter, Element rootElement, String nsURI, String tag, int position, Class type) throws javax.oss.IllegalArgumentException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("decodeParameter", new Object[][] {
                {"newParameter", newParameter},
                {"rootElement", rootElement},
                {"nsURI", nsURI},
                {"tag", tag},
                {"position", new Integer(position)},
                {"type", type}
            });

        Element paramElement = null;

        if (nsURI == null) nsURI = NO_NS;
        /*
        if (nsURI.equals(ALL_NS)) {
            // item in arrays for example can have different namespaces, depending on the array
            NodeList list = rootElement.getChildNodes();
            int correctElements = 0;
            for (int i=0; i<list.getLength() && paramElement==null ; i++) {
                if (correctElements==position+1) {
                    if (isLoggingEnabled) myLog.log("no element (or not enough elements) found for "+tag+" in all namespaces");
                    if (isLoggingEnabled)
                        myLog.logMethodExit("decodeParameter", null);
                    return null;
                }
                if (list.item(i).getNodeType()==Node.ELEMENT_NODE) {
                    Element anElement = (Element)list.item(i);
                    String name = anElement.getNodeName();
                    if (name.lastIndexOf(':')+1==name.indexOf(tag)) {
                        if (correctElements == position) {
                            paramElement = anElement;
                        }
                        correctElements++;
                    }
                }
            }
        } else */ {
         
            // get the first element named like that
            NodeList list = getChildNodes(rootElement, nsURI, tag);
            if (list.getLength()<position+1) {
                if (isLoggingEnabled) myLog.log("no element (or not enough elements) found for "+tag+" in namespace "+nsURI);
                if (isLoggingEnabled)
                    myLog.logMethodExit("decodeParameter", null);
                return null;
            }
            paramElement = (Element)list.item(position);
        }
        
        
        // determine type from xsi:type attribute
        if (type == null) {
            String typeName = paramElement.getAttributeNS(XSI_NS,"type");
            if (isLoggingEnabled)
                myLog.log("found "+XSI_NS+":type = "+typeName);

            // REALLY?!
            if (typeName == null || typeName.equals("")) {
                typeName = paramElement.getTagName();
            }
            // ---

            String withoutNsType = typeName.substring(typeName.indexOf(':')+1, typeName.length());
            if (isLoggingEnabled)
                myLog.log("found xsi:type without NS = "+typeName);
            if (withoutNsType.startsWith("ArrayOf")) {
                typeName = withoutNsType.substring(7, withoutNsType.length());
                Class componentType = (Class)type2Interface.get(typeName);
                Object[] array = (Object[])Array.newInstance(componentType, 0);

                // should be something like javax.oss.order.OrderValue[].class now !
                type = array.getClass();
            } else {
                type = (Class)type2Interface.get(withoutNsType);
            }
            if (isLoggingEnabled)
                myLog.log("found corresponding class = "+type);
        }
        
        // test for nil
        boolean isNil = paramElement.getAttributeNS(XSI_NS,"nil").equals("true");
        
        String value = null;
        NodeList list = paramElement.getChildNodes();
        for (int i=list.getLength()-1; i>=0; i--) {
            if (list.item(i).getNodeType()==Node.TEXT_NODE) {
                value = ((Text)list.item(i)).getNodeValue();
            }
        }
        if (isLoggingEnabled) myLog.log("text value "+value );
        
        //***********************************
        // ARRAY
        //***********************************
        if (type.isArray()) {
            Class componentType = type.getComponentType();
            NodeList items = paramElement.getElementsByTagName("item");
            int itemCount = items.getLength();
            Object[] objects = (Object[])Array.newInstance(componentType, itemCount);
            for (int i=0; i<itemCount ; i++) {
                // relay on xsi:type attribute to determine exact type of array elements
                objects[i] = decodeParameter(null, paramElement, ALL_NS, "item", i, null);
            }
            newParameter = objects;
            
        }

        //***********************************
        // STRING
        //***********************************
        else if (type.equals(String.class)) {
            
            newParameter = value;
            
        }

        //***********************************
        // ILLEGAL ARGUMENT EXCEPTION
        //***********************************
        else if (type.equals(javax.oss.IllegalArgumentException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            
            newParameter = new javax.oss.IllegalArgumentException(message);
        }

        //***********************************
        // UNSUPPORTED OPERATION EXCEPTION
        //***********************************
        else if (type.equals(javax.oss.UnsupportedOperationException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            
            newParameter = new javax.oss.UnsupportedOperationException(message);
        }

        //***********************************
        // SET EXCEPTION
        //***********************************
        else if (type.equals(javax.oss.SetException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            
            newParameter = new javax.oss.SetException(message);
        }

        //***********************************
        // REMOTE EXCEPTION
        //***********************************
        else if (type.equals(RemoteException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            Throwable nested = (Exception)decodeParameter(null, paramElement, "nestedException");
            
            newParameter = new RemoteException(message, nested);
        }

        //***********************************
        // OBJECT NOT FOUND EXCEPTION
        //***********************************
        else if (type.equals(ObjectNotFoundException.class)) { //extends FinderException!
            
            newParameter = decodeParameter(null, rootElement, nsURI, tag, position, FinderException.class);
        }

        //***********************************
        // FINDER EXCEPTION
        //***********************************
        else if (type.equals(FinderException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            
            newParameter = new FinderException(message);
        }

        //***********************************
        // CREATE EXCEPTION
        //***********************************
        else if (type.equals(CreateException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            
            newParameter = new CreateException(message);
        }

        //***********************************
        // REMOVE EXCEPTION
        //***********************************
        else if (type.equals(RemoveException.class)) {
            
            // message has to be set on initialization of new exceptions! :-(
            String message = ((Exception)decodeParameter(null, rootElement, nsURI, tag, position, Exception.class)).getMessage();
            
            newParameter = new RemoveException(message);
        }

        //***********************************
        // DUPLICATE KEY EXCEPTION
        //***********************************
        else if (type.equals(DuplicateKeyException.class)) { //extends CreateException!
            
            newParameter = decodeParameter(null, rootElement, nsURI, tag, position, CreateException.class);
        }

        //***********************************
        // EXCEPTION
        //***********************************
        else if (type.equals(Exception.class)) {
            
            newParameter = new Exception(value);
        }

        //***********************************
        // CREATE ORDER VALUE
        //***********************************
        else if (type.equals(CreateOrderValue.class)) {
            
            // decode OrderValue
            try {
                OrderValue anOrderValue = getJvtActivationSession().makeOrderValue(CreateOrderValue.class.getName());

                newParameter =  decodeParameter(anOrderValue, rootElement, nsURI, tag, position, OrderValue.class);

                anOrderValue = (OrderValueImpl)newParameter;
            } catch (RemoteException re) {
                if (isLoggingEnabled)
                    myLog.logException(re);
            }
        }
        
        //***********************************
        // MODIFY ORDER VALUE
        //***********************************
        else if (type.equals(ModifyOrderValue.class)) {
            
            // decode OrderValue
            try {
                OrderValue anOrderValue = getJvtActivationSession().makeOrderValue(ModifyOrderValue.class.getName());

                newParameter =  decodeParameter(anOrderValue, rootElement, nsURI, tag, position, OrderValue.class);

                anOrderValue = (OrderValueImpl)newParameter;
            } catch (RemoteException re) {
                if (isLoggingEnabled)
                    myLog.logException(re);
            }
        }    
        
        //***********************************
        // CANCEL ORDER VALUE
        //***********************************
        else if (type.equals(CancelOrderValue.class)) {
            
            // decode OrderValue
            try {
                OrderValue anOrderValue = getJvtActivationSession().makeOrderValue(CancelOrderValue.class.getName());

                newParameter =  decodeParameter(anOrderValue, rootElement, nsURI, tag, position, OrderValue.class);

                anOrderValue = (OrderValueImpl)newParameter;
            } catch (RemoteException re) {
                if (isLoggingEnabled)
                    myLog.logException(re);
            }
        }    
        
        //***********************************
        // ORDER VALUE
        //***********************************
        else if (type.equals(OrderValue.class)) {
            
            if (newParameter == null) {
                newParameter = new OrderValueImpl();
            }
            
            OrderValue oValue = (OrderValue)decodeParameter(newParameter, rootElement, nsURI, tag, position, ManagedEntityValue.class);
            newParameter = oValue;
            
            if (containsElement(paramElement, ALL_NS, "actualCompletionDate")) {
                oValue.setActualCompletionDate((java.util.Date)decodeParameter(null, paramElement, "actualCompletionDate", java.util.Date.class));
            }
            
            if (containsElement(paramElement, ALL_NS, "apiClientId")) {
                oValue.setApiClientId((String)decodeParameter(null, paramElement, "apiClientId", String.class));
            }
            
            if (containsElement(paramElement, ALL_NS, "description")) {
                oValue.setDescription((String)decodeParameter(null, paramElement, "description", String.class));
            }
            
            if (containsElement(paramElement, ALL_NS, "failedServices")) {
                oValue.setFailedServices((ServiceKeyResult[])decodeParameter(null, paramElement, "failedServices", ServiceKeyResult[].class));
            }
            
            if (containsElement(paramElement, ALL_NS, "orderDate")) {
                oValue.setOrderDate((java.util.Date)decodeParameter(null, paramElement, "orderDate", java.util.Date.class));
            }
            
            if (containsElement(paramElement, ALL_NS, "orderKey")) {
                oValue.setOrderKey((javax.oss.order.OrderKey)decodeParameter(null, paramElement, "orderKey", javax.oss.order.OrderKey.class));
            }
            
            if (containsElement(paramElement, SA_NS, "priority")) {
                oValue.setPriority(((Integer)decodeParameter(null, paramElement, SA_NS, "priority", 0, Integer.class)).intValue());
            }
            
            if (containsElement(paramElement, ALL_NS, "purchaseOrder")) {
                oValue.setPurchaseOrder((String)decodeParameter(null, paramElement, "purchaseOrder", String.class));
            }
            
            if (containsElement(paramElement, ALL_NS, "services")) {
                oValue.setServices((javax.oss.service.ServiceValue[])decodeParameter(null, paramElement, "services", javax.oss.service.ServiceValue[].class));
            }
            
            if (containsElement(paramElement, SA_NS, "orderState")) {
                oValue.setState((String)decodeParameter(null, paramElement, SA_NS, "orderState", 0, String.class));
            }
       
        }

        //***********************************
        // RI SERVICE VALUE - and all subtypes
        //***********************************
        else if (RiServiceValue.class.isAssignableFrom(type)) {
            
            try {
                if (newParameter == null) {
                    RiServiceValue aService = (RiServiceValue)getJvtActivationSession().makeServiceValue(type.getName());
                    newParameter = aService;
                }

                RiServiceValue aService = (RiServiceValue)newParameter;

                if (containsElement(paramElement, ALL_NS, "serviceActivatorHomeJndiName")) {
                    aService.setServiceActivatorHomeJndiName((String)decodeParameter(null, paramElement, "serviceActivatorHomeJndiName", String.class));
                }
            } catch (RemoteException re) {
                if (isLoggingEnabled)
                    myLog.logException(re);
            }
            
        }

        //***********************************
        // SERVICE VALUE
        //***********************************
        else if (type.equals(ServiceValue.class)) {
            
            if (newParameter == null) {
                newParameter = new RiServiceValueImpl();
            }
            
            ServiceValue aService = (ServiceValue)newParameter;
            
            if (containsElement(paramElement, NO_NS, "serviceKey")) {
                aService.setServiceKey((ServiceKey)decodeParameter(null, paramElement, "serviceActivatorHomeJndiName", ServiceKey.class));
            }
            if (containsElement(paramElement, SA_NS, "serviceState")) {
                aService.setState((String)decodeParameter(null, paramElement, "sa:serviceState", String.class));
            }
            if (containsElement(paramElement, NO_NS, "subscriberId")) {
                aService.setSubscriberId((String)decodeParameter(null, paramElement, "subscriberId", String.class));
            }
            
        }

        //***********************************
        // MANAGED ENTITY VALUE
        //***********************************
        else if (type.equals(ManagedEntityValue.class)) {
            
            // do nothing
            
        }

        //***********************************
        // ORDER KEY
        //***********************************
        else if (type.equals(OrderKey.class)) {
            
            if (newParameter == null) {
                newParameter = new OrderKeyImpl();
            }
            
            newParameter = decodeParameter(newParameter, rootElement, nsURI, tag, position, ManagedEntityKey.class);
            
            OrderKey oKey = (OrderKey)newParameter;
            
            oKey.setPrimaryKey((String)decodeParameter(null, paramElement, "primaryKey", String.class));
        }

        //***********************************
        // SERVICE KEY
        //***********************************
        else if (type.equals(ServiceKey.class)) {
            
            if (newParameter == null) {
                newParameter = new ServiceKeyImpl();
            }
            
            newParameter = decodeParameter(newParameter, rootElement, nsURI, tag, position, ManagedEntityKey.class);
            
            ServiceKey sKey = (ServiceKey)newParameter;
            
            sKey.setPrimaryKey((String)decodeParameter(null, paramElement, "primaryKey", String.class));
        }

        //***********************************
        // MANAGED ENTITY KEY
        //***********************************
        else if (type.equals(ManagedEntityKey.class)) {
            
            if (newParameter == null) {
                // well, MEKImpl is abstract ...
            } else {
            
                ManagedEntityKey key = (ManagedEntityKey)newParameter;
                
                if (key instanceof ManagedEntityKeyImpl) {
                    ManagedEntityKeyImpl meki = (ManagedEntityKeyImpl)key;
                    meki.setApplicationContext((ApplicationContext)decodeParameter(null, paramElement, CO_NS, "applicationContext", 0, ApplicationContext.class));
                    meki.setApplicationDN((String)decodeParameter(null, paramElement, CO_NS, "applicationDN", 0, String.class));
                }
                key.setType((String)decodeParameter(null, paramElement, CO_NS, "type", 0, String.class));
                
            }
            
        }

        //***********************************
        // APPLICATION CONTEXT
        //***********************************
        else if (type.equals(ApplicationContext.class)) {
            
            if (newParameter == null) {
                String factory = (String)decodeParameter(null, paramElement, CO_NS, "factoryClass", 0, String.class);
                String url = (String)decodeParameter(null, paramElement, CO_NS, "url", 0, String.class);
                java.util.Map map = (java.util.Map)decodeParameter(null, paramElement, CO_NS, "systemProperties", 0, java.util.Map.class);
                
                newParameter = new ApplicationContextImpl(factory, map, url);
            }
            
        }

        //***********************************
        // ORDER KEY RESULT
        //***********************************
        else if (type.equals(OrderKeyResult.class)) {

            newParameter = new OrderKeyResultImpl();
            
            newParameter = decodeParameter(newParameter, rootElement, nsURI, tag, position, ManagedEntityKeyResult.class);
            // Order Key gets decoded in MEKR - orderkey type is detected by xsi:type attribute
            
            if (!(((ManagedEntityKeyResult)newParameter).getManagedEntityKey() instanceof OrderKey)) {
                // hmm - somehow the wrong MEK type was used!
                newParameter = null;
            }
        }

        //***********************************
        // SERVICE KEY RESULT
        //***********************************
        else if (type.equals(ServiceKeyResult.class)) {
            
            newParameter = new ServiceKeyResultImpl();
            
            newParameter = decodeParameter(newParameter, rootElement, nsURI, tag, position, ManagedEntityKeyResult.class);
            // Service Key gets decoded in MEKR  - servicekey type is detected by xsi:type attribute
            
            if (!(((ManagedEntityKeyResult)newParameter).getManagedEntityKey() instanceof ServiceKey)) {
                // hmm - somehow the wrong MEK type was used!
                newParameter = null;
            }
        }

        //***********************************
        // MANAGED ENTITY KEY RESULT
        //***********************************
        else if (type.equals(ManagedEntityKeyResult.class)) {
            
            if (newParameter==null) {
                newParameter = new ManagedEntityKeyResultImpl();
            }
            
            // I only know how to set these values with my own implementation
            if (newParameter instanceof ManagedEntityKeyResultImpl) {
                ManagedEntityKeyResultImpl keyResult = (ManagedEntityKeyResultImpl)newParameter;

                keyResult.setSuccess(((Boolean)decodeParameter(null, paramElement, "success", Boolean.class)).booleanValue());

                keyResult.setException((Exception)decodeParameter(null, paramElement, "exception"));
                
                keyResult.setManagedEntityKey((ManagedEntityKey)decodeParameter(null, paramElement, "managedEntityKey"));
            }
        }

        //***********************************
        // MAP ENTRY
        //***********************************
        else if (type.equals(java.util.Map.Entry.class)) {
            
            if (newParameter == null) {
                MapEntryImpl mei = new XmlCoDecBean.MapEntryImpl();
                mei.setName((String)decodeParameter(null, paramElement, "name", String.class));
                mei.setValue((String)decodeParameter(null, paramElement, "value", String.class));
                newParameter = mei;
            }
            
        }
        
        //***********************************
        // MAP
        //***********************************
        else if (type.equals(java.util.Map.class)) {
            
            if (newParameter == null) {
                newParameter = new HashMap();
            }
            
            Map aMap = (Map)newParameter;

            // get the first element named like that
            NodeList properties = getChildNodes(paramElement, ALL_NS, "property");
            for (int i=0; i<properties.getLength() ; i++) {
                Map.Entry entry = (Map.Entry)decodeParameter(null, paramElement, ALL_NS, "property", i, Map.Entry.class);
                aMap.put(entry.getKey(), entry.getValue());
            }
            
        }

        //***********************************
        // DATE
        //***********************************
        else if (type.equals(java.util.Date.class)) {
            
            try {
                newParameter =  xmlDateTime.parse(value);
            } catch (ParseException pe) {
                throw new javax.oss.IllegalArgumentException("Error while parsing date! Unable to convert \""+value+"\" to java.util.Date");
            }
            
        }

        //***********************************
        // INTEGER
        //***********************************
        else if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
            
            try {
                newParameter = new Integer(value);
            } catch (NumberFormatException nfe) {
                // TODO: ???
            }
            
        }

        //***********************************
        // BOOLEAN
        //***********************************
        else if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
            newParameter = new Boolean(value);
        }

        if (isLoggingEnabled)
            myLog.logMethodExit("decodeParameter", new Object[] {"newParameter", newParameter});
        return newParameter;
    }
    
    public void encodeParameter(Document doc, Element parent, Object value) {
        encodeParameter(doc, parent, null, value.getClass(), value);
    }
    
    public void encodeParameter(Document doc, Element parent, Class type, Object value) {
        encodeParameter(doc, parent, null, type, value);
    }
    
    public void encodeParameter(Document doc, Element parent, String name, Object value) {
        if (value!=null) {
            encodeParameter(doc, parent, name, value.getClass(), value);
        } else {
            //type of null value is irrelevant since the element is set to nil anyway
            encodeParameter(doc, parent, name, null, value);
        }
    }
    
    public void encodeParameter(Document doc, Element parent, String name, Class type, Object value) {
        if (isLoggingEnabled)
            myLog.logMethodEntry("encodeParameter", new Object[][] {
                {"doc", doc},
                {"parent", parent},
                {"name", name},
                {"type", type},
                {"value", value}
            });
            
            // create new element first if name is specified
            if (name!=null && name!="") {
                Element child = doc.createElement(name);
                parent.appendChild(child);
                parent = child;
            }
            
            // set element to nil if value is null!
            if (value==null) {
                parent.setAttribute("xsi:nil","true");
                return;
            }
            
            
            //***********************************
            // INTERFACE IMPLS
            //***********************************
            if (type.getName().indexOf("Impl")>0 && ManagedEntityValue.class.isAssignableFrom(type)) {
                String typeName = type.getName();
                try {
                    if (isLoggingEnabled)
                        myLog.log("changing implementation type "+typeName+" to interface type "+typeName.substring(0, typeName.indexOf("Impl")));
                    type = Class.forName(typeName.substring(0, typeName.indexOf("Impl")));
                } catch (ClassNotFoundException cnfe) {
                    if (isLoggingEnabled) myLog.logException(cnfe);
                    return;
                }
            }
            
            //***********************************
            // ARRAY
            //***********************************
            if (type.isArray()) {
                
                Object[] array = (Object[])value;
                
                String namespace_prefix = null;
                for (int i=0; i<SaDefinedArrays.length && namespace_prefix==null ;i++) {
                    if (SaDefinedArrays[i].isAssignableFrom(type)) {
                        namespace_prefix = SA_NS_PREFIX;
                    }
                }
                for (int i=0; i<CoDefinedArrays.length && namespace_prefix==null ;i++) {
                    if (CoDefinedArrays[i].isAssignableFrom(type)) {
                        namespace_prefix = CO_NS_PREFIX;
                    }
                }

                for (int i=0; i<array.length; i++) {
                    encodeParameter(doc, parent, namespace_prefix+"item", array[i]);
                }
            }
            
            //***********************************
            // STRING
            //***********************************
            else if (type.equals(String.class)) {
                
                parent.appendChild(doc.createTextNode((String)value));
            }
            
            //***********************************
            // EXCEPTION
            //***********************************
            else if (Exception.class.isAssignableFrom(type)) {
                
                Exception e = (Exception)value;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String message = e.toString();
                String stackTrace = baos.toString();
                encodeParameter(doc, parent, "message", message+"\n"+stackTrace);
            }
            
            //***********************************
            // ORDER VALUE
            //***********************************
            else if (type.equals(OrderValue.class)) {
                
                OrderValue anOrderValue = (OrderValue)value;
                
                String orderType = null;
                if (anOrderValue.isPopulated(OrderValue.KEY)) {
                    orderType = anOrderValue.getOrderKey().getType();
                } else if (value instanceof ManagedEntityValueImpl) {
                    orderType = ((ManagedEntityValueImpl)value).getManagedEntityKeyDummy().getType();
                } else {
                    // TODO: what else?
                }
                orderType = orderType.substring(orderType.lastIndexOf(".")+1, orderType.length());
                
                parent.setAttribute("xsi:type", orderType);
                
                encodeParameter(doc, parent, ManagedEntityValue.class, value);
                
                // actualCompletionDate
                if (anOrderValue.isPopulated(OrderValue.ACTUAL_COMPLETION_DATE))
                    encodeParameter(doc, parent, "sa:actualCompletionDate", java.util.Date.class, anOrderValue.getActualCompletionDate());
                
                // apiClientId
                if (anOrderValue.isPopulated(OrderValue.API_CLIENT_ID))
                    encodeParameter(doc, parent, "sa:apiClientId", String.class, anOrderValue.getApiClientId());
                
                // description
                if (anOrderValue.isPopulated(OrderValue.DESCRIPTION))
                    encodeParameter(doc, parent, "sa:description", String.class, anOrderValue.getDescription());

                // failedServices
                if (anOrderValue.isPopulated(OrderValue.FAILED_SERVICES))
                    encodeParameter(doc, parent, "sa:failedServices", anOrderValue.getFailedServices());
                
                // orderDate
                if (anOrderValue.isPopulated(OrderValue.ORDER_DATE))
                    encodeParameter(doc, parent, "sa:orderDate", java.util.Date.class, anOrderValue.getOrderDate());
                
                // orderKey
                if (anOrderValue.isPopulated(OrderValue.KEY))
                    encodeParameter(doc, parent, "sa:orderKey", OrderKey.class, anOrderValue.getOrderKey());
                
                // priority
                if (anOrderValue.isPopulated(OrderValue.PRIORITY))
                    encodeParameter(doc, parent, "sa:priority", Integer.TYPE, new Integer(anOrderValue.getPriority()));
                
                //purchase order
                if (anOrderValue.isPopulated(OrderValue.PURCHASE_ORDER))
                    encodeParameter(doc, parent, "sa:purchaseOrder", String.class, anOrderValue.getPurchaseOrder());
                
                // requestedCompletionDate
                if (anOrderValue.isPopulated(OrderValue.REQUESTED_COMPLETION_DATE))
                    encodeParameter(doc, parent, "sa:requestedCompletionDate", java.util.Date.class, anOrderValue.getRequestedCompletionDate());
                
                //services
                if (anOrderValue.isPopulated(OrderValue.SERVICES))
                    encodeParameter(doc, parent, "sa:services", ServiceValue[].class, anOrderValue.getServices());
                
                // orderState
                if (anOrderValue.isPopulated(OrderValue.STATE))
                    encodeParameter(doc, parent, "sa:orderState", String.class, anOrderValue.getState());
            }
            
            //***********************************
            // SERVICE VALUE
            //***********************************
            else if (type.equals(RiServiceValue.class)) {
                
                RiServiceValue aServiceValue = (RiServiceValue)value;
                
                String serviceType = null;
                if (aServiceValue.isPopulated(ServiceValue.KEY)) {
                    serviceType = aServiceValue.getServiceKey().getType();
                } else if (value instanceof ManagedEntityValueImpl) {
                    serviceType = ((ManagedEntityValueImpl)value).getManagedEntityKeyDummy().getType();
                } else {
                    // TODO: what else? 
                }
                
                // how can I check to which namespace the type belongs?!
                serviceType = "ri:"+serviceType.substring(serviceType.lastIndexOf(".")+1, serviceType.length());
                
                parent.setAttribute("xsi:type", serviceType);

                encodeParameter(doc, parent, ServiceValue.class, value);
                
                if (aServiceValue.isPopulated(RiServiceValue.SERVICE_ACTIVATOR_HOME_JNDI_NAME))
                encodeParameter(doc, parent, "ri:serviceActivatorHomeJndiName", aServiceValue.getServiceActivatorHomeJndiName());
            }
            
            //***********************************
            // SERVICE VALUE
            //***********************************
            else if (type.equals(ServiceValue.class)) {
                
                ServiceValue aServiceValue = (ServiceValue)value;
                
                encodeParameter(doc, parent, ManagedEntityValue.class, value);
                
                // key
                if (aServiceValue.isPopulated(ServiceValue.KEY))
                encodeParameter(doc, parent, "sa:serviceKey", ServiceKey.class, aServiceValue.getServiceKey());
                
                // state
                if (aServiceValue.isPopulated(ServiceValue.STATE))
                encodeParameter(doc, parent, "sa:serviceState", aServiceValue.getState());
                
                // subscriber id
                if (aServiceValue.isPopulated(ServiceValue.SUBSCRIBER_ID))
                encodeParameter(doc, parent, "sa:subscriberId", aServiceValue.getSubscriberId());
            }
            
            //***********************************
            // MANAGED ENTITY VALUE
            //***********************************
            else if (type.equals(ManagedEntityValue.class)) {
                
                // do nothing
            }
            
            //***********************************
            // ORDER KEY
            //***********************************
            else if (type.equals(OrderKey.class)) {
                
                encodeParameter(doc, parent, ManagedEntityKey.class, value);
                
                // should be a string or another type that is supported by encodeParameter
                encodeParameter(doc, parent, "sa:primaryKey", ((OrderKey)value).getPrimaryKey());
            }
            
            //***********************************
            // SERVICE KEY
            //***********************************
            else if (type.equals(ServiceKey.class)) {
                
                encodeParameter(doc, parent, ManagedEntityKey.class, value);

                // should be a string or another type that is supported by encodeParameter
                encodeParameter(doc, parent, "sa:primaryKey", ((ServiceKey)value).getPrimaryKey());
            }
            
            //***********************************
            // MANAGED ENTITY KEY
            //***********************************
            else if (type.equals(ManagedEntityKey.class)) {
                
                ManagedEntityKey mek = (ManagedEntityKey)value;
                
                // applicationContext
                encodeParameter(doc, parent, "co:applicationContext", ApplicationContext.class, mek.getApplicationContext());
                
                // applicationDN
                encodeParameter(doc, parent, "co:applicationDN", String.class, mek.getApplicationDN());
                
                // type
                encodeParameter(doc, parent, "co:type", String.class, mek.getType());
            }
            
            //***********************************
            // APPLICATION CONTEXT
            //***********************************
            else if (type.equals(ApplicationContext.class)) {
                
                ApplicationContext appContext = (ApplicationContext)value;
                
                // factory
                encodeParameter(doc, parent, "co:factoryClass", String.class, appContext.getFactoryClass());
                
                // provider url
                encodeParameter(doc, parent, "co:url", String.class, appContext.getURL());
                
                // properties
                encodeParameter(doc, parent, "co:systemProperties", java.util.Map.class, appContext.getSystemProperties());
            }
            
            //***********************************
            // ORDER KEY RESULT
            //***********************************
            else if (type.equals(OrderKeyResult.class)) {
                
                encodeParameter(doc, parent, ManagedEntityKeyResult.class, value);
                encodeParameter(doc, parent, "sa:orderKey", OrderKey.class, ((OrderKeyResult)value).getOrderKey());
            }
            
            //***********************************
            // SERVICE KEY RESULT
            //***********************************
            else if (type.equals(ServiceKeyResult.class)) {
                
                encodeParameter(doc, parent, ManagedEntityKeyResult.class, value);
                encodeParameter(doc, parent, "sa:serviceKey", ServiceKey.class, ((ServiceKeyResult)value).getServiceKey());
            }
            
            //***********************************
            // MANAGED ENTITY KEY RESULT
            //***********************************
            else if (type.equals(ManagedEntityKeyResult.class)) {

                encodeParameter(doc, parent, "co:success", new Boolean(((ManagedEntityKeyResult)value).isSuccess()));
                
                Exception e = ((ManagedEntityKeyResult)value).getException();
                if (e!=null) 
                encodeParameter(doc, parent, "co:exception", e);                    
                
            }
            
            //***********************************
            // MAP
            //***********************************
            else if (type.equals(java.util.Map.class)) {
                Map aMap = (Map)value;
                
                Iterator entrySetIterator = aMap.entrySet().iterator();
                Map.Entry anEntry;
                Element property;
                while (entrySetIterator.hasNext()) {
                    anEntry = (Map.Entry)entrySetIterator.next();
                    property = doc.createElement("co:property");
                    parent.appendChild(property);
                    encodeParameter(doc, property, "co:name", anEntry.getKey().toString());
                    encodeParameter(doc, property, "co:value", anEntry.getValue().toString());
                }
            }
            
            //***********************************
            // DATE
            //***********************************
            else if (type.equals(java.util.Date.class)) {
                
                encodeParameter(doc, parent, xmlDateTime.format((Date)value));
            }
            
            //***********************************
            // INTEGER
            //***********************************
            else if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
                Integer i = (Integer)value;

                encodeParameter(doc, parent, i.toString());
            }
            
            //***********************************
            // BOOLEAN
            //***********************************
            else if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                Boolean bool = (Boolean)value;
                
                encodeParameter(doc, parent, bool.toString());
            }
            
            //***********************************
            // ORDER ATTRIBUTE VALUE CHANGE EVENT
            //***********************************
            else if (type.equals(javax.oss.order.OrderAttributeValueChangeEvent.class)) {

                OrderAttributeValueChangeEvent anEvent = (OrderAttributeValueChangeEvent)value;

                // how can I check to which namespace the type belongs?!
                String eventType = OrderAttributeValueChangeEvent.class.getName();
                eventType = "sa:"+eventType.substring(eventType.lastIndexOf(".")+1, eventType.length())+"Type";
                
                parent.setAttribute("xsi:type", eventType);

                encodeParameter(doc, parent, javax.oss.Event.class, value);
                
                encodeParameter(doc, parent, "newOrderValue", OrderValue.class, anEvent.getNewOrderValue());
            }
            
            //***********************************
            // ORDER CREATE EVENT
            //***********************************
            else if (type.equals(javax.oss.order.OrderCreateEvent.class)) {

                OrderCreateEvent anEvent = (OrderCreateEvent)value;

                // how can I check to which namespace the type belongs?!
                String eventType = OrderCreateEvent.class.getName();
                eventType = "sa:"+eventType.substring(eventType.lastIndexOf(".")+1, eventType.length());
                
                parent.setAttribute("xsi:type", eventType);

                encodeParameter(doc, parent, javax.oss.Event.class, value);
                
                encodeParameter(doc, parent, "orderValue", OrderValue.class, anEvent.getOrderValue());
            }
            
            //***********************************
            // ORDER REMOVE EVENT
            //***********************************
            else if (type.equals(javax.oss.order.OrderRemoveEvent.class)) {

                OrderRemoveEvent anEvent = (OrderRemoveEvent)value;

                // how can I check to which namespace the type belongs?!
                String eventType = OrderRemoveEvent.class.getName();
                eventType = "sa:"+eventType.substring(eventType.lastIndexOf(".")+1, eventType.length());
                
                parent.setAttribute("xsi:type", eventType);

                encodeParameter(doc, parent, javax.oss.Event.class, value);
                
                encodeParameter(doc, parent, "orderValue", OrderValue.class, anEvent.getOrderValue());
            }
            
            //***********************************
            // ORDER STATE CHANGE EVENT
            //***********************************
            else if (type.equals(javax.oss.order.OrderStateChangeEvent.class)) {

                OrderStateChangeEvent anEvent = (OrderStateChangeEvent)value;

                // how can I check to which namespace the type belongs?!
                String eventType = OrderStateChangeEvent.class.getName();
                eventType = "sa:"+eventType.substring(eventType.lastIndexOf(".")+1, eventType.length());
                
                parent.setAttribute("xsi:type", eventType);

                encodeParameter(doc, parent, javax.oss.Event.class, value);
                
                encodeParameter(doc, parent, "currentState", anEvent.getCurrentState());
                encodeParameter(doc, parent, "orderKey", OrderKey.class, anEvent.getOrderKey());
                encodeParameter(doc, parent, "reason", anEvent.getReason());
                
            }
            
            //***********************************
            // EVENT
            //***********************************
            else if (type.equals(javax.oss.Event.class)) {
                
                Event anEvent = (Event)value;
                
                // ApplicationDN
                encodeParameter(doc, parent, "co:applicationDN", anEvent.getApplicationDN());
                
                // eventTime
                encodeParameter(doc, parent, "co:eventTime", anEvent.getEventTime());
            }
            
            if (isLoggingEnabled)
                myLog.logMethodExit("encodeParameter", null);
    }
    
    public void removeWhiteSpaceNodes(Node node)
    {
        if(node == null) return;
        NodeList children = node.getChildNodes();
        if(children == null) {
            return;
        }
        for(int i = children.getLength()-1; i >= 0 ; i--) {
            Node chi=children.item(i);
            if(chi.getNodeType() == Node.TEXT_NODE && chi.getNodeValue()!=null && chi.getNodeValue().trim().length()==0) {
                node.removeChild(chi);
            } else {
                removeWhiteSpaceNodes( chi );
            }
        }
    }
    
    private DocumentBuilder getDocumentBuilder() {
        if (myDocumentBuilder==null) {
            try {
                myDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException pce) {
                System.out.println(pce.toString());
            }
        }
        return myDocumentBuilder;
    }

    public Document getXmlTemplate(String name)  {
        Document aDoc = getDocumentBuilder().newDocument();
        Element root = aDoc.createElement(name);
        aDoc.appendChild(root);
        root.setAttribute("xmlns","http://www.ossj.org/ServiceActivation");
        root.setAttribute("xmlns:co","http://www.ossj.org/Common");
        root.setAttribute("xmlns:sa","http://www.ossj.org/ServiceActivation");
        root.setAttribute("xmlns:ri","http://www.ossj.org/RiServiceActivation");
        root.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xsi:schemaLocation","http://www.ossj.org/ServiceActivation");
        return aDoc;
    }

    public String toString(Document aDoc) {
        try {
            removeWhiteSpaceNodes(aDoc.getDocumentElement());
            ByteArrayOutputStream baOS = new ByteArrayOutputStream();
            org.apache.xml.serialize.OutputFormat output = new org.apache.xml.serialize.OutputFormat("text", "UTF-8", true);
            output.setPreserveSpace(true);
            org.apache.xml.serialize.XMLSerializer serial = new org.apache.xml.serialize.XMLSerializer(baOS, output);
            serial.serialize(aDoc);
            return new String(baOS.toByteArray());
        } catch (java.io.IOException ioe) {
            System.out.println(ioe.toString());
            return "";
        }
    }
    
    public static void main(String[] args) {
        // test static variable initialization
        System.out.println("done");
    }
    
    // EJB METHODS
    
    public void ejbCreate() throws javax.ejb.CreateException {
		//VP
		try {
			myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP
    }
    
    public void setSessionContext(javax.ejb.SessionContext sCtx) throws javax.ejb.EJBException, java.rmi.RemoteException {

        // create new logger
        //VP isLoggingEnabled = ((java.lang.Boolean)lookup("java:comp/env/loggingEnabled")).booleanValue();
        isLoggingEnabled = ((java.lang.Boolean)lookup("loggingEnabled")).booleanValue();
        createLog("OSS/J SA XmlCodec");
        
        if (isLoggingEnabled)
            myLog.logMethodEntry("setSessionContext", new Object[][] {
                {"sCtx", sCtx}
            });
            
        // add service values to type2Interface map
        String[] services = getJvtActivationSession().getServiceTypes();
        for (int i=0; i<services.length ; i++) {
            // let's hope that for new services the xml element name is the interface minus package
            String elementName = services[i].substring(services[i].lastIndexOf('.')+1, services[i].length());
            try {
                Class type = Class.forName(services[i]);
                type2Interface.put(elementName, type);
            } catch (ClassNotFoundException cnfe) {
                if (isLoggingEnabled)
                    myLog.logException(cnfe);
            }
        }

        if (isLoggingEnabled) myLog.logMethodExit("setSessionContext", null);
    }
    
    public void ejbRemove() throws javax.ejb.EJBException, java.rmi.RemoteException {
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
    }
    
    private Object lookup(String name) {
        try {
            if (myNamingContext==null) {
                //VP myNamingContext = new InitialContext();
                myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
            }
            return myNamingContext.lookup(name);
        } catch (NamingException ne) {
            if (isLoggingEnabled)
                myLog.logException(ne);
        }
        return null;
    }
    
    /** Getter for property jvtActivationSession.
     * @return Value of property jvtActivationSession.
 */
    public javax.oss.order.JVTActivationSession getJvtActivationSession() {
        if (jvtActivationSession == null) {
            try {
                //VP JVTActivationHome aJvtHome = (JVTActivationHome)lookup("java:comp/env/ejb/JVTActivationSession");
                JVTActivationHome aJvtHome = (JVTActivationHome)lookup("ejb/JVTActivationSession");
                jvtActivationSession = aJvtHome.create();
            } catch (CreateException ce) {
                if (isLoggingEnabled)
                    myLog.logException(ce);
            } catch (RemoteException re) {
                if (isLoggingEnabled)
                    myLog.logException(re);
            }
        }
        return jvtActivationSession;
    }    
    
    /** Setter for property jvtActivationSession.
     * @param jvtActivationSession New value of property jvtActivationSession.
 */
    public void setJvtActivationSession(javax.oss.order.JVTActivationSession jvtActivationSession) {
        this.jvtActivationSession = jvtActivationSession;
    }
    
    private Document getDocument(Node aNode) {
        return aNode.getOwnerDocument();
        /*
        while (aNode!=null && aNode.getNodeType()!=Node.DOCUMENT_TYPE) {
            aNode = aNode.getParent();
        }
        return aNode;
         **/
    }

    private NodeList getChildNodes(Element anElement, String nsURI, String tag) {
        if (nsURI == null || nsURI.equals(NO_NS)) {
            return anElement.getElementsByTagName(tag);
        } else {
            return anElement.getElementsByTagNameNS(nsURI, tag);
        }
    }

    private void createLog(String subSystem) {
        myLog = null;
        String loggingVersion = "";
        // First, try to use WLS 6.0 facility
        
        try {
            Class wls6Log = Class.forName("weblogic.logging.NonCatalogLogger");
            // if there is no exception thrown, this bean is running under WLS6, so that logging
            // facility can be used
            myLog = (BeaTrace)Class.forName("com.nokia.oss.ossj.common.ri.BeaTrace60").newInstance();
            loggingVersion = "WebLogic 6.0 logging classes";
        } catch (ClassNotFoundException cnfe) {
            //VP System.out.println(cnfe.toString());
        } catch (InstantiationException ie) {
            //VP System.out.println(ie.toString());
        } catch (IllegalAccessException iae) {
            //VP System.out.println(iae.toString());
        }
        if (myLog == null) {
            // if this is not WLS6.0, simulate logging
            myLog = new BeaTraceSimulator();
            loggingVersion = "Bea logging simulator";
        }
        myLog.setSubSystem(subSystem);
        if (isLoggingEnabled) myLog.log("Created new logger, using "+loggingVersion);
    }
    
}
