/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecificationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecificationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelSpecificationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelSpecificationKeyResultImpl
extends ossj.common.cbe.EntitySpecificationKeyResultImpl
implements ServiceLevelSpecificationKeyResult
{ 

    /**
     * Constructs a new ServiceLevelSpecificationKeyResultImpl with empty attributes.
     * 
     */

    public ServiceLevelSpecificationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceLevelSpecificationKeyResultImpl's serviceLevelSpecificationKey
     * 
     * @return the serviceLevelSpecificationKey
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecificationKey getServiceLevelSpecificationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.service.ServiceLevelSpecificationKey)getManagedEntityKey();
    }

}
