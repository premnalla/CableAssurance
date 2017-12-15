/**
 * CmPerformanceConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmPerformanceConfigT  implements java.io.Serializable {
    private java.lang.String downstreamSnrLower;

    private java.lang.String downstreamPowerLower;

    private java.lang.String downstreamPowerUpper;

    private java.lang.String upstreamPowerLower;

    private java.lang.String upstreamPowerUpper;

    public CmPerformanceConfigT() {
    }

    public CmPerformanceConfigT(
           java.lang.String downstreamSnrLower,
           java.lang.String downstreamPowerLower,
           java.lang.String downstreamPowerUpper,
           java.lang.String upstreamPowerLower,
           java.lang.String upstreamPowerUpper) {
           this.downstreamSnrLower = downstreamSnrLower;
           this.downstreamPowerLower = downstreamPowerLower;
           this.downstreamPowerUpper = downstreamPowerUpper;
           this.upstreamPowerLower = upstreamPowerLower;
           this.upstreamPowerUpper = upstreamPowerUpper;
    }


    /**
     * Gets the downstreamSnrLower value for this CmPerformanceConfigT.
     * 
     * @return downstreamSnrLower
     */
    public java.lang.String getDownstreamSnrLower() {
        return downstreamSnrLower;
    }


    /**
     * Sets the downstreamSnrLower value for this CmPerformanceConfigT.
     * 
     * @param downstreamSnrLower
     */
    public void setDownstreamSnrLower(java.lang.String downstreamSnrLower) {
        this.downstreamSnrLower = downstreamSnrLower;
    }


    /**
     * Gets the downstreamPowerLower value for this CmPerformanceConfigT.
     * 
     * @return downstreamPowerLower
     */
    public java.lang.String getDownstreamPowerLower() {
        return downstreamPowerLower;
    }


    /**
     * Sets the downstreamPowerLower value for this CmPerformanceConfigT.
     * 
     * @param downstreamPowerLower
     */
    public void setDownstreamPowerLower(java.lang.String downstreamPowerLower) {
        this.downstreamPowerLower = downstreamPowerLower;
    }


    /**
     * Gets the downstreamPowerUpper value for this CmPerformanceConfigT.
     * 
     * @return downstreamPowerUpper
     */
    public java.lang.String getDownstreamPowerUpper() {
        return downstreamPowerUpper;
    }


    /**
     * Sets the downstreamPowerUpper value for this CmPerformanceConfigT.
     * 
     * @param downstreamPowerUpper
     */
    public void setDownstreamPowerUpper(java.lang.String downstreamPowerUpper) {
        this.downstreamPowerUpper = downstreamPowerUpper;
    }


    /**
     * Gets the upstreamPowerLower value for this CmPerformanceConfigT.
     * 
     * @return upstreamPowerLower
     */
    public java.lang.String getUpstreamPowerLower() {
        return upstreamPowerLower;
    }


    /**
     * Sets the upstreamPowerLower value for this CmPerformanceConfigT.
     * 
     * @param upstreamPowerLower
     */
    public void setUpstreamPowerLower(java.lang.String upstreamPowerLower) {
        this.upstreamPowerLower = upstreamPowerLower;
    }


    /**
     * Gets the upstreamPowerUpper value for this CmPerformanceConfigT.
     * 
     * @return upstreamPowerUpper
     */
    public java.lang.String getUpstreamPowerUpper() {
        return upstreamPowerUpper;
    }


    /**
     * Sets the upstreamPowerUpper value for this CmPerformanceConfigT.
     * 
     * @param upstreamPowerUpper
     */
    public void setUpstreamPowerUpper(java.lang.String upstreamPowerUpper) {
        this.upstreamPowerUpper = upstreamPowerUpper;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmPerformanceConfigT)) return false;
        CmPerformanceConfigT other = (CmPerformanceConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.downstreamSnrLower==null && other.getDownstreamSnrLower()==null) || 
             (this.downstreamSnrLower!=null &&
              this.downstreamSnrLower.equals(other.getDownstreamSnrLower()))) &&
            ((this.downstreamPowerLower==null && other.getDownstreamPowerLower()==null) || 
             (this.downstreamPowerLower!=null &&
              this.downstreamPowerLower.equals(other.getDownstreamPowerLower()))) &&
            ((this.downstreamPowerUpper==null && other.getDownstreamPowerUpper()==null) || 
             (this.downstreamPowerUpper!=null &&
              this.downstreamPowerUpper.equals(other.getDownstreamPowerUpper()))) &&
            ((this.upstreamPowerLower==null && other.getUpstreamPowerLower()==null) || 
             (this.upstreamPowerLower!=null &&
              this.upstreamPowerLower.equals(other.getUpstreamPowerLower()))) &&
            ((this.upstreamPowerUpper==null && other.getUpstreamPowerUpper()==null) || 
             (this.upstreamPowerUpper!=null &&
              this.upstreamPowerUpper.equals(other.getUpstreamPowerUpper())));
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
        if (getDownstreamSnrLower() != null) {
            _hashCode += getDownstreamSnrLower().hashCode();
        }
        if (getDownstreamPowerLower() != null) {
            _hashCode += getDownstreamPowerLower().hashCode();
        }
        if (getDownstreamPowerUpper() != null) {
            _hashCode += getDownstreamPowerUpper().hashCode();
        }
        if (getUpstreamPowerLower() != null) {
            _hashCode += getUpstreamPowerLower().hashCode();
        }
        if (getUpstreamPowerUpper() != null) {
            _hashCode += getUpstreamPowerUpper().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmPerformanceConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downstreamSnrLower");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downstreamSnrLower"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downstreamPowerLower");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downstreamPowerLower"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downstreamPowerUpper");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downstreamPowerUpper"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upstreamPowerLower");
        elemField.setXmlName(new javax.xml.namespace.QName("", "upstreamPowerLower"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upstreamPowerUpper");
        elemField.setXmlName(new javax.xml.namespace.QName("", "upstreamPowerUpper"));
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
