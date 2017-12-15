/**
 * CmtsAlarmConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmtsAlarmConfigT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT cmtsDown;

    public CmtsAlarmConfigT() {
    }

    public CmtsAlarmConfigT(
           com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT cmtsDown) {
           this.cmtsDown = cmtsDown;
    }


    /**
     * Gets the cmtsDown value for this CmtsAlarmConfigT.
     * 
     * @return cmtsDown
     */
    public com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT getCmtsDown() {
        return cmtsDown;
    }


    /**
     * Sets the cmtsDown value for this CmtsAlarmConfigT.
     * 
     * @param cmtsDown
     */
    public void setCmtsDown(com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT cmtsDown) {
        this.cmtsDown = cmtsDown;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmtsAlarmConfigT)) return false;
        CmtsAlarmConfigT other = (CmtsAlarmConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmtsDown==null && other.getCmtsDown()==null) || 
             (this.cmtsDown!=null &&
              this.cmtsDown.equals(other.getCmtsDown())));
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
        if (getCmtsDown() != null) {
            _hashCode += getCmtsDown().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmtsAlarmConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsAlarmConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmtsDown");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsDown"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AlarmTypeConfigT"));
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
