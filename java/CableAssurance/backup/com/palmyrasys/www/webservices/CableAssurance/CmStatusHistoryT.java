/**
 * CmStatusHistoryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import java.math.BigInteger;

import com.palmyrasyscorp.db.tables.*;

public class CmStatusHistoryT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CmStatusT cmStatus;

    private java.math.BigInteger timeSec;

    public CmStatusHistoryT() {
    }

    public CmStatusHistoryT(
           com.palmyrasys.www.webservices.CableAssurance.CmStatusT cmStatus,
           java.math.BigInteger timeSec) {
           this.cmStatus = cmStatus;
           this.timeSec = timeSec;
    }


    public CmStatusHistoryT(CmStatusHistory dbSh) {
    	this.cmStatus = new CmStatusT(dbSh);
    	this.timeSec = new BigInteger(dbSh.getTimeSec().toString());
    }
    
    /**
     * Gets the cmStatus value for this CmStatusHistoryT.
     * 
     * @return cmStatus
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmStatusT getCmStatus() {
        return cmStatus;
    }


    /**
     * Sets the cmStatus value for this CmStatusHistoryT.
     * 
     * @param cmStatus
     */
    public void setCmStatus(com.palmyrasys.www.webservices.CableAssurance.CmStatusT cmStatus) {
        this.cmStatus = cmStatus;
    }


    /**
     * Gets the timeSec value for this CmStatusHistoryT.
     * 
     * @return timeSec
     */
    public java.math.BigInteger getTimeSec() {
        return timeSec;
    }


    /**
     * Sets the timeSec value for this CmStatusHistoryT.
     * 
     * @param timeSec
     */
    public void setTimeSec(java.math.BigInteger timeSec) {
        this.timeSec = timeSec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmStatusHistoryT)) return false;
        CmStatusHistoryT other = (CmStatusHistoryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmStatus==null && other.getCmStatus()==null) || 
             (this.cmStatus!=null &&
              this.cmStatus.equals(other.getCmStatus()))) &&
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
        if (getCmStatus() != null) {
            _hashCode += getCmStatus().hashCode();
        }
        if (getTimeSec() != null) {
            _hashCode += getTimeSec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmStatusHistoryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusHistoryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmStatusT"));
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
