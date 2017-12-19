/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.RequestValue;
import javax.oss.cbe.bi.RequestValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.RequestValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.RequestValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class RequestValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionValueIteratorImpl
implements RequestValueIterator
{ 

    /**
     * Constructs a new RequestValueIteratorImpl with empty attributes.
     * 
     */

    public RequestValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new RequestValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public RequestValueIteratorImpl(RequestValue[] values){
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
    public RequestValue[] getNextRequests(int howMany)
    throws java.rmi.RemoteException {
        return (RequestValue[]) getNext(howMany);
    }




}
