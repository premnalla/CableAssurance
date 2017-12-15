/**
 * LocalSystemSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.LocalSystem;

public class LocalSystemSOAPBindingImpl implements com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemEP{
    public com.nds.www.wsdl.CableAssurance.LocalSystemT getLocalSystem() throws java.rmi.RemoteException {
        // return null;
    	System.out.println("Service invoked");
    	com.nds.www.wsdl.CableAssurance.LocalSystemT ret = new com.nds.www.wsdl.CableAssurance.LocalSystemT(
    			com.nds.www.wsdl.CableAssurance.SystemTypeT.MarketServer, "Oxford, MA");
    	return (ret);
    }

}
