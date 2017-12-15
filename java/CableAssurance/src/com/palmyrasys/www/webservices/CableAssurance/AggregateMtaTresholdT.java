/**
 * AggregateMtaTresholdT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class AggregateMtaTresholdT  implements java.io.Serializable {
    private java.lang.String mtaThresholdCount;

    private java.lang.String detectionWindow;

    public AggregateMtaTresholdT() {
    }

    public AggregateMtaTresholdT(
           java.lang.String mtaThresholdCount,
           java.lang.String detectionWindow) {
           this.mtaThresholdCount = mtaThresholdCount;
           this.detectionWindow = detectionWindow;
    }


    /**
     * Gets the mtaThresholdCount value for this AggregateMtaTresholdT.
     * 
     * @return mtaThresholdCount
     */
    public java.lang.String getMtaThresholdCount() {
        return mtaThresholdCount;
    }


    /**
     * Sets the mtaThresholdCount value for this AggregateMtaTresholdT.
     * 
     * @param mtaThresholdCount
     */
    public void setMtaThresholdCount(java.lang.String mtaThresholdCount) {
        this.mtaThresholdCount = mtaThresholdCount;
    }


    /**
     * Gets the detectionWindow value for this AggregateMtaTresholdT.
     * 
     * @return detectionWindow
     */
    public java.lang.String getDetectionWindow() {
        return detectionWindow;
    }


    /**
     * Sets the detectionWindow value for this AggregateMtaTresholdT.
     * 
     * @param detectionWindow
     */
    public void setDetectionWindow(java.lang.String detectionWindow) {
        this.detectionWindow = detectionWindow;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AggregateMtaTresholdT)) return false;
        AggregateMtaTresholdT other = (AggregateMtaTresholdT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mtaThresholdCount==null && other.getMtaThresholdCount()==null) || 
             (this.mtaThresholdCount!=null &&
              this.mtaThresholdCount.equals(other.getMtaThresholdCount()))) &&
            ((this.detectionWindow==null && other.getDetectionWindow()==null) || 
             (this.detectionWindow!=null &&
              this.detectionWindow.equals(other.getDetectionWindow())));
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
        if (getMtaThresholdCount() != null) {
            _hashCode += getMtaThresholdCount().hashCode();
        }
        if (getDetectionWindow() != null) {
            _hashCode += getDetectionWindow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AggregateMtaTresholdT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AggregateMtaTresholdT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaThresholdCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaThresholdCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detectionWindow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detectionWindow"));
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
