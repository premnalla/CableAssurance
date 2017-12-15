/**
 * CmPerformanceHistoryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import java.math.BigInteger;

import com.palmyrasyscorp.db.tables.*;

public class CmPerformanceHistoryT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT cmPerformance;

    private java.math.BigInteger timeSec;

    public CmPerformanceHistoryT() {
    }

    public CmPerformanceHistoryT(
           com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT cmPerformance,
           java.math.BigInteger timeSec) {
           this.cmPerformance = cmPerformance;
           this.timeSec = timeSec;
    }


    /**
     * 
     * @param dbPerf
     */
    public CmPerformanceHistoryT(CmPerformanceHistory dbPerf) {
    	this.cmPerformance = new CmPerformanceT(dbPerf);
    	this.timeSec = new BigInteger(dbPerf.getTimeSec().toString());
    }
    
    
    /**
     * Gets the cmPerformance value for this CmPerformanceHistoryT.
     * 
     * @return cmPerformance
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT getCmPerformance() {
        return cmPerformance;
    }


    /**
     * Sets the cmPerformance value for this CmPerformanceHistoryT.
     * 
     * @param cmPerformance
     */
    public void setCmPerformance(com.palmyrasys.www.webservices.CableAssurance.CmPerformanceT cmPerformance) {
        this.cmPerformance = cmPerformance;
    }


    /**
     * Gets the timeSec value for this CmPerformanceHistoryT.
     * 
     * @return timeSec
     */
    public java.math.BigInteger getTimeSec() {
        return timeSec;
    }


    /**
     * Sets the timeSec value for this CmPerformanceHistoryT.
     * 
     * @param timeSec
     */
    public void setTimeSec(java.math.BigInteger timeSec) {
        this.timeSec = timeSec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmPerformanceHistoryT)) return false;
        CmPerformanceHistoryT other = (CmPerformanceHistoryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmPerformance==null && other.getCmPerformance()==null) || 
             (this.cmPerformance!=null &&
              this.cmPerformance.equals(other.getCmPerformance()))) &&
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
        if (getCmPerformance() != null) {
            _hashCode += getCmPerformance().hashCode();
        }
        if (getTimeSec() != null) {
            _hashCode += getTimeSec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmPerformanceHistoryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceHistoryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmPerformance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmPerformance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceT"));
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
