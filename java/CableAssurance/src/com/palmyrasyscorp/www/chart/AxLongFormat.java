/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;

/**
 * @author Prem
 * 
 */
public class AxLongFormat extends DecimalFormat {

	/**
	 * 
	 * 
	 */
	public AxLongFormat() {
		super("0");
	}

	/**
	 * 
	 * @param p
	 */
	public AxLongFormat(String p) {
		super(p);
	}

	/**
	 * 
	 * @param p
	 * @param s
	 */
	public AxLongFormat(String p, DecimalFormatSymbols s) {
		super(p, s);
	}

	/**
	 * 
	 */
	public StringBuffer format(double number, StringBuffer result,
			FieldPosition fieldPosition) {
		return super.format((int) number, result, fieldPosition);
	}

}
