/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementKeyResult;
import javax.oss.cbe.agreement.AgreementKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyResultIteratorImpl
implements AgreementKeyResultIterator
{ 

    /**
     * Constructs a new AgreementKeyResultIteratorImpl with empty attributes.
     * 
     */

    public AgreementKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AgreementKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AgreementKeyResultIteratorImpl(AgreementKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public AgreementKeyResult[] getNextAgreementKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (AgreementKeyResult[]) getNext(howMany);
    }




}
