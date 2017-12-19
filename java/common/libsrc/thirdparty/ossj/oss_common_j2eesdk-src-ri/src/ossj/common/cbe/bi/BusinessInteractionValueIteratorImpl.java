/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionValue;
import javax.oss.cbe.bi.BusinessInteractionValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements BusinessInteractionValueIterator
{ 

    /**
     * Constructs a new BusinessInteractionValueIteratorImpl with empty attributes.
     * 
     */

    public BusinessInteractionValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BusinessInteractionValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BusinessInteractionValueIteratorImpl(BusinessInteractionValue[] values){
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
    public BusinessInteractionValue[] getNextBusinessInteractions(int howMany)
    throws java.rmi.RemoteException {
        return (BusinessInteractionValue[]) getNext(howMany);
    }




}
