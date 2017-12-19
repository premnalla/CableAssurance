package ossj.qos.fmri;

import javax.oss.fm.monitor.AlarmEventPropertyDescriptor;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.NotifyAckStateChangedEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyAlarmCommentsEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyAlarmListRebuiltEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyChangedAlarmEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyClearedAlarmEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyNewAlarmEventPropertyDescriptor;

import ossj.qos.fmri.NotifyAckStateChangedEventPDImpl;

import javax.oss.EventPropertyDescriptor;
import javax.oss.util.IRPEvent;
import javax.oss.ManagedEntityValue;

import java.util.HashMap;

/**
 * EventFactory
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class EventFactory extends Object {

    private static HashMap evDescriptors = null;

    static {
        evDescriptors = new HashMap ();

        evDescriptors.put( NotifyAckStateChangedEventPropertyDescriptor.EVENT_TYPE_VALUE,
        new NotifyAckStateChangedEventPDImpl() );

        evDescriptors.put( NotifyAlarmCommentsEventPropertyDescriptor.EVENT_TYPE_VALUE,
        new NotifyAlarmCommentsEventPDImpl() );

        evDescriptors.put( NotifyChangedAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE,
        new NotifyChangedAlarmEventPDImpl() );

        evDescriptors.put( NotifyClearedAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE,
        new NotifyClearedAlarmEventPDImpl() );

        evDescriptors.put( NotifyNewAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE,
        new NotifyNewAlarmEventPDImpl() );

        evDescriptors.put( NotifyAlarmListRebuiltEventPropertyDescriptor.EVENT_TYPE_VALUE,
        new NotifyAlarmListRebuiltEventPDImpl() );
    }


    /** 
     * EventFactory - constructor 
     */
    private EventFactory() {}

    public static IRPEvent makeEvent( String eventType) throws IllegalArgumentException {
        EventPropertyDescriptor descriptor = getPropertyDescriptor( eventType );
        IRPEvent event = (IRPEvent)descriptor.makeEvent();
        return event;
    }

    public static IRPEvent makeEvent ( String eventType, AlarmValue entity )
    throws IllegalArgumentException {
        IRPEvent event = null;
        EventPropertyDescriptor descriptor = getPropertyDescriptor( eventType );
        if ( descriptor instanceof AlarmEventPropertyDescriptor ) {
            event = ( (BaseAlarmEventPD) descriptor).buildAlarmEvent( entity );
        }
        else {
            throw new IllegalArgumentException ( "EventFactory: Invalid event property descriptor type.");
        }
        return event;
    }

    public static String[] getEventTypes() {
        return (String[]) evDescriptors.values().toArray( new String [0]  );
    }

    public static EventPropertyDescriptor getPropertyDescriptor( String eventType ) throws IllegalArgumentException {
        EventPropertyDescriptor descriptor = (EventPropertyDescriptor) evDescriptors.get( eventType );
        if ( descriptor == null ) {
            throw new IllegalArgumentException ( "EventFactory: Invalid event property descriptor type.");
        }
        return descriptor;
    }
}
