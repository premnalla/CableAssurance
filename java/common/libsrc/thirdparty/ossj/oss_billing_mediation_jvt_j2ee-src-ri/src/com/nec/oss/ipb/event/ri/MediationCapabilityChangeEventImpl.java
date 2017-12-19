
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
 * Standard imports.
 */

/**
 * Spec imports.
 */
import javax.oss.ipb.producer.MediationCapability;
import javax.oss.ipb.producer.ProducerKey;

/**
 * Implementation for javax.oss.ipb.event.MediationCapabilityChangeEvent interface.
 * This is an event that will be sent by the <CODE>Producer</CODE> to
 * the clients to inform the introduction of new data <CODE>formats</CODE>.
 */
public class MediationCapabilityChangeEventImpl
extends com.nokia.oss.ossj.common.ri.BaseEvent
implements javax.oss.ipb.event.MediationCapabilityChangeEvent
{

    private String description;

    private MediationCapability newMediationCapability;

    private MediationCapability oldMediationCapability;

    private ProducerKey producerKey;
    
    /**
     * Get a user-friendly text description for describing this event.
     * This could have description to indicate whether this is a newly
     * added mediation capability or a modified mediation capability, etc..
     * <p>
     * This is an optional attribute.
     * @return Description of the event
     * @exception java.lang.IllegalStateException Thrown if the description is
     * not set
     */
    public String getDescription()
        throws java.lang.IllegalStateException
    {
        if(description == null)
        {
            throw new java.lang.IllegalStateException(
                "Description attribute is not set");
            
        }

        return description;
        
    }

    /**
     * Set a user-friendly text description for describing this event.
     * <p>
     * This is an optional attribute.
     * @param description Description of the event
     * @exception java.lang.IllegalArgumentException Thrown if the description
     * is not set
     */
    public void setDescription(String description)
        throws java.lang.IllegalArgumentException
    {
        if(description == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Description parameter value is not valid");
        }
        
        this.description = description;
    }
    
    /**
     * Factory method to make an empty instance of the
     * MediationCapability object.
     * <p>
     * @param mediationCapabilityType The string name of the mediation
     * capability type invoked. The supported types of mediation capabilities
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getMediationCapabilityTypes} method.
     * 
     * @return The created mediation capability object is returned.
     * @exception java.lang.IllegalArgumentException Thrown if the input type
     * is not valid
     */
    public MediationCapability makeMediationCapability(
        String mediationCapabilityType)
        throws java.lang.IllegalArgumentException
    {
        ClassLoader cl = getClass().getClassLoader();
        Class medCapClass = null;
        
        try
        {
            medCapClass = cl.loadClass(mediationCapabilityType);
            return (MediationCapability) (medCapClass.newInstance());
        }
        
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown MediationCapability Type" + e);
        }

    }
    
    /**
     * Get the new value of mediation capability.  
     * <p>
     * If a mediation capability has been removed from the
     * existing set of mediation capabilities of the <CODE>Producer</CODE>,
     * then returned value of this attribute may be 'null'.
     * <p>
     * If this event is due to modification of an already existing
     * mediation capability of the <CODE>Producer</CODE>, then this
     * attribute value may be optionally present.
     *
     * @return The new MediationCapability
     * @exception java.lang.IllegalStateException Thrown if the new medcap
     * is not set
     */
    public MediationCapability getNewMediationCapability()
        throws java.lang.IllegalStateException
    {
        if(newMediationCapability == null)
        {
            throw new java.lang.IllegalStateException(
                "NewMediationCapability attribute is not set");
        }
        
        return (MediationCapability) newMediationCapability.clone();
    }

    /**
     * Set the new value of mediation capability.
     * <p>
     * If this mediation capability has been removed from the
     * existing set of mediation capabilities of the <CODE>Producer</CODE>,
     * then this attribute can be set to null.
     * <p>
     * If this event is due to modification of an already existing
     * mediation capability of the <CODE>Producer</CODE>, then this
     * attribute value may be optionally present.
     * @param mediationCapability The new MediationCapability
     * @exception java.lang.IllegalArgumentException Thrown if the input medcap
     * is not valid
     */
    public void setNewMediationCapability(
        MediationCapability mediationCapability)
        throws java.lang.IllegalArgumentException
    {
        if(mediationCapability == null)
        {
            throw new java.lang.IllegalArgumentException(
                "NewMediationCapability parameter value is not valid");
        }
        
        this.newMediationCapability = mediationCapability;
    }

    /**
     * Get the old value of mediation capability.
     * <p>
     * If this mediation capability has been newly added to the
     * existing set of mediation capabilities of the <CODE>Producer</CODE>,
     * then returned value of this attribute may be 'null'.
     * <p>
     * If this event is due to modification of an already existing
     * mediation capability of the <CODE>Producer</CODE>, then this
     * attribute value may be optionally present.
     * @return The old MediationCapability
     * @exception java.lang.IllegalStateException Thrown if the old medcap
     * is not set
     */
    public MediationCapability getOldMediationCapability()
        throws java.lang.IllegalStateException
    {
        if(oldMediationCapability == null)
        {
            throw new java.lang.IllegalStateException(
                "OldMediationCapability attribute is not set");
        }
        
        return (MediationCapability) oldMediationCapability.clone();
    }
    
    /**
     * Set the old value of mediation capability.
     * <p>
     * If this mediation capability has been newly added to the
     * existing set of mediation capabilities of the <CODE>Producer</CODE>,
     * then this attribute can be set to null.
     * <p>
     * If this event is due to modification of an already existing
     * mediation capability of the <CODE>Producer</CODE>, then this
     * attribute value may be optionally present.
     * @param mediationCapability The new MediationCapability
     * @exception java.lang.IllegalArgumentException Thrown if the input medcap
     * is not valid
     */
    public void setOldMediationCapability(
        MediationCapability mediationCapability)
        throws java.lang.IllegalArgumentException
    {
        if(mediationCapability == null)
        {
            throw new java.lang.IllegalArgumentException(
                "OldMediationCapability parameter value is not valid");
        }
        
        this.oldMediationCapability = mediationCapability;
    }

    /**
     * Factory method to make an empty instance of the ProducerKey object.
     *
     * @param producerKeyType - This specifies the loadable type name
     * of the producerKey object. A Specific ProducerKey instance
     * can be constructed using this interface.
     * <p>
     * The supported key types of <CODE>Producer</CODE>s
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getProducerKeyTypes}
     * method.
     *
     * The producerKeyType parameter can be null. The type of the Producer
     * Key returned will then be dependent upon the implementation.
     * 
     * @return The created key object is returned.
     * @exception java.lang.IllegalArgumentException If the type name is invalid
     *
     */
    public ProducerKey makeProducerKey(
        String producerKeyType)
        throws java.lang.IllegalArgumentException
    {
        ClassLoader cl = getClass().getClassLoader();
        Class producerKeyClass = null;

        try
        {
            producerKeyClass = cl.loadClass(producerKeyType);
            return (ProducerKey) (producerKeyClass.newInstance());
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown ProducerKey Type" + e);
        }

    }
    
    /**
     * Get the key value of the Producer entity emitting this Change event.
	 * 
	 * @return The value of <CODE>Producer</CODE> key.
	 *
	 * @exception java.lang.IllegalStateException If this
     * attribute is not yet set or initialized.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
     */
    public ProducerKey getProducerKey()
        throws java.lang.IllegalStateException
    {
        if(producerKey == null)
        {
            throw new java.lang.IllegalStateException(
                "ProducerKey attribute is not set");
        }

        return producerKey;
    }


    /**
     * Set the key value of the Producer entity emitting this Change event.
	 * 
	 * @param producerKey The value of <CODE>Producer</CODE> Key.
	 *
	 * @exception java.lang.IllegalArgumentException If the passed
     * <CODE>key</CODE> is malformed.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
     */
    public void setProducerKey(ProducerKey producerKey)
        throws java.lang.IllegalArgumentException
    {
        if(producerKey == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ProducerKey parameter value is not valid");
        }
        
        this.producerKey = producerKey;
    }

    /**
     * Creates new MediationCapabilityChangeEvent empty instance.
     */
    public MediationCapabilityChangeEventImpl()
    {
        super("");
    }

    /**
     * Deep copy of this object.
     *
     * @return deep copy of this object.
     */
    public Object clone()
    {
       	MediationCapabilityChangeEventImpl realClone;

       	Object myClone = super.clone();
       	realClone = (MediationCapabilityChangeEventImpl) myClone;


	    if(newMediationCapability != null)
       	{
        	realClone.newMediationCapability =
              	(MediationCapability)
             	newMediationCapability.clone();
        }

	    if(oldMediationCapability != null)
	    {
	        realClone.oldMediationCapability =
	            (MediationCapability)
	            oldMediationCapability.clone();
	    }
	
	    if(producerKey != null)
	    {
	        realClone.producerKey =
	            (ProducerKey)
	            producerKey.clone();
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

        if(!(other instanceof MediationCapabilityChangeEventImpl))
        {
            return false;
        }

        MediationCapabilityChangeEventImpl localOther = 
							(MediationCapabilityChangeEventImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if (description != null)
			{
				if  (!(description.
								equals(localOther.description)))
				{
					return false;
				}
			}
			else if(description != localOther.description)
			{
				return false;
			}

			if (newMediationCapability != null)
			{
				if  (!(newMediationCapability.
								equals(localOther.newMediationCapability)))
				{
					return false;
				}
			}
			else if(newMediationCapability != localOther.newMediationCapability)
			{
				return false;
			}

			if (oldMediationCapability != null)
			{
				if  (!(oldMediationCapability.
								equals(localOther.oldMediationCapability)))
				{
					return false;
				}
			}
			else if(oldMediationCapability != localOther.oldMediationCapability)
			{
				return false;
			}

			if (producerKey != null)
			{
				if  (!(producerKey.
								equals(localOther.producerKey)))
				{
					return false;
				}
			}
			else if(producerKey != localOther.producerKey)
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

        if(description != null)
        {
            hashCode = description.hashCode();
        }

        if(newMediationCapability != null)
        {
            hashCode ^= newMediationCapability.hashCode();
        }

        if(oldMediationCapability != null)
        {
            hashCode ^= oldMediationCapability.hashCode();
        }

        if(producerKey != null)
        {
            hashCode ^= producerKey.hashCode();
        }

		return hashCode;
	}
}
