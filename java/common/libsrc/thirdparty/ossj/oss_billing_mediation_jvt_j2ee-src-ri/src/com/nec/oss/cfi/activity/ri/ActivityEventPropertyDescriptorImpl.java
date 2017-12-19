

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

import javax.oss.cfi.activity.ActivityEvent;
import javax.oss.cfi.activity.ActivityEventPropertyDescriptor;


/**
 * An implementation of Activty Event Property Descriptor interface.
 * This class specifies the filterable properties of the an activity
 * related event.
 * The clients should use these properties to filter out and receive the
 * activity creation event meant for them on the system's JVTEventTopic.
 *
 * It is expected that if an implementation supports separate Destinations
 * for Activities (i.e. not using JVTEventTopic) to disseminate notifciations
 * to the clients.

 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivityEventPropertyDescriptorImpl
extends com.nec.oss.ri.EventPropertyDescriptorImpl
implements ActivityEventPropertyDescriptor
{

	/**
	 * Property names
     */
    private static String [] mypropNames =
    {
        ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME
    };

    private static String [] mypropTypes =
    {
        ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_TYPE
    };


    /**
	 * Constructor - sets the event type and properties supported
     * by this descriptor
     */
	public ActivityEventPropertyDescriptorImpl()
	{
    	setEventType(ActivityEvent.class.getName());
		setPropertyNames(mypropNames);
		setPropertyTypes(mypropTypes);
	}
}
