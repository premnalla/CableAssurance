
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri;

/**
 * Standard imports
 */


/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.producer.ProducerKey;

/**
 * RI imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerValueStatefulIterator;


/**
 * This is the Remote Home interface of the
 * <CODE>ProducerValueIterator</CODE>
 * Stateful Session Bean.
 *
 * This interface is used as the factory for creating new remote
 * interface instances of <CODE>ProducerValueIterator</CODE>.
 *
 * <p>
 * The <CODE>ProducerValueIteratorHome</CODE> interface is registered
 * in the JNDI as part of the
 * <CODE>ProducerValueIterator</CODE> Session Beans deployment
 * process. The <CODE>ProducerValueIteratorHome</CODE>
 * is registered under the naming attributes, which can be used
 * to find the <CODE>ProducerValueIteratorHome</CODE> interface.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface ProducerValueIteratorHome
extends javax.ejb.EJBHome
{
    /**
     * Create for the Home interface.
     * @param producerKeys Array of ProducerKeys values to manage
     * @exception java.rmi.RemoteException Thrown if a communication error occurs
     * @exception javax.ejb.CreateException Thrown if an error occurs in creation
     */
    public ProducerValueStatefulIterator create(
        ProducerKey [] producerKeys,
        String[] attributeNames)
        throws java.rmi.RemoteException,
        javax.ejb.CreateException;
}
