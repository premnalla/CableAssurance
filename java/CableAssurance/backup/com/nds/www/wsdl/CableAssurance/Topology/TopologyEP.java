/**
 * TopologyEP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.Topology;

public interface TopologyEP extends java.rmi.Remote {
    public com.nds.www.wsdl.CableAssurance.RegionT[] getRegions() throws java.rmi.RemoteException;
    public com.nds.www.wsdl.CableAssurance.MarketT[] getMarkets() throws java.rmi.RemoteException;
    public com.nds.www.wsdl.CableAssurance.BladeT[] getBlades() throws java.rmi.RemoteException;
    public com.nds.www.wsdl.CableAssurance.CmtsT[] getCmtses() throws java.rmi.RemoteException;
}
