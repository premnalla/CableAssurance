/**
 * MtaAlarmConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class MtaAlarmConfigT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] alarmTypes;

    public MtaAlarmConfigT() {
    }

    public MtaAlarmConfigT(
           com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] alarmTypes) {
           this.alarmTypes = alarmTypes;
    }


    /**
     * Gets the alarmTypes value for this MtaAlarmConfigT.
     * 
     * @return alarmTypes
     */
    public com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] getAlarmTypes() {
        return alarmTypes;
    }


    /**
     * Sets the alarmTypes value for this MtaAlarmConfigT.
     * 
     * @param alarmTypes
     */
    public void setAlarmTypes(com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] alarmTypes) {
        this.alarmTypes = alarmTypes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaAlarmConfigT)) return false;
        MtaAlarmConfigT other = (MtaAlarmConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alarmTypes==null && other.getAlarmTypes()==null) || 
             (this.alarmTypes!=null &&
              java.util.Arrays.equals(this.alarmTypes, other.getAlarmTypes())));
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
        if (getAlarmTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlarmTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlarmTypes(), i);
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
        new org.apache.axis.description.TypeDesc(MtaAlarmConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAlarmConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AlarmTypeConfigT"));
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
