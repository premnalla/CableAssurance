package ossj.qos.fmri;

/**
 * AlarmListRebuiltEventPDImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
import javax.oss.Event;
import javax.oss.util.IRPEvent;
import javax.oss.fm.monitor.NotifyAlarmListRebuiltEvent;
import javax.oss.fm.monitor.NotifyAlarmListRebuiltEventPropertyDescriptor;
import java.util.Hashtable;
import java.util.HashMap;
import javax.jms.ObjectMessage;
import javax.jms.JMSException;
import javax.jms.TopicSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.oss.fm.monitor.AlarmValue;


public class NotifyAlarmListRebuiltEventPDImpl extends BaseEventPropertyDescriptorImpl
implements NotifyAlarmListRebuiltEventPropertyDescriptor {

    /**
     * Creates new AlarmListRebuiltEventPDImpl
     */
    public NotifyAlarmListRebuiltEventPDImpl() {
        super();
    }

    public String getEventType() {
        return NotifyAlarmListRebuiltEventPropertyDescriptor.EVENT_TYPE_VALUE;
    }
    
    public Event makeEvent() {
        return makeNotifyAlarmListRebuiltEvent();
    }
    
    public IRPEvent makeIRPEvent() {
        return makeNotifyAlarmListRebuiltEvent();
    }

    public NotifyAlarmListRebuiltEvent makeNotifyAlarmListRebuiltEvent() {
        NotifyAlarmListRebuiltEventImpl event = new NotifyAlarmListRebuiltEventImpl();
        return event;
    }
    
    protected void setDefaultMessageProperties( ObjectMessage msg, IRPEvent event )
    throws javax.jms.JMSException {
        super.setDefaultMessageProperties( msg, event );
        msg.setStringProperty( OSS_EVENT_TYPE_PROP_NAME, EVENT_TYPE_VALUE );
        return;
    }

    public ObjectMessage makeObjectMessage( Event event,java.util.HashMap properties, Session session ) 
      throws IllegalArgumentException {

        ObjectMessage objMsg = null;
        if (event instanceof NotifyAlarmListRebuiltEvent)
        {
            objMsg = super.makeObjectMessage( event, properties, session );
        }
        else {
            throw new IllegalArgumentException ( "Invalid EventType entered." );
        }
        return objMsg;
    }

    public TextMessage makeTextMessage( Event event, java.util.Hashtable properties, Session session )
      throws IllegalArgumentException {
        // text messages are not supported in this version of the API.
        return null;
    }
}