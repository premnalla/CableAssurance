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
import javax.oss.fm.monitor.NotifyAlarmCommentsEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyAlarmCommentsEvent;

/**
 * NotifyAlarmCommentsEventPDImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyAlarmCommentsEventPDImpl extends BaseAlarmEventPD
implements NotifyAlarmCommentsEventPropertyDescriptor {

    /** Creates new NotifyAlarmCommentsEventPDImpl */
    public NotifyAlarmCommentsEventPDImpl() {
        super();
    }
    
    public String getEventType() {
        return NotifyAlarmCommentsEventPropertyDescriptor.EVENT_TYPE_VALUE;
    }

    public Event makeEvent() {
        return makeNotifyAlarmCommentsEvent();
    }

    public AlarmEvent makeAlarmEvent() {
        return makeNotifyAlarmCommentsEvent();
    }
    
    public IRPEvent makeIRPEvent() {
        return makeNotifyAlarmCommentsEvent();
    }
    
    public IRPEvent buildAlarmEvent ( AlarmValue alarm ) {
        NotifyAlarmCommentsEventImpl event = new NotifyAlarmCommentsEventImpl( alarm );
        return event;
    }

    public NotifyAlarmCommentsEvent makeNotifyAlarmCommentsEvent() {
        NotifyAlarmCommentsEventImpl event = new NotifyAlarmCommentsEventImpl();
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
        if (event instanceof NotifyAlarmCommentsEvent)
        {
            objMsg = super.makeObjectMessage( event, properties, session );
        }
        else {
            throw new IllegalArgumentException ("Illegal event entered.");
        }
        return objMsg;
    }

    public TextMessage makeTextMessage( Event event, java.util.Hashtable properties, javax.jms.Session session)
      throws IllegalArgumentException {
        // text messages are not supported in this version of the API.
        return null;
    }
   
}