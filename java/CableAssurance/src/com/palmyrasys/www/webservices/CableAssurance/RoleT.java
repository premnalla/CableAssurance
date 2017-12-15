/**
 * RoleT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.OAObject;
import com.palmyrasyscorp.db.tables.Role;
import com.palmyrasyscorp.www.jsp.bean.NewRoleFormBean;

public class RoleT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(RoleT.class);

    private java.lang.String roleName;

    private com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] domains;

    public RoleT() {
    }

    public RoleT(
           java.lang.String roleName,
           com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] domains) {
           this.roleName = roleName;
           this.domains = domains;
    }


    /**
     * @author Prem
     * @param dbRole
     */
    public RoleT(Role dbRole) {
    	try {
	    	this.roleName = dbRole.getRoleName();
	    	HashMap map = dbRole.getObjectMap();
	    	this.domains = new ApplicationDomainT[map.size()];
	    	Iterator iter = map.values().iterator();
	    	for (int i=0; i<map.size() && iter.hasNext(); i++) {
	    		OAObject o = (OAObject) iter.next();
	    		this.domains[i] = new ApplicationDomainT(o);
	    	}
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
    	}
    }
    
    
    /**
     * 
     * @param bean
     */
    public RoleT(NewRoleFormBean bean) {
    	this.roleName = bean.getRoleName();
    }
    
    
    /**
     * Gets the roleName value for this RoleT.
     * 
     * @return roleName
     */
    public java.lang.String getRoleName() {
        return roleName;
    }


    /**
     * Sets the roleName value for this RoleT.
     * 
     * @param roleName
     */
    public void setRoleName(java.lang.String roleName) {
        this.roleName = roleName;
    }


    /**
     * Gets the domains value for this RoleT.
     * 
     * @return domains
     */
    public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] getDomains() {
        return domains;
    }


    /**
     * Sets the domains value for this RoleT.
     * 
     * @param domains
     */
    public void setDomains(com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] domains) {
        this.domains = domains;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoleT)) return false;
        RoleT other = (RoleT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.roleName==null && other.getRoleName()==null) || 
             (this.roleName!=null &&
              this.roleName.equals(other.getRoleName()))) &&
            ((this.domains==null && other.getDomains()==null) || 
             (this.domains!=null &&
              java.util.Arrays.equals(this.domains, other.getDomains())));
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
        if (getRoleName() != null) {
            _hashCode += getRoleName().hashCode();
        }
        if (getDomains() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDomains());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDomains(), i);
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
        new org.apache.axis.description.TypeDesc(RoleT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "RoleT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domains");
        elemField.setXmlName(new javax.xml.namespace.QName("", "domains"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ApplicationDomainT"));
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
