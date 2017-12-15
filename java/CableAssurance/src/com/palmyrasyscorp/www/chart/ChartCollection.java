/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

/**
 * @author Prem
 *
 */
public interface ChartCollection {

	/**
	 * 
	 * @return
	 */
	public LinkedList generateCharts(HttpSession session, PrintWriter pw);
	
}
