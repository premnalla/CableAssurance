/**
 * HistoricalAlarmT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCAServiceObject;
import com.palmyrasys.www.webservices.CableAssurance.Common.UniqueObject;
import com.palmyrasyscorp.db.tables.HistoricalAlarm;

// Prem: extends AbstractCAServiceObject, UniqueObject
public class HistoricalAlarmT extends AbstractCAServiceObject implements
		java.io.Serializable, UniqueObject {
	private com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT abstractAlarm;

	private java.lang.String clearedUser;

	public HistoricalAlarmT() {
	}

	public HistoricalAlarmT(
			com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT abstractAlarm,
			java.lang.String clearedUser) {
		this.abstractAlarm = abstractAlarm;
		this.clearedUser = clearedUser;
	}

	/**
	 * @author Prem
	 * @param dbAlarm
	 */
	public HistoricalAlarmT(HistoricalAlarm dbAlarm) {
		this.abstractAlarm = new AbstractAlarmT(dbAlarm);
		this.clearedUser = "";
	}

	/**
	 * Part of UniqeObject Interface
	 * @author Prem
	 * @return
	 */
	public TopoHierarchyKeyT getTopologyHierarchyKey() {
		return (abstractAlarm.getTopologyKey());
	}
	
	/**
	 * Part of UniqeObject Interface
	 * @author Prem
	 * @return
	 */
	public long getId() {
		return (abstractAlarm.getAlarmId().longValue());
	}

	/**
	 * Gets the abstractAlarm value for this HistoricalAlarmT.
	 * 
	 * @return abstractAlarm
	 */
	public com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT getAbstractAlarm() {
		return abstractAlarm;
	}

	/**
	 * Sets the abstractAlarm value for this HistoricalAlarmT.
	 * 
	 * @param abstractAlarm
	 */
	public void setAbstractAlarm(
			com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT abstractAlarm) {
		this.abstractAlarm = abstractAlarm;
	}

	/**
	 * Gets the clearedUser value for this HistoricalAlarmT.
	 * 
	 * @return clearedUser
	 */
	public java.lang.String getClearedUser() {
		return clearedUser;
	}

	/**
	 * Sets the clearedUser value for this HistoricalAlarmT.
	 * 
	 * @param clearedUser
	 */
	public void setClearedUser(java.lang.String clearedUser) {
		this.clearedUser = clearedUser;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof HistoricalAlarmT))
			return false;
		HistoricalAlarmT other = (HistoricalAlarmT) obj;
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
				&& ((this.abstractAlarm == null && other.getAbstractAlarm() == null) || (this.abstractAlarm != null && this.abstractAlarm
						.equals(other.getAbstractAlarm())))
				&& ((this.clearedUser == null && other.getClearedUser() == null) || (this.clearedUser != null && this.clearedUser
						.equals(other.getClearedUser())));
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
		if (getAbstractAlarm() != null) {
			_hashCode += getAbstractAlarm().hashCode();
		}
		if (getClearedUser() != null) {
			_hashCode += getClearedUser().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			HistoricalAlarmT.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://www.palmyrasys.com/webservices/CableAssurance",
				"HistoricalAlarmT"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("abstractAlarm");
		elemField
				.setXmlName(new javax.xml.namespace.QName("", "abstractAlarm"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.palmyrasys.com/webservices/CableAssurance",
				"AbstractAlarmT"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("clearedUser");
		elemField.setXmlName(new javax.xml.namespace.QName("", "clearedUser"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
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

	/**
	 * @author Prem
	 */
	public int compareTo(Object o) {

		HistoricalAlarmT otherAlm = (HistoricalAlarmT) o;

		long tmp = this.abstractAlarm.getAlarmTime().longValue()
				- otherAlm.abstractAlarm.getAlarmTime().longValue();
		if (tmp != 0) {
			return ((int) tmp);
		}

		tmp = this.abstractAlarm.getAlarmTimeUSec().longValue()
				- otherAlm.abstractAlarm.getAlarmTimeUSec().longValue();

		return ((int) tmp);
	}

}
