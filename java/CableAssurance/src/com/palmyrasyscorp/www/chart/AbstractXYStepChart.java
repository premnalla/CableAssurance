/**
 * !!! OBSOLETED !!!
 */
package com.palmyrasyscorp.www.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.axis.*;

/**
 * @author Prem
 *
 */
public abstract class AbstractXYStepChart extends AbstractXYChart {

	static protected final int CHART_WIDTH=500;
	static protected final int CHART_HEIGHT=400;
	
	/**
	 * 
	 */
	protected AbstractXYStepChart() {
		super();
		setChartWidth(CHART_WIDTH);
		setChartHeight(CHART_HEIGHT);
	}

	protected JFreeChart createChart(String chartName, String xAxisLabel, String yAxisLabel, XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				chartName,
				xAxisLabel, yAxisLabel,
				dataset,
				// PlotOrientation.VERTICAL,
				true,  // legend
				false,  // tool tips
				false  // URLs
		);

		return (chart);
	}
	
	protected void modifyChart(JFreeChart chart, short chartNum) {
		// background colour
		setChartBackgroundColor(chart, chartNum);
		
		// get plot & renderer
		final XYPlot plot = chart.getXYPlot();
		
		// modify renderer
		setChartRenderer(plot, chartNum);

		// modify plot
		setPlot(plot);
		
		// modify time axis
		final DateAxis axis = (DateAxis) plot.getDomainAxis();
 		setDateAxis(axis, chartNum);
        
 		// modify y-axis
  		setYAxis(plot, chartNum);
	}

	protected void setChartBackgroundColor(JFreeChart chart, short chartNum) {
		chart.setBackgroundPaint(java.awt.Color.lightGray);
	}
	
	protected void setChartRenderer(XYPlot plot, short chartNum) {
		XYItemRenderer renderer = new XYStepRenderer();
		renderer.setSeriesPaint(0, Color.blue);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		plot.setRenderer(0, renderer);
	}
	
	protected void setPlot(XYPlot plot) {
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
	}
	
	protected void setDateAxis(DateAxis axis, short chartNum) {
		axis.setDateFormatOverride(new SimpleDateFormat("hh:mma"));
	}
	
	protected void setYAxis(XYPlot plot, short chartNum) {
		// ValueAxis yAxis = plot.getRangeAxis();
		// yAxis.setRange(0.0, 10.0);
	}
	
	protected boolean overlayRemainingDatasets(JFreeChart chart, short chartNum, short datasetNum,
			XYDataset datasetI) {
		boolean ret = true;
		
		final XYPlot myPlot = chart.getXYPlot();
		final XYItemRenderer renderer = new XYStepRenderer();
		renderer.setSeriesPaint(0, Color.green);
		myPlot.setDataset(1, datasetI);
		myPlot.setRenderer(1, renderer);
		
		return (ret);
	}

}
