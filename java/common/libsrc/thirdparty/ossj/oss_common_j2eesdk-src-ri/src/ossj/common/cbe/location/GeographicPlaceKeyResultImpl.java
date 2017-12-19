/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.GeographicPlaceKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.GeographicPlaceKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.GeographicPlaceKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class GeographicPlaceKeyResultImpl
extends ossj.common.cbe.location.PlaceKeyResultImpl
implements GeographicPlaceKeyResult
{ 

    /**
     * Constructs a new GeographicPlaceKeyResultImpl with empty attributes.
     * 
     */

    public GeographicPlaceKeyResultImpl() {
        super();
    }




    /**
     * Returns this GeographicPlaceKeyResultImpl's geographicPlaceKey
     * 
     * @return the geographicPlaceKey
     * 
    */

    public javax.oss.cbe.location.GeographicPlaceKey getGeographicPlaceKey() {
        // Use the based MEK method
        return (javax.oss.cbe.location.GeographicPlaceKey)getManagedEntityKey();
    }

}
