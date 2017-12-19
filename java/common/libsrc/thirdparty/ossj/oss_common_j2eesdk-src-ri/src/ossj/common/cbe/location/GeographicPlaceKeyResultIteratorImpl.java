/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.GeographicPlaceKeyResult;
import javax.oss.cbe.location.GeographicPlaceKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.GeographicPlaceKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.GeographicPlaceKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class GeographicPlaceKeyResultIteratorImpl
extends ossj.common.cbe.location.PlaceKeyResultIteratorImpl
implements GeographicPlaceKeyResultIterator
{ 

    /**
     * Constructs a new GeographicPlaceKeyResultIteratorImpl with empty attributes.
     * 
     */

    public GeographicPlaceKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new GeographicPlaceKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public GeographicPlaceKeyResultIteratorImpl(GeographicPlaceKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public GeographicPlaceKeyResult[] getNextGeographicPlaceKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (GeographicPlaceKeyResult[]) getNext(howMany);
    }




}
