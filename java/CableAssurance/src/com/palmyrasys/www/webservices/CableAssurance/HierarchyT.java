/**
 * HierarchyT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class HierarchyT implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HierarchyT(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Enterprise = "Enterprise";
    public static final java.lang.String _Region = "Region";
    public static final java.lang.String _Market = "Market";
    public static final java.lang.String _Blade = "Blade";
    public static final java.lang.String _CMTS = "CMTS";
    public static final java.lang.String _Channel = "Channel";
    public static final java.lang.String _HFC = "HFC";
    public static final java.lang.String _CM = "CM";
    public static final java.lang.String _MTA = "MTA";
    public static final java.lang.String _CMS = "CMS";
    public static final HierarchyT Enterprise = new HierarchyT(_Enterprise);
    public static final HierarchyT Region = new HierarchyT(_Region);
    public static final HierarchyT Market = new HierarchyT(_Market);
    public static final HierarchyT Blade = new HierarchyT(_Blade);
    public static final HierarchyT CMTS = new HierarchyT(_CMTS);
    public static final HierarchyT Channel = new HierarchyT(_Channel);
    public static final HierarchyT HFC = new HierarchyT(_HFC);
    public static final HierarchyT CM = new HierarchyT(_CM);
    public static final HierarchyT MTA = new HierarchyT(_MTA);
    public static final HierarchyT CMS = new HierarchyT(_CMS);
    public java.lang.String getValue() { return _value_;}
    public static HierarchyT fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        HierarchyT enumeration = (HierarchyT)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static HierarchyT fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(HierarchyT.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HierarchyT"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}