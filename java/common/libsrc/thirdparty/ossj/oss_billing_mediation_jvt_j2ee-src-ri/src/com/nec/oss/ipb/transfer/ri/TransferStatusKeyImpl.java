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

import javax.oss.ApplicationContext;
import javax.oss.ipb.transfer.TransferStatusKey;
import javax.oss.ipb.transfer.TransferStatusPrimaryKey;


/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityKeyImpl;

/**
 * Implementaion of TransferStatusKey interface.
 *
 * A <CODE>TransferStatusKey</CODE> is a unique identifier attribute,
 * present for each
 * <CODE>TransferStatus</CODE> entity represented by
 * <CODE>TransferStatusValue</CODE> object.
 * It is used to identify a single <CODE>TransferStatus</CODE> entity.
 * <p>
 * A <CODE>TransferStatusKey</CODE> value object is returned to the client during
 * setting up of Data Transfer Session between a client and <CODE>Producer</CODE>.
 *
 * <p>
 * The Type, ApplicationDN and ApplicationContext
 * are included in super interface <CODE>ManagedEntityKey</CODE>, because
 * a <CODE>primaryKey</CODE> is only unique within an
 * application instance as given by the ApplicationDN running
 * in a given naming system as given by the ApplicationContext::URL
 *
 * <p>
 * The <CODE>TransferStatusPrimaryKey</CODE> is the <CODE>primaryKey</CODE>
 * representation of the <CODE>TransferStatusKey</CODE>. This interface is also
 * the factory for creating the instances of <CODE>TransferStatusPrimaryKey</CODE>.
 *
 * @see javax.oss.ipb.transfer.TransferStatusPrimaryKey
 * @see javax.oss.ipb.transfer.TransferStatusValue
 * @see javax.oss.ipb.producer.JVTProducerSession
 *
 */
public class TransferStatusKeyImpl
extends ManagedEntityKeyImpl
implements TransferStatusKey
{
    /**
     * Members
     */
    TransferStatusPrimaryKey primaryKey;

    /**
     * Operations
     */

    
    /**
     * Default constructor.
     */
    public TransferStatusKeyImpl()
    {
    	// Perform no operation    
    }

    /**
     * Overloaded constructor.
	 * @param anApplicationContext Associated application context
	 * @param appTypeDN Name of the application context
	 * @param type Type of transfer
	 * @param primaryKey Associated primary key
     */
    public TransferStatusKeyImpl(
        ApplicationContext anApplicationContext,
        String appTypeDN,
        String type,
        Object primaryKey)
    {
        super(anApplicationContext, appTypeDN, type, primaryKey );
    }
    

    /**
     * Make an empty instance of TransferStatus's primary key.
	 *
	 * @return Generated empty primary key
     */
    public TransferStatusPrimaryKey makeTransferStatusPrimaryKey()
    {
        return (TransferStatusPrimaryKey) makePrimaryKey();
    }
    

    /**
     * Get TransferStatus's primary key.
	 *
	 * @return Associated primary key
	 *
	 * @exception java.lang.IllegalStateException If the
     * primaryKey is not yet set.
	 *
     */
    public TransferStatusPrimaryKey getTransferStatusPrimaryKey()
        throws java.lang.IllegalStateException
    {
        Object obj = getPrimaryKey();
        
        if((obj != null) &&
           (obj instanceof TransferStatusPrimaryKeyImpl))
        {
            return (TransferStatusPrimaryKey) obj;
        }

        throw new java.lang.IllegalStateException(
            "The primaryKey is not yet initialized");
    }
    

    /**
     * Set TransferStatus's primary key.
     *
     * @param primaryKey Input primary key value
     *
	 * @exception java.lang.IllegalArgumentException If the
     * <CODE>primaryKey</CODE> parameter value is malformed.
     */
    public void setTransferStatusPrimaryKey(TransferStatusPrimaryKey primaryKey)
        throws java.lang.IllegalArgumentException
    {
        if( (primaryKey == null) ||
            (!(primaryKey instanceof TransferStatusPrimaryKeyImpl)))
        {
            throw new java.lang.IllegalArgumentException(
                "The primaryKey argument is null or " +
                "not of TransferStatusPrimaryKeyImpl Type");
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
        return new TransferStatusPrimaryKeyImpl();
    }

    /**
     * Deep copy of this key
     * 
     * @return deep copy of this key
     */
    public Object clone()
    {

        TransferStatusKeyImpl myClone = (TransferStatusKeyImpl)super.clone();

        if(getPrimaryKey() != null)
        {
            myClone.setPrimaryKey(
                ((TransferStatusKeyImpl)getPrimaryKey()));
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

        if(!(other instanceof TransferStatusKeyImpl))
        {
            return false;
        }

        TransferStatusKeyImpl realOther =
            (TransferStatusKeyImpl) other;

        if(realOther.hashCode() == hashCode())
        {
            boolean result = true;
            
            if(result == true)
            {
                if(primaryKey != null)
                {
                    result =
                        primaryKey.equals(realOther.primaryKey);
                }
                else
                {
                    result = (primaryKey == realOther.primaryKey);
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
        if(primaryKey != null)
        {
            hashCode = primaryKey.hashCode();
        }

        return hashCode;
    }
}
