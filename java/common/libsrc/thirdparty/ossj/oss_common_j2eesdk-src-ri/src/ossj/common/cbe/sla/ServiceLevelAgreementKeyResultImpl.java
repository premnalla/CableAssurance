/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementKeyResultImpl
extends ossj.common.cbe.agreement.AgreementKeyResultImpl
implements ServiceLevelAgreementKeyResult
{ 

    /**
     * Constructs a new ServiceLevelAgreementKeyResultImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceLevelAgreementKeyResultImpl's serviceLevelAgreementKey
     * 
     * @return the serviceLevelAgreementKey
     * 
    */

    public javax.oss.cbe.sla.ServiceLevelAgreementKey getServiceLevelAgreementKey() {
        // Use the based MEK method
        return (javax.oss.cbe.sla.ServiceLevelAgreementKey)getManagedEntityKey();
    }

}
