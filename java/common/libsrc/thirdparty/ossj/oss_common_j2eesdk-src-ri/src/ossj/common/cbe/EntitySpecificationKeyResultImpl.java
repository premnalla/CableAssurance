/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntitySpecificationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntitySpecificationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntitySpecificationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntitySpecificationKeyResultImpl
extends ossj.common.cbe.CBEManagedEntityKeyResultImpl
implements EntitySpecificationKeyResult
{ 

    /**
     * Constructs a new EntitySpecificationKeyResultImpl with empty attributes.
     * 
     */

    public EntitySpecificationKeyResultImpl() {
        super();
    }




    /**
     * Returns this EntitySpecificationKeyResultImpl's entitySpecificationKey
     * 
     * @return the entitySpecificationKey
     * 
    */

    public javax.oss.cbe.EntitySpecificationKey getEntitySpecificationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.EntitySpecificationKey)getManagedEntityKey();
    }

}
