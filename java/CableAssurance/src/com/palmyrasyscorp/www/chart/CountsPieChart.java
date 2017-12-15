/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.text.NumberFormat;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;

/**
 * @author Prem
 *
 */
public class CountsPieChart extends Abstract3DPieChart {

	private String m_chartName;
	
	private PieDataset m_dataset;
	
	protected CountsPieChart() {
		
	}
	
	public CountsPieChart(String chartName, PieDataset ds) {
		m_chartName = chartName;
		m_dataset = ds;
	}
	
	public String getChartName() {
		return m_chartName;
	}

	public PieDataset getDataset() {
		return m_dataset;
	}

	/**
	 * 
	 * @param chart
	 */
	protected void modifyChart(JFreeChart chart) {
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setForegroundAlpha(0.6f);
		plot.setCircular(true);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat
						.getPercentInstance()));
		plot.setNoDataMessage("No data available");
		plot.setSectionPaint(0, Color.GREEN);
		plot.setSectionPaint(1, Color.RED);
		setChartBackgroundColor(chart);
	}

}
