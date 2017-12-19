/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntitySpecificationValue;
import javax.oss.cbe.EntitySpecificationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntitySpecificationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntitySpecificationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntitySpecificationValueIteratorImpl
extends ossj.common.cbe.CBEManagedEntityValueIteratorImpl
implements EntitySpecificationValueIterator
{ 

    /**
     * Constructs a new EntitySpecificationValueIteratorImpl with empty attributes.
     * 
     */

    public EntitySpecificationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new EntitySpecificationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public EntitySpecificationValueIteratorImpl(EntitySpecificationValue[] values){
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
    public EntitySpecificationValue[] getNextEntitySpecifications(int howMany)
    throws java.rmi.RemoteException {
        return (EntitySpecificationValue[]) getNext(howMany);
    }




}
