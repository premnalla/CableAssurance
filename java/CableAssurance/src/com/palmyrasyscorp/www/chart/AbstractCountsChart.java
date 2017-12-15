/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.data.xy.XYDataset;

/**
 * @author Prem
 *
 */
public abstract class AbstractCountsChart extends
		AbstractTimeSeriesStepAreaChart {

	protected boolean overlayRemainingDatasets(JFreeChart chart,
			short datasetNum, XYDataset datasetI) {
		boolean ret = true;

		final XYPlot myPlot = chart.getXYPlot();
		final XYItemRenderer otherRenderer = new XYStepAreaRenderer();
		otherRenderer.setSeriesPaint(0, getOverlaySeriesPaint(datasetNum));
		// otherRenderer.setSeriesStroke(0, new BasicStroke(2));
		
		//
		XYDataset origDs = myPlot.getDataset(0);
		XYItemRenderer origRen = myPlot.getRenderer(0);
		
		myPlot.setDataset(0, datasetI);
		myPlot.setRenderer(0, otherRenderer);
		
		//
		myPlot.setDataset(1, origDs);
		myPlot.setRenderer(1, origRen);

		return (ret);
	}

}
