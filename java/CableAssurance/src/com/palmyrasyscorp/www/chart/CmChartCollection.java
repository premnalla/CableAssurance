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
public class CmChartCollection extends ChartCollectionBase {

	// private TopoHierarchyKeyT m_tK;
	// private BigInteger m_resId;
	
	private CmStatusDataCreator m_cmStatusDc;
	private CmPerfDataCreator m_cmPerfDc;
	
	private CmStatusChart m_cmStatusChart = new CmStatusChart();
	private CmDownSnrChart m_downSnrChart = new CmDownSnrChart();
	private CmUpPowerChart m_upPowerChart = new CmUpPowerChart();
	private CmDownPowerChart m_downPowerChart = new CmDownPowerChart();
	
	private LinkedList m_chartList = new LinkedList();
	
	/**
	 * 
	 *
	 */
	protected CmChartCollection() {		
	}
	
	/**
	 * 
	 * @param tK
	 * @param resId
	 */
	public CmChartCollection(TopoHierarchyKeyT tK, BigInteger resId) {
		super();
		// m_tK = tK;
		// m_resId = resId;
		m_cmStatusDc = new CmStatusDataCreator(tK, resId);
		m_cmPerfDc = new CmPerfDataCreator(tK, resId);
	}
	
	
	/**
	 * 
	 */
	public LinkedList generateCharts(HttpSession session, PrintWriter pw) {
		
		m_cmStatusDc.createData();
		m_cmPerfDc.createData();
		
		m_cmStatusChart.setData(m_cmStatusDc.getCmData());
		m_downSnrChart.setData(m_cmPerfDc.getDownstreamSnrData());
		m_upPowerChart.setData(m_cmPerfDc.getUpstreamPowerData());
		m_downPowerChart.setData(m_cmPerfDc.getDownstreamPowerData());
		
		m_chartList.add(m_cmStatusChart.generateChart(session, pw));
		m_chartList.add(m_downSnrChart.generateChart(session, pw));
		m_chartList.add(m_upPowerChart.generateChart(session, pw));
		m_chartList.add(m_downPowerChart.generateChart(session, pw));		
		
		return m_chartList;
	}

}
