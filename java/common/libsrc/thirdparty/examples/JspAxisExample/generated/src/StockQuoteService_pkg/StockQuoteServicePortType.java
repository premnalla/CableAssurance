/**
 * StockQuoteServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package StockQuoteService_pkg;

public interface StockQuoteServicePortType extends java.rmi.Remote {
    public double getPrice(java.lang.String symbol) throws java.rmi.RemoteException;
    public void update(java.lang.String symbol, double price) throws java.rmi.RemoteException;
}
