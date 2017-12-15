/**
 * AbstractAlarmT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class AbstractAlarmT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger alarmId;

    private java.math.BigInteger resourceId;

    private java.lang.String resourceName;

    private java.math.BigInteger alarmTime;

    private java.math.BigInteger alarmTimeUSec;

    private short soakDuration;

    private java.lang.String alarmState;

    private java.lang.String alarmType;

    private java.lang.String alarmSubType;

    public AbstractAlarmT() {
    }

    public AbstractAlarmT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger alarmId,
           java.math.BigInteger resourceId,
           java.lang.String resourceName,
           java.math.BigInteger alarmTime,
           java.math.BigInteger alarmTimeUSec,
           short soakDuration,
           java.lang.String alarmState,
           java.lang.String alarmType,
           java.lang.String alarmSubType) {
           this.topologyKey = topologyKey;
           this.alarmId = alarmId;
           this.resourceId = resourceId;
           this.resourceName = resourceName;
           this.alarmTime = alarmTime;
           this.alarmTimeUSec = alarmTimeUSec;
           this.soakDuration = soakDuration;
           this.alarmState = alarmState;
           this.alarmType = alarmType;
           this.alarmSubType = alarmSubType;
    }


    /**
     * Gets the topologyKey value for this AbstractAlarmT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this AbstractAlarmT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the alarmId value for this AbstractAlarmT.
     * 
     * @return alarmId
     */
    public java.math.BigInteger getAlarmId() {
        return alarmId;
    }


    /**
     * Sets the alarmId value for this AbstractAlarmT.
     * 
     * @param alarmId
     */
    public void setAlarmId(java.math.BigInteger alarmId) {
        this.alarmId = alarmId;
    }


    /**
     * Gets the resourceId value for this AbstractAlarmT.
     * 
     * @return resourceId
     */
    public java.math.BigInteger getResourceId() {
        return resourceId;
    }


    /**
     * Sets the resourceId value for this AbstractAlarmT.
     * 
     * @param resourceId
     */
    public void setResourceId(java.math.BigInteger resourceId) {
        this.resourceId = resourceId;
    }


    /**
     * Gets the resourceName value for this AbstractAlarmT.
     * 
     * @return resourceName
     */
    public java.lang.String getResourceName() {
        return resourceName;
    }


    /**
     * Sets the resourceName value for this AbstractAlarmT.
     * 
     * @param resourceName
     */
    public void setResourceName(java.lang.String resourceName) {
        this.resourceName = resourceName;
    }


    /**
     * Gets the alarmTime value for this AbstractAlarmT.
     * 
     * @return alarmTime
     */
    public java.math.BigInteger getAlarmTime() {
        return alarmTime;
    }


    /**
     * Sets the alarmTime value for this AbstractAlarmT.
     * 
     * @param alarmTime
     */
    public void setAlarmTime(java.math.BigInteger alarmTime) {
        this.alarmTime = alarmTime;
    }


    /**
     * Gets the alarmTimeUSec value for this AbstractAlarmT.
     * 
     * @return alarmTimeUSec
     */
    public java.math.BigInteger getAlarmTimeUSec() {
        return alarmTimeUSec;
    }


    /**
     * Sets the alarmTimeUSec value for this AbstractAlarmT.
     * 
     * @param alarmTimeUSec
     */
    public void setAlarmTimeUSec(java.math.BigInteger alarmTimeUSec) {
        this.alarmTimeUSec = alarmTimeUSec;
    }


    /**
     * Gets the soakDuration value for this AbstractAlarmT.
     * 
     * @return soakDuration
     */
    public short getSoakDuration() {
        return soakDuration;
    }


    /**
     * Sets the soakDuration value for this AbstractAlarmT.
     * 
     * @param soakDuration
     */
    public void setSoakDuration(short soakDuration) {
        this.soakDuration = soakDuration;
    }


    /**
     * Gets the alarmState value for this AbstractAlarmT.
     * 
     * @return alarmState
     */
    public java.lang.String getAlarmState() {
        return alarmState;
    }


    /**
     * Sets the alarmState value for this AbstractAlarmT.
     * 
     * @param alarmState
     */
    public void setAlarmState(java.lang.String alarmState) {
        this.alarmState = alarmState;
    }


    /**
     * Gets the alarmType value for this AbstractAlarmT.
     * 
     * @return alarmType
     */
    public java.lang.String getAlarmType() {
        return alarmType;
    }


    /**
     * Sets the alarmType value for this AbstractAlarmT.
     * 
     * @param alarmType
     */
    public void setAlarmType(java.lang.String alarmType) {
        this.alarmType = alarmType;
    }


    /**
     * Gets the alarmSubType value for this AbstractAlarmT.
     * 
     * @return alarmSubType
     */
    public java.lang.String getAlarmSubType() {
        return alarmSubType;
    }


    /**
     * Sets the alarmSubType value for this AbstractAlarmT.
     * 
     * @param alarmSubType
     */
    public void setAlarmSubType(java.lang.String alarmSubType) {
        this.alarmSubType = alarmSubType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AbstractAlarmT)) return false;
        AbstractAlarmT other = (AbstractAlarmT) obj;
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
            ((this.alarmId==null && other.getAlarmId()==null) || 
             (this.alarmId!=null &&
              this.alarmId.equals(other.getAlarmId()))) &&
            ((this.resourceId==null && other.getResourceId()==null) || 
             (this.resourceId!=null &&
              this.resourceId.equals(other.getResourceId()))) &&
            ((this.resourceName==null && other.getResourceName()==null) || 
             (this.resourceName!=null &&
              this.resourceName.equals(other.getResourceName()))) &&
            ((this.alarmTime==null && other.getAlarmTime()==null) || 
             (this.alarmTime!=null &&
              this.alarmTime.equals(other.getAlarmTime()))) &&
            ((this.alarmTimeUSec==null && other.getAlarmTimeUSec()==null) || 
             (this.alarmTimeUSec!=null &&
              this.alarmTimeUSec.equals(other.getAlarmTimeUSec()))) &&
            this.soakDuration == other.getSoakDuration() &&
            ((this.alarmState==null && other.getAlarmState()==null) || 
             (this.alarmState!=null &&
              this.alarmState.equals(other.getAlarmState()))) &&
            ((this.alarmType==null && other.getAlarmType()==null) || 
             (this.alarmType!=null &&
              this.alarmType.equals(other.getAlarmType()))) &&
            ((this.alarmSubType==null && other.getAlarmSubType()==null) || 
             (this.alarmSubType!=null &&
              this.alarmSubType.equals(other.getAlarmSubType())));
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
        if (getAlarmId() != null) {
            _hashCode += getAlarmId().hashCode();
        }
        if (getResourceId() != null) {
            _hashCode += getResourceId().hashCode();
        }
        if (getResourceName() != null) {
            _hashCode += getResourceName().hashCode();
        }
        if (getAlarmTime() != null) {
            _hashCode += getAlarmTime().hashCode();
        }
        if (getAlarmTimeUSec() != null) {
            _hashCode += getAlarmTimeUSec().hashCode();
        }
        _hashCode += getSoakDuration();
        if (getAlarmState() != null) {
            _hashCode += getAlarmState().hashCode();
        }
        if (getAlarmType() != null) {
            _hashCode += getAlarmType().hashCode();
        }
        if (getAlarmSubType() != null) {
            _hashCode += getAlarmSubType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AbstractAlarmT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AbstractAlarmT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmTimeUSec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmTimeUSec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soakDuration");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soakDuration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
