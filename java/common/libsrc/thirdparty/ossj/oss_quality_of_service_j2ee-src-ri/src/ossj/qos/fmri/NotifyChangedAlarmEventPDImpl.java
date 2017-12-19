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
import javax.oss.fm.monitor.NotifyChangedAlarmEventPropertyDescriptor;
import javax.oss.fm.monitor.NotifyChangedAlarmEvent;

/**
 * NotifyChangedAlarmEventPDImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyChangedAlarmEventPDImpl extends BaseAlarmEventPD  
  implements NotifyChangedAlarmEventPropertyDescriptor {
    
    
    /** Creates new NotifyChangedAlarmEventPDImpl */
    public NotifyChangedAlarmEventPDImpl() {
        super();
    }
    
    public String getEventType() {
        return NotifyChangedAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE;
    }

    public Event makeEvent() {
        return makeNotifyChangedAlarmEvent();
    }

    public IRPEvent makeIRPEvent() {
        return makeNotifyChangedAlarmEvent();
    }
    
    public AlarmEvent makeAlarmEvent() {
        return makeNotifyChangedAlarmEvent();
    }
    
    public IRPEvent buildAlarmEvent ( AlarmValue alarm ) {
        NotifyChangedAlarmEventImpl event = new NotifyChangedAlarmEventImpl( alarm );
        return event;
    }

    public NotifyChangedAlarmEvent makeNotifyChangedAlarmEvent() {
        NotifyChangedAlarmEventImpl event = new NotifyChangedAlarmEventImpl();
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
        if (event instanceof NotifyChangedAlarmEvent) {
            objMsg = super.makeObjectMessage( event, properties, session );
        }
        else {
            throw new IllegalArgumentException ( "Invalid EventType entered." );
        }
        return objMsg;
    }
    
    public TextMessage makeTextMessage( Event event, java.util.HashMap properties, javax.jms.Session session)
      throws IllegalArgumentException {
        // text messages are not supported in this version of the API.
        return null;
    }
}