
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

import javax.oss.cfi.activity.ActivityReportDataEvent;
import javax.oss.cfi.activity.ActivityReportDataEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 *
 */
public class ActivityReportDataEventPropertyDescriptorImpl
extends ActivityEventPropertyDescriptorImpl
implements ActivityReportDataEventPropertyDescriptor
{

    /**
     * Get the name of the event type
     * associated with that descriptor.
     * 
     * @return the class name of the Event interface
     * associated with that Event descriptor.
     */
    public String getEventType()
    {
        return ActivityReportDataEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE;
    }

    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new ActivityReportDataEventImpl();
    }
}
