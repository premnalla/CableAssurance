/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;

/**
 * @author Prem
 *
 */
public class MtaProvStatusChart extends AbstractTimeSeriesStepChart {

	private String m_mtaProvStatusValues[] = {
			"0", "pass", "inProgress", "failConfigFileError", "passWithWarnings", "passWithIncompleteParsing", 
			"failureInternalError", "failOtherReason"
	};

	/**
	 * 
	 */
	public MtaProvStatusChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "MTA Provisioned Status";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Prov";
		return ret;
	}

//	protected void setYAxis(XYPlot plot) {
//		SymbolAxis symAxis = new SymbolAxis("Prov Status", m_mtaProvStatusValues);
//		// symAxis.setRange(0, 10);
//		symAxis.setGridBandsVisible(false);
//		plot.setRangeAxis(symAxis);
//		// yAxis.setAutoRange(true);
//	}

}
