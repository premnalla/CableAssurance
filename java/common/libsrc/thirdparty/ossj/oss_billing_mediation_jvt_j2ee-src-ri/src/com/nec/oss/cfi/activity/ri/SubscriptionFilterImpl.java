package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Implementation of the SubscriptionFilter interface.
 * This class allows an Activity to specify the subset of
 * data that is of interest to the client.
 */

public class SubscriptionFilterImpl
implements javax.oss.cfi.activity.SubscriptionFilter
{

    /**
     * Members
     */

    protected String dataSource;
    protected String dataType;
    
    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
    {
        SubscriptionFilterImpl clone = null;
        try
        {
            clone = (SubscriptionFilterImpl) super.clone();
        }
        
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
            
        }

        return clone;
    }
    
    /**
     * Get the type of data to filter
	 * @return String Type of data being filtered
     * @exception  java.lang.IllegalStateException Thrown if the data type has
     * not been set.
     */
    public String getDataType()
        throws java.lang.IllegalStateException
    {
        if(dataType == null)
        {
            throw new java.lang.IllegalStateException(
                "dataType attribute was not set");
        }
        
        return dataType;
        
    }
    
    /**
     * Set the type of data to filter
	 * @param dataType Type of data being filtered
     * @throws java.lang.IllegalArgumentException if <CODE>dataType</CODE>
     * is null or invalid
     */
    public void setDataType(
        String dataType)
        throws java.lang.IllegalArgumentException
    {
        if(dataType == null)
        {
            throw new java.lang.IllegalArgumentException(
                "dataType parameter is not valid");
        }
        
        this.dataType = dataType;
    }

    /**
     * Get the source of data to filter
	 * @return String Source of data being filtered
     * @exception  java.lang.IllegalStateException Thrown if the data type has
     * not been set.
     */
    public String getDataSource()
        throws java.lang.IllegalStateException
    {
        if(dataSource == null)
        {
            throw new java.lang.IllegalStateException(
                "dataSource attribute was not set");
        }
        
        return dataSource;
    }
    
    /**
     * Set the source of data to filter
	 * @param dataSource Source of data being filtered
     * @throws java.lang.IllegalArgumentException if <CODE>name</CODE>
     * is null or invalid
     */
    public void setDataSource(
        String dataSource)
        throws java.lang.IllegalArgumentException
    {
        if(dataSource == null)
        {
            throw new java.lang.IllegalArgumentException(
                "dataSource parameter is not valid");
        }
 
        this.dataSource = dataSource;
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

        if(!(other instanceof SubscriptionFilterImpl))
        {
            return false;
        }

        SubscriptionFilterImpl localOther = 
							(SubscriptionFilterImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if (dataType != null)
			{
				if  (!(dataType.
								equals(localOther.dataType)))
				{
					return false;
				}
			}
			else if(dataType != localOther.dataType)
			{
				return false;
			}

			if (dataSource != null)
			{
				if  (!(dataSource.
								equals(localOther.dataSource)))
				{
					return false;
				}
			}
			else if(dataSource != localOther.dataSource)
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

        if(dataType != null)
        {
            hashCode = dataType.hashCode();
        }

        if(dataSource != null)
        {
            hashCode ^= dataSource.hashCode();
        }

		return hashCode;
	}
}
