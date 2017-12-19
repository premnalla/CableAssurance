/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.PlaceKeyResult;
import javax.oss.cbe.location.PlaceKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.PlaceKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.PlaceKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PlaceKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements PlaceKeyResultIterator
{ 

    /**
     * Constructs a new PlaceKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PlaceKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PlaceKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PlaceKeyResultIteratorImpl(PlaceKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PlaceKeyResult[] getNextPlaceKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PlaceKeyResult[]) getNext(howMany);
    }




}
