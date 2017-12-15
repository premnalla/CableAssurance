/**
 * CmCurrentPerformanceT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmCurrentPerformanceT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT cmPerformance;

    private java.lang.String recordedTime;

    public CmCurrentPerformanceT() {
    }

    public CmCurrentPerformanceT(
           com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT cmPerformance,
           java.lang.String recordedTime) {
           this.cmPerformance = cmPerformance;
           this.recordedTime = recordedTime;
    }


    /**
     * Gets the cmPerformance value for this CmCurrentPerformanceT.
     * 
     * @return cmPerformance
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT getCmPerformance() {
        return cmPerformance;
    }


    /**
     * Sets the cmPerformance value for this CmCurrentPerformanceT.
     * 
     * @param cmPerformance
     */
    public void setCmPerformance(com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT cmPerformance) {
        this.cmPerformance = cmPerformance;
    }


    /**
     * Gets the recordedTime value for this CmCurrentPerformanceT.
     * 
     * @return recordedTime
     */
    public java.lang.String getRecordedTime() {
        return recordedTime;
    }


    /**
     * Sets the recordedTime value for this CmCurrentPerformanceT.
     * 
     * @param recordedTime
     */
    public void setRecordedTime(java.lang.String recordedTime) {
        this.recordedTime = recordedTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmCurrentPerformanceT)) return false;
        CmCurrentPerformanceT other = (CmCurrentPerformanceT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmPerformance==null && other.getCmPerformance()==null) || 
             (this.cmPerformance!=null &&
              this.cmPerformance.equals(other.getCmPerformance()))) &&
            ((this.recordedTime==null && other.getRecordedTime()==null) || 
             (this.recordedTime!=null &&
              this.recordedTime.equals(other.getRecordedTime())));
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
        if (getCmPerformance() != null) {
            _hashCode += getCmPerformance().hashCode();
        }
        if (getRecordedTime() != null) {
            _hashCode += getRecordedTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmCurrentPerformanceT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmCurrentPerformanceT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmPerformance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmPerformance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordedTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recordedTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
