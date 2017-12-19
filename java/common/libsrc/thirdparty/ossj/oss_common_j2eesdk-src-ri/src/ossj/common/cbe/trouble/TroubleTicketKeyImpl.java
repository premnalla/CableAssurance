/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyImpl
implements TroubleTicketKey
{ 

    /**
     * Constructs a new TroubleTicketKeyImpl with empty attributes.
     * 
     */

    public TroubleTicketKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TroubleTicketKey val = null;
            val = (TroubleTicketKey)super.clone();

            return val;
    }

    /**
     * Checks whether two TroubleTicketKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an TroubleTicketKey object that has the arguments.
     * 
     * @param value the Object to compare with this TroubleTicketKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TroubleTicketKey)) {
            TroubleTicketKey argVal = (TroubleTicketKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
