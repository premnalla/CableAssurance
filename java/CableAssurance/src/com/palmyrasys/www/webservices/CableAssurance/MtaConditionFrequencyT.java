/**
 * MtaConditionFrequencyT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem: sub-class from AbstractCAServiceObject & Implement interface UniqueObject
import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCAServiceObject;
import com.palmyrasys.www.webservices.CableAssurance.Common.UniqueObject;

public class MtaConditionFrequencyT extends AbstractCAServiceObject implements java.io.Serializable, UniqueObject {
    private com.palmyrasys.www.webservices.CableAssurance.ConditionFrequencyT condFrequency;

    private java.math.BigInteger cmtsResId;

    private java.lang.String cmtsName;

    private java.math.BigInteger hfcResId;

    private java.lang.String hfcName;

    public MtaConditionFrequencyT() {
    }

    public MtaConditionFrequencyT(
           com.palmyrasys.www.webservices.CableAssurance.ConditionFrequencyT condFrequency,
           java.math.BigInteger cmtsResId,
           java.lang.String cmtsName,
           java.math.BigInteger hfcResId,
           java.lang.String hfcName) {
           this.condFrequency = condFrequency;
           this.cmtsResId = cmtsResId;
           this.cmtsName = cmtsName;
           this.hfcResId = hfcResId;
           this.hfcName = hfcName;
    }


    /**
     * @author Prem
     */
    public int compareTo(Object m) {
    	MtaConditionFrequencyT inO = (MtaConditionFrequencyT) m;
   		short thisVal = this.condFrequency.getFrequency();
   		short inVal = inO.getCondFrequency().getFrequency();
   		
    	if (thisVal < inVal) {
    		return (-1);
    	}
    	
    	if (thisVal > inVal) {
    		return (1);
    	}
    	
    	String thisMac = this.condFrequency.getName();
    	String inMac = inO.getCondFrequency().getName();
    	
    	return (thisMac.compareTo(inMac));
    }

	/**
	 * Part of UniqeObject Interface
	 * @author Prem
	 * @return
	 */
	public TopoHierarchyKeyT getTopologyHierarchyKey() {
		return (this.condFrequency.getTopologyKey());
	}
	
	/**
	 * Part of UniqeObject Interface
	 * @author Prem
	 * @return
	 */
	public long getId() {
		return (this.condFrequency.getResId().longValue());
	}

    /**
     * Gets the condFrequency value for this MtaConditionFrequencyT.
     * 
     * @return condFrequency
     */
    public com.palmyrasys.www.webservices.CableAssurance.ConditionFrequencyT getCondFrequency() {
        return condFrequency;
    }


    /**
     * Sets the condFrequency value for this MtaConditionFrequencyT.
     * 
     * @param condFrequency
     */
    public void setCondFrequency(com.palmyrasys.www.webservices.CableAssurance.ConditionFrequencyT condFrequency) {
        this.condFrequency = condFrequency;
    }


    /**
     * Gets the cmtsResId value for this MtaConditionFrequencyT.
     * 
     * @return cmtsResId
     */
    public java.math.BigInteger getCmtsResId() {
        return cmtsResId;
    }


    /**
     * Sets the cmtsResId value for this MtaConditionFrequencyT.
     * 
     * @param cmtsResId
     */
    public void setCmtsResId(java.math.BigInteger cmtsResId) {
        this.cmtsResId = cmtsResId;
    }


    /**
     * Gets the cmtsName value for this MtaConditionFrequencyT.
     * 
     * @return cmtsName
     */
    public java.lang.String getCmtsName() {
        return cmtsName;
    }


    /**
     * Sets the cmtsName value for this MtaConditionFrequencyT.
     * 
     * @param cmtsName
     */
    public void setCmtsName(java.lang.String cmtsName) {
        this.cmtsName = cmtsName;
    }


    /**
     * Gets the hfcResId value for this MtaConditionFrequencyT.
     * 
     * @return hfcResId
     */
    public java.math.BigInteger getHfcResId() {
        return hfcResId;
    }


    /**
     * Sets the hfcResId value for this MtaConditionFrequencyT.
     * 
     * @param hfcResId
     */
    public void setHfcResId(java.math.BigInteger hfcResId) {
        this.hfcResId = hfcResId;
    }


    /**
     * Gets the hfcName value for this MtaConditionFrequencyT.
     * 
     * @return hfcName
     */
    public java.lang.String getHfcName() {
        return hfcName;
    }


    /**
     * Sets the hfcName value for this MtaConditionFrequencyT.
     * 
     * @param hfcName
     */
    public void setHfcName(java.lang.String hfcName) {
        this.hfcName = hfcName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaConditionFrequencyT)) return false;
        MtaConditionFrequencyT other = (MtaConditionFrequencyT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.condFrequency==null && other.getCondFrequency()==null) || 
             (this.condFrequency!=null &&
              this.condFrequency.equals(other.getCondFrequency()))) &&
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
        if (getCondFrequency() != null) {
            _hashCode += getCondFrequency().hashCode();
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
        new org.apache.axis.description.TypeDesc(MtaConditionFrequencyT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaConditionFrequencyT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("condFrequency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "condFrequency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ConditionFrequencyT"));
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
