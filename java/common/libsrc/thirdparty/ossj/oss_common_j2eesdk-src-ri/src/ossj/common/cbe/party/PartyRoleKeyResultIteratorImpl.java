/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyRoleKeyResult;
import javax.oss.cbe.party.PartyRoleKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyRoleKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyRoleKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyRoleKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements PartyRoleKeyResultIterator
{ 

    /**
     * Constructs a new PartyRoleKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PartyRoleKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PartyRoleKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PartyRoleKeyResultIteratorImpl(PartyRoleKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PartyRoleKeyResult[] getNextPartyRoleKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PartyRoleKeyResult[]) getNext(howMany);
    }




}
