/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import com.palmyrasys.www.webservices.CableAssurance.*;

/**
 * @author Prem
 *
 */
public final class MtaAlarmConfigHelper {

	/**
	 * 
	 *
	 */
	private MtaAlarmConfigHelper() {
		
	}
	
	public static AlarmTypeConfigT getAlarmTypeConfig(String alarmType, 
			MtaAlarmConfigT mtaAlarmCfg) {
		AlarmTypeConfigT ret = null;
		
		AlarmTypeConfigT[] array = mtaAlarmCfg.getAlarmTypes();
		for (int i=0; array != null && i<array.length; i++) {
			if (alarmType.equals(array[i].getAlarmType())) {
				ret = array[i];
				break;
			}
		}
		
		return (ret);
	}
	
}
