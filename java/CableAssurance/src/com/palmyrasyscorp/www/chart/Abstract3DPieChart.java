/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

/**
 * @author Prem
 *
 */
public abstract class Abstract3DPieChart extends AbstractPieChart {

	/**
	 * 
	 * @param chartName
	 * @param xAxisLabel
	 * @param yAxisLabel
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(String chartName, PieDataset dataset) {
		
		JFreeChart chart = ChartFactory.createPieChart3D(chartName, dataset,
				false, false, false);
		
		// PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// plot.setForegroundAlpha(0.6f);
		// plot.setCircular(true);
		// plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
		// 		"{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat
		// 				.getPercentInstance()));
		// plot.setNoDataMessage("No data available");

		return (chart);
	}

}
