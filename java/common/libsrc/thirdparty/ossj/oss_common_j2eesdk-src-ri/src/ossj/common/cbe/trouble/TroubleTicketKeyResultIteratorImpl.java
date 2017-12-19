/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketKeyResult;
import javax.oss.cbe.trouble.TroubleTicketKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyResultIteratorImpl
implements TroubleTicketKeyResultIterator
{ 

    /**
     * Constructs a new TroubleTicketKeyResultIteratorImpl with empty attributes.
     * 
     */

    public TroubleTicketKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new TroubleTicketKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public TroubleTicketKeyResultIteratorImpl(TroubleTicketKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public TroubleTicketKeyResult[] getNextTroubleTicketKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (TroubleTicketKeyResult[]) getNext(howMany);
    }




}
