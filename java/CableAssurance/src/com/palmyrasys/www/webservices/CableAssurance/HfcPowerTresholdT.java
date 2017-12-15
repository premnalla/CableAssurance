/**
 * HfcPowerTresholdT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class HfcPowerTresholdT  implements java.io.Serializable {
    private java.lang.String thresholdCount;

    private java.lang.String detectionWindow;

    public HfcPowerTresholdT() {
    }

    public HfcPowerTresholdT(
           java.lang.String thresholdCount,
           java.lang.String detectionWindow) {
           this.thresholdCount = thresholdCount;
           this.detectionWindow = detectionWindow;
    }


    /**
     * Gets the thresholdCount value for this HfcPowerTresholdT.
     * 
     * @return thresholdCount
     */
    public java.lang.String getThresholdCount() {
        return thresholdCount;
    }


    /**
     * Sets the thresholdCount value for this HfcPowerTresholdT.
     * 
     * @param thresholdCount
     */
    public void setThresholdCount(java.lang.String thresholdCount) {
        this.thresholdCount = thresholdCount;
    }


    /**
     * Gets the detectionWindow value for this HfcPowerTresholdT.
     * 
     * @return detectionWindow
     */
    public java.lang.String getDetectionWindow() {
        return detectionWindow;
    }


    /**
     * Sets the detectionWindow value for this HfcPowerTresholdT.
     * 
     * @param detectionWindow
     */
    public void setDetectionWindow(java.lang.String detectionWindow) {
        this.detectionWindow = detectionWindow;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HfcPowerTresholdT)) return false;
        HfcPowerTresholdT other = (HfcPowerTresholdT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.thresholdCount==null && other.getThresholdCount()==null) || 
             (this.thresholdCount!=null &&
              this.thresholdCount.equals(other.getThresholdCount()))) &&
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
        if (getThresholdCount() != null) {
            _hashCode += getThresholdCount().hashCode();
        }
        if (getDetectionWindow() != null) {
            _hashCode += getDetectionWindow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HfcPowerTresholdT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcPowerTresholdT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thresholdCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "thresholdCount"));
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
