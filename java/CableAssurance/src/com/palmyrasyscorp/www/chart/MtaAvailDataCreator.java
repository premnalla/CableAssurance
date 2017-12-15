/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.math.BigInteger;
import java.util.Date;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.www.webservices.helper.PerformanceHelper;

/**
 * @author Prem
 * 
 */
public class MtaAvailDataCreator extends AbstractMtaDataCreator {

	/**
	 * 
	 */
	public MtaAvailDataCreator(TopoHierarchyKeyT tK, BigInteger mtaResId) {
		super(tK, mtaResId);
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
		MtaAvailabilityHistoryT[] s = p.getMtaAvailabilityHistory(getTopKey(),
				getMtaResId(), fromTime, toTime, batch, qs);
		TimeSeries ts1 = new TimeSeries("Mta Availability", Minute.class);
		for (int i = 0; i < s.length; i++) {
			// list.add(s[i]);
			MtaAvailabilityHistoryT h = s[i];
			Short mtaAvail = new Short(h.getAvailable().getAvailable());
			Date d = new Date(h.getTimeSec().longValue() * 1000);
			Minute min = new Minute(d);
			ts1.add(min, mtaAvail);
		}
		TimeSeriesCollection tsc1 = new TimeSeriesCollection(ts1);
		// tsc1.setDomainIsPointsInTime(true);

		getMtaData().add(tsc1);

		return (ret);
	}

}
