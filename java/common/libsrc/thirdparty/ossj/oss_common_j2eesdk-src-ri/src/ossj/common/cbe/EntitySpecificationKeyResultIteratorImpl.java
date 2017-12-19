/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntitySpecificationKeyResult;
import javax.oss.cbe.EntitySpecificationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntitySpecificationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntitySpecificationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntitySpecificationKeyResultIteratorImpl
extends ossj.common.cbe.CBEManagedEntityKeyResultIteratorImpl
implements EntitySpecificationKeyResultIterator
{ 

    /**
     * Constructs a new EntitySpecificationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public EntitySpecificationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new EntitySpecificationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public EntitySpecificationKeyResultIteratorImpl(EntitySpecificationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public EntitySpecificationKeyResult[] getNextEntitySpecificationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (EntitySpecificationKeyResult[]) getNext(howMany);
    }




}
