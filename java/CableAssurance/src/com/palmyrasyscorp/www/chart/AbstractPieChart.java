/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.PieDataset;

// import org.jfree.data.xy.XYDataset;

/**
 * @author Prem
 * 
 */
public abstract class AbstractPieChart extends AbstractChartBase implements Chart {

	static protected final int CHART_WIDTH = 200;

	static protected final int CHART_HEIGHT = 150;

	/**
	 * 
	 */
	protected AbstractPieChart() {
		super();
		setChartHeight(CHART_HEIGHT);
		setChartWidth(CHART_WIDTH);
	}

	/**
	 * 
	 * @return
	 */
	protected abstract PieDataset getDataset();

	/**
	 * 
	 * @return
	 */
	protected abstract String getChartName();

	/**
	 * 
	 * @param session
	 * @param pw
	 * @return List of Filenames
	 */
	public String generateChart(HttpSession session, PrintWriter pw) {
		String filename = null;

		PieDataset dataset = (PieDataset) getDataset();
		String chartName = getChartName();

		JFreeChart chart = createChart(chartName, dataset);

		modifyChart(chart);
		
		try {
			// Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, getChartWidth(),
					getChartHeight(), info, session);

			// System.out.println("Saved to PNG file: " + filename);

			// Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();

			System.out.println("Wrote PNG file: " + filename);

		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
		}

		return (filename);
	}

	/**
	 * 
	 * @param chartName
	 * @param xAxisLabel
	 * @param yAxisLabel
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(String chartName, PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(chartName, dataset,
				false, false, false);
		return (chart);
	}

	/**
	 * 
	 * @param chart
	 */
	protected void modifyChart(JFreeChart chart) {
		setChartBackgroundColor(chart);
	}
	
}
