
package com.nec.oss.cfi.activity.ri;

/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports
 */
import javax.oss.Event;

import javax.oss.cfi.activity.ActivityReportAvailableEvent;
import javax.oss.cfi.activity.ActivityReportAvailableEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 * This class represents the filterable JMS message properties
 * based on which the clients can selectively receive
 * availability notifications regarding the interesting
 * Activity Reports.
 *
 * This is just base example implementation. It is expected that
 * the individual API would need to specify the necessary filterable
 * properties, if any.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivityReportAvailableEventPropertyDescriptorImpl
extends ActivityEventPropertyDescriptorImpl
implements ActivityReportAvailableEventPropertyDescriptor
{

    /**
     * Constructor - sets the event type associated with this event
     * descriptor.
     */
	public ActivityReportAvailableEventPropertyDescriptorImpl()
    {
       setEventType(ActivityReportAvailableEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE);
    }

    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new ActivityReportAvailableEventImpl();
    }
    
}
