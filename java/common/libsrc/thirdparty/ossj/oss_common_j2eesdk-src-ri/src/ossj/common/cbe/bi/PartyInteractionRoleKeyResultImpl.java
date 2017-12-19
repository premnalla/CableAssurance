/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.PartyInteractionRoleKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.PartyInteractionRoleKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.PartyInteractionRoleKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyInteractionRoleKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyResultImpl
implements PartyInteractionRoleKeyResult
{ 

    /**
     * Constructs a new PartyInteractionRoleKeyResultImpl with empty attributes.
     * 
     */

    public PartyInteractionRoleKeyResultImpl() {
        super();
    }




    /**
     * Returns this PartyInteractionRoleKeyResultImpl's partyInteractionRoleKey
     * 
     * @return the partyInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.PartyInteractionRoleKey getPartyInteractionRoleKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.PartyInteractionRoleKey)getManagedEntityKey();
    }

}
