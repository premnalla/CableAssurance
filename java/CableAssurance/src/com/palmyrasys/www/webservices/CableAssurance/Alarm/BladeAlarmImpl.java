/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT;
import com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT;
import com.palmyrasys.www.webservices.CableAssurance.Common.CommonServiceUtils;

/**
 * @author Prem
 *
 */
public class BladeAlarmImpl extends AbstractAlarmImpl {

	private static Log log = LogFactory.getLog(BladeAlarmImpl.class);

	/**
	 * 
	 *
	 */
	public BladeAlarmImpl() {
		
	}
	
	/**
	 * 
	 */
	public CurrentAlarmsRespT getCurrentAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		CurrentAlarmsRespT ret = null;

		try {
			// System.out.println("Got call");
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List bldL = null;	
			short batchSize = batch.getBatchSize();
			long mktStartId = getBladeStartId(queryState, batch);
			
			// System.out.println("StartId=" + mktStartId);
			
			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			bldL = getCurrentAlarmsForMkt(fromTime, toTime, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddCurrentAlarmsToTree(tree, bldL, batchSize);
			
			/*
			 * Debug only
			 */
			// dumpCurrentAlarmTree(tree);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to linear list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, null, 
					bldL, null, al);

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
			if (bldL != null) {
				bldL.clear();
				bldL = null;
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
	public CurrentAlarmsRespT getCurrentAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState) {
		CurrentAlarmsRespT ret = null;

		try {
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List bldL = null;	
			short batchSize = batch.getBatchSize();
			long mktStartId = getBladeStartId(queryState, batch);
			
			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			bldL = getCurrentAlarmsForMkt(alarmType, alarmSubType, fromTime, toTime, 
					batchSize, mktStartId, null);
			CommonServiceUtils.AddCurrentAlarmsToTree(tree, bldL, batchSize);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to linear list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, null, 
					bldL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createCurrentAlarmsResponse(retQs, al);					

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (bldL != null) {
				bldL.clear();
				bldL = null;
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
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List bldL = null;	
			short batchSize = batch.getBatchSize();
			long mktStartId = getBladeStartId(queryState, batch);
			
			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			bldL = getHistoricalAlarmsForMkt(fromTime, toTime, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddHistoricalAlarmsToTree(tree, bldL, batchSize);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to linear list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, null, 
					bldL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createHistoricalAlarmsResponse(retQs, al);		

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (bldL != null) {
				bldL.clear();
				bldL = null;
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
	public HistoricalAlarmsRespT getHistoricalAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState) {
		HistoricalAlarmsRespT ret = null;

		try {
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List bldL = null;	
			short batchSize = batch.getBatchSize();
			long mktStartId = getBladeStartId(queryState, batch);
			
			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			bldL = getHistoricalAlarmsForMkt(alarmType, alarmSubType, fromTime, toTime, 
					batchSize, mktStartId, null);
			CommonServiceUtils.AddHistoricalAlarmsToTree(tree, bldL, batchSize);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to linear list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, null, 
					bldL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createHistoricalAlarmsResponse(retQs, al);		

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (bldL != null) {
				bldL.clear();
				bldL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

}
