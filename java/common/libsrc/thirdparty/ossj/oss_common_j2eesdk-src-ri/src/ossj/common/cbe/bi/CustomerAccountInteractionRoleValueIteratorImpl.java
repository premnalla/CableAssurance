/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.CustomerAccountInteractionRoleValue;
import javax.oss.cbe.bi.CustomerAccountInteractionRoleValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.CustomerAccountInteractionRoleValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.CustomerAccountInteractionRoleValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CustomerAccountInteractionRoleValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleValueIteratorImpl
implements CustomerAccountInteractionRoleValueIterator
{ 

    /**
     * Constructs a new CustomerAccountInteractionRoleValueIteratorImpl with empty attributes.
     * 
     */

    public CustomerAccountInteractionRoleValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new CustomerAccountInteractionRoleValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public CustomerAccountInteractionRoleValueIteratorImpl(CustomerAccountInteractionRoleValue[] values){
        super();
        super.addManagedEntityValues(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueIterator#getNext(int)
     */
    public CustomerAccountInteractionRoleValue[] getNextCustomerAccountInteractionRoles(int howMany)
    throws java.rmi.RemoteException {
        return (CustomerAccountInteractionRoleValue[]) getNext(howMany);
    }




}
