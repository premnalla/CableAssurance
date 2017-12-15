/**
 * CmtsCmDataT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmtsCmDataT  implements java.io.Serializable {
    private java.lang.String cmIpAddress;

    private java.lang.String cmMac;

    private java.lang.String upstreamChannelIndex;

    private java.lang.String downstreamChannelIndex;

    private java.lang.String cmIndex;

    private java.lang.String cmStatus;

    public CmtsCmDataT() {
    }

    public CmtsCmDataT(
           java.lang.String cmIpAddress,
           java.lang.String cmMac,
           java.lang.String upstreamChannelIndex,
           java.lang.String downstreamChannelIndex,
           java.lang.String cmIndex,
           java.lang.String cmStatus) {
           this.cmIpAddress = cmIpAddress;
           this.cmMac = cmMac;
           this.upstreamChannelIndex = upstreamChannelIndex;
           this.downstreamChannelIndex = downstreamChannelIndex;
           this.cmIndex = cmIndex;
           this.cmStatus = cmStatus;
    }


    /**
     * Gets the cmIpAddress value for this CmtsCmDataT.
     * 
     * @return cmIpAddress
     */
    public java.lang.String getCmIpAddress() {
        return cmIpAddress;
    }


    /**
     * Sets the cmIpAddress value for this CmtsCmDataT.
     * 
     * @param cmIpAddress
     */
    public void setCmIpAddress(java.lang.String cmIpAddress) {
        this.cmIpAddress = cmIpAddress;
    }


    /**
     * Gets the cmMac value for this CmtsCmDataT.
     * 
     * @return cmMac
     */
    public java.lang.String getCmMac() {
        return cmMac;
    }


    /**
     * Sets the cmMac value for this CmtsCmDataT.
     * 
     * @param cmMac
     */
    public void setCmMac(java.lang.String cmMac) {
        this.cmMac = cmMac;
    }


    /**
     * Gets the upstreamChannelIndex value for this CmtsCmDataT.
     * 
     * @return upstreamChannelIndex
     */
    public java.lang.String getUpstreamChannelIndex() {
        return upstreamChannelIndex;
    }


    /**
     * Sets the upstreamChannelIndex value for this CmtsCmDataT.
     * 
     * @param upstreamChannelIndex
     */
    public void setUpstreamChannelIndex(java.lang.String upstreamChannelIndex) {
        this.upstreamChannelIndex = upstreamChannelIndex;
    }


    /**
     * Gets the downstreamChannelIndex value for this CmtsCmDataT.
     * 
     * @return downstreamChannelIndex
     */
    public java.lang.String getDownstreamChannelIndex() {
        return downstreamChannelIndex;
    }


    /**
     * Sets the downstreamChannelIndex value for this CmtsCmDataT.
     * 
     * @param downstreamChannelIndex
     */
    public void setDownstreamChannelIndex(java.lang.String downstreamChannelIndex) {
        this.downstreamChannelIndex = downstreamChannelIndex;
    }


    /**
     * Gets the cmIndex value for this CmtsCmDataT.
     * 
     * @return cmIndex
     */
    public java.lang.String getCmIndex() {
        return cmIndex;
    }


    /**
     * Sets the cmIndex value for this CmtsCmDataT.
     * 
     * @param cmIndex
     */
    public void setCmIndex(java.lang.String cmIndex) {
        this.cmIndex = cmIndex;
    }


    /**
     * Gets the cmStatus value for this CmtsCmDataT.
     * 
     * @return cmStatus
     */
    public java.lang.String getCmStatus() {
        return cmStatus;
    }


    /**
     * Sets the cmStatus value for this CmtsCmDataT.
     * 
     * @param cmStatus
     */
    public void setCmStatus(java.lang.String cmStatus) {
        this.cmStatus = cmStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmtsCmDataT)) return false;
        CmtsCmDataT other = (CmtsCmDataT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmIpAddress==null && other.getCmIpAddress()==null) || 
             (this.cmIpAddress!=null &&
              this.cmIpAddress.equals(other.getCmIpAddress()))) &&
            ((this.cmMac==null && other.getCmMac()==null) || 
             (this.cmMac!=null &&
              this.cmMac.equals(other.getCmMac()))) &&
            ((this.upstreamChannelIndex==null && other.getUpstreamChannelIndex()==null) || 
             (this.upstreamChannelIndex!=null &&
              this.upstreamChannelIndex.equals(other.getUpstreamChannelIndex()))) &&
            ((this.downstreamChannelIndex==null && other.getDownstreamChannelIndex()==null) || 
             (this.downstreamChannelIndex!=null &&
              this.downstreamChannelIndex.equals(other.getDownstreamChannelIndex()))) &&
            ((this.cmIndex==null && other.getCmIndex()==null) || 
             (this.cmIndex!=null &&
              this.cmIndex.equals(other.getCmIndex()))) &&
            ((this.cmStatus==null && other.getCmStatus()==null) || 
             (this.cmStatus!=null &&
              this.cmStatus.equals(other.getCmStatus())));
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
        if (getCmIpAddress() != null) {
            _hashCode += getCmIpAddress().hashCode();
        }
        if (getCmMac() != null) {
            _hashCode += getCmMac().hashCode();
        }
        if (getUpstreamChannelIndex() != null) {
            _hashCode += getUpstreamChannelIndex().hashCode();
        }
        if (getDownstreamChannelIndex() != null) {
            _hashCode += getDownstreamChannelIndex().hashCode();
        }
        if (getCmIndex() != null) {
            _hashCode += getCmIndex().hashCode();
        }
        if (getCmStatus() != null) {
            _hashCode += getCmStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmtsCmDataT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsCmDataT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmIpAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmIpAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmMac");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmMac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upstreamChannelIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "upstreamChannelIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downstreamChannelIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downstreamChannelIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmStatus"));
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
