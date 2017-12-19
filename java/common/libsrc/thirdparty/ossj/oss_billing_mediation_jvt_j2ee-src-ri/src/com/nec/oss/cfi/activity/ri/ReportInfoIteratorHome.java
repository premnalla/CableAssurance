
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.cfi.activity.ri;

/**
 * Standard imports
 */


/**
 * OSS/J Interface imports.
 */
import javax.oss.cfi.activity.ReportInfo;

/**
 * RI imports.
 */
import com.nec.oss.cfi.activity.ri.ReportInfoStatefulIterator;


/**
 * This is the Remote Home interface of the
 * <CODE>ReportInfoIterator</CODE>
 * Stateful Session Bean.
 *
 * This interface is used as the factory for creating new remote
 * interface instances of <CODE>ReportInfoIterator</CODE>.
 *
 * <p>
 * The <CODE>ReportInfoIteratorHome</CODE> interface is registered
 * in the JNDI as part of the
 * <CODE>ReportInfoIterator</CODE> Session Beans deployment
 * process. The <CODE>ReportInfoIteratorHome</CODE>
 * is registered under the naming attributes, which can be used
 * to find the <CODE>ReportInfoIteratorHome</CODE> interface.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface ReportInfoIteratorHome
extends javax.ejb.EJBHome
{
    /**
     * Create for the Home interface.
     * @param reportInfo Array of ReportInfo values to manage
     * @exception java.rmi.RemoteException Thrown if a communication error occurs
     * @exception javax.ejb.CreateException Thrown if an error occurs in creation
     */
    public ReportInfoStatefulIterator create(
        ReportInfo [] reportInfo)
        throws java.rmi.RemoteException,
        javax.ejb.CreateException;
}
