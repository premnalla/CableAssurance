/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.CBEManagedEntityValue;
import javax.oss.cbe.CBEManagedEntityValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.CBEManagedEntityValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.CBEManagedEntityValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CBEManagedEntityValueIteratorImpl
extends ossj.common.ManagedEntityValueIteratorImpl
implements CBEManagedEntityValueIterator
{ 

    /**
     * Constructs a new CBEManagedEntityValueIteratorImpl with empty attributes.
     * 
     */

    public CBEManagedEntityValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new CBEManagedEntityValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public CBEManagedEntityValueIteratorImpl(CBEManagedEntityValue[] values){
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
    public CBEManagedEntityValue[] getNextCBEManagedEntities(int howMany)
    throws java.rmi.RemoteException {
        return (CBEManagedEntityValue[]) getNext(howMany);
    }




}
