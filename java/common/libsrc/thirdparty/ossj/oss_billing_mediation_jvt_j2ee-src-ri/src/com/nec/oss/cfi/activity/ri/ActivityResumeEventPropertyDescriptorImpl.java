
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

import javax.oss.cfi.activity.ActivityResumeEvent;
import javax.oss.cfi.activity.ActivityResumeEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivityResumeEventPropertyDescriptorImpl
extends ActivityEventPropertyDescriptorImpl
implements ActivityResumeEventPropertyDescriptor
{

    /**
     * Constructor - sets the type of event associated by this event descriptor
     */
	public ActivityResumeEventPropertyDescriptorImpl()
	{
        setEventType(ActivityResumeEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE);
	}

    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new ActivityResumeEventImpl();
    }
}
