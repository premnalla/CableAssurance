/**
 * ResultBatchT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class ResultBatchT  implements java.io.Serializable {
    private short fromIndex;

    private short toIndex;

    public ResultBatchT() {
    }

    public ResultBatchT(
           short fromIndex,
           short toIndex) {
           this.fromIndex = fromIndex;
           this.toIndex = toIndex;
    }


    /**
     * Gets the fromIndex value for this ResultBatchT.
     * 
     * @return fromIndex
     */
    public short getFromIndex() {
        return fromIndex;
    }


    /**
     * Sets the fromIndex value for this ResultBatchT.
     * 
     * @param fromIndex
     */
    public void setFromIndex(short fromIndex) {
        this.fromIndex = fromIndex;
    }


    /**
     * Gets the toIndex value for this ResultBatchT.
     * 
     * @return toIndex
     */
    public short getToIndex() {
        return toIndex;
    }


    /**
     * Sets the toIndex value for this ResultBatchT.
     * 
     * @param toIndex
     */
    public void setToIndex(short toIndex) {
        this.toIndex = toIndex;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultBatchT)) return false;
        ResultBatchT other = (ResultBatchT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.fromIndex == other.getFromIndex() &&
            this.toIndex == other.getToIndex();
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
        _hashCode += getFromIndex();
        _hashCode += getToIndex();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultBatchT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ResultBatchT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fromIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "toIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
