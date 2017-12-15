/**
 * AlarmBasedStatusThresholdT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class AlarmBasedStatusThresholdT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT color;

    private java.lang.String alarmType;

    private java.lang.String alarmState;

    public AlarmBasedStatusThresholdT() {
    }

    public AlarmBasedStatusThresholdT(
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT color,
           java.lang.String alarmType,
           java.lang.String alarmState) {
           this.color = color;
           this.alarmType = alarmType;
           this.alarmState = alarmState;
    }


    /**
     * Gets the color value for this AlarmBasedStatusThresholdT.
     * 
     * @return color
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getColor() {
        return color;
    }


    /**
     * Sets the color value for this AlarmBasedStatusThresholdT.
     * 
     * @param color
     */
    public void setColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT color) {
        this.color = color;
    }


    /**
     * Gets the alarmType value for this AlarmBasedStatusThresholdT.
     * 
     * @return alarmType
     */
    public java.lang.String getAlarmType() {
        return alarmType;
    }


    /**
     * Sets the alarmType value for this AlarmBasedStatusThresholdT.
     * 
     * @param alarmType
     */
    public void setAlarmType(java.lang.String alarmType) {
        this.alarmType = alarmType;
    }


    /**
     * Gets the alarmState value for this AlarmBasedStatusThresholdT.
     * 
     * @return alarmState
     */
    public java.lang.String getAlarmState() {
        return alarmState;
    }


    /**
     * Sets the alarmState value for this AlarmBasedStatusThresholdT.
     * 
     * @param alarmState
     */
    public void setAlarmState(java.lang.String alarmState) {
        this.alarmState = alarmState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AlarmBasedStatusThresholdT)) return false;
        AlarmBasedStatusThresholdT other = (AlarmBasedStatusThresholdT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.color==null && other.getColor()==null) || 
             (this.color!=null &&
              this.color.equals(other.getColor()))) &&
            ((this.alarmType==null && other.getAlarmType()==null) || 
             (this.alarmType!=null &&
              this.alarmType.equals(other.getAlarmType()))) &&
            ((this.alarmState==null && other.getAlarmState()==null) || 
             (this.alarmState!=null &&
              this.alarmState.equals(other.getAlarmState())));
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
        if (getColor() != null) {
            _hashCode += getColor().hashCode();
        }
        if (getAlarmType() != null) {
            _hashCode += getAlarmType().hashCode();
        }
        if (getAlarmState() != null) {
            _hashCode += getAlarmState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AlarmBasedStatusThresholdT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AlarmBasedStatusThresholdT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("color");
        elemField.setXmlName(new javax.xml.namespace.QName("", "color"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmState"));
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
