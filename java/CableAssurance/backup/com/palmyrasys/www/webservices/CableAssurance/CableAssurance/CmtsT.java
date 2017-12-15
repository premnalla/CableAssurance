/**
 * CmtsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import com.palmyrasyscorp.db.tables.Cmts;

//Prem: added comparable
public class CmtsT  implements java.io.Serializable, Comparable {
// public class CmtsT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger cmtsResId;

    private java.lang.String cmtsName;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    private com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts;

    public CmtsT() {
    }

    public CmtsT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger cmtsResId,
           java.lang.String cmtsName,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor,
           com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
           this.topologyKey = topologyKey;
           this.cmtsResId = cmtsResId;
           this.cmtsName = cmtsName;
           this.statusColor = statusColor;
           this.counts = counts;
    }

    /**
     * Prem: adde data constructor
     * @author Prem
     * @param c
     */
    public CmtsT(Cmts c) {
    	this.cmtsName = c.getName();
    	this.cmtsResId = new java.math.BigInteger(c.getCmtsResId().toString());
    	// this.statusColor = StatusColorT.Green;
    	this.counts = new GenericCountsT();
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

    /**
     * Prem: added compareTo() method
     */
    public int compareTo(Object o) {
    	int ret;
    	
    	CmtsT otherCmts = (CmtsT)o;
    	
    	ret = this.cmtsName.compareTo(otherCmts.getCmtsName());
    	
    	return (ret);
    }

}
