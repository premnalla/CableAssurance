/**
 * ApplicationDomainTypeT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationDomainTypeT implements java.io.Serializable {

	private static Log log = LogFactory.getLog(ApplicationDomainTypeT.class);

    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ApplicationDomainTypeT(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    /**
     * @author Prem
     * @param in
     * @return
     */
    public static final ApplicationDomainTypeT Get(String in) {
    	ApplicationDomainTypeT ret = null;
    
    	if (in.equals(_value1)) {
    		ret = value1;
    	} else if (in.equals(_value2)) {
    		ret = value2;
    	} else if (in.equals(_value3)) {
    		ret = value3;
    	} else if (in.equals(_value4)) {
    		ret = value4;
    	} else {
    		// 
    		ret = value1;
    	}
    	
    	return (ret);
    }
    
    public static final java.lang.String _value1 = "Alarm";
    public static final java.lang.String _value2 = "CSR Portal";
    public static final java.lang.String _value3 = "System Administration";
    public static final java.lang.String _value4 = "User Administration";
    public static final ApplicationDomainTypeT value1 = new ApplicationDomainTypeT(_value1);
    public static final ApplicationDomainTypeT value2 = new ApplicationDomainTypeT(_value2);
    public static final ApplicationDomainTypeT value3 = new ApplicationDomainTypeT(_value3);
    public static final ApplicationDomainTypeT value4 = new ApplicationDomainTypeT(_value4);
    public java.lang.String getValue() { return _value_;}
    public static ApplicationDomainTypeT fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ApplicationDomainTypeT enumeration = (ApplicationDomainTypeT)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ApplicationDomainTypeT fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApplicationDomainTypeT.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ApplicationDomainTypeT"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
