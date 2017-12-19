/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements BusinessInteractionKeyResult
{ 

    /**
     * Constructs a new BusinessInteractionKeyResultImpl with empty attributes.
     * 
     */

    public BusinessInteractionKeyResultImpl() {
        super();
    }




    /**
     * Returns this BusinessInteractionKeyResultImpl's businessInteractionKey
     * 
     * @return the businessInteractionKey
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionKey getBusinessInteractionKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.BusinessInteractionKey)getManagedEntityKey();
    }

}
