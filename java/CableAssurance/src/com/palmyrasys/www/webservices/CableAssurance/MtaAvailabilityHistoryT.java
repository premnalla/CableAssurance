/**
 * MtaAvailabilityHistoryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.*;

public class MtaAvailabilityHistoryT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(MtaAvailabilityHistoryT.class);

    private com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT available;

    private java.math.BigInteger timeSec;

    public MtaAvailabilityHistoryT() {
    }

    public MtaAvailabilityHistoryT(
           com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT available,
           java.math.BigInteger timeSec) {
           this.available = available;
           this.timeSec = timeSec;
    }

 
    /**
     * @author Prem
     * @param db
     */
    public MtaAvailabilityHistoryT(MtaAvailabilityHistory db) {
    	try {
	    	this.available = new MtaAvailabilityT(db);
	    	this.timeSec = new BigInteger(db.getTimeSec().toString());
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    		

    /**
     * Gets the available value for this MtaAvailabilityHistoryT.
     * 
     * @return available
     */
    public com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT getAvailable() {
        return available;
    }


    /**
     * Sets the available value for this MtaAvailabilityHistoryT.
     * 
     * @param available
     */
    public void setAvailable(com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT available) {
        this.available = available;
    }


    /**
     * Gets the timeSec value for this MtaAvailabilityHistoryT.
     * 
     * @return timeSec
     */
    public java.math.BigInteger getTimeSec() {
        return timeSec;
    }


    /**
     * Sets the timeSec value for this MtaAvailabilityHistoryT.
     * 
     * @param timeSec
     */
    public void setTimeSec(java.math.BigInteger timeSec) {
        this.timeSec = timeSec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaAvailabilityHistoryT)) return false;
        MtaAvailabilityHistoryT other = (MtaAvailabilityHistoryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.available==null && other.getAvailable()==null) || 
             (this.available!=null &&
              this.available.equals(other.getAvailable()))) &&
            ((this.timeSec==null && other.getTimeSec()==null) || 
             (this.timeSec!=null &&
              this.timeSec.equals(other.getTimeSec())));
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
        if (getAvailable() != null) {
            _hashCode += getAvailable().hashCode();
        }
        if (getTimeSec() != null) {
            _hashCode += getTimeSec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaAvailabilityHistoryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityHistoryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("available");
        elemField.setXmlName(new javax.xml.namespace.QName("", "available"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAvailabilityT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeSec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeSec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
