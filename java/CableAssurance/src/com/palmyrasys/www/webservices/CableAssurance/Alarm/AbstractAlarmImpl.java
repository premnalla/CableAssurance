/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Alarm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.AlarmHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.AlarmIdT;
import com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT;
import com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT;
import com.palmyrasys.www.webservices.CableAssurance.EventCategoryT;
import com.palmyrasys.www.webservices.CableAssurance.EventMessageT;
import com.palmyrasys.www.webservices.CableAssurance.EventTypeT;
import com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT;
import com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCommonServiceImpl;
import com.palmyrasys.www.webservices.CableAssurance.Common.CommonServiceUtils;
import com.palmyrasys.www.webservices.CableAssurance.Common.GlobalSystemConfig;
import com.palmyrasys.www.webservices.CableAssurance.Common.ServiceUtils;
import com.palmyrasyscorp.db.tables.AlarmHistory;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.db.tables.CurrentAlarm;
import com.palmyrasyscorp.db.tables.HistoricalAlarm;
import com.palmyrasyscorp.www.webservices.helper.AlarmHelper;
import com.palmyrasyscorp.www.webservices.helper.CPeerHelper;
import com.palmyrasyscorp.www.webservices.helper.ConversionHelper;

/**
 * @author Prem
 * 
 */
public abstract class AbstractAlarmImpl extends AbstractCommonServiceImpl
		implements AlarmEP {

	private static Log log = LogFactory.getLog(AbstractAlarmImpl.class);

	/**
	 * 
	 */
	public AlarmHistoryT[] getAlarmHistory(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		AlarmHistoryT[] ret = null;
		
		try {
			// AlarmHistoryT ah = new AlarmHistoryT();
			List l = AlarmHistory.GetHistories(alarmId);
			
			ArrayList al = new ArrayList();
			
			for (int i=0; i<l.size(); i++) {
				AlarmHistory ah = (AlarmHistory) l.get(i);
				AlarmHistoryT svcAh = new AlarmHistoryT(ah);
				if (svcAh.isValid()) {
					al.add(svcAh);
				}
			}
			
			if (al.size() > 0) {
				AlarmHistoryT[] tmp = new AlarmHistoryT[al.size()];
				ret = (AlarmHistoryT[]) al.toArray(tmp);
			}

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CurrentAlarmT getCurrentAlarm(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		CurrentAlarmT ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
			 */
			CurrentAlarm ca = new CurrentAlarm(alarmId);
			ret = new CurrentAlarmT(ca);
			ret.getAbstractAlarm().setTopologyKey(topologyKey);
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

//	/**
//	 * 
//	 */
//	public CurrentAlarmDetailsT getCurrentAlarmDetails(
//			TopoHierarchyKeyT topologyKey, BigInteger alarmId)
//			throws java.rmi.RemoteException {
//		CurrentAlarmDetailsT ret = null;
//
//		try {
//			// System.out.println("getChannels(): cmts resid=" + cmtsResId);
//
//			/*
//			 * Get Alarms from myself (which is Blade)
//			 */
//			CurrentAlarm ca = new CurrentAlarm(alarmId);
//			ret = new CurrentAlarmDetailsT(ca);
//			ca = null;
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);
//		}
//
//		return (ret);
//	}

	/**
	 * 
	 */
	public HistoricalAlarmT getHistoricalAlarm(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		HistoricalAlarmT ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Alarms from myself (which is Blade)
			 */
			HistoricalAlarm ca = new HistoricalAlarm(alarmId);
			ret = new HistoricalAlarmT(ca);
			ca = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

//	/**
//	 * 
//	 */
//	public HistoricalAlarmDetailsT getHistoricalAlarmDetails(
//			TopoHierarchyKeyT topologyKey, BigInteger alarmId)
//			throws java.rmi.RemoteException {
//		HistoricalAlarmDetailsT ret = null;
//
//		try {
//			// System.out.println("getChannels(): cmts resid=" + cmtsResId);
//
//			/*
//			 * Get Alarms from myself (which is Blade)
//			 */
//			HistoricalAlarm ca = new HistoricalAlarm(alarmId);
//			ret = new HistoricalAlarmDetailsT(ca);
//			ca = null;
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);
//		}
//
//		return (ret);
//	}

	/**
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getCurrentAlarmsForMkt(InputTimeT fromTime,
			InputTimeT toTime, short batchSize, long mktStartId,
			GlobalSystemConfig gSysCfg) {
		List mktL = null;

		try {
			/*
			 * if gSysCfg == null => implies Blade
			 */
			if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
				long fromSec = ConversionHelper.inputTimeToSec(fromTime);
				long toSec = ConversionHelper.inputTimeToSec(toTime);
				CurrentAlarm ca = new CurrentAlarm();
				mktL = ca.getAlarms(fromSec, toSec, batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
				ca = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (mktL);
	}

	/**
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getHistoricalAlarmsForMkt(InputTimeT fromTime,
			InputTimeT toTime, short batchSize, long mktStartId,
			GlobalSystemConfig gSysCfg) {
		List mktL = null;

		try {
			/*
			 * if gSysCfg == null => implies Blade
			 */
			if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
				long fromSec = ConversionHelper.inputTimeToSec(fromTime);
				long toSec = ConversionHelper.inputTimeToSec(toTime);
				HistoricalAlarm ca = new HistoricalAlarm();
				mktL = ca.getAlarms(fromSec, toSec, batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
				ca = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (mktL);
	}

	/**
	 * 
	 * @param alarmType
	 * @param alarmSubType
	 * @param fromTime
	 * @param toTime
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getCurrentAlarmsForMkt(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		try {
			/*
			 * if gSysCfg == null => implies Blade
			 */
			if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
				long fromSec = ConversionHelper.inputTimeToSec(fromTime);
				long toSec = ConversionHelper.inputTimeToSec(toTime);
				CurrentAlarm ca = new CurrentAlarm();
				mktL = ca.getAlarmsByType(alarmType, alarmSubType, fromSec,
						toSec, batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
				ca = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (mktL);
	}

	/**
	 * 
	 * @param alarmType
	 * @param alarmSubType
	 * @param fromTime
	 * @param toTime
	 * @param batchSize
	 * @param mktStartId
	 * @param gSysCfg
	 * @return
	 */
	protected List getHistoricalAlarmsForMkt(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			short batchSize, long mktStartId, GlobalSystemConfig gSysCfg) {
		List mktL = null;

		try {
			/*
			 * if gSysCfg == null => implies Blade
			 */
			if (gSysCfg == null || gSysCfg.doesMarketHaveCmts()) {
				long fromSec = ConversionHelper.inputTimeToSec(fromTime);
				long toSec = ConversionHelper.inputTimeToSec(toTime);
				HistoricalAlarm ca = new HistoricalAlarm();
				mktL = ca.getAlarmsByType(alarmType, alarmSubType, fromSec,
						toSec, batchSize, mktStartId);
				// System.out.println("MktList.size=" + mktL.size());
				ca = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (mktL);
	}

	/**
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getCurrentAlarmsFromBlades(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState,
			List blades, TreeMap tree, short batchSize) {

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);

				/*
				 * Create & enQ Runnable NOTE: this scheme is not implemented
				 * for now.
				 */

				/*
				 * Query State setup before getting data from Blade(s)
				 */
				QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
						queryState, b.getId());

				/*
				 * Get Data from Blade(s) and add to Tree
				 */
				AlarmHelper rh = new AlarmHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				CurrentAlarmsRespT rslt = rh.getCurrentAlarms(fromTime, toTime,
						batch, qsToBlade);
				if (rslt != null) {
					BigInteger bldId = new BigInteger(b.getId().toString());
					CurrentAlarmT[] rslts = rslt.getAlarms();
					if (rslts != null) {
						for (int j = 0; j < rslts.length; j++) {
							rslts[j].getAbstractAlarm().getTopologyKey()
									.setBladeId(bldId);
						}
					}
					CommonServiceUtils.AddToTreeDescending(tree, rslts,
							batchSize);
				}

			} // foreach blade
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

	}

	/**
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getHistoricalAlarmsFromBlades(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState,
			List blades, TreeMap tree, short batchSize) {

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);

				/*
				 * Create & enQ Runnable NOTE: this scheme is not implemented
				 * for now.
				 */

				/*
				 * Query State setup before getting data from Blade(s)
				 */
				QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
						queryState, b.getId());

				/*
				 * Get Data from Blade(s) and add to Tree
				 */
				AlarmHelper rh = new AlarmHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				HistoricalAlarmsRespT rslt = rh.getHistoricalAlarms(fromTime,
						toTime, batch, qsToBlade);
				if (rslt != null) {
					BigInteger bldId = new BigInteger(b.getId().toString());
					HistoricalAlarmT[] rslts = rslt.getAlarms();
					for (int j = 0; j < rslts.length; j++) {
						rslts[j].getAbstractAlarm().getTopologyKey()
								.setBladeId(bldId);
					}
					CommonServiceUtils.AddToTreeDescending(tree, rslts,
							batchSize);
				}

			} // foreach blade
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

	}

	/**
	 * 
	 * @param alarmType
	 * @param alarmSubType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getCurrentAlarmsFromBlades(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);

				/*
				 * Create & enQ Runnable NOTE: this scheme is not implemented
				 * for now.
				 */

				/*
				 * Query State setup before getting data from Blade(s)
				 */
				QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
						queryState, b.getId());

				/*
				 * Get Data from Blade(s) and add to Tree
				 */
				AlarmHelper rh = new AlarmHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				CurrentAlarmsRespT rslt = rh.getCurrentAlarmsByType(alarmType,
						alarmSubType, fromTime, toTime, batch, qsToBlade);
				if (rslt != null) {
					BigInteger bldId = new BigInteger(b.getId().toString());
					CurrentAlarmT[] rslts = rslt.getAlarms();
					for (int j = 0; j < rslts.length; j++) {
						rslts[j].getAbstractAlarm().getTopologyKey()
								.setBladeId(bldId);
					}
					CommonServiceUtils.AddToTreeDescending(tree, rslts,
							batchSize);
				}

			} // foreach blade
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

	}

	/**
	 * 
	 * @param alarmType
	 * @param alarmSubType
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @param blades
	 * @param tree
	 * @param batchSize
	 */
	protected void getHistoricalAlarmsFromBlades(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState, List blades,
			TreeMap tree, short batchSize) {

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);

				/*
				 * Create & enQ Runnable NOTE: this scheme is not implemented
				 * for now.
				 */

				/*
				 * Query State setup before getting data from Blade(s)
				 */
				QueryStateT[] qsToBlade = ServiceUtils.SetupQueryStateForBlade(
						queryState, b.getId());

				/*
				 * Get Data from Blade(s) and add to Tree
				 */
				AlarmHelper rh = new AlarmHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				HistoricalAlarmsRespT rslt = rh.getHistoricalAlarmsByType(
						alarmType, alarmSubType, fromTime, toTime, batch,
						qsToBlade);
				if (rslt != null) {
					BigInteger bldId = new BigInteger(b.getId().toString());
					HistoricalAlarmT[] rslts = rslt.getAlarms();
					for (int j = 0; j < rslts.length; j++) {
						rslts[j].getAbstractAlarm().getTopologyKey()
								.setBladeId(bldId);
					}
					CommonServiceUtils.AddToTreeDescending(tree, rslts,
							batchSize);
				}

			} // foreach blade
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

	}

	/**
	 * 
	 * @param retQs
	 * @param al
	 * @return
	 */
	protected CurrentAlarmsRespT createCurrentAlarmsResponse(
			QueryStateT[] retQs, ArrayList al) {
		CurrentAlarmsRespT ret = null;

		try {
			if (al.size() > 0) {
				ret = new CurrentAlarmsRespT();
				ret.setQueryState(retQs);
				CurrentAlarmT[] tmp = new CurrentAlarmT[al.size()];
				ret.setAlarms((CurrentAlarmT[]) al.toArray(tmp));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param retQs
	 * @param al
	 * @return
	 */
	protected HistoricalAlarmsRespT createHistoricalAlarmsResponse(
			QueryStateT[] retQs, ArrayList al) {
		HistoricalAlarmsRespT ret = null;

		if (al.size() > 0) {
			ret = new HistoricalAlarmsRespT();
			ret.setQueryState(retQs);
			HistoricalAlarmT[] tmp = new HistoricalAlarmT[al.size()];
			ret.setAlarms((HistoricalAlarmT[]) al.toArray(tmp));
		}

		return (ret);
	}

	/**
	 * 
	 * @param tree
	 */
	protected void dumpCurrentAlarmTree(TreeMap tree) {
		Collection coln = tree.values();
		Iterator iter = coln.iterator();
		System.out.println("Begin - current alarms {");
		while (iter.hasNext()) {
			CurrentAlarmT ca = (CurrentAlarmT) iter.next();
			System.out.println(ca.toString());
		}
		System.out.println("} End - current alarms;");

		// AbstractCAServiceObject[] tmp = new
		// AbstractCAServiceObject[coln.size()];
		// AbstractCAServiceObject[] array = (AbstractCAServiceObject[])
		// coln.toArray(tmp);
		// int i = array.length - 1;
		// int c = 0;
		// for (; c < batchSize && i>=0; i--, c++) {
		// al.add(array[i]);
		// }
	}

	/**
	 * 
	 */
	public short clearAlarms(AlarmIdT[] alarm) throws java.rmi.RemoteException {
		short ret = 0;

		if (alarm == null) {
			return (ret);
		}

		for (int i = 0; i < alarm.length; i++) {

			try {
				CurrentAlarm ca = new CurrentAlarm(alarm[i].getAlarmId());
				if (ca.clearAlarm()) {
					EventMessageT e = new EventMessageT(EventTypeT.Clear,
							EventCategoryT.Alarm, null, ca.getResourceId()
									.toString());
					CPeerHelper ch = new CPeerHelper();
					ch.sendEvent(e);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error(null, e);
				ret = -1;
			}

		}

		return (ret);
	}

	/**
	 * 
	 */
	public CurrentAlarmsRespT getCurrentAlarmsForResource(
			TopoHierarchyKeyT topoKey, BigInteger resourceId) {
		CurrentAlarmsRespT ret = null;

		try {
			CurrentAlarmsRespT tmp = new CurrentAlarmsRespT();
			CurrentAlarm ca = new CurrentAlarm();
			List l = ca.getAlarmsForResource(resourceId);
			CurrentAlarmT[] a = new CurrentAlarmT[l.size()];
			tmp.setAlarms(a);
			QueryStateT[] qa = new QueryStateT[0];
			tmp.setQueryState(qa);
			// byte[] b = {0};
			// BigInteger bi = new BigInteger(b);
			// TopoHierarchyKeyT tk = new TopoHierarchyKeyT(bi, bi, bi);
			for (int i = 0; i < l.size(); i++) {
				a[i] = new CurrentAlarmT((CurrentAlarm) l.get(i));
				a[i].getAbstractAlarm().setTopologyKey(topoKey);
			}
			ret = tmp;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public HistoricalAlarmsRespT getHistoricalAlarmsForResource(
			TopoHierarchyKeyT topoKey, BigInteger resourceId) {
		HistoricalAlarmsRespT ret = null;

		try {
			HistoricalAlarmsRespT tmp = new HistoricalAlarmsRespT();
			HistoricalAlarm ca = new HistoricalAlarm();
			List l = ca.getAlarmsForResource(resourceId);
			HistoricalAlarmT[] a = new HistoricalAlarmT[l.size()];
			tmp.setAlarms(a);
			QueryStateT[] qa = new QueryStateT[0];
			tmp.setQueryState(qa);
			for (int i = 0; i < l.size(); i++) {
				a[i] = new HistoricalAlarmT((HistoricalAlarm) l.get(i));
				a[i].getAbstractAlarm().setTopologyKey(topoKey);
			}
			ret = tmp;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

}
