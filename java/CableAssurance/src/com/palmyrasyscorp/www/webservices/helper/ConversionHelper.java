/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.util.GregorianCalendar;

import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;

/**
 * @author Prem
 *
 */
public class ConversionHelper {

	/**
	 * 
	 *
	 */
	private ConversionHelper() {
		
	}
	
	/**
	 * 
	 * @param tm
	 * @return
	 */
	public static long inputTimeToSec(InputTimeT tm) {
		long ret = 0L;
		if (tm.getYear() < 1970) {
			return (ret);
		}
		// System.out.println("Yr=" + tm.getYear() + "; Mo=" + tm.getMonthOfYear()
		// 		+ "; Day=" + tm.getDayOfMonth() + "; Hr=" + tm.getHourOfDay()
		// 		+ "; Min=" + tm.getMinuteOfHour());
		GregorianCalendar tmCal = new GregorianCalendar((int) tm
				.getYear(), (int) tm.getMonthOfYear(), (int) tm
				.getDayOfMonth(), (int) tm.getHourOfDay(),
				(int) tm.getMinuteOfHour());
		// System.out.println("TimeMillis=" + tmCal.getTimeInMillis());
		return (tmCal.getTimeInMillis() / 1000);
	}
	
	/**
	 * 
	 * @param total
	 * @param num
	 * @return
	 */
	public static float getPercentToOneDecDigit(short total, short num) {
		return (getPercentToOneDecDigit((int)total, (int)num));
	}
	
	/**
	 * 
	 * @param total
	 * @param num
	 * @return
	 */
	public static float getPercentToOneDecDigit(int total, int num) {
		float ret = 0.0f;
		
		if (total > 0) {
			ret = (float) ((num * 1000)/total);
			// System.out.println("float="+ret);
			int i = (int) ret;
			// System.out.println("int="+i);
			ret = ((float)i)/10;
			// System.out.println("float="+ret);
		}
		
		return (ret);
	}
		
}
