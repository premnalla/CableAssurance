/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public final class AlarmStates {

	public static final Integer UNKNOWN = new Integer(0);
	public static final Integer SOAKING = new Integer(1);
	public static final Integer SOAK_COMPLETE = new Integer(2);
	public static final Integer TICKETING = new Integer(3);
	public static final Integer TICKETED = new Integer(4);
	public static final Integer TICKETING_FAILED = new Integer(5);
	public static final Integer CLEARED = new Integer(6);
	public static final Integer COINCIDENTAL = new Integer(7);
	public static final Integer RESTART_DISCARD = new Integer(8);
	
	/**
	 * 
	 *
	 */
	private AlarmStates() {
		
	}
}
