/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

/**
 * @author Prem
 *
 */
public class SoakDurationListBox extends AbstractDetectionWindowListBox {

	private static SoakDurationListBox m_instance;
	
	/**
	 * 
	 *
	 */
	private SoakDurationListBox() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static SoakDurationListBox getInstance() {
		if (m_instance == null) {
			m_instance = new SoakDurationListBox();
		}
		return (m_instance);
	}

}
