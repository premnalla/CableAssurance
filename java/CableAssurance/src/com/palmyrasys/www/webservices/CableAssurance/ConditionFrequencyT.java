/**
 * ConditionFrequencyT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class ConditionFrequencyT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey;

    private java.math.BigInteger resId;

    private java.lang.String name;

    private short frequency;

    public ConditionFrequencyT() {
    }

    public ConditionFrequencyT(
           com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
           java.math.BigInteger resId,
           java.lang.String name,
           short frequency) {
           this.topologyKey = topologyKey;
           this.resId = resId;
           this.name = name;
           this.frequency = frequency;
    }


    /**
     * Gets the topologyKey value for this ConditionFrequencyT.
     * 
     * @return topologyKey
     */
    public com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT getTopologyKey() {
        return topologyKey;
    }


    /**
     * Sets the topologyKey value for this ConditionFrequencyT.
     * 
     * @param topologyKey
     */
    public void setTopologyKey(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey) {
        this.topologyKey = topologyKey;
    }


    /**
     * Gets the resId value for this ConditionFrequencyT.
     * 
     * @return resId
     */
    public java.math.BigInteger getResId() {
        return resId;
    }


    /**
     * Sets the resId value for this ConditionFrequencyT.
     * 
     * @param resId
     */
    public void setResId(java.math.BigInteger resId) {
        this.resId = resId;
    }


    /**
     * Gets the name value for this ConditionFrequencyT.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ConditionFrequencyT.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the frequency value for this ConditionFrequencyT.
     * 
     * @return frequency
     */
    public short getFrequency() {
        return frequency;
    }


    /**
     * Sets the frequency value for this ConditionFrequencyT.
     * 
     * @param frequency
     */
    public void setFrequency(short frequency) {
        this.frequency = frequency;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConditionFrequencyT)) return false;
        ConditionFrequencyT other = (ConditionFrequencyT) obj;
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
            ((this.resId==null && other.getResId()==null) || 
             (this.resId!=null &&
              this.resId.equals(other.getResId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.frequency == other.getFrequency();
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
        if (getResId() != null) {
            _hashCode += getResId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += getFrequency();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConditionFrequencyT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "ConditionFrequencyT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topologyKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topologyKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "TopoHierarchyKeyT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frequency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frequency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
