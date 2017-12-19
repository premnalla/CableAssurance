/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.RequestKeyResult;
import javax.oss.cbe.bi.RequestKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.RequestKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.RequestKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class RequestKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyResultIteratorImpl
implements RequestKeyResultIterator
{ 

    /**
     * Constructs a new RequestKeyResultIteratorImpl with empty attributes.
     * 
     */

    public RequestKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new RequestKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public RequestKeyResultIteratorImpl(RequestKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public RequestKeyResult[] getNextRequestKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (RequestKeyResult[]) getNext(howMany);
    }




}
