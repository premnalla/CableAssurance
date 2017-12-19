/**
 * LocalSystemType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public class LocalSystemType  implements java.io.Serializable {
    private com.nds.www.wsdl.CableAssurance.SystemTypeType systemType;

    public LocalSystemType() {
    }

    public LocalSystemType(
           com.nds.www.wsdl.CableAssurance.SystemTypeType systemType) {
           this.systemType = systemType;
    }


    /**
     * Gets the systemType value for this LocalSystemType.
     * 
     * @return systemType
     */
    public com.nds.www.wsdl.CableAssurance.SystemTypeType getSystemType() {
        return systemType;
    }


    /**
     * Sets the systemType value for this LocalSystemType.
     * 
     * @param systemType
     */
    public void setSystemType(com.nds.www.wsdl.CableAssurance.SystemTypeType systemType) {
        this.systemType = systemType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LocalSystemType)) return false;
        LocalSystemType other = (LocalSystemType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.systemType==null && other.getSystemType()==null) || 
             (this.systemType!=null &&
              this.systemType.equals(other.getSystemType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSystemType() != null) {
            _hashCode += getSystemType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LocalSystemType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "localSystemType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "systemTypeType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
