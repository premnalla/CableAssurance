/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementItemValue;
import javax.oss.cbe.agreement.AgreementItemValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementItemValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementItemValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementItemValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionItemValueIteratorImpl
implements AgreementItemValueIterator
{ 

    /**
     * Constructs a new AgreementItemValueIteratorImpl with empty attributes.
     * 
     */

    public AgreementItemValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AgreementItemValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AgreementItemValueIteratorImpl(AgreementItemValue[] values){
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
    public AgreementItemValue[] getNextAgreementItems(int howMany)
    throws java.rmi.RemoteException {
        return (AgreementItemValue[]) getNext(howMany);
    }




}
