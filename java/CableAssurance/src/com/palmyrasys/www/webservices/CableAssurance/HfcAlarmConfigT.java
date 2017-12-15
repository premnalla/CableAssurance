/**
 * HfcAlarmConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

public class HfcAlarmConfigT  implements java.io.Serializable {
    private com.palmyrasys.www.webservices.CableAssurance.AggregateCmOfflineTresholdT cmThresold;

    private com.palmyrasys.www.webservices.CableAssurance.SoakWindowT cmSoakWindow;

    private com.palmyrasys.www.webservices.CableAssurance.AggregateMtaTresholdT mtaThresold;

    private com.palmyrasys.www.webservices.CableAssurance.SoakWindowT mtaSoakWindow;

    private com.palmyrasys.www.webservices.CableAssurance.HfcPowerTresholdT powerThresold;

    private com.palmyrasys.www.webservices.CableAssurance.SoakWindowT powerSoakWindow;

    public HfcAlarmConfigT() {
    }

    public HfcAlarmConfigT(
           com.palmyrasys.www.webservices.CableAssurance.AggregateCmOfflineTresholdT cmThresold,
           com.palmyrasys.www.webservices.CableAssurance.SoakWindowT cmSoakWindow,
           com.palmyrasys.www.webservices.CableAssurance.AggregateMtaTresholdT mtaThresold,
           com.palmyrasys.www.webservices.CableAssurance.SoakWindowT mtaSoakWindow,
           com.palmyrasys.www.webservices.CableAssurance.HfcPowerTresholdT powerThresold,
           com.palmyrasys.www.webservices.CableAssurance.SoakWindowT powerSoakWindow) {
           this.cmThresold = cmThresold;
           this.cmSoakWindow = cmSoakWindow;
           this.mtaThresold = mtaThresold;
           this.mtaSoakWindow = mtaSoakWindow;
           this.powerThresold = powerThresold;
           this.powerSoakWindow = powerSoakWindow;
    }


    /**
     * Gets the cmThresold value for this HfcAlarmConfigT.
     * 
     * @return cmThresold
     */
    public com.palmyrasys.www.webservices.CableAssurance.AggregateCmOfflineTresholdT getCmThresold() {
        return cmThresold;
    }


    /**
     * Sets the cmThresold value for this HfcAlarmConfigT.
     * 
     * @param cmThresold
     */
    public void setCmThresold(com.palmyrasys.www.webservices.CableAssurance.AggregateCmOfflineTresholdT cmThresold) {
        this.cmThresold = cmThresold;
    }


    /**
     * Gets the cmSoakWindow value for this HfcAlarmConfigT.
     * 
     * @return cmSoakWindow
     */
    public com.palmyrasys.www.webservices.CableAssurance.SoakWindowT getCmSoakWindow() {
        return cmSoakWindow;
    }


    /**
     * Sets the cmSoakWindow value for this HfcAlarmConfigT.
     * 
     * @param cmSoakWindow
     */
    public void setCmSoakWindow(com.palmyrasys.www.webservices.CableAssurance.SoakWindowT cmSoakWindow) {
        this.cmSoakWindow = cmSoakWindow;
    }


    /**
     * Gets the mtaThresold value for this HfcAlarmConfigT.
     * 
     * @return mtaThresold
     */
    public com.palmyrasys.www.webservices.CableAssurance.AggregateMtaTresholdT getMtaThresold() {
        return mtaThresold;
    }


    /**
     * Sets the mtaThresold value for this HfcAlarmConfigT.
     * 
     * @param mtaThresold
     */
    public void setMtaThresold(com.palmyrasys.www.webservices.CableAssurance.AggregateMtaTresholdT mtaThresold) {
        this.mtaThresold = mtaThresold;
    }


    /**
     * Gets the mtaSoakWindow value for this HfcAlarmConfigT.
     * 
     * @return mtaSoakWindow
     */
    public com.palmyrasys.www.webservices.CableAssurance.SoakWindowT getMtaSoakWindow() {
        return mtaSoakWindow;
    }


    /**
     * Sets the mtaSoakWindow value for this HfcAlarmConfigT.
     * 
     * @param mtaSoakWindow
     */
    public void setMtaSoakWindow(com.palmyrasys.www.webservices.CableAssurance.SoakWindowT mtaSoakWindow) {
        this.mtaSoakWindow = mtaSoakWindow;
    }


    /**
     * Gets the powerThresold value for this HfcAlarmConfigT.
     * 
     * @return powerThresold
     */
    public com.palmyrasys.www.webservices.CableAssurance.HfcPowerTresholdT getPowerThresold() {
        return powerThresold;
    }


    /**
     * Sets the powerThresold value for this HfcAlarmConfigT.
     * 
     * @param powerThresold
     */
    public void setPowerThresold(com.palmyrasys.www.webservices.CableAssurance.HfcPowerTresholdT powerThresold) {
        this.powerThresold = powerThresold;
    }


    /**
     * Gets the powerSoakWindow value for this HfcAlarmConfigT.
     * 
     * @return powerSoakWindow
     */
    public com.palmyrasys.www.webservices.CableAssurance.SoakWindowT getPowerSoakWindow() {
        return powerSoakWindow;
    }


    /**
     * Sets the powerSoakWindow value for this HfcAlarmConfigT.
     * 
     * @param powerSoakWindow
     */
    public void setPowerSoakWindow(com.palmyrasys.www.webservices.CableAssurance.SoakWindowT powerSoakWindow) {
        this.powerSoakWindow = powerSoakWindow;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HfcAlarmConfigT)) return false;
        HfcAlarmConfigT other = (HfcAlarmConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cmThresold==null && other.getCmThresold()==null) || 
             (this.cmThresold!=null &&
              this.cmThresold.equals(other.getCmThresold()))) &&
            ((this.cmSoakWindow==null && other.getCmSoakWindow()==null) || 
             (this.cmSoakWindow!=null &&
              this.cmSoakWindow.equals(other.getCmSoakWindow()))) &&
            ((this.mtaThresold==null && other.getMtaThresold()==null) || 
             (this.mtaThresold!=null &&
              this.mtaThresold.equals(other.getMtaThresold()))) &&
            ((this.mtaSoakWindow==null && other.getMtaSoakWindow()==null) || 
             (this.mtaSoakWindow!=null &&
              this.mtaSoakWindow.equals(other.getMtaSoakWindow()))) &&
            ((this.powerThresold==null && other.getPowerThresold()==null) || 
             (this.powerThresold!=null &&
              this.powerThresold.equals(other.getPowerThresold()))) &&
            ((this.powerSoakWindow==null && other.getPowerSoakWindow()==null) || 
             (this.powerSoakWindow!=null &&
              this.powerSoakWindow.equals(other.getPowerSoakWindow())));
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
        if (getCmThresold() != null) {
            _hashCode += getCmThresold().hashCode();
        }
        if (getCmSoakWindow() != null) {
            _hashCode += getCmSoakWindow().hashCode();
        }
        if (getMtaThresold() != null) {
            _hashCode += getMtaThresold().hashCode();
        }
        if (getMtaSoakWindow() != null) {
            _hashCode += getMtaSoakWindow().hashCode();
        }
        if (getPowerThresold() != null) {
            _hashCode += getPowerThresold().hashCode();
        }
        if (getPowerSoakWindow() != null) {
            _hashCode += getPowerSoakWindow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HfcAlarmConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcAlarmConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmThresold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmThresold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AggregateCmOfflineTresholdT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmSoakWindow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmSoakWindow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SoakWindowT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaThresold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaThresold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AggregateMtaTresholdT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtaSoakWindow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtaSoakWindow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SoakWindowT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("powerThresold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "powerThresold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "HfcPowerTresholdT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("powerSoakWindow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "powerSoakWindow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "SoakWindowT"));
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
