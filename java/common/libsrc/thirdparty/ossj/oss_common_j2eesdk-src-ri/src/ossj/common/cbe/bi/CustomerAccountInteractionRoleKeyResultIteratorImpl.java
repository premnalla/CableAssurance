/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResult;
import javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CustomerAccountInteractionRoleKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyResultIteratorImpl
implements CustomerAccountInteractionRoleKeyResultIterator
{ 

    /**
     * Constructs a new CustomerAccountInteractionRoleKeyResultIteratorImpl with empty attributes.
     * 
     */

    public CustomerAccountInteractionRoleKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new CustomerAccountInteractionRoleKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public CustomerAccountInteractionRoleKeyResultIteratorImpl(CustomerAccountInteractionRoleKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public CustomerAccountInteractionRoleKeyResult[] getNextCustomerAccountInteractionRoleKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (CustomerAccountInteractionRoleKeyResult[]) getNext(howMany);
    }




}
