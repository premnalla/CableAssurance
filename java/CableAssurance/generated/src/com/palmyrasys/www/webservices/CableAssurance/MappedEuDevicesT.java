/**
 * MappedEuDevicesT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class MappedEuDevicesT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CableModemT cm;

    private com.palmyrasys.www.webservices.CableAssurance.EmtaT mta;

    public MappedEuDevicesT() {
    }

    public MappedEuDevicesT(
           com.palmyrasys.www.webservices.CableAssurance.CableModemT cm,
           com.palmyrasys.www.webservices.CableAssurance.EmtaT mta) {
           this.cm = cm;
           this.mta = mta;
    }


    /**
     * Gets the cm value for this MappedEuDevicesT.
     * 
     * @return cm
     */
    public com.palmyrasys.www.webservices.CableAssurance.CableModemT getCm() {
        return cm;
    }


    /**
     * Sets the cm value for this MappedEuDevicesT.
     * 
     * @param cm
     */
    public void setCm(com.palmyrasys.www.webservices.CableAssurance.CableModemT cm) {
        this.cm = cm;
    }


    /**
     * Gets the mta value for this MappedEuDevicesT.
     * 
     * @return mta
     */
    public com.palmyrasys.www.webservices.CableAssurance.EmtaT getMta() {
        return mta;
    }


    /**
     * Sets the mta value for this MappedEuDevicesT.
     * 
     * @param mta
     */
    public void setMta(com.palmyrasys.www.webservices.CableAssurance.EmtaT mta) {
        this.mta = mta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MappedEuDevicesT)) return false;
        MappedEuDevicesT other = (MappedEuDevicesT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cm==null && other.getCm()==null) || 
             (this.cm!=null &&
              this.cm.equals(other.getCm()))) &&
            ((this.mta==null && other.getMta()==null) || 
             (this.mta!=null &&
              this.mta.equals(other.getMta())));
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
        if (getCm() != null) {
            _hashCode += getCm().hashCode();
        }
        if (getMta() != null) {
            _hashCode += getMta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MappedEuDevicesT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MappedEuDevicesT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CableModemT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EmtaT"));
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
