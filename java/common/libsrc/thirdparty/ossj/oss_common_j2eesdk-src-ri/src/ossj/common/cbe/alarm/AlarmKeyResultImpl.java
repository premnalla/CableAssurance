/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AlarmKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements AlarmKeyResult
{ 

    /**
     * Constructs a new AlarmKeyResultImpl with empty attributes.
     * 
     */

    public AlarmKeyResultImpl() {
        super();
    }




    /**
     * Returns this AlarmKeyResultImpl's alarmKey
     * 
     * @return the alarmKey
     * 
    */

    public javax.oss.cbe.alarm.AlarmKey getAlarmKey() {
        // Use the based MEK method
        return (javax.oss.cbe.alarm.AlarmKey)getManagedEntityKey();
    }

}
