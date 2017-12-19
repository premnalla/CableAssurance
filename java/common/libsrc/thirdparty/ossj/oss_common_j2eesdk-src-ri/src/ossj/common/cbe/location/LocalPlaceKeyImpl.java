/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.LocalPlaceKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.LocalPlaceKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.LocalPlaceKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class LocalPlaceKeyImpl
extends ossj.common.cbe.location.PlaceKeyImpl
implements LocalPlaceKey
{ 

    /**
     * Constructs a new LocalPlaceKeyImpl with empty attributes.
     * 
     */

    public LocalPlaceKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        LocalPlaceKey val = null;
            val = (LocalPlaceKey)super.clone();

            return val;
    }

    /**
     * Checks whether two LocalPlaceKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an LocalPlaceKey object that has the arguments.
     * 
     * @param value the Object to compare with this LocalPlaceKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof LocalPlaceKey)) {
            LocalPlaceKey argVal = (LocalPlaceKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
