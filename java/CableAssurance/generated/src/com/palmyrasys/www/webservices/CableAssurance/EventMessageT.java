/**
 * EventMessageT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class EventMessageT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.EventTypeT eventType;

    private com.palmyrasys.www.webservices.CableAssurance.EventCategoryT eventCategory;

    private java.lang.String eventSubCategory;

    private java.lang.String objectId;

    public EventMessageT() {
    }

    public EventMessageT(
           com.palmyrasys.www.webservices.CableAssurance.EventTypeT eventType,
           com.palmyrasys.www.webservices.CableAssurance.EventCategoryT eventCategory,
           java.lang.String eventSubCategory,
           java.lang.String objectId) {
           this.eventType = eventType;
           this.eventCategory = eventCategory;
           this.eventSubCategory = eventSubCategory;
           this.objectId = objectId;
    }


    /**
     * Gets the eventType value for this EventMessageT.
     * 
     * @return eventType
     */
    public com.palmyrasys.www.webservices.CableAssurance.EventTypeT getEventType() {
        return eventType;
    }


    /**
     * Sets the eventType value for this EventMessageT.
     * 
     * @param eventType
     */
    public void setEventType(com.palmyrasys.www.webservices.CableAssurance.EventTypeT eventType) {
        this.eventType = eventType;
    }


    /**
     * Gets the eventCategory value for this EventMessageT.
     * 
     * @return eventCategory
     */
    public com.palmyrasys.www.webservices.CableAssurance.EventCategoryT getEventCategory() {
        return eventCategory;
    }


    /**
     * Sets the eventCategory value for this EventMessageT.
     * 
     * @param eventCategory
     */
    public void setEventCategory(com.palmyrasys.www.webservices.CableAssurance.EventCategoryT eventCategory) {
        this.eventCategory = eventCategory;
    }


    /**
     * Gets the eventSubCategory value for this EventMessageT.
     * 
     * @return eventSubCategory
     */
    public java.lang.String getEventSubCategory() {
        return eventSubCategory;
    }


    /**
     * Sets the eventSubCategory value for this EventMessageT.
     * 
     * @param eventSubCategory
     */
    public void setEventSubCategory(java.lang.String eventSubCategory) {
        this.eventSubCategory = eventSubCategory;
    }


    /**
     * Gets the objectId value for this EventMessageT.
     * 
     * @return objectId
     */
    public java.lang.String getObjectId() {
        return objectId;
    }


    /**
     * Sets the objectId value for this EventMessageT.
     * 
     * @param objectId
     */
    public void setObjectId(java.lang.String objectId) {
        this.objectId = objectId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EventMessageT)) return false;
        EventMessageT other = (EventMessageT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.eventType==null && other.getEventType()==null) || 
             (this.eventType!=null &&
              this.eventType.equals(other.getEventType()))) &&
            ((this.eventCategory==null && other.getEventCategory()==null) || 
             (this.eventCategory!=null &&
              this.eventCategory.equals(other.getEventCategory()))) &&
            ((this.eventSubCategory==null && other.getEventSubCategory()==null) || 
             (this.eventSubCategory!=null &&
              this.eventSubCategory.equals(other.getEventSubCategory()))) &&
            ((this.objectId==null && other.getObjectId()==null) || 
             (this.objectId!=null &&
              this.objectId.equals(other.getObjectId())));
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
        if (getEventType() != null) {
            _hashCode += getEventType().hashCode();
        }
        if (getEventCategory() != null) {
            _hashCode += getEventCategory().hashCode();
        }
        if (getEventSubCategory() != null) {
            _hashCode += getEventSubCategory().hashCode();
        }
        if (getObjectId() != null) {
            _hashCode += getObjectId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EventMessageT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EventMessageT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EventTypeT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "EventCategoryT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventSubCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventSubCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objectId"));
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
