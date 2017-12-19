/**
 * GetQuote.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.stock;

public interface GetQuote extends java.rmi.Remote {
    public float getQuote(java.lang.String symbol) throws java.rmi.RemoteException;
    public java.lang.String test() throws java.rmi.RemoteException;
}
