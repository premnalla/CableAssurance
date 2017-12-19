/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketItemKeyResult;
import javax.oss.cbe.trouble.TroubleTicketItemKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketItemKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketItemKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketItemKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionItemKeyResultIteratorImpl
implements TroubleTicketItemKeyResultIterator
{ 

    /**
     * Constructs a new TroubleTicketItemKeyResultIteratorImpl with empty attributes.
     * 
     */

    public TroubleTicketItemKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new TroubleTicketItemKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public TroubleTicketItemKeyResultIteratorImpl(TroubleTicketItemKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public TroubleTicketItemKeyResult[] getNextTroubleTicketItemKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (TroubleTicketItemKeyResult[]) getNext(howMany);
    }




}
