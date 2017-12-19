/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.PlaceValue;
import javax.oss.cbe.location.PlaceValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.PlaceValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.PlaceValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PlaceValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements PlaceValueIterator
{ 

    /**
     * Constructs a new PlaceValueIteratorImpl with empty attributes.
     * 
     */

    public PlaceValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PlaceValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PlaceValueIteratorImpl(PlaceValue[] values){
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
    public PlaceValue[] getNextPlaces(int howMany)
    throws java.rmi.RemoteException {
        return (PlaceValue[]) getNext(howMany);
    }




}
