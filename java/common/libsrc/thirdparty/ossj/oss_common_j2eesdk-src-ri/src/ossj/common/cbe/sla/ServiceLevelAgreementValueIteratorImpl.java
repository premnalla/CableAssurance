/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementValue;
import javax.oss.cbe.sla.ServiceLevelAgreementValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementValueIteratorImpl
extends ossj.common.cbe.agreement.AgreementValueIteratorImpl
implements ServiceLevelAgreementValueIterator
{ 

    /**
     * Constructs a new ServiceLevelAgreementValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelAgreementValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelAgreementValueIteratorImpl(ServiceLevelAgreementValue[] values){
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
    public ServiceLevelAgreementValue[] getNextServiceLevelAgreements(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelAgreementValue[]) getNext(howMany);
    }




}
