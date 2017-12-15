/**
 * OAPairT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class OAPairT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT domain;

    private com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT access;

    public OAPairT() {
    }

    public OAPairT(
           com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT domain,
           com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT access) {
           this.domain = domain;
           this.access = access;
    }


    /**
     * Gets the domain value for this OAPairT.
     * 
     * @return domain
     */
    public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT getDomain() {
        return domain;
    }


    /**
     * Sets the domain value for this OAPairT.
     * 
     * @param domain
     */
    public void setDomain(com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT domain) {
        this.domain = domain;
    }


    /**
     * Gets the access value for this OAPairT.
     * 
     * @return access
     */
    public com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT getAccess() {
        return access;
    }


    /**
     * Sets the access value for this OAPairT.
     * 
     * @param access
     */
    public void setAccess(com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT access) {
        this.access = access;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OAPairT)) return false;
        OAPairT other = (OAPairT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.domain==null && other.getDomain()==null) || 
             (this.domain!=null &&
              this.domain.equals(other.getDomain()))) &&
            ((this.access==null && other.getAccess()==null) || 
             (this.access!=null &&
              this.access.equals(other.getAccess())));
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
        if (getDomain() != null) {
            _hashCode += getDomain().hashCode();
        }
        if (getAccess() != null) {
            _hashCode += getAccess().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OAPairT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "OAPairT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domain");
        elemField.setXmlName(new javax.xml.namespace.QName("", "domain"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ApplicationDomainTypeT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("access");
        elemField.setXmlName(new javax.xml.namespace.QName("", "access"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "UserAccessTypeT"));
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
