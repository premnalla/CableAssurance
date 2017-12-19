/**
 * ChangeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ibm.w3.schemas.services._2002._11._15.stockquote;

public class ChangeType  implements java.io.Serializable {
    private float dollar;

    private float percent;

    private boolean positive;

    public ChangeType() {
    }

    public ChangeType(
           float dollar,
           float percent,
           boolean positive) {
           this.dollar = dollar;
           this.percent = percent;
           this.positive = positive;
    }


    /**
     * Gets the dollar value for this ChangeType.
     * 
     * @return dollar
     */
    public float getDollar() {
        return dollar;
    }


    /**
     * Sets the dollar value for this ChangeType.
     * 
     * @param dollar
     */
    public void setDollar(float dollar) {
        this.dollar = dollar;
    }


    /**
     * Gets the percent value for this ChangeType.
     * 
     * @return percent
     */
    public float getPercent() {
        return percent;
    }


    /**
     * Sets the percent value for this ChangeType.
     * 
     * @param percent
     */
    public void setPercent(float percent) {
        this.percent = percent;
    }


    /**
     * Gets the positive value for this ChangeType.
     * 
     * @return positive
     */
    public boolean isPositive() {
        return positive;
    }


    /**
     * Sets the positive value for this ChangeType.
     * 
     * @param positive
     */
    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeType)) return false;
        ChangeType other = (ChangeType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.dollar == other.getDollar() &&
            this.percent == other.getPercent() &&
            this.positive == other.isPositive();
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
        _hashCode += new Float(getDollar()).hashCode();
        _hashCode += new Float(getPercent()).hashCode();
        _hashCode += (isPositive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", "changeType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dollar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dollar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("positive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "positive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
