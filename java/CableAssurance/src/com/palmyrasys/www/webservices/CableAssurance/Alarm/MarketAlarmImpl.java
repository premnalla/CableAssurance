/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasys.www.webservices.CableAssurance.Common.CommonServiceUtils;
import com.palmyrasys.www.webservices.CableAssurance.Common.GlobalSystemConfig;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.www.webservices.helper.AlarmHelper;

/**
 * @author Prem
 * 
 */
public class MarketAlarmImpl extends AbstractAlarmImpl {

	private static Log log = LogFactory.getLog(MarketAlarmImpl.class);

	/**
	 * 
	 * 
	 */
	public MarketAlarmImpl() {

	}

	/**
	 * 
	 */
	public AlarmHistoryT[] getAlarmHistory(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		AlarmHistoryT[] ret = null;
		
		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		try {
			int bldId;
			
			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Clear result from myself (which is Market)
				 */
				ret = super.getAlarmHistory(topologyKey, alarmId);
			} else {
				/*
				 * Clear result from Blade
				 */
				Blade bld = new Blade(bldId);
				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.getAlarmHistory(topologyKey, alarmId);
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
	public CurrentAlarmsRespT getCurrentAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		CurrentAlarmsRespT ret = null;

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;
			short batchSize = batch.getBatchSize();
			long mktStartId = getMarketStartId(queryState, batch, gSysCfg);

			// System.out.println("StartId=" + mktStartId);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */

			mktL = getCurrentAlarmsForMkt(fromTime, toTime, batchSize, mktStartId,
					gSysCfg);
			CommonServiceUtils.AddCurrentAlarmsToTree(tree, mktL, batchSize);

			/*
			 * Debug Only
			 */
			// dumpCurrentAlarmTree(tree);
			/*
			 * Get all Blades
			 */

			List blades = Blade.QueryBlades();

			/*
			 * Get Data from Blades & Add them to the Tree
			 */

			getCurrentAlarmsFromBlades(fromTime, toTime, batch, queryState, blades,
					tree, batchSize);

			/*
			 * Debug Only
			 */
			// dumpCurrentAlarmTree(tree);
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */

			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());

			/*
			 * Setup return QueryState (to use in next pass of get)
			 */

			QueryStateT[] retQs = setupReturnQueryStateForMkt(queryState, batch,
					null, mktL, blades, al);

			/*
			 * Debug Only
			 */
			// dumpQueryStates(retQs);
			/*
			 * Create return data
			 */

			ret = createCurrentAlarmsResponse(retQs, al);

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			blades.clear();
			blades = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
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

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				super.getCurrentAlarm(topologyKey, alarmId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.getCurrentAlarm(topologyKey, alarmId);
				ah = null;
				bld = null;
			}
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
//			int bldId;
//
//			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
//				/*
//				 * Get Alarms from myself (which is Market)
//				 */
//				ret = super.getCurrentAlarmDetails(topologyKey, alarmId);
//			} else {
//				/*
//				 * Get Alarms from Blade
//				 */
//				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();
//
//				Blade bld = new Blade(bldId);
//				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
//						.getWebServicesPort()));
//				ret = ah.getCurrentAlarmDetails(topologyKey, alarmId);
//				ah = null;
//				bld = null;
//			}
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
	public CurrentAlarmsRespT getCurrentAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState) {
		CurrentAlarmsRespT ret = null;

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;
			short batchSize = batch.getBatchSize();
			long mktStartId = getMarketStartId(queryState, batch, gSysCfg);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */

			mktL = getCurrentAlarmsForMkt(alarmType, alarmSubType, fromTime,
					toTime, batchSize, mktStartId, gSysCfg);
			CommonServiceUtils.AddCurrentAlarmsToTree(tree, mktL, batchSize);

			/*
			 * Get all Blades
			 */

			List blades = Blade.QueryBlades();

			/*
			 * Get Data from Blades & Add them to the Tree
			 */

			getCurrentAlarmsFromBlades(alarmType, alarmSubType, fromTime, toTime,
					batch, queryState, blades, tree, batchSize);

			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */

			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());

			/*
			 * Setup return QueryState (to use in next pass of get)
			 */

			QueryStateT[] retQs = setupReturnQueryStateForMkt(queryState, batch,
					null, mktL, blades, al);

			/*
			 * Create return data
			 */

			ret = createCurrentAlarmsResponse(retQs, al);

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			blades.clear();
			blades = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
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
	public HistoricalAlarmsRespT getHistoricalAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		HistoricalAlarmsRespT ret = null;

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;
			short batchSize = batch.getBatchSize();
			long mktStartId = getMarketStartId(queryState, batch, gSysCfg);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */

			mktL = getHistoricalAlarmsForMkt(fromTime, toTime, batchSize,
					mktStartId, gSysCfg);
			CommonServiceUtils.AddHistoricalAlarmsToTree(tree, mktL, batchSize);

			/*
			 * Get all Blades
			 */

			List blades = Blade.QueryBlades();

			/*
			 * Get Data from Blades & Add them to the Tree
			 */

			getHistoricalAlarmsFromBlades(fromTime, toTime, batch, queryState,
					blades, tree, batchSize);

			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */

			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());

			/*
			 * Setup return QueryState (to use in next pass of get)
			 */

			QueryStateT[] retQs = setupReturnQueryStateForMkt(queryState, batch,
					null, mktL, blades, al);

			/*
			 * Create return data
			 */

			ret = createHistoricalAlarmsResponse(retQs, al);

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			blades.clear();
			blades = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	public HistoricalAlarmT getHistoricalAlarm(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		HistoricalAlarmT ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getHistoricalAlarm(topologyKey, alarmId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.getHistoricalAlarm(topologyKey, alarmId);
				bld = null;
				ah = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

//	public HistoricalAlarmDetailsT getHistoricalAlarmDetails(
//			TopoHierarchyKeyT topologyKey, BigInteger alarmId)
//			throws java.rmi.RemoteException {
//		HistoricalAlarmDetailsT ret = null;
//
//		try {
//			// System.out.println("getChannels(): cmts resid=" + cmtsResId);
//
//			int bldId;
//
//			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
//				/*
//				 * Get Alarms from myself (which is Market)
//				 */
//				ret = super.getHistoricalAlarmDetails(topologyKey, alarmId);
//			} else {
//				/*
//				 * Get Alarms from Blade
//				 */
//				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();
//
//				Blade bld = new Blade(bldId);
//				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
//						.getWebServicesPort()));
//				ret = ah.getHistoricalAlarmDetails(topologyKey, alarmId);
//				ah = null;
//				bld = null;
//			}
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		}
//
//		return (ret);
//	}

	public HistoricalAlarmsRespT getHistoricalAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState) {
		HistoricalAlarmsRespT ret = null;

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;
			short batchSize = batch.getBatchSize();
			long mktStartId = getMarketStartId(queryState, batch, gSysCfg);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */

			mktL = getHistoricalAlarmsForMkt(alarmType, alarmSubType, fromTime,
					toTime, batchSize, mktStartId, gSysCfg);
			CommonServiceUtils.AddHistoricalAlarmsToTree(tree, mktL, batchSize);

			/*
			 * Get all Blades
			 */

			List blades = Blade.QueryBlades();

			/*
			 * Get Data from Blades & Add them to the Tree
			 */

			getHistoricalAlarmsFromBlades(alarmType, alarmSubType, fromTime,
					toTime, batch, queryState, blades, tree, batchSize);

			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */

			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());

			/*
			 * Setup return QueryState (to use in next pass of get)
			 */

			QueryStateT[] retQs = setupReturnQueryStateForMkt(queryState, batch,
					null, mktL, blades, al);

			/*
			 * Create return data
			 */

			ret = createHistoricalAlarmsResponse(retQs, al);

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			blades.clear();
			blades = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
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
	public short clearAlarms(AlarmIdT[] alarm)
			throws java.rmi.RemoteException {
		short ret = 0;
		
		if (alarm == null) {
			return (ret);
		}
		
		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		for (int i=0; i<alarm.length; i++) {
			try {
				// System.out.println("getChannels(): cmts resid=" + cmtsResId);
	
				int bldId;
	
				if ((bldId = alarm[i].getTopologyKey().getBladeId().intValue()) == 0) {
					/*
					 * Clear Alarms from myself (which is Market)
					 */
					AlarmIdT[] superAlarms = new AlarmIdT[1];
					superAlarms[0] = alarm[i];
					super.clearAlarms(superAlarms);
				} else {
					/*
					 * Clear Alarms from Blade
					 */
					AlarmIdT[] bladeAlarms = new AlarmIdT[1];
					bladeAlarms[0] = alarm[i];
	
					Blade bld = new Blade(bldId);
					AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
							.getWebServicesPort()));
					
					ah.clearAlarms(bladeAlarms);
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

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		try {
			int bldId;
			
			if ((bldId = topoKey.getBladeId().intValue()) == 0) {
				/*
				 * Clear result from myself (which is Market)
				 */
				ret = super.getCurrentAlarmsForResource(topoKey, resourceId);
			} else {
				/*
				 * Clear result from Blade
				 */
				Blade bld = new Blade(bldId);
				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.getCurrentAlarmsForResource(topoKey, resourceId);
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
	public HistoricalAlarmsRespT getHistoricalAlarmsForResource(
			TopoHierarchyKeyT topoKey, BigInteger resourceId) {
		HistoricalAlarmsRespT ret = null;

		GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

		try {
			int bldId;
			
			if ((bldId = topoKey.getBladeId().intValue()) == 0) {
				/*
				 * Clear result from myself (which is Market)
				 */
				ret = super.getHistoricalAlarmsForResource(topoKey, resourceId);
			} else {
				/*
				 * Clear result from Blade
				 */
				Blade bld = new Blade(bldId);
				AlarmHelper ah = new AlarmHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.getHistoricalAlarmsForResource(topoKey, resourceId);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

}
