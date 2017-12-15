/**
 * ChannelT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import java.math.BigInteger;
import com.palmyrasyscorp.db.tables.Channel;

public class ChannelT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger channelResId;

    private java.math.BigInteger cmtsResId;

    private java.lang.String channelName;

    private com.palmyrasys.www.webservices.CableAssurance.ChannelTypeT channelType;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    private com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts;

    public ChannelT() {
    }

    public ChannelT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger channelResId,
           java.math.BigInteger cmtsResId,
           java.lang.String channelName,
           com.palmyrasys.www.webservices.CableAssurance.ChannelTypeT channelType,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor,
           com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
           this.topologyKey = topologyKey;
           this.channelResId = channelResId;
           this.cmtsResId = cmtsResId;
           this.channelName = channelName;
           this.channelType = channelType;
           this.statusColor = statusColor;
           this.counts = counts;
    }

    /**
     * 
     * @author Prem
     * @param dbChnl
     */
    public ChannelT(Channel dbChnl) {
    	this.channelResId = new BigInteger(dbChnl.getChannelResId().toString());
    	this.cmtsResId = new BigInteger(dbChnl.getCmtsResId().toString());
    	this.channelName = dbChnl.getName();
    	this.channelType = ChannelTypeT.Upstream;
    	this.statusColor = StatusColorT.Green;
        this.counts = new GenericCountsT();
    }

    /**
     * Gets the topologyKey value for this ChannelT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this ChannelT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the channelResId value for this ChannelT.
     * 
     * @return channelResId
     */
    public java.math.BigInteger getChannelResId() {
        return channelResId;
    }


    /**
     * Sets the channelResId value for this ChannelT.
     * 
     * @param channelResId
     */
    public void setChannelResId(java.math.BigInteger channelResId) {
        this.channelResId = channelResId;
    }


    /**
     * Gets the cmtsResId value for this ChannelT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this ChannelT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the channelName value for this ChannelT.
     * 
     * @return channelName
     */
    public java.lang.String getChannelName() {
        return channelName;
    }


    /**
     * Sets the channelName value for this ChannelT.
     * 
     * @param channelName
     */
    public void setChannelName(java.lang.String channelName) {
        this.channelName = channelName;
    }


    /**
     * Gets the channelType value for this ChannelT.
     * 
     * @return channelType
     */
    public com.palmyrasys.www.webservices.CableAssurance.ChannelTypeT getChannelType() {
        return channelType;
    }


    /**
     * Sets the channelType value for this ChannelT.
     * 
     * @param channelType
     */
    public void setChannelType(com.palmyrasys.www.webservices.CableAssurance.ChannelTypeT channelType) {
        this.channelType = channelType;
    }


    /**
     * Gets the statusColor value for this ChannelT.
     * 
     * @return statusColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this ChannelT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }


    /**
     * Gets the counts value for this ChannelT.
     * 
     * @return counts
     */
    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCounts() {
        return counts;
    }


    /**
     * Sets the counts value for this ChannelT.
     * 
     * @param counts
     */
    public void setCounts(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
        this.counts = counts;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChannelT)) return false;
        ChannelT other = (ChannelT) obj;
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
            ((this.channelResId==null && other.getChannelResId()==null) || 
             (this.channelResId!=null &&
              this.channelResId.equals(other.getChannelResId()))) &&
            ((this.cmtsResId==null && other.getCmtsResId()==null) || 
             (this.cmtsResId!=null &&
              this.cmtsResId.equals(other.getCmtsResId()))) &&
            ((this.channelName==null && other.getChannelName()==null) || 
             (this.channelName!=null &&
              this.channelName.equals(other.getChannelName()))) &&
            ((this.channelType==null && other.getChannelType()==null) || 
             (this.channelType!=null &&
              this.channelType.equals(other.getChannelType()))) &&
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
        if (getChannelResId() != null) {
            _hashCode += getChannelResId().hashCode();
        }
        if (getCmtsResId() != null) {
            _hashCode += getCmtsResId().hashCode();
        }
        if (getChannelName() != null) {
            _hashCode += getChannelName().hashCode();
        }
        if (getChannelType() != null) {
            _hashCode += getChannelType().hashCode();
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
        new org.apache.axis.description.TypeDesc(ChannelT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ChannelT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channelResId"));
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
        elemField.setFieldName("channelName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channelName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channelType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ChannelTypeT"));
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
