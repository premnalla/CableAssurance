/**
 * HfcStatusSummaryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class HfcStatusSummaryT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT statusSummary;

    private java.math.BigInteger cmtsResId;

    private java.lang.String cmtsName;

    public HfcStatusSummaryT() {
    }

    public HfcStatusSummaryT(
           com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT statusSummary,
           java.math.BigInteger cmtsResId,
           java.lang.String cmtsName) {
           this.statusSummary = statusSummary;
           this.cmtsResId = cmtsResId;
           this.cmtsName = cmtsName;
    }


    /**
     * Gets the statusSummary value for this HfcStatusSummaryT.
     * 
     * @return statusSummary
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT getStatusSummary() {
        return statusSummary;
    }


    /**
     * Sets the statusSummary value for this HfcStatusSummaryT.
     * 
     * @param statusSummary
     */
    public void setStatusSummary(com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT statusSummary) {
        this.statusSummary = statusSummary;
    }


    /**
     * Gets the cmtsResId value for this HfcStatusSummaryT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this HfcStatusSummaryT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the cmtsName value for this HfcStatusSummaryT.
     * 
     * @return cmtsName
     */
    public java.lang.String getCmtsName() {
        return cmtsName;
    }


    /**
     * Sets the cmtsName value for this HfcStatusSummaryT.
     * 
     * @param cmtsName
     */
    public void setCmtsName(java.lang.String cmtsName) {
        this.cmtsName = cmtsName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HfcStatusSummaryT)) return false;
        HfcStatusSummaryT other = (HfcStatusSummaryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statusSummary==null && other.getStatusSummary()==null) || 
             (this.statusSummary!=null &&
              this.statusSummary.equals(other.getStatusSummary()))) &&
            ((this.cmtsResId==null && other.getCmtsResId()==null) || 
             (this.cmtsResId!=null &&
              this.cmtsResId.equals(other.getCmtsResId()))) &&
            ((this.cmtsName==null && other.getCmtsName()==null) || 
             (this.cmtsName!=null &&
              this.cmtsName.equals(other.getCmtsName())));
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
        if (getStatusSummary() != null) {
            _hashCode += getStatusSummary().hashCode();
        }
        if (getCmtsResId() != null) {
            _hashCode += getCmtsResId().hashCode();
        }
        if (getCmtsName() != null) {
            _hashCode += getCmtsName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HfcStatusSummaryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusSummaryT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmtsResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmtsName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsName"));
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
