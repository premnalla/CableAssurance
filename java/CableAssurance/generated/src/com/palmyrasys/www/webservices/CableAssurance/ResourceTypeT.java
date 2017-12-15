/**
 * ResourceTypeT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class ResourceTypeT implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ResourceTypeT(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CMTS = "CMTS";
    public static final java.lang.String _CMS = "CMS";
    public static final java.lang.String _Channel = "Channel";
    public static final java.lang.String _HFC = "HFC";
    public static final java.lang.String _CM = "CM";
    public static final java.lang.String _MTA = "MTA";
    public static final ResourceTypeT CMTS = new ResourceTypeT(_CMTS);
    public static final ResourceTypeT CMS = new ResourceTypeT(_CMS);
    public static final ResourceTypeT Channel = new ResourceTypeT(_Channel);
    public static final ResourceTypeT HFC = new ResourceTypeT(_HFC);
    public static final ResourceTypeT CM = new ResourceTypeT(_CM);
    public static final ResourceTypeT MTA = new ResourceTypeT(_MTA);
    public java.lang.String getValue() { return _value_;}
    public static ResourceTypeT fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ResourceTypeT enumeration = (ResourceTypeT)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ResourceTypeT fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ResourceTypeT.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResourceTypeT"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
