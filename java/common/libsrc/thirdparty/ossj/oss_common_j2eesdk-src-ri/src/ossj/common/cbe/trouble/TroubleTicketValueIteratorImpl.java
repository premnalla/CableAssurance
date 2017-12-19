/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketValue;
import javax.oss.cbe.trouble.TroubleTicketValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionValueIteratorImpl
implements TroubleTicketValueIterator
{ 

    /**
     * Constructs a new TroubleTicketValueIteratorImpl with empty attributes.
     * 
     */

    public TroubleTicketValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new TroubleTicketValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public TroubleTicketValueIteratorImpl(TroubleTicketValue[] values){
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
    public TroubleTicketValue[] getNextTroubleTickets(int howMany)
    throws java.rmi.RemoteException {
        return (TroubleTicketValue[]) getNext(howMany);
    }




}
