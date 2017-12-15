/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Reports;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import com.palmyrasys.www.webservices.CableAssurance.HierarchyT;
import com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT;
import com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT;
import com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.Common.CommonServiceUtils;
import com.palmyrasyscorp.db.tables.Blade;

/**
 * @author Prem
 *
 */
public class BladeReportImpl extends AbstractReportImpl {

	private static Log log = LogFactory.getLog(BladeReportImpl.class);

	/**
	 * 
	 * IF resId==0 => implies for the whole blade/market IF resId!=0 => implies
	 * for CMTS
	 * 
	 */
	public HfcStatusSummaryRespT getHfcStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		HfcStatusSummaryRespT ret = null;
		
		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getHfcStatusSummary1ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddHfcStatusSummary1ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createHfcStatusSummaryResponse(retQs, al);

			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

	/**
	 * 
	 */
	public HfcStatusSummaryRespT getHfcStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		HfcStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getHfcStatusSummary2ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddHfcStatusSummary2ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createHfcStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
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
	 * topologyKey - indicates hierarchy of Resource in 'resId' 
	 * resId - Resource ID 
	 * resType - Resource Type (CMTS, HFC, Channel, etc)
	 * fromTime - time based search input 
	 * toTime - time based search
	 * input batch - num rows to return 
	 * queryState - search state; used in next/prev batch determination
	 * 
	 */
	public MtaStatusSummaryRespT getMtaStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		MtaStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getMtaSummary1ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddMtaStatusSummary1ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createMtaStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

	/**
	 * 
	 */
	public MtaStatusSummaryRespT getMtaStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		MtaStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getMtaSummary2ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddMtaStatusSummary2ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createMtaStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

	public CmStatusSummaryRespT getCmStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		CmStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getCmStatusSummary1ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddCmStatusSummary1ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createCmStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

	public CmStatusSummaryRespT getCmStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		CmStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getCmStatusSummary2ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddCmStatusSummary2ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Get all Blades
			 */
			
			List blades = Blade.QueryBlades();				
			
			/*
			 * Get Data from Blades & Add them to the Tree
			 */
			
			getAndAddCmStatusSummary2DataFromBlades(topologyKey, resId, resType,
					fromTime, toTime, batch, queryState, blades, tree, batchSize);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, blades, al);

			/*
			 * Create return data
			 */
			
			ret = createCmStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

	public CmStatusSummaryRespT getCmTcaStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		CmStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getCmTcaSummary1ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddCmTcaSummary1ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createCmStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

	public CmStatusSummaryRespT getCmTcaStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId,
			ResourceTypeT resType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState)
			throws java.rmi.RemoteException {
		CmStatusSummaryRespT ret = null;

		try {
			/*
			 * Initialize
			 */
			
			ArrayList al = new ArrayList();
			TreeMap tree = new TreeMap();
			List mktL = null;	
			short batchSize = batch.getBatchSize();
			Calendar fromDate = convertToDate(fromTime);
			Calendar toDate = convertToDate(toTime);
			TopoHierarchyKeyT topoKey = getMarketTopoKey(topologyKey);
			long mktStartId = getBladeStartId(queryState, batch);

			/*
			 * Get Data from MarketServer DB & Add them to the Tree
			 */
			
			mktL = getCmTcaSummary2ForMkt(resId, resType, fromDate, toDate, batchSize, 
					mktStartId, null);
			CommonServiceUtils.AddCmTcaSummary2ToTree(tree, mktL, batchSize, topoKey);
			
			/*
			 * Transfer first 'batchSize' elements from Tree to liner list
			 */
			
			// System.out.println("Tree.size=" + tree.size());
			CommonServiceUtils.TreeToListDescending(al, tree, batchSize);
			// System.out.println("ArrayList.size=" + al.size());		
			
			/*
			 * Setup return QueryState (to use in next pass of get)
			 */
					
			QueryStateT[] retQs = setupReturnQueryStateForBlade(queryState, batch, 
					topoKey, mktL, null, al);

			/*
			 * Create return data
			 */
			
			ret = createCmStatusSummaryResponse(retQs, al);
			
			al.clear();
			al = null;
			tree.clear();
			tree = null;
			if (mktL != null) {
				mktL.clear();
				mktL = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

}
