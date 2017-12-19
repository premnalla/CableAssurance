/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.LocalPlaceKeyResult;
import javax.oss.cbe.location.LocalPlaceKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.LocalPlaceKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.LocalPlaceKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class LocalPlaceKeyResultIteratorImpl
extends ossj.common.cbe.location.PlaceKeyResultIteratorImpl
implements LocalPlaceKeyResultIterator
{ 

    /**
     * Constructs a new LocalPlaceKeyResultIteratorImpl with empty attributes.
     * 
     */

    public LocalPlaceKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new LocalPlaceKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public LocalPlaceKeyResultIteratorImpl(LocalPlaceKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public LocalPlaceKeyResult[] getNextLocalPlaceKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (LocalPlaceKeyResult[]) getNext(howMany);
    }




}
