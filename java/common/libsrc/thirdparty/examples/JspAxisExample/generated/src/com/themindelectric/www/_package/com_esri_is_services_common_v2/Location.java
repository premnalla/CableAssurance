/**
 * Location.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.themindelectric.www._package.com_esri_is_services_common_v2;

public class Location  implements java.io.Serializable {
    private com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point point;

    private java.lang.String description1;

    private java.lang.String description2;

    private double score;

    private java.lang.String matchType;

    private java.lang.String type;

    private com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Envelope locationExtent;

    public Location() {
    }

    public Location(
           com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point point,
           java.lang.String description1,
           java.lang.String description2,
           double score,
           java.lang.String matchType,
           java.lang.String type,
           com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Envelope locationExtent) {
           this.point = point;
           this.description1 = description1;
           this.description2 = description2;
           this.score = score;
           this.matchType = matchType;
           this.type = type;
           this.locationExtent = locationExtent;
    }


    /**
     * Gets the point value for this Location.
     * 
     * @return point
     */
    public com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point getPoint() {
        return point;
    }


    /**
     * Sets the point value for this Location.
     * 
     * @param point
     */
    public void setPoint(com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point point) {
        this.point = point;
    }


    /**
     * Gets the description1 value for this Location.
     * 
     * @return description1
     */
    public java.lang.String getDescription1() {
        return description1;
    }


    /**
     * Sets the description1 value for this Location.
     * 
     * @param description1
     */
    public void setDescription1(java.lang.String description1) {
        this.description1 = description1;
    }


    /**
     * Gets the description2 value for this Location.
     * 
     * @return description2
     */
    public java.lang.String getDescription2() {
        return description2;
    }


    /**
     * Sets the description2 value for this Location.
     * 
     * @param description2
     */
    public void setDescription2(java.lang.String description2) {
        this.description2 = description2;
    }


    /**
     * Gets the score value for this Location.
     * 
     * @return score
     */
    public double getScore() {
        return score;
    }


    /**
     * Sets the score value for this Location.
     * 
     * @param score
     */
    public void setScore(double score) {
        this.score = score;
    }


    /**
     * Gets the matchType value for this Location.
     * 
     * @return matchType
     */
    public java.lang.String getMatchType() {
        return matchType;
    }


    /**
     * Sets the matchType value for this Location.
     * 
     * @param matchType
     */
    public void setMatchType(java.lang.String matchType) {
        this.matchType = matchType;
    }


    /**
     * Gets the type value for this Location.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Location.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the locationExtent value for this Location.
     * 
     * @return locationExtent
     */
    public com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Envelope getLocationExtent() {
        return locationExtent;
    }


    /**
     * Sets the locationExtent value for this Location.
     * 
     * @param locationExtent
     */
    public void setLocationExtent(com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Envelope locationExtent) {
        this.locationExtent = locationExtent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Location)) return false;
        Location other = (Location) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.point==null && other.getPoint()==null) || 
             (this.point!=null &&
              this.point.equals(other.getPoint()))) &&
            ((this.description1==null && other.getDescription1()==null) || 
             (this.description1!=null &&
              this.description1.equals(other.getDescription1()))) &&
            ((this.description2==null && other.getDescription2()==null) || 
             (this.description2!=null &&
              this.description2.equals(other.getDescription2()))) &&
            this.score == other.getScore() &&
            ((this.matchType==null && other.getMatchType()==null) || 
             (this.matchType!=null &&
              this.matchType.equals(other.getMatchType()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.locationExtent==null && other.getLocationExtent()==null) || 
             (this.locationExtent!=null &&
              this.locationExtent.equals(other.getLocationExtent())));
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
        if (getPoint() != null) {
            _hashCode += getPoint().hashCode();
        }
        if (getDescription1() != null) {
            _hashCode += getDescription1().hashCode();
        }
        if (getDescription2() != null) {
            _hashCode += getDescription2().hashCode();
        }
        _hashCode += new Double(getScore()).hashCode();
        if (getMatchType() != null) {
            _hashCode += getMatchType().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getLocationExtent() != null) {
            _hashCode += getLocationExtent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Location.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2/", "Location"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("point");
        elemField.setXmlName(new javax.xml.namespace.QName("", "point"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "Point"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("score");
        elemField.setXmlName(new javax.xml.namespace.QName("", "score"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matchType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationExtent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "locationExtent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.themindelectric.com/package/com.esri.is.services.common.v2.geom/", "Envelope"));
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
