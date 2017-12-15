/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Reports;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCommonServiceImpl;
import com.palmyrasys.www.webservices.CableAssurance.Common.CommonServiceUtils;
import com.palmyrasys.www.webservices.CableAssurance.Common.GlobalSystemConfig;
import com.palmyrasys.www.webservices.CableAssurance.Common.MtaStatusSummaryByFreq;
import com.palmyrasys.www.webservices.CableAssurance.Common.CmStatusSummaryByFreq;
import com.palmyrasys.www.webservices.CableAssurance.Common.HfcStatusSummaryByFreq;
import com.palmyrasys.www.webservices.CableAssurance.Common.CmTcaSummaryByFreq;
import com.palmyrasys.www.webservices.CableAssurance.Common.ServiceUtils;
import com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT;
import com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT;
import com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT;
import com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.db.tables.MtaStatusSummary;
import com.palmyrasyscorp.db.tables.HfcStatusSummary;
import com.palmyrasyscorp.db.tables.CmPerfThreshSummary;
import com.palmyrasyscorp.db.tables.CmStatusSummary;
import com.palmyrasyscorp.www.webservices.helper.ReportsHelper;

/**
 * @author Prem
 * 
 */
public abstract class AbstractReportImpl extends AbstractCommonServiceImpl
		implements ReportsEP {

	private static Log log = LogFactory.getLog(AbstractReportImpl.class);

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getMtaSummary1ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					MtaStatusSummary s = new MtaStatusSummary();
					mktL = s.getLongestUnavailableForCmts(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else if (rT.compareTo(ResourceTypeT._HFC) == 0) {
					MtaStatusSummary s = new MtaStatusSummary();
					mktL = s.getLongestUnavailableForHfc(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				MtaStatusSummary s = new MtaStatusSummary();
				mktL = s.getLongestUnavailable(fromDate, toDate, batchSize,
						mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getHfcStatusSummary1ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					HfcStatusSummary s = new HfcStatusSummary();
					mktL = s.getLongestAlarmForCmts(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				HfcStatusSummary s = new HfcStatusSummary();
				mktL = s.getLongestAlarm(fromDate, toDate, batchSize,
						mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getHfcStatusSummary2ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					HfcStatusSummary s = new HfcStatusSummary();
					mktL = s.getHighestAlarmFrequencyForCmts(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				HfcStatusSummary s = new HfcStatusSummary();
				mktL = s.getHighestAlarmFrequency(fromDate, toDate, batchSize,
						mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getCmTcaSummary1ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					CmPerfThreshSummary s = new CmPerfThreshSummary();
					mktL = s.getLongestTcaForCmts(resId.longValue(), fromDate,
							toDate, batchSize, mktStartId);
				} else if (rT.compareTo(ResourceTypeT._HFC) == 0) {
					CmPerfThreshSummary s = new CmPerfThreshSummary();
					mktL = s.getLongestForHfc(resId.longValue(), fromDate,
							toDate, batchSize, mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				CmPerfThreshSummary s = new CmPerfThreshSummary();
				mktL = s.getLongestTca(fromDate, toDate, batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getCmStatusSummary1ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					CmStatusSummary s = new CmStatusSummary();
					mktL = s.getLongestOfflineForCmts(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else if (rT.compareTo(ResourceTypeT._HFC) == 0) {
					CmStatusSummary s = new CmStatusSummary();
					mktL = s.getLongestOfflineForHfc(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				CmStatusSummary s = new CmStatusSummary();
				mktL = s.getLongestOffline(fromDate, toDate, batchSize,
						mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getCmStatusSummary2ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					CmStatusSummary s = new CmStatusSummary();
					mktL = s.getHighestOfflineFrequencyForCmts(resId
							.longValue(), fromDate, toDate, batchSize,
							mktStartId);
				} else if (rT.compareTo(ResourceTypeT._HFC) == 0) {
					CmStatusSummary s = new CmStatusSummary();
					mktL = s.getHighestOfflineFrequencyForHfc(
							resId.longValue(), fromDate, toDate, batchSize,
							mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				CmStatusSummary s = new CmStatusSummary();
				mktL = s.getHighestOfflineFrequency(fromDate, toDate,
						batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getCmTcaSummary2ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					CmPerfThreshSummary s = new CmPerfThreshSummary();
					mktL = s.getHighestTcaFrequencyForCmts(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else if (rT.compareTo(ResourceTypeT._HFC) == 0) {
					CmPerfThreshSummary s = new CmPerfThreshSummary();
					mktL = s.getHighestTcaFrequencyForHfc(resId.longValue(),
							fromDate, toDate, batchSize, mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				CmPerfThreshSummary s = new CmPerfThreshSummary();
				mktL = s.getHighestTcaFrequency(fromDate, toDate, batchSize,
						mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param resId
	 * @param resType
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getMtaSummary2ForMkt(BigInteger resId,
			ResourceTypeT resType, Calendar fromDate, Calendar toDate,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
			if (resId != null && resType != null) {
				String rT = resType.toString();
				if (rT.compareTo(ResourceTypeT._CMTS) == 0) {
					MtaStatusSummary s = new MtaStatusSummary();
					mktL = s.getHighestUnavailFrequencyForCmts(resId
							.longValue(), fromDate, toDate, batchSize,
							mktStartId);
				} else if (rT.compareTo(ResourceTypeT._HFC) == 0) {
					MtaStatusSummary s = new MtaStatusSummary();
					mktL = s.getHighestUnavailFrequencyForHfc(
							resId.longValue(), fromDate, toDate, batchSize,
							mktStartId);
				} else {
					// Error
				}
			} else {
				// System.out.println("batchSize=" + batchSize);
				// System.out.println("mktStartId=" + mktStartId);
				MtaStatusSummary s = new MtaStatusSummary();
				mktL = s.getHighestUnavailFrequency(fromDate, toDate,
						batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
			}
		}

		return (mktL);
	}

	/**
	 * 
	 * @param retQs
	 * @param al
	 * @return
	 */
	protected MtaStatusSummaryRespT createMtaStatusSummaryResponse(
			QueryStateT[] retQs, ArrayList al) {
		MtaStatusSummaryRespT ret = null;

		if (al.size() > 0) {
			ret = new MtaStatusSummaryRespT();
			ret.setQueryState(retQs);
			MtaStatusSummaryT[] tmp = new MtaStatusSummaryT[al.size()];
			ret.setMtaData((MtaStatusSummaryT[]) al.toArray(tmp));
		}

		return (ret);
	}

	/**
	 * 
	 * @param retQs
	 * @param al
	 * @return
	 */
	protected HfcStatusSummaryRespT createHfcStatusSummaryResponse(
			QueryStateT[] retQs, ArrayList al) {
		HfcStatusSummaryRespT ret = null;

		if (al.size() > 0) {
			ret = new HfcStatusSummaryRespT();
			ret.setQueryState(retQs);
			HfcStatusSummaryT[] tmp = new HfcStatusSummaryT[al.size()];
			ret.setHfcData((HfcStatusSummaryT[]) al.toArray(tmp));
		}

		return (ret);
	}

	/**
	 * 
	 * @param retQs
	 * @param al
	 * @return
	 */
	protected CmStatusSummaryRespT createCmStatusSummaryResponse(
			QueryStateT[] retQs, ArrayList al) {
		CmStatusSummaryRespT ret = null;

		if (al.size() > 0) {
			ret = new CmStatusSummaryRespT();
			ret.setQueryState(retQs);
			CmStatusSummaryT[] tmp = new CmStatusSummaryT[al.size()];
			ret.setCmData((CmStatusSummaryT[]) al.toArray(tmp));
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddMtaSummary1DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			MtaStatusSummaryRespT rslt = rh.getMtaStatusSummary1(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				MtaStatusSummaryT[] rslts = rslt.getMtaData();
				CommonServiceUtils.AddToTreeDescending(tree, rslts, batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddHfcStatusSummary1DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			HfcStatusSummaryRespT rslt = rh.getHfcStatusSummary1(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				HfcStatusSummaryT[] rslts = rslt.getHfcData();
				CommonServiceUtils.AddToTreeDescending(tree, rslts, batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddCmTcaSummary1DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			CmStatusSummaryRespT rslt = rh.getCmTcaStatusSummary1(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				CmStatusSummaryT[] rslts = rslt.getCmData();
				CommonServiceUtils.AddToTreeDescending(tree, rslts, batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddCmStatusSummary1DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			CmStatusSummaryRespT rslt = rh.getCmStatusSummary1(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				CmStatusSummaryT[] rslts = rslt.getCmData();
				CommonServiceUtils.AddToTreeDescending(tree, rslts, batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddCmStatusSummary2DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			CmStatusSummaryRespT rslt = rh.getCmStatusSummary2(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				CmStatusSummaryT[] rslts = rslt.getCmData();
				CmStatusSummaryByFreq[] specificRslts = createCmStatusSummFreq(rslts);
				CommonServiceUtils.AddToTreeDescending(tree, specificRslts,
						batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddCmTcaSummary2DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			CmStatusSummaryRespT rslt = rh.getCmTcaStatusSummary2(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				CmStatusSummaryT[] rslts = rslt.getCmData();
				CmTcaSummaryByFreq[] specificRslts = createCmTcaSummFreq(rslts);
				CommonServiceUtils.AddToTreeDescending(tree, specificRslts,
						batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddMtaSummary2DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			MtaStatusSummaryRespT rslt = rh.getMtaStatusSummary2(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				MtaStatusSummaryT[] rslts = rslt.getMtaData();
				MtaStatusSummaryByFreq[] specificRslts = createMtaStatusSummFreq(rslts);
				CommonServiceUtils.AddToTreeDescending(tree, specificRslts,
						batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param resType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getAndAddHfcStatusSummary2DataFromBlades(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i = 0; i < blades.size(); i++) {
			Blade b = (Blade) blades.get(i);

			/*
			 * Create & enQ Runnable NOTE: this scheme is not implemented for
			 * now.
			 */

			/*
			 * Query State setup before getting data from Blade(s)
			 */
			QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
					queryState, b.getId());

			/*
			 * Get Data from Blade(s) and add to Tree
			 */
			ReportsHelper rh = new ReportsHelper(b.getHostForWS(gSysCfg
					.getWebServicesPort()));
			HfcStatusSummaryRespT rslt = rh.getHfcStatusSummary2(topologyKey,
					resId, resType, fromTime, toTime, batch, qsToBlade);
			if (rslt != null) {
				HfcStatusSummaryT[] rslts = rslt.getHfcData();
				HfcStatusSummaryByFreq[] specificRslts = createHfcStatusSummFreq(rslts);
				CommonServiceUtils.AddToTreeDescending(tree, specificRslts,
						batchSize);
			}

		} // foreach blade

	}

	/**
	 * 
	 * @param rslts
	 * @return
	 */
	private MtaStatusSummaryByFreq[] createMtaStatusSummFreq(
			MtaStatusSummaryT[] rslts) {
		MtaStatusSummaryByFreq[] ret = null;

		if (rslts != null) {
			ret = new MtaStatusSummaryByFreq[rslts.length];
			for (int i = 0; i < rslts.length; i++) {
				ret[i] = new MtaStatusSummaryByFreq(rslts[i]);
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param rslts
	 * @return
	 */
	private HfcStatusSummaryByFreq[] createHfcStatusSummFreq(
			HfcStatusSummaryT[] rslts) {
		HfcStatusSummaryByFreq[] ret = null;

		if (rslts != null) {
			ret = new HfcStatusSummaryByFreq[rslts.length];
			for (int i = 0; i < rslts.length; i++) {
				ret[i] = new HfcStatusSummaryByFreq(rslts[i]);
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param rslts
	 * @return
	 */
	private CmStatusSummaryByFreq[] createCmStatusSummFreq(
			CmStatusSummaryT[] rslts) {
		CmStatusSummaryByFreq[] ret = null;

		if (rslts != null) {
			ret = new CmStatusSummaryByFreq[rslts.length];
			for (int i = 0; i < rslts.length; i++) {
				ret[i] = new CmStatusSummaryByFreq(rslts[i]);
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param rslts
	 * @return
	 */
	private CmTcaSummaryByFreq[] createCmTcaSummFreq(CmStatusSummaryT[] rslts) {
		CmTcaSummaryByFreq[] ret = null;

		if (rslts != null) {
			ret = new CmTcaSummaryByFreq[rslts.length];
			for (int i = 0; i < rslts.length; i++) {
				ret[i] = new CmTcaSummaryByFreq(rslts[i]);
			}
		}

		return (ret);
	}

}
