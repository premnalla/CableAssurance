/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

/**
 * @author Prem
 *
 */
public class DetectionWindowListBox extends AbstractDetectionWindowListBox {

	private static DetectionWindowListBox m_instance;
	
	/**
	 * 
	 *
	 */
	private DetectionWindowListBox() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static DetectionWindowListBox getInstance() {
		if (m_instance == null) {
			m_instance = new DetectionWindowListBox();
		}
		return (m_instance);
	}

}
