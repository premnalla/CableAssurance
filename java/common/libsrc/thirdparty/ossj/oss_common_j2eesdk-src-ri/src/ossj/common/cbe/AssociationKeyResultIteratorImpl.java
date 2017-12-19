/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.AssociationKeyResult;
import javax.oss.cbe.AssociationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.AssociationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.AssociationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AssociationKeyResultIteratorImpl
extends ossj.common.cbe.CBEManagedEntityKeyResultIteratorImpl
implements AssociationKeyResultIterator
{ 

    /**
     * Constructs a new AssociationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public AssociationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AssociationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AssociationKeyResultIteratorImpl(AssociationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public AssociationKeyResult[] getNextAssociationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (AssociationKeyResult[]) getNext(howMany);
    }




}
