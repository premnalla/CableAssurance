/**
 * 
 */
package com.palmyrasyscorp.www.chart;


/**
 * @author Prem
 *
 */
public class CmDownSnrChart extends AbstractCmPerfChart {

	/**
	 * 
	 */
	public CmDownSnrChart() {
		super();
	}

	protected String getChartName() {
		String ret;
		ret = "Cable Modem Downstream SNR";
		return ret;
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "SNR (dB)";
		return ret;
	}

}
