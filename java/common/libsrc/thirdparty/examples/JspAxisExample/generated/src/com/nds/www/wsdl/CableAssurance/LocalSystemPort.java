/**
 * LocalSystemPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public interface LocalSystemPort extends java.rmi.Remote {
    public com.nds.www.wsdl.CableAssurance.LocalSystemType queryLocalSystem() throws java.rmi.RemoteException;
    public com.nds.www.wsdl.CableAssurance.AggregateType[] queryChildAggregates() throws java.rmi.RemoteException;
    public com.nds.www.wsdl.CableAssurance.CmtsType[] queryChildCmts() throws java.rmi.RemoteException;
}
