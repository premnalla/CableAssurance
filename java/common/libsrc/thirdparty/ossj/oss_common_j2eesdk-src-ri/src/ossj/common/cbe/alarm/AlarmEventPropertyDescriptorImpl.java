/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmEvent;
import javax.oss.cbe.alarm.AlarmEventPropertyDescriptor;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmEventPropertyDescriptor</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AlarmEventPropertyDescriptor
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmEventPropertyDescriptorImpl
extends ossj.common.EventPropertyDescriptorImpl
implements AlarmEventPropertyDescriptor
{ 

    /**
     * Constructs a new AlarmEventPropertyDescriptorImpl with empty attributes.
     * 
     */

    public AlarmEventPropertyDescriptorImpl() {
        super(AlarmEvent.class.getName(),
            new String[] {
                AlarmEventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME ,AlarmEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME ,AlarmEventPropertyDescriptor.ALARM_VALUE_SPECIFIC_PROBLEM_PROP_NAME ,AlarmEventPropertyDescriptor.ALARM_VALUE_PROBABLE_CAUSE_PROP_NAME ,AlarmEventPropertyDescriptor.ALARM_VALUE_PERCEIVED_SEVERITY_PROP_NAME ,AlarmEventPropertyDescriptor.ALARM_VALUE_ALARM_TYPE_PROP_NAME },
            new String[] {
                AlarmEventPropertyDescriptor.OSS_APPLICATION_DN_PROP_TYPE ,AlarmEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_TYPE ,AlarmEventPropertyDescriptor.ALARM_VALUE_SPECIFIC_PROBLEM_PROP_TYPE ,AlarmEventPropertyDescriptor.ALARM_VALUE_PROBABLE_CAUSE_PROP_TYPE ,AlarmEventPropertyDescriptor.ALARM_VALUE_PERCEIVED_SEVERITY_PROP_TYPE ,AlarmEventPropertyDescriptor.ALARM_VALUE_ALARM_TYPE_PROP_TYPE },
            new AlarmEventImpl());
    }

    /**
    * Constructs a new AlarmEventPropertyDescriptorImpl using the given attributes.
    *
    */
    public AlarmEventPropertyDescriptorImpl(String type, String[] propertyNames,
        String[] propertyTypes, javax.oss.Event dummyEvent)
        throws IllegalArgumentException {
        super(type, propertyNames, propertyTypes, dummyEvent);
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.alarm.AlarmEvent makeAlarmEvent(String type){
        if(type.equals("javax.oss.cbe.alarm.AlarmEvent") || type.equals("ossj.common.cbe.alarm.AlarmEventImpl")) {
            return new AlarmEventImpl();
        } else {
            return null;
        }
    }



}
