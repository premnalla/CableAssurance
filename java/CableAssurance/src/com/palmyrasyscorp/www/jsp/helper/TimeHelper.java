/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.math.BigInteger;

/**
 * @author Prem
 *
 */
public class TimeHelper {

	/**
	 * 
	 *
	 */
	private TimeHelper() {
		
	}
	
	public static String secondsToHMS(BigInteger seconds) {
		String ret = null;
		
		StringBuffer sb = new StringBuffer();
		long sec = seconds.longValue();
		long hr = sec/(3600);
		long remainderSec = sec-(hr*3600);
		long m = remainderSec/60;
		long s = remainderSec-(m*60);
		sb.append(hr).append("hrs ");
		sb.append(m).append("min ");
		sb.append(s).append("sec");
		ret = sb.toString();
		
		return (ret);
	}
}
