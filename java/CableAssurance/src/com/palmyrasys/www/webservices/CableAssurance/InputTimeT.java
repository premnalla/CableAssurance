/**
 * InputTimeT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class InputTimeT  implements java.io.Serializable {
    private short year;

    private short monthOfYear;

    private short dayOfMonth;

    private short hourOfDay;

    private short minuteOfHour;

    public InputTimeT() {
    }

    public InputTimeT(
           short year,
           short monthOfYear,
           short dayOfMonth,
           short hourOfDay,
           short minuteOfHour) {
           this.year = year;
           this.monthOfYear = monthOfYear;
           this.dayOfMonth = dayOfMonth;
           this.hourOfDay = hourOfDay;
           this.minuteOfHour = minuteOfHour;
    }


    /**
     * Gets the year value for this InputTimeT.
     * 
     * @return year
     */
    public short getYear() {
        return year;
    }


    /**
     * Sets the year value for this InputTimeT.
     * 
     * @param year
     */
    public void setYear(short year) {
        this.year = year;
    }


    /**
     * Gets the monthOfYear value for this InputTimeT.
     * 
     * @return monthOfYear
     */
    public short getMonthOfYear() {
        return monthOfYear;
    }


    /**
     * Sets the monthOfYear value for this InputTimeT.
     * 
     * @param monthOfYear
     */
    public void setMonthOfYear(short monthOfYear) {
        this.monthOfYear = monthOfYear;
    }


    /**
     * Gets the dayOfMonth value for this InputTimeT.
     * 
     * @return dayOfMonth
     */
    public short getDayOfMonth() {
        return dayOfMonth;
    }


    /**
     * Sets the dayOfMonth value for this InputTimeT.
     * 
     * @param dayOfMonth
     */
    public void setDayOfMonth(short dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


    /**
     * Gets the hourOfDay value for this InputTimeT.
     * 
     * @return hourOfDay
     */
    public short getHourOfDay() {
        return hourOfDay;
    }


    /**
     * Sets the hourOfDay value for this InputTimeT.
     * 
     * @param hourOfDay
     */
    public void setHourOfDay(short hourOfDay) {
        this.hourOfDay = hourOfDay;
    }


    /**
     * Gets the minuteOfHour value for this InputTimeT.
     * 
     * @return minuteOfHour
     */
    public short getMinuteOfHour() {
        return minuteOfHour;
    }


    /**
     * Sets the minuteOfHour value for this InputTimeT.
     * 
     * @param minuteOfHour
     */
    public void setMinuteOfHour(short minuteOfHour) {
        this.minuteOfHour = minuteOfHour;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InputTimeT)) return false;
        InputTimeT other = (InputTimeT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.year == other.getYear() &&
            this.monthOfYear == other.getMonthOfYear() &&
            this.dayOfMonth == other.getDayOfMonth() &&
            this.hourOfDay == other.getHourOfDay() &&
            this.minuteOfHour == other.getMinuteOfHour();
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
        _hashCode += getYear();
        _hashCode += getMonthOfYear();
        _hashCode += getDayOfMonth();
        _hashCode += getHourOfDay();
        _hashCode += getMinuteOfHour();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InputTimeT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "InputTimeT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("", "year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthOfYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monthOfYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dayOfMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dayOfMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hourOfDay");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hourOfDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minuteOfHour");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minuteOfHour"));
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
