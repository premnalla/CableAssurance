/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.PlaceKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.PlaceKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.PlaceKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PlaceKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements PlaceKeyResult
{ 

    /**
     * Constructs a new PlaceKeyResultImpl with empty attributes.
     * 
     */

    public PlaceKeyResultImpl() {
        super();
    }




    /**
     * Returns this PlaceKeyResultImpl's placeKey
     * 
     * @return the placeKey
     * 
    */

    public javax.oss.cbe.location.PlaceKey getPlaceKey() {
        // Use the based MEK method
        return (javax.oss.cbe.location.PlaceKey)getManagedEntityKey();
    }

}
