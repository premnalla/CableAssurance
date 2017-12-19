
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
import javax.oss.Event;

import javax.oss.ipb.event.MediationCapabilityChangeEvent;
import javax.oss.ipb.event.MediationCapabilityChangeEventPropertyDescriptor;


/**
 * An implementation of MediationCapabilityChange Event Property
 * Descriptor interface.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class MediationCapabilityChangeEventPropertyDescriptorImpl
extends com.nec.oss.cfi.activity.ri.ActivityEventPropertyDescriptorImpl
implements MediationCapabilityChangeEventPropertyDescriptor
{

    private static String [] mypropNames =
    {
        MediationCapabilityChangeEventPropertyDescriptor.PRODUCER_KEY_PROP_NAME
    };

    private static String [] mypropTypes =
    {
        MediationCapabilityChangeEventPropertyDescriptor.PRODUCER_KEY_PROP_TYPE
    };

    /**
     * Constructor - sets the event type and properties.
     * 
     */
	public MediationCapabilityChangeEventPropertyDescriptorImpl()
	{
		setEventType(MediationCapabilityChangeEvent.class.getName());
		setPropertyTypes(mypropTypes);
		setPropertyNames(mypropNames);
	}


    /**
     * Factory for Event associated with
     * ActivityEvent Descriptor.
     * 
     * @return An implementation of the ActivityEvent interface.
     */
    public Event makeEvent()
    {
        return new MediationCapabilityChangeEventImpl();
    }

}
