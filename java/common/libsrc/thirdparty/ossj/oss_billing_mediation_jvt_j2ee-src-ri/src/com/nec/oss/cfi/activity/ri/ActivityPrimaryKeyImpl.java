
package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Standard imports.
 */

/**
 * Spec. imports.
 */

import javax.oss.cfi.activity.ActivityPrimaryKey;

/**
 * An implementation fo ActivityPrimaryKey interface.
 * This is the primary key value representation for a Activity in
 * OSS/J.
 */
public class ActivityPrimaryKeyImpl
implements ActivityPrimaryKey
{

    /**
     * String used to indentify this Activity
     */
    public String activityId;

    /**
     * Operations
     */
    
    /**
     * Deep copy of this primary key
     * 
     * @return deep copy of this primary key
     */
    public Object clone()
    {
        ActivityPrimaryKeyImpl clone = null;
        try
        {
            clone = (ActivityPrimaryKeyImpl) super.clone();
        }
        
        catch (CloneNotSupportedException ce)
        {
            System.err.println("Cloning of ActivityPrimaryKey failed");

            clone = null;
        }

        return clone;
    }
    
    
    /**
     * Get the Activity's primary key value.
     *
     * @return primary ID value for the Activity
     * @exception java.lang.IllegalStateException - thrown if the ID has not
     * been set
     */
    public String getActivityId()
        throws java.lang.IllegalStateException
    {
        if(activityId == null)
        {
            throw new java.lang.IllegalStateException(
                "ID attribute was not set");
        }

        return activityId;
    }
    

    /**
     * Set the Activity's primary key value.
     *
     * @param primaryKeyValue ID value for the Activity
     * @exception java.lang.IllegalArgumentException - thrown if the param
     * is null
     */
    public void setActivityId(String primaryKeyValue)
        throws java.lang.IllegalArgumentException
    {
        if(primaryKeyValue == null)
        {
            throw new java.lang.IllegalArgumentException
                ("primaryKeyValue parameter is not valid");
        }

        this.activityId = primaryKeyValue;
        
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

        if(!(other instanceof ActivityPrimaryKeyImpl))
        {
            return false;
        }

        ActivityPrimaryKeyImpl otherPK = (ActivityPrimaryKeyImpl) other;

        if(otherPK.hashCode() == hashCode())
        {
            if(activityId != null)
            {
                return activityId.equals(otherPK.activityId);
            }
            else
            {
                return (activityId == otherPK.activityId);
            }
        }

        return false;
    }

    /**
     * Returns a string representation of this instance.
     * @return string representation of this instance
     */
    public String toString()
    {
        return "activityId: " + activityId;
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

        if(activityId != null)
        {
            hashCode = activityId.hashCode();
        }
        
        return hashCode;
    }
}
