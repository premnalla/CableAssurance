/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ResourceKeyResult
{ 

    /**
     * Constructs a new ResourceKeyResultImpl with empty attributes.
     * 
     */

    public ResourceKeyResultImpl() {
        super();
    }




    /**
     * Returns this ResourceKeyResultImpl's resourceKey
     * 
     * @return the resourceKey
     * 
    */

    public javax.oss.cbe.resource.ResourceKey getResourceKey() {
        // Use the based MEK method
        return (javax.oss.cbe.resource.ResourceKey)getManagedEntityKey();
    }

}
