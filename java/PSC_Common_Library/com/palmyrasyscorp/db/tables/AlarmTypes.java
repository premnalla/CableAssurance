/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public final class AlarmTypes {

	/**
	 * Not allowed
	 *
	 */
	private AlarmTypes() {
		
	}
	
	public static final short ALARM_TYPE_NULL = 0;
	public static final short ALARM_TYPE_HFC = 1;
	public static final short ALARM_TYPE_MTA = 2;
	public static final short ALARM_TYPE_CMTS = 3;
	public static final short ALARM_TYPE_CMS = 4;
	public static final short ALARM_TYPE_LAST = 4;
	
}
