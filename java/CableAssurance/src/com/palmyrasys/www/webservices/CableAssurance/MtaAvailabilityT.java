/**
 * MtaAvailabilityT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.*;

public class MtaAvailabilityT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(MtaAvailabilityT.class);

    private short available;

    public MtaAvailabilityT() {
    }

    public MtaAvailabilityT(
           short available) {
           this.available = available;
    }


    /**
     * @author Prem
     * @param db
     */
    public MtaAvailabilityT(MtaAvailabilityHistory db) {
    	try {
	    	if (db.getAvailability().booleanValue()) {
	    		this.available = 1;
	    	}
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    
    
    /**
     * Gets the available value for this MtaAvailabilityT.
     * 
     * @return available
     */
    public short getAvailable() {
        return available;
    }


    /**
     * Sets the available value for this MtaAvailabilityT.
     * 
     * @param available
     */
    public void setAvailable(short available) {
        this.available = available;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaAvailabilityT)) return false;
        MtaAvailabilityT other = (MtaAvailabilityT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.available == other.getAvailable();
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
        _hashCode += getAvailable();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaAvailabilityT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("available");
        elemField.setXmlName(new javax.xml.namespace.QName("", "available"));
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