/**
 * 
 */
package com.palmyrasyscorp.www.chart;

/**
 * @author Prem
 *
 */
public class MtaAvailabilityChart extends AbstractTimeSeriesStepChart {

	/**
	 * 
	 */
	public MtaAvailabilityChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "MTA Availability";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Availability";
		return ret;
	}

}
