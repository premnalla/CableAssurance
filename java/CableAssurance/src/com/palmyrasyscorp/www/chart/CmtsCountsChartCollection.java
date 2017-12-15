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
public class CmtsCountsChartCollection extends ChartCollectionBase {

	private CmtsCountsDataCreator m_cmtsCountsDc;

	private LinkedList m_chartList = new LinkedList();
	
	private CmCountsChart m_cmCountsChart = new CmCountsChart();
	private MtaCountsChart m_mtaCountsChart = new MtaCountsChart();

	/**
	 * 
	 *
	 */
	protected CmtsCountsChartCollection() {		
	}
	
	/**
	 * 
	 * @param tK
	 * @param resId
	 */
	public CmtsCountsChartCollection(TopoHierarchyKeyT tK, BigInteger resId) {
		super();
		// m_tK = tK;
		// m_resId = resId;
		m_cmtsCountsDc = new CmtsCountsDataCreator(tK, resId);
	}
	
	public LinkedList generateCharts(HttpSession session, PrintWriter pw) {
		m_cmtsCountsDc.createData();
		
		m_cmCountsChart.setData(m_cmtsCountsDc.getCmCounts());
		m_mtaCountsChart.setData(m_cmtsCountsDc.getMtaCounts());
		
		m_chartList.add(m_cmCountsChart.generateChart(session, pw));
		m_chartList.add(m_mtaCountsChart.generateChart(session, pw));
		
		return m_chartList;
	}

}
