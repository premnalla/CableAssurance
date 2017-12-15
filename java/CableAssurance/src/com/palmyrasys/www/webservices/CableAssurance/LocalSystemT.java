/**
 * LocalSystemT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.LocalSystem;
import com.palmyrasyscorp.www.webservices.helper.LocalSystemHelper;

public class LocalSystemT implements java.io.Serializable {

	private static Log log = LogFactory.getLog(LocalSystemT.class);

	private com.palmyrasys.www.webservices.CableAssurance.SystemTypeT systemType;

	private java.lang.String systemName;

	private java.lang.String parentHost;

	public LocalSystemT() {
	}

	public LocalSystemT(
			com.palmyrasys.www.webservices.CableAssurance.SystemTypeT systemType,
			java.lang.String systemName, java.lang.String parentHost) {
		this.systemType = systemType;
		this.systemName = systemName;
		this.parentHost = parentHost;
	}

	/**
	 * 
	 * @author Prem
	 * @param dbLs
	 */
	public LocalSystemT(LocalSystem dbLs) {
		try {
			// LocalSystemHelper lsh = new LocalSystemHelper();
			this.systemType = LocalSystemHelper.getSystemType(dbLs.getSystemType()
					.intValue());
			this.systemName = dbLs.getSystemName();
			this.parentHost = dbLs.getParentHost();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);				
		}
	}

	/**
	 * Gets the systemType value for this LocalSystemT.
	 * 
	 * @return systemType
	 */
	public com.palmyrasys.www.webservices.CableAssurance.SystemTypeT getSystemType() {
		return systemType;
	}

	/**
	 * Sets the systemType value for this LocalSystemT.
	 * 
	 * @param systemType
	 */
	public void setSystemType(
			com.palmyrasys.www.webservices.CableAssurance.SystemTypeT systemType) {
		this.systemType = systemType;
	}

	/**
	 * Gets the systemName value for this LocalSystemT.
	 * 
	 * @return systemName
	 */
	public java.lang.String getSystemName() {
		return systemName;
	}

	/**
	 * Sets the systemName value for this LocalSystemT.
	 * 
	 * @param systemName
	 */
	public void setSystemName(java.lang.String systemName) {
		this.systemName = systemName;
	}

	/**
	 * Gets the parentHost value for this LocalSystemT.
	 * 
	 * @return parentHost
	 */
	public java.lang.String getParentHost() {
		return parentHost;
	}

	/**
	 * Sets the parentHost value for this LocalSystemT.
	 * 
	 * @param parentHost
	 */
	public void setParentHost(java.lang.String parentHost) {
		this.parentHost = parentHost;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof LocalSystemT))
			return false;
		LocalSystemT other = (LocalSystemT) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.systemType == null && other.getSystemType() == null) || (this.systemType != null && this.systemType
						.equals(other.getSystemType())))
				&& ((this.systemName == null && other.getSystemName() == null) || (this.systemName != null && this.systemName
						.equals(other.getSystemName())))
				&& ((this.parentHost == null && other.getParentHost() == null) || (this.parentHost != null && this.parentHost
						.equals(other.getParentHost())));
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
		if (getSystemType() != null) {
			_hashCode += getSystemType().hashCode();
		}
		if (getSystemName() != null) {
			_hashCode += getSystemName().hashCode();
		}
		if (getParentHost() != null) {
			_hashCode += getParentHost().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			LocalSystemT.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://www.palmyrasys.com/webservices/CableAssurance",
				"LocalSystemT"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("systemType");
		elemField.setXmlName(new javax.xml.namespace.QName("", "systemType"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.palmyrasys.com/webservices/CableAssurance",
				"SystemTypeT"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("systemName");
		elemField.setXmlName(new javax.xml.namespace.QName("", "systemName"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("parentHost");
		elemField.setXmlName(new javax.xml.namespace.QName("", "parentHost"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
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
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}

}
