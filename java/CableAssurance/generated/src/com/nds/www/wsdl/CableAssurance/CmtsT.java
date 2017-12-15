/**
 * CmtsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public class CmtsT  implements java.io.Serializable {
    private java.math.BigInteger regionId;

    private java.math.BigInteger marketId;

    private java.math.BigInteger bladeId;

    private java.math.BigInteger cmtsResId;

    private java.lang.String cmtsName;

    private com.nds.www.wsdl.CableAssurance.StatusColorT statusColor;

    public CmtsT() {
    }

    public CmtsT(
           java.math.BigInteger regionId,
           java.math.BigInteger marketId,
           java.math.BigInteger bladeId,
           java.math.BigInteger cmtsResId,
           java.lang.String cmtsName,
           com.nds.www.wsdl.CableAssurance.StatusColorT statusColor) {
           this.regionId = regionId;
           this.marketId = marketId;
           this.bladeId = bladeId;
           this.cmtsResId = cmtsResId;
           this.cmtsName = cmtsName;
           this.statusColor = statusColor;
    }


    /**
     * Gets the regionId value for this CmtsT.
     * 
     * @return regionId
     */
    public java.math.BigInteger getRegionId() {
        return regionId;
    }


    /**
     * Sets the regionId value for this CmtsT.
     * 
     * @param regionId
     */
    public void setRegionId(java.math.BigInteger regionId) {
        this.regionId = regionId;
    }


    /**
     * Gets the marketId value for this CmtsT.
     * 
     * @return marketId
     */
    public java.math.BigInteger getMarketId() {
        return marketId;
    }


    /**
     * Sets the marketId value for this CmtsT.
     * 
     * @param marketId
     */
    public void setMarketId(java.math.BigInteger marketId) {
        this.marketId = marketId;
    }


    /**
     * Gets the bladeId value for this CmtsT.
     * 
     * @return bladeId
     */
    public java.math.BigInteger getBladeId() {
        return bladeId;
    }


    /**
     * Sets the bladeId value for this CmtsT.
     * 
     * @param bladeId
     */
    public void setBladeId(java.math.BigInteger bladeId) {
        this.bladeId = bladeId;
    }


    /**
     * Gets the cmtsResId value for this CmtsT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this CmtsT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the cmtsName value for this CmtsT.
     * 
     * @return cmtsName
     */
    public java.lang.String getCmtsName() {
        return cmtsName;
    }


    /**
     * Sets the cmtsName value for this CmtsT.
     * 
     * @param cmtsName
     */
    public void setCmtsName(java.lang.String cmtsName) {
        this.cmtsName = cmtsName;
    }


    /**
     * Gets the statusColor value for this CmtsT.
     * 
     * @return statusColor
     */
    public com.nds.www.wsdl.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this CmtsT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.nds.www.wsdl.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmtsT)) return false;
        CmtsT other = (CmtsT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.regionId==null && other.getRegionId()==null) || 
             (this.regionId!=null &&
              this.regionId.equals(other.getRegionId()))) &&
            ((this.marketId==null && other.getMarketId()==null) || 
             (this.marketId!=null &&
              this.marketId.equals(other.getMarketId()))) &&
            ((this.bladeId==null && other.getBladeId()==null) || 
             (this.bladeId!=null &&
              this.bladeId.equals(other.getBladeId()))) &&
            ((this.cmtsResId==null && other.getCmtsResId()==null) || 
             (this.cmtsResId!=null &&
              this.cmtsResId.equals(other.getCmtsResId()))) &&
            ((this.cmtsName==null && other.getCmtsName()==null) || 
             (this.cmtsName!=null &&
              this.cmtsName.equals(other.getCmtsName()))) &&
            ((this.statusColor==null && other.getStatusColor()==null) || 
             (this.statusColor!=null &&
              this.statusColor.equals(other.getStatusColor())));
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
        if (getRegionId() != null) {
            _hashCode += getRegionId().hashCode();
        }
        if (getMarketId() != null) {
            _hashCode += getMarketId().hashCode();
        }
        if (getBladeId() != null) {
            _hashCode += getBladeId().hashCode();
        }
        if (getCmtsResId() != null) {
            _hashCode += getCmtsResId().hashCode();
        }
        if (getCmtsName() != null) {
            _hashCode += getCmtsName().hashCode();
        }
        if (getStatusColor() != null) {
            _hashCode += getStatusColor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmtsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "CmtsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "regionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("marketId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "marketId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bladeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bladeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "StatusColorT"));
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
