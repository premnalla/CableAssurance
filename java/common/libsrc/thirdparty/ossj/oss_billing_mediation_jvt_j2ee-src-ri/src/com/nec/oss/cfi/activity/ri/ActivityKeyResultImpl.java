
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
import javax.oss.ManagedEntityKey;
import javax.oss.cfi.activity.ActivityKeyResult;
import javax.oss.cfi.activity.ActivityKey;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityKeyResultImpl;


/**
 * An implementation of ActivityKeyResult interface.
 * This is the key result value representation for a best effort operation
 * on a Activity by its key, in OSS/J.
 */
public class ActivityKeyResultImpl
extends ManagedEntityKeyResultImpl
implements ActivityKeyResult
{

    /** --------------------------------------------------------
     * Implementation of methods from javax.oss.cfi.activity.ActivityKeyResult
     * -------------------------------------------------------*/

    /**
     * Default constructor
     */
    public ActivityKeyResultImpl()
    {
        
    }
    
    /**
     * Overloaded constructor
     */
    public ActivityKeyResultImpl(ActivityKey activityKey,
                                 boolean success,
                                 Exception exception)
    {
        setManagedEntityKey(activityKey);
        setSuccess(success);
        setException(exception);
    }

	/**
	 * Get the value representation of <CODE>ActivityKey</CODE>.
	 * 
	 * @return The <CODE>ActivityKey</CODE> value associated with this instance.
     *
     * @exception java.lang.IllegalStateException - thrown if the key has
     * not been set
	 *
	 * @see javax.oss.cfi.activity.ActivityKey
	 */
    public ActivityKey getActivityKey()
        throws java.lang.IllegalStateException
    {
        ManagedEntityKey key = getManagedEntityKey();

        if(key == null)
        {
            throw new java.lang.IllegalStateException(
                "managedEntityKey attribute was not set");
        }
        
        return (ActivityKey) key;
    }
    
    
	/**
	 * Sets the <CODE>activityKey</CODE> manangedEntityKey attribute value
     * for this instance.
	 * 
	 * @param theKey <CODE>ManagedEntityKey</CODE> value of this
     * instance, which is a <CODE>ActivityKey</CODE> instance.
     * @exception java.lang.IllegalArgumentException - thrown if the key is
     * null
	 *
	 * @see  javax.oss.cfi.activity.ActivityKey
	 */
    public void setActivityKey(ActivityKey theKey)
        throws java.lang.IllegalArgumentException
    {
        if(theKey == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Key parameter value is not valid");
        }
        
        setManagedEntityKey(theKey);
    }
}
