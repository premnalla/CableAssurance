/**
 * HfcStatusSummaryRespT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class HfcStatusSummaryRespT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT[] hfcData;

    private com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState;

    public HfcStatusSummaryRespT() {
    }

    public HfcStatusSummaryRespT(
           com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT[] hfcData,
           com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) {
           this.hfcData = hfcData;
           this.queryState = queryState;
    }


    /**
     * Gets the hfcData value for this HfcStatusSummaryRespT.
     * 
     * @return hfcData
     */
    public com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT[] getHfcData() {
        return hfcData;
    }


    /**
     * Sets the hfcData value for this HfcStatusSummaryRespT.
     * 
     * @param hfcData
     */
    public void setHfcData(com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT[] hfcData) {
        this.hfcData = hfcData;
    }


    /**
     * Gets the queryState value for this HfcStatusSummaryRespT.
     * 
     * @return queryState
     */
    public com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] getQueryState() {
        return queryState;
    }


    /**
     * Sets the queryState value for this HfcStatusSummaryRespT.
     * 
     * @param queryState
     */
    public void setQueryState(com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState) {
        this.queryState = queryState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HfcStatusSummaryRespT)) return false;
        HfcStatusSummaryRespT other = (HfcStatusSummaryRespT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.hfcData==null && other.getHfcData()==null) || 
             (this.hfcData!=null &&
              java.util.Arrays.equals(this.hfcData, other.getHfcData()))) &&
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
        if (getHfcData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHfcData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHfcData(), i);
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
        new org.apache.axis.description.TypeDesc(HfcStatusSummaryRespT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryRespT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcStatusSummaryT"));
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
