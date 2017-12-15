/**
 * GenericCountsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.*;

public class GenericCountsT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(GenericCountsT.class);

    private short totalCm;

    private short onlineCm;

    private short totalMta;

    private short availableMta;

    public GenericCountsT() {
    }

    public GenericCountsT(
           short totalCm,
           short onlineCm,
           short totalMta,
           short availableMta) {
           this.totalCm = totalCm;
           this.onlineCm = onlineCm;
           this.totalMta = totalMta;
           this.availableMta = availableMta;
    }


    /**
     * 
     * @param dbCounts
     */
    public GenericCountsT(AbstractCountsHistory dbCounts) {
    	try {
	        this.totalCm = dbCounts.getCmTotal().shortValue();
	        this.onlineCm = dbCounts.getCmOnline().shortValue();
	        this.totalMta = dbCounts.getMtaTotal().shortValue();
	        this.availableMta = dbCounts.getMtaAvailable().shortValue();
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    

    /**
     * 
     * @param dbCounts
     */
    public GenericCountsT(CurrentCounts dbCounts) {
		try {
			this.totalCm = dbCounts.getCmTotal().shortValue();
			this.onlineCm = dbCounts.getCmOnline().shortValue();
			this.totalMta = dbCounts.getMtaTotal().shortValue();
			this.availableMta = dbCounts.getMtaAvailable().shortValue();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
	}
    

    /**
	 * Gets the totalCm value for this GenericCountsT.
	 * 
	 * @return totalCm
	 */
    public short getTotalCm() {
        return totalCm;
    }


    /**
     * Sets the totalCm value for this GenericCountsT.
     * 
     * @param totalCm
     */
    public void setTotalCm(short totalCm) {
        this.totalCm = totalCm;
    }


    /**
     * Gets the onlineCm value for this GenericCountsT.
     * 
     * @return onlineCm
     */
    public short getOnlineCm() {
        return onlineCm;
    }


    /**
     * Sets the onlineCm value for this GenericCountsT.
     * 
     * @param onlineCm
     */
    public void setOnlineCm(short onlineCm) {
        this.onlineCm = onlineCm;
    }


    /**
     * Gets the totalMta value for this GenericCountsT.
     * 
     * @return totalMta
     */
    public short getTotalMta() {
        return totalMta;
    }


    /**
     * Sets the totalMta value for this GenericCountsT.
     * 
     * @param totalMta
     */
    public void setTotalMta(short totalMta) {
        this.totalMta = totalMta;
    }


    /**
     * Gets the availableMta value for this GenericCountsT.
     * 
     * @return availableMta
     */
    public short getAvailableMta() {
        return availableMta;
    }


    /**
     * Sets the availableMta value for this GenericCountsT.
     * 
     * @param availableMta
     */
    public void setAvailableMta(short availableMta) {
        this.availableMta = availableMta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenericCountsT)) return false;
        GenericCountsT other = (GenericCountsT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totalCm == other.getTotalCm() &&
            this.onlineCm == other.getOnlineCm() &&
            this.totalMta == other.getTotalMta() &&
            this.availableMta == other.getAvailableMta();
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
        _hashCode += getTotalCm();
        _hashCode += getOnlineCm();
        _hashCode += getTotalMta();
        _hashCode += getAvailableMta();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenericCountsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalCm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlineCm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onlineCm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalMta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalMta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableMta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availableMta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
