/*
 
 *
 */

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
import javax.oss.fm.monitor.NotifyAckStateChangedEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyAckStateChangedEvent;
import javax.oss.fm.monitor.AlarmAckState;

/**
 * NotifyAckStateChangedEventPDImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyAckStateChangedEventPDImpl extends BaseAlarmEventPD
implements NotifyAckStateChangedEventPropertyDescriptor {

    /** Creates new NotifyAckStateChangedEventPDImpl */
    public NotifyAckStateChangedEventPDImpl() {
        super();
    }
    
    public String getEventType() {
        return NotifyAckStateChangedEventPropertyDescriptor.EVENT_TYPE_VALUE;
    }

    public Event makeEvent() {
        return makeNotifyAckStateChangedEvent();
    }
    
    public AlarmEvent makeAlarmEvent() {
        return makeNotifyAckStateChangedEvent();
    }
    
    public IRPEvent makeIRPEvent() {
        return makeNotifyAckStateChangedEvent();
    }
    
    public IRPEvent buildAlarmEvent ( AlarmValue alarm ) {
        NotifyAckStateChangedEventImpl event = new NotifyAckStateChangedEventImpl( alarm );
        return event;
    }

    public NotifyAckStateChangedEvent makeNotifyAckStateChangedEvent() {
        NotifyAckStateChangedEventImpl event = new NotifyAckStateChangedEventImpl();
        return event;
    }
    
    protected void setDefaultMessageProperties( ObjectMessage msg, IRPEvent event )
    throws javax.jms.JMSException {
        super.setDefaultMessageProperties( msg, event );
        msg.setStringProperty( OSS_EVENT_TYPE_PROP_NAME, EVENT_TYPE_VALUE );
        return;
    }

    public ObjectMessage makeObjectMessage( IRPEvent event, java.util.HashMap properties,
    Session session ) {
        ObjectMessage objMsg = null;
        if (event instanceof NotifyAckStateChangedEvent)
        {
            objMsg = super.makeObjectMessage( event, properties, session );
        }
        else {
            throw new IllegalArgumentException( "Invalid event entered.");
        }
        return objMsg;
    } 

    public TextMessage makeTextMessage( Event event, HashMap properties, Session session) 
      throws IllegalArgumentException {
        // text messages are not supported in this version of the API.
        return null;
    }
    
}