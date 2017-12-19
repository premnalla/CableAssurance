package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Standard imports
 */
import java.util.Calendar;

/**
 * Spec imports 
 */
import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import javax.oss.cfi.activity.QueryActivityReportData;
import javax.oss.cfi.activity.SubscriptionFilter;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.AttributeAccessImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;

/**
 * This is an implementation of
 * {@link javax.oss.cfi.activity.QueryActivityReportData} interface.
 *
 */

public class QueryActivityReportDataImpl
extends AttributeAccessImpl
implements javax.oss.cfi.activity.QueryActivityReportData
{
    private static AttributeManager queryAttributeManager;
    private static String[] noSupportedSerializerTypes = new String[0];

    /**
     * Member data.
     */
    /**
     * The subscription object filter. This must be serializable.
     */
    private SubscriptionFilter subscriptionFilter;
    /**
     * The begin date for the record collection timestamp.
     */
    private Calendar beginDate;
    /**
     * The end date for the record collection timestamp.
     */
    private Calendar endDate;
    
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames =
    {
        SUBSCRIPTION_FILTER,
        BEGIN_DATE,
        END_DATE
    };
    
    // writeable attributes
    private static final String[] settableAttributeNames =
    {
        SUBSCRIPTION_FILTER,
        BEGIN_DATE,
        END_DATE
    };
    

    /**
     * Configuration of AttributeManager and AttributeAccess
     */
    
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.attributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.settableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager()
    {
        return queryAttributeManager;
    }

    protected AttributeManager makeAttributeManager()
    {
        queryAttributeManager = new AttributeManager();
        return queryAttributeManager;
    }

    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        QueryActivityReportDataImpl clone = null;

        try
        {
            clone = (QueryActivityReportDataImpl) super.clone();

            if(subscriptionFilter != null)
            {
                clone.subscriptionFilter = (SubscriptionFilter) subscriptionFilter.clone();
            }

            if(beginDate != null)
            {
                clone.beginDate = (Calendar) beginDate.clone();
            }
            
            if(endDate != null)
            {
                clone.endDate = (Calendar) endDate.clone();
            }
            
        }
        
        catch ( CloneNotSupportedException e )
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
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

        if(!(other instanceof QueryActivityReportDataImpl))
        {
            return false;
        }

        QueryActivityReportDataImpl realOther =
            (QueryActivityReportDataImpl) other;

        if(realOther.hashCode() == hashCode())
        {
            boolean result = true;
            
            if(result == true)
            {
                if(subscriptionFilter != null)
                {
                    result =
                        subscriptionFilter.equals(realOther.subscriptionFilter);
                }
                else
                {
                    result = (subscriptionFilter == realOther.subscriptionFilter);
                }
            }

            if(result == true)
            {
                if(beginDate != null)
                {
                    result =
                        beginDate.equals(realOther.beginDate);
                }
                else
                {
                    result = (beginDate == realOther.beginDate);
                }
            }
            
            if(result == true)
            {
                if(endDate != null)
                {
                    result =
                        endDate.equals(realOther.endDate);
                }
                else
                {
                    result = (endDate == realOther.endDate);
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
     * @return hash code for this instance
     */
    public int hashCode()
    {
        int hashCode = -1;

        if(subscriptionFilter != null)
        {
            hashCode = subscriptionFilter.hashCode();
        }

        if(beginDate != null)
        {
            hashCode ^= beginDate.hashCode();
        }
        
        if(endDate != null)
        {
            hashCode ^= endDate.hashCode();
        }
        
        return hashCode;
    }

    /**
     * Returns a string representation of this instance.
     * @return string representation of this instance.
     */
    public String toString()
    {
        return "subscriptionFilter: " + subscriptionFilter
            + ", " + "beginDate: " + beginDate
            + ", " + "endDate: " + endDate
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
     * @return Empty array for all invocations
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
     * @return New instance of Serializer 
     * @exception java.lang.IllegalArgumentException Thrown every time
     */
    
    public Serializer makeSerializer( String serializerType )
        throws java.lang.IllegalArgumentException
    {
        throw new java.lang.IllegalArgumentException(
            "makeSerializer() is not supported in the " +
            RecordDescriptorImpl.class.getName() +
            "Class.");
    }

    /**
     * Get the current subscription filter.
     *
     * @return current subscription filter
     * @exception java.lang.IllegalStateException Thrown if the subscription
     * filter is not set
     */ 
    public SubscriptionFilter getSubscriptionFilter()
        throws java.lang.IllegalStateException
    {
        checkAttribute(SUBSCRIPTION_FILTER);
        return subscriptionFilter;
    }
    
    
    /**
     * This serves as the filter, narrowing the set of report data to
     * the type of data that invoking client is interested. If omitted,
     * no filtering will be performed based on this attribute. The details
     * of this filter is left up to individual implemeantions.
     * @param subscriptionFilter  Subscription filter for this query
     * @exception java.lang.IllegalArgumentException Thrown if the subscription
     * filter is not valid
     */
    public void setSubscriptionFilter(
        SubscriptionFilter subscriptionFilter)
        throws java.lang.IllegalArgumentException
    {
        if (subscriptionFilter == null)
        {
            throw new java.lang.IllegalArgumentException(
                "SubscriptionFilter parameter value is invalid");
        }

        setDirtyAttribute(SUBSCRIPTION_FILTER);
        this.subscriptionFilter = subscriptionFilter;
    }
    
    /**
     * Get the current begin date
     *
     * @return begin date for the query
     * @exception java.lang.IllegalStateException Thrown if the begin date
     * is not set
     */
    public Calendar getBeginDate()
        throws java.lang.IllegalStateException
    {
        checkAttribute(BEGIN_DATE);
        
        return (Calendar) beginDate.clone();
    }
    
    
    /**
     * If specified, all report data that were created on or after this date
     * will be considered. If omitted, all the data created on or before the
     * specified endDate will be considered.
     * @param beginDate  Begin date for this query
     * @exception java.lang.IllegalArgumentException Thrown if the begin date
     * is not valid
     */
    public void setBeginDate(
        Calendar beginDate)
        throws java.lang.IllegalArgumentException
    {
        if (beginDate == null)
        {
            throw new java.lang.IllegalArgumentException(
                "BeginDate parameter value is invalid");
        }

        setDirtyAttribute(BEGIN_DATE);
        this.beginDate = (Calendar) beginDate.clone();
    }
    


    /**
     * If specified, all report data that were created on or before this date
     * will be considered. If omitted, all the data created on or after the
     * specified beginDate will be considered.
     * @return current end date
     * @exception java.lang.IllegalStateException Thrown if the end date
     * is not set
     */
    public Calendar getEndDate()
        throws java.lang.IllegalStateException
    {
        checkAttribute(END_DATE);
        
        return (Calendar) endDate.clone();
    }
    
    /**
     * If specified, all report data that were created on or before this date
     * will be considered. If omitted, all the data created on or after the
     * specified beginDate will be considered.
     *
     * @param endDate  End date for this query
     * @exception java.lang.IllegalArgumentException Thrown if the end date
     * is not valid
     */ 
    public void setEndDate(
        Calendar endDate)
        throws java.lang.IllegalArgumentException
    {
        if (endDate == null)
        {
            throw new java.lang.IllegalArgumentException(
                "EndDate parameter value is invalid");
        }

        setDirtyAttribute(END_DATE);
        this.endDate = (Calendar) endDate.clone();
    }
}
