/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 * @author Prem
 *
 */
public abstract class AbstractCmPerfChart extends AbstractTimeSeriesStepChart {

	/**
	 * 
	 *
	 */
	protected AbstractCmPerfChart() {
		super();
	}
	
	/**
	 * 
	 */
	protected void setChartRenderer(XYPlot plot) {
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, getSeriesPaint());
		renderer.setSeriesStroke(0, getBasicStroke());
		
		// XYItemRenderer renderer = new XYStepRenderer();
		// XYItemRenderer renderer = new XYRenderer();
		// renderer.setSeriesPaint(0, getSeriesPaint());
		// renderer.setSeriesStroke(0, getBasicStroke());
		// plot.setRenderer(0, renderer);
	}

	protected void setYAxis(XYPlot plot) {
		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setStandardTickUnits(AxTickUnits.getInstance().getMixedTickUnits());
		// yAxis.setStandardTickUnits(AxTickUnits.getInstance()
		// 		.getLongTickUnits());
		// yAxis.setRange(0.0, 10.0);
	}

}
