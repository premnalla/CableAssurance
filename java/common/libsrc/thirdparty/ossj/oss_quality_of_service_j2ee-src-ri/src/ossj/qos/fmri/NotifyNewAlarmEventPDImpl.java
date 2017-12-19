package ossj.qos.fmri;

import javax.oss.Event;
import javax.oss.util.IRPEvent;
import javax.oss.EventPropertyDescriptor;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.AlarmEvent;
import java.util.Hashtable;
import java.util.HashMap;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.oss.fm.monitor.AlarmEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyNewAlarmEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyNewAlarmEvent;

/**
 * NotifyNewAlarmEventPDImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyNewAlarmEventPDImpl extends BaseAlarmEventPD
implements NotifyNewAlarmEventPropertyDescriptor {

    /** Creates new NotifyNewAlarmEventPDImpl */
    public NotifyNewAlarmEventPDImpl() {
        super();
    }

    public String getEventType() {
        return NotifyNewAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE;
    }

    public Event makeEvent() {
        return makeNotifyNewAlarmEvent();
    }

    public IRPEvent makeIRPEvent() {
        return makeNotifyNewAlarmEvent();
    }

    public AlarmEvent makeAlarmEvent() {
        return makeNotifyNewAlarmEvent();
    }

    public IRPEvent buildAlarmEvent ( AlarmValue alarm ) {
        NotifyNewAlarmEventImpl event = new NotifyNewAlarmEventImpl( alarm );
        event.setEventTime( alarm.getAlarmRaisedTime() );
        return event;
    }

    public NotifyNewAlarmEvent makeNotifyNewAlarmEvent() {
        NotifyNewAlarmEventImpl event = new NotifyNewAlarmEventImpl();
        return event;
    }

    protected void setDefaultMessageProperties( ObjectMessage msg, IRPEvent event )
    throws javax.jms.JMSException {
        super.setDefaultMessageProperties( msg, event );
        msg.setStringProperty( OSS_EVENT_TYPE_PROP_NAME, EVENT_TYPE_VALUE );
        return;
    }

    public ObjectMessage makeObjectMessage( Event event, java.util.HashMap properties,
    Session session ) throws IllegalArgumentException {
        ObjectMessage objMsg = null;
        if (event instanceof NotifyNewAlarmEvent)
        {
            objMsg = super.makeObjectMessage( event, properties, session );
        }
        else {
            throw new IllegalArgumentException( "Invalid event entered.");
        }
        return objMsg;
    }

    public TextMessage makeTextMessage( Event event, java.util.Hashtable properties, javax.jms.Session session)
    throws IllegalArgumentException {
        // text messages are not supported in this version of the API.
        return null;
    }

}