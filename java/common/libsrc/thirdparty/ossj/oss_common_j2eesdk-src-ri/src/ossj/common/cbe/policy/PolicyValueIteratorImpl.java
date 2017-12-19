/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.policy;

import javax.oss.cbe.policy.PolicyValue;
import javax.oss.cbe.policy.PolicyValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.policy.PolicyValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.policy.PolicyValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PolicyValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements PolicyValueIterator
{ 

    /**
     * Constructs a new PolicyValueIteratorImpl with empty attributes.
     * 
     */

    public PolicyValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PolicyValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PolicyValueIteratorImpl(PolicyValue[] values){
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
    public PolicyValue[] getNextPolicys(int howMany)
    throws java.rmi.RemoteException {
        return (PolicyValue[]) getNext(howMany);
    }




}
