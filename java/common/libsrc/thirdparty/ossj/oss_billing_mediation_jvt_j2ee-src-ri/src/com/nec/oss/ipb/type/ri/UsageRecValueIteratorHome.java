
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.type.ri;

/**
 * Standard imports
 */


/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.type.UsageRecValue;
import javax.oss.cfi.activity.RecordDescriptor;

/**
 * RI imports.
 */
import com.nec.oss.ipb.type.ri.UsageRecValueStatefulIterator;


/**
 * This is the Remote Home interface of the
 * <CODE>UsageRecValueIterator</CODE>
 * Stateful Session Bean.
 *
 * This interface is used as the factory for creating new remote
 * interface instances of <CODE>UsageRecValueIterator</CODE>.
 *
 * <p>
 * The <CODE>UsageRecValueIteratorHome</CODE> interface is registered
 * in the JNDI as part of the
 * <CODE>UsageRecValueIterator</CODE> Session Beans deployment
 * process. The <CODE>UsageRecValueIteratorHome</CODE>
 * is registered under the naming attributes, which can be used
 * to find the <CODE>UsageRecValueIteratorHome</CODE> interface.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface UsageRecValueIteratorHome
extends javax.ejb.EJBHome
{

    /**
     * Create for the Home interface.
     * @param recordDescriptor RecordDescriptor describing the UsageRecValue data
     * @param usageRecValues Array of UsageRecValue values to manage
     * @exception java.rmi.RemoteException Thrown if a communication error occurs
     * @exception javax.ejb.CreateException Thrown if an error occurs in creation
     */
    public UsageRecValueStatefulIterator create(
        RecordDescriptor recordDescriptor,
        UsageRecValue [] usageRecValues)
        throws java.rmi.RemoteException,
        javax.ejb.CreateException;
}
