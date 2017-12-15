/**
 * CmConditionLongestRespT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CmConditionLongestRespT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CmConditionLongestT[] mtaData;

    private com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState;

    public CmConditionLongestRespT() {
    }

    public CmConditionLongestRespT(
           com.palmyrasys.www.webservices.CableAssurance.CmConditionLongestT[] mtaData,
           com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) {
           this.mtaData = mtaData;
           this.queryState = queryState;
    }


    /**
     * Gets the mtaData value for this CmConditionLongestRespT.
     * 
     * @return mtaData
     */
    public com.palmyrasys.www.webservices.CableAssurance.CmConditionLongestT[] getMtaData() {
        return mtaData;
    }


    /**
     * Sets the mtaData value for this CmConditionLongestRespT.
     * 
     * @param mtaData
     */
    public void setMtaData(com.palmyrasys.www.webservices.CableAssurance.CmConditionLongestT[] mtaData) {
        this.mtaData = mtaData;
    }


    /**
     * Gets the queryState value for this CmConditionLongestRespT.
     * 
     * @return queryState
     */
    public com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] getQueryState() {
        return queryState;
    }


    /**
     * Sets the queryState value for this CmConditionLongestRespT.
     * 
     * @param queryState
     */
    public void setQueryState(com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) {
        this.queryState = queryState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CmConditionLongestRespT)) return false;
        CmConditionLongestRespT other = (CmConditionLongestRespT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mtaData==null && other.getMtaData()==null) || 
             (this.mtaData!=null &&
              java.util.Arrays.equals(this.mtaData, other.getMtaData()))) &&
            ((this.queryState==null && other.getQueryState()==null) || 
             (this.queryState!=null &&
              java.util.Arrays.equals(this.queryState, other.getQueryState())));
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
        if (getMtaData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMtaData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMtaData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getQueryState() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQueryState());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQueryState(), i);
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
        new org.apache.axis.description.TypeDesc(CmConditionLongestRespT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmConditionLongestRespT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CmConditionLongestT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "QueryStateT"));
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
