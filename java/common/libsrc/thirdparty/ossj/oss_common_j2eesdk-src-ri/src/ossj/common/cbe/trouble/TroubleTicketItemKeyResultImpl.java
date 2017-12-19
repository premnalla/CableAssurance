/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketItemKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketItemKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketItemKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketItemKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionItemKeyResultImpl
implements TroubleTicketItemKeyResult
{ 

    /**
     * Constructs a new TroubleTicketItemKeyResultImpl with empty attributes.
     * 
     */

    public TroubleTicketItemKeyResultImpl() {
        super();
    }




    /**
     * Returns this TroubleTicketItemKeyResultImpl's troubleTicketItemKey
     * 
     * @return the troubleTicketItemKey
     * 
    */

    public javax.oss.cbe.trouble.TroubleTicketItemKey getTroubleTicketItemKey() {
        // Use the based MEK method
        return (javax.oss.cbe.trouble.TroubleTicketItemKey)getManagedEntityKey();
    }

}
