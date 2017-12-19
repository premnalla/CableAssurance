/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResult;
import javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementItemKeyResultIteratorImpl
extends ossj.common.cbe.agreement.AgreementItemKeyResultIteratorImpl
implements ServiceLevelAgreementItemKeyResultIterator
{ 

    /**
     * Constructs a new ServiceLevelAgreementItemKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementItemKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelAgreementItemKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelAgreementItemKeyResultIteratorImpl(ServiceLevelAgreementItemKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ServiceLevelAgreementItemKeyResult[] getNextServiceLevelAgreementItemKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelAgreementItemKeyResult[]) getNext(howMany);
    }




}
