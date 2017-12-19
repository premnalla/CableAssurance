/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyKeyResult;
import javax.oss.cbe.party.PartyKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements PartyKeyResultIterator
{ 

    /**
     * Constructs a new PartyKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PartyKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PartyKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PartyKeyResultIteratorImpl(PartyKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PartyKeyResult[] getNextPartyKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PartyKeyResult[]) getNext(howMany);
    }




}
