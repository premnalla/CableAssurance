/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.policy;

import javax.oss.cbe.policy.PolicyKeyResult;
import javax.oss.cbe.policy.PolicyKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.policy.PolicyKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.policy.PolicyKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PolicyKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements PolicyKeyResultIterator
{ 

    /**
     * Constructs a new PolicyKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PolicyKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PolicyKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PolicyKeyResultIteratorImpl(PolicyKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PolicyKeyResult[] getNextPolicyKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PolicyKeyResult[]) getNext(howMany);
    }




}
