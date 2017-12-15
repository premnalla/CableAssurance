/**
 * CPeerServiceEP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.CPeerService;

public interface CPeerServiceEP extends java.rmi.Remote {
    public java.lang.String pingMta(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.MtaDataT getMtaData(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmDataT getCmData(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException;
    public com.palmyrasys.www.webservices.CableAssurance.CmtsCmDataT getCmtsCmData(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId, java.math.BigInteger cmResId) throws java.rmi.RemoteException;
    public short sendEvent(com.palmyrasys.www.webservices.CableAssurance.EventMessageT event) throws java.rmi.RemoteException;
}
