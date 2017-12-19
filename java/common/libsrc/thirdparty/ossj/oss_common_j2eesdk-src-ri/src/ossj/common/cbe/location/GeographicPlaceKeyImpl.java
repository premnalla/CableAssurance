/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.GeographicPlaceKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.GeographicPlaceKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.GeographicPlaceKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class GeographicPlaceKeyImpl
extends ossj.common.cbe.location.PlaceKeyImpl
implements GeographicPlaceKey
{ 

    /**
     * Constructs a new GeographicPlaceKeyImpl with empty attributes.
     * 
     */

    public GeographicPlaceKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        GeographicPlaceKey val = null;
            val = (GeographicPlaceKey)super.clone();

            return val;
    }

    /**
     * Checks whether two GeographicPlaceKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an GeographicPlaceKey object that has the arguments.
     * 
     * @param value the Object to compare with this GeographicPlaceKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof GeographicPlaceKey)) {
            GeographicPlaceKey argVal = (GeographicPlaceKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
