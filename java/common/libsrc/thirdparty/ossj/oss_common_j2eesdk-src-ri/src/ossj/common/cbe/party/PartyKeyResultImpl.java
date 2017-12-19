/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements PartyKeyResult
{ 

    /**
     * Constructs a new PartyKeyResultImpl with empty attributes.
     * 
     */

    public PartyKeyResultImpl() {
        super();
    }




    /**
     * Returns this PartyKeyResultImpl's partyKey
     * 
     * @return the partyKey
     * 
    */

    public javax.oss.cbe.party.PartyKey getPartyKey() {
        // Use the based MEK method
        return (javax.oss.cbe.party.PartyKey)getManagedEntityKey();
    }

}
