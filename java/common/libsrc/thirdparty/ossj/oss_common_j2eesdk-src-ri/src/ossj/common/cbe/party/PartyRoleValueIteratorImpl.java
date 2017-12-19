/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyRoleValue;
import javax.oss.cbe.party.PartyRoleValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyRoleValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyRoleValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyRoleValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements PartyRoleValueIterator
{ 

    /**
     * Constructs a new PartyRoleValueIteratorImpl with empty attributes.
     * 
     */

    public PartyRoleValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PartyRoleValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PartyRoleValueIteratorImpl(PartyRoleValue[] values){
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
    public PartyRoleValue[] getNextPartyRoles(int howMany)
    throws java.rmi.RemoteException {
        return (PartyRoleValue[]) getNext(howMany);
    }




}
