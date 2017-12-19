/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.PlaceKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.PlaceKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.PlaceKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PlaceKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements PlaceKey
{ 

    /**
     * Constructs a new PlaceKeyImpl with empty attributes.
     * 
     */

    public PlaceKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PlaceKey val = null;
            val = (PlaceKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PlaceKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PlaceKey object that has the arguments.
     * 
     * @param value the Object to compare with this PlaceKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PlaceKey)) {
            PlaceKey argVal = (PlaceKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
