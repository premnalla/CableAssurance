/**
 * MtaProvStatusHistoryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import java.math.BigInteger;

import com.palmyrasyscorp.db.tables.*;

public class MtaProvStatusHistoryT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT provStatus;

    private java.math.BigInteger timeSec;

    public MtaProvStatusHistoryT() {
    }

    public MtaProvStatusHistoryT(
           com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT provStatus,
           java.math.BigInteger timeSec) {
           this.provStatus = provStatus;
           this.timeSec = timeSec;
    }


    /**
     * @author Prem
     * @param db
     */
    public MtaProvStatusHistoryT(MtaProvisionedHistory db) {
    	this.provStatus = new MtaProvStatusT(db);
    	this.timeSec = new BigInteger(db.getTimeSec().toString());
    }
    
    /**
     * Gets the provStatus value for this MtaProvStatusHistoryT.
     * 
     * @return provStatus
     */
    public com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT getProvStatus() {
        return provStatus;
    }


    /**
     * Sets the provStatus value for this MtaProvStatusHistoryT.
     * 
     * @param provStatus
     */
    public void setProvStatus(com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT provStatus) {
        this.provStatus = provStatus;
    }


    /**
     * Gets the timeSec value for this MtaProvStatusHistoryT.
     * 
     * @return timeSec
     */
    public java.math.BigInteger getTimeSec() {
        return timeSec;
    }


    /**
     * Sets the timeSec value for this MtaProvStatusHistoryT.
     * 
     * @param timeSec
     */
    public void setTimeSec(java.math.BigInteger timeSec) {
        this.timeSec = timeSec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaProvStatusHistoryT)) return false;
        MtaProvStatusHistoryT other = (MtaProvStatusHistoryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.provStatus==null && other.getProvStatus()==null) || 
             (this.provStatus!=null &&
              this.provStatus.equals(other.getProvStatus()))) &&
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
        if (getProvStatus() != null) {
            _hashCode += getProvStatus().hashCode();
        }
        if (getTimeSec() != null) {
            _hashCode += getTimeSec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaProvStatusHistoryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaProvStatusHistoryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaProvStatusT"));
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
