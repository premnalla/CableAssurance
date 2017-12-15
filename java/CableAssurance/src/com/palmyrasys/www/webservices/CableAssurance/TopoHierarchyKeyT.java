/**
 * TopoHierarchyKeyT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class TopoHierarchyKeyT  implements java.io.Serializable {
    private java.math.BigInteger regionId;

    private java.math.BigInteger marketId;

    private java.math.BigInteger bladeId;

    public TopoHierarchyKeyT() {
    }

    public TopoHierarchyKeyT(
           java.math.BigInteger regionId,
           java.math.BigInteger marketId,
           java.math.BigInteger bladeId) {
           this.regionId = regionId;
           this.marketId = marketId;
           this.bladeId = bladeId;
    }


    /**
     * Gets the regionId value for this TopoHierarchyKeyT.
     * 
     * @return regionId
     */
    public java.math.BigInteger getRegionId() {
        return regionId;
    }


    /**
     * Sets the regionId value for this TopoHierarchyKeyT.
     * 
     * @param regionId
     */
    public void setRegionId(java.math.BigInteger regionId) {
        this.regionId = regionId;
    }


    /**
     * Gets the marketId value for this TopoHierarchyKeyT.
     * 
     * @return marketId
     */
    public java.math.BigInteger getMarketId() {
        return marketId;
    }


    /**
     * Sets the marketId value for this TopoHierarchyKeyT.
     * 
     * @param marketId
     */
    public void setMarketId(java.math.BigInteger marketId) {
        this.marketId = marketId;
    }


    /**
     * Gets the bladeId value for this TopoHierarchyKeyT.
     * 
     * @return bladeId
     */
    public java.math.BigInteger getBladeId() {
        return bladeId;
    }


    /**
     * Sets the bladeId value for this TopoHierarchyKeyT.
     * 
     * @param bladeId
     */
    public void setBladeId(java.math.BigInteger bladeId) {
        this.bladeId = bladeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopoHierarchyKeyT)) return false;
        TopoHierarchyKeyT other = (TopoHierarchyKeyT) obj;
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
              this.bladeId.equals(other.getBladeId())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TopoHierarchyKeyT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
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
