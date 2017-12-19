
package com.nec.oss.ipb.producer.ri;


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
import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityKey;
import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.MediationCapability;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityValueImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;

import com.nec.oss.ipb.producer.ri.ProducerKeyImpl;
import com.nec.oss.ipb.producer.ri.MediationCapabilityImpl;

import com.nokia.oss.ossj.common.ri.ApplicationContextImpl;

/**
 * Implementation for
 * javax.oss.ipb.producer.ProducerValue interface.
 * This is base class, representing the attribute values of
 * any type of <CODE>Producer</CODE> Entity in the IP Billing API.
 * The Producer in the IP Billing API is an <CODE>Activity</CODE>
 * (see {@link javax.oss.cfi.activity.ActivityValue}),
 * which knows how and
 * what type of data to collect and how to report it to the client.
 * <p>
 * This is a base Value Object to the <CODE>Producer</CODE> entity.
 * This class and its sub interfaces (if any) provide several ways
 * to access the attributes:
 * <p>
 * <UL>
 * <LI>Attributes can be accessed through standard JavaBeans get/set (isSet)
 * methods.
 * <LI>Attributes can be accessed through the generic methods defined
 *     in <CODE>AttributeAccess</CODE>
 * <UL>
 * <LI><CODE> Object getAttributeValue(String attributeName)</CODE>
 * <LI><CODE> void setAttributeValue(String attributeName, Object newValue)</CODE>
 * </UL>
 * </UL>
 *
 * @see javax.oss.ipb.producer.ProducerValueIterator
 * @see javax.oss.ipb.producer.JVTProducerSession
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public class ProducerValueImpl
extends com.nec.oss.cfi.activity.ri.ActivityValueImpl
implements ProducerValue
{

    protected static AttributeManager producerAttributeManager;

    /**
     * String array which contains all attribute names
     */
    private static final String [] producerAttributeNames =
    {
        MEDIATION_CAPABILITIES,
    };
    

    /**
     * String array which contains names of all settable attributes.
     */
    private static final String [] producerSettableAttributeNames =
    {
        MEDIATION_CAPABILITIES,
    };

    /**
     * protected members.
     */
    /** 
     * Mediation capabilities.
     */
    protected MediationCapability[] medCaps;

    /**
     * Transfer status support capability
     */
    protected boolean transferStatusIsSupported;
    
    /**
     * Value object.
     */

	/**
	 * Returns the loadable type name of the ProducerKey for this Producer.
     * <p>
     * Subtypes of ProducerValue may override this operation to provide the
     * type name of their own key instance.
	 * 
	 * @return String value of ProducerKey type name.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
	public String getProducerKeyType()
    {
        return ProducerKeyImpl.class.getName();
    }
    

	/**
	 * Factory method for making an instance of
     * <CODE>ProducerKey</CODE>, an attribute of
     * this instance.
     * <p>
     * Subtypes of ProducerValue may override this operation to provide their
     * own key instance.
	 * 
	 * @return An new unfilled instance of <CODE>ProducerKey</CODE>.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
	public ProducerKey makeProducerKey()
    {
        return (ProducerKey) makeManagedEntityKey();
    }

	/**
	 * Get the <CODE>Producer</CODE> entity's key attribute value.
	 * 
	 * @return The key value instance of the <CODE>Producer</CODE> Entity's key.
	 *
	 * @exception java.lang.IllegalStateException If the <CODE>ProducerKey</CODE>
     * attribute value is not yet filled in.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
	public ProducerKey getProducerKey()
        throws java.lang.IllegalStateException
    {
        return (ProducerKey) getManagedEntityKey();
    }

	/**
	 * Set the <CODE>Producer</CODE> entity's key attribute value.
	 * 
	 * @param key The key attribute value of the <CODE>Producer</CODE> Entity.
	 *
	 * @exception java.lang.IllegalArgumentException If
     * the <CODE>ProducerKey</CODE> attribute value provided as the argument to
     * this method is not well formed.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
	public void setProducerKey(ProducerKey key) 
		throws java.lang.IllegalArgumentException
    {
        setManagedEntityKey(key);
    }

	/**
	 * Make instances of <CODE>MediationCapability</CODE> of specified type.
     * A Factory Method.
     *
     * @param mediationCapabilityType Specifies which type of Mediation
     * Capability should be instantiated. The supported types of Mediation
     * Capabilities can be obtained by a client by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getMediationCapabilityTypes} method.
	 *
     @param how_many Specifies how many instances to create.
     *
	 * @return An array of <CODE>how_many</CODE> empty instances
     * of specified Mediation Capability type.
	 *
	 */
	public MediationCapability[] makeMediationCapabilityValues(
        String mediationCapabilityType,
        int how_many)
    {
        if(how_many <= 0)
        {
            return new MediationCapability[0];
        }

        MediationCapability[] result = new MediationCapability[how_many];
        
        for (int i = 0; i < how_many; ++i)
        {
            result[i] = new MediationCapabilityImpl();
        }

        return result;
    }

	/**
	 * Get the <CODE>Producer</CODE> Entity's Mediation Capability attribute.
	 * 
	 * 
	 * @return The attribute value of <CODE>MediationCapability</CODE>
	 *
	 * @exception java.lang.IllegalStateException If the
     <CODE>mediationCapability</CODE> * value is not yet filled in.
	 *
	 */
	public MediationCapability[] getMediationCapabilityValues()
        throws java.lang.IllegalStateException
    {
        checkAttribute(MEDIATION_CAPABILITIES);

        MediationCapability[] result =
            (MediationCapability[]) medCaps.clone();
        
        for(int i = 0; i < medCaps.length; ++i)
        {
            result[i] = (MediationCapability) medCaps[i].clone();
        }
        
        return result;
    }

	/**
	 * Set the <CODE>Producer</CODE> Entity's Mediation Capability attribute.
	 * 
	 * 
	 * @param mediationCapability The value of the <CODE>Producer</CODE>'s
     * Mediation Capability attribute.
	 *
	 * @exception java.lang.IllegalArgumentException If
     * the <CODE>mediationCapability</CODE> attribute's value
     * provided as the argument to this method is not well formed.
	 *
	 */
	public void setMediationCapabilityValues(
        MediationCapability[] mediationCapabilities)
		throws java.lang.IllegalArgumentException
    {
        if( mediationCapabilities == null)
        {
            throw new java.lang.IllegalArgumentException(
                "MediationCapabilities parameter value is invalid");
        }

        setDirtyAttribute(MEDIATION_CAPABILITIES);

        this.medCaps = (MediationCapability[]) mediationCapabilities.clone();

        for (int i = 0; i < mediationCapabilities.length; ++i)
        {
            this.medCaps[i] =
                (MediationCapability) mediationCapabilities[i].clone();
        }
    }

	/**
	 * Get the <CODE>Producer</CODE> Entity's Mediation Capability attribute.
	 * 
	 * 
	 * @return The attribute value of <CODE>MediationCapability</CODE>
	 *
	 * @exception java.lang.IllegalStateException If the
     <CODE>mediationCapability</CODE> * value is not yet filled in.
	 *
	 */
	public MediationCapability[] getMediationCapabilities()
        throws java.lang.IllegalStateException
    {
		return getMediationCapabilityValues();
    }

	/**
	 * Set the <CODE>Producer</CODE> Entity's Mediation Capability attribute.
	 * 
	 * 
	 * @param mediationCapability The value of the <CODE>Producer</CODE>'s
     * Mediation Capability attribute.
	 *
	 * @exception java.lang.IllegalArgumentException If
     * the <CODE>mediationCapability</CODE> attribute's value
     * provided as the argument to this method is not well formed.
	 *
	 */
	public void setMediationCapabilities(
        MediationCapability[] mediationCapabilities)
		throws java.lang.IllegalArgumentException
    {
		setMediationCapabilityValues(mediationCapabilities);
    }

    /**
     * True if the transfers can be monitored with the selected
     * reporting mechnism.
     * @return flag indicating of transfer status is supported
     */
    public boolean isTransferStatusSupported()
    {
        return transferStatusIsSupported;
    }

    /**
     * Set if the transfers should be monitored with the selected
     * reporting mechnism.
     * <p>
     * The client may invoke this method to indicate its desire to monitor
     * the transfers. If an implemenation cannot support monitoring, 
     * a javax.oss.UnsupportedOperationException must be thrown at the time
     * of creation of the ProducerValue entity.
     * @param flag Indication of whether transfer status is supported
     */
    public void setTransferStatusSupported(boolean flag)
    {
        transferStatusIsSupported = flag;
    }


    /**
     * Configuration of AttributeManager and AttributeAccess sub classes.
     */
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.producerAttributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.producerSettableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager()
    {
        return producerAttributeManager;
    }

    protected AttributeManager makeAttributeManager()
    {
        producerAttributeManager = new AttributeManager();
        return producerAttributeManager;
    }

    /** Setter for property managedEntityKeyDummy.
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
     */
    public void setManagedEntityKeyDummy(
        javax.oss.ManagedEntityKey managedEntityKeyDummy)
    {
        if (managedEntityKeyDummy instanceof ProducerKey)
        {
            super.setManagedEntityKeyDummy(managedEntityKeyDummy);
        }
    }

    /**
     * Getter for the ManagedEntityKey
     * @return ManagedEntityKey for this object
     * @exception java.lang.IllegalStateException thrown if the ManagedEntityKey
     * has not been set.
     */
    public ManagedEntityKey getManagedEntityKey()
        throws java.lang.IllegalStateException
    {
    	checkAttribute(KEY);
        return activityKey;
    }

    /**
     * Setter for the ManagedEntityKey
     * @param managedEntityKey ManagedEntityKey for this object
     * @exception java.lang.IllegalArgumentException thrown if the input arg
     * isn't valid
     */
    public void setManagedEntityKey(
        ManagedEntityKey managedEntityKey) 
        throws java.lang.IllegalArgumentException
    {
    	if (managedEntityKey instanceof ProducerKey)
        {
        	setDirtyAttribute(KEY);
        	this.activityKey = (ProducerKey) managedEntityKey;
        } 
        else
        {
            throw new java.lang.IllegalArgumentException(
                "Not the correct type of key: "+ managedEntityKey.getClass());
        }
        
    }

	/**
	 * Factory method for making an instance of
     * <CODE>ProducerKey</CODE>, an attribute of
     * this instance.
     * <p>
     * Subtypes of ProducerValue may override this operation to provide their
     * own key instance.
	 * 
	 * @return An new unfilled instance of <CODE>ProducerKey</CODE>.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
    public ManagedEntityKey makeManagedEntityKey()
    {
		ProducerKey tmpKey = null;
		try
		{
			tmpKey=(ProducerKey)getManagedEntityKey();
		}

		catch (IllegalStateException okEX)
		{
			// OK - will get this exception if the key is null
		}

		if(tmpKey == null)
		{
        	ProducerKey newProdKey = new ProducerKeyImpl() ;
			newProdKey.setApplicationContext(new ApplicationContextImpl(null, null, null));
			newProdKey.setApplicationDN("temporary DN");
			newProdKey.setType(this.getClass().getName());
			newProdKey.setPrimaryKey(null);

			setManagedEntityKey(newProdKey);
		}

        return getManagedEntityKey();
    }

    /**
     * Deep copy of this key
     *
     * @return deep copy of this key
     */
    public Object clone()
    {
		ProducerValueImpl myClone = null;


		myClone = (ProducerValueImpl)super.clone();

		if( medCaps != null)
		{
			myClone.medCaps = (MediationCapability []) medCaps.clone();
			
			for(int i = 0; i < medCaps.length; ++i)
			{
				myClone.medCaps[i] =
					(MediationCapability) medCaps[i].clone(); 
			}
		}

        return myClone;
    }


	/*
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

        if(!(other instanceof ProducerValueImpl))
        {
            return false;
        }

        ProducerValueImpl localOther = 
							(ProducerValueImpl) other;

        if(localOther.hashCode() == hashCode())
        {

           if (transferStatusIsSupported == 
                            localOther.transferStatusIsSupported) 
           {
				if ((medCaps != null) && (localOther.medCaps != null))
				{
					if  (medCaps.length != localOther.medCaps.length)
					{
						return false;
					}
					else
					{
						int tpLen=medCaps.length;
						for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
						{
							if (!medCaps[tpIdx].equals(localOther.medCaps[tpIdx]))
							{
								return false;
							}
						}
					}
				}
				else if(medCaps != localOther.medCaps)
				{
					return false;
				}

				if (!super.equals(localOther))
				{
					return false;
				}
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
		boolean hashCodeFlag = false;
        if(medCaps != null)
        {
            int tpLen=medCaps.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(medCaps[tpIdx] != null)
                {
                    if(hashCodeFlag)
                    {
                        hashCode ^= medCaps[tpIdx].hashCode();
                    }
                    else
                    {
                        hashCode = medCaps[tpIdx].hashCode();
                        hashCodeFlag = true;
                    }
                }
            }
        }

		return hashCode;
	}
}
