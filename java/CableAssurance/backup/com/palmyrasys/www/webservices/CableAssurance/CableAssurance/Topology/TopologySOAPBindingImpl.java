/**
 * TopologySOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Topology;

import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.*;

public class TopologySOAPBindingImpl implements com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyEP{
    public com.palmyrasys.www.webservices.CableAssurance.RegionT[] getRegions() throws java.rmi.RemoteException {
        return null;
    }

    public com.palmyrasys.www.webservices.CableAssurance.MarketT[] getMarkets() throws java.rmi.RemoteException {
        return null;
    }

    public com.palmyrasys.www.webservices.CableAssurance.BladeT[] getBlades() throws java.rmi.RemoteException {
        return null;
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmtsT[] getCmtses() throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getCmtses());
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmtsT getCmts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getCmts(topologyKey, cmtsResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.ChannelT[] getChannels(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getChannels(topologyKey, cmtsResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.ChannelT getChannel(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getChannel(topologyKey, channelResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.HfcT[] getHfcs(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getHfcs(topologyKey, cmtsResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.HfcT getHfc(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getHfc(topologyKey, hfcResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT getCableModem(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getCableModem(topologyKey, cmResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT[] getChannelCmes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getChannelCmes(topologyKey, channelResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CableModemT[] getHfcCmes(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getHfcCmes(topologyKey, hfcResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT getEmta(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger emtaResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getEmta(topologyKey, emtaResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT[] getChannelEmtas(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getChannelEmtas(topologyKey, channelResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.EmtaT[] getHfcEmtas(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
    	TopologyEP t = LocalSystemSingleton.getInstance().getHandler();
    	return (t.getHfcEmtas(topologyKey, hfcResId));
    }

}
