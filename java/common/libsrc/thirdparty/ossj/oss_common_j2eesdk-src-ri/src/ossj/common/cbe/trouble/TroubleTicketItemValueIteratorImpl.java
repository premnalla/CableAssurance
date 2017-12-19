/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketItemValue;
import javax.oss.cbe.trouble.TroubleTicketItemValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketItemValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketItemValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketItemValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionItemValueIteratorImpl
implements TroubleTicketItemValueIterator
{ 

    /**
     * Constructs a new TroubleTicketItemValueIteratorImpl with empty attributes.
     * 
     */

    public TroubleTicketItemValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new TroubleTicketItemValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public TroubleTicketItemValueIteratorImpl(TroubleTicketItemValue[] values){
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
    public TroubleTicketItemValue[] getNextTroubleTicketItems(int howMany)
    throws java.rmi.RemoteException {
        return (TroubleTicketItemValue[]) getNext(howMany);
    }




}
