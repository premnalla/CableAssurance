
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
import javax.oss.ipb.producer.ProducerPrimaryKey;

/**
 * RI imports.
 */
import com.nec.oss.cfi.activity.ri.ActivityPrimaryKeyImpl;

/**
 * An implementation of
 * {@link javax.oss.ipb.producer.ProducerPrimaryKey} interface.
 * The <CODE>PrimaryKey</CODE> encapsulation  allows a <CODE>Producer</CODE> 
 * entity to be uniquely identified with just one or
 * a combination of two or more attributes and their values.
 * This insulates the API as well as the implementations from having to
 * change each time a new attribute must be included to the definition of
 * its <CODE>PrimaryKey</CODE>.
 * <p>
 * This is the base for all <CODE>Producer</CODE> primary key classes
 * with just the DN being defined as the key attribute for now.
 * Any derived interfaces are free to add their own attributes.
 *
 * @see javax.oss.ipb.producer.ProducerKey
 *
 */

public class ProducerPrimaryKeyImpl
extends ActivityPrimaryKeyImpl
implements ProducerPrimaryKey
{

    /**
     * Declared for EJB2.0 compliance.
     */
    public String producerType;
    
    /**
     * Default Constructor - does nothing
     */
    public ProducerPrimaryKeyImpl() 
    {
    }

    /**
     * Overloaded Constructor - initializes using the input args
     * @param id value used for the Activity ID
     * @param producerType  value used for the Producer Type
     */
    public ProducerPrimaryKeyImpl(String id, String producerType) 
    {
        this.activityId = id;
        this.producerType = producerType;
    }
    

   /**
     * Return the type of the Producer.
     *
     * @return Type of the associated Producer
     *
     * @exception java.lang.IllegalStateException If this attribute value is
     * not yet set.
     *
     */
    public String getProducerType()
        throws java.lang.IllegalStateException
    {
        if(producerType == null)
        {
            throw new java.lang.IllegalStateException(
                "ProducerType attributre is not set");
            
        }
        
        return producerType;
    }

    /**
     * Set the type of the Producer.
     *
     * @param producerType The type of the associated Producer
     *
     * @exception java.lang.IllegalArgumentException If the supplied
     * <CODE>producerType</CODE> parameter is malformed.
     *
     */
    public void setProducerType(String producerType)
        throws java.lang.IllegalArgumentException
    {
        if(producerType == null)
        {
            throw new java.lang.IllegalStateException(
                "ProducerType attributre is not set");
        }
            
        this.producerType = producerType;
    }
    
    /**
     * Deep copy of this key
     * 
     * @return deep copy of this primary key
     */
    public Object clone()
    {
        ProducerPrimaryKeyImpl clone = null;
        clone = (ProducerPrimaryKeyImpl) super.clone();
        
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

        if(!(other instanceof ProducerPrimaryKeyImpl))
        {
            return false;
        }

        ProducerPrimaryKeyImpl otherPK = (ProducerPrimaryKeyImpl) other;

        boolean result = false;
        
        if(otherPK.hashCode() == hashCode())
        {
            if(activityId != null)
            {
                result = activityId.equals(otherPK.activityId);
            }
            else
            {
                result = (activityId == otherPK.activityId);
            }

            if(result)
            {
                if(producerType != null)
                {
                    result = producerType.equals(otherPK.producerType);
                }
                else
                {
                    result = (producerType == otherPK.producerType);
                }
            }
               
        }

        return result;
    }

    /**
     * Returns a string representation of this instance.
     * @return string representation of this instance
     */
    public String toString()
    {
        return "activityId: " + activityId + 
            " | producerType: " + producerType
            ;
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

        if(producerType != null)
        {
            hashCode ^= producerType.hashCode();
        }
        
        return hashCode;
    }
}
