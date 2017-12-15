/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

/**
 * @author Prem
 *
 */
public class HourOfDayListBox extends AbstractHourOfDayListBox {

	private static HourOfDayListBox m_instance;
	
	/**
	 * 
	 *
	 */
	private HourOfDayListBox() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static HourOfDayListBox getInstance() {
		if (m_instance == null) {
			m_instance = new HourOfDayListBox();
		}
		return (m_instance);
	}

}
