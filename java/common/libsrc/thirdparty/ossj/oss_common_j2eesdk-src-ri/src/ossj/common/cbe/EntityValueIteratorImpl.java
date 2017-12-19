/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntityValue;
import javax.oss.cbe.EntityValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntityValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntityValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntityValueIteratorImpl
extends ossj.common.cbe.CBEManagedEntityValueIteratorImpl
implements EntityValueIterator
{ 

    /**
     * Constructs a new EntityValueIteratorImpl with empty attributes.
     * 
     */

    public EntityValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new EntityValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public EntityValueIteratorImpl(EntityValue[] values){
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
    public EntityValue[] getNextEntities(int howMany)
    throws java.rmi.RemoteException {
        return (EntityValue[]) getNext(howMany);
    }




}
