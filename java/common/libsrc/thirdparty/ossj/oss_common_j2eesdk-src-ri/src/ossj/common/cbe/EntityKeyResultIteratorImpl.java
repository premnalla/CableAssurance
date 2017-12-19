/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntityKeyResult;
import javax.oss.cbe.EntityKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntityKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntityKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntityKeyResultIteratorImpl
extends ossj.common.cbe.CBEManagedEntityKeyResultIteratorImpl
implements EntityKeyResultIterator
{ 

    /**
     * Constructs a new EntityKeyResultIteratorImpl with empty attributes.
     * 
     */

    public EntityKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new EntityKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public EntityKeyResultIteratorImpl(EntityKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public EntityKeyResult[] getNextEntityKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (EntityKeyResult[]) getNext(howMany);
    }




}
