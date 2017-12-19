/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.AssociationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.AssociationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.AssociationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AssociationKeyResultImpl
extends ossj.common.cbe.CBEManagedEntityKeyResultImpl
implements AssociationKeyResult
{ 

    /**
     * Constructs a new AssociationKeyResultImpl with empty attributes.
     * 
     */

    public AssociationKeyResultImpl() {
        super();
    }




    /**
     * Returns this AssociationKeyResultImpl's associationKey
     * 
     * @return the associationKey
     * 
    */

    public javax.oss.cbe.AssociationKey getAssociationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.AssociationKey)getManagedEntityKey();
    }

}
