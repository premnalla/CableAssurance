/**
 * MtaPingStatusHistoryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import java.math.BigInteger;

import com.palmyrasyscorp.db.tables.*;

public class MtaPingStatusHistoryT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT pingStatus;

    private java.math.BigInteger timeSec;

    public MtaPingStatusHistoryT() {
    }

    public MtaPingStatusHistoryT(
           com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT pingStatus,
           java.math.BigInteger timeSec) {
           this.pingStatus = pingStatus;
           this.timeSec = timeSec;
    }

    /**
     * @author Prem
     * @param db
     */
    public MtaPingStatusHistoryT(MtaPingHistory db) {
    	this.pingStatus = new MtaPingStatusT(db);
    	this.timeSec = new BigInteger(db.getTimeSec().toString());
    }

    /**
     * Gets the pingStatus value for this MtaPingStatusHistoryT.
     * 
     * @return pingStatus
     */
    public com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT getPingStatus() {
        return pingStatus;
    }


    /**
     * Sets the pingStatus value for this MtaPingStatusHistoryT.
     * 
     * @param pingStatus
     */
    public void setPingStatus(com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT pingStatus) {
        this.pingStatus = pingStatus;
    }


    /**
     * Gets the timeSec value for this MtaPingStatusHistoryT.
     * 
     * @return timeSec
     */
    public java.math.BigInteger getTimeSec() {
        return timeSec;
    }


    /**
     * Sets the timeSec value for this MtaPingStatusHistoryT.
     * 
     * @param timeSec
     */
    public void setTimeSec(java.math.BigInteger timeSec) {
        this.timeSec = timeSec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaPingStatusHistoryT)) return false;
        MtaPingStatusHistoryT other = (MtaPingStatusHistoryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pingStatus==null && other.getPingStatus()==null) || 
             (this.pingStatus!=null &&
              this.pingStatus.equals(other.getPingStatus()))) &&
            ((this.timeSec==null && other.getTimeSec()==null) || 
             (this.timeSec!=null &&
              this.timeSec.equals(other.getTimeSec())));
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
        if (getPingStatus() != null) {
            _hashCode += getPingStatus().hashCode();
        }
        if (getTimeSec() != null) {
            _hashCode += getTimeSec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaPingStatusHistoryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaPingStatusHistoryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pingStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pingStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaPingStatusT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeSec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeSec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
