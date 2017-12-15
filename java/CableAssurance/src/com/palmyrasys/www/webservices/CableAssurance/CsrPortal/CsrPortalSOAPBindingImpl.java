/**
 * CsrPortalSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.CsrPortal;

import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;

public class CsrPortalSOAPBindingImpl implements com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalEP{
    public com.palmyrasys.www.webservices.CableAssurance.CTEDataT[] getCustomerByName(java.lang.String firstName, java.lang.String lastName) throws java.rmi.RemoteException {
    	CsrPortalEP t = LocalSystemSingleton.getInstance().getCsrPortalHandler();
    	return (t.getCustomerByName(firstName, lastName));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT getCmByMac(java.lang.String macAddress) throws java.rmi.RemoteException {
    	CsrPortalEP t = LocalSystemSingleton.getInstance().getCsrPortalHandler();
    	return (t.getCmByMac(macAddress));
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT getMtaByMac(java.lang.String macAddress) throws java.rmi.RemoteException {
    	CsrPortalEP t = LocalSystemSingleton.getInstance().getCsrPortalHandler();
    	return (t.getMtaByMac(macAddress));
    }

}
