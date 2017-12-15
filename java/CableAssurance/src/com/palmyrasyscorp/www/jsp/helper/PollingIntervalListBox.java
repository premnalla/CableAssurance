/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

/**
 * @author Prem
 *
 */
public class PollingIntervalListBox extends AbstractPollingIntervalListBox {

	private static PollingIntervalListBox m_instance;
	
	/**
	 * 
	 *
	 */
	private PollingIntervalListBox() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static PollingIntervalListBox getInstance() {
		if (m_instance == null) {
			m_instance = new PollingIntervalListBox();
		}
		return (m_instance);
	}
}
