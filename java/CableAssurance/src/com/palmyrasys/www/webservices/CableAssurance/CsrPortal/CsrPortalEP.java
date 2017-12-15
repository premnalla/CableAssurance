/**
 * CsrPortalEP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.CsrPortal;

public interface CsrPortalEP extends java.rmi.Remote {
    public com.palmyrasys.www.webservices.CableAssurance.CTEDataT[] getCustomerByName(java.lang.String firstName, java.lang.String lastName) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CableModemT getCmByMac(java.lang.String macAddress) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.EmtaT getMtaByMac(java.lang.String macAddress) throws java.rmi.RemoteException;
}
