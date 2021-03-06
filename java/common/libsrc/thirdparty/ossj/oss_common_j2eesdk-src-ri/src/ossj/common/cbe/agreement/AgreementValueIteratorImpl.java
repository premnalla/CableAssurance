/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementValue;
import javax.oss.cbe.agreement.AgreementValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionValueIteratorImpl
implements AgreementValueIterator
{ 

    /**
     * Constructs a new AgreementValueIteratorImpl with empty attributes.
     * 
     */

    public AgreementValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AgreementValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AgreementValueIteratorImpl(AgreementValue[] values){
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
    public AgreementValue[] getNextAgreements(int howMany)
    throws java.rmi.RemoteException {
        return (AgreementValue[]) getNext(howMany);
    }




}
