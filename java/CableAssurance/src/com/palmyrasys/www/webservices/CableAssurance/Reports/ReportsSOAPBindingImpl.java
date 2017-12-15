/**
 * ReportsSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Reports;

import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;

public class ReportsSOAPBindingImpl implements
		com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsEP {
	public com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT getHfcStatusSummary1(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getHfcStatusSummary1(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT getHfcStatusSummary2(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getHfcStatusSummary2(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT getMtaStatusSummary1(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getMtaStatusSummary1(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT getMtaStatusSummary2(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getMtaStatusSummary2(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmStatusSummary1(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getCmStatusSummary1(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmStatusSummary2(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getCmStatusSummary2(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmTcaStatusSummary1(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getCmTcaStatusSummary1(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT getCmTcaStatusSummary2(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger resId,
			com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT resType,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT fromTime,
			com.palmyrasys.www.webservices.CableAssurance.InputTimeT toTime,
			com.palmyrasys.www.webservices.CableAssurance.ResultBatchT batch,
			com.palmyrasys.www.webservices.CableAssurance.QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		ReportsEP aEP = LocalSystemSingleton.getInstance().getReportsHandler();
		return (aEP.getCmTcaStatusSummary2(topologyKey, resId, resType, fromTime,
				toTime, batch, queryState));
	}

}
