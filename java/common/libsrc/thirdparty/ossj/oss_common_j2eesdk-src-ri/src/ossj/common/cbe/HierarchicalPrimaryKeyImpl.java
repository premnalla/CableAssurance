/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.HierarchicalPrimaryKey;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.HierarchicalPrimaryKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.HierarchicalPrimaryKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class HierarchicalPrimaryKeyImpl
implements HierarchicalPrimaryKey
{ 

    /**
     * Constructs a new HierarchicalPrimaryKeyImpl with empty attributes.
     * 
     */

    public HierarchicalPrimaryKeyImpl() {
    }

    private java.lang.String _hierarchicalprimarykeyimpl_parentDn = null;
    private java.lang.String _hierarchicalprimarykeyimpl_rdn = null;


    /**
     * Changes the parentDn to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new parentDn for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setParentDn(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _hierarchicalprimarykeyimpl_parentDn = value;
    }


    /**
     * Returns this HierarchicalPrimaryKeyImpl's parentDn
     * 
     * @return the parentDn
     * 
    */

    public java.lang.String getParentDn() {
        return _hierarchicalprimarykeyimpl_parentDn;
    }

    /**
     * Changes the rdn to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new rdn for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRdn(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _hierarchicalprimarykeyimpl_rdn = value;
    }


    /**
     * Returns this HierarchicalPrimaryKeyImpl's rdn
     * 
     * @return the rdn
     * 
    */

    public java.lang.String getRdn() {
        return _hierarchicalprimarykeyimpl_rdn;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        HierarchicalPrimaryKey val = null;
        try { 
            val = (HierarchicalPrimaryKey)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("HierarchicalPrimaryKeyImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two HierarchicalPrimaryKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an HierarchicalPrimaryKey object that has the arguments.
     * 
     * @param value the Object to compare with this HierarchicalPrimaryKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof HierarchicalPrimaryKey)) {
            HierarchicalPrimaryKey argVal = (HierarchicalPrimaryKey) value;
            if( !Utils.compareAttributes( getParentDn(), argVal.getParentDn())) { return false; } 
            if( !Utils.compareAttributes( getRdn(), argVal.getRdn())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
