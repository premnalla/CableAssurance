/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.RequestKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.RequestKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.RequestKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class RequestKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyResultImpl
implements RequestKeyResult
{ 

    /**
     * Constructs a new RequestKeyResultImpl with empty attributes.
     * 
     */

    public RequestKeyResultImpl() {
        super();
    }




    /**
     * Returns this RequestKeyResultImpl's requestKey
     * 
     * @return the requestKey
     * 
    */

    public javax.oss.cbe.bi.RequestKey getRequestKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.RequestKey)getManagedEntityKey();
    }

}
