/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.PartyInteractionRoleKeyResult;
import javax.oss.cbe.bi.PartyInteractionRoleKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.PartyInteractionRoleKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.PartyInteractionRoleKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyInteractionRoleKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyResultIteratorImpl
implements PartyInteractionRoleKeyResultIterator
{ 

    /**
     * Constructs a new PartyInteractionRoleKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PartyInteractionRoleKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PartyInteractionRoleKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PartyInteractionRoleKeyResultIteratorImpl(PartyInteractionRoleKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PartyInteractionRoleKeyResult[] getNextPartyInteractionRoleKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PartyInteractionRoleKeyResult[]) getNext(howMany);
    }




}
