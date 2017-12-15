/**
 * SoakWindowT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class SoakWindowT  implements java.io.Serializable {
    private java.lang.String soakWindow_1_StartTime;

    private java.lang.String soakWindow_1_Duration;

    private java.lang.String soakWindow_2_StartTime;

    private java.lang.String soakWindow_2_Duration;

    public SoakWindowT() {
    }

    public SoakWindowT(
           java.lang.String soakWindow_1_StartTime,
           java.lang.String soakWindow_1_Duration,
           java.lang.String soakWindow_2_StartTime,
           java.lang.String soakWindow_2_Duration) {
           this.soakWindow_1_StartTime = soakWindow_1_StartTime;
           this.soakWindow_1_Duration = soakWindow_1_Duration;
           this.soakWindow_2_StartTime = soakWindow_2_StartTime;
           this.soakWindow_2_Duration = soakWindow_2_Duration;
    }


    /**
     * Gets the soakWindow_1_StartTime value for this SoakWindowT.
     * 
     * @return soakWindow_1_StartTime
     */
    public java.lang.String getSoakWindow_1_StartTime() {
        return soakWindow_1_StartTime;
    }


    /**
     * Sets the soakWindow_1_StartTime value for this SoakWindowT.
     * 
     * @param soakWindow_1_StartTime
     */
    public void setSoakWindow_1_StartTime(java.lang.String soakWindow_1_StartTime) {
        this.soakWindow_1_StartTime = soakWindow_1_StartTime;
    }


    /**
     * Gets the soakWindow_1_Duration value for this SoakWindowT.
     * 
     * @return soakWindow_1_Duration
     */
    public java.lang.String getSoakWindow_1_Duration() {
        return soakWindow_1_Duration;
    }


    /**
     * Sets the soakWindow_1_Duration value for this SoakWindowT.
     * 
     * @param soakWindow_1_Duration
     */
    public void setSoakWindow_1_Duration(java.lang.String soakWindow_1_Duration) {
        this.soakWindow_1_Duration = soakWindow_1_Duration;
    }


    /**
     * Gets the soakWindow_2_StartTime value for this SoakWindowT.
     * 
     * @return soakWindow_2_StartTime
     */
    public java.lang.String getSoakWindow_2_StartTime() {
        return soakWindow_2_StartTime;
    }


    /**
     * Sets the soakWindow_2_StartTime value for this SoakWindowT.
     * 
     * @param soakWindow_2_StartTime
     */
    public void setSoakWindow_2_StartTime(java.lang.String soakWindow_2_StartTime) {
        this.soakWindow_2_StartTime = soakWindow_2_StartTime;
    }


    /**
     * Gets the soakWindow_2_Duration value for this SoakWindowT.
     * 
     * @return soakWindow_2_Duration
     */
    public java.lang.String getSoakWindow_2_Duration() {
        return soakWindow_2_Duration;
    }


    /**
     * Sets the soakWindow_2_Duration value for this SoakWindowT.
     * 
     * @param soakWindow_2_Duration
     */
    public void setSoakWindow_2_Duration(java.lang.String soakWindow_2_Duration) {
        this.soakWindow_2_Duration = soakWindow_2_Duration;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SoakWindowT)) return false;
        SoakWindowT other = (SoakWindowT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.soakWindow_1_StartTime==null && other.getSoakWindow_1_StartTime()==null) || 
             (this.soakWindow_1_StartTime!=null &&
              this.soakWindow_1_StartTime.equals(other.getSoakWindow_1_StartTime()))) &&
            ((this.soakWindow_1_Duration==null && other.getSoakWindow_1_Duration()==null) || 
             (this.soakWindow_1_Duration!=null &&
              this.soakWindow_1_Duration.equals(other.getSoakWindow_1_Duration()))) &&
            ((this.soakWindow_2_StartTime==null && other.getSoakWindow_2_StartTime()==null) || 
             (this.soakWindow_2_StartTime!=null &&
              this.soakWindow_2_StartTime.equals(other.getSoakWindow_2_StartTime()))) &&
            ((this.soakWindow_2_Duration==null && other.getSoakWindow_2_Duration()==null) || 
             (this.soakWindow_2_Duration!=null &&
              this.soakWindow_2_Duration.equals(other.getSoakWindow_2_Duration())));
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
        if (getSoakWindow_1_StartTime() != null) {
            _hashCode += getSoakWindow_1_StartTime().hashCode();
        }
        if (getSoakWindow_1_Duration() != null) {
            _hashCode += getSoakWindow_1_Duration().hashCode();
        }
        if (getSoakWindow_2_StartTime() != null) {
            _hashCode += getSoakWindow_2_StartTime().hashCode();
        }
        if (getSoakWindow_2_Duration() != null) {
            _hashCode += getSoakWindow_2_Duration().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SoakWindowT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SoakWindowT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soakWindow_1_StartTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soakWindow_1_StartTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soakWindow_1_Duration");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soakWindow_1_Duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soakWindow_2_StartTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soakWindow_2_StartTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soakWindow_2_Duration");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soakWindow_2_Duration"));
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
