/**
 * HistoricalAlarmsRespT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class HistoricalAlarmsRespT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] alarms;

    private com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState;

    public HistoricalAlarmsRespT() {
    }

    public HistoricalAlarmsRespT(
           com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] alarms,
           com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) {
           this.alarms = alarms;
           this.queryState = queryState;
    }


    /**
     * Gets the alarms value for this HistoricalAlarmsRespT.
     * 
     * @return alarms
     */
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] getAlarms() {
        return alarms;
    }


    /**
     * Sets the alarms value for this HistoricalAlarmsRespT.
     * 
     * @param alarms
     */
    public void setAlarms(com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT[] alarms) {
        this.alarms = alarms;
    }


    /**
     * Gets the queryState value for this HistoricalAlarmsRespT.
     * 
     * @return queryState
     */
    public com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] getQueryState() {
        return queryState;
    }


    /**
     * Sets the queryState value for this HistoricalAlarmsRespT.
     * 
     * @param queryState
     */
    public void setQueryState(com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) {
        this.queryState = queryState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HistoricalAlarmsRespT)) return false;
        HistoricalAlarmsRespT other = (HistoricalAlarmsRespT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alarms==null && other.getAlarms()==null) || 
             (this.alarms!=null &&
              java.util.Arrays.equals(this.alarms, other.getAlarms()))) &&
            ((this.queryState==null && other.getQueryState()==null) || 
             (this.queryState!=null &&
              java.util.Arrays.equals(this.queryState, other.getQueryState())));
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
        if (getAlarms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlarms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlarms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getQueryState() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQueryState());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQueryState(), i);
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
        new org.apache.axis.description.TypeDesc(HistoricalAlarmsRespT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmsRespT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"));
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
