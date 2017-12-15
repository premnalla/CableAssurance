/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.data.xy.XYDataset;

import com.palmyrasyscorp.db.tables.Channel;

/**
 * @author Prem
 * 
 */
public class ChannelCountsChart extends AbstractTimeSeriesStepAreaChart {

	long m_channelResId = 0;

	String m_channelName = null;

	/**
	 * 
	 */
	protected ChannelCountsChart() {
		super();
	}

	public ChannelCountsChart(long hfcResId) {
		super();

		m_channelResId = hfcResId;
		Channel c = new Channel(hfcResId);
		m_channelName = new String(c.getName());
		c = null;
	}

	protected String getChartName() {
		String ret;
		ret = "CM counts for Channel : " + m_channelName;
		return (ret);
	}

	protected String getYAxisLabel() {
		String ret;
		ret = "Cable Modem counts";
		return (ret);
	}

	protected void setPlot(XYPlot plot) {
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
	}

	protected boolean overlayRemainingDatasets(JFreeChart chart,
			short datasetNum, XYDataset datasetI) {
		boolean ret = true;

		final XYPlot myPlot = chart.getXYPlot();
		final XYItemRenderer otherRenderer = new XYStepAreaRenderer();
		otherRenderer.setSeriesPaint(0, getOverlaySeriesPaint(datasetNum));
		// otherRenderer.setSeriesStroke(0, new BasicStroke(2));
		myPlot.setDataset(1, datasetI);
		myPlot.setRenderer(1, otherRenderer);

		return (ret);
	}

}
