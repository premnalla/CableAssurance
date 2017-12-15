/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Performance;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.*;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasyscorp.db.tables.*;
import com.palmyrasyscorp.www.webservices.helper.ConversionHelper;

/**
 * @author Prem
 * 
 */
public class MarketPerformanceImpl extends AbstractPerformanceImpl implements
		PerformanceEP {

	/**
	 * 
	 */
	public GenericCountsHistoryT[] getChannelCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			ChannelCountsHistory ca = new ChannelCountsHistory(new Long(
					channelResId.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				ChannelCountsHistory dbCnts = (ChannelCountsHistory) l.get(i);
				GenericCountsHistoryT svcCnts = new GenericCountsHistoryT(
						dbCnts);
				al.add(svcCnts);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			GenericCountsHistoryT[] tmp = new GenericCountsHistoryT[al.size()];
			ret = (GenericCountsHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmPerformanceHistoryT[] getCmPerformanceHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		CmPerformanceHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			CmPerformanceHistory ca = new CmPerformanceHistory(new Long(cmResId
					.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				CmPerformanceHistory dbPerf = (CmPerformanceHistory) l.get(i);
				CmPerformanceHistoryT svcPerf = new CmPerformanceHistoryT(
						dbPerf);
				al.add(svcPerf);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			CmPerformanceHistoryT[] tmp = new CmPerformanceHistoryT[al.size()];
			ret = (CmPerformanceHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmStatusHistoryT[] getCmStatusHistory(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch) throws RemoteException {
		CmStatusHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			CmStatusHistory ca = new CmStatusHistory(new Long(cmResId
					.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				CmStatusHistory dbSt = (CmStatusHistory) l.get(i);
				CmStatusHistoryT svcSt = new CmStatusHistoryT(dbSt);
				al.add(svcSt);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			CmStatusHistoryT[] tmp = new CmStatusHistoryT[al.size()];
			ret = (CmStatusHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public GenericCountsHistoryT[] getCmtsCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			CmtsCountsHistory ca = new CmtsCountsHistory(new Long(cmtsResId
					.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				CmtsCountsHistory dbCnts = (CmtsCountsHistory) l.get(i);
				GenericCountsHistoryT svcCnts = new GenericCountsHistoryT(
						dbCnts);
				al.add(svcCnts);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			GenericCountsHistoryT[] tmp = new GenericCountsHistoryT[al.size()];
			ret = (GenericCountsHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public GenericCountsT getCurrentChannelCounts(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public CmCurrentPerformanceT getCurrentCmPerformance(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId)
			throws RemoteException {
		CmCurrentPerformanceT ret = null;

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			CmCurrentPerformance ca = new CmCurrentPerformance(new Long(cmResId
					.longValue()));
			ret = new CmCurrentPerformanceT(ca);

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmStatusT getCurrentCmStatus(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public GenericCountsT getCurrentCmtsCounts(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public GenericCountsT getCurrentHfcCounts(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public MtaAvailabilityT getCurrentMtaAvailability(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public MtaPingStatusT getCurrentMtaPingStatus(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public MtaProvStatusT getCurrentMtaProvisionedStatus(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public GenericCountsHistoryT[] getHfcCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger hfcResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			HfcCountsHistory ca = new HfcCountsHistory(new Long(hfcResId
					.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				HfcCountsHistory dbCnts = (HfcCountsHistory) l.get(i);
				GenericCountsHistoryT svcCnts = new GenericCountsHistoryT(
						dbCnts);
				al.add(svcCnts);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			GenericCountsHistoryT[] tmp = new GenericCountsHistoryT[al.size()];
			ret = (GenericCountsHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MtaAvailabilityHistoryT[] getMtaAvailabilityHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		MtaAvailabilityHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			MtaAvailabilityHistory ca = new MtaAvailabilityHistory(new Long(
					mtaResId.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				MtaAvailabilityHistory dbSt = (MtaAvailabilityHistory) l.get(i);
				MtaAvailabilityT svcSt = new MtaAvailabilityT(dbSt);
				al.add(svcSt);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			MtaAvailabilityHistoryT[] tmp = new MtaAvailabilityHistoryT[al
					.size()];
			ret = (MtaAvailabilityHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MtaPingStatusHistoryT[] getMtaPingStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		MtaPingStatusHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			MtaPingHistory ca = new MtaPingHistory(new Long(mtaResId
					.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				MtaPingHistory dbSt = (MtaPingHistory) l.get(i);
				MtaPingStatusHistoryT svcSt = new MtaPingStatusHistoryT(dbSt);
				al.add(svcSt);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			MtaPingStatusHistoryT[] tmp = new MtaPingStatusHistoryT[al.size()];
			ret = (MtaPingStatusHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MtaProvStatusHistoryT[] getMtaProvisionedStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch)
			throws RemoteException {
		MtaProvStatusHistoryT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			MtaProvisionedHistory ca = new MtaProvisionedHistory(new Long(
					mtaResId.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			for (int i = 0; i < l.size(); i++) {
				MtaProvisionedHistory dbSt = (MtaProvisionedHistory) l.get(i);
				MtaProvStatusHistoryT svcSt = new MtaProvStatusHistoryT(dbSt);
				al.add(svcSt);
			}
			l.clear();

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		if (al.size() > 0) {
			MtaProvStatusHistoryT[] tmp = new MtaProvStatusHistoryT[al.size()];
			ret = (MtaProvStatusHistoryT[]) al.toArray(tmp);
		}

		return (ret);
	}

}
