/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceSpecificationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceSpecificationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceSpecificationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceSpecificationKeyResultImpl
extends ossj.common.cbe.EntitySpecificationKeyResultImpl
implements ServiceSpecificationKeyResult
{ 

    /**
     * Constructs a new ServiceSpecificationKeyResultImpl with empty attributes.
     * 
     */

    public ServiceSpecificationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceSpecificationKeyResultImpl's serviceSpecificationKey
     * 
     * @return the serviceSpecificationKey
     * 
    */

    public javax.oss.cbe.service.ServiceSpecificationKey getServiceSpecificationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.service.ServiceSpecificationKey)getManagedEntityKey();
    }

}
