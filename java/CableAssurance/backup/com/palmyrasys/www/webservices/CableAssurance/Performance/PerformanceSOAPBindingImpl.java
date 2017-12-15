/**
 * PerformanceSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Performance;

//Prem:
import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;

public class PerformanceSOAPBindingImpl implements com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceEP{
    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCurrentCmtsCounts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentCmtsCounts(topologyKey, cmtsResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] getCmtsCountsHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmtsResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCmtsCountsHistory(topologyKey, cmtsResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCurrentChannelCounts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentChannelCounts(topologyKey, channelResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] getChannelCountsHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger channelResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getChannelCountsHistory(topologyKey, channelResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsT getCurrentHfcCounts(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentHfcCounts(topologyKey, hfcResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] getHfcCountsHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger hfcResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getHfcCountsHistory(topologyKey, hfcResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusT getCurrentCmStatus(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentCmStatus(topologyKey, cmResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[] getCmStatusHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCmStatusHistory(topologyKey, cmResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT getCurrentCmPerformance(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentCmPerformance(topologyKey, cmResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[] getCmPerformanceHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger cmResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCmPerformanceHistory(topologyKey, cmResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT getCurrentMtaAvailability(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentMtaAvailability(topologyKey, mtaResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[] getMtaAvailabilityHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getMtaAvailabilityHistory(topologyKey, mtaResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT getCurrentMtaProvisionedStatus(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentMtaProvisionedStatus(topologyKey, mtaResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[] getMtaProvisionedStatusHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getMtaProvisionedStatusHistory(topologyKey, mtaResId, fromTime, toTime, batch));
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT getCurrentMtaPingStatus(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getCurrentMtaPingStatus(topologyKey, mtaResId));
    }

    public com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[] getMtaPingStatusHistory(com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey, java.math.BigInteger mtaResId, com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime, com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime, com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch) throws java.rmi.RemoteException {
    	PerformanceEP aEP = LocalSystemSingleton.getInstance().getPerformanceHandler();
    	return (aEP.getMtaPingStatusHistory(topologyKey, mtaResId, fromTime, toTime, batch));
    }

}
