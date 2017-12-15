/**
 * GenericCountsHistoryT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.*;

public class GenericCountsHistoryT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(GenericCountsHistoryT.class);

    private com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts;

    private java.math.BigInteger timeSec;

    public GenericCountsHistoryT() {
    }

    public GenericCountsHistoryT(
           com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts,
           java.math.BigInteger timeSec) {
           this.counts = counts;
           this.timeSec = timeSec;
    }


    /**
     * 
     * @param dbChnlCntsHist
     */
    public GenericCountsHistoryT(AbstractCountsHistory dbChnlCntsHist) {
    	try {
	    	this.counts = new GenericCountsT(dbChnlCntsHist);
	    	this.timeSec = new BigInteger(dbChnlCntsHist.getTimeSec().toString());
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    
    /**
     * Gets the counts value for this GenericCountsHistoryT.
     * 
     * @return counts
     */
    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCounts() {
        return counts;
    }


    /**
     * Sets the counts value for this GenericCountsHistoryT.
     * 
     * @param counts
     */
    public void setCounts(com.palmyrasys.www.webservices.CableAssurance.GenericCountsT counts) {
        this.counts = counts;
    }


    /**
     * Gets the timeSec value for this GenericCountsHistoryT.
     * 
     * @return timeSec
     */
    public java.math.BigInteger getTimeSec() {
        return timeSec;
    }


    /**
     * Sets the timeSec value for this GenericCountsHistoryT.
     * 
     * @param timeSec
     */
    public void setTimeSec(java.math.BigInteger timeSec) {
        this.timeSec = timeSec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenericCountsHistoryT)) return false;
        GenericCountsHistoryT other = (GenericCountsHistoryT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.counts==null && other.getCounts()==null) || 
             (this.counts!=null &&
              this.counts.equals(other.getCounts()))) &&
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
        if (getCounts() != null) {
            _hashCode += getCounts().hashCode();
        }
        if (getTimeSec() != null) {
            _hashCode += getTimeSec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenericCountsHistoryT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsHistoryT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("counts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "counts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "GenericCountsT"));
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
