package com.nec.oss.ipb.transfer.ri;


/**
 * Title:        JSR130 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec. imports.
 */

import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.transfer.TransferStatusPrimaryKey;
import com.nec.oss.ipb.producer.ri.ProducerKeyImpl;

/**
 * Implementation of TransferStatusPrimaryKey interface.
 *
 * This interface encapsulates the <CODE>PrimaryKey</CODE>
 * of a <CODE>TransferStatus</CODE> entity.
 *
 * <p>
 * The <CODE>PrimaryKey</CODE> encapsulation allows a <CODE>TransferStatus</CODE>
 * entity to be
 * uniquely identified with just one or
 * a combination of two or more attributes and their values.
 * This insulates the API as well as the implementations from having to
 * change each time a new attribute must be included to the definition of
 * its <CODE>PrimaryKey</CODE>.
 * <p>
 * This is the base interface for all <CODE>TransferStatus</CODE> primary key interfaces,
 * with just the DN being defined as the key attribute for now.
 * Any derived interfaces are free to add their own attributes.
 * <p>
 * The primary key for a transfer session consists of a unique
 * session id within the operating domain, plus <CODE>ProducerKey</CODE>,
 * which are involved in the data transfer.
 *
 * @see TransferStatusKey
 * @see javax.oss.ipb.producer.ProducerKey
 *
 */
public class TransferStatusPrimaryKeyImpl
implements TransferStatusPrimaryKey
{
    /**
     * Members
     */
	private String id;
    private long transferStatusSessionId;
	private boolean sessionIdSet=false;
	private ProducerKey producerKey=null;

    /**
     * Operations
     */

    /**
     * Deep copy of this key
     * 
     * @return deep copy of this primary key
     */
    public Object clone()
    {
        TransferStatusPrimaryKeyImpl clone = null;
        try
        {
            clone = (TransferStatusPrimaryKeyImpl) super.clone();
        }
        
        catch (CloneNotSupportedException ce)
        {
//            System.err.println("Cloning of TransferStatusPrimaryKey failed");

            clone = null;
        }


		if (producerKey != null)
		{
			clone.producerKey = (ProducerKey) producerKey.clone();
		}


        return clone;
    }
    

    /**
     * Get the TransferStatus's Session ID
     *
     * @return Session ID value
     * @exception java.lang.IllegalStateException If the
     * <CODE>sessionId</CODE> attribute is not yet set.
     *
     */
    public long getSessionId()
        throws java.lang.IllegalStateException
    {
        if(!sessionIdSet)
        {
            throw new java.lang.IllegalStateException(
                "sessionId attribute was not set");
        }

        return transferStatusSessionId;
    }
    

    /**
     * Set the TransferStatus's Session ID
     *
     * @param value representing the session ID
     * @exception java.lang.IllegalArgumentException If the
     * <CODE>sessionId</CODE> parameter is malformed.
     */
    public void setSessionId(long sessionId)
        throws java.lang.IllegalArgumentException
    {
        this.transferStatusSessionId = sessionId;
		sessionIdSet = true;
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
		return (new ProducerKeyImpl());
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
     * @exception java.lang.IllegalStateException If the
     * <CODE>producerKey</CODE> attribute is not yet set.
	 */
	public ProducerKey getProducerKey()
        throws java.lang.IllegalStateException
    {
        if(producerKey == null)
        {
            throw new java.lang.IllegalStateException(
                "ProducerKey attribute is not set");
        }
        return (ProducerKey) producerKey.clone();
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
     * @exception java.lang.IllegalArgumentException If the
     * <CODE>producerKey</CODE> parameter is malformed.
	 */
	public void setProducerKey(ProducerKey key) 
		throws java.lang.IllegalArgumentException
    {
		if(key == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ProducerKey parameter value is not valid");
        }

        this.producerKey = (ProducerKey) key.clone();
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

        if(!(other instanceof TransferStatusPrimaryKeyImpl))
        {
            return false;
        }

        TransferStatusPrimaryKeyImpl otherPK = (TransferStatusPrimaryKeyImpl) other;

        if(otherPK.hashCode() == hashCode())
        {

			if((transferStatusSessionId == otherPK.transferStatusSessionId) &&
			   (sessionIdSet == otherPK.sessionIdSet))
			{
				boolean returnValue = true;

				if(returnValue == true)
				{
            		if(id != null)
            		{
                		returnValue = id.equals(otherPK.id);
            		}
            		else
            		{
                		returnValue = (id == otherPK.id);
            		}
				}
	
				if(returnValue == true)
				{
	            	if(producerKey != null)
	            	{
	                	returnValue = producerKey.equals(otherPK.producerKey);
	            	}
	            	else
	            	{
	                	returnValue = (producerKey == otherPK.producerKey);
	            	}
				}
			return returnValue;
        	}
		}	
       	return false;
    }

    /**
     * Returns a string representation of this instance.
     * 
     * @return String value of this object
     */
    public String toString()
    {
        return "id: " + id;
    }

    /**
     * Get the hashCode for the object.
     * <p>
     * It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     * 
     * @return hashCode for this object
     */
    public int hashCode()
    {
        int hashCode = -1;


        if(id != null)
        {
            hashCode = id.hashCode();
        }


        if(producerKey != null)
        {
            hashCode = producerKey.hashCode();
        }
        
        return hashCode;
    }
}
