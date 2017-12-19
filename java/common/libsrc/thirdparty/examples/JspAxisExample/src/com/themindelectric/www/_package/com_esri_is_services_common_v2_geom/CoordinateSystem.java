/**
 * CoordinateSystem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.themindelectric.www._package.com_esri_is_services_common_v2_geom;

public class CoordinateSystem  implements java.io.Serializable {
    private java.lang.String projection;

    private java.lang.String datumTransformation;

    public CoordinateSystem() {
    }

    public CoordinateSystem(
           java.lang.String projection,
           java.lang.String datumTransformation) {
           this.projection = projection;
           this.datumTransformation = datumTransformation;
    }


    /**
     * Gets the projection value for this CoordinateSystem.
     * 
     * @return projection
     */
    public java.lang.String getProjection() {
        return projection;
    }


    /**
     * Sets the projection value for this CoordinateSystem.
     * 
     * @param projection
     */
    public void setProjection(java.lang.String projection) {
        this.projection = projection;
    }


    /**
     * Gets the datumTransformation value for this CoordinateSystem.
     * 
     * @return datumTransformation
     */
    public java.lang.String getDatumTransformation() {
        return datumTransformation;
    }


    /**
     * Sets the datumTransformation value for this CoordinateSystem.
     * 
     * @param datumTransformation
     */
    public void setDatumTransformation(java.lang.String datumTransformation) {
        this.datumTransformation = datumTransformation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CoordinateSystem)) return false;
        CoordinateSystem other = (CoordinateSystem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.projection==null && other.getProjection()==null) || 
             (this.projection!=null &&
              this.projection.equals(other.getProjection()))) &&
            ((this.datumTransformation==null && other.getDatumTransformation()==null) || 
             (this.datumTransformation!=null &&
              this.datumTransformation.equals(other.getDatumTransformation())));
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
        if (getProjection() != null) {
            _hashCode += getProjection().hashCode();
        }
        if (getDatumTransformation() != null) {
            _hashCode += getDatumTransformation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CoordinateSystem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "CoordinateSystem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projection");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datumTransformation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datumTransformation"));
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
