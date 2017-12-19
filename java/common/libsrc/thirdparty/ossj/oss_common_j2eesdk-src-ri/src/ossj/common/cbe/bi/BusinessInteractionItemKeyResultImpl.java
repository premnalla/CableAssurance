/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionItemKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionItemKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionItemKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionItemKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements BusinessInteractionItemKeyResult
{ 

    /**
     * Constructs a new BusinessInteractionItemKeyResultImpl with empty attributes.
     * 
     */

    public BusinessInteractionItemKeyResultImpl() {
        super();
    }




    /**
     * Returns this BusinessInteractionItemKeyResultImpl's businessInteractionItemKey
     * 
     * @return the businessInteractionItemKey
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionItemKey getBusinessInteractionItemKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.BusinessInteractionItemKey)getManagedEntityKey();
    }

}
