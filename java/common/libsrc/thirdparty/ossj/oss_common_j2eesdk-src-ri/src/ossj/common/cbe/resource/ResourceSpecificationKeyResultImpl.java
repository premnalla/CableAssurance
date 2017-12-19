/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceSpecificationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceSpecificationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceSpecificationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceSpecificationKeyResultImpl
extends ossj.common.cbe.EntitySpecificationKeyResultImpl
implements ResourceSpecificationKeyResult
{ 

    /**
     * Constructs a new ResourceSpecificationKeyResultImpl with empty attributes.
     * 
     */

    public ResourceSpecificationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ResourceSpecificationKeyResultImpl's resourceSpecificationKey
     * 
     * @return the resourceSpecificationKey
     * 
    */

    public javax.oss.cbe.resource.ResourceSpecificationKey getResourceSpecificationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.resource.ResourceSpecificationKey)getManagedEntityKey();
    }

}
