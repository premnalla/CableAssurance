/**
 * LocalSystemSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public class LocalSystemSOAPBindingImpl implements com.nds.www.wsdl.CableAssurance.LocalSystemPort{
    public com.nds.www.wsdl.CableAssurance.LocalSystemType queryLocalSystem() throws java.rmi.RemoteException {
        // return null;
    	System.out.println("Service invoked");
    	com.nds.www.wsdl.CableAssurance.LocalSystemType ret = new com.nds.www.wsdl.CableAssurance.LocalSystemType(
    			com.nds.www.wsdl.CableAssurance.SystemTypeType.MarketServer);
    	return (ret);
    }

    public com.nds.www.wsdl.CableAssurance.AggregateType[] queryChildAggregates() throws java.rmi.RemoteException {
        return null;
    }

    public com.nds.www.wsdl.CableAssurance.CmtsType[] queryChildCmts() throws java.rmi.RemoteException {
        return null;
    }

}
