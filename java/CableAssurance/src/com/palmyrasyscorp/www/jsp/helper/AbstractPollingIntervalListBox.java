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
public abstract class AbstractPollingIntervalListBox extends AbstractListBox {

	public static final String FIVE_MIN = "5 min";
	public static final String TEN_MIN = "10 min";
	public static final String FIFTEEN_MIN = "15 min";
	public static final String TWENTY_MIN = "20 min";
	public static final String THIRTY_MIN = "30 min";
	public static final String FORTYFIVE_MIN = "45 min";
	public static final String SIXTY_MIN = "1 hr";
	public static final String NINETY_MIN = "1 hr 30 min";
	public static final String ONE_HUNDRED_TWENTY_MIN = "2 hrs";
	public static final String TWO_HUNDRED_FORTY_MIN = "4 hrs";

	private LinkedList m_optList;
	
	/**
	 * 
	 *
	 */
	protected AbstractPollingIntervalListBox() {
		super();
		
		m_optList = new LinkedList();
		m_optList.add(FIVE_MIN);
		m_optList.add(TEN_MIN);
		m_optList.add(FIFTEEN_MIN);
		m_optList.add(TWENTY_MIN);
		m_optList.add(THIRTY_MIN);
		m_optList.add(FORTYFIVE_MIN);
		m_optList.add(SIXTY_MIN);
		m_optList.add(NINETY_MIN);
		m_optList.add(ONE_HUNDRED_TWENTY_MIN);
		m_optList.add(TWO_HUNDRED_FORTY_MIN);
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

		if (opt.equals(FIVE_MIN)) {
			ret = 5 * 60;
		} else if (opt.equals(TEN_MIN)) {
			ret = 10 * 60;			
		} else if (opt.equals(TEN_MIN)) {
			ret = 10 * 60;			
		} else if (opt.equals(FIFTEEN_MIN)) {
			ret = 15 * 60;			
		} else if (opt.equals(TWENTY_MIN)) {
			ret = 20 * 60;			
		} else if (opt.equals(THIRTY_MIN)) {
			ret = 30 * 60;			
		} else if (opt.equals(FORTYFIVE_MIN)) {
			ret = 45 * 60;			
		} else if (opt.equals(SIXTY_MIN)) {
			ret = 60 * 60;			
		} else if (opt.equals(NINETY_MIN)) {
			ret = 90 * 60;			
		} else if (opt.equals(ONE_HUNDRED_TWENTY_MIN)) {
			ret = 120 * 60;			
		} else if (opt.equals(TWO_HUNDRED_FORTY_MIN)) {
			ret = 240 * 60;			
		} else {
			ret = 15 * 60;
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
	public String convertToOption(int seconds) {
		String ret;
		
		if (seconds == 300) {
			ret = FIVE_MIN;			
		} else if (seconds == 600) {
			ret = TEN_MIN;			
		} else if (seconds == 900) {
			ret = FIFTEEN_MIN;			
		} else if (seconds == 900) {
			ret = TWENTY_MIN;			
		} else if (seconds == 1800) {
			ret = THIRTY_MIN;			
		} else if (seconds == 2700) {
			ret = FORTYFIVE_MIN;			
		} else if (seconds == 3600) {
			ret = SIXTY_MIN;			
		} else if (seconds == 5400) {
			ret = NINETY_MIN;			
		} else if (seconds == 7200) {
			ret = ONE_HUNDRED_TWENTY_MIN;			
		} else if (seconds == 14400) {
			ret = TWO_HUNDRED_FORTY_MIN;			
		} else {
			ret = FIFTEEN_MIN;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String convertToOption(String value) {
		int iv = 0;
		
		try {
			iv = Integer.parseInt(value); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (convertToOption(iv));
	}
}
