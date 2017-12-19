
package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports.
 */
import javax.oss.Serializer;
import javax.oss.cfi.activity.SubscriptionParams;
import javax.oss.cfi.activity.SubscriptionFilter;


/**
 * Implements SubscriptionParams interface
 */

public class SubscriptionParamsImpl
implements javax.oss.cfi.activity.SubscriptionParams
{

    /**
     * Members
     */

    protected String subscriber;
    protected String subscriptionName;
    protected SubscriptionFilter subscriptionFilter;

    
    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
    {
        SubscriptionParamsImpl clone = null;
        try
        {
            clone = (SubscriptionParamsImpl) super.clone();
        }
        
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
            
        }

		if(subscriptionFilter != null)
		{
			clone.subscriptionFilter = 
					(SubscriptionFilter) subscriptionFilter.clone();
		}

        return clone;
    }

    /**
     * Get the optional string name of the subscriber. 
     * @return Name of the Subscriber
     * @exception java.lang.IllegalStateException Thrown if the subscriber name
     * has not been set.
     */
    public String getSubscriber()
        throws java.lang.IllegalStateException
    {
        if(subscriber == null)
        {
            throw new java.lang.IllegalStateException(
                "Subscriber attribute was not set");
        }
        
        return subscriber;
    }
    
    /**
     * Set the optional string name of the subscriber.
     * This can be set to null value
     * to indicate shared subscription by multiple clients.
     * @param subscriber Name of the Subscriber
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * subscriber name is not valid
     */
    public void setSubscriber(
        String subscriber)
        throws java.lang.IllegalArgumentException
    {
        if(subscriber == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Subscriber parameter is not valid");
        }
        
        this.subscriber = subscriber;
    }
    
    

    /**
     * Get the user friendly name for this subscription.
     * @return Name of the Subscription
     * @exception java.lang.IllegalStateException Thrown if the subscription
     * name has not been set.
     */
    public String getSubscriptionName()
        throws java.lang.IllegalStateException
    {
        if(subscriptionName == null)
        {
            throw new java.lang.IllegalStateException(
                "SubscriptionName attribute was not set");
        }
        
        return subscriptionName;
    }
    
    /**
     * Set the user friendly name for this subscription. 
     * The default value is an empty String.
     * @param name Name of the Subscription
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * subscription name is not valid
     */
    public void setSubscriptionName(
        String subscriptionName)
        throws java.lang.IllegalArgumentException
    {
        if(subscriptionName == null)
        {
            throw new java.lang.IllegalArgumentException(
                "SubscriptionName parameter is not valid");
        }
        
        this.subscriptionName = subscriptionName;
    }
    
    /**
     * Make a subscription filter.
     * @param subscriptionType Type of subscription to make
     * @return new instance of SubscriptionFilter
     */
    public SubscriptionFilter makeSubscriptionFilter(String filterType)
    {
        ClassLoader cl = getClass().getClassLoader();
        Class filterClass = null;
        try
        {
            filterClass = cl.loadClass(filterType);

            return (SubscriptionFilter) (filterClass.newInstance());

        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown SubscriptionFilter Type" + e);
        }
    }
    

    /**
     * Get the subscription filter.
     * This is set to null by default, meaning it is not set.
     * @return Associated subscription filter
     * @exception java.lang.IllegalStateException Thrown if the subscription
     * filter has not been set.
     */
    public SubscriptionFilter getSubscriptionFilter()
        throws java.lang.IllegalStateException
    {
        if(subscriptionFilter == null)
        {
            throw new java.lang.IllegalStateException(
                "SubscriptionFilter attribute was not set");
        }
        
        return subscriptionFilter;

    }
    
                

    /**
     * Set the subscription filter.
     * This is set to null by default, meaning it is not set.
     * @param subscriptionFilter Subscription filter object
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * subscription filter is not valid
     */
    public void setSubscriptionFilter(
        SubscriptionFilter subscriptionFilter)
        throws java.lang.IllegalArgumentException
    {
        if(subscriptionFilter == null)
        {
            throw new java.lang.IllegalArgumentException(
                "SubscriptionFilter parameter is not valid");
        }
        
        this.subscriptionFilter = subscriptionFilter;

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

        if(!(other instanceof SubscriptionParamsImpl))
        {
            return false;
        }

        SubscriptionParamsImpl localOther = 
							(SubscriptionParamsImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if (subscriber != null)
			{
				if  (!(subscriber.
								equals(localOther.subscriber)))
				{
					return false;
				}
			}
			else if(subscriber != localOther.subscriber)
			{
				return false;
			}

			if (subscriptionName != null)
			{
				if  (!(subscriptionName.
								equals(localOther.subscriptionName)))
				{
					return false;
				}
			}
			else if(subscriptionName != localOther.subscriptionName)
			{
				return false;
			}

			if (subscriptionFilter != null)
			{
				if  (!(subscriptionFilter.
								equals(localOther.subscriptionFilter)))
				{
					return false;
				}
			}
			else if(subscriptionFilter != localOther.subscriptionFilter)
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

        if(subscriber != null)
        {
            hashCode = subscriber.hashCode();
        }

        if(subscriptionName != null)
        {
            hashCode ^= subscriptionName.hashCode();
        }

        if(subscriptionFilter != null)
        {
            hashCode ^= subscriptionFilter.hashCode();
        }

		return hashCode;
	}
}
