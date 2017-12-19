/**
 * AggregateType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public class AggregateType  implements java.io.Serializable {
    private java.lang.String aggregateName;

    private com.nds.www.wsdl.CableAssurance.LocalSystemType aggregateType;

    public AggregateType() {
    }

    public AggregateType(
           java.lang.String aggregateName,
           com.nds.www.wsdl.CableAssurance.LocalSystemType aggregateType) {
           this.aggregateName = aggregateName;
           this.aggregateType = aggregateType;
    }


    /**
     * Gets the aggregateName value for this AggregateType.
     * 
     * @return aggregateName
     */
    public java.lang.String getAggregateName() {
        return aggregateName;
    }


    /**
     * Sets the aggregateName value for this AggregateType.
     * 
     * @param aggregateName
     */
    public void setAggregateName(java.lang.String aggregateName) {
        this.aggregateName = aggregateName;
    }


    /**
     * Gets the aggregateType value for this AggregateType.
     * 
     * @return aggregateType
     */
    public com.nds.www.wsdl.CableAssurance.LocalSystemType getAggregateType() {
        return aggregateType;
    }


    /**
     * Sets the aggregateType value for this AggregateType.
     * 
     * @param aggregateType
     */
    public void setAggregateType(com.nds.www.wsdl.CableAssurance.LocalSystemType aggregateType) {
        this.aggregateType = aggregateType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AggregateType)) return false;
        AggregateType other = (AggregateType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.aggregateName==null && other.getAggregateName()==null) || 
             (this.aggregateName!=null &&
              this.aggregateName.equals(other.getAggregateName()))) &&
            ((this.aggregateType==null && other.getAggregateType()==null) || 
             (this.aggregateType!=null &&
              this.aggregateType.equals(other.getAggregateType())));
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
        if (getAggregateName() != null) {
            _hashCode += getAggregateName().hashCode();
        }
        if (getAggregateType() != null) {
            _hashCode += getAggregateType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AggregateType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "aggregateType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aggregateName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aggregateName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aggregateType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aggregateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.nds.com/wsdl/CableAssurance", "localSystemType"));
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
