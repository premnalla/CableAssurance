/**
 * LocationInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.themindelectric.www._package.com_esri_is_services_common_v2;

public class LocationInfo  implements java.io.Serializable {
    private java.lang.String matchType;

    private com.themindelectric.www._package.com_esri_is_services_common_v2.Location[] candidates;

    private boolean hasMore;

    private java.lang.String errorCode;

    public LocationInfo() {
    }

    public LocationInfo(
           java.lang.String matchType,
           com.themindelectric.www._package.com_esri_is_services_common_v2.Location[] candidates,
           boolean hasMore,
           java.lang.String errorCode) {
           this.matchType = matchType;
           this.candidates = candidates;
           this.hasMore = hasMore;
           this.errorCode = errorCode;
    }


    /**
     * Gets the matchType value for this LocationInfo.
     * 
     * @return matchType
     */
    public java.lang.String getMatchType() {
        return matchType;
    }


    /**
     * Sets the matchType value for this LocationInfo.
     * 
     * @param matchType
     */
    public void setMatchType(java.lang.String matchType) {
        this.matchType = matchType;
    }


    /**
     * Gets the candidates value for this LocationInfo.
     * 
     * @return candidates
     */
    public com.themindelectric.www._package.com_esri_is_services_common_v2.Location[] getCandidates() {
        return candidates;
    }


    /**
     * Sets the candidates value for this LocationInfo.
     * 
     * @param candidates
     */
    public void setCandidates(com.themindelectric.www._package.com_esri_is_services_common_v2.Location[] candidates) {
        this.candidates = candidates;
    }


    /**
     * Gets the hasMore value for this LocationInfo.
     * 
     * @return hasMore
     */
    public boolean isHasMore() {
        return hasMore;
    }


    /**
     * Sets the hasMore value for this LocationInfo.
     * 
     * @param hasMore
     */
    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }


    /**
     * Gets the errorCode value for this LocationInfo.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this LocationInfo.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LocationInfo)) return false;
        LocationInfo other = (LocationInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.matchType==null && other.getMatchType()==null) || 
             (this.matchType!=null &&
              this.matchType.equals(other.getMatchType()))) &&
            ((this.candidates==null && other.getCandidates()==null) || 
             (this.candidates!=null &&
              java.util.Arrays.equals(this.candidates, other.getCandidates()))) &&
            this.hasMore == other.isHasMore() &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode())));
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
        if (getMatchType() != null) {
            _hashCode += getMatchType().hashCode();
        }
        if (getCandidates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCandidates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCandidates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isHasMore() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LocationInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2/", "LocationInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matchType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("candidates");
        elemField.setXmlName(new javax.xml.namespace.QName("", "candidates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2/", "Location"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasMore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasMore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
