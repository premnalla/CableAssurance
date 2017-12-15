/**
 * EmtaT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import java.math.BigInteger;
import com.palmyrasyscorp.db.tables.Emta;

public class EmtaT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger emtaResId;

    private java.math.BigInteger cmResId;

    private java.lang.String macAddress;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    public EmtaT() {
    }

    public EmtaT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger emtaResId,
           java.math.BigInteger cmResId,
           java.lang.String macAddress,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
           this.topologyKey = topologyKey;
           this.emtaResId = emtaResId;
           this.cmResId = cmResId;
           this.macAddress = macAddress;
           this.statusColor = statusColor;
    }

    /**
     * @author Prem
     * @param dbEmta
     */
    public EmtaT(Emta dbEmta) {
        this.emtaResId = new BigInteger(dbEmta.getEmtaResId().toString());
        this.cmResId = new BigInteger(dbEmta.getCmResId().toString());
        this.macAddress = dbEmta.getMacAddress();
        this.statusColor = StatusColorT.Green;
    }

    /**
     * Gets the topologyKey value for this EmtaT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this EmtaT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the emtaResId value for this EmtaT.
     * 
     * @return emtaResId
     */
    public java.math.BigInteger getEmtaResId() {
        return emtaResId;
    }


    /**
     * Sets the emtaResId value for this EmtaT.
     * 
     * @param emtaResId
     */
    public void setEmtaResId(java.math.BigInteger emtaResId) {
        this.emtaResId = emtaResId;
    }


    /**
     * Gets the cmResId value for this EmtaT.
     * 
     * @return cmResId
     */
    public java.math.BigInteger getCmResId() {
        return cmResId;
    }


    /**
     * Sets the cmResId value for this EmtaT.
     * 
     * @param cmResId
     */
    public void setCmResId(java.math.BigInteger cmResId) {
        this.cmResId = cmResId;
    }


    /**
     * Gets the macAddress value for this EmtaT.
     * 
     * @return macAddress
     */
    public java.lang.String getMacAddress() {
        return macAddress;
    }


    /**
     * Sets the macAddress value for this EmtaT.
     * 
     * @param macAddress
     */
    public void setMacAddress(java.lang.String macAddress) {
        this.macAddress = macAddress;
    }


    /**
     * Gets the statusColor value for this EmtaT.
     * 
     * @return statusColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this EmtaT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmtaT)) return false;
        EmtaT other = (EmtaT) obj;
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
            ((this.emtaResId==null && other.getEmtaResId()==null) || 
             (this.emtaResId!=null &&
              this.emtaResId.equals(other.getEmtaResId()))) &&
            ((this.cmResId==null && other.getCmResId()==null) || 
             (this.cmResId!=null &&
              this.cmResId.equals(other.getCmResId()))) &&
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
        if (getEmtaResId() != null) {
            _hashCode += getEmtaResId().hashCode();
        }
        if (getCmResId() != null) {
            _hashCode += getCmResId().hashCode();
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
        new org.apache.axis.description.TypeDesc(EmtaT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emtaResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emtaResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmResId"));
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
