/**
 * CurrentAlarmT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
import com.palmyrasyscorp.db.tables.CurrentAlarm;

public class CurrentAlarmT implements java.io.Serializable, Comparable {
	private com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT abstractAlarm;

	public CurrentAlarmT() {
	}

	public CurrentAlarmT(
			com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT abstractAlarm) {
		this.abstractAlarm = abstractAlarm;
	}

	/**
	 * @author Prem
	 * @param dbAlarm
	 */
	public CurrentAlarmT(CurrentAlarm dbAlarm) {
		this.abstractAlarm = new AbstractAlarmT(dbAlarm);
	}

	/**
	 * Gets the abstractAlarm value for this CurrentAlarmT.
	 * 
	 * @return abstractAlarm
	 */
	public com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT getAbstractAlarm() {
		return abstractAlarm;
	}

	/**
	 * Sets the abstractAlarm value for this CurrentAlarmT.
	 * 
	 * @param abstractAlarm
	 */
	public void setAbstractAlarm(
			com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT abstractAlarm) {
		this.abstractAlarm = abstractAlarm;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof CurrentAlarmT))
			return false;
		CurrentAlarmT other = (CurrentAlarmT) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.abstractAlarm == null && other
				.getAbstractAlarm() == null) || (this.abstractAlarm != null && this.abstractAlarm
				.equals(other.getAbstractAlarm())));
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
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			CurrentAlarmT.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://www.palmyrasys.com/webservices/CableAssurance",
				"CurrentAlarmT"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("abstractAlarm");
		elemField
				.setXmlName(new javax.xml.namespace.QName("", "abstractAlarm"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.palmyrasys.com/webservices/CableAssurance",
				"AbstractAlarmT"));
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

	/**
	 * @author Prem
	 */
	public int compareTo(Object o) {
		Long ret;

		CurrentAlarmT otherCmts = (CurrentAlarmT) o;

		long tmp = this.abstractAlarm.getAlarmTime().longValue()
				- otherCmts.abstractAlarm.getAlarmTime().longValue();
		if (tmp != 0) {
			return ((int) tmp);
		}

		tmp = this.abstractAlarm.getAlarmTimeUSec().longValue()
				- otherCmts.abstractAlarm.getAlarmTimeUSec().longValue();

		ret = new Long(tmp);

		return (ret.intValue());
	}

}
