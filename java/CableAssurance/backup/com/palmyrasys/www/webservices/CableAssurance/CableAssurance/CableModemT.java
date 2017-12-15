/**
 * CableModemT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import java.math.BigInteger;
import com.palmyrasyscorp.db.tables.CableModem;

public class CableModemT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger cmResId;

    private java.math.BigInteger cmtsResId;

    private java.math.BigInteger upChannelResId;

    private java.math.BigInteger downChannelResId;

    private java.math.BigInteger hfcResId;

    private java.lang.String macAddress;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    public CableModemT() {
    }

    public CableModemT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger cmResId,
           java.math.BigInteger cmtsResId,
           java.math.BigInteger upChannelResId,
           java.math.BigInteger downChannelResId,
           java.math.BigInteger hfcResId,
           java.lang.String macAddress,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
           this.topologyKey = topologyKey;
           this.cmResId = cmResId;
           this.cmtsResId = cmtsResId;
           this.upChannelResId = upChannelResId;
           this.downChannelResId = downChannelResId;
           this.hfcResId = hfcResId;
           this.macAddress = macAddress;
           this.statusColor = statusColor;
    }

    /**
     * @author Prem
     * @param cm
     */
    public CableModemT(CableModem dbCm) {
        this.cmResId = new BigInteger(dbCm.getCmResId().toString());
        this.cmtsResId = new BigInteger(dbCm.getCmtsResId().toString());
        this.upChannelResId = new BigInteger(dbCm.getUpChannelResId().toString());
        this.downChannelResId = new BigInteger(dbCm.getDownChannelResId().toString());
        this.hfcResId = new BigInteger(dbCm.getHfcResId().toString());
        this.macAddress = dbCm.getMacAddress();
        this.statusColor = StatusColorT.Green;
    }

    /**
     * Gets the topologyKey value for this CableModemT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this CableModemT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the cmResId value for this CableModemT.
     * 
     * @return cmResId
     */
    public java.math.BigInteger getCmResId() {
        return cmResId;
    }


    /**
     * Sets the cmResId value for this CableModemT.
     * 
     * @param cmResId
     */
    public void setCmResId(java.math.BigInteger cmResId) {
        this.cmResId = cmResId;
    }


    /**
     * Gets the cmtsResId value for this CableModemT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this CableModemT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the upChannelResId value for this CableModemT.
     * 
     * @return upChannelResId
     */
    public java.math.BigInteger getUpChannelResId() {
        return upChannelResId;
    }


    /**
     * Sets the upChannelResId value for this CableModemT.
     * 
     * @param upChannelResId
     */
    public void setUpChannelResId(java.math.BigInteger upChannelResId) {
        this.upChannelResId = upChannelResId;
    }


    /**
     * Gets the downChannelResId value for this CableModemT.
     * 
     * @return downChannelResId
     */
    public java.math.BigInteger getDownChannelResId() {
        return downChannelResId;
    }


    /**
     * Sets the downChannelResId value for this CableModemT.
     * 
     * @param downChannelResId
     */
    public void setDownChannelResId(java.math.BigInteger downChannelResId) {
        this.downChannelResId = downChannelResId;
    }


    /**
     * Gets the hfcResId value for this CableModemT.
     * 
     * @return hfcResId
     */
    public java.math.BigInteger getHfcResId() {
        return hfcResId;
    }


    /**
     * Sets the hfcResId value for this CableModemT.
     * 
     * @param hfcResId
     */
    public void setHfcResId(java.math.BigInteger hfcResId) {
        this.hfcResId = hfcResId;
    }


    /**
     * Gets the macAddress value for this CableModemT.
     * 
     * @return macAddress
     */
    public java.lang.String getMacAddress() {
        return macAddress;
    }


    /**
     * Sets the macAddress value for this CableModemT.
     * 
     * @param macAddress
     */
    public void setMacAddress(java.lang.String macAddress) {
        this.macAddress = macAddress;
    }


    /**
     * Gets the statusColor value for this CableModemT.
     * 
     * @return statusColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this CableModemT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CableModemT)) return false;
        CableModemT other = (CableModemT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.topologyKey==null && other.getTopologyKey()==null) || 
             (this.topologyKey!=null &&
              this.topologyKey.equals(other.getTopologyKey()))) &&
            ((this.cmResId==null && other.getCmResId()==null) || 
             (this.cmResId!=null &&
              this.cmResId.equals(other.getCmResId()))) &&
            ((this.cmtsResId==null && other.getCmtsResId()==null) || 
             (this.cmtsResId!=null &&
              this.cmtsResId.equals(other.getCmtsResId()))) &&
            ((this.upChannelResId==null && other.getUpChannelResId()==null) || 
             (this.upChannelResId!=null &&
              this.upChannelResId.equals(other.getUpChannelResId()))) &&
            ((this.downChannelResId==null && other.getDownChannelResId()==null) || 
             (this.downChannelResId!=null &&
              this.downChannelResId.equals(other.getDownChannelResId()))) &&
            ((this.hfcResId==null && other.getHfcResId()==null) || 
             (this.hfcResId!=null &&
              this.hfcResId.equals(other.getHfcResId()))) &&
            ((this.macAddress==null && other.getMacAddress()==null) || 
             (this.macAddress!=null &&
              this.macAddress.equals(other.getMacAddress()))) &&
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
        if (getTopologyKey() != null) {
            _hashCode += getTopologyKey().hashCode();
        }
        if (getCmResId() != null) {
            _hashCode += getCmResId().hashCode();
        }
        if (getCmtsResId() != null) {
            _hashCode += getCmtsResId().hashCode();
        }
        if (getUpChannelResId() != null) {
            _hashCode += getUpChannelResId().hashCode();
        }
        if (getDownChannelResId() != null) {
            _hashCode += getDownChannelResId().hashCode();
        }
        if (getHfcResId() != null) {
            _hashCode += getHfcResId().hashCode();
        }
        if (getMacAddress() != null) {
            _hashCode += getMacAddress().hashCode();
        }
        if (getStatusColor() != null) {
            _hashCode += getStatusColor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CableModemT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CableModemT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmResId"));
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
        elemField.setFieldName("upChannelResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "upChannelResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downChannelResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downChannelResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("macAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "macAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT"));
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
