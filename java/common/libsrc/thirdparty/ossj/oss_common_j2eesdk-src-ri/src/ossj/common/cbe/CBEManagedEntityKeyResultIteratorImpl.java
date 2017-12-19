/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.CBEManagedEntityKeyResult;
import javax.oss.cbe.CBEManagedEntityKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.CBEManagedEntityKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.CBEManagedEntityKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CBEManagedEntityKeyResultIteratorImpl
extends ossj.common.ManagedEntityKeyResultIteratorImpl
implements CBEManagedEntityKeyResultIterator
{ 

    /**
     * Constructs a new CBEManagedEntityKeyResultIteratorImpl with empty attributes.
     * 
     */

    public CBEManagedEntityKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new CBEManagedEntityKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public CBEManagedEntityKeyResultIteratorImpl(CBEManagedEntityKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public CBEManagedEntityKeyResult[] getNextCBEManagedEntityKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (CBEManagedEntityKeyResult[]) getNext(howMany);
    }




}
