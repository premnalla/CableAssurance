
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

import javax.oss.ApplicationContext;
import javax.oss.cfi.activity.ActivityKey;
import javax.oss.cfi.activity.ActivityPrimaryKey;


/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityKeyImpl;
import com.nec.oss.cfi.activity.ri.ActivityPrimaryKeyImpl;

/**
 * Implementaion of ActivityKey interface.
 * This is the base key value representation for a Activity in
 * OSS/J.
 */
public class ActivityKeyImpl
extends ManagedEntityKeyImpl
implements ActivityKey
{

    /**
     * Operations
     */
    

    /**
     * Default constructor.
     */
    public ActivityKeyImpl()
    {
        
    }

    /**
     * Overloaded constructor.
     */
    public ActivityKeyImpl(
        ApplicationContext anApplicationContext,
        String appTypeDN,
        String type,
        Object primaryKey)
    {
        super(anApplicationContext, appTypeDN, type, primaryKey );
    }
    

    /**
     * Make an empty instance of Activity's primary key.
     *
     * @return a new instance of ActivityPrimaryKey
     */
    public ActivityPrimaryKey makeActivityPrimaryKey()
    {
        return (ActivityPrimaryKey) makePrimaryKey();
    }
    

    /**
     * Get Activity's primary key.
     *
     * @return ActivityPrimaryKey of the object
     *
     * @exception java.lang.IllegalStateException - thrown if the key has
     * not been set
     */
    public ActivityPrimaryKey getActivityPrimaryKey()
        throws java.lang.IllegalStateException
    {
        Object obj = getPrimaryKey();
        
        if((obj != null) &&
           (obj instanceof ActivityPrimaryKeyImpl))
        {
            return (ActivityPrimaryKey) obj;
        }

        throw new java.lang.IllegalStateException(
            "The primaryKey is not yet initialized");
    }
    

    /**
     * Set Activity's primary key.
     *
     * @param primaryKey ActivityPrimaryKey of the object
     *
     * @exception java.lang.IllegalArgumentException - thrown if the key is
     * null
     */
    public void setActivityPrimaryKey(ActivityPrimaryKey primaryKey)
        throws java.lang.IllegalArgumentException
    {
        if( (primaryKey == null) ||
            (!(primaryKey instanceof ActivityPrimaryKeyImpl)))
        {
            throw new java.lang.IllegalArgumentException(
                "The primaryKey argument is null or " +
                "not of ActivityPrimaryKeyImpl Type");
        }

        setPrimaryKey(primaryKey);
        
    }

    /** --------------------------------------------------------------
     * Implementation for methods of ManagedEntityKeyImpl
     * ------------------------------------------------------------*/
    
    /**
     * Manufacture a PrimaryKey
     * 
     * @return PrimaryKey implementation class
     */
    public Object makePrimaryKey()
    {
        return new ActivityPrimaryKeyImpl();
    }

    /**
     * Deep copy of this key
     * 
     * @return deep copy of this key
     */
    public Object clone()
    {

        ActivityKeyImpl myClone = (ActivityKeyImpl)super.clone();
	
        if(getPrimaryKey() != null)
        {
            myClone.setPrimaryKey(
                ((ActivityPrimaryKeyImpl)getPrimaryKey()));
        }
        
        return myClone;
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

        if(!(other instanceof ActivityKeyImpl))
        {
            return false;
        }
		ActivityKeyImpl localOther =
					(ActivityKeyImpl) other;

        boolean result = true;

		if (!super.equals(localOther))
		{
			result = false;
		}
        
        return result;
    }

    /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode=-1;

        return hashCode;
    }
}
