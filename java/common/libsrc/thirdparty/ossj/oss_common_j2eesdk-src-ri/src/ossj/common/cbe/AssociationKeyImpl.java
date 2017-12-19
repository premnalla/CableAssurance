/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.AssociationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.AssociationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.AssociationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AssociationKeyImpl
extends ossj.common.cbe.CBEManagedEntityKeyImpl
implements AssociationKey
{ 

    /**
     * Constructs a new AssociationKeyImpl with empty attributes.
     * 
     */

    public AssociationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AssociationKey val = null;
            val = (AssociationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two AssociationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an AssociationKey object that has the arguments.
     * 
     * @param value the Object to compare with this AssociationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AssociationKey)) {
            AssociationKey argVal = (AssociationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
