/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.xy.XYDataset;

/**
 * @author Prem
 * 
 */
public abstract class AbstractXYChart extends AbstractChartBase implements
		Chart {

	protected abstract String getChartName();

	protected abstract String getXAxisLabel();

	protected abstract String getYAxisLabel();

	protected abstract JFreeChart createChart(String chartName,
			String xAxisLabel, String yAxisLabel, XYDataset dataset);

	protected abstract void modifyChart(JFreeChart chart);

	// private BasicStroke m_bs = new BasicStroke(2);
	private BasicStroke m_bs = new BasicStroke(1.2f);

	public void setBasicStroke(BasicStroke bs) {
		m_bs = bs;
	}

	protected BasicStroke getBasicStroke() {
		return m_bs;
	}

	private Paint m_seriesPaint = Color.blue;

	public void setSeriesPaint(Paint p) {
		m_seriesPaint = p;
	}

	protected Paint getSeriesPaint() {
		return m_seriesPaint;
	}

	// private DateFormat m_dateFormat = new SimpleDateFormat("hh:mma");
	private DateFormat m_dateFormat = new SimpleDateFormat("HH:mm");

	public void setDateFormat(DateFormat df) {
		m_dateFormat = df;
	}

	protected DateFormat getDateFormat() {
		return m_dateFormat;
	}

	public static final int MAX_NUM_OVERLAYS = 4;

	private Paint[] m_overlaySeriesPaint = new Paint[MAX_NUM_OVERLAYS];

	public void setOverlaySeriesPaint(int ovNum, Paint p) {
		if (ovNum >= 0 && ovNum < MAX_NUM_OVERLAYS) {
			m_overlaySeriesPaint[ovNum] = p;
		}
	}

	protected Paint getOverlaySeriesPaint(int ovNum) {
		Paint ret = Color.green;
		if (ovNum >= 0 && ovNum < MAX_NUM_OVERLAYS) {
			ret = m_overlaySeriesPaint[ovNum];
		}
		return (ret);
	}

	private LinkedList m_data = null;
	
	/**
	 * 
	 * @param l
	 */
	public void setData(LinkedList l) {
		m_data = l;		
	}
	
	/**
	 * 
	 * 
	 */
	protected AbstractXYChart() {
		super();
		for (int i = 0; i < MAX_NUM_OVERLAYS; i++) {
			m_overlaySeriesPaint[i] = Color.green;
		}
	}

	/**
	 * 
	 * @param session
	 * @param pw
	 * @return
	 */
	public String generateChart(HttpSession session, PrintWriter pw) {
		String ret = null;

		LinkedList datasetList = m_data;
		XYDataset firstDataset = (XYDataset) datasetList.get(0);
		String chartName = getChartName();
		String xAxisLabel = getXAxisLabel();
		String yAxisLabel = getYAxisLabel();

		JFreeChart chart = createChart(chartName, xAxisLabel, yAxisLabel,
				firstDataset);

		modifyChart(chart);

		// Overlay remaining datasets
		for (short datasetNum = 1; datasetNum < datasetList.size(); datasetNum++) {
			// System.out.println("Overlaying another chart...");
			XYDataset datasetI = (XYDataset) datasetList.get(datasetNum);
			overlayRemainingDatasets(chart, datasetNum, datasetI);
		}

		try {
			// Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			ret = ServletUtilities.saveChartAsPNG(chart, getChartWidth(),
					getChartHeight(), info, session);

			// System.out.println("Saved PNG file ...");

			// Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, ret, info, false);
			pw.flush();

			// System.out.println("Wrote PNG file...");

		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			// filename = "public_error_500x300.png";
		}

		// System.out.println("File name : " + filename);

		datasetList.clear();
		datasetList = null;

		return (ret);
	}

	/**
	 * This method needs to be overwridden by subc-classes that wish to 
	 * overlay additional charts.
	 * 
	 * @param chart
	 * @param dsNum
	 * @param ds
	 * @return
	 */
	protected boolean overlayRemainingDatasets(JFreeChart chart,
			short dsNum, XYDataset ds) {
		return true;
	}

	
}
