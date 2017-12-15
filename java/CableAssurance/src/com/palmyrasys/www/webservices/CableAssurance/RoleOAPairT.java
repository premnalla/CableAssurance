/**
 * RoleOAPairT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class RoleOAPairT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.RoleT role;

    private com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT domain;

    private com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT access;

    public RoleOAPairT() {
    }

    public RoleOAPairT(
           com.palmyrasys.www.webservices.CableAssurance.RoleT role,
           com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT domain,
           com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT access) {
           this.role = role;
           this.domain = domain;
           this.access = access;
    }


    /**
     * Gets the role value for this RoleOAPairT.
     * 
     * @return role
     */
    public com.palmyrasys.www.webservices.CableAssurance.RoleT getRole() {
        return role;
    }


    /**
     * Sets the role value for this RoleOAPairT.
     * 
     * @param role
     */
    public void setRole(com.palmyrasys.www.webservices.CableAssurance.RoleT role) {
        this.role = role;
    }


    /**
     * Gets the domain value for this RoleOAPairT.
     * 
     * @return domain
     */
    public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT getDomain() {
        return domain;
    }


    /**
     * Sets the domain value for this RoleOAPairT.
     * 
     * @param domain
     */
    public void setDomain(com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT domain) {
        this.domain = domain;
    }


    /**
     * Gets the access value for this RoleOAPairT.
     * 
     * @return access
     */
    public com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT getAccess() {
        return access;
    }


    /**
     * Sets the access value for this RoleOAPairT.
     * 
     * @param access
     */
    public void setAccess(com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT access) {
        this.access = access;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoleOAPairT)) return false;
        RoleOAPairT other = (RoleOAPairT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.role==null && other.getRole()==null) || 
             (this.role!=null &&
              this.role.equals(other.getRole()))) &&
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
        if (getRole() != null) {
            _hashCode += getRole().hashCode();
        }
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
        new org.apache.axis.description.TypeDesc(RoleOAPairT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "RoleOAPairT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("role");
        elemField.setXmlName(new javax.xml.namespace.QName("", "role"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "RoleT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
