/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.math.BigInteger;
import java.util.Date;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.www.webservices.helper.PerformanceHelper;

/**
 * @author Prem
 * 
 */
public class CmStatusDataCreator extends AbstractCmDataCreator {

	/**
	 * 
	 */
	public CmStatusDataCreator(TopoHierarchyKeyT tK, BigInteger cmResId) {
		super(tK, cmResId);
	}

	/**
	 * 
	 */
	public boolean createData() {
		boolean ret = true;

		InputTimeT fromTime = new InputTimeT();
		InputTimeT toTime = new InputTimeT();
		ResultBatchT batch = new ResultBatchT();

		PerformanceHelper p = new PerformanceHelper();
		QueryStateT qs = null;
		CmStatusHistoryT[] s = p.getCmStatusHistory(getTopKey(), getCmResId(),
				fromTime, toTime, batch, qs);
		TimeSeries ts1 = new TimeSeries("Modem Status", Minute.class);
		for (int i = 0; i < s.length; i++) {
			// list.add(s[i]);
			CmStatusHistoryT h = s[i];
			Integer cm_state = new Integer(h.getCmStatus().getDocsisState());
			Date d = new Date(h.getTimeSec().longValue() * 1000);
			Minute min = new Minute(d);
			ts1.add(min, cm_state);
		}
		TimeSeriesCollection tsc1 = new TimeSeriesCollection(ts1);
		// tsc1.setDomainIsPointsInTime(true);

		getCmData().add(tsc1);

		return (ret);
	}

}
