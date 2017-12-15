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
/**
 * @author Prem
 *
 */
public class MtaPingStatusChart extends AbstractTimeSeriesStepChart {

	private String m_mtaPingStatusValues[] = {
			"Unknown", "Alive", "Unreachable"
	};

	/**
	 * 
	 */
	public MtaPingStatusChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "MTA Ping Status";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Ping";
		return ret;
	}

//	protected void setYAxis(XYPlot plot) {
//		SymbolAxis symAxis = new SymbolAxis("Ping Status", m_mtaPingStatusValues);
//		// symAxis.setRange(0, 10);
//		symAxis.setGridBandsVisible(false);
//		plot.setRangeAxis(symAxis);
//		// yAxis.setAutoRange(true);
//	}

}
