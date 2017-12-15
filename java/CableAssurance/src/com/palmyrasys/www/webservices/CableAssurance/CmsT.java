/**
 * CmsT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

//Prem:
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.Common.TopoKeyHelper;
import com.palmyrasyscorp.db.tables.Cms;
import com.palmyrasyscorp.www.jsp.helper.StatusColorHelper;
import com.palmyrasyscorp.www.jsp.bean.NewCmsFormBean;

public class CmsT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(CmsT.class);

    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger cmsResId;

    private java.lang.String cmsName;

    private java.lang.String cmsType;

    private java.lang.String cmsSubType;

    private java.lang.String cmsHost;

    private java.lang.String loginName;

    private java.lang.String loginPassword;

    private com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor;

    public CmsT() {
    }

    public CmsT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger cmsResId,
           java.lang.String cmsName,
           java.lang.String cmsType,
           java.lang.String cmsSubType,
           java.lang.String cmsHost,
           java.lang.String loginName,
           java.lang.String loginPassword,
           com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
           this.topologyKey = topologyKey;
           this.cmsResId = cmsResId;
           this.cmsName = cmsName;
           this.cmsType = cmsType;
           this.cmsSubType = cmsSubType;
           this.cmsHost = cmsHost;
           this.loginName = loginName;
           this.loginPassword = loginPassword;
           this.statusColor = statusColor;
    }


    /**
     * @author Prem
     * @param c
     */
    public CmsT(Cms c) {
    	try {
	    	this.cmsResId = new java.math.BigInteger(c.getCmsResId().toString());
	    	this.cmsName = c.getCmsName();
	    	this.cmsType = c.getCmsType();
	    	this.cmsSubType = c.getCmsSubType();
	        this.cmsHost = c.getHost();
	    	this.loginName = c.getCmsLoginUserId();
	    	this.loginPassword = c.getCmsLoginPw();
	        this.statusColor = 
	        	StatusColorHelper.AlertLevelToStatusColor(c);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
    }
    

    /**
     * @author Prem
     * @param bean
     */
    public CmsT(NewCmsFormBean bean) {
    	try {
	        this.topologyKey = TopoKeyHelper.getTopoKey("0","0","0");
	        this.cmsResId = new BigInteger("0");
	        this.cmsName = bean.getCmsName();
	        this.cmsType = bean.getCmsType();
	        this.cmsSubType = bean.getCmsSubType();
	        this.cmsHost = bean.getCmsHost();
	        this.loginName = bean.getLoginUserId();
	        this.loginPassword = bean.getLoginPw();
	        this.statusColor = StatusColorT.Gray;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
    }
    
    
    /**
     * Gets the topologyKey value for this CmsT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this CmsT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the cmsResId value for this CmsT.
     * 
     * @return cmsResId
     */
    public java.math.BigInteger getCmsResId() {
        return cmsResId;
    }


    /**
     * Sets the cmsResId value for this CmsT.
     * 
     * @param cmsResId
     */
    public void setCmsResId(java.math.BigInteger cmsResId) {
        this.cmsResId = cmsResId;
    }


    /**
     * Gets the cmsName value for this CmsT.
     * 
     * @return cmsName
     */
    public java.lang.String getCmsName() {
        return cmsName;
    }


    /**
     * Sets the cmsName value for this CmsT.
     * 
     * @param cmsName
     */
    public void setCmsName(java.lang.String cmsName) {
        this.cmsName = cmsName;
    }


    /**
     * Gets the cmsType value for this CmsT.
     * 
     * @return cmsType
     */
    public java.lang.String getCmsType() {
        return cmsType;
    }


    /**
     * Sets the cmsType value for this CmsT.
     * 
     * @param cmsType
     */
    public void setCmsType(java.lang.String cmsType) {
        this.cmsType = cmsType;
    }


    /**
     * Gets the cmsSubType value for this CmsT.
     * 
     * @return cmsSubType
     */
    public java.lang.String getCmsSubType() {
        return cmsSubType;
    }


    /**
     * Sets the cmsSubType value for this CmsT.
     * 
     * @param cmsSubType
     */
    public void setCmsSubType(java.lang.String cmsSubType) {
        this.cmsSubType = cmsSubType;
    }


    /**
     * Gets the cmsHost value for this CmsT.
     * 
     * @return cmsHost
     */
    public java.lang.String getCmsHost() {
        return cmsHost;
    }


    /**
     * Sets the cmsHost value for this CmsT.
     * 
     * @param cmsHost
     */
    public void setCmsHost(java.lang.String cmsHost) {
        this.cmsHost = cmsHost;
    }


    /**
     * Gets the loginName value for this CmsT.
     * 
     * @return loginName
     */
    public java.lang.String getLoginName() {
        return loginName;
    }


    /**
     * Sets the loginName value for this CmsT.
     * 
     * @param loginName
     */
    public void setLoginName(java.lang.String loginName) {
        this.loginName = loginName;
    }


    /**
     * Gets the loginPassword value for this CmsT.
     * 
     * @return loginPassword
     */
    public java.lang.String getLoginPassword() {
        return loginPassword;
    }


    /**
     * Sets the loginPassword value for this CmsT.
     * 
     * @param loginPassword
     */
    public void setLoginPassword(java.lang.String loginPassword) {
        this.loginPassword = loginPassword;
    }


    /**
     * Gets the statusColor value for this CmsT.
     * 
     * @return statusColor
     */
    public com.palmyrasys.www.webservices.CableAssurance.StatusColorT getStatusColor() {
        return statusColor;
    }


    /**
     * Sets the statusColor value for this CmsT.
     * 
     * @param statusColor
     */
    public void setStatusColor(com.palmyrasys.www.webservices.CableAssurance.StatusColorT statusColor) {
        this.statusColor = statusColor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmsT)) return false;
        CmsT other = (CmsT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.topologyKey==null && other.getTopologyKey()==null) || 
             (this.topologyKey!=null &&
              this.topologyKey.equals(other.getTopologyKey()))) &&
            ((this.cmsResId==null && other.getCmsResId()==null) || 
             (this.cmsResId!=null &&
              this.cmsResId.equals(other.getCmsResId()))) &&
            ((this.cmsName==null && other.getCmsName()==null) || 
             (this.cmsName!=null &&
              this.cmsName.equals(other.getCmsName()))) &&
            ((this.cmsType==null && other.getCmsType()==null) || 
             (this.cmsType!=null &&
              this.cmsType.equals(other.getCmsType()))) &&
            ((this.cmsSubType==null && other.getCmsSubType()==null) || 
             (this.cmsSubType!=null &&
              this.cmsSubType.equals(other.getCmsSubType()))) &&
            ((this.cmsHost==null && other.getCmsHost()==null) || 
             (this.cmsHost!=null &&
              this.cmsHost.equals(other.getCmsHost()))) &&
            ((this.loginName==null && other.getLoginName()==null) || 
             (this.loginName!=null &&
              this.loginName.equals(other.getLoginName()))) &&
            ((this.loginPassword==null && other.getLoginPassword()==null) || 
             (this.loginPassword!=null &&
              this.loginPassword.equals(other.getLoginPassword()))) &&
            ((this.statusColor==null && other.getStatusColor()==null) || 
             (this.statusColor!=null &&
              this.statusColor.equals(other.getStatusColor())));
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
        if (getTopologyKey() != null) {
            _hashCode += getTopologyKey().hashCode();
        }
        if (getCmsResId() != null) {
            _hashCode += getCmsResId().hashCode();
        }
        if (getCmsName() != null) {
            _hashCode += getCmsName().hashCode();
        }
        if (getCmsType() != null) {
            _hashCode += getCmsType().hashCode();
        }
        if (getCmsSubType() != null) {
            _hashCode += getCmsSubType().hashCode();
        }
        if (getCmsHost() != null) {
            _hashCode += getCmsHost().hashCode();
        }
        if (getLoginName() != null) {
            _hashCode += getLoginName().hashCode();
        }
        if (getLoginPassword() != null) {
            _hashCode += getLoginPassword().hashCode();
        }
        if (getStatusColor() != null) {
            _hashCode += getStatusColor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CmsT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmsT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsResId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsResId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsHost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsHost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "StatusColorT"));
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
