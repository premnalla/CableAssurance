/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelObjectiveKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelObjectiveKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelObjectiveKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelObjectiveKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ServiceLevelObjectiveKeyResult
{ 

    /**
     * Constructs a new ServiceLevelObjectiveKeyResultImpl with empty attributes.
     * 
     */

    public ServiceLevelObjectiveKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceLevelObjectiveKeyResultImpl's serviceLevelObjectiveKey
     * 
     * @return the serviceLevelObjectiveKey
     * 
    */

    public javax.oss.cbe.service.ServiceLevelObjectiveKey getServiceLevelObjectiveKey() {
        // Use the based MEK method
        return (javax.oss.cbe.service.ServiceLevelObjectiveKey)getManagedEntityKey();
    }

}
