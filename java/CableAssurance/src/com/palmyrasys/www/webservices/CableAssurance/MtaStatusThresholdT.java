/**
 * MtaStatusThresholdT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class MtaStatusThresholdT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.AlarmBasedStatusThresholdT[] alarmThreshArray;

    public MtaStatusThresholdT() {
    }

    public MtaStatusThresholdT(
           com.palmyrasys.www.webservices.CableAssurance.AlarmBasedStatusThresholdT[] alarmThreshArray) {
           this.alarmThreshArray = alarmThreshArray;
    }


    /**
     * Gets the alarmThreshArray value for this MtaStatusThresholdT.
     * 
     * @return alarmThreshArray
     */
    public com.palmyrasys.www.webservices.CableAssurance.AlarmBasedStatusThresholdT[] getAlarmThreshArray() {
        return alarmThreshArray;
    }


    /**
     * Sets the alarmThreshArray value for this MtaStatusThresholdT.
     * 
     * @param alarmThreshArray
     */
    public void setAlarmThreshArray(com.palmyrasys.www.webservices.CableAssurance.AlarmBasedStatusThresholdT[] alarmThreshArray) {
        this.alarmThreshArray = alarmThreshArray;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaStatusThresholdT)) return false;
        MtaStatusThresholdT other = (MtaStatusThresholdT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alarmThreshArray==null && other.getAlarmThreshArray()==null) || 
             (this.alarmThreshArray!=null &&
              java.util.Arrays.equals(this.alarmThreshArray, other.getAlarmThreshArray())));
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
        if (getAlarmThreshArray() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlarmThreshArray());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlarmThreshArray(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaStatusThresholdT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusThresholdT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmThreshArray");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmThreshArray"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AlarmBasedStatusThresholdT"));
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
