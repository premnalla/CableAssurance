/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyValue;
import javax.oss.cbe.party.PartyValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements PartyValueIterator
{ 

    /**
     * Constructs a new PartyValueIteratorImpl with empty attributes.
     * 
     */

    public PartyValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PartyValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PartyValueIteratorImpl(PartyValue[] values){
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
    public PartyValue[] getNextParties(int howMany)
    throws java.rmi.RemoteException {
        return (PartyValue[]) getNext(howMany);
    }




}
