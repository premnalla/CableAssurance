/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.CBEManagedEntityKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.CBEManagedEntityKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.CBEManagedEntityKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CBEManagedEntityKeyImpl
extends ossj.common.ManagedEntityKeyImpl
implements CBEManagedEntityKey
{ 

    /**
     * Constructs a new CBEManagedEntityKeyImpl with empty attributes.
     * 
     */

    public CBEManagedEntityKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        CBEManagedEntityKey val = null;
            val = (CBEManagedEntityKey)super.clone();

            return val;
    }

    /**
     * Checks whether two CBEManagedEntityKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an CBEManagedEntityKey object that has the arguments.
     * 
     * @param value the Object to compare with this CBEManagedEntityKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof CBEManagedEntityKey)) {
            CBEManagedEntityKey argVal = (CBEManagedEntityKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }
    /**
    * Returns a new primary key. It returns a new String
    * @see ossj.common.ManagedEntityKeyImpl#makePrimaryKey()
    */
    public Object makePrimaryKey() {
        return new String();
    }

}
