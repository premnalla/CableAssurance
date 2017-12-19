/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.PartyInteractionRoleValue;
import javax.oss.cbe.bi.PartyInteractionRoleValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.PartyInteractionRoleValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.PartyInteractionRoleValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyInteractionRoleValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleValueIteratorImpl
implements PartyInteractionRoleValueIterator
{ 

    /**
     * Constructs a new PartyInteractionRoleValueIteratorImpl with empty attributes.
     * 
     */

    public PartyInteractionRoleValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PartyInteractionRoleValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PartyInteractionRoleValueIteratorImpl(PartyInteractionRoleValue[] values){
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
    public PartyInteractionRoleValue[] getNextPartyInteractionRoles(int howMany)
    throws java.rmi.RemoteException {
        return (PartyInteractionRoleValue[]) getNext(howMany);
    }




}
