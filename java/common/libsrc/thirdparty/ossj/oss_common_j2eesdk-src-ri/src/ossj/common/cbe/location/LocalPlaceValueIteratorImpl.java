/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.LocalPlaceValue;
import javax.oss.cbe.location.LocalPlaceValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.LocalPlaceValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.LocalPlaceValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class LocalPlaceValueIteratorImpl
extends ossj.common.cbe.location.PlaceValueIteratorImpl
implements LocalPlaceValueIterator
{ 

    /**
     * Constructs a new LocalPlaceValueIteratorImpl with empty attributes.
     * 
     */

    public LocalPlaceValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new LocalPlaceValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public LocalPlaceValueIteratorImpl(LocalPlaceValue[] values){
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
    public LocalPlaceValue[] getNextLocalPlaces(int howMany)
    throws java.rmi.RemoteException {
        return (LocalPlaceValue[]) getNext(howMany);
    }




}
