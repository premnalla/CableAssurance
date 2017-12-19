
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.transfer.ri;

/**
 * Standard imports
 */

/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.transfer.TransferStatusKey;

/**
 * RI imports.
 */
import com.nec.oss.ipb.transfer.ri.TransferStatusValueStatefulIterator;


/**
 * This is the Remote Home interface of the
 * <CODE>TransferStatusrValueIterator</CODE>
 * Stateful Session Bean.
 *
 * This interface is used as the factory for creating new remote
 * interface instances of <CODE>TransferStatusValueIterator</CODE>.
 *
 * <p>
 * The <CODE>TransferStatusValueIteratorHome</CODE> interface is registered
 * in the JNDI as part of the
 * <CODE>TransferStatusValueIterator</CODE> Session Beans deployment
 * process. The <CODE>TransferStatusValueIteratorHome</CODE>
 * is registered under the naming attributes, which can be used
 * to find the <CODE>TransferStatusValueIteratorHome</CODE> interface.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface TransferStatusValueIteratorHome
extends javax.ejb.EJBHome
{
    /**
     * Create for the Home interface.
     * @param reportInfo Array of TransferStatus values to manage
     * @exception java.rmi.RemoteException Thrown if a communication error occurs
     * @exception javax.ejb.CreateException Thrown if an error occurs in creation
     */
    public TransferStatusValueStatefulIterator create(
        TransferStatusKey [] transferStatKeys,
        String[] attributeNames)
        throws java.rmi.RemoteException,
        javax.ejb.CreateException;
}
