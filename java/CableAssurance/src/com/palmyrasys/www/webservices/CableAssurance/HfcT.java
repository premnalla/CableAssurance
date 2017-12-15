/**
 * HfcT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.HfcPlant;
import com.palmyrasyscorp.www.jsp.helper.StatusColorHelper;

public class HfcT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(HfcT.class);

    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger hfcResId;

    private java.math.BigInteger cmtsResId;

    private java.lang.String hfcName;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    private com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts;

    public HfcT() {
    }

    public HfcT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger hfcResId,
           java.math.BigInteger cmtsResId,
           java.lang.String hfcName,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor,
           com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
           this.topologyKey = topologyKey;
           this.hfcResId = hfcResId;
           this.cmtsResId = cmtsResId;
           this.hfcName = hfcName;
           this.statusColor = statusColor;
           this.counts = counts;
    }

    /**
     * @author Prem
     * @param dbHfc
     */
    public HfcT(HfcPlant dbHfc) {
    	try {
	        this.hfcResId = new BigInteger(dbHfc.getHfcResId().toString());
	        this.cmtsResId = new BigInteger(dbHfc.getCmtsResId().toString());
	        this.hfcName = dbHfc.getName();
	        // this.statusColor = StatusColorT.Green;
	        this.statusColor = 
	        	StatusColorHelper.AlertLevelToStatusColor(dbHfc);
	    	if (dbHfc.getCurrentCounts().getCmTotal() != null) {
	    		this.counts = new GenericCountsT(dbHfc.getCurrentCounts());
	    	}
	    	// this.counts = new GenericCountsT();
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    
    /**
     * Gets the topologyKey value for this HfcT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this HfcT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the hfcResId value for this HfcT.
     * 
     * @return hfcResId
     */
    public java.math.BigInteger getHfcResId() {
        return hfcResId;
    }


    /**
     * Sets the hfcResId value for this HfcT.
     * 
     * @param hfcResId
     */
    public void setHfcResId(java.math.BigInteger hfcResId) {
        this.hfcResId = hfcResId;
    }


    /**
     * Gets the cmtsResId value for this HfcT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this HfcT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the hfcName value for this HfcT.
     * 
     * @return hfcName
     */
    public java.lang.String getHfcName() {
        return hfcName;
    }


    /**
     * Sets the hfcName value for this HfcT.
     * 
     * @param hfcName
     */
    public void setHfcName(java.lang.String hfcName) {
        this.hfcName = hfcName;
    }


    /**
     * Gets the statusColor value for this HfcT.
     * 
     * @return statusColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this HfcT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }


    /**
     * Gets the counts value for this HfcT.
     * 
     * @return counts
     */
    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCounts() {
        return counts;
    }


    /**
     * Sets the counts value for this HfcT.
     * 
     * @param counts
     */
    public void setCounts(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
        this.counts = counts;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HfcT)) return false;
        HfcT other = (HfcT) obj;
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
            ((this.hfcResId==null && other.getHfcResId()==null) || 
             (this.hfcResId!=null &&
              this.hfcResId.equals(other.getHfcResId()))) &&
            ((this.cmtsResId==null && other.getCmtsResId()==null) || 
             (this.cmtsResId!=null &&
              this.cmtsResId.equals(other.getCmtsResId()))) &&
            ((this.hfcName==null && other.getHfcName()==null) || 
             (this.hfcName!=null &&
              this.hfcName.equals(other.getHfcName()))) &&
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
        if (getHfcResId() != null) {
            _hashCode += getHfcResId().hashCode();
        }
        if (getCmtsResId() != null) {
            _hashCode += getCmtsResId().hashCode();
        }
        if (getHfcName() != null) {
            _hashCode += getHfcName().hashCode();
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
        new org.apache.axis.description.TypeDesc(HfcT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcResId"));
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
        elemField.setFieldName("hfcName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcName"));
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
