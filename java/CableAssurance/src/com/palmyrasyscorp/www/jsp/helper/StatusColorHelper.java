/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasyscorp.db.tables.AlertLevel;

/**
 * @author Prem
 *
 */
public final class StatusColorHelper {

	/**
	 * 
	 *
	 */
	private StatusColorHelper() {
		
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static final String getStatusImageUrl(StatusColorT s) {
		return (getStatusImageUrl(s.toString()));
	}
	
	/**
	 * 
	 */
	public static final String getStatusImageUrl(String statucColor) {
		String ret = null;
		
		if (statucColor.equalsIgnoreCase(StatusColorT._Gray)) {
			ret = "../images/black_ball.gif";
		} else if (statucColor.equalsIgnoreCase(StatusColorT._Green)) {
			ret = "../images/green_ball.gif";
		} else if (statucColor.equalsIgnoreCase(StatusColorT._Yellow)) {
			ret = "../images/yellow_ball.gif";
		} else if (statucColor.equalsIgnoreCase(StatusColorT._Orange)) {
			ret = "../images/orange_ball.gif";
		} else if (statucColor.equalsIgnoreCase(StatusColorT._Red)) {
			ret = "../images/red_ball.gif";
		} else {
			ret = "../images/black_ball.gif";
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param alertLevel
	 * @return
	 */
	public static final StatusColorT AlertLevelToStatusColor(AlertLevel al) {
		StatusColorT ret = null;
		
		switch(al.getAlertLevel()) {
		case 0:
			ret = StatusColorT.Gray;
			break;
		case 1:
			ret = StatusColorT.Green;
			break;
		case 2:
			ret = StatusColorT.Gray;
			break;
		case 3:
			ret = StatusColorT.Yellow;
			break;
		case 4:
			ret = StatusColorT.Orange;
			break;
		case 5:
			ret = StatusColorT.Red;
			break;
		default:
			ret = StatusColorT.Gray;
			break;
		}
		
		
		
		return (ret);
	}
}
