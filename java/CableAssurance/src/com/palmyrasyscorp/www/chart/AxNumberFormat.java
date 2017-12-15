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
public class AxNumberFormat extends DecimalFormat {

	public AxNumberFormat() {
		super();
	}

	public AxNumberFormat(String pattern) {
		super(pattern);
	}

	public AxNumberFormat(String pattern, DecimalFormatSymbols symbols) {
		super(pattern, symbols);
	}

	public StringBuffer format(double number, StringBuffer result,
			FieldPosition fieldPosition) {
		
		if (number == 0) {
			result.append("0");
			return result;
		}

		return super.format((int) number, result, fieldPosition);
	}

}
