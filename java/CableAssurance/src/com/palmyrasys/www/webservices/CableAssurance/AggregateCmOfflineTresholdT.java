/**
 * AggregateCmOfflineTresholdT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class AggregateCmOfflineTresholdT  implements java.io.Serializable {
    private java.lang.String percentCmOffline_1;

    private java.lang.String maxCm_1;

    private java.lang.String percentCmOffline_2;

    private java.lang.String maxCm_2;

    private java.lang.String detectionWindow;

    public AggregateCmOfflineTresholdT() {
    }

    public AggregateCmOfflineTresholdT(
           java.lang.String percentCmOffline_1,
           java.lang.String maxCm_1,
           java.lang.String percentCmOffline_2,
           java.lang.String maxCm_2,
           java.lang.String detectionWindow) {
           this.percentCmOffline_1 = percentCmOffline_1;
           this.maxCm_1 = maxCm_1;
           this.percentCmOffline_2 = percentCmOffline_2;
           this.maxCm_2 = maxCm_2;
           this.detectionWindow = detectionWindow;
    }


    /**
     * Gets the percentCmOffline_1 value for this AggregateCmOfflineTresholdT.
     * 
     * @return percentCmOffline_1
     */
    public java.lang.String getPercentCmOffline_1() {
        return percentCmOffline_1;
    }


    /**
     * Sets the percentCmOffline_1 value for this AggregateCmOfflineTresholdT.
     * 
     * @param percentCmOffline_1
     */
    public void setPercentCmOffline_1(java.lang.String percentCmOffline_1) {
        this.percentCmOffline_1 = percentCmOffline_1;
    }


    /**
     * Gets the maxCm_1 value for this AggregateCmOfflineTresholdT.
     * 
     * @return maxCm_1
     */
    public java.lang.String getMaxCm_1() {
        return maxCm_1;
    }


    /**
     * Sets the maxCm_1 value for this AggregateCmOfflineTresholdT.
     * 
     * @param maxCm_1
     */
    public void setMaxCm_1(java.lang.String maxCm_1) {
        this.maxCm_1 = maxCm_1;
    }


    /**
     * Gets the percentCmOffline_2 value for this AggregateCmOfflineTresholdT.
     * 
     * @return percentCmOffline_2
     */
    public java.lang.String getPercentCmOffline_2() {
        return percentCmOffline_2;
    }


    /**
     * Sets the percentCmOffline_2 value for this AggregateCmOfflineTresholdT.
     * 
     * @param percentCmOffline_2
     */
    public void setPercentCmOffline_2(java.lang.String percentCmOffline_2) {
        this.percentCmOffline_2 = percentCmOffline_2;
    }


    /**
     * Gets the maxCm_2 value for this AggregateCmOfflineTresholdT.
     * 
     * @return maxCm_2
     */
    public java.lang.String getMaxCm_2() {
        return maxCm_2;
    }


    /**
     * Sets the maxCm_2 value for this AggregateCmOfflineTresholdT.
     * 
     * @param maxCm_2
     */
    public void setMaxCm_2(java.lang.String maxCm_2) {
        this.maxCm_2 = maxCm_2;
    }


    /**
     * Gets the detectionWindow value for this AggregateCmOfflineTresholdT.
     * 
     * @return detectionWindow
     */
    public java.lang.String getDetectionWindow() {
        return detectionWindow;
    }


    /**
     * Sets the detectionWindow value for this AggregateCmOfflineTresholdT.
     * 
     * @param detectionWindow
     */
    public void setDetectionWindow(java.lang.String detectionWindow) {
        this.detectionWindow = detectionWindow;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AggregateCmOfflineTresholdT)) return false;
        AggregateCmOfflineTresholdT other = (AggregateCmOfflineTresholdT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.percentCmOffline_1==null && other.getPercentCmOffline_1()==null) || 
             (this.percentCmOffline_1!=null &&
              this.percentCmOffline_1.equals(other.getPercentCmOffline_1()))) &&
            ((this.maxCm_1==null && other.getMaxCm_1()==null) || 
             (this.maxCm_1!=null &&
              this.maxCm_1.equals(other.getMaxCm_1()))) &&
            ((this.percentCmOffline_2==null && other.getPercentCmOffline_2()==null) || 
             (this.percentCmOffline_2!=null &&
              this.percentCmOffline_2.equals(other.getPercentCmOffline_2()))) &&
            ((this.maxCm_2==null && other.getMaxCm_2()==null) || 
             (this.maxCm_2!=null &&
              this.maxCm_2.equals(other.getMaxCm_2()))) &&
            ((this.detectionWindow==null && other.getDetectionWindow()==null) || 
             (this.detectionWindow!=null &&
              this.detectionWindow.equals(other.getDetectionWindow())));
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
        if (getPercentCmOffline_1() != null) {
            _hashCode += getPercentCmOffline_1().hashCode();
        }
        if (getMaxCm_1() != null) {
            _hashCode += getMaxCm_1().hashCode();
        }
        if (getPercentCmOffline_2() != null) {
            _hashCode += getPercentCmOffline_2().hashCode();
        }
        if (getMaxCm_2() != null) {
            _hashCode += getMaxCm_2().hashCode();
        }
        if (getDetectionWindow() != null) {
            _hashCode += getDetectionWindow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AggregateCmOfflineTresholdT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AggregateCmOfflineTresholdT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentCmOffline_1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percentCmOffline_1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxCm_1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxCm_1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentCmOffline_2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percentCmOffline_2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxCm_2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxCm_2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detectionWindow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detectionWindow"));
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
