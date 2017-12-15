/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.math.BigInteger;

import com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.www.webservices.helper.PerformanceHelper;

/**
 * @author Prem
 *
 */
public class CmtsCountsDataCreator extends AbstractCountsDataCreator {

	/**
	 * 
	 */
	public CmtsCountsDataCreator(TopoHierarchyKeyT tK, BigInteger resId) {
		super(tK, resId);
	}

	/**
	 * 
	 * @return
	 */
	protected GenericCountsHistoryT[] getCountsHistory() {
		InputTimeT fromTime = new InputTimeT();
		InputTimeT toTime = new InputTimeT();
		ResultBatchT batch = new ResultBatchT();
		PerformanceHelper p = new PerformanceHelper();
		QueryStateT qs = null;
		GenericCountsHistoryT[] s = p.getCmtsCountsHistory(getTopKey(),
				getResId(), fromTime, toTime, batch, qs);
		return (s);
	}

}
