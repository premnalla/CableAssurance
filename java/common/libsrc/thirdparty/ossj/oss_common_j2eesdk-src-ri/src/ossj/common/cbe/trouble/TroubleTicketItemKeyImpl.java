/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketItemKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketItemKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketItemKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketItemKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionItemKeyImpl
implements TroubleTicketItemKey
{ 

    /**
     * Constructs a new TroubleTicketItemKeyImpl with empty attributes.
     * 
     */

    public TroubleTicketItemKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TroubleTicketItemKey val = null;
            val = (TroubleTicketItemKey)super.clone();

            return val;
    }

    /**
     * Checks whether two TroubleTicketItemKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an TroubleTicketItemKey object that has the arguments.
     * 
     * @param value the Object to compare with this TroubleTicketItemKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TroubleTicketItemKey)) {
            TroubleTicketItemKey argVal = (TroubleTicketItemKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
