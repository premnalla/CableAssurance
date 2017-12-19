
package com.nec.oss.ipb.event.ri;

/**
 * This class supports the low-level detailing
 * of a MediationCapabilityChangeEvent emitted by a Producer entity.
 * This class provides many helpful methods to clients interested
 * in decoding such an event, received by them.
 *
 * @ossj:complexdata
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

import javax.oss.ipb.event.MediationCapabilityChangeEvent;
import javax.oss.ipb.producer.MediationCapability;

import java.util.ArrayList;

public class MediationCapabilityChangeEventDecoderImpl
implements javax.oss.ipb.event.MediationCapabilityChangeEventDecoder
{

	MediationCapabilityChangeEvent event;


    /**
     * Interfaces don't have constructors. Hence, we need to get/set methods
     * to hand off an event for analysis.
     */

    /**
     * Returns a deep copy of this value. We need this method to
     * return a new instance of decoder from the JVTProducerSession object.
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
	{
        MediationCapabilityChangeEventDecoderImpl clone = null;

		try
		{
			clone =
           		(MediationCapabilityChangeEventDecoderImpl) super.clone();

			if( event != null )
			{
				clone.event =
					(MediationCapabilityChangeEventImpl) event.clone();
			}
		}
		catch(CloneNotSupportedException e)
		{
			clone = null;
		}

		return clone;
	}

    /**
     * Get the event attribute.
     * @return MediationCapabilityChangeEvent for this decoder
     * @exception java.lang.IllegalStateException Thrown if the event is not set
     */
    public MediationCapabilityChangeEvent getMediationCapabilityChangeEvent()
        throws java.lang.IllegalStateException
	{
		if(event == null)
		{
			throw new java.lang.IllegalStateException(
				"Event parameter value is not set");
		}

		return (MediationCapabilityChangeEvent) event.clone();
	}

    /**
     * Set the event attribute, which will then be analyzed.
     * @param event MediationCapabilityChangeEvent for this decoder
     * @exception java.lang.IllegalArgumentException Thrown if the input event 
     * is not valid
     */
    public void setMediationCapabilityChangeEvent(
        MediationCapabilityChangeEvent event)
        throws java.lang.IllegalArgumentException
	{
		if(event == null)
		{
			throw new java.lang.IllegalArgumentException(
				"Event parameter value is not valid");
		}
	
		this.event = event;
	}

    /**
     * Is this a newly added MediationCapability.
     * @return true if yes. Otherwise, returns false.
     */
    public boolean isACapabilityNewlyAddedEvent()
	{
		if ((event.getOldMediationCapability() == null) &&
			(event.getNewMediationCapability() != null))
		{
			return true;
		}	
		else
		{
			return false;
		}
	}

    /**
     * Is this a removed MediationCapability, meaning it is no longer
     * supported by this Producer Entity?
     * @return true if yes. Otherwise, returns false.
     */
    public boolean isACapabilityRemovedEvent()
	{
		if ((event.getOldMediationCapability() != null) &&
			(event.getNewMediationCapability() == null))
		{
			return true;
		}	
		else
		{
			return false;
		}
	}

    /**
     * Is this a modified MediationCapability of the producer?
     * @return true if yes. Otherwise, returns false.
     */
    public boolean isACapabilityModifiedEvent()
	{
		if ((event.getOldMediationCapability() != null) &&
			(event.getNewMediationCapability() != null))
		{
			if(!event.getOldMediationCapability().equals(event.getNewMediationCapability()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}	
		else
		{
			return false;
		}
	}

    /**
     * Get the list of changed attribute names. This method returns
     * valid attribute names only if invoking
     * the {@link #isACapabilityModifiedEvent} would return true.
     * @return Array of the names of attributes that changed
     * @exception IllegalStateException Thrown if the capabilities haven't changed
     * 
     */
    public String [] getChangedAttributeNames()
        throws IllegalStateException
	{

		ArrayList aList = new ArrayList();

		if(isACapabilityModifiedEvent() == false) {
			throw new IllegalStateException(
				"This isn't a modified MediationCapability of the producer");
		}


		if(!event.getOldMediationCapability().getName().equals
          (event.getNewMediationCapability().getName()))
		{
			aList.add(MediationCapability.NAME);
		}

		if(!event.getOldMediationCapability().getCollectionCategories().equals
          (event.getNewMediationCapability().getCollectionCategories()))
		{
			aList.add(MediationCapability.COLLECTION_CATEGORIES);
		}

		if(!event.getOldMediationCapability().getCollectionFormat().equals
          (event.getNewMediationCapability().getCollectionFormat()))
		{
			aList.add(MediationCapability.COLLECTION_FORMAT);
		}

		if(!event.getOldMediationCapability().getCollectionFormatVersion().equals
          (event.getNewMediationCapability().getCollectionFormatVersion()))
		{
			aList.add(MediationCapability.COLLECTION_FORMAT_VERSION);
		}

		if(!event.getOldMediationCapability().getCollectionEncoding().equals
          (event.getNewMediationCapability().getCollectionEncoding()))
		{
			aList.add(MediationCapability.COLLECTION_ENCODING);
		}

		if(!event.getOldMediationCapability().getCollectionSchema().equals
          (event.getNewMediationCapability().getCollectionSchema()))
		{
			aList.add(MediationCapability.COLLECTION_SCHEMA);
		}

		if(!event.getOldMediationCapability().getOutputFormats().equals
          (event.getNewMediationCapability().getOutputFormats()))
		{
			aList.add(MediationCapability.OUTPUT_FORMATS);
		}

		if(!event.getOldMediationCapability().getOutputFormatVersions().equals
          (event.getNewMediationCapability().getOutputFormatVersions()))
		{
			aList.add(MediationCapability.OUTPUT_FORMAT_VERSIONS);
		}

		if(!event.getOldMediationCapability().getOutputEncodings().equals
          (event.getNewMediationCapability().getOutputEncodings()))
		{
			aList.add(MediationCapability.OUTPUT_ENCODINGS);
		}

		if(!event.getOldMediationCapability().getOutputSchemas().equals
          (event.getNewMediationCapability().getOutputSchemas()))
		{
			aList.add(MediationCapability.OUTPUT_SCHEMAS);
		}

		return (String[]) aList.toArray(new String[aList.size()]);
		
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

        if(!(other instanceof MediationCapabilityChangeEventDecoderImpl))
        {
            return false;
        }

        MediationCapabilityChangeEventDecoderImpl localOther = 
							(MediationCapabilityChangeEventDecoderImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			if (event != null)
			{
				if  (!(event.equals(localOther.event)))
				{
					return false;
				}
			}
			else if(event != localOther.event)
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

        if(event != null)
        {
            hashCode = event.hashCode();
        }

		return hashCode;
	}
}
