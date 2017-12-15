/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

/**
 * @author Prem
 * 
 */
public abstract class AbstractTimeSeriesChart extends AbstractXYChart {

	protected JFreeChart createChart(String chartName, String xAxisLabel,
			String yAxisLabel, XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(chartName,
				xAxisLabel, yAxisLabel, dataset,
				// PlotOrientation.VERTICAL,
				true, // legend
				false, // tool tips
				false // URLs
				);

		return (chart);
	}

	protected abstract void setChartRenderer(XYPlot plot);

	protected void modifyChart(JFreeChart chart) {
		// background colour
		setChartBackgroundColor(chart);

		// get plot & renderer
		final XYPlot plot = chart.getXYPlot();

		// modify renderer
		setChartRenderer(plot);

		// modify plot
		setPlot(plot);

		// modify time axis
		final DateAxis axis = (DateAxis) plot.getDomainAxis();
		setDateAxis(axis);

		// modify y-axis
		setYAxis(plot);
	}

	protected void setPlot(XYPlot plot) {
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
	}

	protected void setDateAxis(DateAxis axis) {
		axis.setDateFormatOverride(getDateFormat());
	}

	protected void setYAxis(XYPlot plot) {
		ValueAxis yAxis = plot.getRangeAxis();
		// yAxis.setStandardTickUnits(AxTickUnits.getInstance().getMixedTickUnits());
		yAxis.setStandardTickUnits(AxTickUnits.getInstance()
				.getLongTickUnits());
		// yAxis.setRange(0.0, 10.0);
	}

	protected String getXAxisLabel() {
		String ret;
		ret = "Time";
		return (ret);
	}

}
