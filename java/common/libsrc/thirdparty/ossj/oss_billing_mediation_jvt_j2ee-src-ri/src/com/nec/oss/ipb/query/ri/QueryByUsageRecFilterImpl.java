
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */
package com.nec.oss.ipb.query.ri;

/**
 * Standard imports
 */

/**
 * Spec imports.
 */
import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import javax.oss.ipb.query.QueryByUsageRecFilter;

import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.type.UsageRecFilterValue;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.AttributeAccessImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;
import com.nec.oss.cfi.activity.ri.QueryActivityReportDataImpl;


/**
 * A javax.oss.ipb.query.QueryByUsageRecFilter interface implementation.
 *
 * <p>
 * The Query for Usage Records may consist of specifying
 * multiple <CODE>UsageRecFilterValue</CODE>
 * objects, which are used as templates for building the query expression.
 * All the Filter Objects in the array are concatenated, using
 * logical OR operation.
 * <p>
 * Each individual <CODE>UsageRecFilterValue</CODE> object may consist of multiple
 * filterable attribute specification. These attributes are concatenated
 * together using a logical AND operation.
 *
 * @see javax.oss.ipb.type.UsageRecFilterValue
 * @see javax.oss.ipb.producer.JVTProducerSession
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */
public class QueryByUsageRecFilterImpl
extends QueryActivityReportDataImpl
implements QueryByUsageRecFilter
{
    private static AttributeManager usageFilterAttributeManager;
    private static String[] noSupportedSerializerTypes = new String[0];

    
    // String array which conatins all attribute Names
    private static final String[] usageFilterAttributeNames =
    {
        PRODUCER_KEY,
        USAGE_REC_FILTER_VALUE
    };
    
    // writeable attributes
    private static final String[] settableUsageFilterAttributeNames =
    {
        PRODUCER_KEY,
        USAGE_REC_FILTER_VALUE
    };

    /**
     * Member data.
     */
    /**
     * Producer key filter attribute.
     */
    protected ProducerKey producerKey;

    /**
     * UsageRec filter attribute.
     */
    protected UsageRecFilterValue usageRecFilter;


	/**
	 * Create a new value object for a Usage Record Filter. The attributes
     * of the value are not initialized.
     * <p>
     * This method can be invoked by the clients of the API to make
     * query request for usage records.
     *
     * @param usageRecFilterValueType - This specifies the loadable type name
     * of the UsageRecFilterValue object. A Specific UsageRecFilterValue instance
     * can be constructed using this interface.
     * <p>
     * The supported types of <CODE>UsageRecFilterValue</CODE>s
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getUsageRecFilterTypes} method.
     * <p>
     * The usageRecFilterValueType parameter can be null. The type
     * of the UsageRecFilterValue
     * returned will then be dependent upon the implementation.
	 * 
	 * @return The created value object representation of Usage Record
     * Filter.
     * @exception java.lang.IllegalArgumentException Thrown if the specified
     * filter type is invalid
	 *
	 * @see UsageRecFilterValue
	 */
	public UsageRecFilterValue makeUsageRecFilterValue(
        String usageRecFilterValueType)
        throws java.lang.IllegalArgumentException
    {
        ClassLoader cl = getClass().getClassLoader();
        Class usageFilterClass = null;
        try
        {
            usageFilterClass = cl.loadClass(usageRecFilterValueType);

            return (UsageRecFilterValue) (usageFilterClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown UsageRecFilterValue Type" + e);
        }
        
    }
    

	/**
	 * Get the 
     * <CODE>usageRecFilterValue</CODE> filterable attribute value.
     * This utilizes the #getSubscriptionFilter operation to retrieve
     * the filter.
	 * 
	 * @return The instance of a filter.
	 *
	 * @exception java.lang.IllegalStateException If this
     * attribute is not yet set or initialized.
	 *
	 * @see javax.oss.ipb.type.UsageRecFilterValue
	 */
	public UsageRecFilterValue getUsageRecFilterValue() 
		throws java.lang.IllegalStateException
    {
        checkAttribute(USAGE_REC_FILTER_VALUE);
        return (UsageRecFilterValue) usageRecFilter.clone();
    }
    

	/**
	 * 
	 * Set the set of filter expression
     * <CODE>usageRecFilterValue</CODE> filterable attribute value.
     * This utilizes the #setSubscriptionFilter operation to retrieve
     * the filter.
	 * 
	 * @param filterValue The instance of filter.
	 *
	 * @exception java.lang.IllegalArgumentException If the passed
     * <CODE>filterValue</CODE> parameter is malformed.
	 *
	 * @see UsageRecFilterValue
	 */
	public void setUsageRecFilterValue(
        UsageRecFilterValue filterValue) 
		throws java.lang.IllegalArgumentException
    {
        if(filterValue == null)
        {
            throw new java.lang.IllegalArgumentException(
                "UsageRecFilterValue parameter is invalid");
        }

        setDirtyAttribute(USAGE_REC_FILTER_VALUE);
        this.usageRecFilter = (UsageRecFilterValue) filterValue.clone();
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
     * {@link javax.oss.ipb.producer.JVTProducerSession#getProducerKeyTypes} method.
     *
     * The producerKeyType parameter can be null. The type of the Producer
     * Key returned will then be dependent upon the implementation.
     * 
     * @return The created key object is returned.
     * @exception java.lang.IllegalArgumentException Thrown if the specified
     * key type is invalid
     *
     */
    public ProducerKey makeProducerKey(
        String producerKeyType)
        throws java.lang.IllegalArgumentException
    {
        ClassLoader cl = getClass().getClassLoader();
        try
        {
            Class producerKeyClass = cl.loadClass(producerKeyType);
            return (ProducerKey) (producerKeyClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown ProducerKey Type" + e);
        }
        
    }
    

	/**
	 * Get the <CODE>producerKey</CODE> filterable attribute value.
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
        checkAttribute(PRODUCER_KEY);
        return (ProducerKey) producerKey.clone();
        
    }
    

	/**
	 * Set the <CODE>producerKey</CODE> filterable attribute value.
     * If this is specified, all reports generated by this specific activity
     * will be considered for filtering.
     * <p>
     * If omitted, all reports in the system will be considered for filtering.
	 * 
	 * @param key The value of <CODE>Producer</CODE> Key.
	 *
	 * @exception java.lang.IllegalArgumentException If the passed
     * <CODE>key</CODE> is malformed.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
	public void setProducerKey(ProducerKey key) 
		throws java.lang.IllegalArgumentException
    {
        if (key == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ProducerKey parameter value is invalid");
        }

        setDirtyAttribute(PRODUCER_KEY);
        this.producerKey = (ProducerKey) key.clone();
        
    }


    /**
     * Configuration of AttributeManager and AttributeAccess
     */
    
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.usageFilterAttributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.settableUsageFilterAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager()
    {
        return usageFilterAttributeManager;
    }

    protected AttributeManager makeAttributeManager()
    {
        usageFilterAttributeManager = new AttributeManager();
        return usageFilterAttributeManager;
    }

    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        QueryByUsageRecFilterImpl clone = null;

        clone = (QueryByUsageRecFilterImpl) super.clone();

        /**
          if(subscriptionFilter != null)
          {
          clone.subscriptionFilter = subscriptionFilter.clone();
          }
          */

        if(producerKey != null)
        {
            clone.producerKey = (ProducerKey) producerKey.clone();
        }
            

        if(usageRecFilter != null)
        {
            clone.usageRecFilter =
                (UsageRecFilterValue) usageRecFilter.clone();
        }
            
        
        return clone;
    }

    /**
     * Compare two instances of this class.
     *
     * @param other The other instance.
     *
     * @return boolean If they are equal returns <CODE>true</CODE>.
     * Otherwise returns <CODE>false</CODE>.
     *
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof QueryByUsageRecFilterImpl))
        {
            return false;
        }

        QueryByUsageRecFilterImpl realOther =
            (QueryByUsageRecFilterImpl) other;

        if(realOther.hashCode() == hashCode())
        {
            boolean result = true;
            
            if(result == true)
            {
                if(producerKey != null)
                {
                    result =
                        producerKey.equals(realOther.producerKey);
                }
                else
                {
                    result = (producerKey == realOther.producerKey);
                }
            }

            if(result == true)
            {
                if(usageRecFilter != null)
                {
                    result =
                        usageRecFilter.equals(realOther.usageRecFilter);
                }
                else
                {
                    result = (usageRecFilter == realOther.usageRecFilter);
                }
            }
            
            return result;
        }

        return false;
    }

    /**
     * It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode = -1;

        if(producerKey != null)
        {
            hashCode = producerKey.hashCode();
        }

        if(usageRecFilter != null)
        {
            hashCode ^= usageRecFilter.hashCode();
        }
        
        return hashCode;
    }

    /**
     * Returns a string representation of this instance.
     * @return string representation of this instance.
     */
    public String toString()
    {
        return "producerKey: " + producerKey
            + ", " + "usageRecFilter: " + usageRecFilter
            ;
    }

    /**
     * Returns an empty String Array. Always.
     * Since, it is not possible to generalize the serilization for each API,
     * each API is expected to
     * derive sub class this, to provide implementation for
     * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
     * and {@link javax.oss.SerializerFactory#makeSerializer}.
     * In this implementation, these methods work as follows:
     * <UL>
     * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
     * Always returns a ZERO length string array.
     * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
     * Always throws an {@link IllegalArgumentException}.
     * </UL>
     */
    public String[] getSupportedSerializerTypes()
        throws java.lang.IllegalStateException
    {
        return noSupportedSerializerTypes;
    }
    
    /**
     * Always throws IllegalArgumentException.
     * Since, it is not possible to generalize the serilization for each API,
     * each API is expected to
     * derive sub class this, to provide implementation for
     * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
     * and {@link javax.oss.SerializerFactory#makeSerializer}.
     * In this implementation, these methods work as follows:
     * <UL>
     * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
     * Always returns a ZERO length string array.
     * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
     * Always throws an {@link IllegalArgumentException}.
     * </UL>
     */
    
    public Serializer makeSerializer( String serializerType )
        throws java.lang.IllegalArgumentException
    {
        throw new java.lang.IllegalArgumentException(
            "makeSerializer() is not supported in the " +
            QueryByUsageRecFilter.class.getName() +
            "Class.");
    }


}
