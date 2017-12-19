/**
 * Quote.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ibm.w3.schemas.services._2002._11._15.stockquote;

public class Quote  implements java.io.Serializable {
    private java.lang.String symbol;

    private java.math.BigInteger volume;

    private com.ibm.w3.schemas.services._2002._11._15.stockquote.LastTradeType lastTrade;

    private com.ibm.w3.schemas.services._2002._11._15.stockquote.ChangeType change;

    public Quote() {
    }

    public Quote(
           java.lang.String symbol,
           java.math.BigInteger volume,
           com.ibm.w3.schemas.services._2002._11._15.stockquote.LastTradeType lastTrade,
           com.ibm.w3.schemas.services._2002._11._15.stockquote.ChangeType change) {
           this.symbol = symbol;
           this.volume = volume;
           this.lastTrade = lastTrade;
           this.change = change;
    }


    /**
     * Gets the symbol value for this Quote.
     * 
     * @return symbol
     */
    public java.lang.String getSymbol() {
        return symbol;
    }


    /**
     * Sets the symbol value for this Quote.
     * 
     * @param symbol
     */
    public void setSymbol(java.lang.String symbol) {
        this.symbol = symbol;
    }


    /**
     * Gets the volume value for this Quote.
     * 
     * @return volume
     */
    public java.math.BigInteger getVolume() {
        return volume;
    }


    /**
     * Sets the volume value for this Quote.
     * 
     * @param volume
     */
    public void setVolume(java.math.BigInteger volume) {
        this.volume = volume;
    }


    /**
     * Gets the lastTrade value for this Quote.
     * 
     * @return lastTrade
     */
    public com.ibm.w3.schemas.services._2002._11._15.stockquote.LastTradeType getLastTrade() {
        return lastTrade;
    }


    /**
     * Sets the lastTrade value for this Quote.
     * 
     * @param lastTrade
     */
    public void setLastTrade(com.ibm.w3.schemas.services._2002._11._15.stockquote.LastTradeType lastTrade) {
        this.lastTrade = lastTrade;
    }


    /**
     * Gets the change value for this Quote.
     * 
     * @return change
     */
    public com.ibm.w3.schemas.services._2002._11._15.stockquote.ChangeType getChange() {
        return change;
    }


    /**
     * Sets the change value for this Quote.
     * 
     * @param change
     */
    public void setChange(com.ibm.w3.schemas.services._2002._11._15.stockquote.ChangeType change) {
        this.change = change;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Quote)) return false;
        Quote other = (Quote) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.symbol==null && other.getSymbol()==null) || 
             (this.symbol!=null &&
              this.symbol.equals(other.getSymbol()))) &&
            ((this.volume==null && other.getVolume()==null) || 
             (this.volume!=null &&
              this.volume.equals(other.getVolume()))) &&
            ((this.lastTrade==null && other.getLastTrade()==null) || 
             (this.lastTrade!=null &&
              this.lastTrade.equals(other.getLastTrade()))) &&
            ((this.change==null && other.getChange()==null) || 
             (this.change!=null &&
              this.change.equals(other.getChange())));
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
        if (getSymbol() != null) {
            _hashCode += getSymbol().hashCode();
        }
        if (getVolume() != null) {
            _hashCode += getVolume().hashCode();
        }
        if (getLastTrade() != null) {
            _hashCode += getLastTrade().hashCode();
        }
        if (getChange() != null) {
            _hashCode += getChange().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Quote.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", ">quote"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("symbol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "symbol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volume");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastTrade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastTrade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", "lastTradeType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("change");
        elemField.setXmlName(new javax.xml.namespace.QName("", "change"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://w3.ibm.com/schemas/services/2002/11/15/stockquote", "changeType"));
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
