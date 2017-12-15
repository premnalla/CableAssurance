/**
 * CurrentAlarmDetailsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import java.math.BigInteger;

import com.palmyrasyscorp.db.tables.CurrentAlarm;

public class CurrentAlarmDetailsT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT currentAlarm;

    private java.lang.String alarmDetails;

    public CurrentAlarmDetailsT() {
    }

    public CurrentAlarmDetailsT(
           com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT currentAlarm,
           java.lang.String alarmDetails) {
           this.currentAlarm = currentAlarm;
           this.alarmDetails = alarmDetails;
    }

	/**
	 * @author Prem
	 * @param dbAlarm
	 */
	public CurrentAlarmDetailsT(CurrentAlarm dbAlarm) {
		this.currentAlarm = new CurrentAlarmT(dbAlarm);
		this.alarmDetails = dbAlarm.getAlarmDetails();
	}

    /**
     * Gets the currentAlarm value for this CurrentAlarmDetailsT.
     * 
     * @return currentAlarm
     */
    public com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT getCurrentAlarm() {
        return currentAlarm;
    }


    /**
     * Sets the currentAlarm value for this CurrentAlarmDetailsT.
     * 
     * @param currentAlarm
     */
    public void setCurrentAlarm(com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT currentAlarm) {
        this.currentAlarm = currentAlarm;
    }


    /**
     * Gets the alarmDetails value for this CurrentAlarmDetailsT.
     * 
     * @return alarmDetails
     */
    public java.lang.String getAlarmDetails() {
        return alarmDetails;
    }


    /**
     * Sets the alarmDetails value for this CurrentAlarmDetailsT.
     * 
     * @param alarmDetails
     */
    public void setAlarmDetails(java.lang.String alarmDetails) {
        this.alarmDetails = alarmDetails;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurrentAlarmDetailsT)) return false;
        CurrentAlarmDetailsT other = (CurrentAlarmDetailsT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currentAlarm==null && other.getCurrentAlarm()==null) || 
             (this.currentAlarm!=null &&
              this.currentAlarm.equals(other.getCurrentAlarm()))) &&
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
        if (getCurrentAlarm() != null) {
            _hashCode += getCurrentAlarm().hashCode();
        }
        if (getAlarmDetails() != null) {
            _hashCode += getAlarmDetails().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurrentAlarmDetailsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmDetailsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentAlarm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentAlarm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CurrentAlarmT"));
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
