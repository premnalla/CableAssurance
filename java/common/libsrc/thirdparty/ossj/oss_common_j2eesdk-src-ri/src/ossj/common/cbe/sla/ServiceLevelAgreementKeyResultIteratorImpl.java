/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementKeyResult;
import javax.oss.cbe.sla.ServiceLevelAgreementKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementKeyResultIteratorImpl
extends ossj.common.cbe.agreement.AgreementKeyResultIteratorImpl
implements ServiceLevelAgreementKeyResultIterator
{ 

    /**
     * Constructs a new ServiceLevelAgreementKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelAgreementKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelAgreementKeyResultIteratorImpl(ServiceLevelAgreementKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ServiceLevelAgreementKeyResult[] getNextServiceLevelAgreementKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelAgreementKeyResult[]) getNext(howMany);
    }




}
