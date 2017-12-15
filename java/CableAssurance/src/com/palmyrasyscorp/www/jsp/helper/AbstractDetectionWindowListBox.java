/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Prem
 *
 */
public class AbstractDetectionWindowListBox extends AbstractListBox {

	public static final String TEN_MIN = "10 min";
	public static final String TWENTY_MIN = "20 min";
	public static final String THIRTY_MIN = "30 min";
	public static final String FORTY_MIN = "40 min";
	public static final String FIFTY_MIN = "50 min";
	public static final String SIXTY_MIN = "60 min";

	private LinkedList m_optList;
	
	/**
	 * 
	 *
	 */
	protected AbstractDetectionWindowListBox() {
		super();
		
		m_optList = new LinkedList();
		m_optList.add(TEN_MIN);
		m_optList.add(TWENTY_MIN);
		m_optList.add(THIRTY_MIN);
		m_optList.add(FORTY_MIN);
		m_optList.add(FIFTY_MIN);
		m_optList.add(SIXTY_MIN);
	}

	/**
	 * 
	 * @return
	 */
	public List getOptionList() {
		return m_optList;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public int convertToSeconds(String opt) {
		int ret;

		if (opt.equals(TEN_MIN)) {
			ret = 10*60;
		} else if (opt.equals(TWENTY_MIN)) {
			ret = 20*60;			
		} else if (opt.equals(THIRTY_MIN)) {
			ret = 30*60;			
		} else if (opt.equals(FORTY_MIN)) {
			ret = 40*60;			
		} else if (opt.equals(FIFTY_MIN)) {
			ret = 50*60;			
		} else if (opt.equals(SIXTY_MIN)) {
			ret = 60*60;			
		} else {
			ret = 20*60;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param opt
	 * @return
	 */
	public String convertToStrSeconds(String opt) {
		int sec = convertToSeconds(opt);
		StringBuffer s = new StringBuffer();
		s.append(sec);
		return (s.toString());
	}
	
	/**
	 * 
	 * @param seconds
	 * @return
	 */
	public String convertToOption(int sec) {
		String ret;
		
		if (sec == 600) {
			ret = TEN_MIN;			
		} else if (sec == 1200) {
			ret = TWENTY_MIN;			
		} else if (sec == 1800) {
			ret = THIRTY_MIN;			
		} else if (sec == 2400) {
			ret = FORTY_MIN;			
		} else if (sec == 3000) {
			ret = FIFTY_MIN;			
		} else if (sec == 3600) {
			ret = SIXTY_MIN;			
		} else {
			ret = TWENTY_MIN;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String convertToOption(String sec) {
		int iv = 0;
		
		try {
			iv = Integer.parseInt(sec); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (convertToOption(iv));
	}


}
