/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.PartyKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements PartyKey
{ 

    /**
     * Constructs a new PartyKeyImpl with empty attributes.
     * 
     */

    public PartyKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PartyKey val = null;
            val = (PartyKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PartyKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PartyKey object that has the arguments.
     * 
     * @param value the Object to compare with this PartyKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PartyKey)) {
            PartyKey argVal = (PartyKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
