/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
import com.palmyrasyscorp.www.webservices.helper.TopologyHelper;

/**
 * @author Prem
 *
 */
public class EmtaChartCollection extends ChartCollectionBase {

	private TopoHierarchyKeyT m_tK;
	private BigInteger m_mtaResId;
	
	private MtaAvailDataCreator m_mtaAvailDc;
	private MtaPingDataCreator m_mtaPingDc;
	private MtaProvDataCreator m_mtaProvDc;

	private CmStatusDataCreator m_cmStatusDc;
	private CmPerfDataCreator m_cmPerfDc;

	private MtaAvailabilityChart m_mtaAvailChart = new MtaAvailabilityChart();
	private MtaPingStatusChart m_mtaPingChart = new MtaPingStatusChart();
	private MtaProvStatusChart m_mtaProvChart = new MtaProvStatusChart();
	
	private CmStatusChart m_cmStatusChart = new CmStatusChart();
	private CmDownSnrChart m_downSnrChart = new CmDownSnrChart();
	private CmUpPowerChart m_upPowerChart = new CmUpPowerChart();
	private CmDownPowerChart m_downPowerChart = new CmDownPowerChart();

	private LinkedList m_chartList = new LinkedList();
	
	/**
	 * 
	 *
	 */
	protected EmtaChartCollection() {		
	}
	
	/**
	 * 
	 * @param tK
	 * @param resId
	 */
	public EmtaChartCollection(TopoHierarchyKeyT tK, BigInteger resId) {
		super();
		m_tK = tK;
		m_mtaResId = resId;
		m_mtaAvailDc = new MtaAvailDataCreator(tK, resId);
		m_mtaPingDc = new MtaPingDataCreator(tK, resId);
		m_mtaProvDc = new MtaProvDataCreator(tK, resId);
	}

	public LinkedList generateCharts(HttpSession session, PrintWriter pw) {
		TopologyHelper th = new TopologyHelper();
		EmtaT e = th.getEmta(m_tK, m_mtaResId);		
		m_cmStatusDc = new CmStatusDataCreator(m_tK, e.getCmResId());
		m_cmPerfDc = new CmPerfDataCreator(m_tK, e.getCmResId());

		m_mtaAvailDc.createData();
		m_mtaPingDc.createData();
		m_mtaProvDc.createData();

		m_cmStatusDc.createData();
		m_cmPerfDc.createData();
		
		m_mtaAvailChart.setData(m_mtaAvailDc.getMtaData());
		m_mtaPingChart.setData(m_mtaPingDc.getMtaData());
		m_mtaProvChart.setData(m_mtaProvDc.getMtaData());
		
		m_cmStatusChart.setData(m_cmStatusDc.getCmData());
		m_downSnrChart.setData(m_cmPerfDc.getDownstreamSnrData());
		m_upPowerChart.setData(m_cmPerfDc.getUpstreamPowerData());
		m_downPowerChart.setData(m_cmPerfDc.getDownstreamPowerData());
		
		m_chartList.add(m_mtaAvailChart.generateChart(session, pw));
		m_chartList.add(m_mtaPingChart.generateChart(session, pw));
		m_chartList.add(m_mtaProvChart.generateChart(session, pw));
		
		m_chartList.add(m_cmStatusChart.generateChart(session, pw));
		m_chartList.add(m_downSnrChart.generateChart(session, pw));
		m_chartList.add(m_upPowerChart.generateChart(session, pw));
		m_chartList.add(m_downPowerChart.generateChart(session, pw));		
		
		return m_chartList;
	}

}
