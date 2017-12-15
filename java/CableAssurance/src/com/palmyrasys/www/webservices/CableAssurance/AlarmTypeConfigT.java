/**
 * AlarmTypeConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class AlarmTypeConfigT  implements java.io.Serializable {
    private java.lang.String alarmType;

    private com.palmyrasys.www.webservices.CableAssurance.SoakWindowT soakWindow;

    public AlarmTypeConfigT() {
    }

    public AlarmTypeConfigT(
           java.lang.String alarmType,
           com.palmyrasys.www.webservices.CableAssurance.SoakWindowT soakWindow) {
           this.alarmType = alarmType;
           this.soakWindow = soakWindow;
    }


    /**
     * Gets the alarmType value for this AlarmTypeConfigT.
     * 
     * @return alarmType
     */
    public java.lang.String getAlarmType() {
        return alarmType;
    }


    /**
     * Sets the alarmType value for this AlarmTypeConfigT.
     * 
     * @param alarmType
     */
    public void setAlarmType(java.lang.String alarmType) {
        this.alarmType = alarmType;
    }


    /**
     * Gets the soakWindow value for this AlarmTypeConfigT.
     * 
     * @return soakWindow
     */
    public com.palmyrasys.www.webservices.CableAssurance.SoakWindowT getSoakWindow() {
        return soakWindow;
    }


    /**
     * Sets the soakWindow value for this AlarmTypeConfigT.
     * 
     * @param soakWindow
     */
    public void setSoakWindow(com.palmyrasys.www.webservices.CableAssurance.SoakWindowT soakWindow) {
        this.soakWindow = soakWindow;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AlarmTypeConfigT)) return false;
        AlarmTypeConfigT other = (AlarmTypeConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alarmType==null && other.getAlarmType()==null) || 
             (this.alarmType!=null &&
              this.alarmType.equals(other.getAlarmType()))) &&
            ((this.soakWindow==null && other.getSoakWindow()==null) || 
             (this.soakWindow!=null &&
              this.soakWindow.equals(other.getSoakWindow())));
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
        if (getAlarmType() != null) {
            _hashCode += getAlarmType().hashCode();
        }
        if (getSoakWindow() != null) {
            _hashCode += getSoakWindow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AlarmTypeConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AlarmTypeConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soakWindow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soakWindow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SoakWindowT"));
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
