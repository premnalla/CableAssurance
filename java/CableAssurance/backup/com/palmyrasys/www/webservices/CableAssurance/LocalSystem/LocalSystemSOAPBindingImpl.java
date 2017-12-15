/**
 * LocalSystemSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.LocalSystem;

public class LocalSystemSOAPBindingImpl implements com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemEP{
    public com.palmyrasys.www.webservices.CableAssurance.LocalSystemT getLocalSystem() throws java.rmi.RemoteException {
        // return null;
    	System.out.println("Service invoked");
    	com.palmyrasys.www.webservices.CableAssurance.LocalSystemT ret = new com.palmyrasys.www.webservices.CableAssurance.LocalSystemT(
    			com.palmyrasys.www.webservices.CableAssurance.SystemTypeT.MarketServer, "Oxford, MA");
    	return (ret);
    }

}
