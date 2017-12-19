package ossj.qos.fmri;

import javax.oss.fm.monitor.NotifyChangedAlarmEvent;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.NotifyChangedAlarmEventPropertyDescriptor;

import java.util.TreeMap;

/**
 * NotifyChangedAlarmEvent
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class NotifyChangedAlarmEventImpl extends BaseAlarmEvent implements NotifyChangedAlarmEvent{
  
    /**
     * Default constructor.
     */
    public NotifyChangedAlarmEventImpl() {
        super();
    }
    
   /** Creates new NotifyChangedAlarmEvent */
    public NotifyChangedAlarmEventImpl(AlarmValue alarm) {
        super(alarm);
         if ( alarm.isPopulated( AlarmValue.ALARM_CHANGED_TIME ) ) {
            setEventTime( alarm.getAlarmChangedTime() );
        }
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        return (NotifyChangedAlarmEventImpl)super.clone();
    }
}