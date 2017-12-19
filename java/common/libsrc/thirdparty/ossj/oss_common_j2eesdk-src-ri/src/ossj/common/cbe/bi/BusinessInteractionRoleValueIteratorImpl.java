/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionRoleValue;
import javax.oss.cbe.bi.BusinessInteractionRoleValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionRoleValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionRoleValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionRoleValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements BusinessInteractionRoleValueIterator
{ 

    /**
     * Constructs a new BusinessInteractionRoleValueIteratorImpl with empty attributes.
     * 
     */

    public BusinessInteractionRoleValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BusinessInteractionRoleValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BusinessInteractionRoleValueIteratorImpl(BusinessInteractionRoleValue[] values){
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
    public BusinessInteractionRoleValue[] getNextBusinessInteractionRoles(int howMany)
    throws java.rmi.RemoteException {
        return (BusinessInteractionRoleValue[]) getNext(howMany);
    }




}
