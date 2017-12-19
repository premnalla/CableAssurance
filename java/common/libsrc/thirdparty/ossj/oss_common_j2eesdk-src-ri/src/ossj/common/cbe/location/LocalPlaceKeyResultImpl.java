/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.LocalPlaceKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.LocalPlaceKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.LocalPlaceKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class LocalPlaceKeyResultImpl
extends ossj.common.cbe.location.PlaceKeyResultImpl
implements LocalPlaceKeyResult
{ 

    /**
     * Constructs a new LocalPlaceKeyResultImpl with empty attributes.
     * 
     */

    public LocalPlaceKeyResultImpl() {
        super();
    }




    /**
     * Returns this LocalPlaceKeyResultImpl's localPlaceKey
     * 
     * @return the localPlaceKey
     * 
    */

    public javax.oss.cbe.location.LocalPlaceKey getLocalPlaceKey() {
        // Use the based MEK method
        return (javax.oss.cbe.location.LocalPlaceKey)getManagedEntityKey();
    }

}
