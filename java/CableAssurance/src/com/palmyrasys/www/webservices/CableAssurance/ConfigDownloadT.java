/**
 * ConfigDownloadT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class ConfigDownloadT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT cmPerfCfg;

    private com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT cmsAlarmCfg;

    private com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT cmtsAlarmCfg;

    private com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT mtaAlarmCfg;

    private com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT hfcAlarmCfg;

    private com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT pollingIntervals;

    private com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] userAccessRights;

    private com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] applicationDomains;

    private com.palmyrasys.www.webservices.CableAssurance.RoleT[] roles;

    private com.palmyrasys.www.webservices.CableAssurance.UserT[] users;

    public ConfigDownloadT() {
    }

    public ConfigDownloadT(
           com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT cmPerfCfg,
           com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT cmsAlarmCfg,
           com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT cmtsAlarmCfg,
           com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT mtaAlarmCfg,
           com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT hfcAlarmCfg,
           com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT pollingIntervals,
           com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] userAccessRights,
           com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] applicationDomains,
           com.palmyrasys.www.webservices.CableAssurance.RoleT[] roles,
           com.palmyrasys.www.webservices.CableAssurance.UserT[] users) {
           this.cmPerfCfg = cmPerfCfg;
           this.cmsAlarmCfg = cmsAlarmCfg;
           this.cmtsAlarmCfg = cmtsAlarmCfg;
           this.mtaAlarmCfg = mtaAlarmCfg;
           this.hfcAlarmCfg = hfcAlarmCfg;
           this.pollingIntervals = pollingIntervals;
           this.userAccessRights = userAccessRights;
           this.applicationDomains = applicationDomains;
           this.roles = roles;
           this.users = users;
    }


    /**
     * Gets the cmPerfCfg value for this ConfigDownloadT.
     * 
     * @return cmPerfCfg
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT getCmPerfCfg() {
        return cmPerfCfg;
    }


    /**
     * Sets the cmPerfCfg value for this ConfigDownloadT.
     * 
     * @param cmPerfCfg
     */
    public void setCmPerfCfg(com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT cmPerfCfg) {
        this.cmPerfCfg = cmPerfCfg;
    }


    /**
     * Gets the cmsAlarmCfg value for this ConfigDownloadT.
     * 
     * @return cmsAlarmCfg
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT getCmsAlarmCfg() {
        return cmsAlarmCfg;
    }


    /**
     * Sets the cmsAlarmCfg value for this ConfigDownloadT.
     * 
     * @param cmsAlarmCfg
     */
    public void setCmsAlarmCfg(com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT cmsAlarmCfg) {
        this.cmsAlarmCfg = cmsAlarmCfg;
    }


    /**
     * Gets the cmtsAlarmCfg value for this ConfigDownloadT.
     * 
     * @return cmtsAlarmCfg
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT getCmtsAlarmCfg() {
        return cmtsAlarmCfg;
    }


    /**
     * Sets the cmtsAlarmCfg value for this ConfigDownloadT.
     * 
     * @param cmtsAlarmCfg
     */
    public void setCmtsAlarmCfg(com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT cmtsAlarmCfg) {
        this.cmtsAlarmCfg = cmtsAlarmCfg;
    }


    /**
     * Gets the mtaAlarmCfg value for this ConfigDownloadT.
     * 
     * @return mtaAlarmCfg
     */
    public com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT getMtaAlarmCfg() {
        return mtaAlarmCfg;
    }


    /**
     * Sets the mtaAlarmCfg value for this ConfigDownloadT.
     * 
     * @param mtaAlarmCfg
     */
    public void setMtaAlarmCfg(com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT mtaAlarmCfg) {
        this.mtaAlarmCfg = mtaAlarmCfg;
    }


    /**
     * Gets the hfcAlarmCfg value for this ConfigDownloadT.
     * 
     * @return hfcAlarmCfg
     */
    public com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT getHfcAlarmCfg() {
        return hfcAlarmCfg;
    }


    /**
     * Sets the hfcAlarmCfg value for this ConfigDownloadT.
     * 
     * @param hfcAlarmCfg
     */
    public void setHfcAlarmCfg(com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT hfcAlarmCfg) {
        this.hfcAlarmCfg = hfcAlarmCfg;
    }


    /**
     * Gets the pollingIntervals value for this ConfigDownloadT.
     * 
     * @return pollingIntervals
     */
    public com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT getPollingIntervals() {
        return pollingIntervals;
    }


    /**
     * Sets the pollingIntervals value for this ConfigDownloadT.
     * 
     * @param pollingIntervals
     */
    public void setPollingIntervals(com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT pollingIntervals) {
        this.pollingIntervals = pollingIntervals;
    }


    /**
     * Gets the userAccessRights value for this ConfigDownloadT.
     * 
     * @return userAccessRights
     */
    public com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] getUserAccessRights() {
        return userAccessRights;
    }


    /**
     * Sets the userAccessRights value for this ConfigDownloadT.
     * 
     * @param userAccessRights
     */
    public void setUserAccessRights(com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] userAccessRights) {
        this.userAccessRights = userAccessRights;
    }


    /**
     * Gets the applicationDomains value for this ConfigDownloadT.
     * 
     * @return applicationDomains
     */
    public com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] getApplicationDomains() {
        return applicationDomains;
    }


    /**
     * Sets the applicationDomains value for this ConfigDownloadT.
     * 
     * @param applicationDomains
     */
    public void setApplicationDomains(com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] applicationDomains) {
        this.applicationDomains = applicationDomains;
    }


    /**
     * Gets the roles value for this ConfigDownloadT.
     * 
     * @return roles
     */
    public com.palmyrasys.www.webservices.CableAssurance.RoleT[] getRoles() {
        return roles;
    }


    /**
     * Sets the roles value for this ConfigDownloadT.
     * 
     * @param roles
     */
    public void setRoles(com.palmyrasys.www.webservices.CableAssurance.RoleT[] roles) {
        this.roles = roles;
    }


    /**
     * Gets the users value for this ConfigDownloadT.
     * 
     * @return users
     */
    public com.palmyrasys.www.webservices.CableAssurance.UserT[] getUsers() {
        return users;
    }


    /**
     * Sets the users value for this ConfigDownloadT.
     * 
     * @param users
     */
    public void setUsers(com.palmyrasys.www.webservices.CableAssurance.UserT[] users) {
        this.users = users;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfigDownloadT)) return false;
        ConfigDownloadT other = (ConfigDownloadT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmPerfCfg==null && other.getCmPerfCfg()==null) || 
             (this.cmPerfCfg!=null &&
              this.cmPerfCfg.equals(other.getCmPerfCfg()))) &&
            ((this.cmsAlarmCfg==null && other.getCmsAlarmCfg()==null) || 
             (this.cmsAlarmCfg!=null &&
              this.cmsAlarmCfg.equals(other.getCmsAlarmCfg()))) &&
            ((this.cmtsAlarmCfg==null && other.getCmtsAlarmCfg()==null) || 
             (this.cmtsAlarmCfg!=null &&
              this.cmtsAlarmCfg.equals(other.getCmtsAlarmCfg()))) &&
            ((this.mtaAlarmCfg==null && other.getMtaAlarmCfg()==null) || 
             (this.mtaAlarmCfg!=null &&
              this.mtaAlarmCfg.equals(other.getMtaAlarmCfg()))) &&
            ((this.hfcAlarmCfg==null && other.getHfcAlarmCfg()==null) || 
             (this.hfcAlarmCfg!=null &&
              this.hfcAlarmCfg.equals(other.getHfcAlarmCfg()))) &&
            ((this.pollingIntervals==null && other.getPollingIntervals()==null) || 
             (this.pollingIntervals!=null &&
              this.pollingIntervals.equals(other.getPollingIntervals()))) &&
            ((this.userAccessRights==null && other.getUserAccessRights()==null) || 
             (this.userAccessRights!=null &&
              java.util.Arrays.equals(this.userAccessRights, other.getUserAccessRights()))) &&
            ((this.applicationDomains==null && other.getApplicationDomains()==null) || 
             (this.applicationDomains!=null &&
              java.util.Arrays.equals(this.applicationDomains, other.getApplicationDomains()))) &&
            ((this.roles==null && other.getRoles()==null) || 
             (this.roles!=null &&
              java.util.Arrays.equals(this.roles, other.getRoles()))) &&
            ((this.users==null && other.getUsers()==null) || 
             (this.users!=null &&
              java.util.Arrays.equals(this.users, other.getUsers())));
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
        if (getCmPerfCfg() != null) {
            _hashCode += getCmPerfCfg().hashCode();
        }
        if (getCmsAlarmCfg() != null) {
            _hashCode += getCmsAlarmCfg().hashCode();
        }
        if (getCmtsAlarmCfg() != null) {
            _hashCode += getCmtsAlarmCfg().hashCode();
        }
        if (getMtaAlarmCfg() != null) {
            _hashCode += getMtaAlarmCfg().hashCode();
        }
        if (getHfcAlarmCfg() != null) {
            _hashCode += getHfcAlarmCfg().hashCode();
        }
        if (getPollingIntervals() != null) {
            _hashCode += getPollingIntervals().hashCode();
        }
        if (getUserAccessRights() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserAccessRights());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserAccessRights(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getApplicationDomains() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getApplicationDomains());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getApplicationDomains(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRoles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRoles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRoles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUsers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsers(), i);
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
        new org.apache.axis.description.TypeDesc(ConfigDownloadT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ConfigDownloadT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmPerfCfg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmPerfCfg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmPerformanceConfigT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmsAlarmCfg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmsAlarmCfg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmsAlarmConfigT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmtsAlarmCfg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmtsAlarmCfg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmtsAlarmConfigT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaAlarmCfg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaAlarmCfg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAlarmConfigT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcAlarmCfg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcAlarmCfg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcAlarmConfigT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pollingIntervals");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pollingIntervals"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "PollingIntervalsT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAccessRights");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userAccessRights"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "UserAccessT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationDomains");
        elemField.setXmlName(new javax.xml.namespace.QName("", "applicationDomains"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ApplicationDomainT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "RoleT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("users");
        elemField.setXmlName(new javax.xml.namespace.QName("", "users"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "UserT"));
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
