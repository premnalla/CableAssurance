/**
 * 
 */
package com.palmyrasyscorp.www.chart;

/**
 * @author Prem
 *
 */
public class CmUpPowerChart extends AbstractCmPerfChart {

	/**
	 * 
	 */
	public CmUpPowerChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "Cable Modem Upstream Power";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Power (dBmV)";
		return ret;
	}

}
