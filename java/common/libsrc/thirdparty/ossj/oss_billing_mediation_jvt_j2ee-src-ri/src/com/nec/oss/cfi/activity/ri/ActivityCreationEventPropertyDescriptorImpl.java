
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

import javax.oss.cfi.activity.ActivityCreationEvent;
import javax.oss.cfi.activity.ActivityCreationEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 *
 * This interface specifies the filterable properties of the event.
 * The clients should use these properties to filter out and receive the
 * activity creation event meant for them on the system's JVTEventTopic.
 *
 * It is expected that if an implementation supports separate Destinations
 * for Activities (i.e. not using JVTEventTopic) to disseminate notifciations
 * to the clients, then the activityValue attribute in
 * ActivityCreationEvent will return that destination type and name.
 * These are retrievable by invoking the respective methods on
 * the (@link javax.oss.cfi.activity.ActivityReportParams} interface.
 *
 * @see javax.oss.cfi.activity.ActivityCreationEvent
 * @see javax.oss.cfi.activity.ActivityValue
 * @see javax.oss.cfi.activity.ActivityReportParams
 * @ossj:eventpropertydescriptor
 *
 */
public class ActivityCreationEventPropertyDescriptorImpl
extends ActivityEventPropertyDescriptorImpl
implements ActivityCreationEventPropertyDescriptor
{

    /**
     * Constructor for the property descriptor. Simply sets the event type
     * associated with this property descriptor.
     */
    public ActivityCreationEventPropertyDescriptorImpl()
    {
        setEventType(ActivityCreationEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE);
    }


    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new ActivityCreationEventImpl();
    }
}
