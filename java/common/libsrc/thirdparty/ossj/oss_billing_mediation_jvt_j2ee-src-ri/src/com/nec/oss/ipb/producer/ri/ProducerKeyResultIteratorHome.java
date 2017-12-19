
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
import javax.oss.ipb.producer.ProducerKeyResult;

/**
 * RI imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerKeyResultStatefulIterator;


/**
 * This is the Remote Home interface of the
 * <CODE>ProducerKeyResultIterator</CODE>
 * Stateful Session Bean.
 *
 * This interface is used as the factory for creating new remote
 * interface instances of <CODE>ProducerKeyResultIterator</CODE>.
 *
 * <p>
 * The <CODE>ProducerKeyResultIteratorHome</CODE> interface is registered
 * in the JNDI as part of the
 * <CODE>ProducerKeyResultIterator</CODE> Session Beans deployment
 * process. The <CODE>ProducerKeyResultIteratorHome</CODE>
 * is registered under the naming attributes, which can be used
 * to find the <CODE>ProducerKeyResultIteratorHome</CODE> interface.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface ProducerKeyResultIteratorHome
extends javax.ejb.EJBHome
{
    /**
     * Create for the Home interface.
     * @param producerKeyResults Array of ProducerKeyResult values to manage
     * @exception java.rmi.RemoteException Thrown if a communication error occurs
     * @exception javax.ejb.CreateException Thrown if an error occurs in creation
     */
    public ProducerKeyResultStatefulIterator create(
        ProducerKeyResult [] producerKeyResults)
        throws java.rmi.RemoteException,
        javax.ejb.CreateException;
}
