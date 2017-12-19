
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

import javax.oss.cfi.activity.ActivityRemovalEvent;
import javax.oss.cfi.activity.ActivityRemovalEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 *
 * This class specifies the filterable properties of the event.
 * The clients should use these properties to filter out and receive the
 * activity removal event meant for them on the system's JVTEventTopic.
 *
 * It is expected that if an implementation supports separate Destinations
 * for Activities (i.e. not using JVTEventTopic) to disseminate notifciations
 * to the clients, then the activityValue attribute in
 * ActivityRemovalEvent will return that destination type and name.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivityRemovalEventPropertyDescriptorImpl
extends ActivityEventPropertyDescriptorImpl
implements ActivityRemovalEventPropertyDescriptor
{

    /**
     * Constructor - sets the event type associated with this property
     * descriptor.
     */
    public ActivityRemovalEventPropertyDescriptorImpl()
    {
        setEventType(ActivityRemovalEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE);
    }


    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new ActivityRemovalEventImpl();
    }
}
