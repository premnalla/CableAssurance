/**
 * Point.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.themindelectric.www._package.com_esri_is_services_common_v2_geom;

public class Point  implements java.io.Serializable {
    private double x;

    private double y;

    private com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem coordinateSystem;

    public Point() {
    }

    public Point(
           double x,
           double y,
           com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem coordinateSystem) {
           this.x = x;
           this.y = y;
           this.coordinateSystem = coordinateSystem;
    }


    /**
     * Gets the x value for this Point.
     * 
     * @return x
     */
    public double getX() {
        return x;
    }


    /**
     * Sets the x value for this Point.
     * 
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Gets the y value for this Point.
     * 
     * @return y
     */
    public double getY() {
        return y;
    }


    /**
     * Sets the y value for this Point.
     * 
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Gets the coordinateSystem value for this Point.
     * 
     * @return coordinateSystem
     */
    public com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }


    /**
     * Sets the coordinateSystem value for this Point.
     * 
     * @param coordinateSystem
     */
    public void setCoordinateSystem(com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.x == other.getX() &&
            this.y == other.getY() &&
            ((this.coordinateSystem==null && other.getCoordinateSystem()==null) || 
             (this.coordinateSystem!=null &&
              this.coordinateSystem.equals(other.getCoordinateSystem())));
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
        _hashCode += new Double(getX()).hashCode();
        _hashCode += new Double(getY()).hashCode();
        if (getCoordinateSystem() != null) {
            _hashCode += getCoordinateSystem().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Point.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "Point"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("x");
        elemField.setXmlName(new javax.xml.namespace.QName("", "x"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("y");
        elemField.setXmlName(new javax.xml.namespace.QName("", "y"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinateSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coordinateSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "CoordinateSystem"));
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
