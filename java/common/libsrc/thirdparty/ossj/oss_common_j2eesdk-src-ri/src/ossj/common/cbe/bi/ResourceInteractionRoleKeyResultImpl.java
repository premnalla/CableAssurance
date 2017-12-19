/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.ResourceInteractionRoleKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.ResourceInteractionRoleKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.ResourceInteractionRoleKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceInteractionRoleKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyResultImpl
implements ResourceInteractionRoleKeyResult
{ 

    /**
     * Constructs a new ResourceInteractionRoleKeyResultImpl with empty attributes.
     * 
     */

    public ResourceInteractionRoleKeyResultImpl() {
        super();
    }




    /**
     * Returns this ResourceInteractionRoleKeyResultImpl's resourceInteractionRoleKey
     * 
     * @return the resourceInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.ResourceInteractionRoleKey getResourceInteractionRoleKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.ResourceInteractionRoleKey)getManagedEntityKey();
    }

}
