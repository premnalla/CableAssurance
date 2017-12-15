/**
 * CmsAlarmConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmsAlarmConfigT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT cmsLossOfComm;

    public CmsAlarmConfigT() {
    }

    public CmsAlarmConfigT(
           com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT cmsLossOfComm) {
           this.cmsLossOfComm = cmsLossOfComm;
    }


    /**
     * Gets the cmsLossOfComm value for this CmsAlarmConfigT.
     * 
     * @return cmsLossOfComm
     */
    public com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT getCmsLossOfComm() {
        return cmsLossOfComm;
    }


    /**
     * Sets the cmsLossOfComm value for this CmsAlarmConfigT.
     * 
     * @param cmsLossOfComm
     */
    public void setCmsLossOfComm(com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT cmsLossOfComm) {
        this.cmsLossOfComm = cmsLossOfComm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmsAlarmConfigT)) return false;
        CmsAlarmConfigT other = (CmsAlarmConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmsLossOfComm==null && other.getCmsLossOfComm()==null) || 
             (this.cmsLossOfComm!=null &&
              this.cmsLossOfComm.equals(other.getCmsLossOfComm())));
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
        if (getCmsLossOfComm() != null) {
            _hashCode += getCmsLossOfComm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmsAlarmConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmsAlarmConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsLossOfComm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsLossOfComm"));
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
