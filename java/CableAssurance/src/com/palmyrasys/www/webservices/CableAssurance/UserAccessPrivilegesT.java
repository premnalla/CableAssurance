/**
 * UserAccessPrivilegesT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class UserAccessPrivilegesT  implements java.io.Serializable {
    private java.lang.String userId;

    private com.palmyrasys.www.webservices.CableAssurance.UserRoleT role;

    private com.palmyrasys.www.webservices.CableAssurance.OAPairT[] oaPairs;

    public UserAccessPrivilegesT() {
    }

    public UserAccessPrivilegesT(
           java.lang.String userId,
           com.palmyrasys.www.webservices.CableAssurance.UserRoleT role,
           com.palmyrasys.www.webservices.CableAssurance.OAPairT[] oaPairs) {
           this.userId = userId;
           this.role = role;
           this.oaPairs = oaPairs;
    }


    /**
     * Gets the userId value for this UserAccessPrivilegesT.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this UserAccessPrivilegesT.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the role value for this UserAccessPrivilegesT.
     * 
     * @return role
     */
    public com.palmyrasys.www.webservices.CableAssurance.UserRoleT getRole() {
        return role;
    }


    /**
     * Sets the role value for this UserAccessPrivilegesT.
     * 
     * @param role
     */
    public void setRole(com.palmyrasys.www.webservices.CableAssurance.UserRoleT role) {
        this.role = role;
    }


    /**
     * Gets the oaPairs value for this UserAccessPrivilegesT.
     * 
     * @return oaPairs
     */
    public com.palmyrasys.www.webservices.CableAssurance.OAPairT[] getOaPairs() {
        return oaPairs;
    }


    /**
     * Sets the oaPairs value for this UserAccessPrivilegesT.
     * 
     * @param oaPairs
     */
    public void setOaPairs(com.palmyrasys.www.webservices.CableAssurance.OAPairT[] oaPairs) {
        this.oaPairs = oaPairs;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserAccessPrivilegesT)) return false;
        UserAccessPrivilegesT other = (UserAccessPrivilegesT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.role==null && other.getRole()==null) || 
             (this.role!=null &&
              this.role.equals(other.getRole()))) &&
            ((this.oaPairs==null && other.getOaPairs()==null) || 
             (this.oaPairs!=null &&
              java.util.Arrays.equals(this.oaPairs, other.getOaPairs())));
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
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getRole() != null) {
            _hashCode += getRole().hashCode();
        }
        if (getOaPairs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOaPairs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOaPairs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserAccessPrivilegesT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "UserAccessPrivilegesT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("role");
        elemField.setXmlName(new javax.xml.namespace.QName("", "role"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "UserRoleT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oaPairs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oaPairs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "OAPairT"));
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
