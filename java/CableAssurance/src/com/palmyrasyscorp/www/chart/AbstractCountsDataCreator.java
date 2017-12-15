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

import com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

/**
 * @author Prem
 *
 */
public abstract class AbstractCountsDataCreator extends DataCreatorBase {

	private TopoHierarchyKeyT m_tK;

	protected TopoHierarchyKeyT getTopKey() {
		return m_tK;
	}

	private BigInteger m_resId;

	protected BigInteger getResId() {
		return m_resId;
	}

	private LinkedList m_cmCounts = new LinkedList();
	private LinkedList m_mtaCounts = new LinkedList();

	/**
	 * 
	 * @return
	 */
	public LinkedList getCmCounts() {
		return m_cmCounts;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList getMtaCounts() {
		return m_mtaCounts;
	}

	/**
	 * 
	 */
	protected AbstractCountsDataCreator(TopoHierarchyKeyT tK, BigInteger resId) {
		super();
		m_tK = tK;
		m_resId = resId;
	}

	/**
	 * 
	 */
	public void clearData() {
		m_cmCounts.clear();
		m_mtaCounts.clear();
	}

	/**
	 * 
	 * @return
	 */
	protected abstract GenericCountsHistoryT[] getCountsHistory();
	
	/**
	 * 
	 */
	public boolean createData() {
		boolean ret = true;

		GenericCountsHistoryT[] s = getCountsHistory();
		
		TimeSeries cm1 = new TimeSeries("Total", Minute.class);
		TimeSeries cm2 = new TimeSeries("Online", Minute.class);
		TimeSeries mta1 = new TimeSeries("Total", Minute.class);
		TimeSeries mta2 = new TimeSeries("Available", Minute.class);

		for (int i = 0; s != null && i < s.length; i++) {
			// list.add(s[i]);
			GenericCountsHistoryT h = s[i];
			Date d = new Date(h.getTimeSec().longValue() * 1000);
			
			Short cm_total = new Short(h.getCounts().getTotalCm());
			Short cm_online = new Short(h.getCounts().getOnlineCm());
			Short mta_total = new Short(h.getCounts().getTotalMta());
			Short mta_available = new Short(h.getCounts().getAvailableMta());

			Minute min = new Minute(d);
			
			cm1.add(min, cm_total);
			cm2.add(min, cm_online);
			mta1.add(min, mta_total);
			mta2.add(min, mta_available);
		}
		
		TimeSeriesCollection cm_tsc1 = new TimeSeriesCollection(cm1);
		// cm_tsc1.setDomainIsPointsInTime(true);

		TimeSeriesCollection cm_tsc2 = new TimeSeriesCollection(cm2);
		// cm_tsc2.setDomainIsPointsInTime(true);

		getCmCounts().add(cm_tsc1);
		// System.out.println("Added to list...");

		getCmCounts().add(cm_tsc2);
		// System.out.println("Added to list...");

		TimeSeriesCollection mta_tsc1 = new TimeSeriesCollection(mta1);
		// mta_tsc1.setDomainIsPointsInTime(true);

		TimeSeriesCollection mta_tsc2 = new TimeSeriesCollection(mta2);
		// mta_tsc2.setDomainIsPointsInTime(true);

		getMtaCounts().add(mta_tsc1);
		// System.out.println("Added to list...");

		getMtaCounts().add(mta_tsc2);
		// System.out.println("Added to list...");


		return (ret);
	}

}
