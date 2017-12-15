/**
 * 
 */
package com.palmyrasyscorp.www.chart;

/**
 * @author Prem
 *
 */
public class CmCountsChart extends AbstractCountsChart {

	/**
	 * 
	 *
	 */
	public CmCountsChart() {
		
	}

	/**
	 * 
	 */
	protected String getChartName() {
		String ret;
		ret = "Cable Modem counts";
		return (ret);
	}

	/**
	 * 
	 */
	protected String getYAxisLabel() {
		String ret;
		ret = "CM counts";
		return (ret);
	}

}
