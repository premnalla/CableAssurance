/**
 * StockQuoteService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl;

public interface StockQuoteService extends javax.xml.rpc.Service {
    public java.lang.String getStockQuoteSOAPPortAddress();

    public com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType getStockQuoteSOAPPort() throws javax.xml.rpc.ServiceException;

    public com.ibm.w3.schemas.services._2002._11._15.stockquote.wsdl.StockQuotePortType getStockQuoteSOAPPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
