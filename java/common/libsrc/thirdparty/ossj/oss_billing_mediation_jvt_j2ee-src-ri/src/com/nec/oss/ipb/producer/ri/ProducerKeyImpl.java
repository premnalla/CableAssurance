
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
import javax.oss.ApplicationContext;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerPrimaryKey;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityKeyImpl;

import com.nec.oss.cfi.activity.ri.ActivityKeyImpl;

import com.nec.oss.ipb.producer.ri.ProducerPrimaryKeyImpl;

/**
 * Implementation for
 * javax.oss.ipb.producer.ProducerKey interface.
 * A <CODE>ProducerKey</CODE> is a unique identifier attribute, present for each
 * <CODE>ProducerValue</CODE> entity. It is used to identify a single
 * <CODE>Producer</CODE> entity.
 *
 * <p>
 * The Type, ApplicationDN and ApplicationContext
 * are included in super interface <CODE>ManagedEntityKey</CODE>, because
 * a <CODE>primaryKey</CODE> is only unique within an
 * application instance as given by the ApplicationDN running
 * in a given naming system as given by the ApplicationContext::URL
 * <p>
 * The <CODE>ProducerPrimaryKey</CODE> is the <CODE>primaryKey</CODE>
 * representation of the <CODE>ProducerKey</CODE>. This class is also
 * the factory for creating the instances of <CODE>ProducerPrimaryKey</CODE>.
 *
 * @see javax.oss.ipb.producer.ProducerPrimaryKey
 * @see javax.oss.ipb.producer.ProducerValue
 * @see javax.oss.ipb.producer.JVTProducerSession
 */

public class ProducerKeyImpl
extends ActivityKeyImpl
implements javax.oss.ipb.producer.ProducerKey
{

    /**
     * Member Data
     */
    
    /**
     * Operations
     */
    
    /**
     * Default constructor - does nothing
     */
    public ProducerKeyImpl()
    {

    }

    /**
     * Overloaded constructor - calls ActivityKey constructor
     */
    public ProducerKeyImpl(
        ApplicationContext anApplicationContext,
        String appTypeDN,
        String type,
        Object primaryKey)
    {
        super(anApplicationContext, appTypeDN, type, primaryKey );
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
        return new ProducerPrimaryKeyImpl();
    }

	/**
	 * Makes an empty instance of <CODE>ProducerPrimaryKey</CODE>. Factory method
     *
     * @return A new and empty instance of <CODE>ProducerPrimaryKey</CODE>.
     *
     * @see javax.oss.ipb.producer.ProducerPrimaryKey
	 */
	public ProducerPrimaryKey makeProducerPrimaryKey()
    {
        return (ProducerPrimaryKey) makePrimaryKey();
    }
    

	/**
	 * Get <CODE>Producer</CODE>'s Primary Key value
     *
     * @return The <CODE>primaryKey</CODE> of this <CODE>ProducerKey</CODE> instance.
     *
     * @throws java.lang.IllegalStateException If the <CODE>primaryKey</CODE>
     * value is not yet filled in (if this <CODE>ProducerKey</CODE> value
     * is just manufactured but not yet set).
     *
     * @see javax.oss.ipb.producer.ProducerPrimaryKey
	 */
	public ProducerPrimaryKey getProducerPrimaryKey() 
		throws java.lang.IllegalStateException
    {
        if(getPrimaryKey() == null)
        {
            throw new java.lang.IllegalStateException(
                "PrimaryKey attribute is not yet set");
        }

        return (ProducerPrimaryKey) getPrimaryKey();
    }
    

	/**
	 * Set the <CODE>primaryKey</CODE> value of this instance.
     *
     * @param primaryKey Value of the <CODE>primaryKey</CODE> of this instance.
     *
     * @throws java.lang.IllegalArgumentException If
     * the <CODE>primaryKey</CODE> instance value provided as the argument to
     * this method is not well formed.
     *
     * @see javax.oss.ipb.producer.ProducerPrimaryKey
	 */
	public void setProducerPrimaryKey(ProducerPrimaryKey primaryKey) 
		throws java.lang.IllegalArgumentException
    {
        if( (primaryKey == null) ||
            (!(primaryKey instanceof ProducerPrimaryKeyImpl)))
        {
            throw new java.lang.IllegalArgumentException(
                "The primaryKey argument is null or " +
                "not of ProducerPrimaryKeyImpl Type");
        }

        setPrimaryKey(primaryKey);
        
    }

    /**
     * Deep copy of this key
     * 
     * @return deep copy of this key
     */
    public Object clone()
    {

        ProducerKeyImpl myClone = (ProducerKeyImpl)super.clone();

        return myClone;
    }
}
