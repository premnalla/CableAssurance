/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Performance;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT;
import com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.CmStatusT;
import com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.GenericCountsT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT;
import com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT;
import com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCommonServiceImpl;
import com.palmyrasyscorp.db.tables.ChannelCountsHistory;
import com.palmyrasyscorp.db.tables.CmCurrentPerformance;
import com.palmyrasyscorp.db.tables.CmPerformanceHistory;
import com.palmyrasyscorp.db.tables.CmStatusHistory;
import com.palmyrasyscorp.db.tables.CmtsCountsHistory;
import com.palmyrasyscorp.db.tables.HfcCountsHistory;
import com.palmyrasyscorp.db.tables.MtaAvailabilityHistory;
import com.palmyrasyscorp.db.tables.MtaPingHistory;
import com.palmyrasyscorp.db.tables.MtaProvisionedHistory;
import com.palmyrasyscorp.www.webservices.helper.ConversionHelper;

/**
 * @author Prem
 *
 */
public abstract class AbstractPerformanceImpl extends AbstractCommonServiceImpl implements PerformanceEP {

	private static Log log = LogFactory.getLog(AbstractPerformanceImpl.class);

	/**
	 * 
	 */
	public GenericCountsHistoryT[] getChannelCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
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

			if (al.size() > 0) {
				GenericCountsHistoryT[] tmp = new GenericCountsHistoryT[al.size()];
				ret = (GenericCountsHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public CmPerformanceHistoryT[] getCmPerformanceHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		CmPerformanceHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
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

			if (al.size() > 0) {
				CmPerformanceHistoryT[] tmp = new CmPerformanceHistoryT[al.size()];
				ret = (CmPerformanceHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmStatusHistoryT[] getCmStatusHistory(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT queryState) throws RemoteException {
		CmStatusHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
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

			if (al.size() > 0) {
				CmStatusHistoryT[] tmp = new CmStatusHistoryT[al.size()];
				ret = (CmStatusHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public GenericCountsHistoryT[] getCmtsCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
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

			if (al.size() > 0) {
				GenericCountsHistoryT[] tmp = new GenericCountsHistoryT[al.size()];
				ret = (GenericCountsHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
			 */
			CmCurrentPerformance ca = new CmCurrentPerformance(new Long(cmResId
					.longValue()));
			ret = new CmCurrentPerformanceT(ca);

			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
			 */
			HfcCountsHistory ca = new HfcCountsHistory(new Long(hfcResId
					.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			
			// System.out.println("arraySize=" + l.size());
			
			for (int i = 0; i < l.size(); i++) {
				HfcCountsHistory dbCnts = (HfcCountsHistory) l.get(i);
				GenericCountsHistoryT svcCnts = new GenericCountsHistoryT(
						dbCnts);
				al.add(svcCnts);
			}
			l.clear();

			if (al.size() > 0) {
				GenericCountsHistoryT[] tmp = new GenericCountsHistoryT[al.size()];
				ret = (GenericCountsHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MtaAvailabilityHistoryT[] getMtaAvailabilityHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		MtaAvailabilityHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getMtaAvailabilityHistory(): cmts resid=" + mtaResId);

			/*
			 * Get Alarms from myself (which is Blade)
			 */
			MtaAvailabilityHistory ca = new MtaAvailabilityHistory(new Long(
					mtaResId.longValue()));
			long fromSec = ConversionHelper.inputTimeToSec(fromTime);
			long toSec = ConversionHelper.inputTimeToSec(toTime);
			List l = ca.queryHistory(fromSec, toSec, batch.getFromIndex(),
					batch.getToIndex());
			
			// System.out.println("arraySize=" + l.size());
			
			for (int i = 0; i < l.size(); i++) {
				MtaAvailabilityHistory dbSt = (MtaAvailabilityHistory) l.get(i);
				MtaAvailabilityHistoryT svcSt = new MtaAvailabilityHistoryT(dbSt);
				al.add(svcSt);
			}
			l.clear();

			if (al.size() > 0) {
				MtaAvailabilityHistoryT[] tmp = new MtaAvailabilityHistoryT[al
						.size()];
				ret = (MtaAvailabilityHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MtaPingStatusHistoryT[] getMtaPingStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		MtaPingStatusHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
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

			if (al.size() > 0) {
				MtaPingStatusHistoryT[] tmp = new MtaPingStatusHistoryT[al.size()];
				ret = (MtaPingStatusHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MtaProvStatusHistoryT[] getMtaProvisionedStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState)
			throws RemoteException {
		MtaProvStatusHistoryT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
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

			if (al.size() > 0) {
				MtaProvStatusHistoryT[] tmp = new MtaProvStatusHistoryT[al.size()];
				ret = (MtaProvStatusHistoryT[]) al.toArray(tmp);
			}

			al.clear(); 
			al = null;
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

}
