/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;


/**
 * @author Prem
 *
 */
public abstract class AbstractTimeSeriesStepChart extends
		AbstractTimeSeriesChart {

	protected void setChartRenderer(XYPlot plot) {
		XYItemRenderer renderer = new XYStepRenderer();
		renderer.setSeriesPaint(0, getSeriesPaint());
		renderer.setSeriesStroke(0, getBasicStroke());
		plot.setRenderer(0, renderer);
	}
	

}
