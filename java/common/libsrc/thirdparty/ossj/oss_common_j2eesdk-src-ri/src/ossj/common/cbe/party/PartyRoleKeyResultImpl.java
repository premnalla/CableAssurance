/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyRoleKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyRoleKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyRoleKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyRoleKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements PartyRoleKeyResult
{ 

    /**
     * Constructs a new PartyRoleKeyResultImpl with empty attributes.
     * 
     */

    public PartyRoleKeyResultImpl() {
        super();
    }




    /**
     * Returns this PartyRoleKeyResultImpl's partyRoleKey
     * 
     * @return the partyRoleKey
     * 
    */

    public javax.oss.cbe.party.PartyRoleKey getPartyRoleKey() {
        // Use the based MEK method
        return (javax.oss.cbe.party.PartyRoleKey)getManagedEntityKey();
    }

}
