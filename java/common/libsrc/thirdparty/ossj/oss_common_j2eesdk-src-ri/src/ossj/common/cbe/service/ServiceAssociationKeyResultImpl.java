/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceAssociationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceAssociationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceAssociationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceAssociationKeyResultImpl
extends ossj.common.cbe.AssociationKeyResultImpl
implements ServiceAssociationKeyResult
{ 

    /**
     * Constructs a new ServiceAssociationKeyResultImpl with empty attributes.
     * 
     */

    public ServiceAssociationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ServiceAssociationKeyResultImpl's serviceAssociationKey
     * 
     * @return the serviceAssociationKey
     * 
    */

    public javax.oss.cbe.service.ServiceAssociationKey getServiceAssociationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.service.ServiceAssociationKey)getManagedEntityKey();
    }

}
