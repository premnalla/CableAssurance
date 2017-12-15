/**
 * MtaDataT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class MtaDataT  implements java.io.Serializable {
    private java.lang.String provStatus;

    private java.lang.String provCounter;

    private java.lang.String pingStatus;

    private java.lang.String batteryStatus1;

    private java.lang.String batteryStatus2;

    private java.lang.String field1;

    private java.lang.String field2;

    public MtaDataT() {
    }

    public MtaDataT(
           java.lang.String provStatus,
           java.lang.String provCounter,
           java.lang.String pingStatus,
           java.lang.String batteryStatus1,
           java.lang.String batteryStatus2,
           java.lang.String field1,
           java.lang.String field2) {
           this.provStatus = provStatus;
           this.provCounter = provCounter;
           this.pingStatus = pingStatus;
           this.batteryStatus1 = batteryStatus1;
           this.batteryStatus2 = batteryStatus2;
           this.field1 = field1;
           this.field2 = field2;
    }


    /**
     * Gets the provStatus value for this MtaDataT.
     * 
     * @return provStatus
     */
    public java.lang.String getProvStatus() {
        return provStatus;
    }


    /**
     * Sets the provStatus value for this MtaDataT.
     * 
     * @param provStatus
     */
    public void setProvStatus(java.lang.String provStatus) {
        this.provStatus = provStatus;
    }


    /**
     * Gets the provCounter value for this MtaDataT.
     * 
     * @return provCounter
     */
    public java.lang.String getProvCounter() {
        return provCounter;
    }


    /**
     * Sets the provCounter value for this MtaDataT.
     * 
     * @param provCounter
     */
    public void setProvCounter(java.lang.String provCounter) {
        this.provCounter = provCounter;
    }


    /**
     * Gets the pingStatus value for this MtaDataT.
     * 
     * @return pingStatus
     */
    public java.lang.String getPingStatus() {
        return pingStatus;
    }


    /**
     * Sets the pingStatus value for this MtaDataT.
     * 
     * @param pingStatus
     */
    public void setPingStatus(java.lang.String pingStatus) {
        this.pingStatus = pingStatus;
    }


    /**
     * Gets the batteryStatus1 value for this MtaDataT.
     * 
     * @return batteryStatus1
     */
    public java.lang.String getBatteryStatus1() {
        return batteryStatus1;
    }


    /**
     * Sets the batteryStatus1 value for this MtaDataT.
     * 
     * @param batteryStatus1
     */
    public void setBatteryStatus1(java.lang.String batteryStatus1) {
        this.batteryStatus1 = batteryStatus1;
    }


    /**
     * Gets the batteryStatus2 value for this MtaDataT.
     * 
     * @return batteryStatus2
     */
    public java.lang.String getBatteryStatus2() {
        return batteryStatus2;
    }


    /**
     * Sets the batteryStatus2 value for this MtaDataT.
     * 
     * @param batteryStatus2
     */
    public void setBatteryStatus2(java.lang.String batteryStatus2) {
        this.batteryStatus2 = batteryStatus2;
    }


    /**
     * Gets the field1 value for this MtaDataT.
     * 
     * @return field1
     */
    public java.lang.String getField1() {
        return field1;
    }


    /**
     * Sets the field1 value for this MtaDataT.
     * 
     * @param field1
     */
    public void setField1(java.lang.String field1) {
        this.field1 = field1;
    }


    /**
     * Gets the field2 value for this MtaDataT.
     * 
     * @return field2
     */
    public java.lang.String getField2() {
        return field2;
    }


    /**
     * Sets the field2 value for this MtaDataT.
     * 
     * @param field2
     */
    public void setField2(java.lang.String field2) {
        this.field2 = field2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaDataT)) return false;
        MtaDataT other = (MtaDataT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.provStatus==null && other.getProvStatus()==null) || 
             (this.provStatus!=null &&
              this.provStatus.equals(other.getProvStatus()))) &&
            ((this.provCounter==null && other.getProvCounter()==null) || 
             (this.provCounter!=null &&
              this.provCounter.equals(other.getProvCounter()))) &&
            ((this.pingStatus==null && other.getPingStatus()==null) || 
             (this.pingStatus!=null &&
              this.pingStatus.equals(other.getPingStatus()))) &&
            ((this.batteryStatus1==null && other.getBatteryStatus1()==null) || 
             (this.batteryStatus1!=null &&
              this.batteryStatus1.equals(other.getBatteryStatus1()))) &&
            ((this.batteryStatus2==null && other.getBatteryStatus2()==null) || 
             (this.batteryStatus2!=null &&
              this.batteryStatus2.equals(other.getBatteryStatus2()))) &&
            ((this.field1==null && other.getField1()==null) || 
             (this.field1!=null &&
              this.field1.equals(other.getField1()))) &&
            ((this.field2==null && other.getField2()==null) || 
             (this.field2!=null &&
              this.field2.equals(other.getField2())));
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
        if (getProvStatus() != null) {
            _hashCode += getProvStatus().hashCode();
        }
        if (getProvCounter() != null) {
            _hashCode += getProvCounter().hashCode();
        }
        if (getPingStatus() != null) {
            _hashCode += getPingStatus().hashCode();
        }
        if (getBatteryStatus1() != null) {
            _hashCode += getBatteryStatus1().hashCode();
        }
        if (getBatteryStatus2() != null) {
            _hashCode += getBatteryStatus2().hashCode();
        }
        if (getField1() != null) {
            _hashCode += getField1().hashCode();
        }
        if (getField2() != null) {
            _hashCode += getField2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaDataT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaDataT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provCounter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provCounter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pingStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pingStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batteryStatus1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "batteryStatus1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batteryStatus2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "batteryStatus2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "field1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "field2"));
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
