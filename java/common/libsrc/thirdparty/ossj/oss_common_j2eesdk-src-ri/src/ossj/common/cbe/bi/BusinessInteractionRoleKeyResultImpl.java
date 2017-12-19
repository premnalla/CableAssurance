/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionRoleKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionRoleKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionRoleKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionRoleKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements BusinessInteractionRoleKeyResult
{ 

    /**
     * Constructs a new BusinessInteractionRoleKeyResultImpl with empty attributes.
     * 
     */

    public BusinessInteractionRoleKeyResultImpl() {
        super();
    }




    /**
     * Returns this BusinessInteractionRoleKeyResultImpl's businessInteractionRoleKey
     * 
     * @return the businessInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionRoleKey getBusinessInteractionRoleKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.BusinessInteractionRoleKey)getManagedEntityKey();
    }

}
