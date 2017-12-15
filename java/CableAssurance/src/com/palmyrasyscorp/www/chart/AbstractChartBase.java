/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.JFreeChart;

/**
 * @author Prem
 * 
 */
public abstract class AbstractChartBase implements Chart {

	static protected final int PLOT_WIDTH = 400;

	static protected final int PLOT_HEIGHT = 200;

	private int m_chartWidth = PLOT_WIDTH;
	protected int getChartWidth() {
		return m_chartWidth;
	}
	public void setChartWidth(int w) {
		m_chartWidth = w;
	}

	private int m_chartHeight = PLOT_HEIGHT;
	protected int getChartHeight() {
		return m_chartHeight;
	}
	public void setChartHeight(int h) {
		m_chartHeight = h;
	}	

	// private Paint m_bgColor = Color.lightGray;
	// private Paint m_bgColor = new Color(255,247,241);
	private Paint m_bgColor = new Color(255,250,241);
	public void setBgColor(Paint p) {
		m_bgColor = p;
	}
	protected Paint getBgColor() {
		return m_bgColor;
	}
	
	private Paint m_fgColour = Color.lightGray;
	public void setFgColor(Paint p) {
		m_fgColour = p;
	}
	protected Paint getFgColor() {
		return m_fgColour;
	}

	protected void setChartBackgroundColor(JFreeChart chart) {
		chart.setBackgroundPaint(m_bgColor);
	}
		
}
