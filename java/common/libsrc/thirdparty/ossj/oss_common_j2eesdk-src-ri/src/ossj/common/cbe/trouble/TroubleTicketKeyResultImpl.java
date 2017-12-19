/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyResultImpl
implements TroubleTicketKeyResult
{ 

    /**
     * Constructs a new TroubleTicketKeyResultImpl with empty attributes.
     * 
     */

    public TroubleTicketKeyResultImpl() {
        super();
    }




    /**
     * Returns this TroubleTicketKeyResultImpl's troubleTicketKey
     * 
     * @return the troubleTicketKey
     * 
    */

    public javax.oss.cbe.trouble.TroubleTicketKey getTroubleTicketKey() {
        // Use the based MEK method
        return (javax.oss.cbe.trouble.TroubleTicketKey)getManagedEntityKey();
    }

}
