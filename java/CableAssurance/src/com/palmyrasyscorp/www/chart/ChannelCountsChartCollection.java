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
public class ChannelCountsChartCollection extends ChartCollectionBase {

	private ChannelCountsDataCreator m_channelCountsDc;

	private LinkedList m_chartList = new LinkedList();
	
	private CmCountsChart m_cmCountsChart = new CmCountsChart();
	private MtaCountsChart m_mtaCountsChart = new MtaCountsChart();

	/**
	 * 
	 *
	 */
	protected ChannelCountsChartCollection() {		
	}
	
	/**
	 * 
	 * @param tK
	 * @param resId
	 */
	public ChannelCountsChartCollection(TopoHierarchyKeyT tK, BigInteger resId) {
		super();
		// m_tK = tK;
		// m_resId = resId;
		m_channelCountsDc = new ChannelCountsDataCreator(tK, resId);
	}
	
	public LinkedList generateCharts(HttpSession session, PrintWriter pw) {
		m_channelCountsDc.createData();
		
		m_cmCountsChart.setData(m_channelCountsDc.getCmCounts());
		m_mtaCountsChart.setData(m_channelCountsDc.getMtaCounts());
		
		m_chartList.add(m_cmCountsChart.generateChart(session, pw));
		m_chartList.add(m_mtaCountsChart.generateChart(session, pw));
		
		return m_chartList;
	}

}
