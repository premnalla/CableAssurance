/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.www.webservices.helper.PerformanceHelper;

/**
 * @author Prem
 *
 */
public class CmPerfDataCreator extends AbstractCmDataCreator {

	private LinkedList m_downstreamSnrList = new LinkedList();
	private LinkedList m_downstreamPowerList = new LinkedList();
	private LinkedList m_upstreamPowerList = new LinkedList();
	private LinkedList m_t1TimeoutList = new LinkedList();
	private LinkedList m_t2TimeoutList = new LinkedList();
	private LinkedList m_t3TimeoutList = new LinkedList();
	private LinkedList m_t4TimeoutList = new LinkedList();

	/**
	 * 
	 * @return
	 */
	public LinkedList getDownstreamSnrData() {
		return m_downstreamSnrList;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList getDownstreamPowerData() {
		return m_downstreamPowerList;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList getUpstreamPowerData() {
		return m_upstreamPowerList;
	}

	/**
	 * 
	 */
	public CmPerfDataCreator(TopoHierarchyKeyT tK, BigInteger cmResId) {
		super(tK, cmResId);
	}

	/**
	 * 
	 */
	public void clearData() {
		m_downstreamSnrList.clear();
		m_downstreamPowerList.clear();
		m_upstreamPowerList.clear();
		m_t1TimeoutList.clear();
		m_t2TimeoutList.clear();
		m_t3TimeoutList.clear();
		m_t4TimeoutList.clear();
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
		CmPerformanceHistoryT[] s = p.getCmPerformanceHistory(getTopKey(),
				getCmResId(), fromTime, toTime, batch, qs);
		TimeSeries ts1 = new TimeSeries("Downstrem SNR", Minute.class);
		TimeSeries ts2 = new TimeSeries("Downstream Power", Minute.class);
		TimeSeries ts3 = new TimeSeries("Upstream Power", Minute.class);
		for (int i = 0; i < s.length; i++) {
			// list.add(s[i]);
			CmPerformanceHistoryT h = s[i];
			Date d = new Date(h.getTimeSec().longValue() * 1000);
			Float dsSnr = new Float(((float) h.getCmPerformance()
					.getDownstreamSNR()) / 10);
			Float dsPower = new Float(((float) h.getCmPerformance()
					.getDownstreamPower()) / 10);
			Float usPower = new Float(((float) h.getCmPerformance()
					.getUpstreamPower()) / 10);
			Minute min = new Minute(d);
			ts1.add(min, dsSnr);
			ts2.add(min, dsPower);
			ts3.add(min, usPower);
		}
		TimeSeriesCollection tsc1 = new TimeSeriesCollection(ts1);
		// tsc1.setDomainIsPointsInTime(true);

		TimeSeriesCollection tsc2 = new TimeSeriesCollection(ts2);
		// tsc2.setDomainIsPointsInTime(true);

		TimeSeriesCollection tsc3 = new TimeSeriesCollection(ts3);
		// tsc3.setDomainIsPointsInTime(true);

		// TimeSeriesCollection tsc4 = new TimeSeriesCollection(ts4);
		// tsc4.setDomainIsPointsInTime(true);

		m_downstreamSnrList.add(tsc1);
		m_downstreamPowerList.add(tsc2);
		m_upstreamPowerList.add(tsc3);

		return (ret);
	}

}
