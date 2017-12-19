/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementItemKeyResult;
import javax.oss.cbe.agreement.AgreementItemKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementItemKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementItemKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementItemKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionItemKeyResultIteratorImpl
implements AgreementItemKeyResultIterator
{ 

    /**
     * Constructs a new AgreementItemKeyResultIteratorImpl with empty attributes.
     * 
     */

    public AgreementItemKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AgreementItemKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AgreementItemKeyResultIteratorImpl(AgreementItemKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public AgreementItemKeyResult[] getNextAgreementItemKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (AgreementItemKeyResult[]) getNext(howMany);
    }




}
