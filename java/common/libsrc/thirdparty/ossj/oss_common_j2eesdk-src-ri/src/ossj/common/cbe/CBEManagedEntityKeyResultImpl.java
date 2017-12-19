/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.CBEManagedEntityKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.CBEManagedEntityKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.CBEManagedEntityKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CBEManagedEntityKeyResultImpl
extends ossj.common.ManagedEntityKeyResultImpl
implements CBEManagedEntityKeyResult
{ 

    /**
     * Constructs a new CBEManagedEntityKeyResultImpl with empty attributes.
     * 
     */

    public CBEManagedEntityKeyResultImpl() {
        super();
    }




    /**
     * Returns this CBEManagedEntityKeyResultImpl's CBEManagedEntityKey
     * 
     * @return the CBEManagedEntityKey
     * 
    */

    public javax.oss.cbe.CBEManagedEntityKey getCBEManagedEntityKey() {
        // Use the based MEK method
        return (javax.oss.cbe.CBEManagedEntityKey)getManagedEntityKey();
    }

}
