package ossj.qos.fmri;

import javax.jms.ObjectMessage;
import java.util.HashMap;
import javax.oss.Event;
import javax.oss.util.IRPEvent;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.AlarmEventPropertyDescriptor;

/**
 * BaseAlarmEventImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public abstract class BaseAlarmEventPD extends BaseEventPropertyDescriptorImpl 
implements AlarmEventPropertyDescriptor {
    
   
    /** Creates new BaseAlarmEventImpl */
    public BaseAlarmEventPD() {
        super();
        addPropertyName( ALARM_TYPE_PROP_NAME );
        addPropertyName( PERCEIVED_SEVERITY_PROP_NAME );
        addPropertyName( PROBABLE_CAUSE_PROP_NAME );
        addPropertyType( ALARM_TYPE_PROP_TYPE );
        addPropertyType( PERCEIVED_SEVERITY_PROP_TYPE );
        addPropertyType( PROBABLE_CAUSE_PROP_TYPE );
    }

    protected void setDefaultMessageProperties( ObjectMessage msg, IRPEvent event )
    throws javax.jms.JMSException {

        //System.out.println("Printing base alarm properties" );
        super.setDefaultMessageProperties( msg, event );
        msg.setStringProperty( ALARM_TYPE_PROP_NAME,
        ((BaseAlarmEvent)event).getAlarmType() );

        msg.setShortProperty( PERCEIVED_SEVERITY_PROP_NAME,
        ((BaseAlarmEvent)event).getPerceivedSeverity() );

        msg.setShortProperty( PROBABLE_CAUSE_PROP_NAME,
        ((BaseAlarmEvent)event).getProbableCause() );
        //System.out.println("Finished printing base alarm properties" );
        return;
    }

    protected void updateMessageProperties( ObjectMessage msg, HashMap properties )
    throws javax.jms.JMSException {
        
        super.updateMessageProperties( msg, properties );

        Object obj = properties.remove( ALARM_TYPE_PROP_NAME );
        if ( obj != null ) {
            msg.setStringProperty( ALARM_TYPE_PROP_NAME, (String) obj );
        }
        obj = properties.remove( PERCEIVED_SEVERITY_PROP_NAME);
        if ( obj != null ) {
            msg.setShortProperty( PERCEIVED_SEVERITY_PROP_NAME,
            ((Short)obj).shortValue());
        }
        obj = properties.remove( PROBABLE_CAUSE_PROP_NAME);
        if ( obj != null ) {
            msg.setShortProperty( PROBABLE_CAUSE_PROP_NAME,
            ((Short)obj).shortValue());
        }
        return;
    }
    
    public abstract IRPEvent buildAlarmEvent ( AlarmValue alarm ); 
}