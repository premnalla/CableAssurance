/**
 * StockQuoteService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package StockQuoteService_pkg;

public interface StockQuoteService extends javax.xml.rpc.Service {
    public java.lang.String getStockQuoteServiceSOAP11portAddress();

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP11port() throws javax.xml.rpc.ServiceException;

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP11port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getStockQuoteServiceSOAP12portAddress();

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP12port() throws javax.xml.rpc.ServiceException;

    public StockQuoteService_pkg.StockQuoteServicePortType getStockQuoteServiceSOAP12port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
