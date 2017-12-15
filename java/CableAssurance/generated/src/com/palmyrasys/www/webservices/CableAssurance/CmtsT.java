/**
 * CmtsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmtsT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger cmtsResId;

    private java.lang.String cmtsName;

    private java.lang.String cmtsHost;

    private com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT cmtsSnmpVersion;

    private com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT cmSnmpVersion;

    private com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT mtaSnmpVersion;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    private com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts;

    public CmtsT() {
    }

    public CmtsT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger cmtsResId,
           java.lang.String cmtsName,
           java.lang.String cmtsHost,
           com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT cmtsSnmpVersion,
           com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT cmSnmpVersion,
           com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT mtaSnmpVersion,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor,
           com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
           this.topologyKey = topologyKey;
           this.cmtsResId = cmtsResId;
           this.cmtsName = cmtsName;
           this.cmtsHost = cmtsHost;
           this.cmtsSnmpVersion = cmtsSnmpVersion;
           this.cmSnmpVersion = cmSnmpVersion;
           this.mtaSnmpVersion = mtaSnmpVersion;
           this.statusColor = statusColor;
           this.counts = counts;
    }


    /**
     * Gets the topologyKey value for this CmtsT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this CmtsT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
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
     * Gets the cmtsHost value for this CmtsT.
     * 
     * @return cmtsHost
     */
    public java.lang.String getCmtsHost() {
        return cmtsHost;
    }


    /**
     * Sets the cmtsHost value for this CmtsT.
     * 
     * @param cmtsHost
     */
    public void setCmtsHost(java.lang.String cmtsHost) {
        this.cmtsHost = cmtsHost;
    }


    /**
     * Gets the cmtsSnmpVersion value for this CmtsT.
     * 
     * @return cmtsSnmpVersion
     */
    public com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT getCmtsSnmpVersion() {
        return cmtsSnmpVersion;
    }


    /**
     * Sets the cmtsSnmpVersion value for this CmtsT.
     * 
     * @param cmtsSnmpVersion
     */
    public void setCmtsSnmpVersion(com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT cmtsSnmpVersion) {
        this.cmtsSnmpVersion = cmtsSnmpVersion;
    }


    /**
     * Gets the cmSnmpVersion value for this CmtsT.
     * 
     * @return cmSnmpVersion
     */
    public com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT getCmSnmpVersion() {
        return cmSnmpVersion;
    }


    /**
     * Sets the cmSnmpVersion value for this CmtsT.
     * 
     * @param cmSnmpVersion
     */
    public void setCmSnmpVersion(com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT cmSnmpVersion) {
        this.cmSnmpVersion = cmSnmpVersion;
    }


    /**
     * Gets the mtaSnmpVersion value for this CmtsT.
     * 
     * @return mtaSnmpVersion
     */
    public com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT getMtaSnmpVersion() {
        return mtaSnmpVersion;
    }


    /**
     * Sets the mtaSnmpVersion value for this CmtsT.
     * 
     * @param mtaSnmpVersion
     */
    public void setMtaSnmpVersion(com.palmyrasys.www.webservices.CableAssurance.SnmpVersionT mtaSnmpVersion) {
        this.mtaSnmpVersion = mtaSnmpVersion;
    }


    /**
     * Gets the statusColor value for this CmtsT.
     * 
     * @return statusColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this CmtsT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }


    /**
     * Gets the counts value for this CmtsT.
     * 
     * @return counts
     */
    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCounts() {
        return counts;
    }


    /**
     * Sets the counts value for this CmtsT.
     * 
     * @param counts
     */
    public void setCounts(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
        this.counts = counts;
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
            ((this.topologyKey==null && other.getTopologyKey()==null) || 
             (this.topologyKey!=null &&
              this.topologyKey.equals(other.getTopologyKey()))) &&
            ((this.cmtsResId==null && other.getCmtsResId()==null) || 
             (this.cmtsResId!=null &&
              this.cmtsResId.equals(other.getCmtsResId()))) &&
            ((this.cmtsName==null && other.getCmtsName()==null) || 
             (this.cmtsName!=null &&
              this.cmtsName.equals(other.getCmtsName()))) &&
            ((this.cmtsHost==null && other.getCmtsHost()==null) || 
             (this.cmtsHost!=null &&
              this.cmtsHost.equals(other.getCmtsHost()))) &&
            ((this.cmtsSnmpVersion==null && other.getCmtsSnmpVersion()==null) || 
             (this.cmtsSnmpVersion!=null &&
              this.cmtsSnmpVersion.equals(other.getCmtsSnmpVersion()))) &&
            ((this.cmSnmpVersion==null && other.getCmSnmpVersion()==null) || 
             (this.cmSnmpVersion!=null &&
              this.cmSnmpVersion.equals(other.getCmSnmpVersion()))) &&
            ((this.mtaSnmpVersion==null && other.getMtaSnmpVersion()==null) || 
             (this.mtaSnmpVersion!=null &&
              this.mtaSnmpVersion.equals(other.getMtaSnmpVersion()))) &&
            ((this.statusColor==null && other.getStatusColor()==null) || 
             (this.statusColor!=null &&
              this.statusColor.equals(other.getStatusColor()))) &&
            ((this.counts==null && other.getCounts()==null) || 
             (this.counts!=null &&
              this.counts.equals(other.getCounts())));
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
        if (getCmtsResId() != null) {
            _hashCode += getCmtsResId().hashCode();
        }
        if (getCmtsName() != null) {
            _hashCode += getCmtsName().hashCode();
        }
        if (getCmtsHost() != null) {
            _hashCode += getCmtsHost().hashCode();
        }
        if (getCmtsSnmpVersion() != null) {
            _hashCode += getCmtsSnmpVersion().hashCode();
        }
        if (getCmSnmpVersion() != null) {
            _hashCode += getCmSnmpVersion().hashCode();
        }
        if (getMtaSnmpVersion() != null) {
            _hashCode += getMtaSnmpVersion().hashCode();
        }
        if (getStatusColor() != null) {
            _hashCode += getStatusColor().hashCode();
        }
        if (getCounts() != null) {
            _hashCode += getCounts().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmtsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
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
        elemField.setFieldName("cmtsHost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsHost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmtsSnmpVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsSnmpVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpVersionT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmSnmpVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmSnmpVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpVersionT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaSnmpVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaSnmpVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpVersionT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("counts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "counts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT"));
        elemField.setNillable(true);
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
