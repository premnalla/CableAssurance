/**
 * 
 */
package com.palmyrasyscorp.www.chart;

/**
 * @author Prem
 *
 */
public class CmDownPowerChart extends AbstractCmPerfChart {

	/**
	 * 
	 */
	public CmDownPowerChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "Cable Modem Downstream Power";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Power (dBmV)";
		return ret;
	}

}
