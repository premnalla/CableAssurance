/**
 * CTEDataT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class CTEDataT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.CTECustomerT customer;

    private com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT cm;

    private com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT mta;

    private com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT cmts;

    private com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT cms;

    private java.lang.String hfcName;

    public CTEDataT() {
    }

    public CTEDataT(
           com.palmyrasys.www.webservices.CableAssurance.CTECustomerT customer,
           com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT cm,
           com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT mta,
           com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT cmts,
           com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT cms,
           java.lang.String hfcName) {
           this.customer = customer;
           this.cm = cm;
           this.mta = mta;
           this.cmts = cmts;
           this.cms = cms;
           this.hfcName = hfcName;
    }


    /**
     * Gets the customer value for this CTEDataT.
     * 
     * @return customer
     */
    public com.palmyrasys.www.webservices.CableAssurance.CTECustomerT getCustomer() {
        return customer;
    }


    /**
     * Sets the customer value for this CTEDataT.
     * 
     * @param customer
     */
    public void setCustomer(com.palmyrasys.www.webservices.CableAssurance.CTECustomerT customer) {
        this.customer = customer;
    }


    /**
     * Gets the cm value for this CTEDataT.
     * 
     * @return cm
     */
    public com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT getCm() {
        return cm;
    }


    /**
     * Sets the cm value for this CTEDataT.
     * 
     * @param cm
     */
    public void setCm(com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT cm) {
        this.cm = cm;
    }


    /**
     * Gets the mta value for this CTEDataT.
     * 
     * @return mta
     */
    public com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT getMta() {
        return mta;
    }


    /**
     * Sets the mta value for this CTEDataT.
     * 
     * @param mta
     */
    public void setMta(com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT mta) {
        this.mta = mta;
    }


    /**
     * Gets the cmts value for this CTEDataT.
     * 
     * @return cmts
     */
    public com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT getCmts() {
        return cmts;
    }


    /**
     * Sets the cmts value for this CTEDataT.
     * 
     * @param cmts
     */
    public void setCmts(com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT cmts) {
        this.cmts = cmts;
    }


    /**
     * Gets the cms value for this CTEDataT.
     * 
     * @return cms
     */
    public com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT getCms() {
        return cms;
    }


    /**
     * Sets the cms value for this CTEDataT.
     * 
     * @param cms
     */
    public void setCms(com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT cms) {
        this.cms = cms;
    }


    /**
     * Gets the hfcName value for this CTEDataT.
     * 
     * @return hfcName
     */
    public java.lang.String getHfcName() {
        return hfcName;
    }


    /**
     * Sets the hfcName value for this CTEDataT.
     * 
     * @param hfcName
     */
    public void setHfcName(java.lang.String hfcName) {
        this.hfcName = hfcName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CTEDataT)) return false;
        CTEDataT other = (CTEDataT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.customer==null && other.getCustomer()==null) || 
             (this.customer!=null &&
              this.customer.equals(other.getCustomer()))) &&
            ((this.cm==null && other.getCm()==null) || 
             (this.cm!=null &&
              this.cm.equals(other.getCm()))) &&
            ((this.mta==null && other.getMta()==null) || 
             (this.mta!=null &&
              this.mta.equals(other.getMta()))) &&
            ((this.cmts==null && other.getCmts()==null) || 
             (this.cmts!=null &&
              this.cmts.equals(other.getCmts()))) &&
            ((this.cms==null && other.getCms()==null) || 
             (this.cms!=null &&
              this.cms.equals(other.getCms()))) &&
            ((this.hfcName==null && other.getHfcName()==null) || 
             (this.hfcName!=null &&
              this.hfcName.equals(other.getHfcName())));
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
        if (getCustomer() != null) {
            _hashCode += getCustomer().hashCode();
        }
        if (getCm() != null) {
            _hashCode += getCm().hashCode();
        }
        if (getMta() != null) {
            _hashCode += getMta().hashCode();
        }
        if (getCmts() != null) {
            _hashCode += getCmts().hashCode();
        }
        if (getCms() != null) {
            _hashCode += getCms().hashCode();
        }
        if (getHfcName() != null) {
            _hashCode += getHfcName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CTEDataT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CTEDataT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CTECustomerT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CTEAbstractMacT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CTEAbstractMacT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CTEAbstractNameT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "CTEAbstractNameT"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hfcName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hfcName"));
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
