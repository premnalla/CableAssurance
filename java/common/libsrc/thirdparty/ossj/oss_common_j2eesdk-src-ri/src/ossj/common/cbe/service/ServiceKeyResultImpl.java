/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ServiceKeyResult
{ 

    /**
     * Constructs a new ServiceKeyResultImpl with empty attributes.
     * 
     */

    public ServiceKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceKeyResultImpl's serviceKey
     * 
     * @return the serviceKey
     * 
    */

    public javax.oss.cbe.service.ServiceKey getServiceKey() {
        // Use the based MEK method
        return (javax.oss.cbe.service.ServiceKey)getManagedEntityKey();
    }

}
