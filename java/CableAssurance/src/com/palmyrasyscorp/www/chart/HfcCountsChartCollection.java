/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

/**
 * @author Prem
 *
 */
public class HfcCountsChartCollection extends ChartCollectionBase {

	private HfcCountsDataCreator m_hfcCountsDc;

	private LinkedList m_chartList = new LinkedList();
	
	private CmCountsChart m_cmCountsChart = new CmCountsChart();
	private MtaCountsChart m_mtaCountsChart = new MtaCountsChart();

	/**
	 * 
	 *
	 */
	protected HfcCountsChartCollection() {		
	}
	
	/**
	 * 
	 * @param tK
	 * @param resId
	 */
	public HfcCountsChartCollection(TopoHierarchyKeyT tK, BigInteger resId) {
		super();
		// m_tK = tK;
		// m_resId = resId;
		m_hfcCountsDc = new HfcCountsDataCreator(tK, resId);
	}
	
	public LinkedList generateCharts(HttpSession session, PrintWriter pw) {
		m_hfcCountsDc.createData();
		
		m_cmCountsChart.setData(m_hfcCountsDc.getCmCounts());
		m_mtaCountsChart.setData(m_hfcCountsDc.getMtaCounts());
		
		m_chartList.add(m_cmCountsChart.generateChart(session, pw));
		m_chartList.add(m_mtaCountsChart.generateChart(session, pw));
		
		return m_chartList;
	}

}
