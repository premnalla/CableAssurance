/**
 * SnmpV2CAttributesT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.SnmpV2CAttributes;

public class SnmpV2CAttributesT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(SnmpV2CAttributesT.class);

    private java.lang.String readCommnunity;

    private java.lang.String writeCommnunity;

    public SnmpV2CAttributesT() {
    }

    public SnmpV2CAttributesT(
           java.lang.String readCommnunity,
           java.lang.String writeCommnunity) {
           this.readCommnunity = readCommnunity;
           this.writeCommnunity = writeCommnunity;
    }


    /**
     * @author Prem
     * @param dbV2CAttrs
     */
    public SnmpV2CAttributesT(SnmpV2CAttributes dbV2CAttrs) {
    	try {
	        this.readCommnunity = dbV2CAttrs.getReadCommunity();
	        this.writeCommnunity = dbV2CAttrs.getWriteCommunity();
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    
    
    /**
     * Gets the readCommnunity value for this SnmpV2CAttributesT.
     * 
     * @return readCommnunity
     */
    public java.lang.String getReadCommnunity() {
        return readCommnunity;
    }


    /**
     * Sets the readCommnunity value for this SnmpV2CAttributesT.
     * 
     * @param readCommnunity
     */
    public void setReadCommnunity(java.lang.String readCommnunity) {
        this.readCommnunity = readCommnunity;
    }


    /**
     * Gets the writeCommnunity value for this SnmpV2CAttributesT.
     * 
     * @return writeCommnunity
     */
    public java.lang.String getWriteCommnunity() {
        return writeCommnunity;
    }


    /**
     * Sets the writeCommnunity value for this SnmpV2CAttributesT.
     * 
     * @param writeCommnunity
     */
    public void setWriteCommnunity(java.lang.String writeCommnunity) {
        this.writeCommnunity = writeCommnunity;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SnmpV2CAttributesT)) return false;
        SnmpV2CAttributesT other = (SnmpV2CAttributesT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.readCommnunity==null && other.getReadCommnunity()==null) || 
             (this.readCommnunity!=null &&
              this.readCommnunity.equals(other.getReadCommnunity()))) &&
            ((this.writeCommnunity==null && other.getWriteCommnunity()==null) || 
             (this.writeCommnunity!=null &&
              this.writeCommnunity.equals(other.getWriteCommnunity())));
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
        if (getReadCommnunity() != null) {
            _hashCode += getReadCommnunity().hashCode();
        }
        if (getWriteCommnunity() != null) {
            _hashCode += getWriteCommnunity().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SnmpV2CAttributesT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SnmpV2CAttributesT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("readCommnunity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "readCommnunity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("writeCommnunity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "writeCommnunity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
