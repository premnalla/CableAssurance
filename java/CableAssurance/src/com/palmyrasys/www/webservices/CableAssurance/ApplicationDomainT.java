/**
 * ApplicationDomainT.java
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

import com.palmyrasyscorp.db.tables.OAAccess;
import com.palmyrasyscorp.db.tables.OAObject;

public class ApplicationDomainT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(ApplicationDomainT.class);

    private com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT type;

    private com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] accessRights;

    public ApplicationDomainT() {
    }

    public ApplicationDomainT(
           com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT type,
           com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] accessRights) {
           this.type = type;
           this.accessRights = accessRights;
    }


    /**
     * @author Prem
     * @param dbDomain
     */
    public ApplicationDomainT(OAObject dbDomain) {
    	try {
    		// System.out.println("domainName=" + dbDomain.getAppDomain());
    		this.type = ApplicationDomainTypeT.Get(dbDomain.getAppDomain());
    		// System.out.println("domanType=" + this.type);
    		HashMap map = dbDomain.getAccessMap();
    		this.accessRights = new UserAccessT[map.size()];
    		Iterator iter = map.values().iterator();
    		for (int i=0; i<map.size() && iter.hasNext(); i++) {
    			OAAccess o = (OAAccess) iter.next();
    			this.accessRights[i] = new UserAccessT(o);
    		}
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
    	}
    }


    /**
     * Gets the type value for this ApplicationDomainT.
     * 
     * @return type
     */
    public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT getType() {
        return type;
    }


    /**
     * Sets the type value for this ApplicationDomainT.
     * 
     * @param type
     */
    public void setType(com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT type) {
        this.type = type;
    }


    /**
     * Gets the accessRights value for this ApplicationDomainT.
     * 
     * @return accessRights
     */
    public com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] getAccessRights() {
        return accessRights;
    }


    /**
     * Sets the accessRights value for this ApplicationDomainT.
     * 
     * @param accessRights
     */
    public void setAccessRights(com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] accessRights) {
        this.accessRights = accessRights;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApplicationDomainT)) return false;
        ApplicationDomainT other = (ApplicationDomainT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.accessRights==null && other.getAccessRights()==null) || 
             (this.accessRights!=null &&
              java.util.Arrays.equals(this.accessRights, other.getAccessRights())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getAccessRights() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccessRights());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccessRights(), i);
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
        new org.apache.axis.description.TypeDesc(ApplicationDomainT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ApplicationDomainT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ApplicationDomainTypeT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessRights");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessRights"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "UserAccessT"));
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
