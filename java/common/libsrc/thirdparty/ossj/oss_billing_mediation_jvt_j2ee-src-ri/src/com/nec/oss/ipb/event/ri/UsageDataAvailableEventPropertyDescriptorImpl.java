package com.nec.oss.ipb.event.ri;

/**
 * Title:        JSR130 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports
 */
import javax.oss.cfi.activity.ActivityReportAvailableEvent;
import javax.oss.ipb.event.UsageDataAvailableEventPropertyDescriptor;

/**
 * Base Spec imports
 */
import javax.oss.Event;

/**
 * An implementation of UsageData Available Event Property Descriptor interface.
 * This class documents and specifies the filterable prooperties of
 * a UsageDataAvailableEvent notification.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class UsageDataAvailableEventPropertyDescriptorImpl
extends com.nec.oss.cfi.activity.ri.ActivityEventPropertyDescriptorImpl
implements UsageDataAvailableEventPropertyDescriptor
{

    private static String [] mypropNames =
    {
        UsageDataAvailableEventPropertyDescriptor.AVAILABLE_SINCE_TIME_PROP_NAME
    };

    private static String [] mypropTypes =
    {
        UsageDataAvailableEventPropertyDescriptor.AVAILABLE_SINCE_TIME_PROP_TYPE
    };


    /**
     * Constructor - sets the event type and properties.
     * 
     */
	public UsageDataAvailableEventPropertyDescriptorImpl()
	{
		setEventType(UsageDataAvailableEventPropertyDescriptor.OSSJ_EVENT_TYPE_VALUE);
		setPropertyTypes(mypropTypes);
        setPropertyNames(mypropNames);
	}


    /**
     * Factory for Event associated with
     * UsageDataAvailableEvent Descriptor.
     * 
     * @return An implementation of the UsageDataAvailableEvent interface.
     */
    public Event makeEvent()
    {
        return new UsageDataAvailableEventImpl();
    }
}
