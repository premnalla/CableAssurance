/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyRoleKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyRoleKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyRoleKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyRoleKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements PartyRoleKey
{ 

    /**
     * Constructs a new PartyRoleKeyImpl with empty attributes.
     * 
     */

    public PartyRoleKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PartyRoleKey val = null;
            val = (PartyRoleKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PartyRoleKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PartyRoleKey object that has the arguments.
     * 
     * @param value the Object to compare with this PartyRoleKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PartyRoleKey)) {
            PartyRoleKey argVal = (PartyRoleKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
