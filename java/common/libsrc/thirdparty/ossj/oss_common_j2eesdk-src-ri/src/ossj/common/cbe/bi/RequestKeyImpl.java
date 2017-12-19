/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.RequestKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.RequestKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.RequestKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class RequestKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyImpl
implements RequestKey
{ 

    /**
     * Constructs a new RequestKeyImpl with empty attributes.
     * 
     */

    public RequestKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        RequestKey val = null;
            val = (RequestKey)super.clone();

            return val;
    }

    /**
     * Checks whether two RequestKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an RequestKey object that has the arguments.
     * 
     * @param value the Object to compare with this RequestKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof RequestKey)) {
            RequestKey argVal = (RequestKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
