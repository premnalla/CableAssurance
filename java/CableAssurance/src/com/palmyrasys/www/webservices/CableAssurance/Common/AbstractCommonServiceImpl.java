/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.ScrollPageT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.db.tables.Blade;

/**
 * @author Prem
 *
 */
public abstract class AbstractCommonServiceImpl {

	/**
	 * 
	 * @param in
	 * @return
	 */
	protected Calendar convertToDate(InputTimeT in) {
		Calendar ret = null;

		if (in.getDayOfMonth() == 0 && in.getMonthOfYear() == 0
				&& in.getYear() == 0) {
			Calendar yesterday = new GregorianCalendar();
			// yesterday.roll(Calendar.DAY_OF_MONTH, false);
			yesterday.add(Calendar.DAY_OF_MONTH, -1);			
			ret = yesterday;
		} else {
			ret = new GregorianCalendar((int) in.getYear(), (int) in
					.getMonthOfYear(), (int) in.getDayOfMonth());
		}
		return (ret);
	}
	
	/**
	 * 
	 * @param topologyKey
	 * @return
	 */
	protected TopoHierarchyKeyT getMarketTopoKey(TopoHierarchyKeyT topologyKey) {
		TopoHierarchyKeyT ret = null;
		if (topologyKey != null) {
			ret = new TopoHierarchyKeyT(topologyKey.getRegionId(),
					topologyKey.getMarketId(), new BigInteger("0"));
		}
		return (ret);
	}
	
	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @param gSysCfg
	 * @return
	 */
	protected long getMarketStartId(QueryStateT[] queryState, ResultBatchT batch, 
			GlobalSystemConfig gSysCfg) {
		long ret = 0;
		
		if (gSysCfg.doesMarketHaveCmts()) {
			QueryStateT mktQs = ServiceUtils.FindQsForMarket(queryState);
			ScrollPageT mktPage = CommonServiceUtils.FindBestScrollPageForStart(mktQs, batch);
			if (mktPage != null) {
				try {
					ret = Long.parseLong(mktPage.getStart());
				} catch (Exception e) {				
				}
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @param gSysCfg
	 * @return
	 */
	protected long getBladeStartId(QueryStateT[] queryState, ResultBatchT batch) {
		long ret = 0;
		
		QueryStateT bldQs = ServiceUtils.FindQsForBlade(queryState);
		ScrollPageT bldPage = CommonServiceUtils.FindBestScrollPageForStart(bldQs, batch);
		if (bldPage != null) {
			try {
				ret = Long.parseLong(bldPage.getStart());
			} catch (Exception e) {				
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @param topoKey
	 * @param mktL
	 * @param blades
	 * @param al
	 * @return
	 */
	protected QueryStateT[] setupReturnQueryStateForMkt(QueryStateT[] queryState,
			ResultBatchT batch, TopoHierarchyKeyT topoKey, List mktL, List blades,
			ArrayList al) {
		QueryStateT[] retQs = null;

		int retQsI = 0;
		int retQsSize = blades.size();
		
		QueryStateT mktQs = ServiceUtils.SetReturnQsForMarket(queryState, batch, al);
		
		if (mktQs != null) {
			retQsSize++;
			retQs = new QueryStateT[retQsSize];
			retQs[retQsI++] = mktQs;
		} else {
			retQs = new QueryStateT[retQsSize];						
		}
		

//		if (mktL != null) {
//			retQsSize++;			
//		}
//
//		retQs = new QueryStateT[retQsSize];
//		
//		if (mktL != null) {
//			/*
//			 * Setup the one for Market
//			 */
//			retQs[retQsI++] = 
//				ServiceUtils.SetReturnQsForMarket(queryState, batch, al);
//		}
		
		/*
		 * Setup the ones for each Blade
		 */
		for (int i = 0; retQsI<retQs.length && i<blades.size(); i++) {
			Blade b = (Blade) blades.get(i);
			retQs[retQsI++] = ServiceUtils.SetReturnQsForBlade(queryState, 
					topoKey, batch, b.getId(), al);			
		} // foreach blade				
		
		return (retQs);
	}
	
	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @param topoKey
	 * @param bldL
	 * @param blades
	 * @param al
	 * @return
	 */
	protected QueryStateT[] setupReturnQueryStateForBlade(QueryStateT[] queryState,
			ResultBatchT batch, TopoHierarchyKeyT topoKey, List bldL, List blades,
			ArrayList al) {
		QueryStateT[] retQs = null;

		QueryStateT bldQs = ServiceUtils.SetReturnQsForBlade(queryState, batch, al);
		
		if (bldQs != null) {
			retQs = new QueryStateT[1];
			retQs[0] = bldQs;
		} else {
			retQs = new QueryStateT[0];			
		}
		
//		boolean hasElem = bldL!=null && bldL.size()>0;
//		
//		if (hasElem) {
//			retQsSize++;			
//		}
//
//		retQs = new QueryStateT[retQsSize];
//		
//		if (hasElem) {
//			/*
//			 * Setup the one for Market
//			 */
//			retQs[retQsI++] = 
//				ServiceUtils.SetReturnQsForBlade(queryState, batch, al);
//		}
		
		return (retQs);
	}
	
	/**
	 * 
	 * @param qsArray
	 */
	protected void dumpQueryStates(QueryStateT[] qsArray) {
		try {
			if (qsArray != null && qsArray.length > 0) {
				System.out.println("QueryState Dump begin {");
				for (int i=0; i<qsArray.length; i++) {
					if (qsArray[i] != null) {
						System.out.println(qsArray[i].toString());
					}
				}
				System.out.println("} QueryState Dump end;");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
	
}
