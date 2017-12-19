
package ossj.qos.fmri;

import javax.oss.Event;
import javax.oss.fm.monitor.AlarmValue;

/**
 * AlarmEventBuilder.java
 * 
 * @author  Audrey Ward
 * @version 1.0
 * 
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public interface AlarmEventBuilder {
    
    public Event buildAlarmEvent( AlarmValue alarm );
}
