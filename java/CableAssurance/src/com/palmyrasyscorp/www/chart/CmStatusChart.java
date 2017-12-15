/**
 * 
 */
package com.palmyrasyscorp.www.chart;

// import org.jfree.chart.axis.*;
// import org.jfree.chart.plot.XYPlot;

/**
 * @author Prem
 * 
 */
public class CmStatusChart extends AbstractTimeSeriesStepChart {

	private String m_cmStatusValues[] = { "0", "other", "rangingranging",
			"rangingAborted", "rangingComplete", "ipComplete",
			"registrationComplete", "accessDenied", "operational",
			"registeredBPIInitializing" };

	/**
	 * 
	 */
	public CmStatusChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "Cable Modem DOCSIS Status";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Status";
		return ret;
	}

//	protected void setYAxis(XYPlot plot) {
//		SymbolAxis symAxis = new SymbolAxis("CM Status", m_cmStatusValues);
//		// symAxis.setRange(0, 10);
//		symAxis.setGridBandsVisible(false);
//		plot.setRangeAxis(symAxis);
//		// yAxis.setAutoRange(true);
//	}

}
