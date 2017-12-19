/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementItemValue;
import javax.oss.cbe.sla.ServiceLevelAgreementItemValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementItemValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementItemValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementItemValueIteratorImpl
extends ossj.common.cbe.agreement.AgreementItemValueIteratorImpl
implements ServiceLevelAgreementItemValueIterator
{ 

    /**
     * Constructs a new ServiceLevelAgreementItemValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementItemValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelAgreementItemValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelAgreementItemValueIteratorImpl(ServiceLevelAgreementItemValue[] values){
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
    public ServiceLevelAgreementItemValue[] getNextServiceLevelAgreementItems(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelAgreementItemValue[]) getNext(howMany);
    }




}
