/**
 * CmPerformanceT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmPerformanceT  implements java.io.Serializable {
    private short downstreamSNR;

    private short downstreamPower;

    private short upstreamPower;

    private short t1Timeouts;

    private short t2Timeouts;

    private short t3Timeouts;

    private short t4Timeouts;

    public CmPerformanceT() {
    }

    public CmPerformanceT(
           short downstreamSNR,
           short downstreamPower,
           short upstreamPower,
           short t1Timeouts,
           short t2Timeouts,
           short t3Timeouts,
           short t4Timeouts) {
           this.downstreamSNR = downstreamSNR;
           this.downstreamPower = downstreamPower;
           this.upstreamPower = upstreamPower;
           this.t1Timeouts = t1Timeouts;
           this.t2Timeouts = t2Timeouts;
           this.t3Timeouts = t3Timeouts;
           this.t4Timeouts = t4Timeouts;
    }


    /**
     * Gets the downstreamSNR value for this CmPerformanceT.
     * 
     * @return downstreamSNR
     */
    public short getDownstreamSNR() {
        return downstreamSNR;
    }


    /**
     * Sets the downstreamSNR value for this CmPerformanceT.
     * 
     * @param downstreamSNR
     */
    public void setDownstreamSNR(short downstreamSNR) {
        this.downstreamSNR = downstreamSNR;
    }


    /**
     * Gets the downstreamPower value for this CmPerformanceT.
     * 
     * @return downstreamPower
     */
    public short getDownstreamPower() {
        return downstreamPower;
    }


    /**
     * Sets the downstreamPower value for this CmPerformanceT.
     * 
     * @param downstreamPower
     */
    public void setDownstreamPower(short downstreamPower) {
        this.downstreamPower = downstreamPower;
    }


    /**
     * Gets the upstreamPower value for this CmPerformanceT.
     * 
     * @return upstreamPower
     */
    public short getUpstreamPower() {
        return upstreamPower;
    }


    /**
     * Sets the upstreamPower value for this CmPerformanceT.
     * 
     * @param upstreamPower
     */
    public void setUpstreamPower(short upstreamPower) {
        this.upstreamPower = upstreamPower;
    }


    /**
     * Gets the t1Timeouts value for this CmPerformanceT.
     * 
     * @return t1Timeouts
     */
    public short getT1Timeouts() {
        return t1Timeouts;
    }


    /**
     * Sets the t1Timeouts value for this CmPerformanceT.
     * 
     * @param t1Timeouts
     */
    public void setT1Timeouts(short t1Timeouts) {
        this.t1Timeouts = t1Timeouts;
    }


    /**
     * Gets the t2Timeouts value for this CmPerformanceT.
     * 
     * @return t2Timeouts
     */
    public short getT2Timeouts() {
        return t2Timeouts;
    }


    /**
     * Sets the t2Timeouts value for this CmPerformanceT.
     * 
     * @param t2Timeouts
     */
    public void setT2Timeouts(short t2Timeouts) {
        this.t2Timeouts = t2Timeouts;
    }


    /**
     * Gets the t3Timeouts value for this CmPerformanceT.
     * 
     * @return t3Timeouts
     */
    public short getT3Timeouts() {
        return t3Timeouts;
    }


    /**
     * Sets the t3Timeouts value for this CmPerformanceT.
     * 
     * @param t3Timeouts
     */
    public void setT3Timeouts(short t3Timeouts) {
        this.t3Timeouts = t3Timeouts;
    }


    /**
     * Gets the t4Timeouts value for this CmPerformanceT.
     * 
     * @return t4Timeouts
     */
    public short getT4Timeouts() {
        return t4Timeouts;
    }


    /**
     * Sets the t4Timeouts value for this CmPerformanceT.
     * 
     * @param t4Timeouts
     */
    public void setT4Timeouts(short t4Timeouts) {
        this.t4Timeouts = t4Timeouts;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmPerformanceT)) return false;
        CmPerformanceT other = (CmPerformanceT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.downstreamSNR == other.getDownstreamSNR() &&
            this.downstreamPower == other.getDownstreamPower() &&
            this.upstreamPower == other.getUpstreamPower() &&
            this.t1Timeouts == other.getT1Timeouts() &&
            this.t2Timeouts == other.getT2Timeouts() &&
            this.t3Timeouts == other.getT3Timeouts() &&
            this.t4Timeouts == other.getT4Timeouts();
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
        _hashCode += getDownstreamSNR();
        _hashCode += getDownstreamPower();
        _hashCode += getUpstreamPower();
        _hashCode += getT1Timeouts();
        _hashCode += getT2Timeouts();
        _hashCode += getT3Timeouts();
        _hashCode += getT4Timeouts();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmPerformanceT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downstreamSNR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downstreamSNR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downstreamPower");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downstreamPower"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upstreamPower");
        elemField.setXmlName(new javax.xml.namespace.QName("", "upstreamPower"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("t1Timeouts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "t1Timeouts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("t2Timeouts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "t2Timeouts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("t3Timeouts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "t3Timeouts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("t4Timeouts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "t4Timeouts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
