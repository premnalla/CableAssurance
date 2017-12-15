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
public class AbstractHourOfDayListBox extends AbstractListBox {

	public static final String ZERO_HRS = "00 hrs";
	public static final String ONE_HRS = "01 hrs";
	public static final String TWO_HRS = "02 hrs";
	public static final String THREE_HRS = "03 hrs";
	public static final String FOUR_HRS = "04 hrs";
	public static final String FIVE_HRS = "05 hrs";
	public static final String SIX_HRS = "06 hrs";
	public static final String SEVEN_HRS = "07 hrs";
	public static final String EIGHT_HRS = "08 hrs";
	public static final String NINE_HRS = "09 hrs";
	public static final String TEN_HRS = "10 hrs";
	public static final String ELEVEN_HRS = "11 hrs";
	public static final String TWELVE_HRS = "12 hrs";
	public static final String THIRTEEN_HRS = "13 hrs";
	public static final String FOURTEEN_HRS = "14 hrs";
	public static final String FIFTEEN_HRS = "15 hrs";
	public static final String SIXTEEN_HRS = "16 hrs";
	public static final String SEVENTEEN_HRS = "17 hrs";
	public static final String EIGHTEEN_HRS = "18 hrs";
	public static final String NINETEEN_HRS = "19 hrs";
	public static final String TWENTY_HRS = "20 hrs";
	public static final String TWENTYONE_HRS = "21 hrs";
	public static final String TWENTYTWO_HRS = "22 hrs";
	public static final String TWENTYTHREE_HRS = "23 hrs";

	private LinkedList m_optList;
	
	/**
	 * 
	 *
	 */
	protected AbstractHourOfDayListBox() {
		super();
		
		m_optList = new LinkedList();
		m_optList.add(ZERO_HRS);
		m_optList.add(ONE_HRS);
		m_optList.add(TWO_HRS);
		m_optList.add(THREE_HRS);
		m_optList.add(FOUR_HRS);
		m_optList.add(FIVE_HRS);
		m_optList.add(SIX_HRS);
		m_optList.add(SEVEN_HRS);
		m_optList.add(EIGHT_HRS);
		m_optList.add(NINE_HRS);
		m_optList.add(TEN_HRS);
		m_optList.add(ELEVEN_HRS);
		m_optList.add(TWELVE_HRS);
		m_optList.add(THIRTEEN_HRS);
		m_optList.add(FOURTEEN_HRS);
		m_optList.add(FIFTEEN_HRS);
		m_optList.add(SIXTEEN_HRS);
		m_optList.add(SEVENTEEN_HRS);
		m_optList.add(EIGHTEEN_HRS);
		m_optList.add(NINETEEN_HRS);
		m_optList.add(TWENTY_HRS);
		m_optList.add(TWENTYONE_HRS);
		m_optList.add(TWENTYTWO_HRS);
		m_optList.add(TWENTYTHREE_HRS);
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
	public int convertToHour(String opt) {
		int ret;

		if (opt.equals(ZERO_HRS)) {
			ret = 0;
		} else if (opt.equals(ONE_HRS)) {
			ret = 1;			
		} else if (opt.equals(TWO_HRS)) {
			ret = 2;			
		} else if (opt.equals(THREE_HRS)) {
			ret = 3;			
		} else if (opt.equals(FOUR_HRS)) {
			ret = 4;			
		} else if (opt.equals(FIVE_HRS)) {
			ret = 5;			
		} else if (opt.equals(SIX_HRS)) {
			ret = 6;			
		} else if (opt.equals(SEVEN_HRS)) {
			ret = 7;			
		} else if (opt.equals(EIGHT_HRS)) {
			ret = 8;			
		} else if (opt.equals(NINE_HRS)) {
			ret = 9;			
		} else if (opt.equals(TEN_HRS)) {
			ret = 10;			
		} else if (opt.equals(ELEVEN_HRS)) {
			ret = 11;			
		} else if (opt.equals(TWELVE_HRS)) {
			ret = 12;			
		} else if (opt.equals(THIRTEEN_HRS)) {
			ret = 13;			
		} else if (opt.equals(FOURTEEN_HRS)) {
			ret = 14;			
		} else if (opt.equals(FIFTEEN_HRS)) {
			ret = 15;			
		} else if (opt.equals(SIXTEEN_HRS)) {
			ret = 16;			
		} else if (opt.equals(SEVENTEEN_HRS)) {
			ret = 17;			
		} else if (opt.equals(EIGHTEEN_HRS)) {
			ret = 18;			
		} else if (opt.equals(NINETEEN_HRS)) {
			ret = 19;			
		} else if (opt.equals(TWENTY_HRS)) {
			ret = 20;			
		} else if (opt.equals(TWENTYONE_HRS)) {
			ret = 21;			
		} else if (opt.equals(TWENTYTWO_HRS)) {
			ret = 22;			
		} else if (opt.equals(TWENTYTHREE_HRS)) {
			ret = 23;			
		} else {
			ret = 0;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param opt
	 * @return
	 */
	public String convertToStrHour(String opt) {
		int hr = convertToHour(opt);
		StringBuffer s = new StringBuffer();
		s.append(hr);
		return (s.toString());
	}
	
	/**
	 * 
	 * @param seconds
	 * @return
	 */
	public String convertToOption(int hr) {
		String ret;
		
		if (hr == 0) {
			ret = ZERO_HRS;			
		} else if (hr == 1) {
			ret = ONE_HRS;			
		} else if (hr == 2) {
			ret = TWO_HRS;			
		} else if (hr == 3) {
			ret = THREE_HRS;			
		} else if (hr == 4) {
			ret = FOUR_HRS;			
		} else if (hr == 5) {
			ret = FIVE_HRS;			
		} else if (hr == 6) {
			ret = SIX_HRS;			
		} else if (hr == 7) {
			ret = SEVEN_HRS;			
		} else if (hr == 8) {
			ret = EIGHT_HRS;			
		} else if (hr == 9) {
			ret = NINE_HRS;			
		} else if (hr == 10) {
			ret = TEN_HRS;			
		} else if (hr == 11) {
			ret = ELEVEN_HRS;			
		} else if (hr == 12) {
			ret = TWELVE_HRS;			
		} else if (hr == 13) {
			ret = THIRTEEN_HRS;			
		} else if (hr == 14) {
			ret = FOURTEEN_HRS;			
		} else if (hr == 15) {
			ret = FIFTEEN_HRS;			
		} else if (hr == 16) {
			ret = SIXTEEN_HRS;			
		} else if (hr == 17) {
			ret = SEVENTEEN_HRS;			
		} else if (hr == 18) {
			ret = EIGHTEEN_HRS;			
		} else if (hr == 19) {
			ret = NINETEEN_HRS;			
		} else if (hr == 20) {
			ret = TWENTY_HRS;			
		} else if (hr == 21) {
			ret = TWENTYONE_HRS;			
		} else if (hr == 22) {
			ret = TWENTYTWO_HRS;			
		} else if (hr == 23) {
			ret = TWENTYTHREE_HRS;			
		} else {
			ret = ZERO_HRS;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String convertToOption(String hr) {
		int iv = 0;
		
		try {
			iv = Integer.parseInt(hr); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (convertToOption(iv));
	}

}
