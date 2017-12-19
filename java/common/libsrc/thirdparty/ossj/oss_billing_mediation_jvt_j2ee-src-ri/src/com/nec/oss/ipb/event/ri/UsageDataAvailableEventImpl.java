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
 * Standard imports
 */
import java.util.*;

/**
 * Spec imports
 */
import javax.oss.ipb.producer.ProducerValue;

/**
 * Base Interface implementation for the UsageData Available events.
 * Raised by an OSS/J application, when UsageData is available.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class UsageDataAvailableEventImpl 
extends com.nec.oss.cfi.activity.ri.ActivityReportAvailableEventImpl
implements javax.oss.ipb.event.UsageDataAvailableEvent
{
	Calendar availableSinceTime=null;

    /**
     * Default constructor -
     * Creates new UsageDataAvailableEvent empty instance.
     */
    public UsageDataAvailableEventImpl()
    {
		// Perform no action
    }

    /**
     * Get time since available 
	 * 
	 * @return Time that the data has been available
	 * @throws java.lang.IllegalStateException if attribute has not been set
     */
    public Calendar getAvailableSince()
        throws java.lang.IllegalStateException,
		javax.oss.UnsupportedAttributeException
    {
        if(availableSinceTime == null)
        {
            throw new java.lang.IllegalStateException(
                "Report availableSinceTime attribute was not set");
        }

        return availableSinceTime;
    }
    

    /**
     * Set time since available
     *
	 * @param date Time that the data has been available
	 * @throws java.lang.IllegalArgumentException if arg is null
     */
    public void setAvailableSince(Calendar date)
        throws java.lang.IllegalArgumentException,
		javax.oss.UnsupportedAttributeException
    {

        if(date == null)
        {
            throw new java.lang.IllegalArgumentException(
                "date Parameter is not valid");
        }

        this.availableSinceTime = (Calendar) date.clone();
    }
    
    /**
     * Make the producer valuee
	 *
	 * @param producerValueType Type of producervalue to create
	 * @throws java.lang.IllegalArgumentException if arg is invalid
     */
    public ProducerValue makeProducerValue(String producerValueType)
        throws java.lang.IllegalArgumentException
    {
		ProducerValue theProducer=null;

		// Check the argument
		if((producerValueType == null) ||
			(producerValueType.length() == 0))
		{
        	throw new java.lang.IllegalArgumentException(
				"producerValueType is invalid:"+producerValueType);
		}

		// Don't have a ProducerValue, so specify that the
		// argument is invalid
	
        throw new java.lang.IllegalArgumentException("No producers exist");
    }

    /**
     * Deep copy of this object.
     *
     * @return deep copy of this object.
     */
    public Object clone()
    {
       	UsageDataAvailableEventImpl realClone;

       	Object myClone = super.clone();
       	realClone = (UsageDataAvailableEventImpl) myClone;

	    if(availableSinceTime != null)
	    {
	        realClone.setAvailableSince(getAvailableSince());
	    }

	    return realClone;
    }

	/**
     * Deep equality checking of the instance.
     *
     * @return Flag indicating if the objects were equal
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof UsageDataAvailableEventImpl))
        {
            return false;
        }

        UsageDataAvailableEventImpl localOther = 
							(UsageDataAvailableEventImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if (availableSinceTime != null)
			{
				if  (!(availableSinceTime.
								equals(localOther.availableSinceTime)))
				{
					return false;
				}
			}
			else if(availableSinceTime != localOther.availableSinceTime)
			{
				return false;
			}

		}
		else
		{
			return false;
		}

		return true;
	}

    /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode=-1;

        if(availableSinceTime != null)
        {
            hashCode = availableSinceTime.hashCode();
        }

		return hashCode;
	}
}
