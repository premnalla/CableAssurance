/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.GeographicPlaceValue;
import javax.oss.cbe.location.GeographicPlaceValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.GeographicPlaceValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.GeographicPlaceValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class GeographicPlaceValueIteratorImpl
extends ossj.common.cbe.location.PlaceValueIteratorImpl
implements GeographicPlaceValueIterator
{ 

    /**
     * Constructs a new GeographicPlaceValueIteratorImpl with empty attributes.
     * 
     */

    public GeographicPlaceValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new GeographicPlaceValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public GeographicPlaceValueIteratorImpl(GeographicPlaceValue[] values){
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
    public GeographicPlaceValue[] getNextGeographicPlaces(int howMany)
    throws java.rmi.RemoteException {
        return (GeographicPlaceValue[]) getNext(howMany);
    }




}
