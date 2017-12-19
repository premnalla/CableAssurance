/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceAssociationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceAssociationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceAssociationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceAssociationKeyResultImpl
extends ossj.common.cbe.AssociationKeyResultImpl
implements ResourceAssociationKeyResult
{ 

    /**
     * Constructs a new ResourceAssociationKeyResultImpl with empty attributes.
     * 
     */

    public ResourceAssociationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ResourceAssociationKeyResultImpl's resourceAssociationKey
     * 
     * @return the resourceAssociationKey
     * 
    */

    public javax.oss.cbe.resource.ResourceAssociationKey getResourceAssociationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.resource.ResourceAssociationKey)getManagedEntityKey();
    }

}
