/**
 * HistoricalAlarmDetailsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import com.palmyrasyscorp.db.tables.HistoricalAlarm;

public class HistoricalAlarmDetailsT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT historicalAlarm;

    private java.lang.String alarmDetails;

    public HistoricalAlarmDetailsT() {
    }

    public HistoricalAlarmDetailsT(
           com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT historicalAlarm,
           java.lang.String alarmDetails) {
           this.historicalAlarm = historicalAlarm;
           this.alarmDetails = alarmDetails;
    }

	/**
	 * @author Prem
	 * @param dbAlarm
	 */
	public HistoricalAlarmDetailsT(HistoricalAlarm dbAlarm) {
		this.historicalAlarm = new HistoricalAlarmT(dbAlarm);
		this.alarmDetails = dbAlarm.getAlarmDetails();
	}

    /**
     * Gets the historicalAlarm value for this HistoricalAlarmDetailsT.
     * 
     * @return historicalAlarm
     */
    public com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT getHistoricalAlarm() {
        return historicalAlarm;
    }


    /**
     * Sets the historicalAlarm value for this HistoricalAlarmDetailsT.
     * 
     * @param historicalAlarm
     */
    public void setHistoricalAlarm(com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT historicalAlarm) {
        this.historicalAlarm = historicalAlarm;
    }


    /**
     * Gets the alarmDetails value for this HistoricalAlarmDetailsT.
     * 
     * @return alarmDetails
     */
    public java.lang.String getAlarmDetails() {
        return alarmDetails;
    }


    /**
     * Sets the alarmDetails value for this HistoricalAlarmDetailsT.
     * 
     * @param alarmDetails
     */
    public void setAlarmDetails(java.lang.String alarmDetails) {
        this.alarmDetails = alarmDetails;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HistoricalAlarmDetailsT)) return false;
        HistoricalAlarmDetailsT other = (HistoricalAlarmDetailsT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.historicalAlarm==null && other.getHistoricalAlarm()==null) || 
             (this.historicalAlarm!=null &&
              this.historicalAlarm.equals(other.getHistoricalAlarm()))) &&
            ((this.alarmDetails==null && other.getAlarmDetails()==null) || 
             (this.alarmDetails!=null &&
              this.alarmDetails.equals(other.getAlarmDetails())));
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
        if (getHistoricalAlarm() != null) {
            _hashCode += getHistoricalAlarm().hashCode();
        }
        if (getAlarmDetails() != null) {
            _hashCode += getAlarmDetails().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HistoricalAlarmDetailsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmDetailsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("historicalAlarm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "historicalAlarm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HistoricalAlarmT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmDetails"));
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
