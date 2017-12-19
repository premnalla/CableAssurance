
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

import javax.oss.cfi.activity.ActivitySuspendEvent;
import javax.oss.cfi.activity.ActivitySuspendEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivitySuspendEventPropertyDescriptorImpl
extends ActivityEventPropertyDescriptorImpl
implements ActivitySuspendEventPropertyDescriptor
{
    /**
     * Constructor - sets the event type associated with this event descriptor
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
	public ActivitySuspendEventPropertyDescriptorImpl()
	{
		setEventType(ActivitySuspendEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE);
	}

    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new ActivitySuspendEventImpl();
    }
    
}
