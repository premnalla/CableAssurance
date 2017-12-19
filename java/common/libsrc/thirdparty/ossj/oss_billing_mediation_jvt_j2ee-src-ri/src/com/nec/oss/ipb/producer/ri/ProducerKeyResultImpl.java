
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
import javax.oss.ManagedEntityKey;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerKeyResult;


/**
 * RI imports.
 */
import com.nec.oss.cfi.activity.ri.ActivityKeyResultImpl;


/**
 * Implementation for
 * javax.oss.ipb.producer.ProducerKeyResult interface.
 * This class encapsulates all the information necessary to indicate the
 * result of a BEST EFFORT operations on a <CODE>Producer</CODE> Managed Entity.
 *
 * <p>
 * The <CODE>ProducerKeyResult</CODE> is used in operations involving multiple
 * <CODE>JVTProducerSession</CODE> entities, for example, in the deletion or creation
 * of multiple <CODE>Producer</CODE> entities.
 *
 * <p>
 * This class defines additional type-safe methods specific
 * to getting and setting of
 * <CODE>ProducerKey</CODE> attribute value, specific to this
 * instances of this class.
 * @see javax.oss.ipb.producer.JVTProducerSession
 * @see javax.oss.ipb.producer.ProducerKey
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public class ProducerKeyResultImpl
extends com.nec.oss.cfi.activity.ri.ActivityKeyResultImpl
implements ProducerKeyResult
{
	/**
	 * Get the value representation of <CODE>ProducerKey</CODE>.
	 * 
	 * @return The <CODE>ProducerKey</CODE> value associated with this instance.
	 *
	 * @see javax.oss.ipb.producer.ProducerKey
	 */
	public ProducerKey getProducerKey()
    {
        ManagedEntityKey key = getManagedEntityKey();

        if(key == null)
        {
            throw new java.lang.IllegalStateException(
                "managedEntityKey attribute was not set");
        }

        return (ProducerKey) key;
    }
    

	/**
	 * Sets the <CODE>producerKey</CODE> manangedEntityKey attribute value
     * for this instance.
	 * 
	 * @param producerKey <CODE>ManagedEntityKey</CODE> value of this
     * instance, which is a <CODE>ProducerKey</CODE> instance.
	 *
	 * @see  javax.oss.ipb.producer.ProducerKey
	 */
	public void setProducerKey(
        ProducerKey producerKey)
    {
		if(producerKey == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Key parameter value is not valid");
        }

        setManagedEntityKey(producerKey);
    }
}
