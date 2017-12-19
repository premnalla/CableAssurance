/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntityKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntityKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntityKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntityKeyResultImpl
extends ossj.common.cbe.CBEManagedEntityKeyResultImpl
implements EntityKeyResult
{ 

    /**
     * Constructs a new EntityKeyResultImpl with empty attributes.
     * 
     */

    public EntityKeyResultImpl() {
        super();
    }




    /**
     * Returns this EntityKeyResultImpl's entityKey
     * 
     * @return the entityKey
     * 
    */

    public javax.oss.cbe.EntityKey getEntityKey() {
        // Use the based MEK method
        return (javax.oss.cbe.EntityKey)getManagedEntityKey();
    }

}
