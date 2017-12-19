/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementItemKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementItemKeyResultImpl
extends ossj.common.cbe.agreement.AgreementItemKeyResultImpl
implements ServiceLevelAgreementItemKeyResult
{ 

    /**
     * Constructs a new ServiceLevelAgreementItemKeyResultImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementItemKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceLevelAgreementItemKeyResultImpl's serviceLevelAgreementItemKey
     * 
     * @return the serviceLevelAgreementItemKey
     * 
    */

    public javax.oss.cbe.sla.ServiceLevelAgreementItemKey getServiceLevelAgreementItemKey() {
        // Use the based MEK method
        return (javax.oss.cbe.sla.ServiceLevelAgreementItemKey)getManagedEntityKey();
    }

}
