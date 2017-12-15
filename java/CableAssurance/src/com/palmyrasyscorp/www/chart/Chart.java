/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

/**
 * @author Prem
 *
 */
public interface Chart {
	
	/**
	 * 
	 * @param session
	 * @param pw
	 * @return
	 */
	public String generateChart(HttpSession session, PrintWriter pw);
	
}
