/**
 * Envelope.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.themindelectric.www._package.com_esri_is_services_common_v2_geom;

public class Envelope  implements java.io.Serializable {
    private double minx;

    private double miny;

    private double maxx;

    private double maxy;

    private com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem coordinateSystem;

    public Envelope() {
    }

    public Envelope(
           double minx,
           double miny,
           double maxx,
           double maxy,
           com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem coordinateSystem) {
           this.minx = minx;
           this.miny = miny;
           this.maxx = maxx;
           this.maxy = maxy;
           this.coordinateSystem = coordinateSystem;
    }


    /**
     * Gets the minx value for this Envelope.
     * 
     * @return minx
     */
    public double getMinx() {
        return minx;
    }


    /**
     * Sets the minx value for this Envelope.
     * 
     * @param minx
     */
    public void setMinx(double minx) {
        this.minx = minx;
    }


    /**
     * Gets the miny value for this Envelope.
     * 
     * @return miny
     */
    public double getMiny() {
        return miny;
    }


    /**
     * Sets the miny value for this Envelope.
     * 
     * @param miny
     */
    public void setMiny(double miny) {
        this.miny = miny;
    }


    /**
     * Gets the maxx value for this Envelope.
     * 
     * @return maxx
     */
    public double getMaxx() {
        return maxx;
    }


    /**
     * Sets the maxx value for this Envelope.
     * 
     * @param maxx
     */
    public void setMaxx(double maxx) {
        this.maxx = maxx;
    }


    /**
     * Gets the maxy value for this Envelope.
     * 
     * @return maxy
     */
    public double getMaxy() {
        return maxy;
    }


    /**
     * Sets the maxy value for this Envelope.
     * 
     * @param maxy
     */
    public void setMaxy(double maxy) {
        this.maxy = maxy;
    }


    /**
     * Gets the coordinateSystem value for this Envelope.
     * 
     * @return coordinateSystem
     */
    public com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }


    /**
     * Sets the coordinateSystem value for this Envelope.
     * 
     * @param coordinateSystem
     */
    public void setCoordinateSystem(com.themindelectric.www._package.com_esri_is_services_common_v2_geom.CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Envelope)) return false;
        Envelope other = (Envelope) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.minx == other.getMinx() &&
            this.miny == other.getMiny() &&
            this.maxx == other.getMaxx() &&
            this.maxy == other.getMaxy() &&
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
        _hashCode += new Double(getMinx()).hashCode();
        _hashCode += new Double(getMiny()).hashCode();
        _hashCode += new Double(getMaxx()).hashCode();
        _hashCode += new Double(getMaxy()).hashCode();
        if (getCoordinateSystem() != null) {
            _hashCode += getCoordinateSystem().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Envelope.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "Envelope"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minx");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("miny");
        elemField.setXmlName(new javax.xml.namespace.QName("", "miny"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxx");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxy"));
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
