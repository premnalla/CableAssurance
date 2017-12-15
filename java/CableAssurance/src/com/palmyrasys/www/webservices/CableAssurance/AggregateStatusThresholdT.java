/**
 * AggregateStatusThresholdT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class AggregateStatusThresholdT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT color;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT childColor;

    private java.lang.String thresholdPercent;

    public AggregateStatusThresholdT() {
    }

    public AggregateStatusThresholdT(
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT color,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT childColor,
           java.lang.String thresholdPercent) {
           this.color = color;
           this.childColor = childColor;
           this.thresholdPercent = thresholdPercent;
    }


    /**
     * Gets the color value for this AggregateStatusThresholdT.
     * 
     * @return color
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getColor() {
        return color;
    }


    /**
     * Sets the color value for this AggregateStatusThresholdT.
     * 
     * @param color
     */
    public void setColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT color) {
        this.color = color;
    }


    /**
     * Gets the childColor value for this AggregateStatusThresholdT.
     * 
     * @return childColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getChildColor() {
        return childColor;
    }


    /**
     * Sets the childColor value for this AggregateStatusThresholdT.
     * 
     * @param childColor
     */
    public void setChildColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT childColor) {
        this.childColor = childColor;
    }


    /**
     * Gets the thresholdPercent value for this AggregateStatusThresholdT.
     * 
     * @return thresholdPercent
     */
    public java.lang.String getThresholdPercent() {
        return thresholdPercent;
    }


    /**
     * Sets the thresholdPercent value for this AggregateStatusThresholdT.
     * 
     * @param thresholdPercent
     */
    public void setThresholdPercent(java.lang.String thresholdPercent) {
        this.thresholdPercent = thresholdPercent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AggregateStatusThresholdT)) return false;
        AggregateStatusThresholdT other = (AggregateStatusThresholdT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.color==null && other.getColor()==null) || 
             (this.color!=null &&
              this.color.equals(other.getColor()))) &&
            ((this.childColor==null && other.getChildColor()==null) || 
             (this.childColor!=null &&
              this.childColor.equals(other.getChildColor()))) &&
            ((this.thresholdPercent==null && other.getThresholdPercent()==null) || 
             (this.thresholdPercent!=null &&
              this.thresholdPercent.equals(other.getThresholdPercent())));
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
        if (getColor() != null) {
            _hashCode += getColor().hashCode();
        }
        if (getChildColor() != null) {
            _hashCode += getChildColor().hashCode();
        }
        if (getThresholdPercent() != null) {
            _hashCode += getThresholdPercent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AggregateStatusThresholdT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AggregateStatusThresholdT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("color");
        elemField.setXmlName(new javax.xml.namespace.QName("", "color"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "childColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thresholdPercent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "thresholdPercent"));
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
