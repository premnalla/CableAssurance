/**
 * MtaStatusSummaryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCAServiceObject;
import com.palmyrasys.www.webservices.CableAssurance.Common.UniqueObject;
import com.palmyrasyscorp.db.tables.MtaStatusSummary;

// Prem: extends AbstractCAServiceObject
public class MtaStatusSummaryT extends AbstractCAServiceObject implements java.io.Serializable, UniqueObject {

	private static Log log = LogFactory.getLog(MtaStatusSummaryT.class);

    private com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT statusSummary;

    private java.math.BigInteger cmtsResId;

    private java.lang.String cmtsName;

    private java.math.BigInteger hfcResId;

    private java.lang.String hfcName;

    public MtaStatusSummaryT() {
    }

    public MtaStatusSummaryT(
           com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT statusSummary,
           java.math.BigInteger cmtsResId,
           java.lang.String cmtsName,
           java.math.BigInteger hfcResId,
           java.lang.String hfcName) {
           this.statusSummary = statusSummary;
           this.cmtsResId = cmtsResId;
           this.cmtsName = cmtsName;
           this.hfcResId = hfcResId;
           this.hfcName = hfcName;
    }


    /**
     * @author Prem
     * @param dbSumm
     */
    public MtaStatusSummaryT(MtaStatusSummary dbSumm) {
    	try {
	        this.statusSummary = new StatusSummaryT(
	        		null, 
	        		new BigInteger(dbSumm.getResId().toString()),
	        		dbSumm.getMtaMac(), 
	        		new BigInteger(dbSumm.getSumBadStateTime().toString()),
	        		new BigInteger(dbSumm.getSumGoodStateTime().toString()),
	        		dbSumm.getSumStateChages().shortValue());
	        this.cmtsResId = new BigInteger(dbSumm.getCmtsResId().toString());
	        this.cmtsName = dbSumm.getCmtsName();
	        this.hfcResId = new BigInteger(dbSumm.getHfcResId().toString());
	        this.hfcName = dbSumm.getHfcName();
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    
    
    /**
     * @author Prem
     */
    public int compareTo(Object m) {
    	MtaStatusSummaryT inO = (MtaStatusSummaryT) m;
   		long thisVal = statusSummary.getSumRedStatusTime().longValue();
   		long inVal = inO.getStatusSummary().getSumRedStatusTime().longValue();
   		
    	if (thisVal < inVal) {
    		return (-1);
    	}
    	
    	if (thisVal > inVal) {
    		return (1);
    	}
    	
    	String thisMac = statusSummary.getName();
    	String inMac = inO.getStatusSummary().getName();
    	
    	return (thisMac.compareTo(inMac));
    }

	/**
	 * Part of UniqeObject Interface
	 * @author Prem
	 * @return
	 */
	public TopoHierarchyKeyT getTopologyHierarchyKey() {
		return (statusSummary.getTopologyKey());
	}
	
	/**
	 * Part of UniqeObject Interface
	 * @author Prem
	 * @return
	 */
	public long getId() {
		return (statusSummary.getResId().longValue());
	}


    /**
     * Gets the statusSummary value for this MtaStatusSummaryT.
     * 
     * @return statusSummary
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT getStatusSummary() {
        return statusSummary;
    }


    /**
     * Sets the statusSummary value for this MtaStatusSummaryT.
     * 
     * @param statusSummary
     */
    public void setStatusSummary(com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT statusSummary) {
        this.statusSummary = statusSummary;
    }


    /**
     * Gets the cmtsResId value for this MtaStatusSummaryT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this MtaStatusSummaryT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the cmtsName value for this MtaStatusSummaryT.
     * 
     * @return cmtsName
     */
    public java.lang.String getCmtsName() {
        return cmtsName;
    }


    /**
     * Sets the cmtsName value for this MtaStatusSummaryT.
     * 
     * @param cmtsName
     */
    public void setCmtsName(java.lang.String cmtsName) {
        this.cmtsName = cmtsName;
    }


    /**
     * Gets the hfcResId value for this MtaStatusSummaryT.
     * 
     * @return hfcResId
     */
    public java.math.BigInteger getHfcResId() {
        return hfcResId;
    }


    /**
     * Sets the hfcResId value for this MtaStatusSummaryT.
     * 
     * @param hfcResId
     */
    public void setHfcResId(java.math.BigInteger hfcResId) {
        this.hfcResId = hfcResId;
    }


    /**
     * Gets the hfcName value for this MtaStatusSummaryT.
     * 
     * @return hfcName
     */
    public java.lang.String getHfcName() {
        return hfcName;
    }


    /**
     * Sets the hfcName value for this MtaStatusSummaryT.
     * 
     * @param hfcName
     */
    public void setHfcName(java.lang.String hfcName) {
        this.hfcName = hfcName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaStatusSummaryT)) return false;
        MtaStatusSummaryT other = (MtaStatusSummaryT) obj;
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
              this.cmtsName.equals(other.getCmtsName()))) &&
            ((this.hfcResId==null && other.getHfcResId()==null) || 
             (this.hfcResId!=null &&
              this.hfcResId.equals(other.getHfcResId()))) &&
            ((this.hfcName==null && other.getHfcName()==null) || 
             (this.hfcName!=null &&
              this.hfcName.equals(other.getHfcName())));
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
        if (getHfcResId() != null) {
            _hashCode += getHfcResId().hashCode();
        }
        if (getHfcName() != null) {
            _hashCode += getHfcName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaStatusSummaryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaStatusSummaryT"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcName"));
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
