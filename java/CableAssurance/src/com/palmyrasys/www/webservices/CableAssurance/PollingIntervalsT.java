/**
 * PollingIntervalsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class PollingIntervalsT  implements java.io.Serializable {
    private java.lang.String cmtsPollInterval;

    private java.lang.String cmPollInterval;

    private java.lang.String mtaPollInterval;

    private java.lang.String mtaPingInterval;

    public PollingIntervalsT() {
    }

    public PollingIntervalsT(
           java.lang.String cmtsPollInterval,
           java.lang.String cmPollInterval,
           java.lang.String mtaPollInterval,
           java.lang.String mtaPingInterval) {
           this.cmtsPollInterval = cmtsPollInterval;
           this.cmPollInterval = cmPollInterval;
           this.mtaPollInterval = mtaPollInterval;
           this.mtaPingInterval = mtaPingInterval;
    }


    /**
     * Gets the cmtsPollInterval value for this PollingIntervalsT.
     * 
     * @return cmtsPollInterval
     */
    public java.lang.String getCmtsPollInterval() {
        return cmtsPollInterval;
    }


    /**
     * Sets the cmtsPollInterval value for this PollingIntervalsT.
     * 
     * @param cmtsPollInterval
     */
    public void setCmtsPollInterval(java.lang.String cmtsPollInterval) {
        this.cmtsPollInterval = cmtsPollInterval;
    }


    /**
     * Gets the cmPollInterval value for this PollingIntervalsT.
     * 
     * @return cmPollInterval
     */
    public java.lang.String getCmPollInterval() {
        return cmPollInterval;
    }


    /**
     * Sets the cmPollInterval value for this PollingIntervalsT.
     * 
     * @param cmPollInterval
     */
    public void setCmPollInterval(java.lang.String cmPollInterval) {
        this.cmPollInterval = cmPollInterval;
    }


    /**
     * Gets the mtaPollInterval value for this PollingIntervalsT.
     * 
     * @return mtaPollInterval
     */
    public java.lang.String getMtaPollInterval() {
        return mtaPollInterval;
    }


    /**
     * Sets the mtaPollInterval value for this PollingIntervalsT.
     * 
     * @param mtaPollInterval
     */
    public void setMtaPollInterval(java.lang.String mtaPollInterval) {
        this.mtaPollInterval = mtaPollInterval;
    }


    /**
     * Gets the mtaPingInterval value for this PollingIntervalsT.
     * 
     * @return mtaPingInterval
     */
    public java.lang.String getMtaPingInterval() {
        return mtaPingInterval;
    }


    /**
     * Sets the mtaPingInterval value for this PollingIntervalsT.
     * 
     * @param mtaPingInterval
     */
    public void setMtaPingInterval(java.lang.String mtaPingInterval) {
        this.mtaPingInterval = mtaPingInterval;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PollingIntervalsT)) return false;
        PollingIntervalsT other = (PollingIntervalsT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmtsPollInterval==null && other.getCmtsPollInterval()==null) || 
             (this.cmtsPollInterval!=null &&
              this.cmtsPollInterval.equals(other.getCmtsPollInterval()))) &&
            ((this.cmPollInterval==null && other.getCmPollInterval()==null) || 
             (this.cmPollInterval!=null &&
              this.cmPollInterval.equals(other.getCmPollInterval()))) &&
            ((this.mtaPollInterval==null && other.getMtaPollInterval()==null) || 
             (this.mtaPollInterval!=null &&
              this.mtaPollInterval.equals(other.getMtaPollInterval()))) &&
            ((this.mtaPingInterval==null && other.getMtaPingInterval()==null) || 
             (this.mtaPingInterval!=null &&
              this.mtaPingInterval.equals(other.getMtaPingInterval())));
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
        if (getCmtsPollInterval() != null) {
            _hashCode += getCmtsPollInterval().hashCode();
        }
        if (getCmPollInterval() != null) {
            _hashCode += getCmPollInterval().hashCode();
        }
        if (getMtaPollInterval() != null) {
            _hashCode += getMtaPollInterval().hashCode();
        }
        if (getMtaPingInterval() != null) {
            _hashCode += getMtaPingInterval().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PollingIntervalsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "PollingIntervalsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmtsPollInterval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsPollInterval"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmPollInterval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmPollInterval"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaPollInterval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaPollInterval"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaPingInterval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaPingInterval"));
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
